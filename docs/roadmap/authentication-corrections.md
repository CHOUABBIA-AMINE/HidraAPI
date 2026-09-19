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

---

## AUTH-COR-002 — Propagate and verify active administrator authority

Commit:

```text
fix(authentication): validate active administrator authority across local jwt
```

Status:

```text
Completed — Active global HIDRA_ADMIN grants are validated from Identity-owned persistent state during LOCAL authentication and on each protected route check. The verified LOCAL role is propagated into HidraPrincipal, Spring authorities, and the signed Hidra JWT; stale, revoked, expired, scoped, or inactive administrator grants do not activate the wildcard bypass. Non-administrator permissions and OIDC/LDAP authentication boundaries remain unchanged. PR #116 CI run 317 passed repository compile/tests/clean verify, acceptance compile/tests/clean verify, deterministic OpenAPI publication and artifact upload on the initial implementation head ba96b40f90911ff55995723a008ee390303454ff. The final head additionally rejects wildcard JWT-scope bypass and adds a regression test; final-head CI must pass before merge.
```

Validation:

```bash
mvn -q test
mvn -q clean verify
```

Tests: active LOCAL administrator claim/authority; ordinary LOCAL user isolation; signed JWT role reconstruction; revoked/expired/scoped/inactive grants; stale bearer administrator authority rejection; ordinary permission enforcement.
