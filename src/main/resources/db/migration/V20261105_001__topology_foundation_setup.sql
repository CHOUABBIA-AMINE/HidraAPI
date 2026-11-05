-- HAPI-926 owner-approved fresh-schema setup. No migration history rewrite or active grants.
CREATE TABLE public.hidra_topology_foundation_system_owner (
 pipeline_system_id varchar(80) PRIMARY KEY REFERENCES public.hidra_topology_pipeline_system(id),
 company_party_id varchar(80) NOT NULL CHECK(length(btrim(company_party_id))>0 AND company_party_id=btrim(company_party_id))
);
CREATE TABLE public.hidra_topology_foundation_request (
 company_party_id varchar(80) NOT NULL CHECK(length(btrim(company_party_id))>0 AND company_party_id=btrim(company_party_id)),
 request_id uuid NOT NULL,
 operation varchar(40) NOT NULL CHECK(operation IN ('CREATE_SYSTEM','REGISTER_FACILITY','ACTIVATE_FACILITY','REGISTER_POSITION')),
 resource_id varchar(80) NOT NULL CHECK(length(btrim(resource_id))>0 AND resource_id=btrim(resource_id)),
 command_sha256 varchar(64) NOT NULL CHECK(command_sha256 ~ '^[0-9a-f]{64}$'),
 result_json jsonb NOT NULL CHECK(jsonb_typeof(result_json)='object'),
 actor_user_id varchar(80) NOT NULL,
 actor_binding_id varchar(80) NOT NULL,
 actor_session_id varchar(80) NOT NULL,
 sampled_at timestamptz NOT NULL,
 valid_until timestamptz NOT NULL CHECK(valid_until>sampled_at),
 backend_pid integer NOT NULL CHECK(backend_pid>0),
 transaction_id bigint NOT NULL CHECK(transaction_id>0),
 reason varchar(1024) NOT NULL CHECK(length(btrim(reason))>0 AND reason=btrim(reason)),
 PRIMARY KEY(company_party_id,request_id)
);
CREATE TRIGGER foundation_owner_immutable BEFORE UPDATE OR DELETE ON public.hidra_topology_foundation_system_owner FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER foundation_owner_no_truncate BEFORE TRUNCATE ON public.hidra_topology_foundation_system_owner FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER foundation_request_immutable BEFORE UPDATE OR DELETE ON public.hidra_topology_foundation_request FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER foundation_request_no_truncate BEFORE TRUNCATE ON public.hidra_topology_foundation_request FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
REVOKE ALL ON public.hidra_topology_foundation_system_owner,public.hidra_topology_foundation_request FROM PUBLIC;
-- Permissions are catalog declarations only; operators receive deliberately provisioned current scopes.
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES ('topology-foundation-read','topology:foundation:read','topology','TOPOLOGY_FOUNDATION','read',false,'ACTIVE',now(),now()),
       ('topology-foundation-write','topology:foundation:write','topology','TOPOLOGY_FOUNDATION','write',true,'ACTIVE',now(),now());
