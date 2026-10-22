#!/usr/bin/env python3
"""Selection failures must not turn into silently reduced verification."""
import copy
import io
import json
from pathlib import Path
import tempfile
import unittest
from unittest.mock import patch

import verify_ci_test_inventory as ci


def source(root, name, text):
    path = root / 'src/test/java/example' / (name + '.java')
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text('package example;\n' + text)
    return path


def fixture(root, postgres_shards=2):
    source(root, 'UnitTest', 'class UnitTest { @Test void provesBehavior() {} }\n')
    source(root, 'FirstDatabaseTest', 'import org.testcontainers.containers.PostgreSQLContainer;\nclass FirstDatabaseTest {}\n')
    source(root, 'SecondDatabaseTest', 'import org.testcontainers.containers.PostgreSQLContainer;\nclass SecondDatabaseTest {}\n')
    if postgres_shards >= 3:
        source(root, 'ThirdDatabaseTest', 'import org.testcontainers.containers.PostgreSQLContainer;\nclass ThirdDatabaseTest {}\n')
    if postgres_shards >= 4:
        source(root, 'FourthDatabaseTest', 'import org.testcontainers.containers.PostgreSQLContainer;\nclass FourthDatabaseTest {}\n')
    if postgres_shards >= 5:
        source(root, 'FifthDatabaseTest', 'import org.testcontainers.containers.PostgreSQLContainer;\nclass FifthDatabaseTest {}\n')
    if postgres_shards >= 6:
        source(root, 'SixthDatabaseTest', 'import org.testcontainers.containers.PostgreSQLContainer;\nclass SixthDatabaseTest {}\n')
    if postgres_shards >= 7:
        source(root, 'SeventhDatabaseTest', 'import org.testcontainers.containers.PostgreSQLContainer;\nclass SeventhDatabaseTest {}\n')
    classes = []
    for name, item in ci.discover(root).items():
        unit = name.endswith('UnitTest')
        classes.append({'class': name, 'path': item['path'], 'source_sha256': item['sha256'],
                        'owner': 'example', 'lane': 'unit-architecture' if unit else 'postgres-integration',
                        'fast': unit, 'dependency_reason': 'Reviewed runtime dependency', 'supports': [],
                        'cases': ['provesBehavior'], 'baseline_seconds': 1.0})
    return {'format': 1, 'mode': 'shadow-fallback-required', 'postgres_shards': postgres_shards,
            'classes': classes, 'supporting_classes': []}


def report(directory, name='example.UnitTest', case='provesBehavior', outcome='', declared=None):
    directory.mkdir(parents=True, exist_ok=True)
    counts = dict(tests=1, failures=int(outcome == 'failure'), errors=int(outcome == 'error'), skipped=int(outcome == 'skipped'))
    counts.update(declared or {})
    attributes = ' '.join(f'{key}="{value}"' for key, value in counts.items())
    path = directory / ('TEST-' + name + '.xml')
    path.write_text(f'<testsuite name="{name}" time="1.2" {attributes}>'
                    f'<testcase classname="{name}" name="{case}">{"<" + outcome + "/>" if outcome else ""}</testcase></testsuite>')
    return path


