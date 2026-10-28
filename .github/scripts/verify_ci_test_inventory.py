#!/usr/bin/env python3
"""Reviewed exact-class selection and strict, source-linked Surefire evidence."""
from __future__ import annotations

import argparse
from collections import Counter
import hashlib
import json
import os
from pathlib import Path
import re
import shutil
import statistics
import subprocess
import xml.etree.ElementTree as ET

LANES = ('fast', 'unit-architecture', 'postgres-0', 'postgres-1', 'postgres-2', 'postgres-3', 'postgres-4', 'postgres-5', 'postgres-6', 'postgres-7', 'postgres-8', 'postgres-9', 'oracle', 'historical')
APPROVED_GREENFIELD_ARCHIVE = frozenset({
    'dz.sh.hidra.modules.organization.infrastructure.persistence.OrganizationEmployeeContactBackfillMigrationTest',
    'dz.sh.hidra.modules.organization.infrastructure.persistence.OrganizationMultilingualCompatibilityRetirementMigrationTest',
    'dz.sh.hidra.modules.organization.infrastructure.persistence.OrganizationMultilingualMigrationTest',
    'dz.sh.hidra.modules.organization.infrastructure.persistence.OrganizationMultilingualTableRetirementMigrationTest',
})
SHA = re.compile(r'^[0-9a-f]{40}$')


def canonical(value):
    return (json.dumps(value, sort_keys=True, separators=(',', ':')) + '\n').encode()


def digest(data):
    return hashlib.sha256(data).hexdigest()


