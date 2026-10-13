#!/usr/bin/env python3
"""Selection failures must not turn into silently reduced verification."""
import copy
import json
from pathlib import Path
import tempfile
import unittest

import verify_ci_test_inventory as ci


def source(root, name, text):
    path = root / 'src/test/java/example' / (name + '.java')
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text('package example;\n' + text)
    return path


def fixture(root):
    source(root, 'UnitTest', 'class UnitTest { @Test void provesBehavior() {} }\n')
    source(root, 'FirstDatabaseTest', 'import org.testcontainers.containers.PostgreSQLContainer;\nclass FirstDatabaseTest {}\n')
    source(root, 'SecondDatabaseTest', 'import org.testcontainers.containers.PostgreSQLContainer;\nclass SecondDatabaseTest {}\n')
    classes = []
    for name, item in ci.discover(root).items():
        unit = name.endswith('UnitTest')
        classes.append({'class': name, 'path': item['path'], 'source_sha256': item['sha256'],
                        'owner': 'example', 'lane': 'unit-architecture' if unit else 'postgres-integration',
                        'fast': unit, 'dependency_reason': 'Reviewed runtime dependency', 'supports': [],
                        'cases': ['provesBehavior'], 'baseline_seconds': 1.0})
    return {'format': 1, 'mode': 'shadow-fallback-required', 'classes': classes, 'supporting_classes': []}


def report(directory, name='example.UnitTest', case='provesBehavior', outcome='', declared=None):
    directory.mkdir(parents=True, exist_ok=True)
    counts = dict(tests=1, failures=int(outcome == 'failure'), errors=int(outcome == 'error'), skipped=int(outcome == 'skipped'))
    counts.update(declared or {})
    attributes = ' '.join(f'{key}="{value}"' for key, value in counts.items())
    path = directory / ('TEST-' + name + '.xml')
    path.write_text(f'<testsuite name="{name}" time="1.2" {attributes}>'
                    f'<testcase classname="{name}" name="{case}">{"<" + outcome + "/>" if outcome else ""}</testcase></testsuite>')
    return path


class InventoryTest(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.manifest = fixture(self.root)

    def test_complete_disjoint_selection(self):
        ci.validate(self.root, self.manifest)
        lanes = ci.partitions(self.manifest)
        self.assertEqual(len(lanes['oracle']), 3)
        self.assertEqual(set(lanes['postgres-0']) & set(lanes['postgres-1']), set())
        self.assertEqual(lanes, ci.partitions(copy.deepcopy(self.manifest)))

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
        selected = next(e for e in self.manifest['classes'] if e['lane'] == 'postgres-integration')
        selected['archived_for_greenfield'] = True
        lanes = ci.partitions(self.manifest)
        self.assertEqual(lanes['historical'], [selected['class']])
        self.assertNotIn(selected['class'], lanes['oracle'])
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
