-- HAPI-599 Package B: a fixed lock capability, never a data/permission API.
-- No deployment role or grant is provisioned automatically. Runtime use fails closed
-- until a dedicated NOLOGIN NOINHERIT nonowner is provisioned with non-grantable
-- UPDATE on the two fixed tables, no administrative flags/memberships/schema CREATE,
-- and the approved SELECT-only reader receives explicit non-grantable EXECUTE.
CREATE FUNCTION public.hidra_topology_lock_installed_unit_position_ownership()
RETURNS void
LANGUAGE plpgsql
VOLATILE
PARALLEL UNSAFE
SECURITY DEFINER
SET search_path=pg_catalog,pg_temp
AS $position_owner$
BEGIN
    LOCK TABLE public.hidra_topology_equipment, public.hidra_topology_facility
        IN SHARE ROW EXCLUSIVE MODE NOWAIT;
END;
$position_owner$;
REVOKE ALL ON FUNCTION public.hidra_topology_lock_installed_unit_position_ownership() FROM PUBLIC;
COMMENT ON FUNCTION public.hidra_topology_lock_installed_unit_position_ownership() IS
    'Fixed NOWAIT equipment/facility locks in the caller transaction; explicit restricted owner and reader provisioning required.';
