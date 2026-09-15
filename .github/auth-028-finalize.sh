#!/usr/bin/env bash
set -euo pipefail

python3 - <<'PY'
from pathlib import Path
p = Path('docs/roadmap/authentication.md')
s = p.read_text()

replacements = [
    (
        '| Status | Active — AUTH-027 Hidra authorization ownership proof completed |',
        '| Status | Active — AUTH-028 authentication provider deployment runbook completed |'
    ),
    (
        '| AUTH-028 | `docs(authentication): add ldap and provider deployment runbook` | Document non-secret provider/TLS/configuration inputs and diagnostics | Planned |',
        '| AUTH-028 | `docs(authentication): add ldap and provider deployment runbook` | Document non-secret provider/TLS/configuration inputs and diagnostics | Completed — `docs/AUTHENTICATION_DEPLOYMENT_RUNBOOK.md` documents LOCAL bootstrap inputs, LDAP/AD connection/search/LDAPS/trust requirements, OIDC validation/linkage, Hidra JWT settings, provider prerequisites, deployment checks, diagnostics, and secret-handling rules; repository CI run 293 passed |'
    ),
    (
'''### AUTH-028 — Deployment runbook

Commit:

```text
docs(authentication): add ldap and provider deployment runbook
```

Document only non-secret operational requirements:

```text
provider enablement
LDAPS hosts/base DN/search filter/username attribute
immutable AD identifier
bind/search strategy
truststore/certificate requirements
OIDC issuer/client/audience/scope configuration
JWT signing/verification configuration
timeouts and health diagnostics
```

Do not commit passwords, client secrets, private certificates, or private keys.

---''',
'''### AUTH-028 — Deployment runbook

Commit:

```text
docs(authentication): add ldap and provider deployment runbook
```

Status:

```text
Completed — docs/AUTHENTICATION_DEPLOYMENT_RUNBOOK.md records the current non-secret deployment contract for persistent LOCAL bootstrap, LDAP/Active Directory enablement and search behavior, immutable directory identity selection, staging/production LDAPS enforcement, external JVM trust requirements, OIDC browser/external-token validation inputs, Hidra-issued JWT signing/validation inputs, IdentityProvider/ExternalIdentity prerequisites, provider-selection behavior, deployment checks, diagnostics, and explicit secret-handling rules. The runbook is grounded in live application properties and authentication configuration; it introduces no production behavior, provider provisioning shortcut, external group-to-role mapping, or AUTH-029+ guardrail implementation.
```

Validation:

```text
Documentation/source inspection plus repository CI.
```

Result:

```text
PASS — PR CI run 293 completed repository compile/tests/full verification, acceptance compile/tests/clean verify, deterministic OpenAPI publication, and artifact upload successfully for AUTH-028 implementation commit 2c477be58e0fcf776f86837c6408f686599d2604.
```

---'''
    ),
    (
'''AUTH-028 — docs(authentication): add ldap and provider deployment runbook
```

AUTH-027 now proves Hidra authorization ownership across OIDC and LDAP/Active Directory authentication: external claims and directory identity context cannot grant business permissions beyond Hidra Identity-owned effective permissions. Do not implement AUTH-029 or later tasks during AUTH-028.''',
'''AUTH-029 — test(authentication): add authentication architecture and secret guardrails
```

AUTH-028 now documents the live non-secret provider deployment contract for LOCAL, LDAP/Active Directory, OIDC validation, and Hidra JWT operation. Do not implement AUTH-030 or later tasks during AUTH-029.'''
    )
]

for old, new in replacements:
    if old not in s:
        raise SystemExit('Expected roadmap text not found:\n' + old[:300])
    s = s.replace(old, new, 1)

p.write_text(s)
PY

git config user.name "github-actions[bot]"
git config user.email "41898282+github-actions[bot]@users.noreply.github.com"
git add docs/roadmap/authentication.md
git rm .github/auth-028-finalize.sh .github/workflows/auth-028-roadmap-finalize.yml
git commit -m "docs(authentication): add ldap and provider deployment runbook"
git push origin HEAD:auth-028-authentication-deployment-runbook
