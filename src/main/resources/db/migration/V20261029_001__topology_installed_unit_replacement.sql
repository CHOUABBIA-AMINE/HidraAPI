-- HAPI-599 B. One immutable receipt binds both physical units for atomic replacement.
-- Existing rows and trigger/function definitions are preserved; no role/grant provisioning.
ALTER TABLE public.hidra_topology_installed_unit_operation_request
 ADD COLUMN replacement_unit_id varchar(80),
 ADD CONSTRAINT fk_installed_request_replacement_unit_company
 FOREIGN KEY(replacement_unit_id,company_party_id)
 REFERENCES public.hidra_topology_installed_unit(id,company_party_id)
 ON UPDATE RESTRICT ON DELETE RESTRICT NOT DEFERRABLE;
ALTER TABLE public.hidra_topology_installed_unit_operation_request
 DROP CONSTRAINT hidra_topology_installed_unit_operation_request_operation_check,
 ADD CONSTRAINT ck_installed_request_operation
 CHECK(operation IN ('REGISTER_UNIT','RECORD_INSTALLATION','CLOSE_INSTALLATION','RELOCATE','REPLACE')),
 ADD CONSTRAINT ck_installed_request_replacement_unit
 CHECK(CASE WHEN operation='REPLACE' THEN replacement_unit_id IS NOT NULL AND replacement_unit_id<>unit_id
    ELSE replacement_unit_id IS NULL END);
