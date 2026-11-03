-- HAPI-923. Forward-only append evidence and explicit revocation reasons; existing rows preserved.
ALTER TABLE public.hidra_identity_user_permission_grant
    ADD COLUMN revoked_reason text;
ALTER TABLE public.hidra_identity_role_permission_grant
    ADD COLUMN revoked_at timestamptz,
    ADD COLUMN revoked_reason text;

CREATE TABLE public.hidra_identity_lifecycle_event (
    id varchar(80) PRIMARY KEY,
    actor_id varchar(80) NOT NULL REFERENCES public.hidra_identity_user(id),
    target_type varchar(40) NOT NULL,
    target_id varchar(80) NOT NULL,
    action varchar(40) NOT NULL,
    reason text NOT NULL,
    occurred_at timestamptz NOT NULL,
    CONSTRAINT ck_hidra_identity_lifecycle_reason CHECK (length(btrim(reason)) BETWEEN 1 AND 2000),
    CONSTRAINT ck_hidra_identity_lifecycle_action CHECK (action IN ('ACTIVATE','DEACTIVATE','CREDENTIAL_RESET','REVOKE'))
);
CREATE INDEX ix_hidra_identity_lifecycle_event_actor_at
    ON public.hidra_identity_lifecycle_event(actor_id,occurred_at DESC);
CREATE INDEX ix_hidra_identity_lifecycle_event_target_at
    ON public.hidra_identity_lifecycle_event(target_type,target_id,occurred_at DESC);

CREATE FUNCTION public.hidra_identity_lifecycle_event_immutable()
RETURNS trigger LANGUAGE plpgsql SECURITY INVOKER SET search_path=pg_catalog,public AS $$
BEGIN
    RAISE EXCEPTION 'Identity lifecycle events are immutable' USING ERRCODE='23514';
END;
$$;
CREATE TRIGGER trg_hidra_identity_lifecycle_event_no_update
    BEFORE UPDATE OR DELETE ON public.hidra_identity_lifecycle_event
    FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_lifecycle_event_immutable();
CREATE TRIGGER trg_hidra_identity_lifecycle_event_no_truncate
    BEFORE TRUNCATE ON public.hidra_identity_lifecycle_event
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_lifecycle_event_immutable();
