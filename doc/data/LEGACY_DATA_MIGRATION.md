# HidraAPI Legacy Data Migration Governance

## Status and applicability

CURRENT — HPR-P2-010 source-derived governance baseline, verified on 2026-10-09
against parent `b36733fc05e789613485606e1e1dd1731b11af53`. Metadata and ownership follow [the data index](README.md).
TARGET admission requirements below are separated from implemented controls;
unknown owner approvals and business values remain NOT ESTABLISHED.

## Current provisioning disposition

The preserved [legacy provisioning roadmap](../../docs/roadmap/data-provisioning.md) is paused/blocked history. Its source-content/owner/security/mapping gates are not
automatically satisfied by HPR-P2-008/009 semantic completion. No legacy HDP/HMS task
is executed or unblocked here, and no dataset is newly approved or loaded. This
baseline states the documented workstream disposition, not a scan asserting that an
unseen deployed database contains no external data.

| Historical evidence | Applicability and remaining limits |
|---|---|
| [source-inventory](../../docs/data-provisioning/source-inventory.md) | Metadata-only HyFloAPI source manifest, 31 tracked files at pinned source commit f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b; source custody/blob identity is not business owner or import approval |
| [source-classification](../../docs/data-provisioning/source-classification.md) | Provisional DEFER/EXCLUDE dispositions; later structural extraction reports 21 scoped structured artifacts, 20 inspected and one unreadable, replacing some technical-access limitations without approving business meaning/reuse |
| [target-inventory](../../docs/data-provisioning/target-inventory.md) | Historical target-only inventory; target matching must be recomputed against current owner contracts and fields |
| [current semantic decisions](../domain/SEMANTIC_DECISIONS.md) | Lasting reference/optional/history rules, not source-dataset approval |

Later structural evidence in source-classification qualifies the older source-inventory
limitations. Worksheet dimensions, XML row elements or SQL INSERT-line counts are not
validated business record counts. Filename dates and variant suffixes do not establish
canonical precedence, validated effective dates or identifier correspondence. No raw
workbook cells, SQL data rows, credentials or restricted attachments are copied here;
legacy SQL/geocoding scripts are not executed by documentation work.

## TARGET governed admission sequence

These stages specify evidence required for a separately admitted provisioning task.
They are not executable ETL, current approval or permission to load a database.

| Stage | Required reviewed evidence | Admission boundary |
|---|---|---|
| Source identity and safe inspection | Exact repository/source artifact/version and actual digest kind; permitted read-only extraction; schema/field/format meaning and inspection limits | Unknown or unreadable evidence remains explicit; no credential/row publication into Git |
| Owner and allowed use | Actual source owner, collection authority, intended use and confidentiality/reuse decision | Repository visibility/custody is insufficient; approval remains NOT ESTABLISHED until recorded |
| Dataset classification and precedence | Reference/master, operational/history, derived or unclassified meaning; approved effective snapshot and canonical source | Do not select a source by filename chronology or treat a dump as one homogeneous dataset |
| Field and identity mapping | Explicit source column/table to current owner model/property/type, nullable/catalog/range/units, transform, identifier strategy and loading contract | Unmapped fields have explicit UNMAPPED disposition; no guessed IDs, enums, translations, units or implicit merges |
| Policy/reference admission | Actual owner-approved catalogs/mappings and eligible parents/context before new writes | Same-name/code equality is not approval; historical semantics cannot be silently repaired |
| Validation and dry run | Selected-data counts, exceptions, duplicates, owner/context integrity and mapping reconciliation at pinned source/target versions | Test scope and approval evidence precede any separately authorized write |
| Controlled loading and recovery | Approved execution scope, reproducible transform/run identity, allowed owner write path and tested rollback/recovery plan | Do not replay arbitrary SQL into production or bypass existing application/integrity guards |
| Acceptance and retained provenance | Actual result/count reconciliation, exception disposition, owner acceptance and source/mapping/run provenance | No successful-import claim without real results; rollback/unknown outcome remains explicit |

Identity users/credentials, personal data, audit/approval history, raw operational facts
and derived reports must be assessed under their actual owners and approved purpose.
Legacy labels/IDs do not recreate authenticated actions or formal approval evidence.
These are governance boundaries inherited from current source and historical gates,
not newly granted loading authority.

## Schema migration is separate from data provisioning

[Flyway policy](../database/FLYWAY_POLICY.md) preserves applied migration/history immutability, forward schema changes, validation
and production clean-disabled behavior. It retains its stated historical 82-migration
baseline; current source has 139 unique versioned migrations. The older
[dictionary](../database/DATA_DICTIONARY.md) also retains its own applicability and is not regenerated by this scope. Neither
schema presence nor a migration passing on empty fixtures establishes business-data
import eligibility or approved populated-deployment mappings.

Planning target policy migration
[V20261008_019__hmr_094_planning_target_value_policy](../../src/main/resources/db/migration/V20261008_019__hmr_094_planning_target_value_policy.sql) and [V20261008_020__hmr_094_planning_plan_target_integrity](../../src/main/resources/db/migration/V20261008_020__hmr_094_planning_plan_target_integrity.sql) install policy metadata and fail closed where target integrity/approved mappings
are absent. Nomination
[V20261008_025__hmr_080_nomination_owner_reference_policies](../../src/main/resources/db/migration/V20261008_025__hmr_080_nomination_owner_reference_policies.sql) and [V20261008_026__hmr_080_planning_nomination_integrity](../../src/main/resources/db/migration/V20261008_026__hmr_080_planning_nomination_integrity.sql) similarly require actual approved product/quantity-role/unit-pair facts. Do not seed
guessed mappings, falsify Flyway history, disable checks or silently rewrite historical
records to force migration success. Valid unchanged inactive history follows its
specific source contract; new import eligibility is a different owner decision.

## NOT ESTABLISHED and deferred work

No source dataset, field mapping, reusable extraction approval, legacy loading adapter,
import acceptance or production recovery result is established by HPR-P2-010.
Future implementation needs explicit current roadmap admission and evidence at its
actual target head. Deferred spatial/TimescaleDB capabilities and later testing/CI
governance tasks are not executed through this document.
