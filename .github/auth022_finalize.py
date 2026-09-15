from pathlib import Path

path = Path('docs/roadmap/authentication.md')
text = path.read_text()

replacements = {
    '| Status | Active — AUTH-021 safe persistent LOCAL administrator bootstrap completed |':
        '| Status | Active — AUTH-022 dynamic provider routing proof completed |',
    '| AUTH-022 | `test(authentication): cover dynamic provider routing` | Verify provider-selection dispatch, supports contracts, unsupported type, and no fallback | Planned |':
        '| AUTH-022 | `test(authentication): cover dynamic provider routing` | Verify provider-selection dispatch, supports contracts, unsupported type, and no fallback | Completed — focused router and ProviderManager tests prove LOCAL -> LocalAuthenticationToken, LDAP/ACTIVE_DIRECTORY -> LdapAuthenticationToken, OIDC/unsupported selections fail closed, providers may support only one Hidra request token type, selected-provider failure never invokes another provider, and unsupported request types are rejected; `mvn -q test` passed in PR CI run 239 |',
    '### AUTH-022 through AUTH-027 — Security proof tasks\n\nThese tasks add dedicated routing, LOCAL, LDAP, OIDC normalization, JWT compatibility, and authorization-ownership tests.\n\nEach task must run the appropriate Maven test command and record real results in this roadmap.':
        '### AUTH-022 — Dynamic provider routing proof\n\nCommit:\n\n```text\ntest(authentication): cover dynamic provider routing\n```\n\nStatus:\n\n```text\nCompleted — IdentityAuthenticationRequestRouterTest proves deterministic explicit provider selection: LOCAL produces only LocalAuthenticationToken; LDAP and ACTIVE_DIRECTORY produce only LdapAuthenticationToken; OIDC is rejected from direct-credential routing; all other ProviderType values fail closed; and a missing provider selection is rejected. HidraAuthenticationManagerConfigurationTest proves supports-contract isolation, dispatches LOCAL/LDAP requests only to matching providers, rejects any provider claiming both Hidra request token types, rejects unsupported request types, and verifies a failed selected LOCAL provider never invokes the LDAP provider. No production behavior or AUTH-023+ provider integration coverage was added.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — PR CI run 239 completed repository mvn -q test successfully for AUTH-022 corrected implementation commit 4dba3e6cb138c388dbf9de248069052824e264a9. The same run also passed repository full verification, acceptance tests, acceptance clean verify, and deterministic OpenAPI publication. Earlier run 238 exposed only an over-specific assertion on Spring ProviderManager error text; the proof was corrected without changing production behavior.\n```\n\n---\n\n### AUTH-023 through AUTH-027 — Remaining security proof tasks\n\nThese tasks add dedicated LOCAL, LDAP, OIDC normalization, JWT compatibility, and authorization-ownership tests.\n\nEach task must run the appropriate Maven test command and record real results in this roadmap.',
    'AUTH-022 — test(authentication): cover dynamic provider routing':
        'AUTH-023 — test(authentication): cover local authentication',
    'AUTH-021 now provides explicit, persistent, auditable LOCAL administrator bootstrap with no default password or in-memory fallback. Do not implement AUTH-023 or later tasks during AUTH-022.':
        'AUTH-022 now proves deterministic provider-selection routing, provider supports isolation, unsupported-type failure, and no provider fallback. Do not implement AUTH-024 or later tasks during AUTH-023.'
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f'Missing expected roadmap text: {old[:100]}')
    text = text.replace(old, new, 1)

path.write_text(text)
