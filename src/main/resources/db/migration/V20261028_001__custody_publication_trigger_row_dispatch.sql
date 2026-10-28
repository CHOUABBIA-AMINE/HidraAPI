-- HAPI-596 Package B repair. Preserve applied migrations and all publication evidence.
-- Dispatch by relation before planning expressions that reference relation-specific NEW fields.
DO $precondition$
BEGIN
 IF NOT EXISTS (SELECT 1 FROM pg_catalog.pg_proc p
   WHERE p.oid='public.hidra_custody_publication_admit()'::regprocedure
     AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='bb2c7e7a9c5b864ca4d2b573dad078297ce5215c95165c7ea915f395e86543e8') THEN
  RAISE EXCEPTION 'Unexpected product publication admission predecessor' USING ERRCODE='23514';
 END IF;
END;
$precondition$;

CREATE OR REPLACE FUNCTION public.hidra_custody_publication_admit() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
DECLARE observed timestamptz; previous public.hidra_custody_product_publication_head%ROWTYPE; latest bigint; transition public.hidra_custody_product_publication_event%ROWTYPE;
BEGIN
 observed:=clock_timestamp();
 IF NEW.backend_pid<>pg_backend_pid() OR NEW.transaction_id<>txid_current() OR observed<NEW.sampled_at OR observed>=NEW.valid_until THEN
  RAISE EXCEPTION 'Invalid product publication origin' USING ERRCODE='23514'; END IF;
 PERFORM id FROM public.hidra_custody_product_engineering_draft d WHERE d.id=NEW.product_draft_id
  AND d.company_party_id=NEW.company_party_id AND d.catalog_product_id=NEW.catalog_product_id FOR UPDATE;
 IF NOT FOUND THEN RAISE EXCEPTION 'Product publication ownership mismatch' USING ERRCODE='23514'; END IF;
 IF TG_TABLE_NAME='hidra_custody_product_publication_candidate' THEN
  IF NEW.metadata_version IS DISTINCT FROM (SELECT max(version) FROM public.hidra_custody_product_engineering_draft_version WHERE draft_id=NEW.product_draft_id)
   OR NEW.supplied_version IS DISTINCT FROM (SELECT max(version) FROM public.hidra_custody_supplied_fluid_property_version WHERE product_draft_id=NEW.product_draft_id)
   OR NOT EXISTS(SELECT 1 FROM public.hidra_custody_supplied_fluid_property_version v WHERE v.product_draft_id=NEW.product_draft_id AND v.version=NEW.supplied_version AND v.product_metadata_version=NEW.metadata_version) THEN
   RAISE EXCEPTION 'Stale publication candidate sources' USING ERRCODE='23514'; END IF;
 ELSIF TG_TABLE_NAME='hidra_custody_product_publication_event' THEN
  SELECT * INTO previous FROM public.hidra_custody_product_publication_head WHERE product_draft_id=NEW.product_draft_id;
  SELECT COALESCE(max(epoch),0) INTO latest FROM public.hidra_custody_product_publication_event WHERE product_draft_id=NEW.product_draft_id;
  IF latest=9223372036854775807::bigint OR NEW.epoch<>latest+1 OR COALESCE(previous.epoch,0)<>latest
   OR NEW.prior_publication_id IS DISTINCT FROM previous.publication_id OR NEW.effective_at>observed OR NEW.effective_at<NEW.sampled_at
   OR (latest>0 AND NEW.effective_at<=previous.effective_at)
   OR (NEW.action='WITHDRAW' AND previous.state IS DISTINCT FROM 'ACTIVE')
   OR (NEW.action='REACTIVATE' AND previous.state IS DISTINCT FROM 'WITHDRAWN') THEN
   RAISE EXCEPTION 'Invalid publication transition' USING ERRCODE='23514'; END IF;
 ELSIF TG_TABLE_NAME='hidra_custody_product_publication_head' THEN
  SELECT * INTO transition FROM public.hidra_custody_product_publication_event WHERE event_id=NEW.event_id AND product_draft_id=NEW.product_draft_id;
  IF NOT FOUND OR (NEW.publication_id,NEW.epoch,NEW.state,NEW.effective_at) IS DISTINCT FROM
    (transition.publication_id,transition.epoch,transition.state,transition.effective_at)
   OR NEW.epoch IS DISTINCT FROM (SELECT max(epoch) FROM public.hidra_custody_product_publication_event WHERE product_draft_id=NEW.product_draft_id)
   OR (TG_OP='INSERT' AND NEW.epoch<>1) THEN RAISE EXCEPTION 'Invalid publication head' USING ERRCODE='23514'; END IF;
  IF TG_OP='UPDATE' THEN
   IF NEW.product_draft_id<>OLD.product_draft_id OR NEW.company_party_id<>OLD.company_party_id OR NEW.catalog_product_id<>OLD.catalog_product_id
    OR NEW.epoch<>OLD.epoch+1 OR transition.prior_publication_id<>OLD.publication_id THEN RAISE EXCEPTION 'Stale publication head' USING ERRCODE='23514'; END IF;
  END IF;
 ELSIF TG_TABLE_NAME='hidra_custody_product_publication_request' THEN
  IF NEW.kind<>'PREPARE' THEN
  IF NOT EXISTS(SELECT 1 FROM public.hidra_custody_product_publication_event e WHERE e.event_id=NEW.event_id AND e.product_draft_id=NEW.product_draft_id
    AND e.request_id=NEW.request_id AND e.command_sha256=NEW.command_sha256 AND e.action=NEW.kind AND e.actor_user_id=NEW.actor_user_id) THEN
   RAISE EXCEPTION 'Invalid publication receipt' USING ERRCODE='23514'; END IF;
  END IF;
 END IF;
 NEW.created_at:=observed;
 RETURN NEW;
END;
$guard$;
