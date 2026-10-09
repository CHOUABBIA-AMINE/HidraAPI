# HidraAPI API Testing

## Status and applicability

CURRENT — HPR-P2-011 source-derived verification documentation, checked on 2026-10-09
against parent `35d9d949aa773a4d754c22d00181330f579f752b`. Owner authority, evidence precedence and
verification limits follow [the testing index](README.md). TARGET additions and
NOT ESTABLISHED facts below do not represent implemented gates or executed results.

## Current HTTP and security evidence

| Source test | Fixture scope |
|---|---|
| [HidraOperationalWorkbenchHttpExposureTest](../../src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchHttpExposureTest.java) | Standalone MockMvc with mocked EntityManager; admitted resource/field exposure and serialized sensitive-field exclusion |
| [HidraOidcContractControllerTest](../../src/test/java/dz/sh/hidra/platform/security/HidraOidcContractControllerTest.java) | Direct controller metadata assertions for configured OIDC contract and external IdP registration requirement; no live login/filter-chain exercise |
| [HidraRouteAuthorizationInterceptorTest](../../src/test/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptorTest.java) | Effective permission matching/missing denial and public login exception |
| [HidraRequestContextFilterTest](../../src/test/java/dz/sh/hidra/platform/web/HidraRequestContextFilterTest.java) | Request-context/filter-specific behavior |
| [HidraGlobalExceptionHandlerTest](../../src/test/java/dz/sh/hidra/platform/exception/HidraGlobalExceptionHandlerTest.java) | Exception-handler fixture; not universal machine-readable error-envelope coverage |
| [HidraOpenApiSecurityConfigurationTest](../../src/test/java/dz/sh/hidra/platform/configuration/HidraOpenApiSecurityConfigurationTest.java) | OpenAPI bearer/security configuration |
| [HidraApplicationTests](../../src/test/java/dz/sh/hidra/HidraApplicationTests.java) | Full application context startup; no assertion of all route responses |

Standalone controllers, direct security objects, mocked persistence and the full
application context are different evidence populations. A controller passing does
not imply a full live Identity/database/filter-chain request. [Authentication](../api/AUTHENTICATION_AUTHORIZATION.md)
and [error model](../api/ERROR_MODEL.md) keep their own applicability; this set adds
no endpoint, permissions or common error contract. Actual commands are in
[Test Strategy](TEST_STRATEGY.md#commands-and-prerequisites).

## Runtime OpenAPI and compatibility gate

The [production CI](../../.github/workflows/ci.yml) runs clean verify, starts the built JAR with test profile and
Springdoc explicitly enabled, waits for health and fetches /v3/api-docs. It checks
hidraBearerJwt/externalOidcBearerJwt HTTP bearer JWT schemes, protected workbench,
OIDC completion and public-login security declarations. It adds x-hidra-ci-source-sha
and serializes sorted JSON before generating the comparison-base contract.

[base resolver](../../.github/scripts/resolve_openapi_base.py) preserves the
exact PR target. For push/dispatch it selects a successful production-CI ancestor
of the requested predecessor, validating ancestry and failing closed when no suitable
base exists. Documentation-success alone is insufficient. Its existing
[resolver tests](../../.github/scripts/test_resolve_openapi_base.py) run in CI.
The historical base builds with maven.test.skip=true; current-head tests are required
by the earlier repository-verification step. The base/current JARs use separate ports.

[compatibility checker](../../.github/scripts/openapi_compatibility.py) compares
its supported path/operation, parameter/request-body, content/response, schema
required-property/type/enum and public-to-authenticated changes. Local references
are resolved where supported. This is a bounded checker, not every OpenAPI/JSON
Schema compatibility rule, every authentication-policy change or HTTP behavior.
Current output is uploaded as hidra-api-openapi-<SHA>, retained 30 days by the workflow.
An artifact name/source SHA supplies provenance; it does not approve operational data.

## Historical HPR-P2-011 snapshot/documentation boundary

[OpenAPI governance](../api/OPENAPI_GOVERNANCE.md) and
[versioned snapshot](../api/openapi.yaml) retain HPR-P2-005 provenance. Their older
x-hidra-ci-source-sha identifies the executable generator, not this documentation
head. Prior full CI #604 passed generation/base/compatibility steps; this task did
not regenerate either contract or assert snapshot equality. The [documentation CI](../../.github/workflows/docs.yml) checks
Markdown UTF-8/nonempty/conflicts. It does not enforce link/status/index drift or
canonical-vs-generated OpenAPI equality; those controls belong to HPR-P2-012.

## HPR-P2-012 implemented drift controls

Source parent `508337351eef03b82e2c6078a7c8523013efd063`, checked 2026-10-09. The snapshot now derives from verified CI #604 (244 paths/263 operations/231 schemas). [Validation guide](../governance/DOCUMENTATION_VALIDATION.md) records offline structural/serialization/provenance and exact production runtime equality, removing only the validated source-SHA field. Compatibility remains distinct. Equality against the retained successful generator was checked locally; new exact-head runtime generation is a pending CI result, not a claimed local Maven pass. The preceding section retains historical HPR-P2-011 applicability.

## TARGET and NOT ESTABLISHED

Complete route/filter-chain coverage, universal error behavior, production endpoint
acceptance and new exact-head runtime acceptance are
NOT ESTABLISHED by the documented controls. No mock fixture or generated schema proves every runtime
authorization decision. Further enforcement needs its own admitted scope.
