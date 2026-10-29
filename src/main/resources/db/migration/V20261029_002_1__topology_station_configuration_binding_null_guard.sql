-- HAPI-600 Package A: reject SQL UNKNOWN for occupied installation revisions.
-- Additive correction; historical migrations and all other binding rules are preserved.
ALTER TABLE public.hidra_topology_station_configuration_member
    DROP CONSTRAINT ck_station_configuration_member_binding,
    ADD CONSTRAINT ck_station_configuration_member_binding CHECK (
        (disposition='UNOCCUPIED' AND installed_unit_id IS NULL AND
         installation_id IS NULL AND installation_revision IS NULL AND
         installation_from IS NULL AND installation_until IS NULL AND
         installation_evidence_reference IS NULL)
      OR
        (disposition IN ('CONNECTED','ISOLATED') AND installed_unit_id IS NOT NULL AND
         installation_id IS NOT NULL AND installation_revision IS NOT NULL AND installation_revision>0 AND
         installation_from IS NOT NULL AND
         (installation_until IS NULL OR installation_until>installation_from) AND
         nullif(btrim(installation_evidence_reference),'') IS NOT NULL));
