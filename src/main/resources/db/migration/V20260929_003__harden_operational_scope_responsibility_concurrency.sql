-- ORG-028 - Harden canonical Organization responsibility scope schema.
-- Module: organization
--
-- V20260927_001__add_operational_scope_registry.sql is immutable and remains the additive baseline.
-- Legacy responsibility rows with scope_id IS NULL are intentionally outside the new canonical
-- temporal rule until ORG-029 reconciliation/backfill is authorized.
--
-- Concurrency is enforced by pessimistic locking of the canonical OperationalScope row during
-- assignment creation and of the ResponsibilityAssignment row during revocation. This migration
-- adds the supporting ACTIVE-identity index and canonical temporal constraint without requiring
-- PostgreSQL extension privileges.

ALTER TABLE hidra_org_responsibility_assignment
    ADD CONSTRAINT ck_org_responsibility_canonical_temporal
    CHECK (
        scope_id IS NULL
        OR valid_to IS NULL
        OR valid_to > valid_from
    ) NOT VALID;

ALTER TABLE hidra_org_responsibility_assignment
    VALIDATE CONSTRAINT ck_org_responsibility_canonical_temporal;

CREATE INDEX ix_org_responsibility_active_identity
    ON hidra_org_responsibility_assignment (
        scope_id,
        assignee_type,
        assignee_id,
        responsibility_type,
        valid_from,
        valid_to
    )
    WHERE status = 'ACTIVE'
      AND scope_id IS NOT NULL;
