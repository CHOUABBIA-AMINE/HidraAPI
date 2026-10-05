# Documentation gaps and controlled tasks

This roadmap starts the canonical `doc/` workstream. Existing roadmaps in `docs/roadmap/` retain their historical scope and status. Execute one task code at a time; do not automatically execute the next code.

Finding baseline: `d8beccedbb6373f16605540562bf134aed384edd`.
Open finding: **HID-DOC-001 (P0)** — source controls implemented; release evidence still pending.
Policy: [Workbench data exposure](../security/WORKBENCH_DATA_EXPOSURE.md).

| Code | Status | Completion note |
| --- | --- | --- |
| DOC-001 | Completed | Policy and controlled follow-up documented; Markdown structure, relative links, anchors and pinned source paths checked. |
| WSEC-001 | Completed | Source controls and regression tests implemented; clean verify passed (504 passed, 35 Docker-dependent tests skipped). Release evidence remains WSEC-002. |
| WSEC-002 | Planned | Record exact-commit CI, security review and deployment configuration evidence before closing the finding. |

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

Status: **Completed (source implementation; release verification remains WSEC-002)**.
Priority: **P0**.
Exact commit message: `fix(security): enforce explicit workbench exposure policy`.
Prerequisite: review the proposed policy and approve any initial resource registrations and concrete permission mappings. Until registrations are approved, the registry must remain empty and deny access.

Implement a default-deny resource registry and explicit field projections; exclude credential resources for all callers; bind authorization and discovery to resolved concrete resources; enforce alias parity and query-field validation. Add the regression coverage specified in the [policy](../security/WORKBENCH_DATA_EXPOSURE.md#required-regression-coverage). Keep deployment verification and approvals visible in the remediation PR.

Write allowlist (the canonical index is included to keep its enforcement status consistent):

- `src/main/java/dz/sh/hidra/platform/workbench/*.java`
- `src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java`
- `src/main/java/dz/sh/hidra/platform/permissions/HidraRoutePermissionNaming.java`
- `src/main/java/dz/sh/hidra/platform/permissions/HidraRoutePermissionCatalogService.java`
- `src/test/java/dz/sh/hidra/platform/workbench/*.java`
- `src/test/java/dz/sh/hidra/platform/permissions/*.java`
- `doc/README.md`
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

### WSEC-001 implementation and validation record

Implementation baseline: `b4d9b4ceec2dbe281bb9cc93b0700f4cc03d0f57`, on the existing documentation branch. One code task is implemented; no initial adapters are approved or enabled. The canonical index was added to this task's documentation allowlist to keep its enforcement status accurate.

Created:

- `src/main/java/dz/sh/hidra/platform/workbench/WorkbenchResourceDefinition.java`
- `src/main/java/dz/sh/hidra/platform/workbench/WorkbenchResource.java`
- `src/main/java/dz/sh/hidra/platform/workbench/WorkbenchResourceRegistry.java`
- `src/main/java/dz/sh/hidra/platform/workbench/WorkbenchExceptionHandler.java`
- `src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchServiceTest.java`
- `src/test/java/dz/sh/hidra/platform/workbench/WorkbenchResourceDefinitionTest.java`
- `src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchControllerSecurityTest.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraWorkbenchPermissionCatalogTest.java`

Updated: workbench service and resource descriptor; route authorization interceptor and permission catalog; canonical index, security policy and this roadmap. The permission naming implementation is unchanged and no longer used to grant workbench template access.

Validation on 2026-10-05:

- Java 21.0.2 and Maven wrapper 3.9.11 used without repository build/dependency changes. Temporary proxy settings enabled dependency resolution. An explicit Mockito 5.23.0 agent was supplied locally because self-attachment is unavailable in this container.
- Compile: `bash mvnw -B -q -DskipTests compile` (with temporary Maven proxy settings) passed.
- Focused workbench and existing permission suites: `bash mvnw -B -q -DargLine="-javaagent:<Mockito jar>" -Dtest=HidraOperationalWorkbenchServiceTest,WorkbenchResourceDefinitionTest,HidraOperationalWorkbenchControllerSecurityTest,HidraWorkbenchPermissionCatalogTest,HidraRouteAuthorizationInterceptorTest,HidraEffectivePermissionResolverTest,AlarmSuppressionRoutePermissionTest test` passed: 33 tests, zero failures/errors/skips. Subsequent fixed-instance error coverage is included in the final full verification below.
- Final repository verification: `bash mvnw -B -q -DargLine="-javaagent:<Mockito jar>" clean verify` (with temporary Maven proxy settings) passed: 539 tests discovered, 504 passed, 35 skipped, zero failures/errors. The skipped tests require Docker, unavailable locally. Packaging completed successfully. This is not a claim that the Docker-dependent tests passed.
- `git diff --check` and local Python checks for allowlisted scope, canonical Java headers, Markdown links and anchors passed.

Deployment configuration and exact-commit hosted CI/OpenAPI evidence are not covered by the local result. They remain release gates in WSEC-002. No deployment, merge, release tag or new pull request is performed by this task. HID-DOC-001 remains OPEN.

## WSEC-002 — verify workbench release evidence

Status: **Planned; not executed**.
Exact commit message: `docs(security): record workbench release verification`.

Record exact-commit repository CI and OpenAPI publication results, including the Docker-dependent tests skipped locally. Obtain the security review and verify enabled security configuration in the intended deployment before rollout. Keep the generic registry empty. Any resource enablement needs a separate module-owned task with its reviewed projection, field sets, grants and row-scope tests; this task does not authorize registrations or deployment.

Write allowlist:

- `doc/security/WORKBENCH_DATA_EXPOSURE.md`
- `doc/roadmap/DOCUMENTATION_GAPS.md`

Do not invent named approvals or deployment evidence. Leave HID-DOC-001 OPEN and record a blocker if a review or target deployment is unavailable. This task must not change code, credentials, runtime configuration or release tags.
