# HidraAPI API Error Model

## Status and applicability

CURRENT — source-derived HPR-P2-005 runtime error documentation correction,
verified on 2026-10-09 against parent `b39c87dcaf887ada1d22c6a88d1e1f51b9f7ecaa`
after Documentation Validation #130 passed. A shared machine-readable error envelope
is still **NOT ESTABLISHED by the generated OpenAPI contract**. Ownership and
verification limits follow [the API index](README.md) and
[canonical register](../governance/DOCUMENT_REGISTER.md).

## Generated contract evidence

[openapi.yaml](openapi.yaml) contains 244 paths, 263 operations and 231 schemas.
Its response declarations contain HTTP 200 for **258** operations and HTTP 201 for
**five** operations. The former 254 count belonged to older generation evidence and
is superseded for the current snapshot. No 4xx/5xx responses or shared generated
ProblemDetail/error component are declared. This does not mean runtime errors are absent.

The sections below describe executable source, not a newly standardized generated
error contract. Do not hand-edit the snapshot to add undocumented error responses.

## Global MVC controller-advice mappings

Evidence: [HidraGlobalExceptionHandler](../../src/main/java/dz/sh/hidra/platform/exception/HidraGlobalExceptionHandler.java).
Its RestControllerAdvice selects base package `dz.sh.hidra`. The table applies when
that handler processes the exception; higher-precedence scoped advice and failures
handled before/elsewhere in MVC can produce different responses.

| Exception handled | HTTP status | `code` and `title` | Additional behavior |
|---|---:|---|---|
| DomainException | 422 | `DOMAIN_ERROR` | Global ProblemDetail fields below |
| PlatformException | 500 | `PLATFORM_ERROR` | Global ProblemDetail fields below |
| NoSuchElementException | 404 | `RESOURCE_NOT_FOUND` | Not every application not-found path throws this exception |
| IllegalArgumentException | 400 | `INVALID_REQUEST` | Includes source paths using this exception for unknown references |
| MethodArgumentNotValidException | 400 | `VALIDATION_ERROR` | Adds `fieldErrors`, a list of field/message strings |
| ConstraintViolationException | 400 | `CONSTRAINT_VIOLATION` | Does not add the above fieldErrors list in this handler |
| AuthenticationException | 401 | `AUTHENTICATION_REQUIRED` | Applies when this advice receives the exception; not a universal JWT/filter mapping |
| AccessDeniedException | 403 | `ACCESS_DENIED` | Applies when this advice receives the exception; scoped denial can use another code |
| Other Exception handled by catch-all | 500 | `INTERNAL_ERROR` | No generic promise that every framework binding/error path uses a 400 response |

### Body fields and validation details

The handler constructs Spring ProblemDetail with the status above, then sets:

| Field/extension | Source behavior |
|---|---|
| `status` | HTTP status supplied to ProblemDetail.forStatus |
| `title`, `code` | Stable handler code from the table |
| `detail` | Trimmed exception message; null/blank message becomes `Unexpected error.` |
| `instance` | Request URI |
| `path` | Request URI extension |
| `timestamp` | Instant.now().toString() extension |
| `correlationId`, `requestId` | Added only when the corresponding LoggingContext value exists |
| `fieldErrors` | Only the MethodArgumentNotValid handler: `field: message`; missing default message becomes `invalid value` |
| `exception` | Exception class name only when `hidra.api.errors.include-debug-details` enables it; injected default is false |

This advice does not set a custom `type` URI. Exact wire serialization/media type
is not declared as a shared generated envelope by the current snapshot. A schematic
source-derived validation example consists of status 400, title/code VALIDATION_ERROR,
request instance/path, timestamp and fieldErrors; optional tracing/debug properties
must not be treated as required fields. Messages are source exception/validation text,
not a documented localization taxonomy or universal disclosure-redaction guarantee.

## Higher-precedence controller-scoped advice

Each class below has highest-precedence advice limited to the named controller.
For the listed exceptions it constructs ProblemDetail with status, title, exception
message as detail and the `code` extension. It does not explicitly add global
`path`, `timestamp`, `correlationId`, `requestId`, debug or validation extensions.
An actual framework-populated field must not be confused with a field explicitly
set by these classes.

