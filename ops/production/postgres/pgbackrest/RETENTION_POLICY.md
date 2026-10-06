# HPR-P1-028 Retention Enforcement

Policy identifier: `HIDRA-P1-BACKUP-RETENTION-001`.

The owner-approved retained-recovery baseline is one repo2 full recovery point per UTC calendar month, retaining the latest 12 monthly points for a 12-month horizon. The policy start month is October 2026.

`check-monthly-retention.sh` distinguishes two states:

- **BOOTSTRAP** — fewer than 12 policy months have elapsed; every UTC calendar month from the policy start through the current month must have exactly one completed repo2 full recovery point;
- **MATURE** — 12 or more policy months have elapsed; exactly the latest 12 required UTC calendar months must be represented, with no duplicate monthly full points.

The checker also preserves the latest-point age requirement of no more than 35 days and the repository count ceiling of 12 full backups.

Repository configuration can prove the intended retention model only. Physical independence of repo2 from the live PostgreSQL primary/standby failure domain and actual restorability of a retained repo2 point are production-equivalent evidence obligations under HPR-P1-029. The existing PITR harness must be executed with `HIDRA_PITR_REPO=2` for that retained-point restoration proof.
