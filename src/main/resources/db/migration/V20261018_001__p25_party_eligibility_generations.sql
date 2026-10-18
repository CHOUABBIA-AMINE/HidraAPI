-- HAPI-582: fresh-development owner witness; no production backfill or epoch adoption.
-- Retain tombstones permanently. Migration owner is distinct from ordinary runtime roles.
LOCK TABLE public.hidra_party_party IN ACCESS EXCLUSIVE MODE;
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM public.hidra_party_party) THEN
        RAISE EXCEPTION 'Owner witness initialization requires empty development master' USING ERRCODE='23514';
    END IF;
END; $$;

CREATE TABLE public.hidra_party_eligibility_generation (
    party_id varchar(80) PRIMARY KEY,
    generation bigint NOT NULL,
    present boolean NOT NULL,
    CONSTRAINT ck_p25_party_eligibility_positive CHECK (generation>0)
);
COMMENT ON TABLE public.hidra_party_eligibility_generation IS 'Owner-issued live/tombstone generation, not publication epoch or permission';
REVOKE ALL ON TABLE public.hidra_party_eligibility_generation FROM PUBLIC;

CREATE FUNCTION public.hidra_p25_party_eligibility_write() RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp AS $owner$
DECLARE old_id varchar(80); new_id varchar(80); item record;
BEGIN
    IF TG_RELID <> 'public.hidra_party_party'::regclass OR TG_NAME <> 'trg_p25_party_eligibility_write'
       OR TG_WHEN <> 'AFTER' OR TG_LEVEL <> 'ROW' OR TG_NARGS <> 0
       OR TG_OP NOT IN ('INSERT','UPDATE','DELETE') THEN
        RAISE EXCEPTION 'Invalid owner generation invocation' USING ERRCODE='23514';
    END IF;
    IF TG_OP IN ('UPDATE','DELETE') THEN old_id := OLD.id; END IF;
    IF TG_OP IN ('UPDATE','INSERT') THEN new_id := NEW.id; END IF;
    IF old_id IS NOT NULL AND old_id = new_id THEN
        UPDATE public.hidra_party_eligibility_generation SET generation=generation+1
        WHERE party_id=old_id AND present AND generation<9223372036854775807::bigint;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Owner generation unavailable or exhausted' USING ERRCODE='23514';
        END IF;
    ELSE
        FOR item IN SELECT v.id,v.live FROM (VALUES(old_id,false),(new_id,true)) AS v(id,live)
                    WHERE v.id IS NOT NULL ORDER BY v.id COLLATE "C" LOOP
            IF item.live THEN
                INSERT INTO public.hidra_party_eligibility_generation AS g (party_id,generation,present) VALUES(item.id,1,true)
                ON CONFLICT (party_id) DO UPDATE SET generation=g.generation+1,present=true
                WHERE NOT g.present AND g.generation<9223372036854775807::bigint;
            ELSE
                UPDATE public.hidra_party_eligibility_generation SET generation=generation+1,present=false
                WHERE party_id=item.id AND present AND generation<9223372036854775807::bigint;
            END IF;
            IF NOT FOUND THEN
                RAISE EXCEPTION 'Owner generation unavailable or exhausted' USING ERRCODE='23514';
            END IF;
        END LOOP;
    END IF;
    RETURN NULL;
END;
$owner$;
REVOKE ALL ON FUNCTION public.hidra_p25_party_eligibility_write() FROM PUBLIC;
CREATE TRIGGER trg_p25_party_eligibility_write AFTER INSERT OR UPDATE OF id,status OR DELETE
ON public.hidra_party_party FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_party_eligibility_write();

CREATE FUNCTION public.hidra_p25_party_eligibility_truncate() RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp AS $owner$
BEGIN
    IF TG_WHEN <> 'BEFORE' OR TG_LEVEL <> 'STATEMENT' OR TG_OP <> 'TRUNCATE' OR TG_NARGS <> 0
       OR NOT ((TG_RELID='public.hidra_party_party'::regclass AND TG_NAME='trg_p25_party_eligibility_root_truncate')
               OR (TG_RELID='public.hidra_party_eligibility_generation'::regclass AND TG_NAME='trg_p25_party_eligibility_registry_truncate')) THEN
        RAISE EXCEPTION 'Invalid owner truncate invocation' USING ERRCODE='23514';
    END IF;
    RAISE EXCEPTION 'Owner generation lifetime cannot be truncated' USING ERRCODE='23514';
END;
$owner$;
REVOKE ALL ON FUNCTION public.hidra_p25_party_eligibility_truncate() FROM PUBLIC;
CREATE TRIGGER trg_p25_party_eligibility_root_truncate BEFORE TRUNCATE ON public.hidra_party_party
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_party_eligibility_truncate();
CREATE TRIGGER trg_p25_party_eligibility_registry_truncate BEFORE TRUNCATE ON public.hidra_party_eligibility_generation
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_party_eligibility_truncate();
