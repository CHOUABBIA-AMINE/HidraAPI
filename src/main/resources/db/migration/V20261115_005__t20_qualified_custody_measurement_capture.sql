-- T20: software-only synthetic qualified observed custody interval.
-- No fiscal acceptance, interval integration, conversion or field/OT actuation.
INSERT INTO public.hidra_identity_permission
 (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
 ('t20-custody-measurements-capture','custody:measurements:capture','custody','MEASUREMENT','capture',true,'ACTIVE',now(),now()),
 ('t20-custody-measurements-read','custody:measurements:read','custody','MEASUREMENT','read',true,'ACTIVE',now(),now())
ON CONFLICT (code) DO NOTHING;

-- Telemetry-owned immutable interval attestation. No cross-owner database foreign keys.
-- Fixture authors attested intervals from real qualified trusted readings; no client authoring API.
CREATE TABLE public.hidra_telemetry_custody_interval_evidence (
 id varchar(80) PRIMARY KEY CHECK(length(btrim(id)) BETWEEN 1 AND 80),
 trusted_reading_id varchar(80) NOT NULL
   REFERENCES public.hidra_telemetry_trusted_reading(id) ON DELETE RESTRICT,
 meter_run_snapshot_id varchar(80) NOT NULL CHECK(length(btrim(meter_run_snapshot_id)) BETWEEN 1 AND 80),
 metering_system_id varchar(80) NOT NULL CHECK(length(btrim(metering_system_id)) BETWEEN 1 AND 80),
 asset_type varchar(80) NOT NULL CHECK(asset_type='FACILITY'),
 asset_id varchar(80) NOT NULL CHECK(length(btrim(asset_id)) BETWEEN 1 AND 80),
 measurement_kind varchar(40) NOT NULL CHECK(measurement_kind='INTERVAL_VOLUME_TOTAL'),
 observed_quantity numeric(18,6) NOT NULL CHECK(observed_quantity>=0),
 unit_id varchar(80) NOT NULL REFERENCES public.hidra_telemetry_unit(id) ON DELETE RESTRICT,
 interval_start timestamptz NOT NULL CHECK(isfinite(interval_start)),
 interval_end timestamptz NOT NULL CHECK(isfinite(interval_end)),
 attested_at timestamptz NOT NULL DEFAULT now() CHECK(isfinite(attested_at)),
 CHECK(interval_start<interval_end),
 UNIQUE(id,trusted_reading_id,unit_id)
);
CREATE INDEX ix_t20_telemetry_interval_trusted
 ON public.hidra_telemetry_custody_interval_evidence(trusted_reading_id,interval_start,interval_end);

-- One Custody-owned immutable receipt and legacy JPA-compatible snapshot per capture.
-- All cross-owner references are scalar immutable evidence, not shared aggregate FKs.
CREATE TABLE public.hidra_custody_measurement_receipt (
 measurement_id varchar(80) PRIMARY KEY
   REFERENCES public.hidra_custody_measurement_snapshot(id) ON DELETE RESTRICT,
 period_id varchar(80) NOT NULL
   REFERENCES public.hidra_custody_measurement_period(id) ON DELETE RESTRICT,
 command_id uuid NOT NULL,
 payload_fingerprint char(64) NOT NULL CHECK(payload_fingerprint ~ '^[a-f0-9]{64}$'),
 meter_run_snapshot_id varchar(80) NOT NULL
   REFERENCES public.hidra_custody_meter_run_snapshot(id) ON DELETE RESTRICT,
 metering_system_id varchar(80) NOT NULL
   REFERENCES public.hidra_custody_metering_system(id) ON DELETE RESTRICT,
 measurement_type_id varchar(80) NOT NULL
   REFERENCES public.hidra_custody_catalog_entry(id) ON DELETE RESTRICT,
 product_publication_id uuid NOT NULL,
 agreement_party_id varchar(80) NOT NULL
   REFERENCES public.hidra_custody_agreement_party(id) ON DELETE RESTRICT,
 company_id varchar(80) NOT NULL CHECK(length(btrim(company_id)) BETWEEN 1 AND 80),
 transfer_point_id varchar(80) NOT NULL
   REFERENCES public.hidra_custody_transfer_point(id) ON DELETE RESTRICT,
 asset_type varchar(80) NOT NULL CHECK(asset_type='FACILITY'),
 asset_id varchar(80) NOT NULL CHECK(length(btrim(asset_id)) BETWEEN 1 AND 80),
 trusted_reading_id varchar(80) NOT NULL,
 raw_reading_id varchar(80) NOT NULL,
 assessment_id varchar(80) NOT NULL,
 point_id varchar(80) NOT NULL,
 binding_id varchar(80) NOT NULL,
 interval_evidence_id varchar(80) NOT NULL,
 observed_quantity numeric(18,6) NOT NULL CHECK(observed_quantity>=0),
 unit_id varchar(80) NOT NULL,
 quantity_basis varchar(20) NOT NULL CHECK(quantity_basis='OBSERVED'),
 interval_start timestamptz NOT NULL CHECK(isfinite(interval_start)),
 interval_end timestamptz NOT NULL CHECK(isfinite(interval_end)),
 actor_id varchar(80) NOT NULL CHECK(length(btrim(actor_id)) BETWEEN 1 AND 80),
 captured_at timestamptz NOT NULL CHECK(isfinite(captured_at)),
 CHECK(interval_start<interval_end),
 UNIQUE(period_id,command_id),
 UNIQUE(period_id,meter_run_snapshot_id,measurement_type_id,interval_evidence_id)
);
CREATE INDEX ix_t20_custody_receipt_period ON public.hidra_custody_measurement_receipt(period_id,captured_at);
CREATE INDEX ix_t20_custody_receipt_company_point
 ON public.hidra_custody_measurement_receipt(company_id,transfer_point_id,captured_at);

CREATE FUNCTION public.hidra_t20_append_only() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
 RAISE EXCEPTION 'T20 qualified interval and Custody receipt evidence is immutable' USING ERRCODE='23514';
END;
$$;
CREATE TRIGGER hidra_t20_telemetry_evidence_immutable
 BEFORE UPDATE OR DELETE ON public.hidra_telemetry_custody_interval_evidence
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t20_append_only();
CREATE TRIGGER hidra_t20_telemetry_evidence_no_truncate
 BEFORE TRUNCATE ON public.hidra_telemetry_custody_interval_evidence
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_t20_append_only();
CREATE TRIGGER hidra_t20_custody_receipt_immutable
 BEFORE UPDATE OR DELETE ON public.hidra_custody_measurement_receipt
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t20_append_only();
CREATE TRIGGER hidra_t20_custody_receipt_no_truncate
 BEFORE TRUNCATE ON public.hidra_custody_measurement_receipt
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_t20_append_only();
-- A T20 snapshot may not diverge from its immutable companion receipt.
CREATE FUNCTION public.hidra_t20_measurement_snapshot_guard() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
 IF EXISTS(SELECT 1 FROM public.hidra_custody_measurement_receipt
            WHERE measurement_id=OLD.id) THEN
    RAISE EXCEPTION 'T20 attested measurement snapshot is immutable' USING ERRCODE='23514';
 END IF;
 RETURN OLD;
END;
$$;
CREATE TRIGGER hidra_t20_measurement_snapshot_immutable
 BEFORE UPDATE OR DELETE ON public.hidra_custody_measurement_snapshot
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t20_measurement_snapshot_guard();
