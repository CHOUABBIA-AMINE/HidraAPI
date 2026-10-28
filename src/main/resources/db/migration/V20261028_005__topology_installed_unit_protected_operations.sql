-- HAPI-599 Package B. Additive owned reference and immutable operation receipts.
-- No runtime role, source DML or broad EXECUTE grant is provisioned by this migration.
ALTER TABLE public.hidra_topology_installed_unit
 ADD CONSTRAINT fk_installed_unit_equipment_type FOREIGN KEY(equipment_kind_id)
 REFERENCES public.hidra_topology_equipment_type(id)
 ON UPDATE RESTRICT ON DELETE RESTRICT NOT DEFERRABLE;
CREATE TABLE public.hidra_topology_installed_unit_operation_request (
 request_id varchar(80) PRIMARY KEY,operation varchar(40) NOT NULL,
 company_party_id varchar(80) NOT NULL,unit_id varchar(80) NOT NULL,
 installation_id varchar(80),replacement_installation_id varchar(80),expected_revision bigint NOT NULL,
 canonical_command bytea NOT NULL,command_sha256 varchar(64) NOT NULL,
 result_payload bytea NOT NULL,result_sha256 varchar(64) NOT NULL,
 actor_user_id varchar(80) NOT NULL,binding_id varchar(80) NOT NULL,session_id varchar(80) NOT NULL,
 sampled_at timestamptz NOT NULL,valid_until timestamptz NOT NULL,
 backend_pid integer NOT NULL,transaction_id bigint NOT NULL,database_role name NOT NULL,
 FOREIGN KEY(unit_id,company_party_id) REFERENCES public.hidra_topology_installed_unit(id,company_party_id),
 CHECK(operation IN ('REGISTER_UNIT','RECORD_INSTALLATION','CLOSE_INSTALLATION','RELOCATE')),
 CHECK(expected_revision>=0 AND sampled_at<valid_until AND backend_pid>0 AND transaction_id>0),
 CHECK(octet_length(canonical_command)<=1048576 AND octet_length(result_payload)<=1048576)
);
CREATE FUNCTION public.hidra_topology_installed_unit_request_guard() RETURNS trigger
LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $receipt$
BEGIN
 IF TG_OP<>'INSERT' THEN
  RAISE EXCEPTION 'Installed unit receipts are immutable' USING ERRCODE='23514';
 END IF;
 IF NEW.backend_pid<>pg_backend_pid() OR NEW.transaction_id<>txid_current() OR NEW.database_role<>current_user
  OR clock_timestamp()<NEW.sampled_at OR clock_timestamp()>=NEW.valid_until
  OR NEW.command_sha256<>encode(sha256(NEW.canonical_command),'hex')
  OR NEW.result_sha256<>encode(sha256(NEW.result_payload),'hex')
  OR btrim(NEW.request_id)='' OR NEW.request_id<>btrim(NEW.request_id)
  OR btrim(NEW.actor_user_id)='' OR btrim(NEW.binding_id)='' OR btrim(NEW.session_id)='' THEN
  RAISE EXCEPTION 'Invalid installed unit receipt origin' USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END;
$receipt$;
REVOKE ALL ON FUNCTION public.hidra_topology_installed_unit_request_guard() FROM PUBLIC;
CREATE TRIGGER topology_installed_unit_request_guard BEFORE INSERT OR UPDATE OR DELETE
 ON public.hidra_topology_installed_unit_operation_request FOR EACH ROW
 EXECUTE FUNCTION public.hidra_topology_installed_unit_request_guard();
CREATE TRIGGER topology_installed_unit_request_no_truncate BEFORE TRUNCATE
 ON public.hidra_topology_installed_unit_operation_request FOR EACH STATEMENT
 EXECUTE FUNCTION public.hidra_topology_installed_unit_immutable();
