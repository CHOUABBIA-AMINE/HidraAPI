-- T32: immutable retirement receipt for existing HAPI-927 typed environment overrides.
-- Fresh-schema additive migration; preserve prior Flyway history and B5 override values.
CREATE TABLE public.hidra_configuration_override_retirement (
    id varchar(80) PRIMARY KEY,
    value_id varchar(80) NOT NULL UNIQUE REFERENCES public.hidra_configuration_value(id) ON DELETE RESTRICT,
    definition_id varchar(80) NOT NULL REFERENCES public.hidra_configuration_definition(id) ON DELETE RESTRICT,
    definition_version_id varchar(80) NOT NULL REFERENCES public.hidra_configuration_definition_version(id) ON DELETE RESTRICT,
    environment varchar(40) NOT NULL,
    cutoff timestamp with time zone NOT NULL,
    before_source varchar(40) NOT NULL CHECK (before_source='ENVIRONMENT_OVERRIDE'),
    before_value_id varchar(80) NOT NULL CHECK (before_value_id=value_id),
    after_source varchar(40) NOT NULL CHECK (after_source IN ('DEFAULT','ENVIRONMENT_OVERRIDE')),
    after_value_id varchar(80),
    state_token varchar(64) NOT NULL CHECK (state_token ~ '^[0-9a-f]{64}$'),
    actor_id varchar(80) NOT NULL CHECK (length(btrim(actor_id))>0 AND btrim(actor_id)=actor_id),
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT ck_t32_after_provenance CHECK (
        (after_source='DEFAULT' AND after_value_id IS NULL)
        OR (after_source='ENVIRONMENT_OVERRIDE' AND after_value_id IS NOT NULL AND after_value_id<>value_id)
    )
);
CREATE INDEX ix_t32_retirement_definition_environment
    ON public.hidra_configuration_override_retirement(definition_id,environment,cutoff);

CREATE FUNCTION public.hidra_t32_retirement_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE
    v public.hidra_configuration_value%ROWTYPE;
    d public.hidra_configuration_definition%ROWTYPE;
    ver public.hidra_configuration_definition_version%ROWTYPE;
    successor_id varchar(80);
BEGIN
    -- Match the exact locking order used by the admitted B5 override writer.
    SELECT * INTO v FROM public.hidra_configuration_value WHERE id=NEW.value_id;
    IF NOT FOUND THEN RAISE EXCEPTION 'Unknown retirement target' USING ERRCODE='23514'; END IF;
    SELECT * INTO d FROM public.hidra_configuration_definition WHERE id=v.definition_id FOR UPDATE;
    SELECT * INTO v FROM public.hidra_configuration_value WHERE id=NEW.value_id FOR SHARE;
    SELECT * INTO ver FROM public.hidra_configuration_definition_version WHERE id=v.definition_version_id;
    IF d.id IS NULL OR ver.id IS NULL OR ver.validation_summary IS DISTINCT FROM 'HAPI-927'
       OR NOT ver.active OR ver.schema_json IS NOT NULL OR ver.definition_id<>v.definition_id
       OR d.status<>'ACTIVE' OR d.scoped OR d.requires_approval OR d.sensitivity NOT IN ('PUBLIC','INTERNAL')
       OR d.value_type NOT IN ('STRING','BOOLEAN','NUMBER')
       OR v.status<>'ACTIVE' OR v.raw_value IS NULL OR v.json_value IS NOT NULL OR v.secret_reference IS NOT NULL
       OR NEW.definition_id<>v.definition_id OR NEW.definition_version_id<>v.definition_version_id
       OR NEW.environment<>v.environment OR NEW.before_value_id<>v.id
       OR v.effective_from IS NULL OR v.effective_from>=NEW.cutoff
       OR (v.effective_to IS NOT NULL AND NEW.cutoff>=v.effective_to)
       OR NEW.cutoff<=clock_timestamp()
       OR EXISTS(SELECT 1 FROM public.hidra_configuration_override_retirement WHERE value_id=NEW.value_id)
       OR EXISTS(SELECT 1 FROM public.hidra_configuration_validation_rule
                  WHERE definition_id=v.definition_id AND status='ACTIVE') THEN
        RAISE EXCEPTION 'Invalid B5 retirement lifecycle' USING ERRCODE='23514';
    END IF;

    -- The receipt records the observed successor or default *at the scheduling instant*.
    SELECT x.id INTO successor_id FROM public.hidra_configuration_value x
      LEFT JOIN public.hidra_configuration_override_retirement r ON r.value_id=x.id
      WHERE x.id<>v.id AND x.definition_id=v.definition_id
        AND x.definition_version_id=v.definition_version_id AND x.environment=v.environment
        AND x.status='ACTIVE' AND x.effective_from<=NEW.cutoff
        AND (LEAST(x.effective_to,r.cutoff) IS NULL OR NEW.cutoff<LEAST(x.effective_to,r.cutoff))
      ORDER BY x.id LIMIT 1;
    IF successor_id IS NULL THEN
        IF NEW.after_source<>'DEFAULT' OR NEW.after_value_id IS NOT NULL THEN
            RAISE EXCEPTION 'Stale retirement successor' USING ERRCODE='23514';
        END IF;
    ELSIF NEW.after_source<>'ENVIRONMENT_OVERRIDE' OR NEW.after_value_id IS DISTINCT FROM successor_id THEN
        RAISE EXCEPTION 'Stale retirement successor' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_t32_retirement_insert BEFORE INSERT ON public.hidra_configuration_override_retirement
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t32_retirement_guard();

