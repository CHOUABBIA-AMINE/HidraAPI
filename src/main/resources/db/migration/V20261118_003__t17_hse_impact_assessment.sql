-- Scoped HSE impact evidence. Existing legacy assessments and closures retain their history.
INSERT INTO public.hidra_identity_permission
    (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES ('t17-hse-capa-assess','hse:capa:assess','hse','HSE_CAPA','assess',true,'ACTIVE',now(),now());

-- Replace only the two original event checks governing operation and case-vs-CAPA shape.
DO $$
DECLARE original record; replaced integer := 0;
BEGIN
    FOR original IN
        SELECT conname FROM pg_constraint
        WHERE conrelid = 'public.hidra_hse_t17_capa_event'::regclass AND contype = 'c'
          AND (pg_get_constraintdef(oid) LIKE '%PROPOSE%'
               OR (pg_get_constraintdef(oid) LIKE '%capa_id IS NULL%'
                   AND pg_get_constraintdef(oid) LIKE '%CLOSE%'))
    LOOP
        EXECUTE format('ALTER TABLE public.hidra_hse_t17_capa_event DROP CONSTRAINT %I',
                       original.conname);
        replaced := replaced + 1;
    END LOOP;
    IF replaced <> 2 THEN
        RAISE EXCEPTION 'Unexpected T17 event constraint baseline: %', replaced;
    END IF;
END $$;

ALTER TABLE public.hidra_hse_t17_capa_event
    ADD CONSTRAINT ck_t17_assessed_event_shape CHECK (
        (operation = 'CLOSE' AND capa_id IS NULL AND after_status = 'CLOSED')
        OR (operation = 'ASSESS_IMPACT' AND capa_id IS NULL AND reference_id IS NOT NULL
            AND before_status IS NOT NULL AND before_status IN ('OPEN', 'IN_PROGRESS')
            AND after_status = before_status AND revision > 0)
        OR (operation IN ('PROPOSE','APPROVE','ASSIGN','EVIDENCE','COMPLETE','VERIFY','FINALIZE')
            AND capa_id IS NOT NULL)
    );
CREATE UNIQUE INDEX uq_t17_case_impact_event
    ON public.hidra_hse_t17_capa_event(case_id) WHERE operation = 'ASSESS_IMPACT';
ALTER TABLE public.hidra_hse_impact_assessment
    ADD CONSTRAINT uq_t17_impact_case_identity UNIQUE (hse_case_id, id);
ALTER TABLE public.hidra_hse_t17_case_close_receipt ADD COLUMN assessment_id varchar(80);
ALTER TABLE public.hidra_hse_t17_case_close_receipt
    ADD CONSTRAINT fk_t17_close_scoped_assessment FOREIGN KEY (case_id, assessment_id)
    REFERENCES public.hidra_hse_impact_assessment(hse_case_id, id) ON DELETE RESTRICT;

CREATE FUNCTION public.t17_validate_impact_event() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NEW.operation <> 'ASSESS_IMPACT' THEN RETURN NEW; END IF;
    PERFORM 1 FROM public.hidra_hse_case WHERE id = NEW.case_id FOR UPDATE;
    IF NEW.revision <> 1 + (SELECT count(*) FROM public.hidra_hse_t17_capa_event
                            WHERE case_id = NEW.case_id)
       OR NOT EXISTS (
        SELECT 1 FROM public.hidra_hse_impact_assessment a
        JOIN public.hidra_hse_catalog_entry t ON t.id = a.impact_type_id
        JOIN public.hidra_hse_catalog_field_policy p ON p.catalog_name = t.catalog_name
        JOIN public.hidra_hse_case c ON c.id = a.hse_case_id
        WHERE a.id = NEW.reference_id AND a.hse_case_id = NEW.case_id
          AND a.assessed_by_actor_id = NEW.actor_id AND a.assessed_at = NEW.occurred_at
          AND a.description = NEW.reason_text
          AND a.impact_domain IN ('HEALTH','SAFETY','ENVIRONMENT','COMPLIANCE','COMMUNITY','OPERATIONS')
          AND a.severity IN ('LOW','MEDIUM','HIGH','CRITICAL','UNKNOWN')
          AND c.status = NEW.before_status
          AND t.active AND p.active AND p.field_role = 'CASE_IMPACT_TYPE'
        FOR SHARE OF a, t, p, c
    ) THEN
        RAISE EXCEPTION 'Scoped impact event requires the exact eligible same-case assessment'
            USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER t17_impact_event_guard BEFORE INSERT ON public.hidra_hse_t17_capa_event
    FOR EACH ROW EXECUTE FUNCTION public.t17_validate_impact_event();

CREATE FUNCTION public.t17_guard_retained_impact() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP = 'TRUNCATE' THEN
        IF EXISTS (SELECT 1 FROM public.hidra_hse_t17_capa_event WHERE operation = 'ASSESS_IMPACT') THEN
            RAISE EXCEPTION 'Scoped HSE impact evidence is immutable' USING ERRCODE = '23514';
        END IF;
        RETURN NULL;
    END IF;
    IF EXISTS (SELECT 1 FROM public.hidra_hse_t17_capa_event
        WHERE case_id = OLD.hse_case_id AND operation = 'ASSESS_IMPACT' AND reference_id = OLD.id) THEN
        RAISE EXCEPTION 'Scoped HSE impact evidence is immutable' USING ERRCODE = '23514';
    END IF;
    IF TG_OP = 'DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER t17_impact_immutable BEFORE UPDATE OR DELETE ON public.hidra_hse_impact_assessment
    FOR EACH ROW EXECUTE FUNCTION public.t17_guard_retained_impact();
CREATE TRIGGER t17_impact_no_truncate BEFORE TRUNCATE ON public.hidra_hse_impact_assessment
    FOR EACH STATEMENT EXECUTE FUNCTION public.t17_guard_retained_impact();

CREATE FUNCTION public.t17_require_closure_impact() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NEW.assessment_id IS NULL OR NOT EXISTS (
        SELECT 1 FROM public.hidra_hse_t17_capa_event
        WHERE case_id = NEW.case_id AND operation = 'ASSESS_IMPACT'
          AND reference_id = NEW.assessment_id
    ) THEN
        RAISE EXCEPTION 'Scoped HSE closure requires admitted impact evidence'
            USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER t17_close_impact_guard BEFORE INSERT ON public.hidra_hse_t17_case_close_receipt
    FOR EACH ROW EXECUTE FUNCTION public.t17_require_closure_impact();
