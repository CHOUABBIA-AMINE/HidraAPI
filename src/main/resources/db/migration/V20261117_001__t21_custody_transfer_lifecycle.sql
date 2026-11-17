-- T21 Custody software lifecycle: immutable observed-volume reconciliation and append-only decisions.
-- Only synthetic GZ2 qualification is admitted. These records are NOT fiscal certificates.
-- No T20 receipt is promoted to accepted_for_custody or edited by this migration.

INSERT INTO public.hidra_identity_permission
 (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
 ('t21-custody-periods-read','custody:periods:read','custody','MEASUREMENT_PERIOD','read',true,'ACTIVE',now(),now()),
 ('t21-custody-reconciliations-read','custody:reconciliations:read','custody','RECONCILIATION','read',true,'ACTIVE',now(),now()),
 ('t21-custody-reconciliations-reconcile','custody:reconciliations:reconcile','custody','RECONCILIATION','reconcile',true,'ACTIVE',now(),now()),
 ('t21-custody-tickets-read','custody:tickets:read','custody','TRANSFER_TICKET','read',true,'ACTIVE',now(),now()),
 ('t21-custody-tickets-finalize','custody:tickets:finalize','custody','TRANSFER_TICKET','finalize',true,'ACTIVE',now(),now()),
 ('t21-custody-tickets-correct','custody:tickets:correct','custody','TRANSFER_TICKET','correct',true,'ACTIVE',now(),now()),
 ('t21-custody-discrepancies-read','custody:discrepancies:read','custody','DISCREPANCY','read',true,'ACTIVE',now(),now()),
 ('t21-custody-discrepancies-dispose','custody:discrepancies:dispose','custody','DISCREPANCY','dispose',true,'ACTIVE',now(),now())
ON CONFLICT (code) DO NOTHING;

CREATE TABLE public.hidra_custody_t21_reconciliation (
 id varchar(80) PRIMARY KEY,
 period_id varchar(80) NOT NULL REFERENCES public.hidra_custody_measurement_period(id) ON DELETE RESTRICT,
 agreement_id varchar(80) NOT NULL REFERENCES public.hidra_custody_agreement(id) ON DELETE RESTRICT,
 transfer_point_id varchar(80) NOT NULL REFERENCES public.hidra_custody_transfer_point(id) ON DELETE RESTRICT,
 company_id varchar(80) NOT NULL CHECK(length(btrim(company_id)) BETWEEN 1 AND 80),
 meter_run_snapshot_id varchar(80) NOT NULL REFERENCES public.hidra_custody_meter_run_snapshot(id) ON DELETE RESTRICT,
 product_publication_id uuid NOT NULL,
 agreement_party_id varchar(80) NOT NULL REFERENCES public.hidra_custody_agreement_party(id) ON DELETE RESTRICT,
 unit_id varchar(80) NOT NULL,
 digest char(64) NOT NULL CHECK(digest ~ '^[0-9a-f]{64}$'),
 ticket_id varchar(80) NOT NULL,
 parent_ticket_id varchar(80),
 observed_total numeric(18,6) NOT NULL CHECK(observed_total>=0),
 ticket_total numeric(18,6) NOT NULL CHECK(ticket_total>=0),
 difference_quantity numeric(18,6) NOT NULL,
 selection_version varchar(40) NOT NULL CHECK(selection_version='T21-OBSERVED-1'),
 actor_id varchar(80) NOT NULL CHECK(length(btrim(actor_id)) BETWEEN 1 AND 80),
 created_at timestamptz NOT NULL CHECK(isfinite(created_at)),
 CHECK(difference_quantity=ticket_total-observed_total),
 UNIQUE(id,ticket_id),
 UNIQUE(id,period_id,company_id,transfer_point_id),
 UNIQUE(ticket_id)
);
CREATE UNIQUE INDEX uq_t21_initial_reconciliation_per_period
 ON public.hidra_custody_t21_reconciliation(period_id) WHERE parent_ticket_id IS NULL;
CREATE INDEX ix_t21_reconciliation_period ON public.hidra_custody_t21_reconciliation(period_id,created_at);

-- Both the period and agreement are Custody-owned; guard their actual relationship.
-- Company ownership remains a live Topology assertion in the application boundary.
CREATE FUNCTION public.hidra_t21_reconciliation_context_guard() RETURNS trigger
LANGUAGE plpgsql AS $
BEGIN
 IF NOT EXISTS(
   SELECT 1 FROM public.hidra_custody_measurement_period p
   JOIN public.hidra_custody_agreement a ON a.id=p.agreement_id
   JOIN public.hidra_custody_transfer_point tp ON tp.id=p.transfer_point_id
   JOIN public.hidra_custody_meter_run_snapshot mr ON mr.measurement_period_id=p.id
   JOIN public.hidra_custody_metering_system ms ON ms.id=mr.metering_system_id
   JOIN public.hidra_custody_agreement_party ap ON ap.agreement_id=a.id
   WHERE p.id=NEW.period_id AND p.agreement_id=NEW.agreement_id
      AND p.transfer_point_id=NEW.transfer_point_id
      AND a.transfer_point_id=p.transfer_point_id AND tp.id=NEW.transfer_point_id
      AND mr.id=NEW.meter_run_snapshot_id
      AND ms.transfer_point_id=NEW.transfer_point_id
      AND ap.id=NEW.agreement_party_id
 ) THEN
    RAISE EXCEPTION 'T21 reconciliation is inconsistent with Custody period, agreement or metering'
       USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END;
$;
CREATE TRIGGER hidra_t21_reconciliation_context_guard
 BEFORE INSERT ON public.hidra_custody_t21_reconciliation
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t21_reconciliation_context_guard();

CREATE TABLE public.hidra_custody_t21_reconciliation_receipt (
 reconciliation_id varchar(80) NOT NULL
   REFERENCES public.hidra_custody_t21_reconciliation(id) ON DELETE RESTRICT,
 ordinal int NOT NULL CHECK(ordinal>=1),
 measurement_id varchar(80) NOT NULL
   REFERENCES public.hidra_custody_measurement_receipt(measurement_id) ON DELETE RESTRICT,
 PRIMARY KEY(reconciliation_id,ordinal),
 UNIQUE(reconciliation_id,measurement_id)
);

CREATE TABLE public.hidra_custody_t21_ticket (
 id varchar(80) PRIMARY KEY,
 reconciliation_id varchar(80) NOT NULL,
 period_id varchar(80) NOT NULL,
 agreement_id varchar(80) NOT NULL REFERENCES public.hidra_custody_agreement(id) ON DELETE RESTRICT,
 transfer_point_id varchar(80) NOT NULL REFERENCES public.hidra_custody_transfer_point(id) ON DELETE RESTRICT,
 company_id varchar(80) NOT NULL,
 calculation_id varchar(80) NOT NULL UNIQUE,
 approval_target_digest char(64) NOT NULL UNIQUE CHECK(approval_target_digest ~ '^[0-9a-f]{64}$'),
 issued_by varchar(80) NOT NULL CHECK(length(btrim(issued_by)) BETWEEN 1 AND 80),
 parent_ticket_id varchar(80) REFERENCES public.hidra_custody_t21_ticket(id) ON DELETE RESTRICT,
 version bigint NOT NULL CHECK(version>=1),
 ticket_quantity numeric(18,6) NOT NULL CHECK(ticket_quantity>=0),
 unit_id varchar(80) NOT NULL,
 issued_at timestamptz NOT NULL CHECK(isfinite(issued_at)),
 FOREIGN KEY(reconciliation_id,id)
    REFERENCES public.hidra_custody_t21_reconciliation(id,ticket_id) ON DELETE RESTRICT,
 FOREIGN KEY(reconciliation_id,period_id,company_id,transfer_point_id)
    REFERENCES public.hidra_custody_t21_reconciliation(id,period_id,company_id,transfer_point_id)
    ON DELETE RESTRICT,
 UNIQUE(period_id,version),
 UNIQUE(id,period_id,company_id,transfer_point_id)
);
CREATE INDEX ix_t21_ticket_period ON public.hidra_custody_t21_ticket(period_id,version);

CREATE TABLE public.hidra_custody_t21_calculation (
 id varchar(80) PRIMARY KEY,
 ticket_id varchar(80) NOT NULL UNIQUE
   REFERENCES public.hidra_custody_t21_ticket(id) ON DELETE RESTRICT,
 reconciliation_id varchar(80) NOT NULL REFERENCES public.hidra_custody_t21_reconciliation(id) ON DELETE RESTRICT,
 observed_total numeric(18,6) NOT NULL CHECK(observed_total>=0),
 ticket_total numeric(18,6) NOT NULL CHECK(ticket_total>=0),
 unit_id varchar(80) NOT NULL,
 basis varchar(20) NOT NULL CHECK(basis='OBSERVED'),
 method_version varchar(40) NOT NULL CHECK(method_version='T21-OBSERVED-1'),
 official boolean NOT NULL DEFAULT false CHECK(official=false),
 created_at timestamptz NOT NULL CHECK(isfinite(created_at)),
 UNIQUE(id,ticket_id)
);
ALTER TABLE public.hidra_custody_t21_ticket
 ADD CONSTRAINT fk_t21_ticket_calculation
 FOREIGN KEY(calculation_id,id)
 REFERENCES public.hidra_custody_t21_calculation(id,ticket_id)
 DEFERRABLE INITIALLY DEFERRED;

CREATE TABLE public.hidra_custody_t21_discrepancy (
 id varchar(80) PRIMARY KEY,
 reconciliation_id varchar(80) NOT NULL UNIQUE
    REFERENCES public.hidra_custody_t21_reconciliation(id) ON DELETE RESTRICT,
 ticket_id varchar(80) NOT NULL REFERENCES public.hidra_custody_t21_ticket(id) ON DELETE RESTRICT,
 difference_quantity numeric(18,6) NOT NULL CHECK(difference_quantity<>0),
 unit_id varchar(80) NOT NULL,
 opened_at timestamptz NOT NULL CHECK(isfinite(opened_at))
);
CREATE TABLE public.hidra_custody_t21_disposition (
 discrepancy_id varchar(80) PRIMARY KEY
   REFERENCES public.hidra_custody_t21_discrepancy(id) ON DELETE RESTRICT,
 disposition varchar(16) NOT NULL CHECK(disposition IN ('ACCEPTED','REJECTED')),
 reason varchar(80) NOT NULL CHECK(length(btrim(reason)) BETWEEN 1 AND 80),
 evidence_reference varchar(80) NOT NULL CHECK(length(btrim(evidence_reference)) BETWEEN 1 AND 80),
 actor_id varchar(80) NOT NULL CHECK(length(btrim(actor_id)) BETWEEN 1 AND 80),
 disposed_at timestamptz NOT NULL CHECK(isfinite(disposed_at))
);
CREATE TABLE public.hidra_custody_t21_finalization (
 ticket_id varchar(80) PRIMARY KEY
   REFERENCES public.hidra_custody_t21_ticket(id) ON DELETE RESTRICT,
 workflow_instance_id varchar(80) NOT NULL,
 workflow_task_id varchar(80) NOT NULL,
 workflow_action_id varchar(80) NOT NULL UNIQUE,
 reviewer_id varchar(80) NOT NULL CHECK(length(btrim(reviewer_id)) BETWEEN 1 AND 80),
 approved_at timestamptz NOT NULL CHECK(isfinite(approved_at)),
 finalized_at timestamptz NOT NULL CHECK(isfinite(finalized_at)),
 finalized_by varchar(80) NOT NULL,
 CHECK(approved_at<=finalized_at)
);
CREATE TABLE public.hidra_custody_t21_correction (
 original_ticket_id varchar(80) PRIMARY KEY
   REFERENCES public.hidra_custody_t21_ticket(id) ON DELETE RESTRICT,
 successor_ticket_id varchar(80) NOT NULL UNIQUE
   REFERENCES public.hidra_custody_t21_ticket(id) ON DELETE RESTRICT,
 reason varchar(80) NOT NULL CHECK(length(btrim(reason)) BETWEEN 1 AND 80),
 evidence_reference varchar(80) NOT NULL CHECK(length(btrim(evidence_reference)) BETWEEN 1 AND 80),
 actor_id varchar(80) NOT NULL CHECK(length(btrim(actor_id)) BETWEEN 1 AND 80),
 corrected_at timestamptz NOT NULL CHECK(isfinite(corrected_at)),
 CHECK(original_ticket_id<>successor_ticket_id)
);
CREATE TABLE public.hidra_custody_t21_command (
 operation varchar(16) NOT NULL CHECK(operation IN ('RECONCILE','DISPOSE','FINALIZE','CORRECT')),
 command_id uuid NOT NULL,
 actor_id varchar(80) NOT NULL CHECK(length(btrim(actor_id)) BETWEEN 1 AND 80),
 payload_fingerprint char(64) NOT NULL CHECK(payload_fingerprint ~ '^[0-9a-f]{64}$'),
 period_id varchar(80) NOT NULL REFERENCES public.hidra_custody_measurement_period(id) ON DELETE RESTRICT,
 resource_id varchar(80) NOT NULL,
 created_at timestamptz NOT NULL DEFAULT now() CHECK(isfinite(created_at)),
 PRIMARY KEY(operation,command_id)
);

-- The receipt belongs to the exact immutable scope of the reconciliation; PostgreSQL
-- rejects direct SQL inserts that try to substitute receipts from another owner/period.
CREATE FUNCTION public.hidra_t21_receipt_scope_guard() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
-- Serialize direct evidence membership writes on the same immutable parent.
 PERFORM id FROM public.hidra_custody_t21_reconciliation
  WHERE id=NEW.reconciliation_id FOR UPDATE;
 IF NOT FOUND THEN
   RAISE EXCEPTION 'T21 reconciliation is unavailable' USING ERRCODE='23514';
 END IF;
 IF EXISTS(
    SELECT 1 FROM public.hidra_custody_t21_reconciliation_receipt selected
    JOIN public.hidra_custody_measurement_receipt existing
      ON existing.measurement_id=selected.measurement_id
    JOIN public.hidra_custody_measurement_receipt incoming
      ON incoming.measurement_id=NEW.measurement_id
    WHERE selected.reconciliation_id=NEW.reconciliation_id
       AND incoming.interval_start<existing.interval_end
       AND existing.interval_start<incoming.interval_end
 ) THEN
   RAISE EXCEPTION 'T21 overlapping immutable observed interval is prohibited' USING ERRCODE='23514';
 END IF;
 IF NOT EXISTS (
    SELECT 1 FROM public.hidra_custody_t21_reconciliation rec
      JOIN public.hidra_custody_measurement_receipt r ON r.measurement_id=NEW.measurement_id
     WHERE rec.id=NEW.reconciliation_id
       AND r.period_id=rec.period_id AND r.company_id=rec.company_id
       AND r.transfer_point_id=rec.transfer_point_id AND r.meter_run_snapshot_id=rec.meter_run_snapshot_id
       AND r.product_publication_id=rec.product_publication_id AND r.agreement_party_id=rec.agreement_party_id
       AND r.unit_id=rec.unit_id AND r.quantity_basis='OBSERVED'
 ) THEN
   RAISE EXCEPTION 'T21 receipt and immutable reconciliation scope mismatch' USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END;
$$;
CREATE TRIGGER hidra_t21_receipt_scope_guard
 BEFORE INSERT ON public.hidra_custody_t21_reconciliation_receipt
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t21_receipt_scope_guard();

CREATE FUNCTION public.hidra_t21_correction_guard() RETURNS trigger
LANGUAGE plpgsql AS $$
DECLARE old_ticket public.hidra_custody_t21_ticket%ROWTYPE;
DECLARE next_ticket public.hidra_custody_t21_ticket%ROWTYPE;
BEGIN
 SELECT * INTO old_ticket FROM public.hidra_custody_t21_ticket WHERE id=NEW.original_ticket_id FOR UPDATE;
 SELECT * INTO next_ticket FROM public.hidra_custody_t21_ticket WHERE id=NEW.successor_ticket_id;
 IF NOT FOUND OR old_ticket.id IS NULL
    OR NOT EXISTS(SELECT 1 FROM public.hidra_custody_t21_finalization WHERE ticket_id=old_ticket.id)
    OR next_ticket.parent_ticket_id IS DISTINCT FROM old_ticket.id
    OR next_ticket.period_id<>old_ticket.period_id
    OR next_ticket.company_id<>old_ticket.company_id
    OR next_ticket.transfer_point_id<>old_ticket.transfer_point_id
    OR next_ticket.version<>old_ticket.version+1
    OR EXISTS(SELECT 1 FROM public.hidra_custody_t21_finalization WHERE ticket_id=next_ticket.id)
 THEN
    RAISE EXCEPTION 'T21 correction must extend one finalized same-owner head' USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END;
$$;
CREATE TRIGGER hidra_t21_correction_guard
 BEFORE INSERT ON public.hidra_custody_t21_correction
 FOR EACH ROW EXECUTE FUNCTION public.hidra_t21_correction_guard();

-- Frozen evidence and decisions. No updates, deletes or truncates by ordinary writers.
CREATE FUNCTION public.hidra_t21_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 RAISE EXCEPTION 'T21 reconciliation and decision history is append-only' USING ERRCODE='23514';
END;
$$;
DO $$
DECLARE relation text;
BEGIN
 FOREACH relation IN ARRAY ARRAY[
   'hidra_custody_t21_reconciliation','hidra_custody_t21_reconciliation_receipt',
   'hidra_custody_t21_ticket','hidra_custody_t21_calculation',
   'hidra_custody_t21_discrepancy','hidra_custody_t21_disposition',
   'hidra_custody_t21_finalization','hidra_custody_t21_correction',
   'hidra_custody_t21_command'
 ] LOOP
   EXECUTE format('CREATE TRIGGER %I BEFORE UPDATE OR DELETE ON public.%I FOR EACH ROW EXECUTE FUNCTION public.hidra_t21_immutable()',relation||'_immutable',relation);
   EXECUTE format('CREATE TRIGGER %I BEFORE TRUNCATE ON public.%I FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_t21_immutable()',relation||'_no_truncate',relation);
 END LOOP;
END;
$$;
