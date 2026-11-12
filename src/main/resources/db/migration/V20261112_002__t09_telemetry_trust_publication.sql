-- T09: qualify immutable synthetic observations without changing raw ingestion or HMR-053.
-- Native company/source-point grants are separately provisioned; no implicit permission grants.
INSERT INTO public.hidra_identity_permission
(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
('t09-qualify','telemetry:trusted-reading:qualify','telemetry','TELEMETRY_READING','qualify',
 true,'ACTIVE',now(),now()),
('t09-trusted-read','telemetry:trusted-reading:read','telemetry','TELEMETRY_READING','read',
 true,'ACTIVE',now(),now());

ALTER TABLE public.hidra_telemetry_trusted_reading
    ADD COLUMN binding_id varchar(80);
ALTER TABLE public.hidra_telemetry_trusted_reading
    ADD CONSTRAINT fk_t09_trusted_binding
        FOREIGN KEY (binding_id) REFERENCES public.hidra_telemetry_point_binding(id)
        ON DELETE RESTRICT,
    ADD CONSTRAINT uq_t09_one_trusted_per_raw UNIQUE (reading_id);
ALTER TABLE public.hidra_telemetry_quality_assessment
    ADD CONSTRAINT uq_t09_one_assessment_per_raw UNIQUE (reading_id);

CREATE FUNCTION public.hidra_t09_validate_binding() RETURNS trigger
LANGUAGE plpgsql SECURITY INVOKER SET search_path=pg_catalog,public AS $$
DECLARE v_binding public.hidra_telemetry_point_binding%ROWTYPE;
BEGIN
    IF NEW.binding_id IS NULL THEN RETURN NEW; END IF; -- Historical private records remain valid.
    SELECT * INTO v_binding FROM public.hidra_telemetry_point_binding
        WHERE id=NEW.binding_id FOR SHARE;
    IF NOT FOUND OR v_binding.point_id IS DISTINCT FROM NEW.point_id
        OR NOT v_binding.active
        OR v_binding.valid_from > NEW.trusted_at
        OR (v_binding.valid_to IS NOT NULL AND NEW.trusted_at >= v_binding.valid_to)
        OR (v_binding.topology_asset_type_code,v_binding.topology_asset_id,
            v_binding.topology_asset_code,v_binding.topology_snapshot_id)
            IS DISTINCT FROM
           (NEW.topology_asset_type_code,NEW.topology_asset_id,
            NEW.topology_asset_code,NEW.topology_snapshot_id) THEN
        RAISE EXCEPTION 'T09 trusted binding provenance mismatch' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END
$$;
CREATE TRIGGER trg_t09_validate_binding
    BEFORE INSERT ON public.hidra_telemetry_trusted_reading
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t09_validate_binding();

CREATE FUNCTION public.hidra_t09_deny_evidence_mutation() RETURNS trigger
LANGUAGE plpgsql SECURITY INVOKER SET search_path=pg_catalog,public AS $$
BEGIN
    RAISE EXCEPTION 'T09 immutable assessment/publication evidence' USING ERRCODE='23514';
END
$$;
CREATE TRIGGER trg_t09_assessment_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_telemetry_quality_assessment
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t09_deny_evidence_mutation();
CREATE TRIGGER trg_t09_trusted_delete_immutable
    BEFORE DELETE ON public.hidra_telemetry_trusted_reading
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t09_deny_evidence_mutation();
