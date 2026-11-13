-- T10: immutable Monitoring revisions and activation receipts, serialized mutable head.
-- No pre-existing migration rewrite, Monitoring->Telemetry FK or implicit Identity grants.
INSERT INTO public.hidra_identity_permission
(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
('t10-rule-create','monitoring:rule:create','monitoring','MONITORING_RULE','create',true,'ACTIVE',now(),now()),
('t10-rule-revise','monitoring:rule:revise','monitoring','MONITORING_RULE','revise',true,'ACTIVE',now(),now()),
('t10-rule-activate','monitoring:rule:activate','monitoring','MONITORING_RULE','activate',true,'ACTIVE',now(),now()),
('t10-rule-read','monitoring:rule:read','monitoring','MONITORING_RULE','read',true,'ACTIVE',now(),now());

CREATE TABLE public.hidra_monitoring_rule_scope (
    rule_id varchar(80) PRIMARY KEY REFERENCES public.hidra_monitoring_rule(id) ON DELETE RESTRICT,
    company_party_id varchar(80) NOT NULL,
    asset_type varchar(80) NOT NULL CHECK (asset_type IN ('FACILITY','EQUIPMENT')),
    asset_id varchar(80) NOT NULL,
    created_by_actor_id varchar(80) NOT NULL,
    created_at timestamptz NOT NULL
);
CREATE INDEX ix_t10_rule_company_asset
    ON public.hidra_monitoring_rule_scope(company_party_id,asset_type,asset_id);

CREATE TABLE public.hidra_monitoring_rule_head (
    rule_id varchar(80) PRIMARY KEY REFERENCES public.hidra_monitoring_rule_scope(rule_id) ON DELETE RESTRICT,
    last_revision bigint NOT NULL DEFAULT 0 CHECK (last_revision>=0),
    active_revision bigint NOT NULL DEFAULT 0 CHECK (active_revision>=0 AND active_revision<=last_revision),
    effective_from timestamptz,
    effective_until timestamptz,
    CHECK ((active_revision=0 AND effective_from IS NULL AND effective_until IS NULL)
        OR (active_revision>0 AND effective_from<effective_until))
);

CREATE TABLE public.hidra_monitoring_rule_revision (
    rule_id varchar(80) NOT NULL REFERENCES public.hidra_monitoring_rule_scope(rule_id) ON DELETE RESTRICT,
    revision bigint NOT NULL CHECK (revision>0),
    company_party_id varchar(80) NOT NULL,
    asset_type varchar(80) NOT NULL,
    asset_id varchar(80) NOT NULL,
    telemetry_point_id varchar(80) NOT NULL,
    rule_type varchar(80) NOT NULL CHECK (rule_type='PLAN_COMPARISON'),
    unit_id varchar(80) NOT NULL,
    evidence_policy_code varchar(80) NOT NULL,
    valid_from timestamptz NOT NULL,
    valid_to timestamptz NOT NULL,
    created_by_actor_id varchar(80) NOT NULL,
    created_at timestamptz NOT NULL,
    PRIMARY KEY (rule_id,revision),
    CHECK (valid_from<valid_to)
);

CREATE TABLE public.hidra_monitoring_rule_activation (
    rule_id varchar(80) NOT NULL,
    revision bigint NOT NULL,
    effective_from timestamptz NOT NULL,
    effective_until timestamptz NOT NULL,
    activated_by_actor_id varchar(80) NOT NULL,
    activated_at timestamptz NOT NULL,
    PRIMARY KEY (rule_id,revision),
    UNIQUE (rule_id,effective_from),
    FOREIGN KEY (rule_id,revision) REFERENCES
        public.hidra_monitoring_rule_revision(rule_id,revision) ON DELETE RESTRICT,
    CHECK (effective_from<effective_until)
);
CREATE INDEX ix_t10_rule_effective
    ON public.hidra_monitoring_rule_activation(rule_id,effective_from DESC);

-- Lock the same head for direct SQL writers and deny overlapping half-open intervals.
CREATE FUNCTION public.hidra_t10_guard_activation_overlap() RETURNS trigger
LANGUAGE plpgsql SECURITY INVOKER SET search_path=pg_catalog,public AS $
BEGIN
    PERFORM 1 FROM public.hidra_monitoring_rule_head WHERE rule_id=NEW.rule_id FOR UPDATE;
    IF EXISTS (
        SELECT 1 FROM public.hidra_monitoring_rule_activation a
        WHERE a.rule_id=NEW.rule_id
        AND tstzrange(a.effective_from,a.effective_until,'[)')
            && tstzrange(NEW.effective_from,NEW.effective_until,'[)')
    ) THEN
        RAISE EXCEPTION 'T10 overlapping rule activation intervals' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END
$;
CREATE TRIGGER trg_t10_activation_no_overlap
    BEFORE INSERT ON public.hidra_monitoring_rule_activation
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t10_guard_activation_overlap();

CREATE FUNCTION public.hidra_t10_deny_version_mutation() RETURNS trigger
LANGUAGE plpgsql SECURITY INVOKER SET search_path=pg_catalog,public AS $$
BEGIN
    RAISE EXCEPTION 'T10 revision/activation evidence is immutable' USING ERRCODE='23514';
END
$$;
CREATE TRIGGER trg_t10_revision_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_monitoring_rule_revision
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t10_deny_version_mutation();
CREATE TRIGGER trg_t10_activation_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_monitoring_rule_activation
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t10_deny_version_mutation();

