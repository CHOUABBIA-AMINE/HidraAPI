-- T06 stores exact submissions and an append-only publication head per company and pipeline.
ALTER TABLE public.hidra_topology_engineering_baseline_revision
    ADD UNIQUE (company_party_id, pipeline_system_id, manifest_id, version, canonical_sha256);

CREATE TABLE public.hidra_topology_baseline_submission (
    company_party_id varchar(80) NOT NULL,
    pipeline_system_id varchar(80) NOT NULL,
    submission_id uuid NOT NULL,
    manifest_id uuid NOT NULL,
    version bigint NOT NULL,
    canonical_sha256 varchar(64) NOT NULL,
    submitted_by varchar(80) NOT NULL,
    submitted_at timestamptz NOT NULL,
    PRIMARY KEY (company_party_id, pipeline_system_id, submission_id),
    FOREIGN KEY (company_party_id, pipeline_system_id, manifest_id, version, canonical_sha256)
        REFERENCES public.hidra_topology_engineering_baseline_revision
            (company_party_id, pipeline_system_id, manifest_id, version, canonical_sha256)
);

CREATE TABLE public.hidra_topology_baseline_publication (
    company_party_id varchar(80) NOT NULL,
    pipeline_system_id varchar(80) NOT NULL,
    receipt_id uuid NOT NULL,
    submission_id uuid NOT NULL,
    epoch bigint NOT NULL CHECK (epoch > 0),
    definition_id varchar(80) NOT NULL,
    definition_version integer NOT NULL CHECK (definition_version > 0),
    target_type_id varchar(80) NOT NULL,
    purpose_id varchar(80) NOT NULL,
    instance_id varchar(80) NOT NULL,
    task_id varchar(80) NOT NULL,
    action_id varchar(80) NOT NULL UNIQUE,
    reviewer_id varchar(80) NOT NULL,
    reviewed_at timestamptz NOT NULL,
    published_by varchar(80) NOT NULL,
    published_at timestamptz NOT NULL CHECK (published_at >= reviewed_at),
    PRIMARY KEY (company_party_id, pipeline_system_id, epoch),
    UNIQUE (company_party_id, pipeline_system_id, receipt_id),
    UNIQUE (company_party_id, pipeline_system_id, submission_id),
    FOREIGN KEY (company_party_id, pipeline_system_id, submission_id)
        REFERENCES public.hidra_topology_baseline_submission
            (company_party_id, pipeline_system_id, submission_id)
);

CREATE TRIGGER baseline_submission_immutable
    BEFORE UPDATE OR DELETE OR TRUNCATE ON public.hidra_topology_baseline_submission
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_baseline_immutable_guard();
CREATE TRIGGER baseline_publication_immutable
    BEFORE UPDATE OR DELETE OR TRUNCATE ON public.hidra_topology_baseline_publication
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_baseline_immutable_guard();

REVOKE ALL ON public.hidra_topology_baseline_submission, public.hidra_topology_baseline_publication FROM PUBLIC;

INSERT INTO public.hidra_identity_permission
    (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
    ('t06-baseline-submit','topology:engineering-baseline:submit','topology','ENGINEERING_BASELINE_DRAFT','submit',true,'ACTIVE',now(),now()),
    ('t06-baseline-review','topology:engineering-baseline:review','topology','ENGINEERING_BASELINE_DRAFT','review',true,'ACTIVE',now(),now()),
    ('t06-baseline-publish','topology:engineering-baseline:publish','topology','ENGINEERING_BASELINE_DRAFT','publish',true,'ACTIVE',now(),now());

INSERT INTO public.hidra_workflow_type_catalog
    (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
VALUES
    ('t06-baseline-target','WORKFLOW_TARGET_TYPE','ENGINEERING_BASELINE_REVISION',true,0,true,now(),now()),
    ('t06-baseline-purpose','WORKFLOW_PURPOSE','ENGINEERING_BASELINE_APPROVAL',true,0,true,now(),now());

CREATE FUNCTION public.hidra_topology_baseline_lock_publication() RETURNS boolean
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    LOCK TABLE public.hidra_topology_baseline_submission, public.hidra_topology_baseline_publication
        IN SHARE ROW EXCLUSIVE MODE NOWAIT;
    RETURN true;
END;
$body$;
REVOKE ALL ON FUNCTION public.hidra_topology_baseline_lock_publication() FROM PUBLIC;
