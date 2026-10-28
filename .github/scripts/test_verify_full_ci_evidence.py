#!/usr/bin/env python3
"""Negative policy fixtures for aggregate and release acceptance."""
import copy
import io
import json
import os
import shutil
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


class CachePreparationTest(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(); self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.environment = self.root / 'github-env'
        self.env = {'RUNNER_TEMP': str(self.root), 'GITHUB_ENV': str(self.environment),
                    'GITHUB_SHA': SHA, 'GITHUB_RUN_ID': '5', 'GITHUB_RUN_ATTEMPT': '2',
                    'GITHUB_JOB': 'postgres-integration', 'HIDRA_CACHE_HIT': ''}

    def test_cold_is_empty_and_selected_outside_clean_target(self):
        with patch.dict(os.environ, self.env, clear=True): cache = full.prepare_cache('cold')
        self.assertEqual(cache['initial_entries'], 0)
        self.assertFalse(cache['restore_enabled'])
        repository = Path(cache['repository'])
        self.assertEqual(repository.parent, self.root)
        self.assertEqual(list(repository.iterdir()), [])
        self.assertIn('-Dmaven.repo.local=' + str(repository), self.environment.read_text())
        self.assertIn('HIDRA_MAVEN_CACHE_EVIDENCE=', self.environment.read_text())
        full.verify_cache({'dependency_cache': cache, 'source_sha': SHA, 'run_id': 5,
                          'run_attempt': 2, 'job': 'postgres-integration'}, 'cold')
        (repository / 'cached.pom').write_text('synthetic dependency')
        with patch.dict(os.environ, self.env, clear=True), self.assertRaises(FileExistsError): full.prepare_cache('cold')

    def test_existing_even_empty_repository_denies_reuse(self):
        (self.root / 'hidra-cold-maven-repository').mkdir()
        with patch.dict(os.environ, self.env, clear=True), self.assertRaises(FileExistsError): full.prepare_cache('cold')

    def test_restore_hit_or_conflicting_override_cannot_claim_cold(self):
        for update in ({'HIDRA_CACHE_HIT': 'true'}, {'MAVEN_OPTS': '-Dmaven.repo.local=/elsewhere'}):
            with self.subTest(update=update), patch.dict(os.environ, dict(self.env, **update), clear=True), self.assertRaises(ValueError):
                full.prepare_cache('cold')

    def test_cached_records_observed_miss_without_claiming_warmth(self):
        home = self.root / 'home'
        with patch.dict(os.environ, dict(self.env, HIDRA_CACHE_HIT='false'), clear=True), patch.object(Path, 'home', return_value=home):
            cache = full.prepare_cache('cached')
        self.assertFalse(cache['cache_hit'])
        self.assertTrue(cache['restore_enabled'])
        self.assertEqual(cache['initial_entries'], 0)
        self.assertFalse((home / '.m2/repository').exists())


class FullEvidenceTest(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name) / 'source'
        self.root.mkdir()
        self.manifest = fixture(self.root, postgres_shards=10)
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
            job = {'oracle': 'verify', 'postgres-0': 'postgres-integration', 'postgres-1': 'postgres-integration', 'postgres-2': 'postgres-integration', 'postgres-3': 'postgres-integration', 'postgres-4': 'postgres-integration', 'postgres-5': 'postgres-integration', 'postgres-6': 'postgres-integration', 'postgres-7': 'postgres-integration', 'postgres-8': 'postgres-integration', 'postgres-9': 'postgres-integration'}.get(lane, lane)
            evidence = {'format': 1, 'source_sha': SHA, 'run_id': 5, 'run_attempt': 2,
                        'lane': lane, 'job': job, 'inventory_sha256': self.fingerprint,
                        'classifier_sha256': inventory.digest(script.read_bytes()), 'files': {}}
            if lane != 'static-validation':
                evidence['dependency_cache'] = {'mode': 'cached', 'restore_enabled': True,
                    'cache_hit': True, 'initial_entries': 1, 'repository': '/home/runner/.m2/repository',
                    'runner_temp': '/runner/temp', 'source_sha': SHA, 'run_id': 5, 'run_attempt': 2, 'job': job}
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

    def development(self):
        return full.aggregate(self.root, self.directory, SHA, 5, 2, self.needs, mode='development')

    def prepare_development(self):
        shutil.rmtree(self.directory / 'oracle')
        self.needs['verify']['result'] = 'skipped'

    def test_partial_rerun_recovers_same_sha_successful_old_lane_only(self):
        self.prepare_development()
        static = self.directory / 'static-validation/evidence.json'
        receipt = json.loads(static.read_text())
        receipt['run_attempt'] = 1
        static.write_bytes(inventory.canonical(receipt))
        job = {'name': 'Static validation', 'head_sha': SHA, 'run_attempt': 1,
               'workflow_name': 'HidraAPI CI', 'status': 'completed', 'conclusion': 'success'}
        with patch.object(full, 'pages', return_value=[job]) as api:
            result = self.development()
        self.assertEqual(result['lane_run_attempts']['static-validation'], 1)
        self.assertEqual(result['lane_run_attempts']['postgres-3'], 2)
        self.assertTrue(all(value == 2 for lane, value in result['lane_run_attempts'].items()
                            if lane != 'static-validation'))
        api.assert_called_once_with('actions/runs/5/attempts/1/jobs', 'jobs')

    def test_new_shards_require_exact_earlier_job_attestation(self):
        for index in (7, 8, 9):
            job = {'name': f'PostgreSQL integration shard {index}', 'head_sha': SHA,
                   'run_attempt': 1, 'workflow_name': 'HidraAPI CI',
                   'status': 'completed', 'conclusion': 'success'}
            with self.subTest(index=index):
                with patch.object(full, 'pages', return_value=[job]):
                    full.verify_earlier_lane_job(f'postgres-{index}', SHA, 5, 1)
                for mutation in ({'head_sha': 'f' * 40}, {'conclusion': 'failure'}, {'run_attempt': 2}):
                    with patch.object(full, 'pages', return_value=[dict(job, **mutation)]):
                        with self.assertRaises(ValueError):
                            full.verify_earlier_lane_job(f'postgres-{index}', SHA, 5, 1)

    def test_partial_rerun_fails_closed_without_positive_exact_job_evidence(self):
        self.prepare_development()
        receipt = self.directory / 'postgres-1/evidence.json'
        old = json.loads(receipt.read_text()); old['run_attempt'] = 1
        old['dependency_cache']['run_attempt'] = 1
        receipt.write_bytes(inventory.canonical(old))
        valid = {'name': 'PostgreSQL integration shard 1', 'head_sha': SHA,
                 'run_attempt': 1, 'workflow_name': 'HidraAPI CI',
                 'status': 'completed', 'conclusion': 'success'}
        for invalid in ({'conclusion': 'failure'}, {'status': 'queued'},
                        {'run_attempt': 2}, {'head_sha': 'b' * 40},
                        {'name': 'PostgreSQL integration shard 0'},
                        {'workflow_name': 'other'}):
            job = dict(valid, **invalid)
            with self.subTest(invalid=invalid), patch.object(full, 'pages', return_value=[job]):
                with self.assertRaises(ValueError):
                    self.development()
        with patch.object(full, 'pages', return_value=[valid, valid]):
            with self.assertRaises(ValueError): self.development()
        with patch.object(full, 'pages', side_effect=ValueError('GitHub API unavailable')):
            with self.assertRaises(ValueError): self.development()

    def test_partial_rerun_never_uses_forged_or_conflicting_receipts(self):
        self.prepare_development()
        receipt = self.directory / 'unit-architecture/evidence.json'
        original = json.loads(receipt.read_text())
        stale = copy.deepcopy(original); stale['run_attempt'] = 1
        stale['dependency_cache']['run_attempt'] = 1
        prior = self.directory / 'older-unit'; prior.mkdir()
        shutil.copytree(self.directory / 'unit-architecture/reports', prior / 'reports')
        (prior / 'evidence.json').write_bytes(inventory.canonical(stale))
        jobs = [{'name': 'Java 21 unit and architecture', 'head_sha': SHA, 'run_attempt': 1,
                 'workflow_name': 'HidraAPI CI', 'status': 'completed', 'conclusion': 'success'}]
        # A valid latest attempt dominates a valid previous success.
        result = self.development()
        self.assertEqual(result['lane_run_attempts']['unit-architecture'], 2)
        # Duplicates within the same attempt are never accepted.
        stale['run_attempt'] = 2; stale['dependency_cache']['run_attempt'] = 2
        (prior / 'evidence.json').write_bytes(inventory.canonical(stale))
        with self.assertRaises(ValueError): self.development()
        stale['run_attempt'] = 1; stale['dependency_cache']['run_attempt'] = 1
        (prior / 'evidence.json').write_bytes(inventory.canonical(stale))
        original['run_attempt'] = 3
        receipt.write_bytes(inventory.canonical(original))
        with self.assertRaises(ValueError): self.development()
        receipt.write_bytes(inventory.canonical(original | {'run_attempt': 2}))

    def test_audit_rejects_older_attempt_even_with_successful_job_receipt(self):
        receipt = self.directory / 'static-validation/evidence.json'
        doc = json.loads(receipt.read_text()); doc['run_attempt'] = 1
        receipt.write_bytes(inventory.canonical(doc))
        verified = {'name': 'Static validation', 'head_sha': SHA, 'run_attempt': 1,
                    'workflow_name': 'HidraAPI CI', 'status': 'completed', 'conclusion': 'success'}
        with patch.object(full, 'pages', return_value=[verified]):
            with self.assertRaises(ValueError):
                self.aggregate()

    def test_partial_rerun_cannot_bypass_audit_or_failed_dependency(self):
        self.prepare_development()
        receipt = self.directory / 'static-validation/evidence.json'
        original = json.loads(receipt.read_text())
        earlier = dict(original, run_attempt=1)
        receipt.write_bytes(inventory.canonical(earlier))
        self.needs['postgres-integration']['result'] = 'failure'
        with self.assertRaises(ValueError): self.development()
        self.needs['postgres-integration']['result'] = 'success'
        self.needs['verify']['result'] = 'success'
        with self.assertRaises(ValueError): self.aggregate()  # audit is single-attempt strict


    def test_development_requires_all_ten_partitions_and_denies_release_equivalence(self):
        self.prepare_development()
        result = self.development()
        self.assertEqual(result['mode'], 'development-partitioned')
        self.assertFalse(result['same_run_equivalence'])
        self.assertFalse(result['release_eligible'])
        self.assertFalse(result['fallback_required'])
        self.assertTrue(result['fallback_available'])
        self.assertFalse(result['optimized_adoption_eligible'])
        self.assertEqual(result['classes'], 11)
        self.assertEqual(result['counts'], dict(tests=11, failures=0, errors=0, skipped=0))
        self.assertEqual(set(result['lane_evidence']), set(full.REQUIRED_LANES) - {'oracle'})
        with self.assertRaises(ValueError): self.aggregate()  # audit is still the default

    def test_development_does_not_hide_failed_or_cancelled_oracle(self):
        self.prepare_development()
        for state in ('failure', 'cancelled', 'timed_out', 'success', 'in_progress'):
            self.needs['verify']['result'] = state
            with self.subTest(state=state), self.assertRaises(ValueError): self.development()

    def test_development_required_jobs_cannot_be_skipped(self):
        self.prepare_development()
        for job in set(full.REQUIRED_NEEDS) - {'verify'}:
            self.needs[job]['result'] = 'skipped'
            with self.subTest(job=job), self.assertRaises(ValueError): self.development()
            self.needs[job]['result'] = 'success'

    def test_development_missing_fifth_shard_and_corrupt_report_deny(self):
        self.prepare_development()
        path = self.directory / 'postgres-4/evidence.json'
        old = path.read_bytes(); path.unlink()
        with self.assertRaises(ValueError): self.development()
        path.write_bytes(old)
        next((self.directory / 'postgres-4/reports').glob('*.xml')).write_text('corrupt')
        with self.assertRaises((ValueError, inventory.ET.ParseError)): self.development()

    def test_development_missing_any_shard_denies(self):
        self.prepare_development()
        for index in range(10):
            with self.subTest(index=index):
                path = self.directory / f'postgres-{index}/evidence.json'
                original = path.read_bytes()
                path.unlink()
                with self.assertRaises(ValueError):
                    self.development()
                path.write_bytes(original)

    def test_development_extra_oracle_receipt_and_unknown_mode_deny(self):
        self.needs['verify']['result'] = 'skipped'
        with self.assertRaises(ValueError): self.development()
        with self.assertRaises(ValueError): full.aggregate(self.root, self.directory, SHA, 5, 2, self.needs, mode='green')

    def test_development_omission_overlap_and_skip_deny_even_with_fresh_hashes(self):
        self.prepare_development()
        for mutation in ('omit', 'overlap', 'skip'):
            with self.subTest(mutation=mutation):
                directory = self.directory / 'postgres-4'
                receipt = directory / 'evidence.json'; old_receipt = receipt.read_bytes()
                original = next((directory / 'reports').glob('*.xml')); old_report = original.read_bytes()
                if mutation == 'omit':
                    original.unlink(); changed = original
                elif mutation == 'overlap':
                    changed = report(directory / 'reports', name='example.UnitTest')
                else:
                    name = inventory.ET.fromstring(old_report).get('name')
                    changed = report(directory / 'reports', name=name, outcome='skipped')
                document = json.loads(old_receipt)
                if changed.exists(): document['files'][changed.relative_to(directory).as_posix()] = inventory.digest(changed.read_bytes())
                else: del document['files'][changed.relative_to(directory).as_posix()]
                receipt.write_bytes(inventory.canonical(document))
                with self.assertRaises(ValueError): self.development()
                if changed != original: changed.unlink()
                original.write_bytes(old_report); receipt.write_bytes(old_receipt)

    def test_release_jobs_reject_intentionally_skipped_development_oracle(self):
        jobs = [{'name': name, 'status': 'completed', 'conclusion': 'success'} for name in full.REQUIRED_JOB_NAMES]
        next(job for job in jobs if job['name'] == 'Java 21 Maven verification')['conclusion'] = 'skipped'
        with self.assertRaises(ValueError): full.verify_jobs(jobs)

    def cold_receipts(self):
        for lane in full.REQUIRED_LANES:
            if lane == 'static-validation': continue
            path = self.directory / lane / 'evidence.json'
            evidence = json.loads(path.read_text())
            cache = evidence['dependency_cache']
            cache.update(mode='cold', restore_enabled=False, cache_hit=None, initial_entries=0,
                         repository='/runner/temp/hidra-cold-maven-repository')
            self.change(lane, dependency_cache=cache)

    def test_cold_audit_requires_all_lanes_and_keeps_independent_equivalence(self):
        self.cold_receipts()
        result = full.aggregate(self.root, self.directory, SHA, 5, 2, self.needs, dependency_cache='cold')
        self.assertTrue(result['same_run_equivalence'])
        self.assertEqual(result['dependency_cache_mode'], 'cold')
        self.assertEqual(len(result['dependency_cache']), 13)
        with self.assertRaises(ValueError): self.aggregate()

    def test_missing_or_mixed_cache_evidence_denies(self):
        self.change('postgres-4', dependency_cache={})
        with self.assertRaises(ValueError): self.aggregate()
        self.change('postgres-4', dependency_cache={'mode': 'cold'})
        with self.assertRaises(ValueError): self.aggregate()

    def test_nonempty_restored_wrong_path_and_stale_cold_receipts_deny(self):
        self.cold_receipts()
        path = self.directory / 'oracle/evidence.json'
        good = json.loads(path.read_text())['dependency_cache']
        for field, value in [('initial_entries', 1), ('initial_entries', True), ('cache_hit', True),
                             ('restore_enabled', True), ('repository', '/home/runner/.m2/repository'),
                             ('run_attempt', 1), ('source_sha', 'b' * 40), ('job', 'unit-architecture')]:
            with self.subTest(field=field, value=value):
                self.change('oracle', dependency_cache=dict(good, **{field: value}))
                with self.assertRaises(ValueError): full.aggregate(self.root, self.directory, SHA, 5, 2, self.needs, dependency_cache='cold')
        self.change('oracle', dependency_cache=good)

    def test_cache_miss_is_not_misrepresented_as_warm(self):
        path = self.directory / 'postgres-0/evidence.json'
        cache = json.loads(path.read_text())['dependency_cache']
        cache.update(cache_hit=False, initial_entries=0)
        self.change('postgres-0', dependency_cache=cache)
        result = self.aggregate()
        self.assertFalse(result['dependency_cache']['postgres-0']['cache_hit'])
        with self.assertRaises(ValueError): full.aggregate(self.root, self.directory, SHA, 5, 2, self.needs, dependency_cache='unknown')

    def test_parallel_audit_has_full_coverage_without_claiming_equivalence_or_release(self):
        self.prepare_development()  # remove oracle and require its job to be intentionally skipped
        result = full.aggregate(self.root, self.directory, SHA, 5, 2, self.needs,
                                mode='audit', serial_oracle=False)
        self.assertEqual(result['mode'], 'audit-partitioned')
        self.assertEqual(result['verification_mode'], 'audit')
        self.assertFalse(result['serial_oracle'])
        self.assertFalse(result['same_run_equivalence'])
        self.assertFalse(result['release_eligible'])
        self.assertEqual(result['classes'], 11)
        self.assertEqual(result['counts']['tests'], 11)
        with self.assertRaises(ValueError): self.aggregate()  # default internal/release audit remains strict

    def test_parallel_cold_audit_preserves_all_cache_checks(self):
        self.cold_receipts(); self.prepare_development()
        result = full.aggregate(self.root, self.directory, SHA, 5, 2, self.needs,
                                mode='audit', dependency_cache='cold', serial_oracle=False)
        self.assertEqual(result['dependency_cache_mode'], 'cold')
        self.assertEqual(len(result['dependency_cache']), 12)
        self.change('postgres-4', dependency_cache={})
        with self.assertRaises(ValueError): full.aggregate(self.root, self.directory, SHA, 5, 2,
                                                          self.needs, mode='audit', dependency_cache='cold', serial_oracle=False)

    def test_parallel_audit_rejects_unexpected_oracle_and_hidden_failure(self):
        with self.assertRaises(ValueError): full.aggregate(self.root, self.directory, SHA, 5, 2,
                                                          self.needs, mode='audit', serial_oracle=False)
        self.prepare_development()
        self.needs['verify']['result'] = 'failure'
        with self.assertRaises(ValueError): full.aggregate(self.root, self.directory, SHA, 5, 2,
                                                          self.needs, mode='audit', serial_oracle=False)

    def test_serial_oracle_opt_in_retains_complete_independent_comparison(self):
        result = full.aggregate(self.root, self.directory, SHA, 5, 2, self.needs, mode='audit', serial_oracle=True)
        self.assertTrue(result['same_run_equivalence'])
        self.assertTrue(result['serial_oracle'])
        self.assertTrue(result['release_eligible'])
        for mode, value in [('development', True), ('audit', 'false')]:
            with self.subTest(mode=mode, value=value), self.assertRaises(ValueError):
                full.aggregate(self.root, self.directory, SHA, 5, 2, self.needs, mode=mode, serial_oracle=value)

    def test_aggregate_cli_requires_explicit_serial_flag(self):
        env = {'GITHUB_SHA': SHA, 'GITHUB_RUN_ID': '5', 'GITHUB_RUN_ATTEMPT': '2',
               'HIDRA_CI_NEEDS': json.dumps(self.needs)}
        for flags in ([], ['--serial-oracle']):
            args = ['evidence', '--root', str(self.root), 'aggregate', '--mode', 'audit',
                    '--directory', str(self.directory)] + flags
            with self.subTest(flags=flags), patch('sys.argv', args), patch.dict(os.environ, env), \
                    patch.object(full, 'aggregate', return_value={'fixture': True}) as call, \
                    patch('sys.stdout', new_callable=io.StringIO):
                self.assertEqual(full.main(), 0)
                self.assertIs(call.call_args.args[-1], bool(flags))

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

    def test_unchecked_dictionary_receipt_rejects_and_checked_receipt_still_validates_catalog(self):
        self.api_patch.stop()
        directory = self.directory / 'api-contract'
        files = directory / 'files'
        current = files / 'target/openapi/hidra-api.json'
        current.parent.mkdir(parents=True, exist_ok=True)
        current.write_text(json.dumps({'x-hidra-ci-source-sha': SHA}))
        database = files / 'target/database'
        database.mkdir(parents=True, exist_ok=True)
        (database / 'schema.json').write_text(json.dumps({'source_sha': SHA}))
        receipt = database / 'verification.json'
        evidence = {'files': {'files/' + name: 'f' * 64 for name in full.API_FILES}}
        receipt.write_text(json.dumps({'source_sha': SHA, 'reviewed_dictionary_checked': False}))
        with patch.object(full.dictionary, 'validate_document') as validate:
            with self.assertRaisesRegex(ValueError, 'Stale or unchecked dictionary'):
                full.api_facts(evidence, directory, self.root, SHA)
            validate.assert_not_called()
        receipt.write_text(json.dumps({'source_sha': SHA, 'reviewed_dictionary_checked': True}))
        with patch.object(full.dictionary, 'validate_document', side_effect=ValueError('catalog validation still mandatory')) as validate:
            with self.assertRaisesRegex(ValueError, 'catalog validation still mandatory'):
                full.api_facts(evidence, directory, self.root, SHA)
            validate.assert_called_once()

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
