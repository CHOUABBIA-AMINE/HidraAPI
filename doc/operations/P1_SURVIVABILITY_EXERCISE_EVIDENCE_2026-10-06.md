# P1 Survivability Exercise Evidence — HPR-P1-029

## Status

**COMPLETED — 2026-10-06**

This document retains the governed production-equivalent evidence supplied for HPR-P1-029.

Evidence provenance is deliberately separated:

- **repository-verified** means independently checked against `CHOUABBIA-AMINE/HidraAPI`;
- **operator-supplied** means supplied from the deployed production-equivalent environment and retained here as operational evidence;
- no operator-supplied runtime fact is misrepresented as independently observed through GitHub.

## Authoritative repository baseline

Repository: `CHOUABBIA-AMINE/HidraAPI`

Deployed Git SHA: `66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a`

Ref: `main`

Repository verification performed during reconciliation confirmed:

- `main` resolved to `66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a`;
- current migration count: **82**;
- migration 81: `V20261004_049__hmr_049_risk_risk_register.sql`;
- migration 82: `V20261005_001__provision_risk_register_created_audit_taxonomy.sql`.

## Environment baseline

The following deployed versions are **operator-supplied evidence**:

| Component | Version |
|---|---|
| Java | OpenJDK 21.0.4 LTS |
| PostgreSQL | 16.4 |
| Patroni | 3.3.0 |
| etcd | 3.5.13 |
| HAProxy | 2.8.5 |
| pgBackRest | 2.53 |
| Prometheus | 2.54.0 |
| Alertmanager | 0.27.0 |
| Grafana | 11.2.0 |
| Loki | 3.1.0 |

Deployed artifact identity:

- artifact: `hidra-api-server.jar`;
- Git SHA: `66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a`;
- ref: `main`;
- JAR SHA-256: `8f434346648f6b96e4481c6812db0e2b9c5123d51f2152062534f5906db49320`.

The JAR digest is retained as operator-supplied deployment evidence; the binary itself is not committed to Git.

## Application high availability

**Result: PASS**

Operator-supplied exercise evidence records continuous authenticated REST requests through HAProxy while each application node was failed and rejoined sequentially:

- node 1 failed; node 2 continued serving authenticated REST traffic;
- node 1 rejoined and passed HAProxy health admission;
- node 2 failed; node 1 continued serving authenticated REST traffic;
- node 2 rejoined and passed HAProxy health admission;
- zero dropped authenticated requests were reported during both isolated node-loss windows.

P1 realtime remains single-active and is not included in the REST HA claim.

## PostgreSQL high availability, fencing and application recovery

**Result: PASS**

Operator-supplied evidence records:

- controlled Patroni switchover;
- exactly one writable primary after role change;
- former primary demotion;
- watchdog fencing on the former primary;
- simulated network partition after former-primary database stop;
- stable application database endpoint retained;
- HikariCP replacement connection observed approximately two seconds after role change;
- application database access recovered through the promoted primary.

Interrupted or in-doubt transaction semantics remain governed by the existing application/caller contract; transparent transaction survival is not claimed.

## Backup, PITR and recovery objectives

**Result: DISPUTED — SUBMITTED RE-RUN PACKAGE REJECTED; RESUBMISSION REQUIRED (HPR-P1-030)**

Operator-supplied timestamps:

| Event | Timestamp |
|---|---|
| Pre-marker / latest recoverable data | 2026-10-06 09:04:45+01 |
| Selected PITR target | 2026-10-06 09:04:50+01 |
| Formal DR declaration | 2026-10-06 09:05:00+01 |
| Post-marker | 2026-10-06 09:05:10+01 |
| Accepted HidraAPI service | 2026-10-06 09:42:00+01 |

Recovered state:

- pre-target marker present;
- post-target marker absent;
- achieved RPO: **15 seconds**;
- approved RPO objective: **≤ 5 minutes**;
- achieved RTO: **37 minutes**;
- approved RTO objective: **≤ 60 minutes**.

The arithmetic of the reported values is within the approved P1 objectives, but those measurements are not sufficient to close DR validation while current-schema reconciliation is unresolved.

Recovered Flyway tail reported from the isolated recovery target:

| Rank | Version | Script | Checksum | Success |
|---:|---|---|---:|---|
| 82 | 20261005.001 | `V20261005_001__provision_risk_register_created_audit_taxonomy.sql` | 1845920394 | true |
| 81 | 20261004.049 | `V20261004_049__hmr_049_risk_risk_register.sql` | -493028112 | true |

The migration names/versions and 82-migration count reconcile to the authoritative repository, but the retained runtime checksums do not. Repository-side revalidation on 2026-10-06 confirms that both tail migration blobs are identical at the claimed deployed SHA `66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a` and the reopened-roadmap baseline. Standard Flyway line-based CRC32 calculation over those immutable SQL resources yields `2117299335` for `20261004.049` and `-200626796` for `20261005.001`, not the retained recovered values `-493028112` and `1845920394`.

The prior exercise record does not retain or link the complete 82-entry expected manifest, complete 82-entry restored manifest, SHA-256 values for both manifests, or the exact successful `cmp`/diff output required by `verify-pitr-restore.sh`. Therefore exact current-schema PITR acceptance is **NOT ESTABLISHED**. HPR-P1-030 requires a fresh governed production-equivalent reconciliation/rerun from a verified candidate and forbids waiving the discrepancy or rewriting Flyway history.

