INSERT INTO public.hidra_identity_permission
    (id, code, permission_domain, resource_type, action, sensitive, status, created_at, updated_at)
VALUES ('t11-rule-evaluate', 'monitoring:rule:evaluate', 'monitoring', 'MONITORING_RULE',
        'evaluate', true, 'ACTIVE', now(), now());

CREATE TABLE public.hidra_monitoring_evaluation_freshness_policy (
    rule_id varchar(80) NOT NULL,
    rule_revision bigint NOT NULL,
    maximum_age_seconds bigint NOT NULL CHECK (maximum_age_seconds > 0),
    PRIMARY KEY (rule_id, rule_revision),
    FOREIGN KEY (rule_id, rule_revision)
        REFERENCES public.hidra_monitoring_rule_revision (rule_id, revision)
);

CREATE TABLE public.hidra_monitoring_authoritative_evaluation (
    id varchar(80) PRIMARY KEY,
    rule_id varchar(80) NOT NULL,
    rule_revision bigint NOT NULL,
    requested_target_id varchar(80) NOT NULL,
    requested_reading_id varchar(80) NOT NULL,
    evaluated_at timestamptz NOT NULL CHECK (isfinite(evaluated_at)),
    recorded_at timestamptz NOT NULL CHECK (isfinite(recorded_at) AND recorded_at >= evaluated_at),
    actor_id varchar(80) NOT NULL,
    unit_id varchar(80) NOT NULL,
    outcome varchar(40) NOT NULL CHECK (outcome IN
        ('COMPUTED', 'MISSING_EVIDENCE', 'STALE', 'UNCERTAIN', 'INCOMPATIBLE', 'POLICY_UNAVAILABLE')),
    target_id varchar(80),
    target_revision_id varchar(80),
    approval_id varchar(80),
    approved_at timestamptz,
    expected_value numeric,
    trusted_reading_id varchar(80),
    raw_reading_id varchar(80),
    assessment_id varchar(80),
    binding_id varchar(80),
    quality_code_id varchar(80),
    trust_level varchar(40),
    observed_at timestamptz,
    trusted_at timestamptz,
    actual_value numeric,
    difference_value numeric GENERATED ALWAYS AS (actual_value - expected_value) STORED,
    policy_revision bigint,
    FOREIGN KEY (rule_id, rule_revision)
        REFERENCES public.hidra_monitoring_rule_activation (rule_id, revision),
    FOREIGN KEY (rule_id, policy_revision)
        REFERENCES public.hidra_monitoring_evaluation_freshness_policy (rule_id, rule_revision),
    UNIQUE (rule_id, requested_target_id, requested_reading_id, evaluated_at),
    CHECK (target_id IS NULL OR (target_id = requested_target_id AND target_revision_id IS NOT NULL
        AND approval_id IS NOT NULL AND approved_at IS NOT NULL)),
    CHECK (trusted_reading_id IS NULL OR (trusted_reading_id = requested_reading_id
        AND raw_reading_id IS NOT NULL AND observed_at IS NOT NULL AND trusted_at IS NOT NULL)),
    CHECK ((outcome = 'POLICY_UNAVAILABLE' AND policy_revision IS NULL)
        OR (outcome <> 'POLICY_UNAVAILABLE' AND policy_revision IS NOT NULL AND policy_revision = rule_revision)),
    CHECK ((outcome = 'COMPUTED' AND expected_value IS NOT NULL AND actual_value IS NOT NULL
        AND target_id IS NOT NULL AND trusted_reading_id IS NOT NULL
        AND assessment_id IS NOT NULL AND binding_id IS NOT NULL)
        OR (outcome <> 'COMPUTED' AND expected_value IS NULL AND actual_value IS NULL))
);

CREATE INDEX ix_t11_evaluation_history
    ON public.hidra_monitoring_authoritative_evaluation (rule_id, evaluated_at DESC, id DESC);

CREATE FUNCTION public.hidra_t11_deny_evidence_mutation() RETURNS trigger
LANGUAGE plpgsql SECURITY INVOKER SET search_path=pg_catalog,public AS $$
BEGIN
    RAISE EXCEPTION 'T11 evaluation evidence is immutable' USING ERRCODE='23514';
END
$$;

CREATE TRIGGER trg_t11_evaluation_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_monitoring_authoritative_evaluation
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t11_deny_evidence_mutation();
CREATE TRIGGER trg_t11_evaluation_no_truncate
    BEFORE TRUNCATE ON public.hidra_monitoring_authoritative_evaluation
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_t11_deny_evidence_mutation();
CREATE TRIGGER trg_t11_freshness_policy_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_monitoring_evaluation_freshness_policy
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t11_deny_evidence_mutation();
CREATE TRIGGER trg_t11_freshness_policy_no_truncate
    BEFORE TRUNCATE ON public.hidra_monitoring_evaluation_freshness_policy
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_t11_deny_evidence_mutation();
