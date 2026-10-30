-- HAPI-600 B3-S2: one protected station-configuration lifecycle; no production principal is invented.
-- Only the configured, separately provisioned restricted publication principal may receive
-- SELECT/INSERT/UPDATE grants on the four owned relations and EXECUTE on the locking function.
-- No grants to PUBLIC, no login roles and no existing migrated data are rewritten.
--
-- A fixed SECURITY DEFINER function is needed because a least-privilege publication user
-- must NOT receive UPDATE/DELETE rights over Identity, graph, equipment or installed-unit sources.
-- It locks a fixed 12-table set, not caller-supplied SQL or table names, in its ORIGINAL transaction.
CREATE FUNCTION public.hidra_station_configuration_lock_sources()
RETURNS void LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    IF pg_catalog.current_setting('transaction_isolation') <> 'read committed'
       OR pg_catalog.current_setting('transaction_read_only') <> 'off'
       OR pg_catalog.current_setting('session_replication_role') <> 'origin'
       OR pg_catalog.pg_backend_pid() < 1
       OR pg_catalog.txid_current() < 1 THEN
        RAISE EXCEPTION 'Protected station configuration lock unavailable' USING ERRCODE='42501';
    END IF;
    LOCK TABLE
        public.hidra_topology_equipment,
        public.hidra_topology_facility,
        public.hidra_topology_facility_owner_generation,
        public.hidra_topology_installed_unit,
        public.hidra_topology_installed_unit_installation,
        public.hidra_topology_installed_unit_installation_event,
        public.hidra_topology_station_graph_head,
        public.hidra_topology_station_graph_revision,
        public.hidra_topology_station_configuration_revision,
        public.hidra_topology_station_configuration_member,
        public.hidra_topology_station_configuration_head,
        public.hidra_topology_station_configuration_request
    IN SHARE ROW EXCLUSIVE MODE NOWAIT;
END;
$body$;
REVOKE ALL ON FUNCTION public.hidra_station_configuration_lock_sources() FROM PUBLIC;

-- Extend the existing request receipt, retaining its immutable append-only semantics.
ALTER TABLE public.hidra_topology_station_configuration_request
  ADD COLUMN actor_session_id varchar(80) NOT NULL,
  ADD COLUMN actor_user_id varchar(80) NOT NULL,
  ADD COLUMN sampled_at timestamptz NOT NULL,
  ADD COLUMN valid_until timestamptz NOT NULL,
  ADD COLUMN backend_pid integer NOT NULL,
  ADD COLUMN transaction_id bigint NOT NULL,
  ADD COLUMN database_role name NOT NULL,
  ADD CONSTRAINT ck_station_configuration_request_physical_origin CHECK (
    backend_pid>0 AND transaction_id>0 AND valid_until>sampled_at AND
    actor_session_id=btrim(actor_session_id) AND length(actor_session_id)>0 AND
    actor_user_id=btrim(actor_user_id) AND length(actor_user_id)>0
  );

-- The owner-side insert witness has actual backend/clock/role provenance. This does
-- not replace or manufacture native Identity authorization: the application must
-- contribute a valid original native operation admission before any owner DML.
CREATE FUNCTION public.hidra_station_configuration_request_origin()
RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    IF TG_OP <> 'INSERT' OR
       NEW.backend_pid IS DISTINCT FROM pg_catalog.pg_backend_pid() OR
       NEW.transaction_id IS DISTINCT FROM pg_catalog.txid_current() OR
       NEW.database_role IS DISTINCT FROM session_user OR
       session_user IS DISTINCT FROM current_setting('role',true)::name AND
           current_setting('role',true) <> 'none' OR
       pg_catalog.clock_timestamp() < NEW.sampled_at OR
       pg_catalog.clock_timestamp() >= NEW.valid_until OR
       NEW.recorded_at < NEW.sampled_at OR
       NEW.recorded_at >= NEW.valid_until THEN
        RAISE EXCEPTION 'Station configuration receipt origin unavailable' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END;
$body$;
CREATE TRIGGER trg_station_configuration_request_origin
BEFORE INSERT ON public.hidra_topology_station_configuration_request
FOR EACH ROW EXECUTE FUNCTION public.hidra_station_configuration_request_origin();
REVOKE ALL ON FUNCTION public.hidra_station_configuration_request_origin() FROM PUBLIC;

-- Existing immutable table/trigger history is preserved. The restricted publisher
-- is provisioned separately with table SELECT, revision/member/request INSERT and
-- head INSERT/UPDATE only; ordinary principals never inherit these rights. Absence
-- of configured role grants intentionally fails the protected workflow closed.
