#!/usr/bin/env python3
"""FD-02: bounded real HTTP production-profile smoke, no bearer or secret logging."""
from __future__ import annotations

import argparse
import json
import os
import sys
import urllib.error
import urllib.request
import urllib.parse
import uuid
from pathlib import Path


class QualificationFailure(Exception):
    """Fail-closed assertion without printing sensitive server response bodies."""


class Client:
    def __init__(self, base: str):
        parsed = urllib.parse.urlsplit(base)
        if (parsed.scheme != 'http' or parsed.hostname != '127.0.0.1'
                or parsed.username is not None or parsed.password is not None
                or parsed.path or parsed.query or parsed.fragment or parsed.port is None):
            raise QualificationFailure('Only local loopback HTTP is admitted')
        self.base = base.rstrip('/')
        self.events: list[dict[str, object]] = []

    def request(self, method: str, path: str, *, token: str | None = None,
                payload: dict | None = None, expected: int = 200) -> object:
        if not path.startswith('/') or path.startswith('//'):
            raise QualificationFailure('Invalid local request path')
        data = json.dumps(payload).encode('utf-8') if payload is not None else None
        headers = {'Accept': 'application/json'}
        if payload is not None:
            headers['Content-Type'] = 'application/json'
        if token is not None:
            headers['Authorization'] = 'Bearer ' + token
        req = urllib.request.Request(self.base + path, data=data, method=method, headers=headers)
        status: int
        body: bytes
        try:
            with urllib.request.urlopen(req, timeout=15) as resp:
                status = resp.status
                body = resp.read(256 * 1024)
        except urllib.error.HTTPError as exc:
            status = exc.code
            body = b''  # Never log or parse denial bodies.
        except (urllib.error.URLError, TimeoutError) as exc:
            raise QualificationFailure('Local HTTP endpoint unavailable') from exc
        self.events.append({'method': method, 'path': path.split('?')[0], 'status': status})
        if status != expected:
            raise QualificationFailure(f'HTTP {method} {path.split("?")[0]}: expected {expected}, received {status}')
        if status in (204, 401, 403, 404):
            return None
        try:
            return json.loads(body)
        except (ValueError, UnicodeDecodeError) as exc:
            raise QualificationFailure('Expected structured JSON response') from exc


def require_shape(value: object, kind: type, operation: str) -> object:
    if not isinstance(value, kind):
        raise QualificationFailure(f'{operation} returned the wrong JSON structure')
    return value


def login(client: Client, username: str, password: str) -> tuple[str, str]:
    result = require_shape(client.request('POST', '/api/v1/identity/authentication/login',
        payload={'providerType': 'LOCAL', 'principal': username, 'credentials': password}), dict, 'login')
    token = result.get('accessToken')
    if not isinstance(token, str) or len(token) < 32:
        raise QualificationFailure('Login returned no usable access bearer')
    me = require_shape(client.request('GET', '/api/v1/identity/me', token=token), dict, 'principal')
    user_id = me.get('userId')
    if not isinstance(user_id, str) or not user_id:
        raise QualificationFailure('Principal returned no canonical user ID')
    return token, user_id


def module_reads(client: Client, bearer: str) -> None:
    # Only existing native REST routes; capabilities are not counted as business acceptance.
    choices = (
        ('/api/v1/organization/capabilities', dict),
        ('/api/v1/party/reference/party-types', list),
        ('/api/v1/topology/capabilities', dict),
        ('/api/v1/configuration/namespaces', list),
        ('/api/v1/workflow/capabilities', dict),
    )
    for endpoint, shape in choices:
        require_shape(client.request('GET', endpoint, token=bearer), shape, endpoint)


def run(base: str, phase: str, state_path: Path, username: str, password: str) -> dict:
    client = Client(base)
    # Missing, malformed and wrong credentials must fail without leaking protected state.
    client.request('GET', '/api/v1/identity/me', expected=401)
    client.request('GET', '/api/v1/identity/me', token='invalid.token.value', expected=401)
    client.request('POST', '/api/v1/identity/authentication/login', expected=401,
                   payload={'providerType': 'LOCAL', 'principal': username,
                            'credentials': 'deliberately-invalid-' + str(uuid.uuid4())})
    bearer, admin_id = login(client, username, password)
    module_reads(client, bearer)
    if phase == 'initial':
        user_name = 'fd02-operator-' + uuid.uuid4().hex[:12]
        user = require_shape(client.request('POST', '/api/v1/identity/users', token=bearer,
            payload={'username': user_name, 'emailAddress': user_name + '@synthetic.invalid',
                     'displayName': 'Synthetic FD02 Operator', 'userType': 'HUMAN'}), dict, 'user registration')
        user_id = user.get('id')
        if not isinstance(user_id, str) or not user_id or user.get('status') != 'REGISTERED':
            raise QualificationFailure('Native user registration did not return a canonical registered ID')
        saved = {'administrator': admin_id, 'user_id': user_id, 'username': user_name}
        state_path.write_text(json.dumps(saved, sort_keys=True), encoding='utf-8')
        state_path.chmod(0o600)
    else:
        saved = json.loads(state_path.read_text(encoding='utf-8'))
        if admin_id != saved['administrator']:
            raise QualificationFailure('Local administrator ID changed after process restart')
    user = require_shape(client.request('GET', '/api/v1/identity/users/' + saved['user_id'], token=bearer),
                         dict, 'persisted identity readback')
    if user.get('id') != saved['user_id'] or user.get('username') != saved['username']:
        raise QualificationFailure('Persisted identity changed across readback')
    client.request('GET', '/api/v1/identity/users/' + saved['user_id'], expected=401)
    return {'phase': phase, 'administrator_id': admin_id, 'business_id': saved['user_id'],
            'checks': client.events, 'case_count': len(client.events)}


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument('--phase', choices=('initial', 'restart'), required=True)
    parser.add_argument('--state', type=Path, required=True)
    parser.add_argument('--output', type=Path, required=True)
    args = parser.parse_args()
    try:
        result = run(os.environ['FD02_BASE_URL'], args.phase, args.state,
                     os.environ['FD02_ADMIN_USERNAME'], os.environ['FD02_ADMIN_PASSWORD'])
        args.output.write_text(json.dumps(result, sort_keys=True, indent=2) + '\n', encoding='utf-8')
        args.output.chmod(0o600)
        print('FD02 ' + args.phase + ' native HTTP cases: ' + str(result['case_count']) + ' PASS (redacted)')
        return 0
    except (QualificationFailure, KeyError, OSError, ValueError) as exc:
        # Errors are deliberately written as fixed class-level summaries; server response
        # bodies, secrets and tokens are never included in evidence or exception traces.
        print('FD02 ' + args.phase + ' FAILED: ' + str(exc), file=sys.stderr)
        return 1


if __name__ == '__main__':
    raise SystemExit(main())
