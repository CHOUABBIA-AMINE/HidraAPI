# HidraAPI OpenAPI Governance

## Status

CURRENT — canonical OpenAPI generation/provenance control; HAPI-587 generation-step refresh with aggregate CI acceptance pending.

## Source of Generation

The repository CI generates the current contract by:

1. running the Java/Maven verification gate;
2. starting the built HidraAPI executable with `SPRING_PROFILES_ACTIVE=test`;
3. enabling Springdoc API docs and disabling Swagger UI;
4. waiting for application health;
5. retrieving `/v3/api-docs`;
6. verifying required security schemes and representative protected/OIDC/public operations;
7. adding `x-hidra-ci-source-sha`;
8. serializing deterministically with sorted JSON keys;
9. generating the exact comparison-base contract;
10. applying `.github/scripts/openapi_compatibility.py`;
11. retaining the generated contract as a workflow artifact.

## Canonical Snapshot

`doc/api/openapi.yaml` is the version-controlled canonical snapshot.

For deterministic compactness it is stored using JSON syntax, which is valid YAML 1.2. Its semantic object is reconstructed without endpoint/schema edits from the generated CI artifact.

The current `x-hidra-ci-source-sha` is:

`a98ef0ea115b85a80941be40406511334b4b477a`

That is intentional: it identifies the executable revision that generated the contract, not the later documentation/CI commit that versions the snapshot.

## Historical HPR-P2-005 Provenance

- initial generator: `6822f3ce79b305e1c22f48ff20ef0b0bd2c7f135`;
- full CI run #566 / ID `37444404855`: SUCCESS;
- generated artifact ID `11402224423`;
- artifact digest `sha256:763472783957989e9aa4d56394d5d183fd835321d1c3cec2639d2a4fe8a06a44`;
- generated OpenAPI: 3.1.0;
- generated paths: 240;
- generated operations: 259;
- generated schemas: 227.

The restored P1-complete tree was verified identical to that executable source tree, and intervening P2 changes through the HPR-P2-005 base are documentation-only.

## HPR-P2-012 refreshed generation provenance

Current generator: `617c2eec812e3a5734957ee9fa0360f6f5613032`; successful production CI #604,
run `37841205677`; artifact `11577329466`,
`hidra-api-openapi-617c2eec812e3a5734957ee9fa0360f6f5613032`. ZIP SHA-256:
`2ff35c384c54b2d49e50aa2a9e646a67c62427e8afcc8db92e17e5368a91b334`.
Generated member hidra-api.json SHA-256:
`1ee03ad711b79c7ef42767a6aa3c685f4857800f09f03002deeac49e4e2d23d4`.
Sorted compact snapshot SHA-256:
`ba9511455ebabb51c704ef510d08f4fdc766f29d8def0a34ef7a8f1acf86d9fd`.
These are distinct file/archive digests, not Git object IDs. The object is copied
from verified generated evidence without endpoint/schema/security edits.
HPR-P2-012 source applicability: parent `508337351eef03b82e2c6078a7c8523013efd063`, checked 2026-10-09.
This commit is the validator/snapshot consumer, not its executable generator.

## Update Procedure

When executable API behavior changes:

1. change executable source/tests as authorized by its roadmap task;
2. run full exact-head CI;
3. allow CI to generate current and base OpenAPI;
4. pass security-contract assertions;
5. pass backward-compatibility checking;
6. retain the generated artifact;
7. replace the committed snapshot with the successful generated contract;
8. update API governance/provenance metadata when appropriate.

Do not hand-author endpoint/schema changes directly in `openapi.yaml`.

## Drift Control

[Validation guide](../governance/DOCUMENTATION_VALIDATION.md) documents the reviewed manifest, structural checks and strict production runtime comparison. Offline documentation validation verifies canonical serialization, file digest/counts/source provenance, local references and representative security declarations. Production CI compares freshly generated content after removing only the source-SHA field, validating both generator identities separately. All other object values and array order remain significant. Supported backward compatibility remains a separate required gate.

