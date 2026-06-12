# IdentityPersistenceMapper Fix Report

## Fixed issue

The following identity mappings previously passed `AuthorizationScope` directly to JPA constructors even though the JPA entities store scope as flattened columns:

- `scopeType`
- `scopeReferenceId`
- `scopeCodeSnapshot`

## Fixed mappings

- `AuthorizationDelegationGrant`
- `ExternalRoleMapping`
- `GroupRoleGrant`
- `UserPermissionGrant`
- `UserRoleGrant`

## Mapper changes

- `toEntity(...)` now maps `model.scope()` into `scopeType(model.scope())`, `scopeReferenceId(model.scope())`, and `scopeCodeSnapshot(model.scope())`.
- `toDomain(...)` now rebuilds `AuthorizationScope` using `toAuthorizationScope(entity.scopeType(), entity.scopeReferenceId(), entity.scopeCodeSnapshot())`.
- No `entity.scope()` calls remain in `IdentityPersistenceMapper`.

## Validation

```text
Identity domain + identity JPA entities + IdentityPersistenceMapper compile: PASSED
entity.scope() occurrences: 0
model.scope(), direct constructor argument occurrences: 0
```
