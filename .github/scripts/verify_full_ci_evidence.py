#!/usr/bin/env python3
"""Fail-closed full CI aggregation and authenticated exact-candidate release lookup."""
from __future__ import annotations

import argparse
from collections import Counter
import json
import os
from pathlib import Path, PurePosixPath
import re
import shutil
import tempfile
from urllib.error import HTTPError
from urllib.request import Request, build_opener, HTTPRedirectHandler, urlopen
import zipfile
import io

import verify_ci_test_inventory as inventory
import generate_data_dictionary as dictionary

REQUIRED_LANES = ('static-validation', 'unit-architecture', 'postgres-0', 'postgres-1', 'api-contract', 'oracle')
REQUIRED_NEEDS = ('static-validation', 'unit-architecture', 'postgres-integration', 'api-contract', 'verify')
REQUIRED_JOB_NAMES = ('Static validation', 'Java 21 unit and architecture', 'PostgreSQL integration shard 0',
                      'PostgreSQL integration shard 1', 'API contract and dictionary',
                      'Java 21 Maven verification', 'Full Verification Gate')
API_FILES = ('target/openapi/hidra-api.json', 'target/openapi/hidra-api-base.json',
             'target/openapi/base-provenance.txt', 'target/database/schema.json',
             'target/database/DATA_DICTIONARY.md', 'target/database/verification.json')
STATIC_CHECKS = ('docs-unit', 'openapi-unit', 'dictionary-unit', 'base-resolver-unit',
                 'inventory-unit', 'full-evidence-unit', 'docs', 'offline-openapi',
                 'p1-evidence', 'app-ha', 'postgres-ha', 'backup-pitr', 'release-artifacts',
                 'observability', 'database-operations', 'inventory')


def safe_path(directory, relative):
    path = PurePosixPath(relative)
    if path.is_absolute() or '..' in path.parts or '\\' in relative:
        raise ValueError('Unsafe evidence path')
    result = directory / relative
    if result.is_symlink() or directory.resolve() not in result.resolve().parents:
        raise ValueError('Evidence must remain inside its artifact')
    return result


def verify_files(evidence, directory):
    files = evidence.get('files')
    if not isinstance(files, dict) or not files:
        raise ValueError('Missing artifact file hashes')
    for name, expected in files.items():
        path = safe_path(directory, name)
        if not re.fullmatch(r'[0-9a-f]{64}', expected) or not path.is_file() or inventory.digest(path.read_bytes()) != expected:
            raise ValueError('Absent or corrupt evidence artifact')


def api_facts(evidence, directory, root, sha):
    expected = {'files/' + name for name in API_FILES}
    if not expected.issubset(evidence['files']):
        raise ValueError('Missing current/base API or dictionary provenance')
    payload = json.loads((directory / 'files/target/openapi/hidra-api.json').read_text())
    if payload.get('x-hidra-ci-source-sha') != sha:
        raise ValueError('Stale current API')
    schema = json.loads((directory / 'files/target/database/schema.json').read_text())
    verification = json.loads((directory / 'files/target/database/verification.json').read_text())
    if schema.get('source_sha') != sha or verification.get('source_sha') != sha or not verification.get('reviewed_dictionary_checked'):
        raise ValueError('Stale or unchecked dictionary')
    dictionary.validate_document(schema, root)
    rendered = (directory / 'files/target/database/DATA_DICTIONARY.md').read_bytes()
    if verification['catalog_sha256'] != dictionary.digest(dictionary.canonical(schema)) or verification['dictionary_sha256'] != inventory.digest(rendered):
        raise ValueError('Dictionary capture/render hash mismatch')
    if verification['source_bundle_sha256'] != schema['source']['bundle_sha256'] or verification['server_version'] != schema['catalog']['server_version']:
        raise ValueError('Dictionary source/server provenance mismatch')
    if dictionary.comparable_markdown(rendered.decode()) != dictionary.comparable_markdown((root / 'doc/database/DATA_DICTIONARY.md').read_text()):
        raise ValueError('Reviewed physical dictionary drift')
    base = evidence.get('base_sha', '')
    provenance = (directory / 'files/target/openapi/base-provenance.txt').read_text()
    if not inventory.SHA.fullmatch(base) or base == sha or base not in provenance:
        raise ValueError('Missing or mismatched historical base identity')
    return {'current_api_sha256': evidence['files']['files/target/openapi/hidra-api.json'],
            'base_api_sha256': evidence['files']['files/target/openapi/hidra-api-base.json'],
            'base_sha': base, 'catalog_sha256': verification['catalog_sha256'],
            'source_bundle_sha256': verification['source_bundle_sha256'],
            'server_version': verification['server_version']}


