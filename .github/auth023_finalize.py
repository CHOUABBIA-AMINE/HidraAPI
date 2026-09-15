from pathlib import Path

path = Path('docs/roadmap/authentication.md')
text = path.read_text()

replacements = {
    '| Status | Active — AUTH-022 dynamic provider routing proof completed |':
        '| Status | Active — AUTH-023 persisted LOCAL authentication proof completed |',
    '| AUTH-023 | `test(authentication): cover local authentication` | Unit/integration/API coverage for persisted LOCAL authentication | Planned |':
        '| AUTH-023 | `test(authentication): cover local authentication` | Unit/integration/API coverage for persisted LOCAL authentication | Completed — provider unit tests, PostgreSQL Testcontainers integration, and the LOCAL login API boundary prove persisted BCrypt credential verification, account/credential state enforcement, sanitized outcomes, normalized HidraPrincipal/response contracts, and successful persisted login-state update; `mvn -q test` passed in PR CI run 248 |',
    '### AUTH-023 through AUTH-027 — Remaining security proof tasks\n\nThese tasks add dedicated LOCAL, LDAP, OIDC normalization, JWT compatibility, and authorization-ownership tests.\n\nEach task must run the appropriate Maven test command and record real results in this roadmap.':
        '### AUTH-023 — Persisted LOCAL authentication proof\n\nCommit:\n\n```text\ntest(authentication): cover local authentication\n```\n\nStatus:\n\n```text\nCompleted — LocalAuthenticationProviderTest exercises successful persisted BCrypt-backed LOCAL authentication plus wrong-password, locked-account, inactive-credential, unavailable-provider, supports-contract, sanitized failure-outcome, permission-resolution, and HidraPrincipal normalization behavior. LocalAuthenticationIntegrationTest uses PostgreSQL Testcontainers and the real Spring Identity infrastructure to persist a normal active user and LocalCredential, seed the active LOCAL provider in the migrated schema, authenticate through the real LocalAuthenticationProvider, and prove the persisted BCrypt hash and successful last-authenticated state update. IdentityAuthenticationControllerLocalApiTest proves the LOCAL API boundary forwards explicit provider selection, credentials, client metadata, and correlation ID into DirectAuthenticationCommand and returns the normalized Hidra session/token/principal response. No production behavior or AUTH-024+ LDAP/OIDC/JWT proof scope was added.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — PR CI run 248 completed repository mvn -q test successfully for AUTH-023 corrected implementation commit cbf1b00d6942e427c15842b4cab5f70436037dfe. The same run also passed repository full verification, acceptance tests, acceptance clean verify, and deterministic OpenAPI publication. Earlier runs 246 and 247 exposed only integration-fixture issues: the existing IdentityProvider String-to-jsonb JPA insert quirk and JdbcTemplate Instant type inference respectively; the test fixture was narrowed to direct migrated-table provider seeding with JDBC Timestamp values without changing production behavior.\n```\n\n---\n\n### AUTH-024 through AUTH-027 — Remaining security proof tasks\n\nThese tasks add dedicated LDAP, OIDC normalization, JWT compatibility, and authorization-ownership tests.\n\nEach task must run the appropriate Maven test command and record real results in this roadmap.',
    'AUTH-023 — test(authentication): cover local authentication':
        'AUTH-024 — test(authentication): cover ldap authentication',
    'AUTH-022 now proves deterministic provider-selection routing, provider supports isolation, unsupported-type failure, and no provider fallback. Do not implement AUTH-024 or later tasks during AUTH-023.':
        'AUTH-023 now proves persisted LOCAL authentication across provider unit, PostgreSQL integration, and login API boundary coverage. Do not implement AUTH-025 or later tasks during AUTH-024.'
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f'Missing expected roadmap text: {old[:120]}')
    text = text.replace(old, new, 1)

path.write_text(text)
