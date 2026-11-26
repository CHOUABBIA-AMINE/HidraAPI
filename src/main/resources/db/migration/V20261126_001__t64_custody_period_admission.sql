-- T64 protected period admission. Additive, no retrospective enrollment or backfill.
INSERT INTO public.hidra_identity_permission
 (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES ('t64-custody-periods-create','custody:periods:create','custody','MEASUREMENT_PERIOD','create',true,'ACTIVE',now(),now())
ON CONFLICT (code) DO NOTHING;

ALTER TABLE public.hidra_custody_measurement_period
 ADD CONSTRAINT uq_t64_period_identity UNIQUE(id,agreement_id,transfer_point_id);

CREATE TABLE public.hidra_custody_period_admission (
 period_id varchar(80) PRIMARY KEY,
 agreement_id varchar(80) NOT NULL CHECK(length(btrim(agreement_id))>0 AND agreement_id=btrim(agreement_id)),
 transfer_point_id varchar(80) NOT NULL CHECK(length(btrim(transfer_point_id))>0 AND transfer_point_id=btrim(transfer_point_id)),
 company_id varchar(80) NOT NULL CHECK(length(btrim(company_id))>0 AND company_id=btrim(company_id)),
 facility_id varchar(80) NOT NULL CHECK(length(btrim(facility_id))>0 AND facility_id=btrim(facility_id)),
 command_id uuid NOT NULL,
 actor_id varchar(80) NOT NULL CHECK(length(btrim(actor_id))>0 AND actor_id=btrim(actor_id)),
 admitted_at timestamptz NOT NULL CHECK(isfinite(admitted_at)),
 payload_fingerprint char(64) NOT NULL CHECK(payload_fingerprint ~ '^[0-9a-f]{64}$'),
 CHECK(length(btrim(period_id))>0 AND period_id=btrim(period_id)),
 UNIQUE(company_id,transfer_point_id,command_id),
 FOREIGN KEY(period_id,agreement_id,transfer_point_id)
   REFERENCES public.hidra_custody_measurement_period(id,agreement_id,transfer_point_id) ON DELETE RESTRICT
);
CREATE INDEX ix_t64_admission_point_company ON public.hidra_custody_period_admission(transfer_point_id,company_id);

CREATE FUNCTION public.hidra_t64_admission_insert_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE p public.hidra_custody_measurement_period%ROWTYPE;
BEGIN
 SELECT * INTO p FROM public.hidra_custody_measurement_period WHERE id=NEW.period_id FOR UPDATE;
 IF NOT FOUND OR p.status<>'OPEN' OR p.agreement_id<>NEW.agreement_id OR p.transfer_point_id<>NEW.transfer_point_id
    OR NOT isfinite(p.period_start) OR NOT isfinite(p.period_end) OR p.period_start>=p.period_end
    OR length(btrim(p.period_code))=0 OR p.period_code<>btrim(p.period_code)
    OR p.created_at<>NEW.admitted_at OR p.updated_at<>NEW.admitted_at
    OR p.locked_by_actor_id IS NOT NULL OR p.locked_at IS NOT NULL
    OR p.approved_by_actor_id IS NOT NULL OR p.approved_at IS NOT NULL
    OR EXISTS(SELECT 1 FROM public.hidra_custody_measurement_receipt WHERE period_id=NEW.period_id)
    OR EXISTS(SELECT 1 FROM public.hidra_custody_t21_reconciliation WHERE period_id=NEW.period_id)
    OR EXISTS(SELECT 1 FROM public.hidra_custody_transfer_ticket WHERE measurement_period_id=NEW.period_id)
    OR EXISTS(SELECT 1 FROM public.hidra_custody_reconciliation WHERE measurement_period_id=NEW.period_id)
    OR EXISTS(SELECT 1 FROM public.hidra_custody_measurement_snapshot WHERE measurement_period_id=NEW.period_id)
 THEN
    RAISE EXCEPTION 'Invalid protected period admission context' USING ERRCODE='23514';
 END IF;
 IF NOT EXISTS(SELECT 1 FROM public.hidra_custody_transfer_point tp
    JOIN public.hidra_custody_agreement a ON a.id=p.agreement_id AND a.transfer_point_id=tp.id
    WHERE tp.id=p.transfer_point_id AND tp.topology_asset_type_code='FACILITY'
      AND tp.topology_asset_id=NEW.facility_id AND tp.status='ACTIVE' AND a.status='ACTIVE'
      AND (tp.effective_from IS NULL OR p.period_start>=tp.effective_from)
      AND (tp.effective_to IS NULL OR p.period_end<=tp.effective_to)
      AND p.period_start>=a.valid_from AND (a.valid_to IS NULL OR p.period_end<=a.valid_to))
 THEN RAISE EXCEPTION 'Invalid protected period admission window or point' USING ERRCODE='23514'; END IF;
 RETURN NEW;
END; $$;
CREATE TRIGGER hidra_t64_admission_insert_guard BEFORE INSERT ON public.hidra_custody_period_admission
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t64_admission_insert_guard();

CREATE FUNCTION public.hidra_t64_admission_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN RAISE EXCEPTION 'Protected period admission evidence is immutable' USING ERRCODE='23514'; END; $$;
CREATE TRIGGER hidra_t64_admission_immutable BEFORE UPDATE OR DELETE ON public.hidra_custody_period_admission
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t64_admission_immutable();
CREATE TRIGGER hidra_t64_admission_no_truncate BEFORE TRUNCATE ON public.hidra_custody_period_admission
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_t64_admission_immutable();

CREATE FUNCTION public.hidra_t64_period_identity_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF EXISTS(SELECT 1 FROM public.hidra_custody_period_admission WHERE period_id=OLD.id)
    AND (NEW.id,NEW.agreement_id,NEW.transfer_point_id,NEW.period_code,NEW.period_start,NEW.period_end,NEW.created_at)
        IS DISTINCT FROM (OLD.id,OLD.agreement_id,OLD.transfer_point_id,OLD.period_code,OLD.period_start,OLD.period_end,OLD.created_at)
 THEN RAISE EXCEPTION 'Protected period identity is immutable' USING ERRCODE='23514'; END IF;
 RETURN NEW;
END; $$;
CREATE TRIGGER hidra_t64_period_identity_guard BEFORE UPDATE ON public.hidra_custody_measurement_period
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t64_period_identity_guard();

-- Serialize direct evidence insertions with parent admission. No cross-owner FK.
CREATE FUNCTION public.hidra_t64_child_scope_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE ad public.hidra_custody_period_admission%ROWTYPE;
BEGIN
 PERFORM id FROM public.hidra_custody_measurement_period WHERE id=NEW.period_id FOR UPDATE;
 SELECT * INTO ad FROM public.hidra_custody_period_admission WHERE period_id=NEW.period_id;
 IF FOUND THEN
   IF NEW.company_id<>ad.company_id OR NEW.transfer_point_id<>ad.transfer_point_id
      OR NOT EXISTS(SELECT 1 FROM public.hidra_custody_agreement_party
                    WHERE id=NEW.agreement_party_id AND agreement_id=ad.agreement_id)
   THEN RAISE EXCEPTION 'Protected period evidence scope mismatch' USING ERRCODE='23514'; END IF;
   IF TG_TABLE_NAME='hidra_custody_measurement_receipt' THEN
     IF NEW.asset_type<>'FACILITY' OR NEW.asset_id<>ad.facility_id
     THEN RAISE EXCEPTION 'Protected period receipt facility mismatch' USING ERRCODE='23514'; END IF;
   ELSE
     IF NEW.agreement_id<>ad.agreement_id
     THEN RAISE EXCEPTION 'Protected period reconciliation agreement mismatch' USING ERRCODE='23514'; END IF;
   END IF;
 END IF;
 RETURN NEW;
END; $$;
CREATE TRIGGER hidra_t64_receipt_scope_guard BEFORE INSERT ON public.hidra_custody_measurement_receipt
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t64_child_scope_guard();
CREATE TRIGGER hidra_t64_reconciliation_scope_guard BEFORE INSERT ON public.hidra_custody_t21_reconciliation
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t64_child_scope_guard();

CREATE FUNCTION public.hidra_t64_reject_legacy_period(parent_id varchar) RETURNS void LANGUAGE plpgsql AS $$
BEGIN
 PERFORM id FROM public.hidra_custody_measurement_period WHERE id=parent_id FOR UPDATE;
 IF EXISTS(SELECT 1 FROM public.hidra_custody_period_admission WHERE period_id=parent_id)
 THEN RAISE EXCEPTION 'Legacy Custody writes not admitted for protected period' USING ERRCODE='23514'; END IF;
END; $$;
CREATE FUNCTION public.hidra_t64_legacy_period_child_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF TG_OP<>'INSERT' THEN PERFORM public.hidra_t64_reject_legacy_period(OLD.measurement_period_id); END IF;
 IF TG_OP<>'DELETE' THEN PERFORM public.hidra_t64_reject_legacy_period(NEW.measurement_period_id); RETURN NEW; END IF;
 RETURN OLD;
END; $$;
CREATE TRIGGER hidra_t64_legacy_ticket_guard BEFORE INSERT OR UPDATE OR DELETE ON public.hidra_custody_transfer_ticket
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t64_legacy_period_child_guard();
CREATE TRIGGER hidra_t64_legacy_reconciliation_guard BEFORE INSERT OR UPDATE OR DELETE ON public.hidra_custody_reconciliation
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t64_legacy_period_child_guard();

CREATE FUNCTION public.hidra_t64_reject_legacy_discrepancy(rec_id varchar) RETURNS void LANGUAGE plpgsql AS $$
DECLARE parent_id varchar;
BEGIN
 FOR parent_id IN SELECT measurement_period_id FROM public.hidra_custody_reconciliation WHERE id=rec_id
     UNION SELECT period_id FROM public.hidra_custody_t21_reconciliation WHERE id=rec_id
 LOOP PERFORM public.hidra_t64_reject_legacy_period(parent_id); END LOOP;
END; $$;
CREATE FUNCTION public.hidra_t64_legacy_discrepancy_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF TG_OP<>'INSERT' THEN PERFORM public.hidra_t64_reject_legacy_discrepancy(OLD.reconciliation_id); END IF;
 IF TG_OP<>'DELETE' THEN PERFORM public.hidra_t64_reject_legacy_discrepancy(NEW.reconciliation_id); RETURN NEW; END IF;
 RETURN OLD;
END; $$;
CREATE TRIGGER hidra_t64_legacy_discrepancy_guard BEFORE INSERT OR UPDATE OR DELETE ON public.hidra_custody_discrepancy
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t64_legacy_discrepancy_guard();
