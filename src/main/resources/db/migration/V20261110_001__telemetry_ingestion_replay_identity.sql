-- HAPI-933: one owner-approved synthetic raw-reading admission; immutable prior Flyway files.
-- The deferred reading FK permits unique receipt reservation BEFORE the first raw insert.
CREATE TABLE public.hidra_telemetry_reading_ingest_receipt (
    source_id varchar(80) NOT NULL REFERENCES public.hidra_telemetry_source(id),
    point_id varchar(80) NOT NULL REFERENCES public.hidra_telemetry_point(id),
    source_sequence_number varchar(160) NOT NULL,
    reading_id varchar(80) NOT NULL,
    payload_digest varchar(64) NOT NULL,
    unit_id varchar(80) REFERENCES public.hidra_telemetry_unit(id),
    mapping_id varchar(80) REFERENCES public.hidra_telemetry_external_tag_mapping(id),
    received_at timestamptz NOT NULL,
    CONSTRAINT pk_hapi933_ingest_replay PRIMARY KEY(source_id,point_id,source_sequence_number),
    CONSTRAINT uq_hapi933_ingest_reading UNIQUE(reading_id),
    CONSTRAINT fk_hapi933_ingest_reading FOREIGN KEY(reading_id)
        REFERENCES public.hidra_telemetry_reading(id) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT ck_hapi933_ingest_sequence CHECK(length(btrim(source_sequence_number))>0),
    CONSTRAINT ck_hapi933_ingest_digest CHECK(payload_digest ~ '^[0-9a-f]{64}$'),
    CONSTRAINT ck_hapi933_ingest_time CHECK(isfinite(received_at))
);
CREATE INDEX ix_hapi933_ingest_point ON public.hidra_telemetry_reading_ingest_receipt(point_id);
-- Only the migration owner may define the receipt policy. No arbitrary runtime updates or deletes.
CREATE FUNCTION public.hidra_hapi933_receipt_immutable()
RETURNS trigger LANGUAGE plpgsql SECURITY INVOKER SET search_path=pg_catalog,public AS $$
BEGIN
    RAISE EXCEPTION 'HAPI-933 ingestion receipt is immutable' USING ERRCODE='23514';
END
$$;
CREATE TRIGGER trg_hapi933_receipt_immutable
BEFORE UPDATE OR DELETE ON public.hidra_telemetry_reading_ingest_receipt
FOR EACH ROW EXECUTE FUNCTION public.hidra_hapi933_receipt_immutable();
CREATE TRIGGER trg_hapi933_receipt_no_truncate
BEFORE TRUNCATE ON public.hidra_telemetry_reading_ingest_receipt
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_hapi933_receipt_immutable();
REVOKE ALL ON public.hidra_telemetry_reading_ingest_receipt FROM PUBLIC;
DO $grant$
BEGIN
    IF EXISTS(SELECT 1 FROM pg_catalog.pg_roles WHERE rolname='hidra') THEN
        GRANT SELECT,INSERT ON public.hidra_telemetry_reading_ingest_receipt TO hidra;
    END IF;
END
$grant$;
INSERT INTO public.hidra_identity_permission
(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
('hapi933-ingest','telemetry:raw-reading:ingest','telemetry','TELEMETRY_READING','ingest',true,'ACTIVE',now(),now()),
('hapi933-read','telemetry:raw-reading:read','telemetry','TELEMETRY_READING','read',false,'ACTIVE',now(),now());
