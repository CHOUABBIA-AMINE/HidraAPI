#!/usr/bin/env bash
set -euo pipefail
python3 - <<'PY'
from pathlib import Path
p = Path('docs/roadmap/authentication.md')
s = p.read_text()
old = '| Status | Active — AUTH-029 authentication architecture and secret guardrails completed |'
new = '| Status | Active — AUTH-030 final authentication gap-closure verification in progress |'
if old not in s:
    raise SystemExit('document-control status anchor not found')
s = s.replace(old, new, 1)
old = '| AUTH-030 | `docs(authentication): finalize authentication gap closure checklist` | Record executable evidence and remaining optional cleanup | Planned |'
new = '| AUTH-030 | `docs(authentication): finalize authentication gap closure checklist` | Record executable evidence and remaining optional cleanup | In Progress — executable closure evidence recorded; final `mvn -q clean verify` pending |'
if old not in s:
    raise SystemExit('AUTH-030 table anchor not found')
s = s.replace(old, new, 1)
anchor = '''Do not mark items complete without evidence.\n\n---\n\n## 20. Explicit non-goals'''
replacement = '''Do not mark items complete without evidence.\n\nStatus:\n\n```text\nIn Progress — executable evidence for every required authentication gap-closure criterion is recorded below. Final closure remains pending until this AUTH-030 documentation state itself passes `mvn -q clean verify` in repository CI.\n```\n\nClosure evidence checklist:\n\n| Closure criterion | Evidence | Result |\n|---|---|---|\n| Explicit provider selection dynamically routes to the intended provider | AUTH-022 `IdentityAuthenticationRequestRouterTest` and `HidraAuthenticationManagerConfigurationTest`; CI run 239 | PASS — LOCAL, LDAP, and ACTIVE_DIRECTORY dispatch deterministically; OIDC direct-password routing and unsupported types fail closed |\n| LOCAL authenticates against PostgreSQL-backed credentials | AUTH-023 provider/unit/API proof plus PostgreSQL Testcontainers integration; CI run 248 | PASS — persisted `LocalCredential` BCrypt hash is used by the real LOCAL provider |\n| LDAP/AD authenticates through protected LDAP transport | AUTH-024 LDAP provider/adapter/configuration proof; CI run 257; AUTH-012 infrastructure baseline CI run 153 | PASS — directory verification/linkage is covered and staging/production enforce `ldaps://` with positive connection/read timeouts |\n| OIDC remains functional and maps to Hidra identity | AUTH-025 OIDC normalization/API proof; CI run 270; existing OIDC contract remains protected | PASS — validated issuer+subject resolves through active provider + linked `ExternalIdentity` to Hidra User |\n| All providers normalize to `HidraPrincipal` | AUTH-023, AUTH-024, AUTH-025 provider-specific proofs plus AUTH-026 unified JWT compatibility proof; CI runs 248, 257, 270, 279 | PASS — LOCAL, LDAP, ACTIVE_DIRECTORY, and OIDC converge on stable Hidra identity |\n| Hidra roles/permissions remain authoritative | AUTH-027 authorization-ownership proof; CI run 286 | PASS — external OIDC claims and directory context cannot grant business permissions beyond Identity-owned effective permissions |\n| All successful providers issue/produce the same Hidra JWT contract | AUTH-026 production encoder/decoder/converter compatibility proof; CI run 279 | PASS — common subject, issuer/audience/JTI, provider metadata, roles/scope reconstruction, and bearer factor verified across all provider sources |\n| Ordinary in-memory LOCAL authentication is retired | AUTH-020 retirement; CI run 220; AUTH-021 persistent administrator bootstrap; CI run 229 | PASS — ordinary runtime no longer relies on `InMemoryUserDetailsManager`; bootstrap provisions normal persisted Identity state |\n| No provider fallback exists | AUTH-022 routing/provider-manager proof; CI run 239 | PASS — failed selected LOCAL authentication does not invoke LDAP and unsupported/OIDC direct routes fail closed |\n| No secrets are committed | AUTH-029 architecture/secret guardrails; CI run 300 and clean-tree CI run 305; AUTH-028 deployment runbook | PASS — secret-bearing properties remain externally supplied and production source/resource checks reject embedded private-key material |\n| `mvn -q clean verify` passes for final closure | AUTH-030 CI | PENDING — must pass on the final closure documentation state before AUTH-030 is marked Completed |\n\nDefinition-of-done cross-check:\n\n```text\nItems 1-15 are backed by AUTH-002 through AUTH-029 implementation/test evidence recorded in this roadmap.\nItem 16 remains the AUTH-030 final clean verification gate and is intentionally not marked complete yet.\nNo mandatory authentication gap remains open based on the recorded executable evidence; section 20 items remain explicit optional/non-goal work.\n```\n\nValidation:\n\n```bash\nmvn -q clean verify\n```\n\nResult:\n\n```text\nPENDING — execute repository CI for the AUTH-030 documentation state before final closure.\n```\n\n---\n\n## 20. Explicit non-goals'''
if anchor not in s:
    raise SystemExit('AUTH-030 detail anchor not found')
s = s.replace(anchor, replacement, 1)
p.write_text(s)
PY

git config user.name "Aminosoft"
git config user.email "147345045+CHOUABBIA-AMINE@users.noreply.github.com"
git add docs/roadmap/authentication.md
git commit -m "docs(authentication): finalize authentication gap closure checklist"
git push origin HEAD

git rm .github/auth-030-initial.sh .github/workflows/auth-030-initial.yml
git commit -m "docs(authentication): finalize authentication gap closure checklist"
git push origin HEAD
