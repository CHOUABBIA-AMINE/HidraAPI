#!/usr/bin/env python3
"""Regression coverage for repair CI against an unbootable immediate predecessor."""

import json
import os
import subprocess
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

import resolve_openapi_base as resolver


class VerifiedBaseTest(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory()
        self.addCleanup(self.directory.cleanup)
        self.repo = Path(self.directory.name)
        self.git("init", "-q", "-b", "main")
        self.git("config", "user.name", "Hidra CI Test")
        self.git("config", "user.email", "ci-test@example.invalid")
        self.first = self.commit("verified baseline")
        self.broken = self.commit("unbootable prior application")
        self.current = self.commit("application repair")
        self.git("checkout", "-q", "-b", "other", self.first)
        self.foreign = self.commit("unrelated branch")
        self.git("checkout", "-q", "main")
        self.git_patch = patch.object(resolver, "git", lambda *args: subprocess.run(
            ["git", "-C", str(self.repo), *args], text=True, capture_output=True, timeout=15))
        self.git_patch.start()
        self.addCleanup(self.git_patch.stop)

    def git(self, *args):
        return subprocess.check_output(["git", "-C", str(self.repo), *args], text=True).strip()

    def commit(self, text):
        (self.repo / "state.txt").write_text(text, encoding="utf-8")
        self.git("add", "state.txt")
        self.git("commit", "-q", "-m", text)
        return self.git("rev-parse", "HEAD")

    def workflow(self, sha, **values):
        row = {"id": 590, "run_number": 590, "path": resolver.WORKFLOW_PATH,
               "head_branch": "main", "head_sha": sha, "status": "completed", "conclusion": "success"}
        row.update(values)
        return row

    def history(self, *runs):
        return lambda _: {"workflow_runs": list(runs)}

    def select(self, *runs):
        return resolver.select_base("push", {"before": self.broken}, "main", self.history(*runs))

    def test_failed_immediate_base_uses_verified_ancestor_before_the_repair(self):
        sha, run = self.select(self.workflow(self.broken, conclusion="failure"), self.workflow(self.first))
        self.assertEqual(self.first, sha)
        self.assertEqual(590, run["run_number"])

    def test_successful_immediate_base_remains_the_comparison_target(self):
        sha, _ = self.select(self.workflow(self.broken), self.workflow(self.first))
        self.assertEqual(self.broken, sha)

    def test_docs_success_cannot_certify_an_application_baseline(self):
        sha, _ = self.select(self.workflow(self.broken, path=".github/workflows/docs.yml"), self.workflow(self.first))
        self.assertEqual(self.first, sha)

    def test_foreign_branch_and_nonancestor_and_current_head_are_excluded(self):
        sha, _ = self.select(self.workflow(self.broken, head_branch="other"),
                             self.workflow(self.foreign), self.workflow(self.current), self.workflow(self.first))
        self.assertEqual(self.first, sha)

    def test_queued_and_cancelled_runs_do_not_count_as_success(self):
        sha, _ = self.select(self.workflow(self.broken, status="queued"),
                             self.workflow(self.broken, conclusion="cancelled"), self.workflow(self.first))
        self.assertEqual(self.first, sha)

    def test_pull_request_keeps_exact_target_without_history_lookup(self):
        def forbidden(_):
            self.fail("PR must not replace the exact target with a successful ancestor")
        sha, run = resolver.select_base("pull_request", {"pull_request": {"base": {"sha": self.broken}}}, "main", forbidden)
        self.assertEqual((self.broken, None), (sha, run))

    def test_dispatch_checks_ancestry_against_the_actual_parent(self):
        sha, _ = resolver.select_base("workflow_dispatch", {}, "main", self.history(
            self.workflow(self.current), self.workflow(self.first)))
        self.assertEqual(self.first, sha)

    def test_pagination_can_find_a_successful_ancestor_after_unrelated_history(self):
        visited = []
        def page(number):
            visited.append(number)
            return {"workflow_runs": [self.workflow(self.foreign)] * 100 if number == 1 else [self.workflow(self.first)]}
        sha, _ = resolver.select_base("push", {"before": self.broken}, "main", page)
        self.assertEqual(self.first, sha)
        self.assertEqual([1, 2], visited)

    def test_no_verified_ancestor_never_falls_back_to_broken_parent(self):
        with self.assertRaisesRegex(RuntimeError, "No successful production-CI ancestor"):
            self.select(self.workflow(self.current), self.workflow(self.foreign))

    def test_lookup_failure_is_not_converted_to_an_unverified_fallback(self):
        def unavailable(_):
            raise RuntimeError("GitHub unavailable")
        with self.assertRaisesRegex(RuntimeError, "GitHub unavailable"):
            resolver.select_base("push", {"before": self.broken}, "main", unavailable)

    def test_invalid_or_missing_event_base_fails_before_history_lookup(self):
        for sha in ("", "0" * 40, "invalid", "f" * 40):
            with self.subTest(sha=sha), self.assertRaises(ValueError):
                resolver.select_base("push", {"before": sha}, "main", self.history(self.workflow(self.first)))

    def test_invalid_history_and_malformed_candidate_do_not_become_a_baseline(self):
        with self.assertRaisesRegex(RuntimeError, "invalid workflow history"):
            resolver.select_base("push", {"before": self.broken}, "main", lambda _: {})
        sha, _ = self.select(self.workflow("malformed"), self.workflow(self.first))
        self.assertEqual(self.first, sha)

    def test_cli_emits_exact_verified_sha_to_github_output_with_provenance(self):
        event = self.repo / "event.json"
        event.write_text(json.dumps({"before": self.broken}), encoding="utf-8")
        output = self.repo / "github-output"
        environment = {"GITHUB_EVENT_NAME": "push", "GITHUB_EVENT_PATH": str(event),
                       "GITHUB_REPOSITORY": "example/HidraAPI", "GITHUB_OUTPUT": str(output)}
        with patch.dict(os.environ, environment), patch.object(resolver, "github_history", return_value=self.history(self.workflow(self.first))):
            resolver.main()
        self.assertEqual(f"sha={self.first}\n", output.read_text(encoding="utf-8"))

    def test_repository_and_token_requirements_fail_closed_without_network(self):
        with self.assertRaises(ValueError):
            resolver.github_history("invalid repository", "main")
        with patch.dict(os.environ, {"GH_TOKEN": ""}), self.assertRaisesRegex(ValueError, "read token"):
            resolver.github_history("example/HidraAPI", "main")


if __name__ == "__main__":
    unittest.main()
