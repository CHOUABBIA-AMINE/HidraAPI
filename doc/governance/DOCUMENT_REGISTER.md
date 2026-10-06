# HidraAPI Canonical Documentation Register

## Status

CURRENT — canonical documentation register.

## Applicability

Repository: `CHOUABBIA-AMINE/HidraAPI`

Canonical root: `doc/`

Legacy/reference/evidence root: `docs/`

Execution authority: `doc/roadmap/ULTIMATE_ROADMAP.md`

Last governance verification point: HPR-P2-001 execution baseline `36924e1303b1884cd501e83f236c7cd834e338d5`; post-commit validation is recorded by the resulting GitHub Actions run.

## Ownership

Documentation authority follows the repository governance and the owner/decision authorities already recorded in individual canonical documents and the Ultimate Roadmap. No additional named document owner is established by HPR-P2-001.

## Domain Register

| Canonical area | Status at HPR-P2-001 | Authority / applicability | Next canonicalization work |
|---|---|---|---|
| `doc/README.md` | CURRENT | Repository documentation entry point and precedence/navigation control | Maintain as domains are added |
| `doc/governance/**` | CURRENT | Documentation governance, lifecycle, status and register controls | HPR-P2-001 complete |
| `doc/roadmap/ULTIMATE_ROADMAP.md` | CURRENT | Sole platform-finalization execution authority | Maintain per executed HPR |
| `doc/security/**` | CURRENT where individually stated | Canonical P0 security baseline and approved security procedures | No P2 replacement implied by HPR-P2-001 |
| `doc/architecture/**` | CURRENT where individually stated | Canonical HPR-P2-002 architecture set; `RUNTIME_ARCHITECTURE.md` is historical P1 provenance | HPR-P2-002 complete |
| `doc/operations/**` | CURRENT where individually stated | P1 operational, HA, DR, deployment, observability and survivability evidence | Preserve; later tasks may cross-link |
| `doc/database/**` | CURRENT where individually stated | Canonical HPR-P2-006 database architecture, ownership, Flyway policy and generated persistence dictionary; earlier P1 stage docs retained as provenance | HPR-P2-006 complete |
| `doc/domain/**` | CURRENT where individually stated | Canonical ubiquitous language, ownership and focused semantic baseline from current source | HPR-P2-003 complete |
| `doc/modules/**` | CURRENT | Canonical current-state documentation for all 24 implemented module roots; excludes non-implemented agents/environment/otsecurity | HPR-P2-004 complete |
| `doc/api/**` | CURRENT | Canonical version-controlled OpenAPI 3.1 contract plus overview, conventions, authentication, error-model, compatibility and OpenAPI-governance controls | HPR-P2-005 complete |
| `doc/model-remediation/**` | CURRENT | Exact-current-source reconciliation of legacy HMR/HMSR execution obligations; legacy `docs/roadmap/model-semantic-remediation.md` remains history | HPR-P2-007 complete; HPR-P2-008 executes still-required items |
| Data-governance canonical set | NOT ESTABLISHED | Retention/provenance values must not be invented | HPR-P2-010 |
| Testing canonical set | NOT ESTABLISHED | Verification documentation must remain tied to executable evidence | HPR-P2-011 |
| Documentation CI drift controls | PARTIAL | Lightweight documentation validation exists; P2 link/status/index drift controls are later scope | HPR-P2-012 |

## Status Interpretation

- `CURRENT where individually stated` means this register does not replace the document's own status or applicability statement.
- `NOT ESTABLISHED` means the later canonical set has not been created; it is not a claim that all underlying implementation is absent.
- `PARTIAL` is register-level descriptive metadata only and is not a new document-status vocabulary value for individual canonical documents.

## Historical / Legacy Estate

Everything under `docs/**` remains preserved as legacy/reference/evidence material. It can be cited for provenance after reconciliation, but it is not current platform-finalization execution authority.

Legacy material must not be mass-rewritten to match present state. Supersession changes authority, not history.

## Current vs Target Discipline

Existing implementation claims require current repository/runtime/migration/test/CI evidence. Target or deferred architecture must be labelled as such. HPR-P2-001 does not promote any P2 architecture, domain, module, API, database, data-governance or testing target to CURRENT.