A contract change requires a successful generated artifact and reviewed snapshot/provenance/manifest refresh; never widen an ignore list or hand-author a schema to silence a failure. These checks cover the repository JSON snapshot subset, not complete OpenAPI certification or production acceptance.

## HAPI-587 generated snapshot repair — 2026-10-19

The canonical snapshot now uses executable generator `a98ef0ea115b85a80941be40406511334b4b477a`.
It is reconstructed without endpoint/schema/security edits from the actual retained
`openapi/hidra-api.json` member of artifact12191249823 in run39264047957, attempt1,
job118098882769 (API contract and dictionary). Generation step7,
`Publish deterministic OpenAPI contract`, completed **successfully**.
The overall job and workflow **FAILED**: runtime snapshot equality rejected the old
snapshot; other lanes reported independent guardrail/fixture failures.
The manifest records both failed aggregate conclusions explicitly. Its
`provenance.conclusion=success` is scoped only to the named generation step,
not to full CI, compatibility acceptance or HAPI-587 completion.

Artifact ZIP SHA256: `db55d874636feba7df69dab8ac112403e06a79d820bdb23ae94af38fb0f70f75`.
Actual generated member SHA256: `1144f929d23a0b3d4bb63fc2bac645c6ca1a1cb9b9e59b91deeb8933305842ce`.
Canonical sorted compact snapshot SHA256: `b4e5f611148bce49e9855c5f5af944499e3a3cf69e995c3c7176b49ffd82546a`.
The generated object contains247 paths/267 operations/236 schemas.
Every old path/operation object and every old schema are preserved exactly; the
addition is three path items/four operations/five schemas for engineering drafts.
The source SHA identifies the executable generator, not this subsequent repair commit.
The repaired source still requires freshly generated exact-SHA equality, full tests
and actual database dictionary capture/reconciliation before acceptance.
The four-case/14-fixture-error failures are not presented as passing test evidence.

## HAPI-588B generated snapshot repair — 2026-10-20

The snapshot is copied from the actual `openapi/hidra-api.json` member in
artifact12247149549 (run39386968386, attempt1, API job118509010097), generated
from `5f21de85ed9320916ee545f28192aeb8516df27e`. The named generation step7
succeeded; the job and workflow failed. The manifest scopes its success claim
to generation only, with failed job/workflow conclusions recorded separately.
No full-CI, compatibility, dictionary or engineering acceptance is claimed.

Artifact ZIP SHA256: `8068c3274aaae5259945869583b6e88722d936edd59120fa49724adecd2a400c`.
Actual generated member SHA256: `d9c87f4ea24b74da5688f92527dbe301cf128bde7c5d3e03207b05fb87cb9155`.
Canonical sorted compact snapshot SHA256: `d1e8827a81a3009c793bfbd6945e95e5023442f693de98bc77465c69c12c1b77`.
The document contains248 paths/269 operations/244 schemas. Every prior path item
and schema is unchanged; the generated addition is one graph route, two operations
and eight schemas. No endpoint/schema/security object was manually authored.
Strict local snapshot validation and semantic equality to this retained generator
passed. Fresh repair-SHA generation/equality and compatibility remain required.
The physical dictionary was not produced because equality failed first; preserve
the prior dictionary until actual migrated-catalog evidence is available.
This targeted failure repair is not Package C completion or HAPI-588 acceptance.

## HAPI-588C successful runtime artifact reconciliation — 2026-10-20

The current snapshot and physical dictionary now identify successful executable
source `e8f4f631ff45d75dbd761ee4372453e1f76721fe`, Full CI #771,
run39400041135/attempt1. All seven jobs succeeded, including the unrestricted
Maven oracle, both PostgreSQL shards, API contract and dictionary, and full gate.
Fast #96/run39400041256 and Docs #445/run39400041231 also succeeded on this SHA.
The prior failed-run repair provenance above remains historical evidence.

