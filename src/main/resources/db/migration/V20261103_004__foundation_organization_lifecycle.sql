-- HAPI-925: synthetic/development employee activation evidence, no employer or appointment policy.
CREATE TABLE public.hidra_org_employee_lifecycle_event (
    id varchar(80) PRIMARY KEY,
    employee_id varchar(80) NOT NULL REFERENCES public.hidra_org_employee(id),
    actor_id varchar(120) NOT NULL CHECK (length(btrim(actor_id)) > 0),
    reason varchar(2000) NOT NULL CHECK (length(btrim(reason)) > 0),
    previous_updated_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    occurred_at timestamptz NOT NULL,
    CHECK (updated_at > previous_updated_at)
);
CREATE INDEX ix_org_employee_lifecycle_history ON public.hidra_org_employee_lifecycle_event(employee_id,occurred_at,id);
CREATE FUNCTION public.hidra_org_employee_lifecycle_immutable() RETURNS trigger
LANGUAGE plpgsql SET search_path = pg_catalog, pg_temp AS $$
BEGIN
    RAISE EXCEPTION 'Employee lifecycle evidence is immutable' USING ERRCODE='55000';
END;
$$;
CREATE TRIGGER trg_org_employee_lifecycle_immutable
BEFORE UPDATE OR DELETE OR TRUNCATE ON public.hidra_org_employee_lifecycle_event
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_org_employee_lifecycle_immutable();
REVOKE UPDATE,DELETE,TRUNCATE ON public.hidra_org_employee_lifecycle_event FROM PUBLIC;
