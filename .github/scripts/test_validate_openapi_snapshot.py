#!/usr/bin/env python3
"""Exercise valid generated contracts and meaningful snapshot/runtime failure cases."""
import copy
import hashlib
import json
import sys
import tempfile
import unittest
from pathlib import Path
sys.dont_write_bytecode = True
import validate_openapi_snapshot as validator

SOURCE = 'a' * 40
RUNTIME = 'b' * 40


def fixture():
    return {'openapi': '3.1.0', 'info': {'title': 'Fixture', 'version': 'v1'},
            'x-hidra-ci-source-sha': SOURCE,
            'paths': {'/api/v1/workbench/modules': {'get': {'security': [{'hidraBearerJwt': []}]}},
                      '/api/v1/identity/authentication/oidc/complete': {'post': {'security': [{'externalOidcBearerJwt': []}]}},
                      '/api/v1/identity/authentication/login': {'post': {'security': []}}},
            'components': {'schemas': {'Answer': {'type': 'object', 'description': 'Original',
                                                'properties': {'n': {'type': 'number', 'minimum': 1}}}},
                           'securitySchemes': {name: {'type': 'http', 'scheme': 'bearer', 'bearerFormat': 'JWT'}
                                               for name in ('hidraBearerJwt', 'externalOidcBearerJwt')}}}


class SnapshotTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.path = self.root / 'snapshot.yaml'
        self.document = fixture()
        self.path.write_bytes(validator.canonical_bytes(self.document))
        self.manifest = {'openapi': {'path': 'snapshot.yaml', 'source_sha': SOURCE,
                         'sha256': hashlib.sha256(self.path.read_bytes()).hexdigest(),
                         'counts': {'paths': 3, 'operations': 3, 'schemas': 1},
                         'provenance': {'source_sha': SOURCE, 'conclusion': 'success', 'run_id': 1,
                                        'artifact_id': 2, 'zip_sha256': 'c' * 64, 'json_sha256': 'd' * 64}}}

    def test_valid_snapshot_and_different_runtime_sha(self):
        snapshot, counts = validator.validate_snapshot(self.root, self.manifest)
        generated = copy.deepcopy(snapshot)
        generated['x-hidra-ci-source-sha'] = RUNTIME
        validator.compare_generated(snapshot, generated, RUNTIME)
        self.assertEqual(counts['operations'], 3)

    def test_object_key_order_is_not_semantic(self):
        generated = dict(reversed(list(self.document.items())))
        generated['x-hidra-ci-source-sha'] = RUNTIME
        validator.compare_generated(self.document, generated, RUNTIME)

    def test_real_schema_description_and_type_changes_are_not_ignored(self):
        for key, value in [('description', 'Changed'), ('type', 'string')]:
            generated = copy.deepcopy(self.document)
            generated['x-hidra-ci-source-sha'] = RUNTIME
            generated['components']['schemas']['Answer'][key] = value
            with self.subTest(key=key), self.assertRaisesRegex(ValueError, 'differs'):
                validator.compare_generated(self.document, generated, RUNTIME)

    def test_array_order_and_boolean_number_difference_are_significant(self):
        for value in (True, 2):
            generated = copy.deepcopy(self.document)
            generated['x-hidra-ci-source-sha'] = RUNTIME
            generated['components']['schemas']['Answer']['properties']['n']['minimum'] = value
            with self.subTest(value=value), self.assertRaisesRegex(ValueError, 'differs'):
                validator.compare_generated(self.document, generated, RUNTIME)
        before = copy.deepcopy(self.document)
        before['components']['schemas']['Answer']['enum'] = ['one', 'two']
        after = copy.deepcopy(before)
        after['components']['schemas']['Answer']['enum'].reverse()
        with self.assertRaisesRegex(ValueError, 'differs'):
            validator.compare_generated(before, after, SOURCE)

    def test_wrong_or_invalid_source_sha(self):
        for sha in ('C' * 40, 'abc', RUNTIME):
            with self.subTest(sha=sha), self.assertRaises(ValueError):
                validator.validate_document(self.document, sha)

    def test_duplicate_keys_nonfinite_and_malformed_json(self):
        for payload in ('{"a":1,"a":2}', '{"a":NaN}', '{"a":Infinity}', '{'):
            self.path.write_text(payload)
            with self.subTest(payload=payload), self.assertRaises(ValueError):
                validator.load_json(self.path)

    def test_noncanonical_serialization_digest_and_counts(self):
        self.path.write_text(json.dumps(self.document, indent=2))
        with self.assertRaisesRegex(ValueError, 'sorted compact'):
            validator.validate_snapshot(self.root, self.manifest)
        self.path.write_bytes(validator.canonical_bytes(self.document))
        self.manifest['openapi']['sha256'] = '0' * 64
        with self.assertRaisesRegex(ValueError, 'digest'):
            validator.validate_snapshot(self.root, self.manifest)
        self.manifest['openapi']['sha256'] = hashlib.sha256(self.path.read_bytes()).hexdigest()
        self.manifest['openapi']['counts']['paths'] = 4
        with self.assertRaisesRegex(ValueError, 'counts'):
            validator.validate_snapshot(self.root, self.manifest)

    def test_invalid_structure_security_and_public_login(self):
        mutations = [lambda d: d.update(openapi='3.0.0'),
                     lambda d: d.update(paths=[]),
                     lambda d: d['components']['securitySchemes']['hidraBearerJwt'].update(scheme='basic'),
                     lambda d: d['paths']['/api/v1/identity/authentication/login']['post'].update(security=[{'hidraBearerJwt': []}]),
                     lambda d: d.update(security=[{'unknown': []}])]
        for change in mutations:
            candidate = copy.deepcopy(self.document)
            change(candidate)
            with self.subTest(change=change), self.assertRaises(ValueError):
                validator.validate_document(candidate, SOURCE)

    def test_local_escaped_pointer_and_dangling_external_refs(self):
        self.document['components']['schemas']['a/b~c'] = {'type': 'string'}
        self.assertEqual(validator.resolve_ref(self.document, '#/components/schemas/a~1b~0c'), {'type': 'string'})
        for ref in ('#/components/schemas/missing', 'https://example.test/schema', '#/bad~9pointer'):
            candidate = copy.deepcopy(self.document)
            candidate['components']['schemas']['Answer']['$ref'] = ref
            with self.subTest(ref=ref), self.assertRaises(ValueError):
                validator.validate_document(candidate, SOURCE)

    def test_generation_provenance_is_required(self):
        self.manifest['openapi']['provenance']['conclusion'] = 'failure'
        with self.assertRaisesRegex(ValueError, 'provenance'):
            validator.validate_snapshot(self.root, self.manifest)


if __name__ == '__main__':
    unittest.main()
