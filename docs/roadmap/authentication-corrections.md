# HidraAPI Authentication Corrections Roadmap

## Document control

```text
Repository     : CHOUABBIA-AMINE/HidraAPI
Area           : authentication / route authorization
CreatedOn      : 2026-09-17
Execution mode : one corrective commit code at a time
```

This roadmap records post-AUTH-030 corrective work discovered by runtime integration testing. It does not reopen the authentication architecture or add a new provider capability.

---

## AUTH-COR-001 — Allow direct login before route authorization

Commit:

```text
fix(authentication): allow direct login before route authorization
```

### Problem

Spring Security already marks:

```text
POST /api/v1/identity/authentication/login
```

as `permitAll`, but `HidraRouteAuthorizationInterceptor` runs afterward and derives `identity:authentication:execute` for the still-anonymous caller. The request is therefore rejected before LOCAL/LDAP/AD credential authentication can establish a Hidra principal.

Observed runtime evidence:

```text
AnonymousAuthenticationFilter - Set SecurityContextHolder to anonymous SecurityContext
FilterChainProxy - Secured POST /api/v1/identity/authentication/login
Missing required permission: identity:authentication:execute
```

### Required change

```text
- preserve Spring Security permitAll for the direct-login endpoint
- exempt only /api/v1/identity/authentication/login from Hidra route-permission enforcement before authentication
- preserve OIDC completion authentication requirements
- preserve permission enforcement for ordinary protected /api/v1 routes
- do not grant authentication permissions to anonymous users
- do not change provider routing, password verification, token issuance, or authorization ownership
```

### Tests

```text
anonymous direct login -> interceptor permits request to reach authentication boundary
anonymous ordinary protected route -> interceptor still denies without effective permission
existing matching-permission behavior remains green
```

### Validation

```bash
mvn -q test
mvn -q clean verify
```

### Status

```text
Completed — HidraRouteAuthorizationInterceptor now exempts only the direct-login path from pre-authentication route-permission enforcement. Anonymous protected routes remain denied. PR CI run 314 passed repository compile/tests/full verification, acceptance compile/tests/clean verify, deterministic OpenAPI publication, and artifact upload on implementation head 4d73635a2e37c20cbed415bed0a0c8189e6afef2.
```
