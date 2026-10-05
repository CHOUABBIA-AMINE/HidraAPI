# Workbench Data Exposure Security Contract

## Status

CURRENT REMEDIATION CONTRACT — HPR-P0-001 INPUT

## Audit Finding

The forensic audit established this source-level path:

1. `HidraOperationalWorkbenchService.ensureIndexed()` discovers module entities from the JPA metamodel.
2. Workbench module discovery does not establish exclusion of Identity credential persistence.
3. Generic record mapping reflects entity fields into response attributes.
4. `LocalCredentialJpaEntity` contains `passwordHash`.
5. The audited Workbench path did not establish an approved-resource allowlist or approved-field/redaction allowlist.

The audit did not execute an exploit and did not establish deployed exploitation. The source-level path is nevertheless a P0 defect requiring remediation.

## Required Contract

### Fail-closed resource exposure

- A JPA entity MUST NOT become externally readable solely because it exists in the metamodel.
- Every Workbench-readable resource MUST be explicitly approved.
- Credential, authentication-secret, token-secret, or equivalent sensitive persistence resources MUST be excluded unless a separately reviewed API-safe projection is intentionally provided.
- Unknown/unclassified resources MUST be denied.

### Fail-closed field exposure

- A field MUST NOT become externally readable solely because reflection can access it.
- Exposed fields MUST be explicitly approved or supplied through a reviewed API-safe projection.
- `passwordHash` and equivalent credential/secret values are prohibited.
- Unknown/unclassified sensitive fields MUST be denied.
- Serialization annotations alone are insufficient protection for code that reads `java.lang.reflect.Field` values directly.

### Authorization

- Authentication and route authorization remain mandatory.
- Generic route permission derivation is not field-level authorization.
- Every exposed resource/operation MUST have a verified authorization policy.
- Exposure policy MUST remain independent of broad route access.

### Architecture boundary

- Platform generic reads MUST NOT silently bypass module-owned application boundaries.
- If a generic platform read boundary is retained, it MUST be explicit, narrowly governed, covered by architecture/security tests, and documented as an approved exception.

## HPR-P0-001 Write Discipline

Before code changes:

1. recover exact current Workbench controller/service/response behavior;
2. recover current Identity credential persistence fields;
3. recover current route authorization behavior;
4. identify actual Workbench resource requirements from current repository evidence;
5. choose the narrowest fail-closed implementation that preserves required behavior.

Do not add unrelated features.

## Required Regression Coverage for HPR-P0-002

Tests must prove prohibited data is absent from:

- list responses;
- detail responses;
- filtered/search responses;
- generic attribute maps;
- Identity credential resources.

An explicit test MUST assert that `passwordHash` is never returned.

## Closure Gate

The Workbench P0 exposure is not closed until:

1. fail-closed resource policy is implemented;
2. fail-closed field policy/API-safe projection policy is implemented;
3. credential/secret structures are protected;
4. resource/operation authorization is verified;
5. focused regression tests pass;
6. architecture/security guardrails cover the resulting boundary;
7. full repository verification succeeds;
8. `doc/roadmap/ULTIMATE_ROADMAP.md` records exact completion evidence.
