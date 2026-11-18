-- Forward-only corrective migration for T24 (CI #1289, PostgreSQL 16.16).
-- Preserve V20261117_005 and the existing deferred constraints/triggers.
-- The PL/pgSQL d record shadows a nested SQL d alias at commit; rename only
-- that anti-join SQL alias to decision_row to preserve the same invariant.

CREATE OR REPLACE FUNCTION public.hidra_documents_t24_consistency() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE docid varchar(80);
DECLARE d record;
DECLARE s record;
DECLARE receipt record;
DECLARE v record;
DECLARE l record;
DECLARE oldv record;
BEGIN
    IF TG_TABLE_NAME='hidra_documents_document' THEN docid=NEW.id;
    ELSE docid=NEW.document_id; END IF;
    SELECT * INTO s FROM public.hidra_documents_controlled_scope WHERE document_id=docid;
    IF NOT FOUND THEN RETURN NULL; END IF;
    SELECT * INTO d FROM public.hidra_documents_document WHERE id=docid;
    IF NOT FOUND OR d.owner_module<>'topology' OR d.owner_target_type_code<>'FACILITY'
       OR d.owner_target_id<>s.facility_id OR d.created_by_actor_id<>s.created_by_actor_id
    THEN RAISE EXCEPTION 'T24 enrolled scope owner identity mismatch'; END IF;
    IF d.current_version_id IS NULL THEN
        IF d.status<>'DRAFT' OR EXISTS (SELECT 1 FROM public.hidra_documents_controlled_decision
                    WHERE document_id=docid)
        THEN RAISE EXCEPTION 'T24 invalid controlled draft head'; END IF;
    ELSE
        IF (SELECT count(*) FROM public.hidra_documents_document_version
                WHERE document_id=docid AND version_status='CURRENT')<>1
           OR (SELECT count(*) FROM public.hidra_documents_controlled_decision
                WHERE document_id=docid AND previous_version_id IS NULL)<>1
        THEN RAISE EXCEPTION 'T24 precisely one accepted current and initial decision required'; END IF;
        SELECT * INTO v FROM public.hidra_documents_document_version WHERE id=d.current_version_id
            AND document_id=docid;
        IF NOT FOUND OR d.status<>'ACTIVE' OR v.version_status<>'CURRENT'
           OR NOT EXISTS (SELECT 1 FROM public.hidra_documents_controlled_decision
                         WHERE version_id=v.id AND document_id=docid)
        THEN RAISE EXCEPTION 'T24 controlled current pointer has no sealed decision'; END IF;
    END IF;
    FOR receipt IN SELECT * FROM public.hidra_documents_controlled_decision WHERE document_id=docid LOOP
        SELECT * INTO v FROM public.hidra_documents_document_version WHERE id=receipt.version_id
            AND document_id=docid;
        SELECT * INTO l FROM public.hidra_documents_target_link WHERE id=receipt.link_id
            AND document_id=docid AND document_version_id=receipt.version_id;
        IF NOT FOUND OR v.id IS NULL
           OR l.target_module<>'topology' OR l.target_type_code<>'FACILITY'
           OR l.target_id<>s.facility_id OR NOT l.primary_link OR NOT l.active
           OR l.link_role_id<>receipt.link_role_id
           OR l.linked_by_actor_id<>receipt.actor_id
           OR receipt.company_id<>s.company_id OR receipt.facility_id<>s.facility_id
           OR receipt.actor_id IN (d.created_by_actor_id,v.uploaded_by_actor_id)
           OR receipt.content_length<>v.file_size_bytes
           OR receipt.checksum_sha256<>lower(v.checksum_value)
           OR v.checksum_algorithm<>'SHA-256'
        THEN RAISE EXCEPTION 'T24 controlled decision lineage invalid'; END IF;
        IF receipt.previous_version_id IS NULL THEN
            IF receipt.action<>'ACCEPT' THEN RAISE EXCEPTION 'T24 initial decision mismatch'; END IF;
        ELSE
            SELECT * INTO oldv FROM public.hidra_documents_document_version
                 WHERE id=receipt.previous_version_id AND document_id=docid;
            IF NOT FOUND OR oldv.version_status<>'SUPERSEDED'
               OR oldv.superseded_by_version_id<>receipt.version_id
               OR oldv.version_number>=v.version_number
            THEN RAISE EXCEPTION 'T24 predecessor lineage invalid'; END IF;
        END IF;
    END LOOP;
    IF EXISTS (SELECT 1 FROM public.hidra_documents_document_version ver
        WHERE ver.document_id=docid AND ver.version_status IN ('CURRENT','SUPERSEDED')
          AND NOT EXISTS (SELECT 1 FROM public.hidra_documents_controlled_decision decision_row
            WHERE decision_row.document_id=docid AND decision_row.version_id=ver.id))
    THEN RAISE EXCEPTION 'T24 accepted version without immutable receipt'; END IF;
    RETURN NULL;
END;
$$;
