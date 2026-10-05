# Workbench data exposure

Status: **Proposed policy; enforcement not implemented.**  
Finding: **HID-DOC-001 — P0 — OPEN**.  
Source baseline: `d8beccedbb6373f16605540562bf134aed384edd` (reviewed 2026-10-05).  
Documentation task: DOC-001. Code remediation: [WSEC-001](../roadmap/DOCUMENTATION_GAPS.md#wsec-001--enforce-workbench-exposure-policy).

## Current behavior and evidence

The generic workbench discovers JPA entities in module packages and reflects their fields into response attributes. This creates a path from persistence internals to API output without an explicit resource or field exposure contract.

| Source at the pinned baseline | Observed behavior |
| --- | --- |
| [Workbench service](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/d8beccedbb6373f16605540562bf134aed384edd/src/main/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchService.java) | `ensureIndexed()` discovers entities from the JPA metamodel; `moduleOf()` recognizes module packages. `toRecord()` reads all declared and inherited fields. String fields become searchable; reflected fields can be used in filters and sorting. |
| [Record response](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/d8beccedbb6373f16605540562bf134aed384edd/src/main/java/dz/sh/hidra/platform/workbench/OperationalRecordResponse.java) | The response contains an arbitrary `Map<String, Object> attributes`; there is no field masking contract. |
| [Resource descriptor](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/d8beccedbb6373f16605540562bf134aed384edd/src/main/java/dz/sh/hidra/platform/workbench/OperationalResourceDescriptor.java) | Descriptors include Java type, entity/table names and searchable fields. |
| [Local credential entity](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/d8beccedbb6373f16605540562bf134aed384edd/src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java) | A module JPA entity contains `passwordHash`. Naming derives the workbench resource `local-credentials`; its fields enter the generic reflection path. |
| [Route interceptor](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/d8beccedbb6373f16605540562bf134aed384edd/src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java) and [MVC registration](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/d8beccedbb6373f16605540562bf134aed384edd/src/main/java/dz/sh/hidra/platform/configuration/HidraPermissionWebMvcConfiguration.java) | The interceptor is registered for `/api/v1/**` and derives permissions from the matched route pattern. |
| [Permission naming](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/d8beccedbb6373f16605540562bf134aed384edd/src/main/java/dz/sh/hidra/platform/permissions/HidraRoutePermissionNaming.java) | Template placeholders produce generic permission tokens rather than a permission bound to the requested concrete module/resource. The two workbench aliases also derive different resource tokens. |
| [Permission resolver](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/d8beccedbb6373f16605540562bf134aed384edd/src/main/java/dz/sh/hidra/platform/security/HidraEffectivePermissionResolver.java) | Effective permission checks allow an administrator wildcard after active global administrator confirmation. They do not redact workbench records. |
| [Security configuration](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/d8beccedbb6373f16605540562bf134aed384edd/src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java) | Normal configured security requires authentication for workbench routes. Explicitly disabled security modes change that behavior. |

This is a static source finding. It does not establish that an anonymous caller can retrieve credentials, that a deployment is vulnerable under its actual configuration, or that credentials were accessed. No production data was queried.

Authentication and route permission checks do not themselves define which persistence fields may be disclosed. A caller who passes the current checks reaches the reflection path; sensitive entity exclusion is absent there.

## Routes in scope

[Controller source](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/d8beccedbb6373f16605540562bf134aed384edd/src/main/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchController.java) maps these routes under `/api/v1`:

| Operation | Primary route | Alias |
| --- | --- | --- |
| Modules | `GET /workbench/modules` | None |
| Resources | `GET /workbench/{module}/resources` | `GET /{module}/workbench/resources` |
| List | `GET /workbench/{module}/{resource}` | `GET /{module}/workbench/{resource}` |
| Detail | `GET /workbench/{module}/{resource}/{id}` | `GET /{module}/workbench/{resource}/{id}` |
| Search | `POST /workbench/{module}/{resource}/search` | `POST /{module}/workbench/{resource}/search` |

For list/detail templates, the current primary route derives `dynamic-module:dynamic-resource:read`, while the alias derives `dynamic-module:workbench:read`. Search uses the corresponding `search` action. These are current implementation results, not the target permission contract.

## Target exposure policy

The requirements below are proposed acceptance criteria. They are not enforced by this documentation change.

1. **Default deny.** Only explicitly registered resources may be discovered, queried or returned. Adding an entity or field must not publish it automatically. Start with an empty approved registry until each entry has a reviewed business purpose and projection.
2. **Exclude credential resources.** Local credentials and other authentication secrets must never be generic workbench resources. Password hashes, passwords, tokens, API keys, private keys and secret-bearing connection settings must never appear in output, metadata, search, filtering or sorting. Administrator permissions do not override this exclusion.
3. **Approve fields separately.** Each registered resource must declare its output, searchable, filterable and sortable fields explicitly. Query fields must be subsets of approved visible fields. Identifiers require explicit approval too. Nested objects, maps and collections require safe projections; raw entity or arbitrary JSON passthrough is insufficient.
4. **Bind access to the resolved resource.** Use validated, registered module/resource identifiers from handler variables. Check the caller's concrete resource permission and applicable row scope before fetching rows or counts. Generic template permissions alone must not grant concrete resource access. Both aliases must enforce the same policy and action.
5. **Limit discovery.** Module and resource catalogs must contain only approved resources available to that caller. Descriptors may expose approved public names and query fields; Java class names, table names and hidden fields must not be exposed.
6. **Reject unsupported queries.** Unknown or unapproved query fields must fail validation before persistence queries. Denied and unregistered resources must use a consistent external response that does not reveal entity existence, row counts or secret values. Error and log output must omit sensitive values.
7. **Preserve authentication requirements.** Workbench remediation must not broaden public routes or permissions. Deployment must retain enabled security; this exposure policy is not a substitute for deployment configuration.

Dedicated module APIs remain responsible for business authorization and sensitive workflows. Existing generic workbench URLs do not justify continued credential access. Disabling unapproved resources is an intentional compatibility change; each approved replacement must be documented before release.

No resources are approved by this document. An approval entry must record the resource identifier, business purpose, explicit field sets, projection, permission/action mapping, row scope, accountable module owner and security review evidence.

## Accountability and release evidence

The platform maintainer owns generic workbench enforcement. The relevant module owner defines the safe business projection and row scope. A security reviewer verifies exclusions and authorization behavior. These are required roles; no named owner or approval has been assigned or obtained in this change.

Before release, attach the approved registry, passing focused regression results, compatibility impact and deployment security configuration verification to the remediation pull request. Keep HID-DOC-001 open until code enforcement and those checks are complete.

## Required regression coverage

| Case | Required result |
| --- | --- |
| Credential resource, including an administrator caller | Absent from module/resource catalogs; list, detail and search denied before entity reads or count queries on both aliases. |
| Unregistered entity or newly added field | Not discovered or serialized; schema evolution cannot expand the API contract. |
| Approved resource with nested secret-bearing values | Only its explicit safe projection appears; no raw nested object/map escapes. |
| Search/filter/sort against a hidden or unknown field | Rejected before a persistence query; no secret-dependent match, count or ordering is observable. |
| Correct concrete permission versus another module/resource permission | Correct grant follows the approved row scope; unrelated or generic template grants do not authorize access. |
| Both URL aliases and all operations | Same concrete permission and exposure decisions; unauthenticated and unauthorized requests remain denied in enabled security mode. |
| Descriptors, errors and logs | No secret values, hidden fields, Java type names or database table names disclosed. |
| Approved list/detail/search responses | Identical approved field contract and applicable row scope; allowed paging and count behavior verified. |

Tests must demonstrate behavior with controlled fixtures and query verification. No runtime tests or security fix are claimed by DOC-001.