class OracleResourceTest(unittest.TestCase):
    def test_public_runner_uses_four_forks_with_bounded_heap_budget(self):
        self.assertEqual(ci.oracle_forks(4, 15988), 4)
        self.assertEqual(ci.oracle_forks(64, 262144), 4)

    def test_cpu_and_memory_each_limit_concurrency(self):
        self.assertEqual(ci.oracle_forks(2, 16384), 2)
        self.assertEqual(ci.oracle_forks(4, 7168), 2)
        self.assertEqual(ci.oracle_forks(4, 5120), 1)
        for cpus in (1, 2, 3, 4, 8):
            for memory in (5120, 7167, 7168, 9216, 11263, 11264, 15988):
                with self.subTest(cpus=cpus, memory=memory):
                    forks = ci.oracle_forks(cpus, memory)
                    self.assertGreaterEqual(forks, 1)
                    self.assertLessEqual(forks, min(cpus, 4))
                    self.assertLessEqual(forks * 2048 + 3072, memory)

    def test_unusable_resources_fail_before_maven(self):
        for cpus, memory in ((0, 16384), (-1, 16384), (4, 5119), (4, 0), (4, -1)):
            with self.subTest(cpus=cpus, memory=memory):
                with self.assertRaises(ValueError): ci.oracle_forks(cpus, memory)

    def test_resource_cli_cannot_bypass_class_or_report_verification(self):
        with patch('sys.argv', ['inventory', '--oracle-forks', '4', '15988']), \
                patch('sys.stdout', new_callable=io.StringIO) as output:
            self.assertEqual(ci.main(), 0)
            self.assertEqual(output.getvalue(), '4\n')
        for arguments in (['--select', 'oracle'], ['--reports', 'reports'], ['--evidence', 'receipt.json']):
            with self.subTest(arguments=arguments), \
                    patch('sys.argv', ['inventory', '--oracle-forks', '4', '15988'] + arguments), \
                    patch('sys.stdout', new_callable=io.StringIO):
                self.assertEqual(ci.main(), 1)


