-- T01: company ownership for new periods. Existing unproven owners remain NULL.
ALTER TABLE public.hidra_planning_period
    ADD COLUMN company_party_id varchar(80),
    ADD CONSTRAINT fk_t01_planning_period_company FOREIGN KEY (company_party_id)
        REFERENCES public.hidra_party_party(id) ON DELETE RESTRICT;

DROP INDEX public.uk_hmr006_planning_period_code;
CREATE UNIQUE INDEX uk_t01_planning_period_company_code
    ON public.hidra_planning_period (company_party_id, code);

CREATE FUNCTION public.hidra_t01_planning_period_company_guard()
RETURNS trigger LANGUAGE plpgsql SECURITY INVOKER SET search_path=pg_catalog,public AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        IF NEW.company_party_id IS NULL OR NEW.company_party_id = ''
                OR NEW.company_party_id <> btrim(NEW.company_party_id) THEN
            RAISE EXCEPTION 'Planning period company is required' USING ERRCODE = '23514';
        END IF;
    ELSIF NEW.company_party_id IS DISTINCT FROM OLD.company_party_id THEN
        RAISE EXCEPTION 'Planning period company is immutable' USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END
$$;

CREATE TRIGGER trg_t01_planning_period_company
    BEFORE INSERT OR UPDATE OF company_party_id ON public.hidra_planning_period
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t01_planning_period_company_guard();

INSERT INTO public.hidra_identity_permission
    (id, code, permission_domain, resource_type, action, sensitive, status, created_at, updated_at)
VALUES
    ('t01-period-read', 'planning:scoped-period:read', 'planning', 'PLANNING_PERIOD', 'read', false, 'ACTIVE', now(), now()),
    ('t01-period-create', 'planning:scoped-period:create', 'planning', 'PLANNING_PERIOD', 'create', true, 'ACTIVE', now(), now()),
    ('t01-plan-read', 'planning:scoped-plan:read', 'planning', 'OPERATIONAL_PLAN', 'read', false, 'ACTIVE', now(), now()),
    ('t01-plan-create', 'planning:scoped-plan:create', 'planning', 'OPERATIONAL_PLAN', 'create', true, 'ACTIVE', now(), now());
