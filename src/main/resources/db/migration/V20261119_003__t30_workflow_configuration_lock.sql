-- T30 fail repair: narrow command-time Workflow configuration shared-lock interface.
-- PostgreSQL FOR SHARE requires UPDATE-level row-lock privilege; ordinary app roles
-- intentionally retain SELECT-only access to configuration tables.
-- No application role/table grant, schema rewrite or legacy migration change is made.
CREATE FUNCTION public.hidra_workflow_t30_lock_current_configuration(
    p_definition_id text, p_version integer, p_module text,
    p_target_type_id text, p_purpose_id text
) RETURNS boolean
LANGUAGE plpgsql VOLATILE SECURITY DEFINER
SET search_path = pg_catalog, pg_temp
AS $$
DECLARE expected record;
BEGIN
    -- The caller must already be in its write transaction. The function never
    -- starts a transaction, modifies configuration or impersonates an actor.
    IF p_definition_id IS NULL OR btrim(p_definition_id) = ''
       OR p_version IS NULL OR p_version < 1
       OR p_module IS NULL OR btrim(p_module) = ''
       OR p_target_type_id IS NULL OR btrim(p_target_type_id) = ''
       OR p_purpose_id IS NULL OR btrim(p_purpose_id) = ''
       OR current_setting('transaction_read_only') <> 'off'
       OR current_setting('transaction_isolation') <> 'read committed'
       OR current_setting('session_replication_role') <> 'origin'
    THEN RETURN false;
    END IF;

    -- Only the definer acquires row locks. NOWAIT denies competing configuration
    -- mutations rather than waiting with a Workflow instance/task locked.
    PERFORM 1 FROM public.hidra_workflow_definition d
    WHERE d.id = p_definition_id AND d.version = p_version
      AND d.status = 'ACTIVE'
    FOR SHARE OF d NOWAIT;
    IF NOT FOUND THEN RETURN false; END IF;

    -- A stable catalog-id order covers both families without lock-order inversion.
    FOR expected IN
        SELECT candidate.id, candidate.family
        FROM (VALUES (p_target_type_id, 'WORKFLOW_TARGET_TYPE'),
                     (p_purpose_id, 'WORKFLOW_PURPOSE'))
             AS candidate(id, family)
        ORDER BY candidate.id, candidate.family
    LOOP
        PERFORM 1 FROM public.hidra_workflow_type_catalog c
        WHERE c.id = expected.id AND c.catalog_name = expected.family
          AND c.active = true
        FOR SHARE OF c NOWAIT;
        IF NOT FOUND THEN RETURN false; END IF;
    END LOOP;

    PERFORM 1 FROM public.hidra_workflow_definition_target_binding b
    WHERE b.definition_id = p_definition_id
      AND b.target_module = p_module
      AND b.target_type_id = p_target_type_id
      AND b.workflow_purpose_id = p_purpose_id
      AND b.active = true
    FOR SHARE OF b NOWAIT;
    RETURN FOUND;
EXCEPTION
    WHEN lock_not_available THEN RETURN false;
END;
$$;

-- PostgreSQL initially grants EXECUTE on functions to PUBLIC. This migration
-- revokes it in the same Flyway transaction. Only the trusted migration owner
-- retains implicit execution; approved runtime role grants are provisioned
-- explicitly, not guessed in migrations. Synthetic native-role fixtures grant
-- only this function to their isolated Workflow application role.
REVOKE ALL ON FUNCTION
    public.hidra_workflow_t30_lock_current_configuration(text,integer,text,text,text)
    FROM PUBLIC;
