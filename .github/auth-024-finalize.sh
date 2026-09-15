#!/usr/bin/env bash
set -euo pipefail

python3 - <<'PY'
from pathlib import Path

path = Path('docs/roadmap/authentication.md')
text = path.read_text(encoding='utf-8')
replacements = [
    (
        '| Status | Active — AUTH-023 persisted LOCAL authentication proof completed |',
        '| Status | Active — AUTH-024 LDAP/Active Directory authentication proof completed |'
    ),
    (
        '| AUTH-024 | `test(authentication): cover ldap authentication` | LDAP/AD adapter, mapping, outage, TLS, and end-to-end coverage | Planned |',
        '| AUTH-024 | `test(authentication): cover ldap authentication` | LDAP/AD adapter, mapping, outage, TLS, and end-to-end coverage | Completed — provider, credential-adapter, security-configuration, and direct-login API tests prove LDAP/ACTIVE_DIRECTORY normalization, stable external identity linkage, Hidra account-state/permission ownership, invalid/unmapped/inactive/locked failure behavior, directory outage fail-closed behavior, LDAP filter escaping, AD objectGUID normalization, production/staging LDAPS enforcement, timeout validation, and explicit LDAP/AD API provider selection; `mvn -q test` passed in PR CI run 257 |'
    ),
    (
        '### AUTH-024 through AUTH-027 — Remaining security proof tasks\n\nThese tasks add dedicated LDAP, OIDC normalization, JWT compatibility, and authorization-ownership tests.',
        '''### AUTH-024 — LDAP/Active Directory authentication proof

Commit:

```text
test(authentication): cover ldap authentication
```

Status:

```text
Completed — LdapAuthenticationProviderTest proves successful LDAP and ACTIVE_DIRECTORY normalization through stable ExternalIdentity linkage to an active Hidra User, Hidra-owned effective permissions with no directory-group authority promotion, invalid credentials, missing mappings, inactive external identity, locked account, unavailable/ambiguous provider configuration, directory outage fail-closed behavior, and provider token support isolation. LdapCredentialVerificationAdapterTest proves submitted principals are LDAP-filter escaped, the configured search filter must contain the principal placeholder, AD objectGUID bytes normalize to a stable Base64 subject, directory identity attributes are normalized, and blank credentials fail before directory access. HidraLdapSecurityConfigurationTest proves production/staging require LDAPS and validates positive connection/read timeouts. IdentityAuthenticationControllerLdapApiTest proves the canonical direct-login API preserves explicit LDAP and ACTIVE_DIRECTORY provider selection, credentials, client metadata, correlation ID, and the normalized Hidra response contract. No production behavior or AUTH-025+ OIDC/JWT proof scope was added.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — PR CI run 257 completed repository mvn -q test successfully for AUTH-024 corrected implementation commit 60437b8660a7ff59f5fcd9513142fe5d118ef240. The same run also passed repository compile/full verification, acceptance compile/tests/clean verify, and deterministic OpenAPI publication. Earlier run 256 exposed only a test assertion that expected lower-case LDAP base-DN text although LdapContextSource preserves the configured DN casing; the assertion was corrected without changing production behavior.
```

---

### AUTH-025 through AUTH-027 — Remaining security proof tasks

These tasks add dedicated OIDC normalization, JWT compatibility, and authorization-ownership tests.'''
    ),
    (
        'AUTH-024 — test(authentication): cover ldap authentication',
        'AUTH-025 — test(authentication): cover oidc normalization'
    ),
    (
        'AUTH-023 now proves persisted LOCAL authentication across provider unit, PostgreSQL integration, and login API boundary coverage. Do not implement AUTH-025 or later tasks during AUTH-024.',
        'AUTH-024 now proves LDAP/Active Directory authentication across provider, credential-adapter, security-configuration, and direct-login API coverage. Do not implement AUTH-026 or later tasks during AUTH-025.'
    )
]
for old, new in replacements:
    count = text.count(old)
    if count != 1:
        raise SystemExit(f'expected one occurrence, found {count}: {old[:100]!r}')
    text = text.replace(old, new, 1)
path.write_text(text, encoding='utf-8')
PY

git config user.name "Aminosoft"
git config user.email "147345045+CHOUABBIA-AMINE@users.noreply.github.com"
git add docs/roadmap/authentication.md
git rm -f .github/auth-024-finalize.sh .github/workflows/auth-024-roadmap-finalize.yml .github/auth-024-finalize-trigger.txt
git commit -m "test(authentication): cover ldap authentication"
git push origin HEAD:auth-024-ldap-authentication-tests
