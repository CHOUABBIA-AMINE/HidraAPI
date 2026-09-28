# API / Domain Enum Exposure Policy

**Recorded:** 2026-09-28  
**Roadmap:** HRA-102  
**Purpose:** distinguish intentional REST vocabulary reuse from forbidden domain-model representation leakage.

## 1. Evidence and decision

The forensic baseline recorded **136 API-to-domain imports**, of which **125 were domain enums**.
That concentration means a rule that treats every API-to-domain import as equivalent leakage would
misclassify a large, existing category of vocabulary reuse.

HidraAPI therefore adopts the following decision:

> A REST request or response MAY expose a same-module domain enum directly when that enum is
> deliberately accepted as a stable external vocabulary. All other domain representation remains
> behind the application/API mapping boundary.

This is an exception for enum vocabulary only. It is not permission for REST code to expose
aggregates, domain records, value objects, repositories, persistence entities, or another module's
domain types.

## 2. Permitted direct enum exposure

A domain enum is eligible for direct REST exposure only when all of the following are true:

1. **Same module:** the API type and enum belong to the same bounded module.
2. **Actual Java enum:** the referenced type is declared with `enum`; package naming alone is not enough.
3. **Wire vocabulary:** its constants are meaningful external request/response values, not an internal
   implementation state that merely happens to be represented as an enum.
4. **Stable serialization:** the API intentionally serializes/deserializes the enum vocabulary and does
   not depend on persistence-only ordinals or infrastructure representation.
5. **No framework contamination:** the domain enum does not acquire Spring MVC, JPA, Jackson, or
   OpenAPI annotations merely to support the REST contract.
6. **Compatibility ownership:** changing its externally visible constants is reviewed as an API contract
   change, not as an unconstrained domain refactor.

When these conditions are satisfied, direct exposure is classified as:

`PERMITTED_SAME_MODULE_DOMAIN_ENUM_WIRE_VOCABULARY`

## 3. Forbidden API-to-domain representation

The following remain forbidden:

- API importing or returning a domain aggregate/model;
- API importing a non-enum domain record or value object as its wire representation;
- API importing another module's domain enum or other domain type;
- API exposing infrastructure/JPA types;
- API exposing an enum whose vocabulary is internal, persistence-only, security-sensitive, provisional,
  or expected to evolve independently from the external API;
- adding serialization/framework annotations to a domain type solely because REST needs a different
  representation.

These are classified as:

`FORBIDDEN_DOMAIN_REPRESENTATION_LEAKAGE`

## 4. When to map instead of expose

Use an API-owned enum or string plus an API mapper when any of these applies:

- API and domain lifecycles may diverge;
- compatibility aliases or deprecated wire values are required;
- the external vocabulary is defined by an external protocol or standard;
- multiple API versions need different vocabularies;
- the domain enum contains internal states that must not be public;
- the public token differs from the Java constant name;
- backward-compatible parsing requires values the domain no longer models;
- a cross-module boundary is involved.

Mapping may be explicit or compile-time generated, but compatibility decisions stay in the API
boundary and must not be pushed into the domain model.

## 5. Compatibility rules for directly exposed enums

For a directly exposed domain enum, its serialized constant names form part of the versioned API
contract.

Therefore:

- renaming or removing an exposed constant is a breaking API change unless a compatibility layer is
  introduced first;
- changing a constant's public meaning is a contract change even if the Java name is unchanged;
- adding a constant requires API compatibility review because strict clients may not tolerate unknown
  values;
- persistence representation must not dictate the REST token;
- enum declaration order is not an API contract and must never be used as a wire ordinal.

## 6. Architecture guardrail classification

A repository guardrail evaluating an import from
`dz.sh.hidra.modules.<api-module>.api..` to a domain type SHOULD apply this order:

1. Resolve the referenced Java type.
2. If the target belongs to a different business module: **forbid**.
3. If the target is not an actual Java enum: **forbid**.
4. If the target is a same-module enum: classify it as
   `PERMITTED_SAME_MODULE_DOMAIN_ENUM_WIRE_VOCABULARY`, subject to this policy.
5. Any explicit policy exception narrower than this rule must be named and documented; generic
   package-wide exceptions are not allowed.

Illustrative classifier:

```text
API -> same module -> actual enum      = permitted vocabulary exposure
API -> same module -> non-enum domain = forbidden representation leakage
API -> other module -> domain type    = forbidden cross-module leakage
API -> infrastructure/JPA             = forbidden layer leakage
```

This classification is deliberately structural so ArchUnit/static analysis can distinguish the
permitted enum category from domain-model leakage without guessing from class names.

## 7. Application boundary interaction

Application code may depend on its own module's domain enums because dependency direction remains
`Application -> Domain`.

REST code must still use application use cases/contracts rather than bypassing the application layer.
Direct enum reuse in an API request/response does not authorize controllers to call domain services or
construct domain aggregates when that work belongs in application mapping/orchestration.

## 8. Review checklist

Before exposing a new domain enum directly through REST, confirm:

- same module;
- actual enum;
- public vocabulary is intentional;
- constant names are acceptable wire tokens;
- no internal-only constants are exposed;
- compatibility impact is documented;
- OpenAPI reflects the vocabulary;
- no framework annotations are added to the domain enum.

If any answer is no, introduce an API-owned representation and map it at the boundary.
