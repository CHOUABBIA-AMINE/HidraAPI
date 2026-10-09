# HidraAPI Retention and Archival

## Status and applicability

CURRENT — HPR-P2-010 source-derived governance baseline, verified on 2026-10-09
against parent `b36733fc05e789613485606e1e1dd1731b11af53`. Metadata and ownership follow [the data index](README.md).
TARGET admission requirements below are visibly separated from implemented controls;
unknown owner approvals and business values remain NOT ESTABLISHED.

## Approved infrastructure policy and current configuration

[P1 owner-approved values](../operations/P1_OWNER_APPROVED_VALUES.md) and [HIDRA-P1-BACKUP-RETENTION-001](../../ops/production/postgres/pgbackrest/RETENTION_POLICY.md) establish the existing P1 operational populations. They do not define Audit,
Documents, employee, Telemetry or Custody business-record lifetimes.

| Population | Existing value/control | Source and scope |
|---|---|---|
| repo1 operational full backups | Time-based full retention 35 days | [pgbackrest.conf](../../ops/production/postgres/pgbackrest/pgbackrest.conf.tpl); recovery-copy policy, not row expiry |
| repo2 monthly full recovery points | One completed point per UTC calendar month; retain latest 12 monthly points under the approved horizon; policy starts October 2026 | [RETENTION_POLICY](../../ops/production/postgres/pgbackrest/RETENTION_POLICY.md) and repo2 count-based full retention 12 in the template |
| repo2 freshness/coverage validation | Latest completed point age no more than 35 days; no duplicate month; ceiling 12 full points | [check-monthly-retention](../../ops/production/postgres/scripts/check-monthly-retention.sh) |
| Prometheus operational metrics | --storage.tsdb.retention.time=30d | [prometheus.service](../../ops/production/observability/prometheus/prometheus.service.tpl) |
| Loki operational logs | retention_period=2160h (90 days), compactor retention enabled, retention_delete_delay=2h | [loki](../../ops/production/observability/loki/loki.yml); configured deletion control, not business disposal approval |

The monthly checker computes required UTC months from October 2026 through the current
month, capped to the latest twelve. BOOTSTRAP requires coverage of elapsed policy
months; MATURE requires exactly the latest twelve months and exactly twelve full
points. The implementation rejects duplicate months and missing required months in
both modes; exact set equality is enforced in MATURE. During BOOTSTRAP the checker
also allows extra distinct retained months within its count cap, so its checks must
not be described as exact set equality in both modes. Schedule/configuration is not
evidence that backups exist or are restorable in a particular environment.

The prior [P1 campaign](../operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md) records October bootstrap and retained restore evidence against deployed SHA
`66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a` with 82 migrations. Repository-verified
and operator-supplied facts remain distinguished. This task neither repeats the
campaign nor claims mature monthly coverage or physical independence for today's tree.

## Current business metadata versus enforcement

| Owner | Metadata evidence | What it does not establish |
|---|---|---|
| Audit | [AuditRetentionPolicyJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditRetentionPolicyJpaEntity.java): retentionDays, archiveAfterDays, legalHoldSupported, purgeAllowed, validity and active flag | Approved per-category durations, running archive/purge worker or actual hold enforcement |
| Audit | [AuditRetentionPolicyPort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditRetentionPolicyPort.java): available(referenceId) | Approval or policy execution; declaration does not evaluate retention eligibility |
| Documents | [DocumentRetentionRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentRetentionRecordJpaEntity.java): retainUntil, legalHold/reason, archive time/object and disposal fields | A legal period, approved disposal decision, archive scheduler or deleting binary content safely |

Search of current production Java/resource configuration and relevant tests for these
retention ports/repositories and archive/purge/hold/retention orchestration did not
establish a business retention/disposal worker wired to this metadata. The repositories
are Spring Data declarations, and mapped lifecycle fields are structural evidence.
This is a bounded repository finding, not an assertion about external deployed tools.

Append-only [Audit/Workflow/Alarm rules](../domain/SEMANTIC_DECISIONS.md) prevent evidence rewrites on their guarded paths; they are not a lifecycle retention
engine. Document archival flags are not proof of moving bytes. Binary rollback cleanup
is compensation for a newly failed upload, not authorized historical-data disposal.
Backup/WAL expiry, metrics/log deletion and business-record disposal remain different.

## TARGET decisions before business archival or disposal

An actual policy must identify record population and owner-approved value/trigger,
archival destination and recoverability, hold priority and authorized release,
approval/audit evidence, allowed write path, verification and recovery handling.
Any implementation must preserve existing append-only/owner constraints and require
its own admitted scope. These are governance requirements; this document authorizes
no purge job, deletion, hold release or new policy seed.

## NOT ESTABLISHED

Business durations, per-category approval, universal hold enforcement, archive-worker
availability and approved disposal are NOT ESTABLISHED by this task. Existing enterprise
security/audit policy is referenced by P1; its contents are not supplied here and are
not guessed. TimescaleDB retention/compression remains a separately deferred P3 scope.
