-- HAPI-599 Package B: fixed lock-only capability, not manufacturer/operator authorization.
-- Flyway executes this migration transactionally: PUBLIC never observes an executable helper.
-- No production principal, role membership or application DML/EXECUTE grant is invented here.
-- Before enabling a reader, a schema administrator must provision a dedicated NOLOGIN,
-- NOINHERIT, non-superuser helper owner with UPDATE (without grant option) on the four
-- relations below, transfer function ownership, revoke schema CREATE, and explicitly
-- grant EXECUTE only to the reviewed non-owner evidence reader. The adapter rejects
-- an unprovisioned/superuser owner and any PUBLIC/unreviewed EXECUTE grant.
CREATE FUNCTION public.hidra_party_lock_installed_unit_manufacturer_evidence()
RETURNS void
LANGUAGE plpgsql
VOLATILE
PARALLEL UNSAFE
SECURITY DEFINER
SET search_path = pg_catalog, pg_temp
AS $hidra_lock$
BEGIN
    LOCK TABLE public.hidra_party_party, public.hidra_party_role,
        public.hidra_party_role_assignment, public.hidra_party_manufacturer_profile
        IN SHARE ROW EXCLUSIVE MODE NOWAIT;
END;
$hidra_lock$;
REVOKE ALL ON FUNCTION public.hidra_party_lock_installed_unit_manufacturer_evidence() FROM PUBLIC;
COMMENT ON FUNCTION public.hidra_party_lock_installed_unit_manufacturer_evidence() IS
    'Fixed four-relation NOWAIT owner lock only; explicit non-superuser owner and restricted reader provisioning required; no authorization or DML capability returned';
