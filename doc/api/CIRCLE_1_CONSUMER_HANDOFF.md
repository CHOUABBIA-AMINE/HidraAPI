# Circle 1 HidraAPI consumer handoff

## Status

CURRENT — development API contract and disposable GZ2 qualification runbook. HidraAPI Integration and QA maintain this handoff with the Circle 1 module owners. The [T39 roadmap entry](../roadmap/ROADMAP.md#close-t38-and-register-the-t39-circle-1-consumer-handoff--2026-11-22) controls acceptance. [OpenAPI](openapi.yaml) is the generated transport contract. [Authentication and authorization](AUTHENTICATION_AUTHORIZATION.md) and the [error model](ERROR_MODEL.md) apply.

## Route contract

The table names the OpenAPI operation and its request → successful response schema. Names refer to `components.schemas` in the pinned JSON. Query/path fields and media types are defined by the listed operation. A dash on the left denotes a request without a body. An asterisk marks a source-defined success DTO whose operation currently omits a generated 2xx schema. All protected calls use the signed Hidra bearer token.

| Method | Route | Operation ID | Body → success schema |
|---|---|---|---|
| `POST` | `/api/v1/identity/authentication/login` | `login` | AuthenticationLoginRequest → AuthenticationLoginResponse |
| `GET` | `/api/v1/identity/me/regional-access/{resourceKind}/{resourceId}` | `current` | — → RegionalAccessResponse |
| `GET` | `/api/v1/telemetry/assets/{assetType}/{assetId}/points` | `listAssetScopedTelemetryPoints` | — → CommissionedPointsView |
| `GET` | `/api/v1/telemetry/assets/{assetType}/{assetId}/points/{pointId}/trusted-readings/history` | `listAssetScopedTrustedObservationHistory` | — → ObservationHistoryView |
| `GET` | `/api/v1/telemetry/assets/{assetType}/{assetId}/points/{pointId}/trusted-readings/latest` | `readLatestAssetScopedTrustedObservation` | — → LatestObservationView |
| `GET` | `/api/v1/topology/operating-constraints/pipelines/{pipelineId}` | `pipeline_1` | — → QualifiedOperatingConstraintView |
| `POST` | `/api/v1/topology/operating-constraints/pipelines/{pipelineId}/restriction-requests` | `submitPressureRestrictionRequest` | PressureRestrictionSubmission → PressureRestrictionRequest |
| `GET` | `/api/v1/topology/operating-constraints/pipelines/{pipelineId}/restriction-requests/{requestId}` | `getPressureRestrictionRequest` | — → PressureRestrictionRequest |
| `GET` | `/api/v1/topology/operating-constraints/pipelines/{pipelineId}/restriction-requests/{requestId}/evidence` | `getPressureRestrictionDecisionEvidence` | — → PressureRestrictionDecisionEvidence |
| `POST` | `/api/v1/topology/operating-constraints/pipelines/{pipelineId}/restriction-requests/{requestId}/publication` | `publishPressureRestrictionRequest` | PressureRestrictionPublicationCommand → PressureRestrictionPublicationReceipt |
| `GET` | `/api/v1/topology/operating-constraints/pipelines/{pipelineId}/sections/{sectionId}` | `section_1` | — → QualifiedOperatingConstraintView |
| `GET` | `/api/v1/topology/pipeline-dossiers/{pipelineId}` | `pipeline` | — → PipelineView |
| `GET` | `/api/v1/topology/pipeline-dossiers/{pipelineId}/baseline` | `baseline` | — → BaselineView |
| `GET` | `/api/v1/topology/pipeline-dossiers/{pipelineId}/connections` | `connections` | — → ConnectionsPage |
| `GET` | `/api/v1/topology/pipeline-dossiers/{pipelineId}/equipment` | `equipment` | — → EquipmentPage |
| `GET` | `/api/v1/topology/pipeline-dossiers/{pipelineId}/sections` | `sections` | — → SectionsPage |
| `GET` | `/api/v1/topology/pipeline-dossiers/{pipelineId}/sections/{sectionId}` | `section` | — → SectionView* |
| `GET` | `/api/v1/topology/regional-map/geojson` | `geoJson` | — → FeatureCollection |
| `GET` | `/api/v1/topology/regional-map/layers` | `layers` | — → LayerDescriptor[] |
| `GET` | `/api/v1/topology/regional-map/layers/{layerId}` | `layer` | — → LayerDescriptor* |
| `GET` | `/api/v1/topology/regional-map/layers/{layerId}/features` | `features` | — → FeatureCollection |
| `GET` | `/api/v1/topology/regional-map/layers/{layerId}/features/{entityId}` | `feature` | — → Feature* |
| `GET` | `/api/v1/topology/regional-map/search` | `search_2` | — → SearchResult |
| `GET` | `/api/v1/workflow/definitions` | `definitions_1` | — → Definition[] |
| `POST` | `/api/v1/workflow/instances` | `startWorkflowInstance` | StartWorkflowInstanceRequest → WorkflowInstanceResponse |
| `GET` | `/api/v1/workflow/instances/{id}` | `instance` | — → InstanceView |
| `GET` | `/api/v1/workflow/instances/{id}/timeline` | `timeline` | — → TimelineEntry[] |
| `POST` | `/api/v1/workflow/tasks` | `createWorkflowTask` | CreateWorkflowTaskRequest → WorkflowTaskResponse |
| `GET` | `/api/v1/workflow/tasks/{id}` | `task` | — → TaskView |
| `GET` | `/api/v1/workflow/tasks/{id}/available-actions` | `availableActions` | — → AvailableActionView[] |
| `POST` | `/api/v1/workflow/tasks/{taskId}/transitions/{transitionId}/execute` | `execute` | ExecuteWorkflowTransitionRequest → WorkflowTransitionExecutionResponse |

## Navigation and authority

1. Sign in using the LOCAL provider and use `accessToken` from login as the bearer token. Resolve regional scope through Identity's current resource check when needed. For the listed owner calls, route admission is only one condition. Identity evaluates current grant/deny decisions, Organization supplies effective employee responsibilities, and Topology resolves actual company and resource ancestry. A role label or previous UI decision grants no continuing access.
2. Load regional layers or search by text. For a pipeline, use the `pipelines` layer and take `features[].properties.entityId` for detail and dossier calls. The outer `id` is namespaced and is not the dossier ID. GeoJSON uses longitude then latitude. Use `page` (zero-based) and `size` on paged map routes, and render `totalFeatures`, `totalPages` and `hasNext` as returned. Filters, aggregation and geometry are scope-qualified. Empty collections or unavailable geometry are normal.
3. Read the dossier and its sections, connections, equipment and baseline under the same `companyPartyId`. Use dossier `items[].id` for section navigation and an equipment item's `facilityId` for Telemetry only when present. Join its `sectionId` to the section item. Dossier pages use `page`, `size`, `totalElements`, `totalPages` and `hasNext`. Render quantities with their units, availability and source metadata. A baseline receipt is for the pipeline system. Preserve its receipt ID, epoch and manifest digest. A current baseline may supersede the source cited by a historical restriction.
4. Read the pipeline or exact section constraint with `conditionCode` and UTC `effectiveAt`. Show approved upper pressure only for `QUALIFIED`, with `pressureUnit`, `pressureBasis`, interval and source receipt/revision as returned. `UNAVAILABLE` and `CONFLICT` carry no usable pressure. A pipeline restriction does not inherit into a section. Keep dossier design pressure separate from qualified operating pressure.
5. Read commissioned points using `FACILITY` and the dossier's linked `facilityId`. Discovery accepts UTC `at` and the exclusive `afterPointId` cursor. Read latest/history for a returned `pointId`. History is ordered by descending observation time and trusted ID, uses a half-open `[from, until)` UTC window, and uses the paired `afterObservedAt` and `afterTrustedId` cursor. Set `maxAgeSeconds` explicitly (1–604800). Latest uses the current valid binding; history uses the bound revision valid at the relevant instant and still checks current reader authority. Distinguish `FRESH`, `STALE` and `UNAVAILABLE`. Preserve source, raw/trusted/assessment identifiers, observed time, binding validity, measured unit and assessment/quality metadata.
6. Submit an `INITIAL`, `REPLACEMENT` or `WITHDRAWAL` with the exact scope, baseline receipt and expected stream version. Use the returned `requestId`, `candidateId` and `workflowLocator`. For the exact submitted candidate, use the configured target type/purpose IDs with Workflow definition discovery, and use its first step, assignment choices and decisions. Start the instance with the chosen definition/version and candidate/locator. Create the review task for an authorized independent reviewer. Query the task and available actions before executing the selected transition with its actual `updatedAt`. Each replacement candidate requires its own exact approval. An approval action does not publish a restriction.
7. Publish the owner outcome with one exact candidate, instance, task and action tuple per required approval, and the matching expected version. Read the owner request and evidence under the same target scope. Retain the publication receipt and decision/Audit IDs from the evidence response. This handoff uses Topology's scoped evidence, so it has no dependency on a separate Audit search permission. Read the effective restriction at the desired instant. A withdrawn revision stays historical.

| Surface | Authorization and client handling |
|---|---|
| Regional access, map and dossier | `topology:regional-resource:read` with current regional responsibility. The Identity regional-access endpoint also requires `identity:me:read`. Map adds `topology:regional-map:read` or `topology:regional-map:search` as applicable. Dossier requires `topology:pipeline-dossiers:read`. Linked facilities, equipment and sections must also be visible. Baseline reads require current protected baseline read authority. |
| Qualified restriction read | `topology:operating-constraints:read` and current regional/owner-source read authority. No applicable approved source returns the applicable unavailable state. |
| Trusted observations | `telemetry:assets:read`, current regional asset scope and trusted-reading read authority for the company and commissioned source point. No observation under an admitted binding yields `UNAVAILABLE` for latest or an empty history. |
| Governed restrictions | `topology:operating-constraints:execute` for writes or `topology:operating-constraints:read` for reads, plus current `topology:pressure-restriction:submit`, `review`, `publish` or `evidence-read` as applicable to the actor and scope. Workflow additionally checks instance/task admission and actor eligibility. Workflow discovery requires `workflow:definitions:read` and returns eligible definitions and configured independent assignees for the exact target. |
| Errors and caching | Handle 401 as missing/invalid authentication and 403 as denied route/authority where the route returns it. Protected detail can return opaque 404 for a missing or hidden object. Treat 400 as invalid input. Restriction writes can return 409 on stale version, invalid/reused decision or rolled-back contention. Re-read authorized state before retrying. Do not interpret an unknown write outcome as a rejection. Respect `Cache-Control` on protected reads, including the constraint, trusted observation and decision responses marked private/no-store. Clear scoped UI state after authority changes. |

## Exact-SHA evidence and consumer pin

Choose the successful **HidraAPI CI** run for the T39 implementation commit, and verify the Full Verification Gate for the same SHA and run attempt. Use `hidra-full-ci-evidence-<sha>-<run-id>-<attempt>`. Compare its ZIP SHA-256 to the GitHub Actions artifact digest, and verify the source SHA, run ID and attempt in `gate.json`. Verify the extracted `files/target/openapi/hidra-api.json` from its API-contract lane against `api.current_api_sha256` in the gate receipt, and check `x-hidra-ci-source-sha` in that JSON. Compare the canonical API file using the repository snapshot validator with that generated member and SHA. Use that raw JSON for the HidraWEB pin and record the commit/run/artifact identities and raw digest together.

In the same bundle, find the shard-1 `reports/TEST-dz.sh.hidra.qualification.Gz2SignedWorkflowNativeHttpPostgresIntegrationTest.xml`. Read the single line in `system-out` beginning with `HIDRA_CIRCLE1_HANDOFF_V1 `. The suffix is JSON. The version-1 object has `roles` (synthetic login usernames), `scope` (company, pipeline, section and linked facility identifiers), `limits` (explicit effective-time routes and original/current baseline receipt IDs), `observation` (point, lineage and latest/history routes), `decision` (owned request/publication, candidate, Workflow and Audit handles), `currentPublication` (the winning stream version and revision), `workflowBinding` and the asserted `checks` list. Only the successful report from the verified bundle constitutes execution evidence. Treat locators as run-specific.

## Local loopback replay

Use a POSIX development shell with Java 21, Docker and the Maven wrapper. Run from the HidraAPI repository root at the accepted T39 implementation SHA. Leave the first terminal running. The fixture creates and migrates a fresh PostgreSQL container, seeds the signed GZ2 journey and then keeps the restarted API available on loopback for up to ten minutes.

```sh
replay_dir="$(mktemp -d)"
chmod 700 "$replay_dir"
printf 'Replay directory %s\n' "$replay_dir"
HIDRA_CIRCLE1_REPLAY_DIR="$replay_dir" ./mvnw -B -q test \
  -Dtest=Gz2SignedWorkflowNativeHttpPostgresIntegrationTest
```

In another terminal, use the printed path and wait for the replay-ready message and `circle1-local.json`. Read the localhost `baseUrl`, `providerType`, synthetic `password` and the run-specific `receipt` from that mode-0600 file. A local HidraWEB development proxy can forward same-origin API calls to this loopback URL. Authenticate separately for each username in `receipt.roles`. The primary reader can browse GZ2, the sibling reader sees the disjoint same-company region, the foreign reader sees their own company, and the revoked reader remains denied. Use the author for published request/evidence, the reviewer for independent review, and the receipt's explicit times to view historical state after restart. The example request is already published and its first revision is historical. For a new submission, use a new UUID, current stream version, current baseline and applicable predecessor, and obtain fresh decisions. The historical pressure and observation routes in the receipt are stable within that replay; live freshness is evaluated at each request.

Delete `circle1-local.json` to release the fixture early or let the bound expire. The suite closes the child JVM and container and removes the credential file. Remove the empty directory after the command exits. Keep the credential file local. CI disallows the replay opt-in and emits only the credential-free receipt. The temporary runtime and synthetic actors qualify development behavior. HidraWEB records its independent browser result against its pinned API and consumer SHAs.