def oracle_forks(cpu_count, memory_mib):
    """Bound test JVMs by CPUs and 2 GiB heaps, reserving 3 GiB for other work."""
    if cpu_count < 1 or memory_mib < 5120:
        raise ValueError('Oracle requires at least one CPU and 5120 MiB RAM')
    return min(4, cpu_count, (memory_mib - 3072) // 2048)


def read_manifest(root, path=None):
    path = path or root / '.github/ci-test-inventory.json'
    return json.loads(path.read_text()), digest(path.read_bytes())


def discover(root):
    result = {}
    for path in sorted((root / 'src/test/java').rglob('*.java')):
        text = path.read_text()
        # This inventory deliberately supports only the reviewed top-level JUnit shape.
        # Exact source hashes and baseline parameterized invocation identities avoid
        # guessing executable counts from annotations or Integration naming.
        if re.search(r'@(Nested|TestFactory|TestTemplate|RepeatedTest)\b', text):
            raise ValueError(f'Unsupported test discovery shape: {path}')
        package = re.search(r'^package\s+([\w.]+)\s*;', text, re.M)
        declaration = re.search(r'^\s*(?:public\s+)?(?P<abstract>abstract\s+)?class\s+'
                                + re.escape(path.stem) + r'\b(?:\s+extends\s+(\w+))?', text, re.M)
        if not package or not declaration:
            raise ValueError(f'Unsupported Java test source: {path}')
        name = package[1] + '.' + path.stem
        if name in result:
            raise ValueError(f'Duplicate source class: {name}')
        parent = declaration[2]
        result[name] = {'path': path.relative_to(root).as_posix(),
                        'sha256': digest(path.read_bytes()),
                        'abstract': bool(declaration['abstract']),
                        'parent': package[1] + '.' + parent if parent else None,
                        'text': text}
    if not result:
        raise ValueError('No test sources discovered')
    return result


def validate(root, manifest):
    if manifest.get('format') != 1 or manifest.get('mode') != 'shadow-fallback-required':
        raise ValueError('Unreviewed inventory format or optimized adoption')
    postgres_lanes(manifest)
    sources = discover(root)
    entries = manifest.get('classes', [])
    policy = manifest.get('greenfield_policy')
    archived = {e['class'] for e in entries if e.get('archived_for_greenfield')}
    if policy is not None:
        if (not isinstance(policy, dict) or policy.get('mode') != 'fresh-schema'
                or policy.get('excluded_classes') != sorted(APPROVED_GREENFIELD_ARCHIVE)
                or not isinstance(policy.get('reason'), str) or not policy['reason'].strip()
                or archived != APPROVED_GREENFIELD_ARCHIVE):
            raise ValueError('Unreviewed greenfield archive selection or policy')
    elif archived:
        raise ValueError('Historical test archived without approved greenfield policy')

    supports = manifest.get('supporting_classes', [])
    names = [entry['class'] for entry in entries + supports]
    if len(names) != len(set(names)) or set(names) != set(sources):
        raise ValueError('Missing, extra or duplicate classified source')
    for entry in entries + supports:
        source = sources[entry['class']]
        if entry['path'] != source['path'] or entry['source_sha256'] != source['sha256']:
            raise ValueError(f'Stale source identity: {entry["class"]}')
        if entry.get('abstract', False) != source['abstract']:
            raise ValueError('Abstract support must not be selected as executable')
        if entry.get('supports', []) != ([source['parent']] if source['parent'] else []):
            raise ValueError('Inherited test association is missing or wrong')
        if source['parent'] and (source['parent'] not in sources or not sources[source['parent']]['abstract']):
            raise ValueError('Unreviewed inherited source')
        if source['abstract'] and entry.get('used_by') != sorted(e['class'] for e in entries
                                                                if entry['class'] in e.get('supports', [])):
            raise ValueError('Unused or stale support association')
    for entry in entries:
        if entry.get('lane') not in ('unit-architecture', 'postgres-integration'):
            raise ValueError('Wrong owning lane')
        if not entry.get('owner') or not entry.get('dependency_reason') or not entry.get('cases'):
            raise ValueError('Missing owner/dependency/case evidence')
        if len(entry['cases']) != len(set(entry['cases'])):
            raise ValueError('Duplicate baseline invocation')
        own_source = sources[entry['class']]
        dependencies = own_source['text'] + ''.join(sources[p]['text'] for p in entry['supports'])
        service = re.search(r'^import\s+org\.testcontainers\.|@SpringBootTest\b|@DataJpaTest\b', dependencies, re.M)
        if entry['lane'] == 'unit-architecture' and service:
            raise ValueError('Wrong lane for live service/context dependency')
        if entry.get('archived_for_greenfield'):
            if entry['class'] not in APPROVED_GREENFIELD_ARCHIVE or entry['lane'] != 'postgres-integration' or entry.get('fast'):
                raise ValueError('Unapproved current-integrity class excluded from greenfield')
        if entry.get('fast'):
            if entry['lane'] != 'unit-architecture' or sources[entry['class']]['abstract']:
                raise ValueError('Fast/DB overlap')
            if service:
                raise ValueError('Fast selection attempts a live service/context')
        if not isinstance(entry.get('baseline_seconds'), (float, int)) or entry['baseline_seconds'] < 0:
            raise ValueError('Invalid measured shard timing')
    return sources


def postgres_lanes(manifest):
    count = manifest.get('postgres_shards', 2)
    if type(count) is not int or count not in (2, 3, 4, 5, 7, 10):
        raise ValueError('Unreviewed PostgreSQL shard count')
    return tuple(f'postgres-{index}' for index in range(count))


def partitions(manifest):
    active = [e for e in manifest['classes'] if not e.get('archived_for_greenfield')]
    historical = sorted(e['class'] for e in manifest['classes'] if e.get('archived_for_greenfield'))
    unit = sorted(e['class'] for e in active if e['lane'] == 'unit-architecture')
    fast = sorted(e['class'] for e in active if e.get('fast'))
    lanes = postgres_lanes(manifest)
    shards, totals = [[] for _ in lanes], [0.0 for _ in lanes]
    database = [e for e in active if e['lane'] == 'postgres-integration']
    measured = [e['baseline_seconds'] for e in database if e['baseline_seconds'] > 0]
    # Zero means unmeasured, not free. Keep recorded measurements unchanged;
    # a scheduling-only median estimate prevents all new suites piling into one lane.
    unmeasured_weight = statistics.median(measured) if measured else 1.0
    def weight(entry):
        return entry['baseline_seconds'] if entry['baseline_seconds'] > 0 else unmeasured_weight
    for entry in sorted(database, key=lambda e: (-weight(e), e['class'])):
        index = min(range(len(lanes)), key=lambda i: (totals[i], i))
        shards[index].append(entry['class'])
        totals[index] += weight(entry)
    result = {'fast': fast, 'unit-architecture': unit,
              'oracle': sorted(e['class'] for e in active), 'historical': historical}
    result.update({lane: sorted(shard) for lane, shard in zip(lanes, shards)})
    union = unit + [name for shard in shards for name in shard]
    if len(union) != len(set(union)) or set(union) != set(result['oracle']) or not all(result[lane] for lane in ('fast', 'unit-architecture', 'oracle') + lanes):
        raise ValueError('Empty, missing or overlapping full partition')
    return result


def parse_reports(directory):
    reports = sorted(directory.glob('TEST-*.xml'))
    if not reports:
        raise ValueError('Missing Surefire reports')
    suites, cases, counts, timings = [], [], Counter(), {}
    for report in reports:
        suite = ET.parse(report).getroot()
        if suite.tag != 'testsuite' or not suite.get('name'):
            raise ValueError('Unsupported report root')
        name = suite.attrib['name']
        suite_cases = suite.findall('testcase')
        declared = {key: int(suite.get(key, '-1')) for key in ('tests', 'failures', 'errors', 'skipped')}
        observed = {'tests': len(suite_cases), **{key: sum(c.find(tag) is not None for c in suite_cases)
                    for key, tag in (('failures', 'failure'), ('errors', 'error'), ('skipped', 'skipped'))}}
        if declared != observed or not suite_cases:
            raise ValueError('Empty or inconsistent Surefire counts')
        if any(declared[key] for key in ('failures', 'errors', 'skipped')):
            raise ValueError('Required test failed, errored or skipped')
        suites.append(name)
        counts.update(declared)
        timings[name] = float(suite.get('time', '0'))
        for case in suite_cases:
            if not case.get('classname') or not case.get('name'):
                raise ValueError('Missing testcase identity')
            cases.append((name, case.attrib['classname'], case.attrib['name']))
    if len(suites) != len(set(suites)) or len(cases) != len(set(cases)):
        raise ValueError('Duplicate suite or invocation')
    return {'classes': sorted(suites), 'cases': sorted(cases), 'counts': dict(counts), 'timings': timings}


def check_reports(manifest, lane, directory):
    actual = parse_reports(directory)
    selected = partitions(manifest)[lane]
    expected = sorted((e['class'], e['class'], name) for e in manifest['classes']
                      if e['class'] in selected for name in e['cases'])
    if actual['classes'] != selected or actual['cases'] != expected:
        raise ValueError('Missing/extra class or invocation; review the exact source inventory')
    return actual


def context(root, inventory_sha256, lane):
    sha = os.environ.get('GITHUB_SHA', '')
    actual = subprocess.check_output(['git', 'rev-parse', 'HEAD'], cwd=root, text=True).strip()
    if not SHA.fullmatch(sha) or sha != actual:
        raise ValueError('Evidence checkout SHA mismatch')
    if subprocess.run(['git', 'diff', '--quiet', '--', 'src/main', 'src/test', 'pom.xml', '.mvn'], cwd=root).returncode:
        raise ValueError('Evidence source differs from exact checkout')
    run = int(os.environ['GITHUB_RUN_ID'])
    attempt = int(os.environ['GITHUB_RUN_ATTEMPT'])
    if min(run, attempt) < 1:
        raise ValueError('Invalid run/attempt')
    evidence = {'format': 1, 'source_sha': sha, 'run_id': run, 'run_attempt': attempt,
            'lane': lane, 'job': os.environ['GITHUB_JOB'], 'inventory_sha256': inventory_sha256,
            'runner_os': os.environ.get('RUNNER_OS'),
            'runner_image': os.environ.get('ImageVersion'),
            'classifier_sha256': digest((root / '.github/scripts/verify_ci_test_inventory.py').read_bytes()),
            'files': {}}
    cache = os.environ.get('HIDRA_MAVEN_CACHE_EVIDENCE')
    if cache:
        evidence['dependency_cache'] = json.loads(Path(cache).read_text())
    return evidence


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--root', type=Path, default=Path('.'))
    parser.add_argument('--select', choices=LANES)
    parser.add_argument('--reports', type=Path)
    parser.add_argument('--evidence', type=Path)
    parser.add_argument('--oracle-forks', nargs=2, type=int, metavar=('CPUS', 'MEMORY_MIB'),
                        help='Print the bounded oracle fork count for observed Linux resources')
    args = parser.parse_args()
    try:
        if args.oracle_forks is not None:
            if args.select or args.reports or args.evidence:
                raise ValueError('Oracle resource query cannot replace selection or report checking')
            print(oracle_forks(*args.oracle_forks))
            return 0
        root = args.root.resolve()
        manifest, fingerprint = read_manifest(root)
        validate(root, manifest)
        selections = partitions(manifest)
        if args.select and not args.reports:
            print(','.join(selections[args.select]))
        elif args.reports:
            if not args.select:
                raise ValueError('Report checking requires an explicit owning lane')
            result = check_reports(manifest, args.select, args.reports)
            if args.evidence:
                evidence = context(root, fingerprint, args.select)
                evidence.update(result)
                version = subprocess.run(['java', '-version'], text=True, capture_output=True, check=True)
                java = (version.stdout + version.stderr).strip()
                if not re.search(r'version "21(?:\.|\")', java):
                    raise ValueError('Java 21 runtime evidence required')
                evidence['lifecycle'] = {'phase': 'test' if args.select == 'fast' else 'clean verify',
                                         'java_version': java, 'pom_sha256': digest((root / 'pom.xml').read_bytes())}
                if args.select != 'fast':
                    jars = sorted(path for path in (root / 'target').glob('*.jar') if not path.name.endswith('.original'))
                    if len(jars) != 1:
                        raise ValueError('Selected clean verify did not produce exactly one packaged application')
                    evidence['lifecycle']['jar_sha256'] = digest(jars[0].read_bytes())
                args.evidence.parent.mkdir(parents=True, exist_ok=True)
                for report in sorted(args.reports.glob('TEST-*.xml')):
                    target = args.evidence.parent / 'reports' / report.name
                    target.parent.mkdir(parents=True, exist_ok=True)
                    shutil.copyfile(report, target)
                    evidence['files'][target.relative_to(args.evidence.parent).as_posix()] = digest(target.read_bytes())
                args.evidence.write_bytes(canonical(evidence))
            print(json.dumps({'lane': args.select, 'classes': len(result['classes']), **result['counts']}))
            if os.environ.get('GITHUB_STEP_SUMMARY'):
                with Path(os.environ['GITHUB_STEP_SUMMARY']).open('a') as summary:
                    summary.write(f'## {args.select}: {len(result["classes"])} classes; {result["counts"]}\n')
                    summary.write('\nSlowest reported class elapsed times (not workflow wall time):\n')
                    for name, seconds in sorted(result['timings'].items(), key=lambda item: (-item[1], item[0]))[:15]:
                        summary.write(f'- {name}: {seconds:.3f}s\n')
        else:
            print(json.dumps({'inventory_sha256': fingerprint,
                              'classes': {key: len(value) for key, value in selections.items()}}))
        return 0
    except (ValueError, KeyError, OSError, ET.ParseError, subprocess.SubprocessError) as error:
        print(f'CI inventory FAILED: {error}')
        return 1


if __name__ == '__main__':
    raise SystemExit(main())
