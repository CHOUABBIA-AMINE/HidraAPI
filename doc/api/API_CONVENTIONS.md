# HidraAPI API Conventions

## Status and applicability

CURRENT — source-derived HPR-P2-005 audit correction, verified on 2026-10-09
against parent `b39c87dcaf887ada1d22c6a88d1e1f51b9f7ecaa` after Documentation
Validation #130 passed. Ownership/evidence authority follows [the API index](README.md)
and [the canonical register](../governance/DOCUMENT_REGISTER.md). This document
records inspected behavior; it does not assert a new runtime test result.

## Contract and runtime authority

[The committed OpenAPI snapshot](openapi.yaml) governs generated operation paths,
parameters, request/response schemas and security declarations. Runtime constraints
not encoded there are labelled source-derived below. Consumers must use the media
type, requiredness and representation declared by the particular operation.

Current product APIs use `/api/v1/**`; generated API metadata reports `v1`.
JSON is the dominant request representation, with multipart and text/event-stream
surfaces where explicitly declared. Do not infer one media type for every endpoint.
Generated enum/format/minimum/maximum/required-property declarations apply where
present; runtime business validation may be stricter. Generated operation IDs exist,
but the compatibility checker does not compare their changes; an operation ID alone
is not a backward-compatibility guarantee. See [compatibility](VERSIONING_COMPATIBILITY.md).

## Workbench list and search

Evidence: [controller](../../src/main/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchController.java),
[service](../../src/main/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchService.java),
[search request](../../src/main/java/dz/sh/hidra/platform/workbench/OperationalSearchRequest.java),
[page response](../../src/main/java/dz/sh/hidra/platform/workbench/OperationalPageResponse.java).
These rules apply to approved Workbench resources, not all module queries.

| Surface | Generated shape | Source-derived behavior |
|---|---|---|
| GET `/api/v1/workbench/{module}/{resource}` and `/api/v1/{module}/workbench/{resource}` | Optional `page`, `size`, `q` query parameters | `q` becomes the service query; GET supplies no structured filters or sort |
| POST to either corresponding `/search` path | Optional JSON `OperationalSearchRequest` body: `query`, `filters`, `page`, `size`, `sortBy`, `sortDirection` | Absent body uses empty criteria; fields are normalized as below |
| Both surfaces | `OperationalPageResponse` | Zero-based page; absent/negative page becomes 0. Absent/nonpositive size becomes 50; positive size is capped at 200 |

### Filtering and sorting

- `query` (GET `q`) is trimmed and lowercased with Locale.ROOT; nonblank input
  generates SQL LIKE predicates across the resource's approved searchable fields.
  Those predicates are ORed. The pattern is `%query%`; the implementation does not
  escape LIKE wildcard characters, so `%`/`_` are not promised as literal matching.
- POST `filters` is a map. Approved exposed fields with non-null values generate
  equality predicates using string casts and `value.toString()`. Predicates are
  ANDed with each other and the text-query predicate. Unknown/null keys or null
  values are ignored; this is not a universal typed filter/operator language.
- POST `sortBy` must name an exposed field. Case-insensitive `desc` requests
  descending order; another direction with a valid field requests ascending order.
  Missing/blank/unknown fields produce no order clause. No stable default or
  tie-break order is established by this implementation.

A scoped request example is POST to an existing approved resource's `/search` with
`{"page":0,"size":50,"filters":{},"sortDirection":"desc"}`. Because no `sortBy`
is supplied, this example has no order clause; direction alone does not select a field.
Do not infer that an arbitrary module/resource or field is exposed.

Workbench response fields are `module`, `resource`, `page`, `size`, `totalElements`,
`totalPages` and `items`. Each item is an `OperationalRecordResponse`, rather than
telemetry's `ReadingView`. `totalPages` is calculated from the matching count and
normalized size. A request beyond the last page is not automatically reset to it.

## Telemetry readings, latest reading and trend

Evidence: [TelemetryQueryController](../../src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java),
[JpaTelemetryQueryAdapter](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/query/JpaTelemetryQueryAdapter.java),
[TelemetryQueryUseCase DTOs](../../src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java).