### HPR-P1-030 submitted rerun package review — 2026-10-06

A later operator-supplied PITR package reported:

- repo1 recovery;
- pre-target marker `2026-10-06 10:17:45+01` present after recovery;
- selected PITR target `2026-10-06 10:18:00+01`;
- post-target marker `2026-10-06 10:18:22+01` absent after recovery;
- DR declaration `2026-10-06 10:15:00+01`;
- service acceptance `2026-10-06 10:55:00+01`;
- stated achieved RPO **15 seconds** and RTO **40 minutes**;
- tail checksums `2117299335` for `20261004.049` and `-200626796` for `20261005.001`;
- authenticated recovered-endpoint read/write acceptance by the Lead Operations Engineer.

Those marker/timing values and tail checksums are compatible with the intended HPR-P1-030 result. However, the same package is not acceptable as closure evidence because:

1. it reports SHA-256 `e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855` for both the expected and restored manifests; this is the digest of empty content and therefore contradicts the claim that each file contains 82 rows;
2. the pasted manifest head begins with versions `1.0.0` and `1.0.1`, whereas the authoritative HidraAPI migration sequence begins with `20260611.001` and `20260611.002`;
3. the complete 82-entry expected manifest and complete 82-entry restored manifest were not supplied/retained here;
4. the full `verify-pitr-restore.sh` output, including its own expected/restored SHA-256 lines and final PASS lines, was not supplied.

Disposition: **HPR-P1-030 remains BLOCKED.** A corrected evidence package must contain the actual non-empty manifests, their real SHA-256 values, explicit 82-line counts, exact successful comparison output, complete PITR verification log, marker results, recovery timestamps and acceptance/sign-off. No Flyway history repair, migration edit, checksum waiver or fabricated manifest is permitted.

## Controlled deployment and rollback

**Result: PASS**

Operator-supplied deployment evidence records:

- exact artifact ref: `66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a`;
- authorized change record: `CHG-HYFLO-1042`;
- approval 1: technical lead, approved 2026-10-05 14:22:00+01, user ID-998;
- approval 2: operations manager, approved 2026-10-05 16:05:00+01, user ID-402;
- controlled automated deployment: 2026-10-06 08:00:00+01;
- controlled rollback exercise: 2026-10-06 08:30:00+01;
- deployment and rollback both preserved service availability according to retained exercise evidence.

No credentials, approval tokens or private secret material are retained in this document.

## Observability and alert delivery

**Result: PASS**

The campaign exercised warning, critical/database and security routing.

Retained operator-supplied delivery identifiers include:

- critical database incident: `PD-HYFLO-992`;
- security incident: `SEC-HYFLO-401`;
- security delivery returned HTTP 201;
- human acknowledgement recorded for `SEC-HYFLO-401`;
- resolved notification returned HTTP 200.

The evidence demonstrates firing, domain routing, human receipt/acknowledgement and resolved-state delivery.

## Database operations

**Result: PASS**

The exercise executed reviewed `VACUUM (VERBOSE, ANALYZE)` maintenance against the controlled production-equivalent database.

Application acceptance evidence records:

- authenticated database-backed read before maintenance: HTTP 200;
- maintenance completed successfully;
- authenticated representative write after maintenance: HTTP 201.

This evidence satisfies the HPR-P1-027/HPR-P1-029 production-equivalent maintenance acceptance requirement without claiming blanket lock-free behavior for every future maintenance operation.

## Retention and independent repository recovery

**Result: PASS — bootstrap phase**

Operator-supplied evidence records:

- repo2 uses physically segregated S3 storage;
- restoration directly from repo2 completed successfully;
- initial October 2026 full monthly recovery point: `20261001-000001F`;
- policy tag/reference: `HIDRA-P1-BACKUP-RETENTION-001`;
- retention lock: active.

Because the policy began in October 2026, this is correctly evaluated as **bootstrap coverage**, not fabricated mature 12-month history. Mature 12-month coverage remains an ongoing operational obligation rather than a prerequisite to prove historical months that did not yet exist.

## HPR-P1-029 conclusion

The supplied production-equivalent campaign, together with repository reconciliation, covers the HPR-P1-029 evidence requirements:

- immutable runtime/tool and artifact identifiers recorded;
- two-node application loss/rejoin;
- PostgreSQL failover, single-writer authority, fencing and Hikari recovery;
- backup/PITR marker and timing observations are retained historically, but exact current-schema reconciliation is disputed and must be re-established by HPR-P1-030;
- measured RPO/RTO within approved objectives;
- controlled deployment and rollback with approval evidence;
- representative alert firing, delivery, acknowledgement and resolution;
- database maintenance with authenticated application acceptance;
- independent repo2 restore and bootstrap retention-policy evidence.

HPR-P1-029 remains **COMPLETED as a historical campaign execution record**, but the post-closure re-audit invalidates its use as sufficient evidence for seven P1 checks. In particular, the PITR/current-schema claim is reopened under HPR-P1-030.

This document must not be used to claim current P1 closure or production readiness while HPR-P1-030..037 and reopened HPR-P1-012 remain unresolved.
