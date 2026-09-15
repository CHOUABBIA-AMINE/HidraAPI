from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text()

replacements = {
    "| Status | Active — AUTH-028 authentication provider deployment runbook completed |":
        "| Status | Active — AUTH-029 authentication architecture and secret guardrails completed |",
    "| AUTH-029 | `test(authentication): add authentication architecture and secret guardrails` | Enforce module boundaries, credential secrecy, and provider isolation | Planned |":
        "| AUTH-029 | `test(authentication): add authentication architecture and secret guardrails` | Enforce module boundaries, credential secrecy, and provider isolation | Completed — authentication-specific architecture/secret tests protect Identity LDAP-domain isolation, controller persistence isolation, external-provider authorization mapping isolation, platform business-model ownership, API password-hash non-exposure, externalized authentication secret properties, and embedded private-key exclusion; repository CI run 300 passed |",
    '''### AUTH-029 — Architecture and secret guardrails

Commit:

```text
test(authentication): add authentication architecture and secret guardrails
```

Protect boundaries and credentials with automated tests where practical.

Validation:

```bash
mvn -q clean verify
```

---''':
        '''### AUTH-029 — Architecture and secret guardrails

Commit:

```text
test(authentication): add authentication architecture and secret guardrails
```

Protect boundaries and credentials with automated tests where practical.

Status:

```text
Completed — AuthenticationArchitectureAndSecretGuardrailTest supplements the repository-wide ArchUnit baseline with authentication-specific checks. Identity domain code is prohibited from Spring LDAP implementation dependencies; Identity REST controllers are prohibited from persistence/Spring Data dependencies; LDAP/OIDC normalization classes are prohibited from depending on external group/role/permission mapping models; platform code is prohibited from depending on Identity User/Role/Permission domain models; ordinary Identity API Java sources are scanned for password-hash exposure; production authentication bootstrap/JWT-HMAC/LDAP-bind secret properties must remain empty or environment placeholders; and text production sources/resources are rejected if they contain embedded PEM private-key material. No production behavior or AUTH-030 closure work was introduced.
```

Validation:

```bash
mvn -q clean verify
```

Result:

```text
PASS — pull-request CI run 300 completed repository compile/tests/full verification, acceptance compile/tests/clean verify, deterministic OpenAPI publication, and artifact upload successfully for AUTH-029 implementation commit aaf9691c941b2d3182cd2f9fb4975a7397ec53ed.
```

---''',
    '''Execute only:

```text
AUTH-029 — test(authentication): add authentication architecture and secret guardrails
```

AUTH-028 now documents the live non-secret provider deployment contract for LOCAL, LDAP/Active Directory, OIDC validation, and Hidra JWT operation. Do not implement AUTH-030 or later tasks during AUTH-029.''':
        '''Execute only:

```text
AUTH-030 — docs(authentication): finalize authentication gap closure checklist
```

AUTH-029 now protects the authentication architecture/secret boundaries with executable tests and CI evidence. Do not execute work beyond AUTH-030 as part of the final closure task.'''
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap text not found:\n{old}")
    text = text.replace(old, new, 1)

path.write_text(text)
