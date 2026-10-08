#!/usr/bin/env python3
"""Select a runnable, CI-verified historical base without skipping compatibility."""

from __future__ import annotations

import json
import os
import re
import subprocess
import sys
from pathlib import Path
from typing import Callable
from urllib.error import HTTPError, URLError
from urllib.parse import urlencode
from urllib.request import Request, urlopen

SHA = re.compile(r"^[0-9a-fA-F]{40}$")
WORKFLOW_PATH = ".github/workflows/ci.yml"


def git(*arguments: str) -> subprocess.CompletedProcess[str]:
    return subprocess.run(["git", *arguments], text=True, capture_output=True, timeout=15)


def require_commit(sha: str) -> str:
    if not isinstance(sha, str) or not SHA.fullmatch(sha) or sha == "0" * 40:
        raise ValueError("No valid OpenAPI compatibility base revision is available.")
    sha = sha.lower()
    if git("cat-file", "-e", f"{sha}^{{commit}}").returncode:
        raise ValueError("OpenAPI compatibility base is absent from checkout history.")
    return sha


def requested_base(event: str, payload: dict) -> str:
    if event == "pull_request":
        sha = payload.get("pull_request", {}).get("base", {}).get("sha", "")
    elif event == "push":
        sha = payload.get("before", "")
    elif event == "workflow_dispatch":
        result = git("rev-parse", "HEAD^")
        sha = result.stdout.strip() if result.returncode == 0 else ""
    else:
        raise ValueError("Unsupported event for OpenAPI compatibility-base selection.")
    return require_commit(sha)


def select_base(event: str, payload: dict, branch: str,
                history_page: Callable[[int], dict]) -> tuple[str, dict | None]:
    requested = requested_base(event, payload)
    if event == "pull_request":
        # Preserve comparison with the exact PR target revision.
        return requested, None
    # GitHub lists workflow runs from newest to oldest. Select only completed full-CI
    # success, never a successful documentation run or an unrelated branch/descendant.
    for page in range(1, 21):
        runs = history_page(page).get("workflow_runs")
        if not isinstance(runs, list):
            raise RuntimeError("GitHub returned invalid workflow history.")
        for run in runs:
            if (run.get("status") != "completed" or run.get("conclusion") != "success"
                    or run.get("path", "").split("@", 1)[0] != WORKFLOW_PATH
                    or run.get("head_branch") != branch):
                continue
            candidate = run.get("head_sha", "")
            if not isinstance(candidate, str) or not SHA.fullmatch(candidate):
                continue
            candidate = candidate.lower()
            result = git("merge-base", "--is-ancestor", candidate, requested)
            if result.returncode == 0:
                return candidate, run
            if result.returncode not in (1, 128):
                raise RuntimeError("Git could not verify compatibility-base ancestry.")
        if len(runs) < 100:
            break
    raise RuntimeError("No successful production-CI ancestor is available; compatibility remains required.")


def github_history(repository: str, branch: str) -> Callable[[int], dict]:
    if not re.fullmatch(r"[A-Za-z0-9_.-]+/[A-Za-z0-9_.-]+", repository):
        raise ValueError("Invalid GitHub repository identity.")
    api = os.environ.get("GITHUB_API_URL", "https://api.github.com").rstrip("/")
    token = os.environ.get("GH_TOKEN", "")
    if not token:
        raise ValueError("GitHub Actions read token is required for verified-base provenance.")

    def page(number: int) -> dict:
        query = urlencode({"branch": branch, "status": "success", "per_page": 100, "page": number})
        request = Request(f"{api}/repos/{repository}/actions/runs?{query}", headers={
            "Accept": "application/vnd.github+json", "Authorization": f"Bearer {token}",
            "X-GitHub-Api-Version": "2022-11-28", "User-Agent": "HidraAPI-OpenAPI-base",
        })
        try:
            with urlopen(request, timeout=20) as response:
                result = json.load(response)
        except HTTPError as failure:
            raise RuntimeError(f"GitHub workflow-history lookup failed: HTTP {failure.code}.") from None
        except (URLError, TimeoutError, json.JSONDecodeError):
            raise RuntimeError("GitHub workflow-history lookup failed; no unverified fallback is allowed.") from None
        if not isinstance(result, dict):
            raise RuntimeError("GitHub returned invalid workflow-history data.")
        return result

    return page


def main() -> None:
    event = os.environ["GITHUB_EVENT_NAME"]
    payload = json.loads(Path(os.environ["GITHUB_EVENT_PATH"]).read_text(encoding="utf-8"))
    if event == "pull_request":
        history = lambda _: {}  # PR resolution does not consume GitHub success history.
    else:
        history = github_history(os.environ["GITHUB_REPOSITORY"], "main")
    sha, run = select_base(event, payload, "main", history)
    if run is None:
        print(f"OpenAPI comparison base: exact PR target {sha}")
    else:
        print(f"OpenAPI comparison base: {sha}; successful CI #{run['run_number']} (run {run['id']}).")
    with Path(os.environ["GITHUB_OUTPUT"]).open("a", encoding="utf-8") as output:
        output.write(f"sha={sha}\n")


if __name__ == "__main__":
    try:
        main()
    except (ValueError, RuntimeError, KeyError, OSError, subprocess.TimeoutExpired) as failure:
        print(f"OpenAPI base resolution failed: {failure}", file=sys.stderr)
        raise SystemExit(1)
