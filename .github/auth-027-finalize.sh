#!/usr/bin/env bash
set -euo pipefail

python3 - <<'PY'
from pathlib import Path

path = Path('docs/roadmap/authentication.md')
text = path.read_text()

replacements = {
    '| Status | Active — AUTH-026 unified JWT compatibility proof completed |':
        '| Status | Active — AUTH-027 Hidra authorization ownership proof completed |',
    '| AUTH-027 | `test(authentication): protect hidra authorization ownership` | Prove external groups/claims do not bypass Hidra role/permission decisions | Planned |':
        '| AUTH-027 | `test(authentication): protect hidra authorization ownership` | Prove external groups/claims do not bypass Hidra role/permission decisions | Completed — dedicated OIDC and LDAP authorization-ownership proof shows external roles/groups/scopes/permissions and directory identity context cannot grant business permissions, wildcard access, or admin authority beyond Hidra Identity-owned effective permissions; `mvn -q test` passed in PR CI run 286 |',
    '''### AUTH-027 — Remaining authorization-ownership proof task

AUTH-027 adds dedicated proof that external groups/claims cannot bypass Hidra authorization.

Critical final proof:

```text
same Hidra user authorization semantics regardless of authentication provider
provider failure never falls through to another provider
all successful providers yield the same API token/principal contract
```

---''':
        '''### AUTH-027 — Hidra authorization ownership proof

Commit:

```text
test(authentication): protect hidra authorization ownership
```

Status:

```text
Completed — HidraAuthorizationOwnershipTest exercises the production OIDC normalization converter, LDAP AuthenticationProvider, and HidraEffectivePermissionResolver. Privileged external OIDC roles, groups, scopes, permissions, wildcard-like values, and administrator claims remain non-authoritative: the normalized authentication carries no external business authorities, HidraPrincipal contains only Identity-owned effective permissions, Hidra-granted permissions are accepted, and externally asserted permissions/wildcard access remain denied. LDAP directory verification and identity/DN context likewise authenticate and link identity only; only Hidra Identity-owned effective permissions can satisfy authorization. No production behavior or AUTH-028+ deployment/runbook scope was added.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — PR CI run 286 completed repository `mvn -q test` successfully for AUTH-027 implementation commit 2623511d894c2a4a7692f1203d5dc57a3e02f83d. The same run also passed repository compile/full verification, acceptance compile/tests/clean verify, deterministic OpenAPI publication, and artifact upload with no production changes required.
```

---''',
    '''## 22. Next task

Execute only:

```text
AUTH-027 — test(authentication): protect hidra authorization ownership
```

AUTH-026 now proves unified Hidra JWT compatibility across LOCAL, LDAP/Active Directory, and OIDC using the production issuer, decoder, and protected-API authentication converter. Do not implement AUTH-028 or later tasks during AUTH-027.''':
        '''## 22. Next task

Execute only:

```text
AUTH-028 — docs(authentication): add ldap and provider deployment runbook
```

AUTH-027 now proves Hidra authorization ownership across OIDC and LDAP/Active Directory authentication: external claims and directory identity context cannot grant business permissions beyond Hidra Identity-owned effective permissions. Do not implement AUTH-029 or later tasks during AUTH-028.'''
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f'expected roadmap text not found: {old[:120]!r}')
    text = text.replace(old, new, 1)

path.write_text(text)
PY

rm -f .github/auth-027-finalize.sh .github/workflows/auth-027-roadmap-finalize.yml

git config user.name "Aminosoft"
git config user.email "147345045+CHOUABBIA-AMINE@users.noreply.github.com"
git add docs/roadmap/authentication.md .github/auth-027-finalize.sh .github/workflows/auth-027-roadmap-finalize.yml
git commit -m "test(authentication): protect hidra authorization ownership"
git push origin HEAD:auth-027-hidra-authorization-ownership
