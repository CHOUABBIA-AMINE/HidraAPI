# HidraAPI CI/CD Release Guide

## Status

CONTROLLED PIPELINE IMPLEMENTED / LIVE PRODUCTION-EQUIVALENT EXECUTION PENDING — HPR-P1-018

Execution base: 4923d8cc3a770e108e9171feb3d53a833fa7300f.

## Release authority

Production release uses .github/workflows/release.yml.

The deploy job targets GitHub Environment production. Repository/platform administrators must configure required reviewers and deployment access restrictions on that Environment. Repository source alone cannot prove those protection settings are active.

## Promotion gate

Each release dispatch requires an exact 40-character source SHA and an approved change identifier.

Before packaging, the workflow verifies that the SHA is reachable from main and that a successful exact-SHA HidraAPI CI run exists.

Mutable branch names or tags are not accepted as the release identity.

## Immutable artifact

The exact candidate is packaged once as hidra-api-<SOURCE_SHA>.jar, hashed with SHA-256, recorded in a release manifest, uploaded as a workflow artifact, downloaded by the deploy job, and digest-verified before rollout.

The deployment job does not rebuild a different revision.

## Production configuration and Vault

Production nodes must provide:

- /etc/hidra/hidra.env with SPRING_PROFILES_ACTIVE=production;
- /etc/hidra/hidra.env with HIDRA_ENVIRONMENT=production;
- node-specific role configuration;
- Vault-backed /run/hidra/hidra-secrets.env.

The Vault-rendered file is mandatory and must include HIDRA_SECRETS_SOURCE=vault.

The release workflow never transports application database/JWT/LDAP secrets. GitHub Environment secrets are limited to deployment transport and acceptance credentials.

## Environment configuration

Required production Environment variables:

- HIDRA_APP_NODE_1_SSH;
- HIDRA_APP_NODE_2_SSH;
- HIDRA_HA_CONTROL_SSH;
- HIDRA_HA_BASE_URL;
- HIDRA_PRODUCTION_ACCEPTANCE_PATH.

Required production Environment secrets:

- HIDRA_PRODUCTION_SSH_PRIVATE_KEY;
- HIDRA_PRODUCTION_SSH_KNOWN_HOSTS;
- HIDRA_PRODUCTION_ACCEPTANCE_BEARER_TOKEN.

The acceptance path must be an approved harmless authenticated read that traverses the application/database path.

## Controlled rollout

Deployment order is hidra-api-2 first, then realtime-active hidra-api-1.

For each node the workflow drains HAProxy traffic, copies and checksum-verifies the exact artifact, verifies production/Vault preconditions, stops systemd, installs to /opt/hidra/releases/<SHA>, atomically changes /opt/hidra/current, starts the service, waits for local readiness, performs an authenticated database-backed acceptance request, rejoins HAProxy, and verifies the HA endpoint.

This order preserves the P1 single-active realtime node until the second deployment step.

## Migration safety

The workflow requires schema_change_mode to be declared as no-schema-change or forward-compatible-migration.

A rolling migration must remain compatible with the previous application node while that node is serving.

Flyway remains schema authority. The release workflow never runs clean, edits migration history, migrates a standby independently, or implicitly reverses database schema.

## Rollback

Automatic application-artifact rollback is allowed only when artifact_rollback_compatible=true was explicitly supplied.

The prior release symlink is retained as /opt/hidra/previous.

If compatibility is not approved, a failed node remains drained for diagnosis rather than silently reverting application code against an incompatible schema.

Database recovery follows the database/DR procedures and is not an automatic application rollback side effect.

## Evidence and remaining verification

GitHub Actions records the actor, run ID, source SHA, change ID, candidate digest, schema-change declaration, rollback declaration, production Environment approval history where configured, and deployment result.

HPR-P1-018 remains IMPLEMENTED-PENDING-ENVIRONMENT-AND-EXERCISE until required-reviewer protection is confirmed, production variables/secrets are configured, Vault rendering is active on both VMs, a validate-only run succeeds, and an approved production-equivalent deployment plus schema-compatible rollback exercise is observed.

That measured evidence remains mandatory for HPR-P1-012.
