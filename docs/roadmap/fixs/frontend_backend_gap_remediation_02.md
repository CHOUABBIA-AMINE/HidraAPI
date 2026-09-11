# Frontend ↔ Backend Gap Remediation 02

```text
Roadmap code    : FRONTEND-BACKEND-GAP-002
Commit message  : FRONTEND-BACKEND-GAP-002
Source register : CHOUABBIA-AMINE/HidraWEB/docs/roadmap/HidraAPI-Frontend-Backend-Gap-Register.md
Backend baseline: d21ea9184ce2fede9b2e8f39d5fd9f374b4b2046
Branch          : frontend-backend-gap-remediation-02
Status          : In Progress
```

## Objective

Close or explicitly disposition every confirmed backend-owned gap currently recorded by HidraWEB, without moving business rules to the frontend and without violating HidraAPI module ownership.

This is one cross-cutting remediation task because the consumer register is the acceptance checklist. Each change remains physically owned by its existing module.

## Gap acceptance checklist

| Gap | Backend action in this task | Status |
|---|---|---|
| GAP-SEC-001 | Verify and harden authenticated principal contract for both Basic and JWT authentication. | In Progress |
| GAP-SEC-002 | Publish repository-side browser OIDC/JWT contract and configuration metadata; external IdP registration remains environment-owned. | In Progress |
| GAP-SEC-003 | Enforce effective Hidra permissions at the backend route boundary and publish effective principal permissions. | In Progress |
| GAP-CONTRACT-001 | Produce deterministic OpenAPI artifact in CI and retain it as a workflow artifact. | In Progress |
| GAP-REALTIME-001 | Publish a typed realtime event envelope/catalog with destinations and recovery semantics. | In Progress |
| GAP-ID-001 | Verify dedicated identity read/query API merged by remediation 01. | In Progress |
| GAP-ID-002 | Verify and harden role/permission/grant administration contracts merged by remediation 01. | In Progress |
| GAP-ORG-001 | Add dedicated organization hierarchy/employee/assignment read APIs. | In Progress |
| GAP-TOPO-001 | Verify strongly typed map responses merged by remediation 01. | In Progress |
| GAP-TOPO-002 | Verify pipeline-system and pipeline map layers merged by remediation 01. | In Progress |
| GAP-TOPO-003 | Verify count-aware paging/filter-before-slice semantics merged by remediation 01. | In Progress |
| GAP-TEL-001 | Add telemetry reading/history/trend query contract. | In Progress |
| GAP-TEL-002 | Add telemetry reading-state and quality reference contract. | In Progress |
| GAP-MON-001 | Add monitoring rule/deviation read/query contract. | In Progress |
| GAP-WF-001 | Add authenticated-principal task inbox/query contract. | In Progress |
| GAP-WF-002 | Add workflow instance/task timeline/history query contract. | In Progress |
| GAP-WF-003 | Add backend-provided available-action/transition metadata. | In Progress |
| GAP-ALARM-001 | Add active/history/detail alarm query contracts. | In Progress |
| GAP-ALARM-002 | Add audited alarm shelving/unshelving contract when supported by the existing alarm model; otherwise document the model limitation explicitly. | In Progress |

## Architecture rules

- API controllers expose DTOs/application-port records only, never JPA entities.
- Domain packages stay free of Spring, JPA, REST, and foreign aggregate classes.
- Query adapters live in the owning module infrastructure package.
- Cross-module references remain opaque IDs.
- Workflow owns process state and transition availability; target modules own business facts.
- Authentication transport remains platform-owned; identity owns user/role/permission meaning.
- No frontend-driven authorization is trusted as an enforcement boundary.

## Validation

Required before completion:

```bash
./mvnw -B -q -DskipTests compile
./mvnw -B -q test
./mvnw -B -q clean verify
```

Additionally verify:

- application context starts in existing integration tests;
- architecture guardrails remain green;
- `/v3/api-docs` contains the new contracts;
- CI publishes the OpenAPI artifact;
- all repository-owned gaps above have implementation evidence;
- external IdP registration/configuration is clearly separated from repository-owned work.

## Completion evidence

To be filled after implementation and CI verification.