Artifacts were recovered from the authenticated full evidence bundle12255170844,
ZIP SHA256 `2f3e80bea58775cf4980dd65873c0956603b21bf9cc5a937123e3db7b56e3622`.
API job118552180381 and oracle job118552179948 produced byte-identical OpenAPI,
catalog JSON and rendered dictionary outputs. Named generation/equality/catalog/
compatibility steps all succeeded. Bundle file hashes and raw test reports were
rechecked locally:2555 cases/425 classes, zero failures/errors/skips, matching
partition/oracle cases. This does not approve optimized-only CI; the bundle still
requires unrestricted fallback and reports optimized adoption ineligible.

Generated OpenAPI member SHA256:
`1fed7799a2ad3e5d74e04f1184b1471936625273d00b0dbf1511628fa56b592f`.
Canonical snapshot SHA256:
`c06cac659168aa5c433b72eb57bc2c76367fc083fb6c8791cee2c2f814247461`.
Counts remain248 paths/269 operations/244 schemas. Removing only the generator
SHA yields exact equality to the prior reviewed snapshot: every path, schema,
security declaration and array order is retained. Supported compatibility also
passes against the actual generated base `2702a010bdb659a9a238672a90b7e2b2214fd2bf`.

Catalog member SHA256:
`101d9e2b1e5b959ab8d541e03e87ff2ce09ddc5ac6c079064ce7b965d74d2cca`.
Migration/JPA source bundle:
`9dfd75ade23bb7b2162527345f209ab0173c53ec4eb1ba8054647cbd4bdc2e47`.
Rendered dictionary SHA256:
`bf652f851ad9885925bbc44496f5758cc616fc9783f84a3b1d4f8dc6e6d9d9aa`.
The unchanged generator revalidated156 migrations/470 mappings/501 relations,
zero unresolved owners, against the recovered source files and actual PostgreSQL
16.15 catalog. Local rendering equals the hosted dictionary byte for byte and
strict --check passes. Only its capture SHA differs from the prior dictionary;
no physical field or ownership declaration is edited.

This HAPI-588C commit reconciles successful evidence; formal HAPI-588 acceptance
remains a separate registered step after C's own applicable CI passes. It does
not qualify an effective physical network, numerical solver, commissioning,
deployment or operational control.

## HAPI-590C generated publication artifact repair — 2026-10-21

Owner Fail addresses the inherited canonical equality failure on
`11084d86126b0a0a5851f4d8bd17b7fea3e1eb47`, Full789 run39501079650/attempt1.
Fast110 and Docs475 passed, as did unit/architecture, static and all five
PostgreSQL jobs. The API job118898431865 failed equality; its application build
step8 and deterministic generation step9 succeeded. The manifest's success
claim is scoped to those generation steps only. The failed API job/workflow
and skipped standard dictionary/compatibility stages remain explicit.

The actual generated member openapi/hidra-api.json was recovered from diagnostics
artifact12300211836, ZIP SHA256
`562dfebc21389185e2fc9750144d9d243d27378f807db4326d50e57bdb4088f0`.
Member SHA256:
`692060ef6b414abd09cd4f4099518703c49297e64c124e41e5704576ea652354`.
Sorted compact canonical snapshot SHA256:
`0464bf15e2afcdbb7c8275fe3347118a8b60da9979de27967a4938beebb39069`.
Counts are257 paths/279 operations/267 schemas. The actual additions are eight
publication routes/eight operations/nine schemas; every prior path item/schema,
non-schema component, security declaration and other top-level contract value
is unchanged, excluding only the validated generator-source SHA. No endpoint,
field or response schema was manually authored. Strict local generated equality
and the supported backward-compatibility comparison to the preceding canonical
snapshot pass. Fresh repair-SHA generation and hosted compatibility still apply.

