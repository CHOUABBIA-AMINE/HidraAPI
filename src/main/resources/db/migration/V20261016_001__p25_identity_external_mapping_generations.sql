-- HPR-P25-051H-B2: Identity-owned mapping write epoch; fresh-schema synthetic acceptance.
-- Migration owner owns these trusted trigger functions. Ordinary application roles must not.
CREATE TABLE public.hidra_identity_external_mapping_generation (
    provider_id varchar(80) PRIMARY KEY REFERENCES public.hidra_identity_provider(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    generation bigint NOT NULL DEFAULT 1,
    CONSTRAINT ck_p25_external_mapping_generation_positive CHECK (generation > 0)
);
COMMENT ON TABLE public.hidra_identity_external_mapping_generation IS
    'Identity-owned committed mapping revision; neither source claims nor authorization.';
REVOKE ALL ON TABLE public.hidra_identity_external_mapping_generation FROM PUBLIC;

CREATE FUNCTION public.hidra_identity_external_mapping_provider_initialize()
RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER
SET search_path = pg_catalog, pg_temp
AS $$
BEGIN
    INSERT INTO public.hidra_identity_external_mapping_generation(provider_id,generation)
    VALUES (NEW.id,1);
    RETURN NULL;
END;
$$;
REVOKE ALL ON FUNCTION public.hidra_identity_external_mapping_provider_initialize() FROM PUBLIC;
CREATE TRIGGER trg_p25_external_mapping_provider_initialize
AFTER INSERT ON public.hidra_identity_provider FOR EACH ROW
EXECUTE FUNCTION public.hidra_identity_external_mapping_provider_initialize();

-- Existing development seed providers get one durable initial revision. No production backfill.
INSERT INTO public.hidra_identity_external_mapping_generation(provider_id,generation)
SELECT id,1 FROM public.hidra_identity_provider ORDER BY id;

CREATE FUNCTION public.hidra_identity_external_mapping_increment(changed_provider_id varchar)
RETURNS void LANGUAGE plpgsql SECURITY DEFINER
SET search_path = pg_catalog, pg_temp
AS $$
BEGIN
    UPDATE public.hidra_identity_external_mapping_generation
       SET generation = generation + 1
     WHERE provider_id = changed_provider_id
       AND generation < 9223372036854775807::bigint;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Mapping generation unavailable or exhausted' USING ERRCODE='23514';
    END IF;
END;
$$;
REVOKE ALL ON FUNCTION public.hidra_identity_external_mapping_increment(varchar) FROM PUBLIC;

CREATE FUNCTION public.hidra_identity_external_mapping_write_guard()
RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER
SET search_path = pg_catalog, pg_temp
AS $$
DECLARE old_provider varchar(80); new_provider varchar(80);
BEGIN
    IF TG_OP='TRUNCATE' THEN
        RAISE EXCEPTION 'Mapping TRUNCATE forbidden' USING ERRCODE='23514';
    END IF;
    IF TG_OP IN ('UPDATE','DELETE') THEN old_provider := OLD.identity_provider_id; END IF;
    IF TG_OP IN ('UPDATE','INSERT') THEN new_provider := NEW.identity_provider_id; END IF;
    IF old_provider IS NOT NULL AND new_provider IS NOT NULL
       AND old_provider <> new_provider THEN
        IF old_provider < new_provider THEN
            PERFORM public.hidra_identity_external_mapping_increment(old_provider);
            PERFORM public.hidra_identity_external_mapping_increment(new_provider);
        ELSE
            PERFORM public.hidra_identity_external_mapping_increment(new_provider);
            PERFORM public.hidra_identity_external_mapping_increment(old_provider);
        END IF;
    ELSE
        PERFORM public.hidra_identity_external_mapping_increment(
            COALESCE(old_provider,new_provider));
    END IF;
    RETURN NULL;
END;
$$;
REVOKE ALL ON FUNCTION public.hidra_identity_external_mapping_write_guard() FROM PUBLIC;

-- Fixed table allowlist; no caller input, no search-path dependent relation lookup.
DO $$
DECLARE mapped_table text; prefix text;
BEGIN
    FOREACH mapped_table IN ARRAY ARRAY[
        'hidra_identity_external_group_mapping',
        'hidra_identity_external_role_mapping',
        'hidra_identity_external_permission_mapping'
    ] LOOP
        prefix := replace(mapped_table,'hidra_identity_external_','');
        EXECUTE format('CREATE TRIGGER %I AFTER INSERT OR UPDATE OR DELETE ON public.%I '
            || 'FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_external_mapping_write_guard()',
            'trg_p25_'||prefix||'_generation',mapped_table);
        EXECUTE format('CREATE TRIGGER %I BEFORE TRUNCATE ON public.%I '
            || 'FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_external_mapping_write_guard()',
            'trg_p25_'||prefix||'_truncate',mapped_table);
    END LOOP;
END;
$$;

CREATE FUNCTION public.hidra_identity_external_mapping_generation_deny_truncate()
RETURNS trigger LANGUAGE plpgsql SECURITY INVOKER
SET search_path = pg_catalog, pg_temp
AS $$
BEGIN
    RAISE EXCEPTION 'Mapping generation TRUNCATE forbidden' USING ERRCODE='23514';
END;
$$;
REVOKE ALL ON FUNCTION public.hidra_identity_external_mapping_generation_deny_truncate()
FROM PUBLIC;
CREATE TRIGGER trg_p25_external_mapping_generation_truncate
BEFORE TRUNCATE ON public.hidra_identity_external_mapping_generation
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_external_mapping_generation_deny_truncate();
