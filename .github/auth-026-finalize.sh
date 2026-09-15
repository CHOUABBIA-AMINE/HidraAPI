#!/usr/bin/env bash
set -euo pipefail

python3 - <<'PY'
from pathlib import Path

path = Path('docs/roadmap/authentication.md')
text = path.read_text(encoding='utf-8')
replacements = [
    (
        '| Status | Active — AUTH-025 OIDC normalization proof completed |',
        '| Status | Active — AUTH-026 unified JWT compatibility proof completed |'
    ),
    (
        '| AUTH-026 | `test(authentication): verify unified jwt compatibility` | Verify all providers produce tokens accepted by current security filters and permission enforcement | Planned |',
        '| AUTH-026 | `test(authentication): verify unified jwt compatibility` | Verify all providers produce tokens accepted by current security filters and permission enforcement | Completed — real production issuer/encoder -> Hidra decoder -> protected-API authentication conversion is proven for LOCAL, LDAP, ACTIVE_DIRECTORY, and OIDC, including stable subject/issuer/audience/JTI, provider metadata, Hidra roles/scopes, ROLE_/SCOPE_ reconstruction, and FACTOR_BEARER; `mvn -q test` passed in PR CI run 279 |'
    ),
    (
        '''### AUTH-026 through AUTH-027 — Remaining security proof tasks

These tasks add dedicated unified JWT compatibility and authorization-ownership tests.

Each task must run the appropriate Maven test command and record real results in this roadmap.

Critical final proof:

```text
same Hidra user authorization semantics regardless of authentication provider
provider failure never falls through to another provider
all successful providers yield the same API token/principal contract
```''',
        '''### AUTH-026 — Unified Hidra JWT compatibility proof

Commit:

```text
test(authentication): verify unified jwt compatibility
```

Status:

```text
Completed — HidraUnifiedJwtCompatibilityTest issues real Hidra JWTs from normalized LOCAL, LDAP, ACTIVE_DIRECTORY, and OIDC HidraPrincipal values through the production HidraAccessTokenIssuer/HidraJwtEncoderConfiguration, validates them through the production hidraJwtDecoder used by ordinary protected APIs, and reconstructs authorities through the production Hidra JWT authentication converter. The proof verifies stable Hidra subject, issuer/audience/JTI, authentication-source metadata, optional provider linkage, deterministic Hidra-owned roles/scope claims, ROLE_/SCOPE_ authority reconstruction, and the framework FACTOR_BEARER authentication factor for every provider source. No production behavior or AUTH-027+ authorization-ownership scope was added.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — PR CI run 279 completed repository `mvn -q test` successfully for AUTH-026 corrected implementation commit 9669ff803b1132127bbc78f320a33f83e2e250fc. The same run also passed repository compile/full verification, acceptance compile/tests/clean verify, deterministic OpenAPI publication, and artifact upload. Earlier runs 277 and 278 exposed only test expectation mismatches around Spring's typed issuer accessor for the configured non-URL issuer string and the framework-added FACTOR_BEARER authority; both were corrected without changing production behavior.
```

---

### AUTH-027 — Remaining authorization-ownership proof task

AUTH-027 adds dedicated proof that external groups/claims cannot bypass Hidra authorization.

Critical final proof:

```text
same Hidra user authorization semantics regardless of authentication provider
provider failure never falls through to another provider
all successful providers yield the same API token/principal contract
```'''
    ),
    (
        'AUTH-026 — test(authentication): verify unified jwt compatibility',
        'AUTH-027 — test(authentication): protect hidra authorization ownership'
    ),
    (
        'AUTH-025 now proves OIDC normalization across validated issuer/subject identity mapping, Hidra account-state/authorization ownership, external-claim isolation, and secured completion API coverage. Do not implement AUTH-027 or later tasks during AUTH-026.',
        'AUTH-026 now proves unified Hidra JWT compatibility across LOCAL, LDAP/Active Directory, and OIDC using the production issuer, decoder, and protected-API authentication converter. Do not implement AUTH-028 or later tasks during AUTH-027.'
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
git rm -f .github/auth-026-finalize.sh .github/workflows/auth-026-roadmap-finalize.yml
git commit -m "test(authentication): verify unified jwt compatibility"
git push origin HEAD:auth-026-unified-jwt-compatibility