| GET path under `/api/v1/telemetry` | Generated parameters/defaults | Source-derived query rules | Response |
|---|---|---|---|
| `/points/{pointId}/readings` | Optional ISO date-time `from`/`to`, optional `state`; `page=0`, `size=100` | Page clamps to at least 0; size clamps to 1–500. Time bounds are inclusive. Blank state means no state filter; nonblank state matches case-insensitively, without a declared fixed validation enum. Order is sourceTimestamp descending | `PageReadingView`: `content`, `page`, `size`, `totalElements`, `totalPages`, `hasNext` |
| `/points/{pointId}/readings/latest` | Required path `pointId` | Latest sourceTimestamp; absence of a reading throws IllegalArgumentException | One `ReadingView` |
| `/points/{pointId}/trend` | Optional ISO date-time `from`/`to`; `limit=1000` | Limit clamps to 1–10000, inclusive time bounds, sourceTimestamp ascending | A list of `ReadingView`; not a page |

For example, a readings request with `page=-1&size=600` is normalized by this adapter
to page 0 and size 500; Workbench would cap the size at 200 instead. Trend's default
1000 is a limit, not the maximum readings page size. Invalid timestamp binding is
not the same case as a parsed but out-of-range integer.

Telemetry's response page is the nested `TelemetryQueryUseCase.Page`, including
`hasNext`; it is not `kernel.pagination.Page`. ReadingView carries typed value fields,
unit/quality metadata, source/receive timestamps, state, correlationId and rejectionReason.
An unknown point throws IllegalArgumentException. The generic missing-resource 404
mapping must not be promised for these adapter paths; see [errors](ERROR_MODEL.md).
The source performs these selected filters in memory after retrieval; documentation
does not claim database-level paging or a throughput guarantee.

## Kernel pagination is a separate contract

[PageRequest](../../src/main/java/dz/sh/hidra/kernel/pagination/PageRequest.java)
rejects negative pages and sizes outside 1–200. A blank sort field becomes absent;
with no field the direction becomes null, and with a field/missing direction it
becomes ASC. [Kernel Page](../../src/main/java/dz/sh/hidra/kernel/pagination/Page.java)
has `content`, `page`, `size`, `totalElements` and `totalPages`.
These are kernel type rules for paths that actually use them, not overrides of the
Workbench or Telemetry implementations documented above. No universal page envelope,
page-size policy, sorting syntax or filtering language is established for all APIs.

## Correlation and request metadata

Evidence: [PlatformHeaders](../../src/main/java/dz/sh/hidra/platform/web/PlatformHeaders.java),
[HidraRequestContextFilter](../../src/main/java/dz/sh/hidra/platform/web/HidraRequestContextFilter.java).

| Header | Source-backed handling on this filter's request path |
|---|---|
| `X-Correlation-Id` | Trim a supplied nonblank value; otherwise generate a UUID. Populate LoggingContext/MDC and set the resolved response header before the chain proceeds |
| `X-Request-Id` | Resolve independently with the same trim/fallback rules; populate context and set the response header |

The component is a highest-precedence OncePerRequestFilter and clears its context
in finally. For a supplied value ` trace-1 `, the resolved header is `trace-1`;
blank input uses a generated UUID. The filter does not enforce a UUID format for
caller-supplied values or prove caller uniqueness. IDs are trace metadata, not
trusted authenticated actor identity. They do not establish idempotency/replay rules.
Global advice uses available context in its body, while scoped advice may omit it.

These are current runtime facts even when generated operations do not expose the
headers as explicit parameters. Universal async/error-dispatch behavior or a common
header/body outcome on every framework/filter failure is NOT ESTABLISHED here.
CORS settings control browser exposure separately; a response header does not by
itself prove that every browser deployment exposes it. See
[security configuration](../../src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java).

## Response documentation and verification limits

The current snapshot has 258 HTTP-200 and five HTTP-201 response declarations across
263 operations. It contains no declared 4xx/5xx response taxonomy or shared generated
error schema. [ERROR_MODEL.md](ERROR_MODEL.md) documents available runtime advice
without promoting it to a universal generated contract. Source-linked tests are
corroboration, not a newly executed endpoint campaign; applicability follows the index.
