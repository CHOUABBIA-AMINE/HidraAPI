# HidraAPI Assets Roadmap

```text
Roadmap file : docs/roadmap/assets.md
Roadmap code : AST
Scope        : Assets bounded context public contracts required by HWEB engineering workflows
Repository   : HidraAPI
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-09-12
Status       : Active
```

---

## 1. Purpose

This roadmap is the execution source of truth for frontend-facing `assets` contracts. Assets owns maintainable assets, condition evidence, maintenance work orders, maintenance metadata, and asset lifecycle evidence. Topology, organization, party, workflow, documents, risk, and incident remain separate bounded contexts and are referenced only through neutral identifiers/snapshots or public application contracts.

Frontend-facing contracts must be deterministic in OpenAPI. Route permissions are derived by the platform in canonical `<module>:<resource>:<action>` form and backend authorization remains authoritative.

---

## 2. Execution sequence

| Code | Commit message | Status | Scope |
|---|---|---:|---|
| `AST-001` | `feat(assets): publish maintainable asset concurrency contract` | In Progress | Close `GAP-ENG-001` / issue #79 by publishing a typed maintainable-asset read plus one metadata-only PATCH protected by explicit `expectedUpdatedAt`. Only `assetName` is mutable. Stale writes return deterministic `409 ASSETS_MAINTAINABLE_ASSET_CONFLICT`; retry requires refetch. No lifecycle/status/topology/maintenance mutation is introduced. |

---

## 3. AST-001 public contract

```text
GET   /api/v1/assets/maintainable-assets/{assetId}
PATCH /api/v1/assets/maintainable-assets/{assetId}
```

PATCH request:

```json
{
  "expectedUpdatedAt": "<MaintainableAssetResponse.updatedAt>",
  "assetName": "<new non-blank asset name>"
}
```

The typed GET and successful PATCH response return the maintainable asset plus `updatedAt`. `MaintainableAsset.updatedAt` is explicitly promoted to the write precondition for this mutation only.

The PATCH operation:

- acquires a pessimistic write lock on the assets-owned maintainable-asset row;
- reloads the aggregate inside one transaction;
- compares `expectedUpdatedAt` exactly with the persisted `updatedAt` token;
- changes only `assetName`;
- preserves lifecycle status, topology references, criticality, ownership/manufacturer references, installation/commissioning timestamps, and all other asset fields;
- saves with a strictly refreshed `updatedAt` token.

A stale token returns HTTP 409 with problem code:

```text
ASSETS_MAINTAINABLE_ASSET_CONFLICT
```

The conflict response instructs the client to refetch before retrying. HidraWEB must not merge/rebase stale changes locally or infer a replacement token from unrelated timestamps.

Canonical permissions are backend-derived from the published route descriptors:

```text
GET   -> assets:maintainable-assets:read
PATCH -> assets:maintainable-assets:update
```

HidraWEB must consume `GET /api/v1/security/permissions/routes` and intersect it with `GET /api/v1/identity/me/permissions`; backend authorization remains final.

### Boundary evidence

- Assets application code accesses only `MaintainableAssetRepositoryPort`.
- Persistence locking remains inside the assets infrastructure adapter/repository.
- No foreign module entity, repository, infrastructure, or domain import is introduced.
- No lifecycle transition is inferred from `AssetLifecycleStatus`.
- No asset lifecycle event is fabricated because AST-001 changes metadata only, not lifecycle state.
- No optimistic-lock token is invented outside the explicit `updatedAt` precondition defined here.

---

## 4. Validation

Required before AST-001 completion:

```bash
./mvnw -B -q -DskipTests compile
./mvnw -B -q test
./mvnw -B -q clean verify
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

Additionally verify:

- focused tests prove current-token success and stale-token conflict;
- conflict HTTP mapping is deterministic and frontend-consumable;
- `/v3/api-docs` contains the typed GET, PATCH request/response, and required `expectedUpdatedAt` field;
- CI publishes an exact-head and merge-SHA OpenAPI artifact;
- issue #79 may close only after merge-SHA verification is green.
