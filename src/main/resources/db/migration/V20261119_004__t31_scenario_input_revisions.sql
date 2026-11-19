-- T31: additive, engine-neutral input preparation. No solver, no Topology private FK,
-- no retroactive reinterpretation of legacy Simulation scenarios or runs.
INSERT INTO public.hidra_identity_permission
(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
('t31-scenario-inputs-read','simulation:scenario-inputs:read','simulation',
 'SCENARIO_INPUT_REVISION','read',true,'ACTIVE',now(),now()),
('t31-scenario-inputs-write','simulation:scenario-inputs:write','simulation',
 'SCENARIO_INPUT_REVISION','write',true,'ACTIVE',now(),now());

-- Exact original company/pipeline, immutable Topology receipt/manifest/epoch/digest
-- and typed values are stored together for historical reconstruction.
CREATE TABLE public.hidra_simulation_t31_scenario_input_revision (
    scenario_id uuid NOT NULL,
    revision bigint NOT NULL CHECK(revision >= 1),
    predecessor_revision bigint,
    company_id varchar(80) NOT NULL CHECK(company_id <> '' AND company_id=btrim(company_id)),
    pipeline_system_id varchar(80) NOT NULL CHECK(pipeline_system_id <> '' AND pipeline_system_id=btrim(pipeline_system_id)),
    baseline_receipt_id uuid NOT NULL,
    manifest_id uuid NOT NULL,
    baseline_epoch bigint NOT NULL CHECK(baseline_epoch > 0),
    baseline_digest char(64) NOT NULL CHECK(baseline_digest ~ '^[a-f0-9]{64}$'),
    scenario_name varchar(120) NOT NULL CHECK(scenario_name <> '' AND scenario_name=btrim(scenario_name)),
    mode varchar(12) NOT NULL CHECK(mode IN ('STEADY','TRANSIENT')),
    pressure_value numeric NOT NULL CHECK(pressure_value > 0),
    pressure_unit varchar(16) NOT NULL CHECK(pressure_unit IN ('PA','BAR_ABSOLUTE')),
    pressure_si_pa numeric NOT NULL CHECK(pressure_si_pa = pressure_value *
                         CASE WHEN pressure_unit='BAR_ABSOLUTE' THEN 100000 ELSE 1 END),
    flow_value numeric NOT NULL CHECK(flow_value >= 0),
    flow_unit varchar(12) NOT NULL CHECK(flow_unit='KG_PER_S'),
    flow_si_kg_per_s numeric NOT NULL CHECK(flow_si_kg_per_s=flow_value),
    duration_seconds integer,
    step_seconds integer,
    actor_id varchar(80) NOT NULL CHECK(actor_id <> '' AND actor_id=btrim(actor_id)),
    recorded_at timestamptz NOT NULL,
    policy_id varchar(16) NOT NULL CHECK(policy_id='T31_INPUT_V1'),
    input_digest char(64) NOT NULL CHECK(input_digest ~ '^[a-f0-9]{64}$'),
    request_key varchar(80) NOT NULL CHECK(request_key<>'' AND request_key=btrim(request_key)),
    request_fingerprint char(64) NOT NULL CHECK(request_fingerprint ~ '^[a-f0-9]{64}$'),
    CONSTRAINT t31_revision_pk PRIMARY KEY(scenario_id,revision),
    CONSTRAINT t31_scoped_revision_key UNIQUE(scenario_id,revision,company_id,pipeline_system_id),
    CONSTRAINT t31_predecessor_consistency CHECK (
       (revision=1 AND predecessor_revision IS NULL)
       OR (revision>1 AND predecessor_revision=revision-1)),
    CONSTRAINT t31_same_owner_predecessor FOREIGN KEY
       (scenario_id,predecessor_revision,company_id,pipeline_system_id)
       REFERENCES public.hidra_simulation_t31_scenario_input_revision
       (scenario_id,revision,company_id,pipeline_system_id) ON DELETE RESTRICT,
    CONSTRAINT t31_original_actor_request UNIQUE(company_id,pipeline_system_id,actor_id,request_key),
    CONSTRAINT t31_window_shape CHECK (
       (mode='STEADY' AND duration_seconds IS NULL AND step_seconds IS NULL)
       OR
       (mode='TRANSIENT' AND duration_seconds>0 AND step_seconds>0
         AND MOD(duration_seconds,step_seconds)=0
         AND duration_seconds / NULLIF(step_seconds,0)<=10000)),
    CONSTRAINT t31_bound_numeric CHECK (
       scale(pressure_value)<=6 AND scale(flow_value)<=6
       AND abs(pressure_value)<1000000000000000000
       AND abs(flow_value)<1000000000000000000)
);
CREATE INDEX t31_scenario_scope_head ON public.hidra_simulation_t31_scenario_input_revision
   (company_id,pipeline_system_id,scenario_id,revision DESC);

-- Immutable even for accidental direct DML: UPDATE, DELETE and TRUNCATE are forbidden.
CREATE FUNCTION public.t31_simulation_immutable_revision()
RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    RAISE EXCEPTION 'T31 immutable scenario input revision' USING ERRCODE='23514';
END $$;
CREATE TRIGGER t31_revision_update_delete_guard
BEFORE UPDATE OR DELETE ON public.hidra_simulation_t31_scenario_input_revision
FOR EACH ROW EXECUTE FUNCTION public.t31_simulation_immutable_revision();
CREATE TRIGGER t31_revision_truncate_guard
BEFORE TRUNCATE ON public.hidra_simulation_t31_scenario_input_revision
FOR EACH STATEMENT EXECUTE FUNCTION public.t31_simulation_immutable_revision();