def stamp(root, kind, output):
    manifest, fingerprint = inventory.read_manifest(root)
    inventory.validate(root, manifest)
    output.parent.mkdir(parents=True, exist_ok=True)
    if kind == 'oracle':
        evidence = json.loads(output.read_text())
        if evidence['lane'] != 'oracle':
            raise ValueError('Oracle test evidence required first')
    else:
        evidence = inventory.context(root, fingerprint, kind)
    evidence['kind'] = kind
    if kind == 'static-validation':
        # Emitted only after the corresponding required workflow steps succeed;
        # aggregate also requires the owning GitHub job success.
        evidence['checks'] = list(STATIC_CHECKS)
        source = root / '.github/workflows/ci.yml'
        target = output.parent / 'files/ci.yml'
        target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(source, target)
        evidence['files']['files/ci.yml'] = inventory.digest(target.read_bytes())
    else:
        evidence['base_sha'] = os.environ['HIDRA_BASE_SHA']
        for name in API_FILES:
            source = root / name
            if not source.is_file() or not source.stat().st_size:
                raise ValueError('Missing API/dictionary output')
            target = output.parent / 'files' / name
            target.parent.mkdir(parents=True, exist_ok=True)
            shutil.copyfile(source, target)
            evidence['files'][target.relative_to(output.parent).as_posix()] = inventory.digest(target.read_bytes())
        verify_files(evidence, output.parent)
        evidence['api'] = api_facts(evidence, output.parent, root, evidence['source_sha'])
    output.write_bytes(inventory.canonical(evidence))


def aggregate(root, directory, sha, run, attempt, needs):
    manifest, fingerprint = inventory.read_manifest(root)
    inventory.validate(root, manifest)
    if set(needs) != set(REQUIRED_NEEDS) or any(needs[key].get('result') != 'success' for key in REQUIRED_NEEDS):
        raise ValueError('Mandatory dependency failed, skipped, cancelled or absent')
    documents = {}
    for path in sorted(directory.rglob('evidence.json')):
        evidence = json.loads(path.read_text())
        lane = evidence.get('lane')
        if lane not in REQUIRED_LANES or lane in documents:
            raise ValueError('Extra, overlapping or duplicate evidence lane')
        if (evidence.get('format'), evidence.get('source_sha'), evidence.get('run_id'), evidence.get('run_attempt'), evidence.get('inventory_sha256')) != (1, sha, run, attempt, fingerprint):
            raise ValueError('Wrong SHA, run, attempt or classifier')
        classifier = inventory.digest((root / '.github/scripts/verify_ci_test_inventory.py').read_bytes())
        if evidence.get('classifier_sha256') != classifier:
            raise ValueError('Wrong selection helper revision')
        expected_job = {'oracle': 'verify', 'postgres-0': 'postgres-integration',
                        'postgres-1': 'postgres-integration'}.get(lane, lane)
        if evidence.get('job') != expected_job:
            raise ValueError('Missing owning job identity')
        verify_files(evidence, path.parent)
        documents[lane] = (evidence, path)
    if set(documents) != set(REQUIRED_LANES):
        raise ValueError('Missing full lane evidence')
    static, _ = documents['static-validation']
    if static.get('checks') != list(STATIC_CHECKS):
        raise ValueError('Missing required static validator')
    if static['files'].get('files/ci.yml') != inventory.digest((root / '.github/workflows/ci.yml').read_bytes()):
        raise ValueError('Static evidence workflow differs from candidate')
    all_cases, all_classes = [], []
    for lane in ('unit-architecture', 'postgres-0', 'postgres-1', 'oracle'):
        evidence, path = documents[lane]
        result = inventory.check_reports(manifest, lane, path.parent / 'reports')
        lifecycle = evidence.get('lifecycle', {})
        if (lifecycle.get('phase') != 'clean verify'
                or lifecycle.get('pom_sha256') != inventory.digest((root / 'pom.xml').read_bytes())
                or not re.search(r'version "21(?:\.|\")', lifecycle.get('java_version', ''))
                or not re.fullmatch(r'[0-9a-f]{64}', lifecycle.get('jar_sha256', ''))):
            raise ValueError('Missing Java21/clean-verify/package lifecycle evidence')
        for field in ('classes', 'cases', 'counts', 'timings'):
            if inventory.canonical(result[field]) != inventory.canonical(evidence.get(field)):
                raise ValueError('Report payload differs from hashed raw XML')
        if lane != 'oracle':
            all_cases.extend(result['cases'])
            all_classes.extend(result['classes'])
    oracle, _ = documents['oracle']
    if len(all_cases) != len(set(all_cases)) or len(all_classes) != len(set(all_classes)) or sorted(all_classes) != oracle['classes'] or inventory.canonical(sorted(all_cases)) != inventory.canonical(oracle['cases']):
        raise ValueError('Partition/oracle class and case equivalence failed')
    apis = [api_facts(documents[lane][0], documents[lane][1].parent, root, sha)
            for lane in ('api-contract', 'oracle')]
    if apis[0] != apis[1]:
        raise ValueError('Partition/oracle API, base or physical catalog mismatch')
    return {'format': 'partition-shadow-v1', 'source_sha': sha, 'run_id': run,
            'run_attempt': attempt, 'inventory_sha256': fingerprint,
            'mode': manifest['mode'], 'fallback_required': True,
            'same_run_equivalence': True, 'optimized_adoption_eligible': False,
            'classes': len(all_classes), 'counts': oracle['counts'], 'api': apis[0],
            'needs': needs, 'lane_evidence': {lane: inventory.digest(path.read_bytes())
                                             for lane, (_, path) in documents.items()}}