class InventoryTest(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.manifest = fixture(self.root)

    def test_context_retains_prebuild_cache_receipt(self):
        receipt = {'mode': 'cold', 'initial_entries': 0}
        path = self.root / 'cache.json'; path.write_text(json.dumps(receipt))
        helper = self.root / '.github/scripts/verify_ci_test_inventory.py'
        helper.parent.mkdir(parents=True); helper.write_text('fixture')
        from types import SimpleNamespace
        env = {'GITHUB_SHA': 'a' * 40, 'GITHUB_RUN_ID': '5', 'GITHUB_RUN_ATTEMPT': '2',
               'GITHUB_JOB': 'postgres-integration', 'HIDRA_MAVEN_CACHE_EVIDENCE': str(path)}
        with patch.dict('os.environ', env, clear=True), \
                patch.object(ci.subprocess, 'check_output', return_value='a' * 40), \
                patch.object(ci.subprocess, 'run', return_value=SimpleNamespace(returncode=0)):
            evidence = ci.context(self.root, 'b' * 64, 'postgres-4')
        self.assertEqual(evidence['dependency_cache'], receipt)

    def test_complete_disjoint_selection(self):
        ci.validate(self.root, self.manifest)
        lanes = ci.partitions(self.manifest)
        self.assertEqual(len(lanes['oracle']), 3)
        self.assertEqual(set(lanes['postgres-0']) & set(lanes['postgres-1']), set())
        self.assertEqual(lanes, ci.partitions(copy.deepcopy(self.manifest)))

    def test_three_shards_are_disjoint_complete_and_weighted(self):
        manifest = fixture(self.root, postgres_shards=3)
        template = next(e for e in manifest['classes'] if e['lane'] == 'postgres-integration')
        for name in ('FourthDatabaseTest', 'FifthDatabaseTest', 'SixthDatabaseTest'):
            path = source(self.root, name, f'import org.testcontainers.containers.PostgreSQLContainer;\nclass {name} {{}}\n')
            entry = ci.discover(self.root)['example.' + name]
            manifest['classes'].append(dict(template, **{'class': 'example.' + name,
                'path': entry['path'], 'source_sha256': entry['sha256']}))
        weights = [9.0, 8.0, 7.0, 6.0, 5.0, 4.0]
        for entry, weight in zip((e for e in manifest['classes'] if e['lane'] == 'postgres-integration'), weights):
            entry['baseline_seconds'] = weight
        ci.validate(self.root, manifest)
        lanes = ci.partitions(manifest)
        names = [name for lane in ci.postgres_lanes(manifest) for name in lanes[lane]]
        self.assertEqual(len(names), len(set(names)))
        self.assertEqual(set(names + lanes['unit-architecture']), set(lanes['oracle']))
        self.assertEqual([sum(e['baseline_seconds'] for e in manifest['classes'] if e['class'] in lanes[lane])
                          for lane in ci.postgres_lanes(manifest)], [13.0, 13.0, 13.0])
        self.assertEqual(lanes, ci.partitions(copy.deepcopy(manifest)))

    def test_four_shards_cover_each_class_once(self):
        manifest = fixture(self.root, postgres_shards=4)
        ci.validate(self.root, manifest)
        lanes = ci.partitions(manifest)
        names = [name for lane in ci.postgres_lanes(manifest) for name in lanes[lane]]
        self.assertEqual(len(names), 4)
        self.assertEqual(len(set(names)), 4)
        self.assertEqual(set(names + lanes['unit-architecture']), set(lanes['oracle']))
        self.assertTrue(all(lanes[lane] for lane in ci.postgres_lanes(manifest)))

    def test_five_shards_cover_each_class_once(self):
        manifest = fixture(self.root, postgres_shards=5)
        ci.validate(self.root, manifest)
        lanes = ci.partitions(manifest)
        names = [name for lane in ci.postgres_lanes(manifest) for name in lanes[lane]]
        self.assertEqual(len(names), 5)
        self.assertEqual(len(set(names)), 5)
        self.assertEqual(set(names + lanes['unit-architecture']), set(lanes['oracle']))
        self.assertTrue(all(lanes[lane] for lane in ci.postgres_lanes(manifest)))

    def test_seven_shards_cover_each_class_once(self):
        manifest = fixture(self.root, postgres_shards=7)
        ci.validate(self.root, manifest)
        lanes = ci.partitions(manifest)
        names = [name for lane in ci.postgres_lanes(manifest) for name in lanes[lane]]
        self.assertEqual(len(ci.postgres_lanes(manifest)), 7)
        self.assertEqual(len(names), 7)
        self.assertEqual(len(set(names)), 7)
        self.assertEqual(set(names + lanes['unit-architecture']), set(lanes['oracle']))
        self.assertTrue(all(lanes[lane] for lane in ci.postgres_lanes(manifest)))
        self.assertEqual(lanes, ci.partitions(copy.deepcopy(manifest)))

    def test_unreviewed_or_empty_shard_count_denies(self):
        for count in (0, 1, 6, True, '3', 3, 4, 5):
            with self.subTest(count=count):
                self.manifest['postgres_shards'] = count
                with self.assertRaises(ValueError): ci.partitions(self.manifest)

    def test_archive_without_policy_denies(self):
        selected = next(e for e in self.manifest['classes'] if e['lane'] == 'postgres-integration')
        selected['archived_for_greenfield'] = True
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_unapproved_archive_denies(self):
        selected = next(e for e in self.manifest['classes'] if e['lane'] == 'postgres-integration')
        selected['archived_for_greenfield'] = True
        self.manifest['greenfield_policy'] = {'mode': 'fresh-schema',
            'excluded_classes': [selected['class']], 'reason': 'synthetic'}
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_archive_requires_exact_agreed_set(self):
        self.manifest['greenfield_policy'] = {'mode': 'fresh-schema',
            'excluded_classes': sorted(ci.APPROVED_GREENFIELD_ARCHIVE), 'reason': 'synthetic'}
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_archive_not_misreported_in_greenfield_oracle(self):
        # Keep two runnable DB classes after archiving one: the scheduler
        # correctly rejects an empty required shard.
        path = source(self.root, 'ThirdDatabaseTest', """import org.testcontainers.containers.PostgreSQLContainer;
class ThirdDatabaseTest {}
""")
        third = ci.discover(self.root)['example.ThirdDatabaseTest']
        template = next(e for e in self.manifest['classes'] if e['lane'] == 'postgres-integration')
        self.manifest['classes'].append(dict(template, **{
            'class': 'example.ThirdDatabaseTest', 'path': third['path'],
            'source_sha256': ci.digest(path.read_bytes()), 'baseline_seconds': 1.0,
        }))
        template['archived_for_greenfield'] = True
        lanes = ci.partitions(self.manifest)
        self.assertEqual(lanes['historical'], [template['class']])
        self.assertNotIn(template['class'], lanes['oracle'])
        self.assertTrue(lanes['postgres-0'] and lanes['postgres-1'])
        self.assertEqual(set(lanes['unit-architecture'] + lanes['postgres-0'] + lanes['postgres-1']),
                         set(lanes['oracle']))

    def test_new_unclassified_source_denies(self):
        source(self.root, 'NewTest', 'class NewTest {}')
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_removed_source_denies(self):
        (self.root / self.manifest['classes'][0]['path']).unlink()
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_duplicate_class_denies(self):
        self.manifest['classes'].append(copy.deepcopy(self.manifest['classes'][0]))
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_stale_source_denies(self):
        path = self.root / self.manifest['classes'][0]['path']
        path.write_text(path.read_text() + '\n// New source must be reviewed\n')
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_live_dependency_cannot_become_unit_or_fast(self):
        self.manifest['classes'][0].update(lane='unit-architecture', fast=True)
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_wrong_lane_denies(self):
        self.manifest['classes'][0]['lane'] = 'api-contract'
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_unsupported_source_discovery_denies(self):
        source(self.root, 'NestedTest', 'class NestedTest { @Nested class Cases {} }')
        with self.assertRaises(ValueError): ci.discover(self.root)

    def test_inherited_cases_and_support_are_not_dropped(self):
        parent = source(self.root, 'Support', 'abstract class Support { @Test void inherited() {} }')
        child = source(self.root, 'UnitTest', 'class UnitTest extends Support {}')
        entry = next(e for e in self.manifest['classes'] if e['class'] == 'example.UnitTest')
        entry.update(source_sha256=ci.digest(child.read_bytes()), supports=['example.Support'], cases=['inherited'])
        self.manifest['supporting_classes'] = [{'class': 'example.Support', 'path': parent.relative_to(self.root).as_posix(),
                  'source_sha256': ci.digest(parent.read_bytes()), 'abstract': True, 'supports': [], 'used_by': ['example.UnitTest']}]
        ci.validate(self.root, self.manifest)
        report(self.root / 'reports', case='inherited')
        self.assertEqual(ci.check_reports(self.manifest, 'fast', self.root / 'reports')['counts']['tests'], 1)
        entry['supports'] = []
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_unreviewed_optimized_adoption_denies(self):
        self.manifest['mode'] = 'optimized'
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_duplicate_invocation_denies(self):
        self.manifest['classes'][0]['cases'] *= 2
        with self.assertRaises(ValueError): ci.validate(self.root, self.manifest)

    def test_missing_report_denies(self):
        with self.assertRaises(ValueError): ci.check_reports(self.manifest, 'fast', self.root / 'reports')

    def test_exact_report_passes(self):
        report(self.root / 'reports')
        self.assertEqual(ci.check_reports(self.manifest, 'fast', self.root / 'reports')['classes'], ['example.UnitTest'])

    def test_failed_errored_skipped_reports_deny(self):
        for outcome in ('failure', 'error', 'skipped'):
            with self.subTest(outcome=outcome):
                report(self.root / 'reports', outcome=outcome)
                with self.assertRaises(ValueError): ci.parse_reports(self.root / 'reports')

    def test_forged_counts_deny(self):
        report(self.root / 'reports', declared={'tests': 0})
        with self.assertRaises(ValueError): ci.parse_reports(self.root / 'reports')

    def test_wrong_case_or_extra_class_denies(self):
        report(self.root / 'reports', case='omittedActualBehavior')
        with self.assertRaises(ValueError): ci.check_reports(self.manifest, 'fast', self.root / 'reports')
        report(self.root / 'reports')
        report(self.root / 'reports', name='example.UnselectedTest')
        with self.assertRaises(ValueError): ci.check_reports(self.manifest, 'fast', self.root / 'reports')

    def test_duplicate_report_identity_denies(self):
        path = report(self.root / 'reports')
        (path.parent / 'TEST-duplicate.xml').write_bytes(path.read_bytes())
        with self.assertRaises(ValueError): ci.parse_reports(path.parent)

    def test_parameterized_invocations_are_required_individually(self):
        entry = next(e for e in self.manifest['classes'] if e['class'] == 'example.UnitTest')
        entry['cases'] = ['provesBehavior(String)[1]', 'provesBehavior(String)[2]']
        report(self.root / 'reports', case=entry['cases'][0])
        with self.assertRaises(ValueError): ci.check_reports(self.manifest, 'fast', self.root / 'reports')


if __name__ == '__main__':
    unittest.main()