CREATE FUNCTION public.hidra_t32_retirement_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    RAISE EXCEPTION 'Configuration retirement receipts are immutable' USING ERRCODE='23514';
END $$;
CREATE TRIGGER tr_t32_retirement_immutable BEFORE UPDATE OR DELETE ON public.hidra_configuration_override_retirement
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t32_retirement_immutable();

-- B5 uses the effective exclusive end for *both* direct-SQL inserts and the writer's
-- explicit overlap check. Legacy values retain their original validation branch.
CREATE OR REPLACE FUNCTION public.hidra_b5_configuration_value_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE d hidra_configuration_definition%ROWTYPE; v hidra_configuration_definition_version%ROWTYPE;
BEGIN
 SELECT * INTO d FROM hidra_configuration_definition WHERE id=NEW.definition_id FOR UPDATE;
 SELECT * INTO v FROM hidra_configuration_definition_version WHERE id=NEW.definition_version_id;
 -- Preserve legacy producer contracts. The B5 version marker selects the strict lifecycle.
 IF v.validation_summary IS DISTINCT FROM 'HAPI-927' THEN
   IF EXISTS(SELECT 1 FROM hidra_configuration_definition_version WHERE definition_id=NEW.definition_id AND validation_summary='HAPI-927') THEN
     RAISE EXCEPTION 'Exact active B5 version required' USING ERRCODE='23514';
   END IF;
   RETURN NEW;
 END IF;
 IF TG_OP='UPDATE' THEN RAISE EXCEPTION 'B5 override revisions are append only' USING ERRCODE='23514'; END IF;
 IF d.status<>'ACTIVE' OR d.requires_approval OR d.scoped OR d.sensitivity NOT IN ('PUBLIC','INTERNAL')
   OR NOT v.active OR v.definition_id<>NEW.definition_id OR v.schema_json IS NOT NULL
   OR NEW.status<>'ACTIVE' OR NEW.json_value IS NOT NULL OR NEW.secret_reference IS NOT NULL
   OR NEW.raw_value IS NULL OR length(NEW.raw_value)>2000 OR btrim(NEW.raw_value)<>NEW.raw_value
   OR NEW.created_by_actor_id IS NULL OR btrim(NEW.created_by_actor_id)=''
   OR NEW.environment !~ '^[A-Za-z0-9][A-Za-z0-9_.-]{0,39}$'
   OR NEW.effective_from IS NULL OR NEW.effective_to<=NEW.effective_from THEN
   RAISE EXCEPTION 'Invalid B5 value contract' USING ERRCODE='23514';
 END IF;
 IF (d.value_type='BOOLEAN' AND NEW.raw_value NOT IN ('true','false'))
   OR (d.value_type='NUMBER' AND (length(NEW.raw_value)>58 OR NEW.raw_value !~ '^-?(0|[1-9][0-9]*)(\.[0-9]+)?$'))
   OR d.value_type NOT IN ('STRING','BOOLEAN','NUMBER') THEN
   RAISE EXCEPTION 'Invalid B5 typed value' USING ERRCODE='23514';
 END IF;
 IF d.value_type='NUMBER' AND (length(replace(ltrim(NEW.raw_value,'-'),'.',''))>38
   OR (position('.' in NEW.raw_value)>0 AND length(split_part(NEW.raw_value,'.',2))>18)) THEN
   RAISE EXCEPTION 'B5 decimal bounds exceeded' USING ERRCODE='23514';
 END IF;
 IF EXISTS(SELECT 1 FROM public.hidra_configuration_value x
   LEFT JOIN public.hidra_configuration_override_retirement r ON r.value_id=x.id
   WHERE x.definition_id=NEW.definition_id
   AND x.environment=NEW.environment AND x.status='ACTIVE'
   AND (LEAST(x.effective_to,r.cutoff) IS NULL OR LEAST(x.effective_to,r.cutoff)>NEW.effective_from)
   AND (NEW.effective_to IS NULL OR x.effective_from IS NULL OR x.effective_from<NEW.effective_to)) THEN
   RAISE EXCEPTION 'Overlapping B5 override' USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END $$;

