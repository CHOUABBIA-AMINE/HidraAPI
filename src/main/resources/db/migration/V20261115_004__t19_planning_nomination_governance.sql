-- T19 governs new nominations without changing historical nominations or plan revisions.
CREATE TABLE public.hidra_planning_nomination_governance (
    nomination_id varchar(80) PRIMARY KEY REFERENCES public.hidra_planning_nomination(id) ON DELETE RESTRICT,
    revision_id varchar(80) NOT NULL REFERENCES public.hidra_planning_plan_revision(id) ON DELETE RESTRICT,
    company_id varchar(80) NOT NULL CHECK (btrim(company_id) <> ''),
    source_facility_id varchar(80) NOT NULL CHECK (btrim(source_facility_id) <> ''),
    destination_facility_id varchar(80) NOT NULL CHECK (btrim(destination_facility_id) <> ''),
    normalized_code varchar(80) NOT NULL CHECK (normalized_code ~ '^[A-Z0-9][A-Z0-9._-]{0,79}$'),
    author_id varchar(80) NOT NULL CHECK (btrim(author_id) <> ''),
    CHECK (source_facility_id <> destination_facility_id),
    UNIQUE (revision_id, normalized_code),
    UNIQUE (nomination_id, revision_id)
);

CREATE TABLE public.hidra_planning_nomination_event (
    event_id uuid PRIMARY KEY,
    command_id uuid NOT NULL,
    nomination_id varchar(80) NOT NULL,
    revision_id varchar(80) NOT NULL,
    event_version bigint NOT NULL,
    actor_id varchar(80) NOT NULL CHECK (btrim(actor_id) <> ''),
    previous_status varchar(24),
    status varchar(24) NOT NULL,
    reason varchar(500),
    recorded_at timestamptz NOT NULL CHECK (isfinite(recorded_at)),
    payload_fingerprint varchar(64) NOT NULL CHECK (payload_fingerprint ~ '^[a-f0-9]{64}$'),
    FOREIGN KEY (nomination_id, revision_id) REFERENCES public.hidra_planning_nomination_governance
        (nomination_id, revision_id) ON DELETE RESTRICT,
    UNIQUE (revision_id, command_id),
    UNIQUE (nomination_id, event_version),
    CHECK (
        (event_version = 0 AND previous_status IS NULL AND status = 'DRAFT' AND reason IS NULL)
        OR (event_version = 1 AND previous_status IS NOT NULL AND previous_status = 'DRAFT'
            AND status IN ('CONFIRMED', 'REJECTED') AND reason IS NOT NULL AND btrim(reason) <> '')
    )
);

CREATE FUNCTION public.hidra_t19_nomination_event_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE
    scope_author varchar(80);
    created timestamptz;
BEGIN
    SELECT author_id INTO STRICT scope_author
    FROM public.hidra_planning_nomination_governance WHERE nomination_id = NEW.nomination_id;
    IF NEW.event_version = 0 THEN
        IF NEW.actor_id <> scope_author THEN
            RAISE EXCEPTION 'T19 creation requires the registered author' USING ERRCODE = '23514';
        END IF;
    ELSE
        SELECT recorded_at INTO created FROM public.hidra_planning_nomination_event
        WHERE nomination_id = NEW.nomination_id AND event_version = 0;
        IF created IS NULL OR NEW.recorded_at <= created OR NEW.actor_id = scope_author THEN
            RAISE EXCEPTION 'T19 disposition requires a later event and an independent reviewer'
                USING ERRCODE = '23514';
        END IF;
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER hidra_t19_nomination_event_insert_guard
    BEFORE INSERT ON public.hidra_planning_nomination_event
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t19_nomination_event_guard();

CREATE FUNCTION public.hidra_t19_nomination_append_only() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    RAISE EXCEPTION 'T19 nomination governance evidence is immutable' USING ERRCODE = '23514';
END;
$$;

CREATE TRIGGER hidra_t19_nomination_governance_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_planning_nomination_governance
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t19_nomination_append_only();
CREATE TRIGGER hidra_t19_nomination_governance_no_truncate
    BEFORE TRUNCATE ON public.hidra_planning_nomination_governance
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_t19_nomination_append_only();
CREATE TRIGGER hidra_t19_nomination_event_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_planning_nomination_event
    FOR EACH ROW EXECUTE FUNCTION public.hidra_t19_nomination_append_only();
CREATE TRIGGER hidra_t19_nomination_event_no_truncate
    BEFORE TRUNCATE ON public.hidra_planning_nomination_event
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_t19_nomination_append_only();