| Advice/source | Controller scope | Exception/reason | HTTP | `title` / `code` |
|---|---|---|---:|---|
| [PlanningRevisionApiExceptionHandler](../../src/main/java/dz/sh/hidra/modules/planning/api/rest/PlanningRevisionApiExceptionHandler.java) | PlanRevisionCommandController | PlanningRevisionConflictException | 409 | `PLANNING_REVISION_CONFLICT` |
| [AlarmSuppressionApiExceptionHandler](../../src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandler.java) | AlarmSuppressionController | AlarmSuppressionConflictException | 409 | `ALARM_SUPPRESSION_CONFLICT` |
| [AssetsApiExceptionHandler](../../src/main/java/dz/sh/hidra/modules/assets/api/rest/AssetsApiExceptionHandler.java) | SpringAssetsController | MaintainableAssetConflictException | 409 | `ASSETS_MAINTAINABLE_ASSET_CONFLICT` |
| [WorkflowTransitionApiExceptionHandler](../../src/main/java/dz/sh/hidra/modules/workflow/api/rest/WorkflowTransitionApiExceptionHandler.java) | WorkflowTransitionController | WorkflowTransitionConflictException | 409 | `WORKFLOW_TRANSITION_CONFLICT` |
| Same Workflow advice | WorkflowTransitionController | WorkflowTransitionDeniedException | 403 | `WORKFLOW_TRANSITION_DENIED` |
| [DocumentsContentApiExceptionHandler](../../src/main/java/dz/sh/hidra/modules/documents/api/rest/DocumentsContentApiExceptionHandler.java) | SpringDocumentsController | DocumentContentTransferException: INVALID_CONTENT | 400 | `DOCUMENTS_CONTENT_INVALID` |
| Same Documents advice | SpringDocumentsController | DocumentContentTransferException: NOT_FOUND | 404 | `DOCUMENTS_CONTENT_NOT_FOUND` |
| Same Documents advice | SpringDocumentsController | DocumentContentTransferException: STORAGE_FAILURE | 503 | `DOCUMENTS_CONTENT_STORAGE_FAILURE` |

For example, a PlanningRevisionConflictException handled in its scoped controller
produces 409/PLANNING_REVISION_CONFLICT. A generic domain exception handled by the
global advice produces 422/DOMAIN_ERROR instead; neither row establishes that all
business conflicts are 409. These are exception/controller mappings, not guarantees
that every operation exposes every listed response.

## Not-found and validation are exception-specific

[JpaTelemetryQueryAdapter](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/query/JpaTelemetryQueryAdapter.java)
throws IllegalArgumentException for an unknown point or missing latest reading.
[Workbench service](../../src/main/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchService.java)
uses it for a missing record. With global advice those cases map to 400/INVALID_REQUEST,
not 404/RESOURCE_NOT_FOUND. Documents content's NOT_FOUND has its explicit 404 rule.
Do not silently rewrite these source behaviors into an idealized REST convention.

Pagination clamps are also distinct from validation failures: representative
Workbench and telemetry behavior is documented in [API conventions](API_CONVENTIONS.md).
Parsed normalization, bean-validation exceptions, malformed binding and business
invariants are different paths; only the evidenced handlers are mapped above.

## Authentication, authorization and request-filter boundary

[HidraSecurityConfiguration](../../src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java)
configures JWT resource-server filter chains. These filters are distinct from MVC
controller advice. The global 401/403 rows are proven advice methods, not proof that
all missing/invalid token or filter-level access failures contain the global fields.
A universal filter/authentication error-body contract is NOT ESTABLISHED by the
current generated snapshot or this source-only review. Other framework/servlet
error handling is likewise outside the uniform advice-body claim.

[HidraRequestContextFilter](../../src/main/java/dz/sh/hidra/platform/web/HidraRequestContextFilter.java)
sets resolved X-Correlation-Id/X-Request-Id response headers on its request path;
global advice can copy available context into body extensions. Scoped advice does
not explicitly copy those extensions. Headers are tracing metadata, not proof of
an authenticated actor, and universal async/error-dispatch outcomes are not claimed.

## Evidence and verification limits

Existing [global handler tests](../../src/test/java/dz/sh/hidra/platform/exception/HidraGlobalExceptionHandlerTest.java)
assert NoSuchElement 404 and AccessDenied 403. The
[Planning conflict test](../../src/test/java/dz/sh/hidra/modules/planning/api/rest/PlanningRevisionApiExceptionHandlerTest.java)
asserts the stable 409 code. These source references support particular assertions;
this documentation correction does not rerun them or claim every mapping was exercised.

A future shared generated error contract requires implementation, generated schema,
tests and compatibility review. Until then, consumers should use the scoped current
source documentation and operation contract without assuming one universal body,
status or required extension across all handlers/filter failures.
