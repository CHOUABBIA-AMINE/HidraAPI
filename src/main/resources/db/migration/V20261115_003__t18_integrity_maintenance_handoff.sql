-- T18: governed Integrity finding approval -> Assets execution -> independent verification.
-- Software-only synthetic qualification; no physical completion or OT actuation is inferred.
-- Only Identity permission catalog entries are seeded. Effective grants remain owner-controlled.
INSERT INTO public.hidra_identity_permission
 (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
 ('t18-integrity-maintenance-approve','integrity:maintenance:approve','integrity','INTEGRITY_MAINTENANCE','approve',true,'ACTIVE',now(),now()),
 ('t18-integrity-maintenance-read','integrity:maintenance:read','integrity','INTEGRITY_MAINTENANCE','read',true,'ACTIVE',now(),now()),
 ('t18-assets-maintenance-handoff','assets:integrity-work-orders:handoff','assets','INTEGRITY_WORK_ORDER','handoff',true,'ACTIVE',now(),now()),
 ('t18-assets-maintenance-assign','assets:integrity-work-orders:assign','assets','INTEGRITY_WORK_ORDER','assign',true,'ACTIVE',now(),now()),
 ('t18-assets-maintenance-execute','assets:integrity-work-orders:execute','assets','INTEGRITY_WORK_ORDER','execute',true,'ACTIVE',now(),now()),
 ('t18-assets-maintenance-verify','assets:integrity-work-orders:verify','assets','INTEGRITY_WORK_ORDER','verify',true,'ACTIVE',now(),now()),
 ('t18-assets-maintenance-read','assets:integrity-work-orders:read','assets','INTEGRITY_WORK_ORDER','read',true,'ACTIVE',now(),now())
ON CONFLICT (code) DO NOTHING;

CREATE TABLE public.hidra_integrity_maintenance_approval (
 id varchar(80) PRIMARY KEY CHECK(NULLIF(btrim(id),'') IS NOT NULL),
 command_id varchar(80) NOT NULL UNIQUE CHECK(NULLIF(btrim(command_id),'') IS NOT NULL),
 command_sha256 char(64) NOT NULL CHECK(command_sha256 ~ '^[0-9a-f]{64}$'),
 recommendation_id varchar(80) NOT NULL UNIQUE
   REFERENCES public.hidra_integrity_recommendation(id) ON DELETE RESTRICT,
 finding_id varchar(80) NOT NULL REFERENCES public.hidra_integrity_inspection_finding(id) ON DELETE RESTRICT,
 case_id varchar(80) NOT NULL REFERENCES public.hidra_integrity_case(id) ON DELETE RESTRICT,
 defect_id varchar(80) NOT NULL REFERENCES public.hidra_integrity_pipeline_defect(id) ON DELETE RESTRICT,
 asset_type varchar(80) NOT NULL CHECK(asset_type='EQUIPMENT'),
 asset_id varchar(80) NOT NULL CHECK(NULLIF(btrim(asset_id),'') IS NOT NULL),
 company_id varchar(80) NOT NULL CHECK(NULLIF(btrim(company_id),'') IS NOT NULL),
 approver_id varchar(80) NOT NULL CHECK(NULLIF(btrim(approver_id),'') IS NOT NULL),
 approved_at timestamptz NOT NULL CHECK(isfinite(approved_at)),
 reason text NOT NULL CHECK(length(btrim(reason)) BETWEEN 1 AND 1000),
 source_version varchar(100) NOT NULL CHECK(NULLIF(btrim(source_version),'') IS NOT NULL)
);
CREATE INDEX ix_t18_integrity_approval_finding ON public.hidra_integrity_maintenance_approval(finding_id,approved_at,id);

CREATE TABLE public.hidra_integrity_maintenance_handoff (
 approval_id varchar(80) PRIMARY KEY REFERENCES public.hidra_integrity_maintenance_approval(id) ON DELETE RESTRICT,
 work_order_id varchar(80) NOT NULL UNIQUE CHECK(NULLIF(btrim(work_order_id),'') IS NOT NULL),
 linked_at timestamptz NOT NULL CHECK(isfinite(linked_at))
);

CREATE TABLE public.hidra_integrity_maintenance_verification (
 verification_id varchar(80) PRIMARY KEY CHECK(NULLIF(btrim(verification_id),'') IS NOT NULL),
 approval_id varchar(80) NOT NULL REFERENCES public.hidra_integrity_maintenance_handoff(approval_id) ON DELETE RESTRICT,
 work_order_id varchar(80) NOT NULL CHECK(NULLIF(btrim(work_order_id),'') IS NOT NULL),
 execution_id varchar(80) NOT NULL CHECK(NULLIF(btrim(execution_id),'') IS NOT NULL),
 verifier_id varchar(80) NOT NULL CHECK(NULLIF(btrim(verifier_id),'') IS NOT NULL),
 verdict varchar(10) NOT NULL CHECK(verdict IN ('ACCEPT','REJECT')),
 evidence text NOT NULL CHECK(length(btrim(evidence)) BETWEEN 1 AND 1000),
 verified_at timestamptz NOT NULL CHECK(isfinite(verified_at))
);
CREATE UNIQUE INDEX uq_t18_positive_verification
 ON public.hidra_integrity_maintenance_verification(approval_id) WHERE verdict='ACCEPT';
CREATE INDEX ix_t18_verification_history
 ON public.hidra_integrity_maintenance_verification(approval_id,verified_at,verification_id);

CREATE TABLE public.hidra_asset_integrity_work_order (
 work_order_id varchar(80) PRIMARY KEY
   REFERENCES public.hidra_asset_maintenance_work_order(id) ON DELETE RESTRICT,
 approval_id varchar(80) NOT NULL UNIQUE CHECK(NULLIF(btrim(approval_id),'') IS NOT NULL),
 finding_id varchar(80) NOT NULL CHECK(NULLIF(btrim(finding_id),'') IS NOT NULL),
 recommendation_id varchar(80) NOT NULL UNIQUE CHECK(NULLIF(btrim(recommendation_id),'') IS NOT NULL),
 company_id varchar(80) NOT NULL CHECK(NULLIF(btrim(company_id),'') IS NOT NULL),
 asset_type varchar(80) NOT NULL CHECK(asset_type='EQUIPMENT'),
 asset_id varchar(80) NOT NULL CHECK(NULLIF(btrim(asset_id),'') IS NOT NULL),
 maintainable_asset_id varchar(80) NOT NULL REFERENCES public.hidra_asset_maintainable_asset(id) ON DELETE RESTRICT,
 status varchar(30) NOT NULL CHECK(status IN ('DRAFT','SCHEDULED','IN_PROGRESS','COMPLETED','VERIFIED')),
 version bigint NOT NULL DEFAULT 0 CHECK(version>=0),
 assigned_actor_id varchar(80),
 completion_id varchar(80),
 created_at timestamptz NOT NULL DEFAULT now() CHECK(isfinite(created_at)),
 CHECK(status='DRAFT' OR NULLIF(btrim(assigned_actor_id),'') IS NOT NULL),
 CHECK(status NOT IN ('COMPLETED','VERIFIED') OR NULLIF(btrim(completion_id),'') IS NOT NULL)
);
CREATE INDEX ix_t18_work_order_asset ON public.hidra_asset_integrity_work_order(company_id,asset_id);

CREATE TABLE public.hidra_asset_integrity_work_order_event (
 id varchar(80) PRIMARY KEY CHECK(NULLIF(btrim(id),'') IS NOT NULL),
 work_order_id varchar(80) NOT NULL REFERENCES public.hidra_asset_integrity_work_order(work_order_id) ON DELETE RESTRICT,
 command_id varchar(80) NOT NULL UNIQUE CHECK(NULLIF(btrim(command_id),'') IS NOT NULL),
 command_sha256 char(64) NOT NULL CHECK(command_sha256 ~ '^[0-9a-f]{64}$'),
 operation varchar(16) NOT NULL CHECK(operation IN ('CREATE','ASSIGN','START','EXECUTE','VERIFY')),
 status varchar(30) NOT NULL CHECK(status IN ('DRAFT','SCHEDULED','IN_PROGRESS','COMPLETED','VERIFIED')),
 version bigint NOT NULL CHECK(version>=0),
 actor_id varchar(80) NOT NULL CHECK(NULLIF(btrim(actor_id),'') IS NOT NULL),
 execution_id varchar(80),
 verdict varchar(10) CHECK(verdict IS NULL OR verdict IN ('ACCEPT','REJECT')),
 summary text NOT NULL CHECK(length(btrim(summary)) BETWEEN 1 AND 1000),
 recorded_at timestamptz NOT NULL CHECK(isfinite(recorded_at)),
 UNIQUE(work_order_id,version)
);
CREATE INDEX ix_t18_work_order_event_history
 ON public.hidra_asset_integrity_work_order_event(work_order_id,version,id);

-- Do not let any historical decision, approval, source-link or event be rewritten.
CREATE FUNCTION public.t18_maintenance_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 RAISE EXCEPTION 'T18 approval/handoff/verification/event is append-only';
END $$;
CREATE TRIGGER trg_t18_approval_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_integrity_maintenance_approval FOR EACH ROW
 EXECUTE FUNCTION public.t18_maintenance_immutable();
CREATE TRIGGER trg_t18_handoff_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_integrity_maintenance_handoff FOR EACH ROW
 EXECUTE FUNCTION public.t18_maintenance_immutable();
CREATE TRIGGER trg_t18_verification_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_integrity_maintenance_verification FOR EACH ROW
 EXECUTE FUNCTION public.t18_maintenance_immutable();
CREATE TRIGGER trg_t18_assets_event_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_asset_integrity_work_order_event FOR EACH ROW
 EXECUTE FUNCTION public.t18_maintenance_immutable();
CREATE TRIGGER trg_t18_approval_no_truncate BEFORE TRUNCATE
 ON public.hidra_integrity_maintenance_approval FOR EACH STATEMENT
 EXECUTE FUNCTION public.t18_maintenance_immutable();
CREATE TRIGGER trg_t18_handoff_no_truncate BEFORE TRUNCATE
 ON public.hidra_integrity_maintenance_handoff FOR EACH STATEMENT
 EXECUTE FUNCTION public.t18_maintenance_immutable();
CREATE TRIGGER trg_t18_verification_no_truncate BEFORE TRUNCATE
 ON public.hidra_integrity_maintenance_verification FOR EACH STATEMENT
 EXECUTE FUNCTION public.t18_maintenance_immutable();
CREATE TRIGGER trg_t18_assets_event_no_truncate BEFORE TRUNCATE
 ON public.hidra_asset_integrity_work_order_event FOR EACH STATEMENT
 EXECUTE FUNCTION public.t18_maintenance_immutable();