def select_run(runs, sha):
    matches = [run for run in runs if run.get('name') == 'HidraAPI CI'
               and run.get('path', '').split('@')[0] == '.github/workflows/ci.yml'
               and run.get('head_sha') == sha and run.get('head_branch') == 'main'
               and run.get('event') in ('push', 'workflow_dispatch')]
    if not matches:
        raise ValueError('No exact-main full verification run')
    latest = max(matches, key=lambda r: (r['run_number'], r.get('run_attempt', 1)))
    if sum((r['run_number'], r.get('run_attempt', 1)) == (latest['run_number'], latest.get('run_attempt', 1)) for r in matches) != 1:
        raise ValueError('Ambiguous full verification run')
    if latest.get('status') != 'completed' or latest.get('conclusion') != 'success':
        raise ValueError('Latest full verification has not succeeded')
    return latest


def verify_jobs(jobs, legacy=False):
    if legacy:
        required = ('Java 21 Maven verification',)
    else:
        required = REQUIRED_JOB_NAMES
    names = [job.get('name') for job in jobs]
    if sorted(names) != sorted(required):
        raise ValueError('Missing, extra or duplicated full jobs')
    if any(job.get('status') != 'completed' or job.get('conclusion') != 'success' for job in jobs):
        raise ValueError('Failed, skipped, timed out or cancelled full job')


def api_get(path):
    repository = os.environ['GITHUB_REPOSITORY']
    if not re.fullmatch(r'[A-Za-z0-9_.-]+/[A-Za-z0-9_.-]+', repository):
        raise ValueError('Invalid repository')
    base = os.environ.get('GITHUB_API_URL', 'https://api.github.com').rstrip('/')
    if base != 'https://api.github.com':
        raise ValueError('Unsupported release evidence API')
    request = Request(f'{base}/repos/{repository}/{path}', headers={
        'Authorization': 'Bearer ' + os.environ['GH_TOKEN'],
        'Accept': 'application/vnd.github+json', 'X-GitHub-Api-Version': '2022-11-28'})
    with urlopen(request, timeout=30) as response:
        return json.load(response)


def pages(path, field):
    result = []
    for page in range(1, 21):
        separator = '&' if '?' in path else '?'
        response = api_get(f'{path}{separator}per_page=100&page={page}')
        items = response.get(field)
        if not isinstance(items, list):
            raise ValueError('Malformed or incomplete GitHub pagination')
        result.extend(items)
        if len(items) < 100:
            if response.get('total_count', len(result)) != len(result):
                raise ValueError('Incomplete GitHub pagination')
            return result
    raise ValueError('GitHub pagination limit; no partial acceptance')


class NoRedirect(HTTPRedirectHandler):
    def redirect_request(self, req, fp, code, msg, headers, newurl):
        return None


def archive(artifact):
    if artifact.get('expired') or not re.fullmatch(r'sha256:[0-9a-f]{64}', artifact.get('digest', '')):
        raise ValueError('Expired artifact or absent GitHub digest')
    repository = os.environ['GITHUB_REPOSITORY']
    request = Request(f'https://api.github.com/repos/{repository}/actions/artifacts/{artifact["id"]}/zip',
                      headers={'Authorization': 'Bearer ' + os.environ['GH_TOKEN']})
    try:
        build_opener(NoRedirect).open(request, timeout=30)
        raise ValueError('Missing artifact redirect')
    except HTTPError as response:
        if response.code != 302 or not response.headers.get('Location', '').startswith('https://'):
            raise ValueError('Invalid artifact redirect') from None
        # The signed download URL receives no GitHub authorization header.
        with urlopen(response.headers['Location'], timeout=60) as download:
            data = download.read(50_000_001)
    if len(data) > 50_000_000 or inventory.digest(data) != artifact['digest'][7:]:
        raise ValueError('Artifact ZIP size or digest mismatch')
    return data


