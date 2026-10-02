-- ALM-SUP-007 - Enforce deterministic exact-scope ACTIVE suppression conflict semantics.

CREATE UNIQUE INDEX IF NOT EXISTS ux_alarm_suppression_active_scope
    ON hidra_alarm_suppression (scope_type, scope_reference_id)
    WHERE status = 'ACTIVE';
