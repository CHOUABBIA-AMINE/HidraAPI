# HidraAPI Canonical Documentation Validation

## Status and applicability

CURRENT — HPR-P2-012 bounded CI validation controls, verified against source parent
`508337351eef03b82e2c6078a7c8523013efd063`, on 2026-10-09. Repository governance controls
manifest/index/status changes; module owners retain implementation authority. No new
named owner, per-class runtime result, deployment acceptance or physical evidence is
established. [Standard](DOCUMENTATION_STANDARD.md), [status model](DOCUMENT_STATUS_MODEL.md)
and [lifecycle](DOCUMENT_LIFECYCLE.md) govern metadata and preserved evidence.

## Implemented controls and boundaries

| Control | Source / bounded responsibility |
|---|---|
| Canonical estate | [validate_docs.py](../../.github/scripts/validate_docs.py): exact reviewed inventory, UTF-8/nonempty/conflicts, local inline/reference links and anchors |
| Metadata/status | Leading declared statuses; explicit containing-index/register metadata signals; exact historical P1 exceptions and roadmap ACTIVE control |
| Navigation/module/register | Declared index targets, actual module roots/documents, duplicate module links and CURRENT registry consistency |
| Execution control | Primary 13-row P2 registry shape/status/codes, selected next task and prerequisite-safe later closure |
| Snapshot integrity | [validate_openapi_snapshot.py](../../.github/scripts/validate_openapi_snapshot.py): strict JSON, sorted compact bytes, digest/counts/source/provenance, local pointers and representative security |
| Runtime drift | Production CI compares every generated value against canonical content except x-hidra-ci-source-sha; expected source identities validated separately |
| Compatibility | Existing generated-base resolver/tests and supported backward-compatibility gate remain separate and required |

[Reviewed manifest](../../.github/documentation-validation.json) declares inventory,
metadata inheritance, index/register obligations, module roots, historical-status
exceptions and artifact-derived snapshot identities. Signals and structural consistency
do not prove narrative truth, comprehensive business metadata or owner approval. Only
the leading status/control block is checked; appended historical task paragraphs are
not reclassified as present authority. Eleven exact P1 operating/status phrases retain
path-specific rationale, while bold HISTORICAL/CURRENT formatting uses normal statuses.
No historical P1 document is rewritten to satisfy the checker.

Local paths/fragments are checked without network calls; external/mailto availability
is not checked. Fenced/inline examples are ignored. Heading anchors handle formatting,
duplicates, Unicode and explicit IDs. Case-mismatched/missing targets and repository
escapes fail. The parser implements the estate's Markdown conventions, not every
CommonMark extension or arbitrary HTML resource reference.

## Commands

Run from repository root using standard-library Python 3 (CI Ubuntu provides it):

```bash
python3 .github/scripts/test_validate_docs.py
python3 .github/scripts/test_validate_openapi_snapshot.py
python3 .github/scripts/validate_docs.py
python3 .github/scripts/validate_openapi_snapshot.py
python3 .github/scripts/validate_openapi_snapshot.py --generated target/openapi/hidra-api.json --generated-source-sha "$GITHUB_SHA"
```

The last command requires actual generated output and its expected build SHA; it is
not part of the offline documentation job. Both scripts accept --root and --manifest
for isolated fixtures. Tests create temporary estates/contracts and assert actual
negative failures; they make no network/runtime/production-data changes.

## CI wiring

[Documentation workflow](../../.github/workflows/docs.yml) runs both suites and both
offline validators on doc/Markdown, exact validator/test/manifest and workflow changes.
[Production workflow](../../.github/workflows/ci.yml) also runs these checks, preserves
all prior P1/Java 21/PostgreSQL/clean-verify/OpenAPI gates and adds exact runtime equality
after fresh generation. Existing read-only permissions/concurrency remain intact.
A source-only production change still exercises documentation consistency and snapshot
equality through production CI. This implementation starts both exact-head workflows;
startup is not completion. Failures must be diagnosed before later task execution.

## Maintenance and generated-only refresh

Adding/removing a canonical document, module, entry point or status exception requires
deliberate manifest and index/register review. New unknown statuses do not become
accepted just by editing prose. Preserved historical phrases are exact/path-specific;
never add a wildcard exemption. Metadata inheritance must name existing canonical
sources and contain the declared owner/applicability/evidence/target/unknown/verification
signals. This is bounded validation, not automated business attestation.

When runtime contract content changes, obtain a successful exact-source generated
artifact; verify run/source/archive/member/digests; serialize its object with sorted
compact JSON and newline; update source/counts/file digest and generation provenance
in manifest/API documents. Preserve prior generation evidence. Never hand-edit the
contract, claim the consumer commit generated it, remove other fields during equality,
or treat supported compatibility as equality. The current snapshot is exactly the
verified CI #604 object at `617c2eec812e3a5734957ee9fa0360f6f5613032`: 244 paths,
263 operations, 231 schemas. Source/archive/file identities are in
[OpenAPI governance](../api/OPENAPI_GOVERNANCE.md).

## Actual validation and unresolved evidence

Python test/validator results and current Maven attempt are recorded in canonical
[execution memory](../roadmap/ULTIMATE_ROADMAP.md). The successful CI #604 generator
remains prior evidence; local Java 17 cannot establish a Java 21 build. The current
Bash wrapper clean-verify attempt failed before compilation resolving Boot parent
4.1.1 because Maven Central DNS was unavailable. No runtime/JPA/test pass or physical
campaign is claimed. New exact-head production/documentation CI follows publication.

## TARGET and NOT ESTABLISHED

Complete narrative truth, full Markdown/OpenAPI certification, external-link uptime,
per-class execution/no skips, deployed-data/import approval and fresh physical
survivability are NOT ESTABLISHED by these controls. HPR-P2-013 closure remains pending,
P3 deferred and P2 open. Future rule expansions require their own reviewed scope.