The dictionary was independently rendered from the actual migrated PostgreSQL
catalog retained in a successful11-case SimulationEquipmentParameterRevision
PostgresIntegrationTest report in diagnostics artifact12300606228, same source/
run/attempt, successful shard3 job118898432732. ZIP SHA256:
`acce81c7576acceb07c7be4c00d64775d7211ce2a7fcd838adc75240d2cce798`.
XML member:
`surefire-reports/TEST-dz.sh.hidra.modules.simulation.infrastructure.persistence.SimulationEquipmentParameterRevisionPostgresIntegrationTest.xml`.
Its contiguous Base64/gzip CATALOG_PART sequence reconstructed catalog SHA256
`a320108f2affd477f3aaac7d9e4f76665d924b062a00439b8a1d545fc496d51c`.
The unchanged generator validates the full source inventory against an exact-SHA
checkout and reviewed ownership:158 migrations/470 JPA mappings/505 relations,
zero unresolved owners, PostgreSQL16.15. Source bundle SHA256:
`edcbccdd40dc9db169773adfdec7c1793260513d4a052a36916492ccef503152`.
Rendered dictionary SHA256:
`c3dfaa10a90a7872a5bdee22c1a5e59290135697ef20b6b25bcad14f2f19b1c7`,
identical to the test's retained verification digest. Strict local dictionary
check passes. No local database/catalog capture or production-data proof is
claimed; this uses the actual hosted disposable migrated catalog. The standard
API-lane capture/equality was skipped in Full789 and must succeed after repair.

This is the registered five-path HAPI-590C artifact reconciliation, published
with `docs(topology): reconcile governed publication artifacts`. No Java,
test/inventory, migration, workflow, grant, equality/compatibility implementation,
policy default or frozen registry state changes. The tooling obstacle is
resolved, but HAPI-590's retained PARTIAL/BLOCKED status and separate acceptance
gate remain pending. HAPI-778 is not marked accepted by this repair. Exact
repair-SHA applicable CI must run; no full workflow success or software,
engineering, deployment or operational acceptance is inferred here.

## HAPI-601 CI #1025 generated manufacturer contract reconciliation — 2026-10-31

The owner-selected Fail repair addresses [Full CI #1025](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41203839131) on executable source `07af36e5061a8b300814cc396e8c0c2cdb9cee8f`. Unit/architecture, static validation and all fourteen PostgreSQL partitions succeeded; the API job failed strict runtime snapshot equality, and the dependent full-evidence gate failed. The API application build and named runtime-generation step 9 succeeded; the API job and entire workflow did **not** pass.

The actual `openapi/hidra-api.json` member came from diagnostic artifact `13043436381` (API job `124544800340`); ZIP SHA256 `e287d10ddec9dabd51abaea81b3dc96247074a06ce6f807e5a4672c31de4d909`, JSON member SHA256 `c535a953aefe0a8c1e75b39f50a3094ed1aeeb2c4161459cca72e7f84b7b19c5`. The canonical sorted compact form has SHA256 `f9a60a38ba8cec568cffb9f2acf871545d8e0978f4fbc73da4f571aa0905f17b` and Git blob `5a3798e053ee262aec01d0f5bd24e739b2cf8f09`: 296 paths, 323 operations and 369 schemas. The new document preserves every prior path, schema, security requirement and other contract object except the generator SHA; it adds only four manufacturer path items/five methods and their 21 generated transport schemas. This was exact artifact reconstruction, not manually invented field contracts. The manifest scopes success to generation, retains previous provenance, and records that Full CI failed.

This is a documentation/manifest reconciliation within the incomplete HAPI-601 Package C source stage, not HAPI-601 engineering acceptance. Fresh exact-repair-SHA CI must confirm generated equality, real PostgreSQL dictionary capture, supported backward compatibility, and full coverage. No live industrial qualification, HAPI-602, numerical engine, OT control or deployment is admitted.
