#!/usr/bin/env bash
set -euo pipefail

python3 - <<'PY'
from pathlib import Path

path = Path('docs/roadmap/authentication.md')
text = path.read_text(encoding='utf-8')
replacements = [
    (
        '| Status | Active — AUTH-024 LDAP/Active Directory authentication proof completed |',
        '| Status | Active — AUTH-025 OIDC normalization proof completed |'
    ),
    (
        '| AUTH-025 | `test(authentication): cover oidc normalization` | Protect existing OIDC behavior and prove Hidra principal/authorization normalization | Planned |',
        '| AUTH-025 | `test(authentication): cover oidc normalization` | Protect existing OIDC behavior and prove Hidra principal/authorization normalization | Completed — validated issuer+subject normalization, active provider/linkage/account-state enforcement, Hidra-owned permission resolution, external role/group/scope isolation, and secured OIDC completion API convergence are covered; existing OIDC PKCE/browser contract regression tests remain intact; `mvn -q test` passed in PR CI run 270 |'
    ),
    (
        '### AUTH-025 through AUTH-027 — Remaining security proof tasks\n\nThese tasks add dedicated OIDC normalization, JWT compatibility, and authorization-ownership tests.',
        '''### AUTH-025 — OIDC normalization proof

Commit:

```text
test(authentication): cover oidc normalization
```

Status:

```text
Completed — IdentityOidcJwtAuthenticationConverterTest proves already-validated external OIDC JWTs normalize by issuer + subject through an ACTIVE OIDC IdentityProvider and LINKED ExternalIdentity to an ACTIVE Hidra User/HidraPrincipal, while missing issuer/subject, unavailable/inactive providers, unmapped/inactive external identities, missing users, locked users, and disabled users fail closed before Hidra authorization is loaded. The test also proves external roles, groups, and scope claims are ignored for Hidra business authorization and only Identity-owned effective permissions populate HidraPrincipal. IdentityAuthenticationControllerOidcApiTest proves the secured OIDC completion boundary accepts only an OIDC HidraPrincipal, forwards client metadata to the provider-neutral completion use case, never re-enters direct credential authentication, and returns the same normalized Hidra session/token/principal response shape. Existing HidraOidcContractControllerTest continues to protect the authorization-code + PKCE browser contract and external-IdP dependency signaling. No production behavior or AUTH-026+ unified-JWT/authorization-ownership proof scope was added.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — PR CI run 270 completed repository `mvn -q test` successfully for AUTH-025 implementation commit 63f5d39084ae8604a53bff1fcb926c33d4a171c1. The same run also passed repository compile/full verification, acceptance compile/tests/clean verify, deterministic OpenAPI publication, and artifact upload with no corrective production changes required.
```

---

### AUTH-026 through AUTH-027 — Remaining security proof tasks

These tasks add dedicated unified JWT compatibility and authorization-ownership tests.'''
    ),
    (
        'AUTH-025 — test(authentication): cover oidc normalization',
        'AUTH-026 — test(authentication): verify unified jwt compatibility'
    ),
    (
        'AUTH-024 now proves LDAP/Active Directory authentication across provider, credential-adapter, security-configuration, and direct-login API coverage. Do not implement AUTH-026 or later tasks during AUTH-025.',
        'AUTH-025 now proves OIDC normalization across validated issuer/subject identity mapping, Hidra account-state/authorization ownership, external-claim isolation, and secured completion API coverage. Do not implement AUTH-027 or later tasks during AUTH-026.'
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
git rm -f .github/auth-025-finalize.sh .github/workflows/auth-025-roadmap-finalize.yml
git commit -m "test(authentication): cover oidc normalization"
git push origin HEAD:auth-025-oidc-normalization-tests
