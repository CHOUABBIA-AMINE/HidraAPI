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
| `doc/architecture/RUNTIME_ARCHITECTURE.md` | CURRENT where individually stated | Existing P1 runtime architecture evidence | HPR-P2-002 adds the canonical architecture set |
| `doc/operations/**` | CURRENT where individually stated | P1 operational, HA, DR, deployment, observability and survivability evidence | Preserve; later tasks may cross-link |
| `doc/database/**` | CURRENT where individually stated | Existing P1 database HA/backup/operations evidence | HPR-P2-006 adds canonical database documentation |
| `doc/domain/**` | NOT ESTABLISHED | No canonical P2 domain set yet | HPR-P2-003 |
| `doc/modules/**` | NOT ESTABLISHED | No canonical per-module P2 set yet | HPR-P2-004 |
| `doc/api/**` | NOT ESTABLISHED | No canonical version-controlled P2 API documentation set yet | HPR-P2-005 |
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
