#!/usr/bin/env python3
"""Deterministic FD02 HTTP-client safety checks; no Docker or credentials."""
import io
import json
import unittest
import urllib.error
from unittest.mock import patch

import fd02_native_http_checks as module


class FakeResponse:
    status = 200
    def __init__(self, value):
        self.value = value
    def __enter__(self): return self
    def __exit__(self, *ignored): return False
    def read(self, _: int) -> bytes: return json.dumps(self.value).encode()


class AuditFixtureContractTests(unittest.TestCase):
    """Static tripwires complement, never replace, hosted PostgreSQL ACL probes."""
    def test_synthetic_audit_ids_match_bootstrap_contract(self):
        from pathlib import Path
        script = Path(__file__).with_name('fd02_one_node_runtime.sh').read_text()
        self.assertIn("'IDENTITY_LOCAL_ADMIN_BOOTSTRAP', 'EVENT_TYPE'", script)
        self.assertIn("'SECURITY_ADMINISTRATION', 'EVENT_CATEGORY'", script)
        self.assertIn('AND active AND NOT system_defined', script)
        self.assertIn("bootstrap_audit_count", script)

    def test_audit_role_is_insert_only_with_restricted_membership(self):
        from pathlib import Path
        script = Path(__file__).with_name('fd02_one_node_runtime.sh').read_text()
        self.assertIn('CREATE ROLE fd02_audit_append_writer NOLOGIN', script)
        self.assertIn('GRANT INSERT ON TABLE public.hidra_audit_event', script)
        self.assertIn('WITH ADMIN FALSE, INHERIT TRUE, SET FALSE', script)
        self.assertIn('REVOKE UPDATE,DELETE,TRUNCATE', script)
        self.assertIn("denied-audit-update.log", script)
        self.assertIn("denied-audit-delete.log", script)
        self.assertIn("denied-audit-set-role.log", script)
        self.assertIn("denied-audit-trigger.log", script)
        self.assertIn("unset FD02_PG_ADMIN_PASSWORD FD02_MIGRATOR_PASSWORD", script)
        self.assertIn("privileged_migration_credentials_absent_from_jvm_environ", script)


class ClientTests(unittest.TestCase):
    def test_rejects_external_urls(self):
        for base in ('https://example.invalid', 'http://localhost:8080', 'http://127.0.0.1.evil:8080'):
            with self.assertRaises(module.QualificationFailure):
                module.Client(base)

    def test_json_status_and_redacted_request_records(self):
        secret = 'synthetic-not-for-output'
        client = module.Client('http://127.0.0.1:12345')
        with patch.object(module.urllib.request, 'urlopen', return_value=FakeResponse({'id': 'persisted'})):
            result = client.request('POST', '/api/v1/identity/authentication/login',
                                    payload={'credentials': secret}, token=secret)
        self.assertEqual(result, {'id': 'persisted'})
        evidence = json.dumps(client.events)
        self.assertNotIn(secret, evidence)
        self.assertNotIn('credentials', evidence)
        self.assertEqual(client.events[0]['status'], 200)

    def test_denial_bodies_are_not_emitted(self):
        client = module.Client('http://127.0.0.1:12345')
        with patch.object(module.urllib.request, 'urlopen', side_effect=urllib.error.HTTPError(
                'http://127.0.0.1:12345/denied', 403, 'secret body', {}, io.BytesIO(b'token leaked'))):
            self.assertIsNone(client.request('GET', '/denied', expected=403))
        self.assertEqual(client.events, [{'method': 'GET', 'path': '/denied', 'status': 403}])

    def test_unexpected_status_fails_closed(self):
        client = module.Client('http://127.0.0.1:12345')
        with patch.object(module.urllib.request, 'urlopen', side_effect=urllib.error.HTTPError(
                'http://127.0.0.1:12345/invalid', 500, 'internal', {}, io.BytesIO(b'private'))):
            with self.assertRaises(module.QualificationFailure) as context:
                client.request('GET', '/invalid')
        self.assertNotIn('private', str(context.exception))

    def test_json_response_shape_rejected(self):
        with self.assertRaises(module.QualificationFailure):
            module.require_shape({}, list, 'test')


if __name__ == '__main__':
    unittest.main()
