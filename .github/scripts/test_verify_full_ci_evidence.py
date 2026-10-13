#!/usr/bin/env python3
"""Negative policy fixtures for aggregate and release acceptance."""
import copy
import io
import json
from pathlib import Path
import tempfile
import unittest
import warnings
from unittest.mock import patch
import zipfile

import verify_ci_test_inventory as inventory
import verify_full_ci_evidence as full
from test_verify_ci_test_inventory import fixture, report

SHA = 'a' * 40


class FullEvidenceTest(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name) / 'source'
        self.root.mkdir()
        self.manifest = fixture(self.root)
        (self.root / 'pom.xml').write_text('<project/>')
        script = self.root / '.github/scripts/verify_ci_test_inventory.py'
        script.parent.mkdir(parents=True)
        script.write_text('unit fixture classifier')
        (self.root / '.github/workflows').mkdir()
        (self.root / '.github/workflows/ci.yml').write_text('unit fixture workflow')
        path = self.root / '.github/ci-test-inventory.json'
        path.write_bytes(inventory.canonical(self.manifest))
        self.fingerprint = inventory.digest(path.read_bytes())
        self.directory = Path(self.temp.name) / 'evidence'
        self.directory.mkdir()
        self.needs = {key: {'result': 'success'} for key in full.REQUIRED_NEEDS}
        self.run = {'id': 5, 'run_number': 10, 'run_attempt': 2, 'name': 'HidraAPI CI',
                    'path': '.github/workflows/ci.yml', 'head_sha': SHA, 'head_branch': 'main',
                    'event': 'push', 'status': 'completed', 'conclusion': 'success'}
        for lane in full.REQUIRED_LANES:
            lane_root = self.directory / lane
            lane_root.mkdir()
            job = {'oracle': 'verify', 'postgres-0': 'postgres-integration', 'postgres-1': 'postgres-integration'}.get(lane, lane)
            evidence = {'format': 1, 'source_sha': SHA, 'run_id': 5, 'run_attempt': 2,
                        'lane': lane, 'job': job, 'inventory_sha256': self.fingerprint,
                        'classifier_sha256': inventory.digest(script.read_bytes()), 'files': {}}
            if lane in inventory.LANES:
                for name in inventory.partitions(self.manifest)[lane]: report(lane_root / 'reports', name=name)
                evidence.update(inventory.check_reports(self.manifest, lane, lane_root / 'reports'))
                evidence['lifecycle'] = {'phase':'clean verify', 'java_version':'openjdk version "21.0.1"',
                                         'pom_sha256': inventory.digest((self.root / 'pom.xml').read_bytes()),
                                         'jar_sha256': 'd' * 64}
                for path in (lane_root / 'reports').glob('*.xml'):
                    evidence['files'][path.relative_to(lane_root).as_posix()] = inventory.digest(path.read_bytes())
            else:
                (lane_root / 'marker').write_text('fixture')
                evidence['files']['marker'] = inventory.digest(b'fixture')
            if lane == 'static-validation':
                evidence['checks'] = list(full.STATIC_CHECKS)
                target = lane_root / 'files/ci.yml'; target.parent.mkdir()
                target.write_bytes((self.root / '.github/workflows/ci.yml').read_bytes())
                evidence['files']['files/ci.yml'] = inventory.digest(target.read_bytes())
            (lane_root / 'evidence.json').write_bytes(inventory.canonical(evidence))
        self.api_patch = patch.object(full, 'api_facts', return_value={'catalog_sha256': 'verified-unit-fixture'})
        self.api_patch.start()
        self.addCleanup(self.api_patch.stop)

    def aggregate(self):
        return full.aggregate(self.root, self.directory, SHA, 5, 2, self.needs)

    def change(self, lane, **updates):
        path = self.directory / lane / 'evidence.json'
        document = json.loads(path.read_text()); document.update(updates)
        path.write_bytes(inventory.canonical(document))

    def test_same_run_equivalence_keeps_fallback_and_denies_adoption(self):
        result = self.aggregate()
        self.assertTrue(result['same_run_equivalence'])
        self.assertTrue(result['fallback_required'])
        self.assertFalse(result['optimized_adoption_eligible'])

    def test_sha_attempt_classifier_and_job_mismatch_deny(self):
        for field, value in [('source_sha', 'b' * 40), ('run_id', 6), ('run_attempt', 1),
                             ('inventory_sha256', 'c' * 64), ('job', 'fast')]:
            with self.subTest(field=field):
                path = self.directory / 'unit-architecture/evidence.json'; old = path.read_bytes()
                self.change('unit-architecture', **{field: value})
                with self.assertRaises(ValueError): self.aggregate()
                path.write_bytes(old)

    def test_failed_skipped_cancelled_and_missing_jobs_deny(self):
        for result in ('failure', 'skipped', 'cancelled', 'timed_out'):
            self.needs['verify']['result'] = result
            with self.assertRaises(ValueError): self.aggregate()
        del self.needs['verify']
        with self.assertRaises(ValueError): self.aggregate()

    def test_missing_receipt_denies(self):
        (self.directory / 'postgres-0/evidence.json').unlink()
        with self.assertRaises(ValueError): self.aggregate()

    def test_wrong_helper_revision_or_verify_lifecycle_denies(self):
        self.change('unit-architecture', classifier_sha256='e' * 64)
        with self.assertRaises(ValueError): self.aggregate()
        self.change('unit-architecture', classifier_sha256=inventory.digest((self.root / '.github/scripts/verify_ci_test_inventory.py').read_bytes()), lifecycle={'phase': 'test'})
        with self.assertRaises(ValueError): self.aggregate()

    def test_duplicate_or_fast_receipt_denies(self):
        extra = self.directory / 'extra'; extra.mkdir()
        original = (self.directory / 'unit-architecture/evidence.json').read_bytes()
        (extra / 'evidence.json').write_bytes(original)
        with self.assertRaises(ValueError): self.aggregate()
        document = json.loads(original); document['lane'] = 'fast'
        (extra / 'evidence.json').write_bytes(inventory.canonical(document))
        with self.assertRaises(ValueError): self.aggregate()

    def test_stale_or_corrupt_report_denies(self):
        path = next((self.directory / 'oracle/reports').glob('*.xml'))
        path.write_text(path.read_text() + 'corruption')
        with self.assertRaises(ValueError): self.aggregate()

    def test_overlapping_class_or_omitted_case_denies_even_with_fresh_hash(self):
        path = report(self.directory / 'postgres-0/reports', name='example.UnitTest')
        receipt = self.directory / 'postgres-0/evidence.json'
        document = json.loads(receipt.read_text())
        document['files'][path.relative_to(receipt.parent).as_posix()] = inventory.digest(path.read_bytes())
        receipt.write_bytes(inventory.canonical(document))
        with self.assertRaises(ValueError): self.aggregate()

    def test_skip_cannot_be_hidden_by_updated_artifact_hash(self):
        path = report(self.directory / 'unit-architecture/reports', outcome='skipped')
        receipt = self.directory / 'unit-architecture/evidence.json'
        document = json.loads(receipt.read_text())
        document['files'][path.relative_to(receipt.parent).as_posix()] = inventory.digest(path.read_bytes())
        receipt.write_bytes(inventory.canonical(document))
        with self.assertRaises(ValueError): self.aggregate()

    def test_missing_raw_report_denies(self):
        next((self.directory / 'postgres-0/reports').glob('*.xml')).unlink()
        with self.assertRaises(ValueError): self.aggregate()

    def test_forged_report_payload_denies(self):
        self.change('unit-architecture', counts={'tests': 9000})
        with self.assertRaises(ValueError): self.aggregate()

    def test_required_static_check_missing_denies(self):
        self.change('static-validation', checks=list(full.STATIC_CHECKS[:-1]))
        with self.assertRaises(ValueError): self.aggregate()

    def test_runtime_provenance_error_cannot_be_waived(self):
        with patch.object(full, 'api_facts', side_effect=ValueError('stale catalog')):
            with self.assertRaises(ValueError): self.aggregate()

    def test_oracle_api_and_partition_catalog_mismatch_deny(self):
        with patch.object(full, 'api_facts', side_effect=[{'catalog_sha256': 'a'}, {'catalog_sha256': 'b'}]):
            with self.assertRaises(ValueError): self.aggregate()

    def test_newest_failed_run_cannot_fall_back_to_older_success(self):
        later = dict(self.run, id=6, run_number=11, conclusion='failure')
        with self.assertRaises(ValueError): full.select_run([self.run, later], SHA)

    def test_fast_parent_and_pr_merge_runs_cannot_qualify(self):
        for updates in ({'name': 'HidraAPI Fast Feedback'}, {'head_sha': 'b' * 40},
                        {'event': 'pull_request'}, {'path': '.github/workflows/docs.yml'}, {'head_branch': 'other'}):
            with self.subTest(updates=updates):
                with self.assertRaises(ValueError): full.select_run([dict(self.run, **updates)], SHA)

    def test_rerun_attempt_and_ambiguity_are_not_ignored(self):
        with self.assertRaises(ValueError): full.select_run([self.run, self.run], SHA)
        latest = dict(self.run, run_attempt=3, status='in_progress', conclusion=None)
        with self.assertRaises(ValueError): full.select_run([self.run, latest], SHA)

    def test_partial_same_named_future_run_denies(self):
        jobs = [{'name': 'Java 21 Maven verification', 'status': 'completed', 'conclusion': 'success'}]
        with self.assertRaises(ValueError): full.verify_jobs(jobs)
        full.verify_jobs(jobs, legacy=True)

    def test_full_job_cancellation_and_duplicates_deny(self):
        jobs = [{'name': name, 'status': 'completed', 'conclusion': 'success'} for name in full.REQUIRED_JOB_NAMES]
        full.verify_jobs(jobs)
        jobs[0]['conclusion'] = 'cancelled'
        with self.assertRaises(ValueError): full.verify_jobs(jobs)
        jobs[0]['conclusion'] = 'success'; jobs.append(copy.deepcopy(jobs[0]))
        with self.assertRaises(ValueError): full.verify_jobs(jobs)

    def test_artifact_paths_cannot_escape_or_link(self):
        for path in ('../outside', '/absolute', 'a\\b'):
            with self.assertRaises(ValueError): full.safe_path(self.directory, path)
        (self.directory / 'linked').symlink_to(self.root)
        with self.assertRaises(ValueError): full.safe_path(self.directory, 'linked/file')

    def test_zip_traversal_and_duplicate_entries_deny(self):
        for names in (['../outside'], ['same', 'same']):
            stream = io.BytesIO()
            with warnings.catch_warnings():
                warnings.simplefilter('ignore', UserWarning)
                with zipfile.ZipFile(stream, 'w') as zipped:
                    for name in names: zipped.writestr(name, 'fixture')
            with self.assertRaises(ValueError): full.extract(stream.getvalue(), self.directory)

    def test_incomplete_pagination_denies(self):
        with patch.object(full, 'api_get', return_value={'total_count': 2, 'jobs': [{'id': 1}]}):
            with self.assertRaises(ValueError): full.pages('actions/jobs', 'jobs')

    def test_absent_or_expired_artifact_digest_denies(self):
        for artifact in ({'expired': True, 'digest': 'sha256:' + 'a' * 64}, {'expired': False}):
            with self.assertRaises(ValueError): full.archive(artifact)

    def test_missing_api_payload_and_wrong_current_sha_deny(self):
        # Exercise the runtime provenance reader independently of aggregate mocks.
        self.api_patch.stop()
        directory = self.directory / 'api-contract'
        evidence = json.loads((directory / 'evidence.json').read_text())
        with self.assertRaises(ValueError): full.api_facts(evidence, directory, self.root, SHA)
        evidence['files'] = {'files/' + name: 'f' * 64 for name in full.API_FILES}
        current = directory / 'files/target/openapi/hidra-api.json'
        current.parent.mkdir(parents=True)
        current.write_text(json.dumps({'x-hidra-ci-source-sha': 'b' * 40}))
        with self.assertRaises(ValueError): full.api_facts(evidence, directory, self.root, SHA)

    def test_download_digest_mismatch_denies(self):
        from urllib.error import HTTPError
        error = HTTPError('https://api.github.com', 302, 'redirect',
                          {'Location': 'https://example.test/archive'}, None)
        opener = unittest.mock.Mock(); opener.open.side_effect = error
        response = io.BytesIO(b'wrong bytes')
        with patch.dict('os.environ', {'GITHUB_REPOSITORY': 'owner/repo', 'GH_TOKEN': 'synthetic'}), \
             patch.object(full, 'build_opener', return_value=opener), patch.object(full, 'urlopen', return_value=response):
            with self.assertRaises(ValueError): full.archive({'id': 1, 'digest': 'sha256:' + 'a' * 64, 'expired': False})


if __name__ == '__main__':
    unittest.main()
