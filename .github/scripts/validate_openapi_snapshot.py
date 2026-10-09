#!/usr/bin/env python3
"""Validate the generated JSON snapshot subset and exact runtime semantic equality."""
import argparse
import hashlib
import json
import re
from pathlib import Path
from urllib.parse import unquote

SHA = re.compile(r'^[0-9a-f]{40}$')
METHODS = {'get', 'put', 'post', 'delete', 'options', 'head', 'patch', 'trace'}
DEFAULT_MANIFEST = '.github/documentation-validation.json'


def reject_constant(value):
    raise ValueError(f'Nonfinite JSON value: {value}')


def unique_object(pairs):
    result = {}
    for key, value in pairs:
        if key in result:
            raise ValueError(f'Duplicate JSON key: {key}')
        result[key] = value
    return result


def load_json(path):
    return json.loads(Path(path).read_text(encoding='utf-8'),
                      object_pairs_hook=unique_object, parse_constant=reject_constant)


def canonical_bytes(document):
    return (json.dumps(document, sort_keys=True, separators=(',', ':'),
                       allow_nan=False) + '\n').encode('utf-8')


def walk(value):
    yield value
    if isinstance(value, dict):
        for item in value.values():
            yield from walk(item)
    elif isinstance(value, list):
        for item in value:
            yield from walk(item)


def resolve_ref(document, ref):
    if not isinstance(ref, str) or not ref.startswith('#/'):
        raise ValueError(f'Only local JSON-pointer references are supported: {ref!r}')
    value = document
    for token in unquote(ref[2:]).split('/'):
        if re.search(r'~(?![01])', token):
            raise ValueError(f'Invalid JSON pointer: {ref}')
        token = token.replace('~1', '/').replace('~0', '~')
        try:
            if isinstance(value, list):
                if not re.fullmatch(r'0|[1-9][0-9]*', token):
                    raise ValueError('Invalid array index')
                value = value[int(token)]
            else:
                value = value[token]
        except (KeyError, IndexError, TypeError, ValueError) as error:
            raise ValueError(f'Unresolved reference: {ref}') from error
    return value


def validate_document(document, expected_sha):
    if not isinstance(expected_sha, str) or not SHA.fullmatch(expected_sha):
        raise ValueError('Expected source SHA must be 40 lowercase hex characters')
    if not isinstance(document, dict) or document.get('openapi') != '3.1.0':
        raise ValueError('Expected OpenAPI 3.1.0 JSON object')
    if document.get('x-hidra-ci-source-sha') != expected_sha:
        raise ValueError('Generated source SHA does not match expected context')
    if not isinstance(document.get('info'), dict):
        raise ValueError('Missing API info object')
    if document['info'].get('version') != 'v1' or not document['info'].get('title'):
        raise ValueError('Missing API title or v1 information version')
    paths = document.get('paths')
    components = document.get('components')
    if not isinstance(paths, dict) or not paths or not isinstance(components, dict):
        raise ValueError('Missing paths/components objects')
    schemas = components.get('schemas')
    if not isinstance(schemas, dict) or not schemas:
        raise ValueError('Missing component schemas')
    for path, item in paths.items():
        if not path.startswith('/') or not isinstance(item, dict):
            raise ValueError(f'Invalid path item: {path}')
        for method, operation in item.items():
            if method in METHODS and not isinstance(operation, dict):
                raise ValueError(f'Invalid operation: {method} {path}')
    for value in walk(document):
        if isinstance(value, dict) and '$ref' in value:
            resolve_ref(document, value['$ref'])
    schemes = components.get('securitySchemes', {})
    for name in ('hidraBearerJwt', 'externalOidcBearerJwt'):
        scheme = schemes.get(name, {})
        if (scheme.get('type'), scheme.get('scheme'), scheme.get('bearerFormat')) != ('http', 'bearer', 'JWT'):
            raise ValueError(f'Invalid security scheme: {name}')
    for path, method, security in (
        ('/api/v1/workbench/modules', 'get', [{'hidraBearerJwt': []}]),
        ('/api/v1/identity/authentication/oidc/complete', 'post', [{'externalOidcBearerJwt': []}]),
        ('/api/v1/identity/authentication/login', 'post', []),
    ):
        operation = paths.get(path, {}).get(method)
        if not isinstance(operation, dict) or operation.get('security') != security:
            raise ValueError(f'Invalid representative operation/security: {method} {path}')
    # Security references are names, not $ref pointers.
    for value in walk(document):
        if isinstance(value, dict) and 'security' in value:
            security = value['security']
            if not isinstance(security, list):
                raise ValueError('Security requirement must be an array')
            for requirement in security:
                if not isinstance(requirement, dict) or any(name not in schemes for name in requirement):
                    raise ValueError('Unresolved security scheme requirement')
    return {'paths': len(paths),
            'operations': sum(method in METHODS for item in paths.values() for method in item),
            'schemas': len(schemas)}


def validate_snapshot(root, manifest):
    contract = manifest['openapi']
    path = Path(root) / contract['path']
    document = load_json(path)
    counts = validate_document(document, contract['source_sha'])
    if path.read_bytes() != canonical_bytes(document):
        raise ValueError('Snapshot must be sorted compact JSON with one trailing newline')
    if hashlib.sha256(path.read_bytes()).hexdigest() != contract['sha256']:
        raise ValueError('Snapshot digest differs from reviewed manifest')
    if counts != contract['counts']:
        raise ValueError('Snapshot counts differ from reviewed manifest')
    provenance = contract['provenance']
    if (provenance['source_sha'] != contract['source_sha'] or provenance['conclusion'] != 'success'
            or not isinstance(provenance['run_id'], int) or not isinstance(provenance['artifact_id'], int)
            or not re.fullmatch(r'[0-9a-f]{64}', provenance['zip_sha256'])
            or not re.fullmatch(r'[0-9a-f]{64}', provenance['json_sha256'])):
        raise ValueError('Incomplete reviewed generation provenance')
    return document, counts


def compare_generated(snapshot, generated, expected_sha):
    validate_document(generated, expected_sha)
    before, after = dict(snapshot), dict(generated)
    before.pop('x-hidra-ci-source-sha')
    after.pop('x-hidra-ci-source-sha')
    if canonical_bytes(before) != canonical_bytes(after):
        raise ValueError('Runtime OpenAPI differs from canonical snapshot; review a generated-artifact refresh')


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--root', type=Path, default=Path('.'))
    parser.add_argument('--manifest', default=DEFAULT_MANIFEST)
    parser.add_argument('--generated', type=Path)
    parser.add_argument('--generated-source-sha')
    args = parser.parse_args()
    try:
        manifest = load_json(args.root / args.manifest)
        snapshot, counts = validate_snapshot(args.root, manifest)
        if bool(args.generated) != bool(args.generated_source_sha):
            raise ValueError('--generated and --generated-source-sha must be supplied together')
        if args.generated:
            compare_generated(snapshot, load_json(args.generated), args.generated_source_sha)
        print('OpenAPI validation passed: ' + json.dumps(counts, sort_keys=True)
              + ('; runtime equality verified' if args.generated else '; offline snapshot only'))
        return 0
    except (ValueError, OSError, KeyError, TypeError) as error:
        print(f'OpenAPI validation FAILED: {error}')
        return 1


if __name__ == '__main__':
    raise SystemExit(main())