def extract(data, destination):
    with zipfile.ZipFile(io.BytesIO(data)) as zipped:
        if sum(entry.file_size for entry in zipped.infolist()) > 150_000_000:
            raise ValueError('Oversized evidence archive')
        for entry in zipped.infolist():
            if entry.is_dir():
                continue
            path = safe_path(destination, entry.filename)
            if path.exists() or (entry.external_attr >> 16) & 0o170000 == 0o120000:
                raise ValueError('Duplicate or symlink ZIP entry')
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_bytes(zipped.read(entry))


def release(root, policy, sha):
    if not inventory.SHA.fullmatch(sha):
        raise ValueError('Full lowercase candidate SHA required')
    run = select_run(pages(f'actions/runs?head_sha={sha}', 'workflow_runs'), sha)
    attempt = run.get('run_attempt', 1)
    jobs = pages(f'actions/runs/{run["id"]}/attempts/{attempt}/jobs', 'jobs')
    source = (root / '.github/workflows/ci.yml').read_bytes()
    legacy = next((item for item in policy.get('legacy_acceptances', [])
                   if (item['source_sha'], item['run_id'], item['run_attempt'], item['workflow_sha256'])
                   == (sha, run['id'], attempt, inventory.digest(source))), None)
    verify_jobs(jobs, legacy=bool(legacy))
    artifacts = pages(f'actions/runs/{run["id"]}/artifacts', 'artifacts')
    with tempfile.TemporaryDirectory() as temp:
        destination = Path(temp)
        if legacy:
            matches = [a for a in artifacts if a['id'] == legacy['report_artifact_id']
                       and a['digest'] == 'sha256:' + legacy['report_zip_sha256']]
            if len(matches) != 1:
                raise ValueError('Recorded legacy report artifact unavailable')
            extract(archive(matches[0]), destination)
            actual = inventory.parse_reports(destination)
            discovered = {name for name, value in inventory.discover(root).items() if not value['abstract']}
            if set(actual['classes']) != discovered:
                raise ValueError('Legacy unrestricted test/source coverage mismatch')
            print(f'Explicit recorded legacy full run {run["id"]}, attempt {attempt}, SHA {sha}')
        else:
            if b'# HIDRA_CI_FORMAT: partition-shadow-v1' not in source:
                raise ValueError('Unknown workflow format; no same-name fallback')
            name = f'hidra-full-ci-evidence-{sha}-{run["id"]}-{attempt}'
            matches = [a for a in artifacts if a.get('name') == name
                       and a.get('workflow_run', {}).get('head_sha') == sha]
            if len(matches) != 1:
                raise ValueError('Missing or ambiguous aggregate artifact')
            extract(archive(matches[0]), destination)
            gate = json.loads((destination / 'gate.json').read_text())
            actual = aggregate(root, destination, sha, run['id'], attempt, gate['needs'])
            if actual != gate:
                raise ValueError('Aggregate receipt differs from authenticated raw evidence')
            print(f'Verified full aggregate with mandatory fallback: run {run["id"]}, attempt {attempt}, SHA {sha}')


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--root', type=Path, default=Path('.'))
    commands = parser.add_subparsers(dest='command', required=True)
    stamp_args = commands.add_parser('stamp')
    stamp_args.add_argument('--kind', choices=('static-validation', 'api-contract', 'oracle'), required=True)
    stamp_args.add_argument('--output', type=Path, required=True)
    gate_args = commands.add_parser('aggregate')
    gate_args.add_argument('--directory', type=Path, required=True)
    release_args = commands.add_parser('release')
    release_args.add_argument('--sha', required=True)
    release_args.add_argument('--policy', type=Path, required=True)
    args = parser.parse_args()
    try:
        root = args.root.resolve()
        if args.command == 'stamp':
            stamp(root, args.kind, args.output)
        elif args.command == 'aggregate':
            result = aggregate(root, args.directory, os.environ['GITHUB_SHA'], int(os.environ['GITHUB_RUN_ID']),
                               int(os.environ['GITHUB_RUN_ATTEMPT']), json.loads(os.environ['HIDRA_CI_NEEDS']))
            (args.directory / 'gate.json').write_bytes(inventory.canonical(result))
            print(json.dumps(result, sort_keys=True))
        else:
            release(root, json.loads(args.policy.read_text()), args.sha)
        return 0
    except (ValueError, KeyError, TypeError, OSError, zipfile.BadZipFile, inventory.ET.ParseError) as error:
        print(f'Full CI evidence FAILED: {error}')
        return 1


if __name__ == '__main__':
    raise SystemExit(main())
