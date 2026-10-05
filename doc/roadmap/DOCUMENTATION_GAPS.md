# Documentation gaps and controlled tasks

This roadmap starts the canonical `doc/` workstream. Existing roadmaps in `docs/roadmap/` retain their historical scope and status. Execute one task code at a time; do not automatically execute the next code.

Baseline: `d8beccedbb6373f16605540562bf134aed384edd`.  
Open finding: **HID-DOC-001 (P0)** — generic workbench persistence-field exposure.  
Policy: [Workbench data exposure](../security/WORKBENCH_DATA_EXPOSURE.md).

| Code | Status | Completion note |
| --- | --- | --- |
| DOC-001 | Completed | Policy and controlled follow-up documented; Markdown structure, relative links, anchors and pinned source paths checked. |
| WSEC-001 | Planned | Code enforcement and runtime regression coverage not started. |

## DOC-001 — define the workbench exposure policy

Status: **Completed (documentation only)**.  
Exact commit message: `docs(security): define workbench data exposure policy`.

Authorized scope: document current evidence and target controls, establish the canonical index, and register a testable follow-up code task. No implementation, existing roadmap edits, deployment or merge is included.

Write allowlist:

- `doc/README.md`
- `doc/security/WORKBENCH_DATA_EXPOSURE.md`
- `doc/roadmap/DOCUMENTATION_GAPS.md`

Acceptance and validation:

- Pinned source evidence identifies reflection-based output and the credential entity.
- Both route aliases and their current permission derivation are documented.
- Target controls are distinguished from current behavior; HID-DOC-001 remains OPEN.
- Relative links and pinned source paths resolve against the repository inventory.
- Only the three allowlisted Markdown files are added.
- Markdown structural checks and source-path/link checks passed locally.
- Runtime tests were not run: this task changes documentation only and does not enforce the policy.

Completion refers to the documentation deliverable. Review and merge remain separate from task completion.

## WSEC-001 — enforce workbench exposure policy

Status: **Planned; not executed**.  
Priority: **P0**.  
Exact commit message: `fix(security): enforce explicit workbench exposure policy`.  
Prerequisite: review the proposed policy and approve any initial resource registrations and concrete permission mappings. Until registrations are approved, the registry must remain empty and deny access.

Implement a default-deny resource registry and explicit field projections; exclude credential resources for all callers; bind authorization and discovery to resolved concrete resources; enforce alias parity and query-field validation. Add the regression coverage specified in the [policy](../security/WORKBENCH_DATA_EXPOSURE.md#required-regression-coverage). Keep deployment verification and approvals visible in the remediation PR.

Write allowlist for this future task:

- `src/main/java/dz/sh/hidra/platform/workbench/*.java`
- `src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java`
- `src/main/java/dz/sh/hidra/platform/permissions/HidraRoutePermissionNaming.java`
- `src/main/java/dz/sh/hidra/platform/permissions/HidraRoutePermissionCatalogService.java`
- `src/test/java/dz/sh/hidra/platform/workbench/*.java`
- `src/test/java/dz/sh/hidra/platform/permissions/*.java`
- `doc/security/WORKBENCH_DATA_EXPOSURE.md`
- `doc/roadmap/DOCUMENTATION_GAPS.md`

The wildcard entries cover files directly in those packages, not unrelated packages. No entity, schema, migration, authentication configuration or build dependency change is authorized by this task. If implementation needs a broader scope, update and review the task before proceeding.

Acceptance criteria:

1. Credential and unregistered resources are unavailable in discovery, list, detail and search, including administrator requests, before data/count queries.
2. Output and query field sets are explicit; new fields and unsafe nested values cannot leak.
3. Concrete authorization, row scope and both aliases pass positive and negative tests; existing non-workbench permission behavior remains covered.
4. Approved projections and catalog metadata contain no persistence internals or secret fields; error/log paths contain no secret values.
5. Focused workbench and permission tests plus required repository checks pass with recorded commands and results.
6. Compatibility impact and enabled deployment security configuration are verified before release.

Do not mark WSEC-001 Completed or close HID-DOC-001 based on this documentation commit.
