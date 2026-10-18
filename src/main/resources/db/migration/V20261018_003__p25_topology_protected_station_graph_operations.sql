-- HAPI-584: fresh-schema, Topology-owned station publication state. No data backfill.
-- Existing immutable V1 revision declarations and K owner generations remain untouched.
CREATE TABLE public.hidra_topology_station_graph_operation_event (
    event_id uuid PRIMARY KEY,
    facility_id varchar(80) NOT NULL,
    event_kind varchar(16) NOT NULL,
    resulting_epoch bigint,
    prior_revision_id text,
    resulting_revision_id text,
    actor_binding_id varchar(80) NOT NULL,
    actor_session_id varchar(80) NOT NULL,
    actor_user_id varchar(80) NOT NULL,
    company_party_id varchar(80) NOT NULL,
    party_generation bigint NOT NULL,
    facility_generation bigint NOT NULL,
    sampled_at timestamptz NOT NULL,
    valid_until timestamptz NOT NULL,
    backend_pid integer NOT NULL,
    transaction_id bigint NOT NULL,
    CONSTRAINT ck_p25_graph_event_kind CHECK (event_kind IN ('AUTHOR','PUBLISH','WITHDRAW','REACTIVATE')),
    CONSTRAINT ck_p25_graph_event_epoch CHECK (
       (event_kind='AUTHOR' AND resulting_epoch IS NULL) OR
       (event_kind<>'AUTHOR' AND resulting_epoch>0)),
    CONSTRAINT ck_p25_graph_event_positive CHECK (party_generation>0 AND facility_generation>0 AND backend_pid>0 AND transaction_id>0),
    CONSTRAINT ck_p25_graph_event_time CHECK (valid_until>sampled_at),
    CONSTRAINT ck_p25_graph_event_id CHECK (length(btrim(facility_id))>0 AND facility_id=btrim(facility_id)
         AND length(btrim(company_party_id))>0 AND company_party_id=btrim(company_party_id)
         AND length(btrim(actor_binding_id))>0 AND length(btrim(actor_session_id))>0 AND length(btrim(actor_user_id))>0),
    CONSTRAINT ck_p25_graph_event_revision CHECK (resulting_revision_id IS NOT NULL),
    CONSTRAINT fk_p25_graph_event_revision FOREIGN KEY (facility_id,resulting_revision_id)
        REFERENCES public.hidra_topology_station_graph_revision(facility_id,revision_id),
    CONSTRAINT uq_p25_graph_event_head_witness UNIQUE(facility_id,event_id,resulting_epoch,resulting_revision_id),
    CONSTRAINT uq_p25_graph_event_epoch UNIQUE(facility_id,resulting_epoch)
);
CREATE INDEX ix_p25_graph_event_history ON public.hidra_topology_station_graph_operation_event
    (facility_id,resulting_epoch DESC,event_id);

CREATE TABLE public.hidra_topology_station_graph_head (
    facility_id varchar(80) PRIMARY KEY,
    revision_id text NOT NULL,
    head_epoch bigint NOT NULL CHECK (head_epoch>0),
    state varchar(16) NOT NULL CHECK (state IN ('ACTIVE','WITHDRAWN')),
    last_event_id uuid NOT NULL,
    CONSTRAINT fk_p25_graph_head_revision FOREIGN KEY (facility_id,revision_id)
        REFERENCES public.hidra_topology_station_graph_revision(facility_id,revision_id),
    CONSTRAINT fk_p25_graph_head_event FOREIGN KEY (facility_id,last_event_id,head_epoch,revision_id)
        REFERENCES public.hidra_topology_station_graph_operation_event(facility_id,event_id,resulting_epoch,resulting_revision_id)
);
CREATE FUNCTION public.hidra_p25_graph_immutable_event() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
BEGIN
    RAISE EXCEPTION 'Immutable station publication event' USING ERRCODE='23514';
END;
$guard$;
CREATE TRIGGER trg_p25_graph_event_immutable BEFORE UPDATE OR DELETE
ON public.hidra_topology_station_graph_operation_event
FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_graph_immutable_event();
CREATE TRIGGER trg_p25_graph_event_no_truncate BEFORE TRUNCATE
ON public.hidra_topology_station_graph_operation_event
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_graph_immutable_event();

CREATE FUNCTION public.hidra_p25_graph_head_transition() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
DECLARE action_name varchar(16);
BEGIN
    IF TG_OP='TRUNCATE' OR TG_OP='DELETE' THEN
        RAISE EXCEPTION 'Station publication head must retain tombstone' USING ERRCODE='23514';
    END IF;
    SELECT e.event_kind INTO action_name FROM public.hidra_topology_station_graph_operation_event e
    WHERE e.event_id=NEW.last_event_id AND e.facility_id=NEW.facility_id
      AND e.resulting_epoch=NEW.head_epoch AND e.resulting_revision_id=NEW.revision_id;
    IF action_name IS NULL THEN
        RAISE EXCEPTION 'Missing publication event witness' USING ERRCODE='23514';
    END IF;
    IF TG_OP='INSERT' THEN
        IF NEW.head_epoch<>1 OR NEW.state<>'ACTIVE' OR action_name<>'PUBLISH' THEN
            RAISE EXCEPTION 'Initial station head must publish epoch one' USING ERRCODE='23514';
        END IF;
    ELSIF TG_OP='UPDATE' THEN
        IF NEW.facility_id<>OLD.facility_id OR OLD.head_epoch=9223372036854775807::bigint
           OR NEW.head_epoch<>OLD.head_epoch+1 THEN
            RAISE EXCEPTION 'Station head must advance exactly one epoch' USING ERRCODE='23514';
        END IF;
        IF (action_name='PUBLISH' AND (OLD.state<>'ACTIVE' OR NEW.state<>'ACTIVE'
             OR NEW.revision_id=OLD.revision_id))
           OR (action_name='WITHDRAW' AND (OLD.state<>'ACTIVE' OR NEW.state<>'WITHDRAWN'
             OR NEW.revision_id<>OLD.revision_id))
           OR (action_name='REACTIVATE' AND (OLD.state<>'WITHDRAWN' OR NEW.state<>'ACTIVE'
             OR NEW.revision_id<>OLD.revision_id))
           OR action_name NOT IN ('PUBLISH','WITHDRAW','REACTIVATE') THEN
            RAISE EXCEPTION 'Invalid publication head transition' USING ERRCODE='23514';
        END IF;
    END IF;
    RETURN NEW;
END;
$guard$;
CREATE TRIGGER trg_p25_graph_head_transition BEFORE INSERT OR UPDATE
ON public.hidra_topology_station_graph_head
FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_graph_head_transition();
CREATE TRIGGER trg_p25_graph_head_no_delete BEFORE DELETE
ON public.hidra_topology_station_graph_head
FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_graph_immutable_event();
CREATE TRIGGER trg_p25_graph_head_no_truncate BEFORE TRUNCATE
ON public.hidra_topology_station_graph_head
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_graph_immutable_event();
REVOKE ALL ON public.hidra_topology_station_graph_operation_event,public.hidra_topology_station_graph_head FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_p25_graph_head_transition(),public.hidra_p25_graph_immutable_event() FROM PUBLIC;
-- Nonowner grants must be verified with the actual application-role policy; do not grant DML to PUBLIC.
