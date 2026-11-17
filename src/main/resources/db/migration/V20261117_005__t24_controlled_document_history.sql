-- T24: enrolled, scoped Topology FACILITY Documents, no legacy record rewrite.
-- Definitions only. Identity grants are not seeded.
INSERT INTO public.hidra_identity_permission
(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
('t24-doc-create','documents:controlled:create','documents','CONTROLLED_DOCUMENT','create',true,'ACTIVE',now(),now()),
('t24-doc-write','documents:controlled:write','documents','CONTROLLED_DOCUMENT','write',true,'ACTIVE',now(),now()),
('t24-doc-read','documents:controlled:read','documents','CONTROLLED_DOCUMENT','read',true,'ACTIVE',now(),now()),
('t24-doc-accept','documents:controlled:accept','documents','CONTROLLED_DOCUMENT','accept',true,'ACTIVE',now(),now())
ON CONFLICT (code) DO NOTHING;

CREATE UNIQUE INDEX uq_t24_version_parent_id
    ON public.hidra_documents_document_version(document_id,id);

CREATE TABLE public.hidra_documents_controlled_scope (
    document_id varchar(80) PRIMARY KEY REFERENCES public.hidra_documents_document(id) ON DELETE RESTRICT,
    company_id varchar(120) NOT NULL CHECK (length(trim(company_id))>0),
    facility_id varchar(120) NOT NULL CHECK (length(trim(facility_id))>0),
    create_request_id varchar(120) NOT NULL CHECK (length(trim(create_request_id))>0),
    payload_digest char(64) NOT NULL CHECK (payload_digest ~ '^[0-9a-f]{64}$'),
    created_by_actor_id varchar(80) NOT NULL,
    created_at timestamptz NOT NULL,
    CONSTRAINT uq_t24_scope_create UNIQUE(company_id,facility_id,create_request_id)
);
CREATE INDEX ix_t24_scope_facility ON public.hidra_documents_controlled_scope(company_id,facility_id,document_id);

CREATE TABLE public.hidra_documents_controlled_decision (
    id varchar(80) PRIMARY KEY,
    document_id varchar(80) NOT NULL REFERENCES public.hidra_documents_controlled_scope(document_id) ON DELETE RESTRICT,
    version_id varchar(80) NOT NULL,
    previous_version_id varchar(80),
    request_id varchar(120) NOT NULL CHECK (length(trim(request_id))>0),
    payload_digest char(64) NOT NULL CHECK (payload_digest ~ '^[0-9a-f]{64}$'),
    actor_id varchar(80) NOT NULL CHECK (length(trim(actor_id))>0),
    reason varchar(500) NOT NULL CHECK (length(trim(reason))>0),
    action varchar(20) NOT NULL CHECK (action IN ('ACCEPT','SUPERSEDE')),
    checksum_sha256 char(64) NOT NULL CHECK (checksum_sha256 ~ '^[0-9a-f]{64}$'),
    content_length bigint NOT NULL CHECK (content_length>=0),
    company_id varchar(120) NOT NULL,
    facility_id varchar(120) NOT NULL,
    link_role_id varchar(80) NOT NULL,
    link_id varchar(80) NOT NULL UNIQUE REFERENCES public.hidra_documents_target_link(id) ON DELETE RESTRICT,
    decided_at timestamptz NOT NULL,
    CONSTRAINT uq_t24_decision_request UNIQUE(document_id,request_id),
    CONSTRAINT uq_t24_decision_version UNIQUE(version_id),
    CONSTRAINT uq_t24_decision_previous UNIQUE(previous_version_id),
    CONSTRAINT ck_t24_decision_transition CHECK
        ((action='ACCEPT' AND previous_version_id IS NULL)
         OR (action='SUPERSEDE' AND previous_version_id IS NOT NULL)),
    CONSTRAINT fk_t24_decision_current_parent FOREIGN KEY(document_id,version_id)
         REFERENCES public.hidra_documents_document_version(document_id,id) ON DELETE RESTRICT,
    CONSTRAINT fk_t24_decision_previous_parent FOREIGN KEY(document_id,previous_version_id)
         REFERENCES public.hidra_documents_document_version(document_id,id) ON DELETE RESTRICT
);
CREATE INDEX ix_t24_decision_history ON public.hidra_documents_controlled_decision(document_id,decided_at,id);

-- Existing enrolled records can never have their receipt or immutable scope rewritten.
CREATE FUNCTION public.hidra_documents_t24_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    RAISE EXCEPTION 'T24 immutable controlled Documents evidence';
END;
$$;
CREATE TRIGGER tr_t24_scope_immutable BEFORE UPDATE OR DELETE ON public.hidra_documents_controlled_scope
    FOR EACH ROW EXECUTE FUNCTION public.hidra_documents_t24_immutable();
CREATE TRIGGER tr_t24_decision_immutable BEFORE UPDATE OR DELETE ON public.hidra_documents_controlled_decision
    FOR EACH ROW EXECUTE FUNCTION public.hidra_documents_t24_immutable();

-- Guard the legacy JPA write paths at the physical owner boundary.
CREATE FUNCTION public.hidra_documents_t24_parent_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE enrolled boolean;
BEGIN
    SELECT EXISTS (SELECT 1 FROM public.hidra_documents_controlled_scope WHERE document_id=OLD.id) INTO enrolled;
    IF NOT enrolled THEN RETURN COALESCE(NEW,OLD); END IF;
    IF TG_OP='DELETE' THEN RAISE EXCEPTION 'T24 enrolled document delete forbidden'; END IF;
    IF NOT ((OLD.status='DRAFT' AND OLD.current_version_id IS NULL
               AND NEW.status='ACTIVE' AND NEW.current_version_id IS NOT NULL)
            OR (OLD.status='ACTIVE' AND OLD.current_version_id IS NOT NULL
               AND NEW.status='ACTIVE' AND NEW.current_version_id IS DISTINCT FROM OLD.current_version_id
               AND NEW.current_version_id IS NOT NULL))
       OR (to_jsonb(NEW)-'status'-'current_version_id'-'updated_at')
           IS DISTINCT FROM (to_jsonb(OLD)-'status'-'current_version_id'-'updated_at')
    THEN RAISE EXCEPTION 'T24 enrolled document may only advance its current pointer'; END IF;
    RETURN NEW;
END;
$$;
CREATE TRIGGER tr_t24_parent_guard BEFORE UPDATE OR DELETE ON public.hidra_documents_document
    FOR EACH ROW EXECUTE FUNCTION public.hidra_documents_t24_parent_guard();

CREATE FUNCTION public.hidra_documents_t24_version_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE enrolled boolean;
BEGIN
    SELECT EXISTS (SELECT 1 FROM public.hidra_documents_controlled_scope WHERE document_id=OLD.document_id) INTO enrolled;
    IF NOT enrolled THEN RETURN COALESCE(NEW,OLD); END IF;
    IF TG_OP='DELETE' THEN RAISE EXCEPTION 'T24 enrolled version delete forbidden'; END IF;
    IF (to_jsonb(NEW)-'version_status'-'superseded_by_version_id')
            IS DISTINCT FROM (to_jsonb(OLD)-'version_status'-'superseded_by_version_id')
      OR NOT ((OLD.version_status='DRAFT' AND NEW.version_status='CURRENT'
                AND OLD.superseded_by_version_id IS NULL AND NEW.superseded_by_version_id IS NULL)
              OR (OLD.version_status='CURRENT' AND NEW.version_status='SUPERSEDED'
                AND OLD.superseded_by_version_id IS NULL AND NEW.superseded_by_version_id IS NOT NULL))
    THEN RAISE EXCEPTION 'T24 controlled version metadata and transitions are frozen'; END IF;
    RETURN NEW;
END;
$$;
CREATE TRIGGER tr_t24_version_guard BEFORE UPDATE OR DELETE ON public.hidra_documents_document_version
    FOR EACH ROW EXECUTE FUNCTION public.hidra_documents_t24_version_guard();

CREATE FUNCTION public.hidra_documents_t24_link_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE s record;
BEGIN
    SELECT * INTO s FROM public.hidra_documents_controlled_scope
        WHERE document_id=CASE WHEN TG_OP='DELETE' THEN OLD.document_id ELSE NEW.document_id END;
    IF NOT FOUND THEN RETURN COALESCE(NEW,OLD); END IF;
    IF TG_OP <> 'INSERT' THEN RAISE EXCEPTION 'T24 controlled target evidence immutable'; END IF;
    IF NEW.target_module <> 'topology' OR NEW.target_type_code <> 'FACILITY'
       OR NEW.target_id <> s.facility_id OR NEW.document_version_id IS NULL
       OR NOT NEW.active OR NEW.unlinked_at IS NOT NULL
       OR NOT EXISTS (SELECT 1 FROM public.hidra_documents_document_version v
                JOIN public.hidra_documents_document d ON d.id=v.document_id
                WHERE v.document_id=NEW.document_id AND v.id=NEW.document_version_id
                  AND v.version_status='CURRENT' AND d.current_version_id=v.id)
    THEN RAISE EXCEPTION 'T24 target link must bind exact facility and version'; END IF;
    RETURN NEW;
END;
$$;
CREATE TRIGGER tr_t24_link_guard BEFORE INSERT OR UPDATE OR DELETE ON public.hidra_documents_target_link
    FOR EACH ROW EXECUTE FUNCTION public.hidra_documents_t24_link_guard();

CREATE FUNCTION public.hidra_documents_t24_storage_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM public.hidra_documents_document_version v
       JOIN public.hidra_documents_controlled_scope s ON s.document_id=v.document_id
       WHERE v.storage_object_id=OLD.id)
    THEN RAISE EXCEPTION 'T24 controlled storage metadata immutable'; END IF;
    RETURN COALESCE(NEW,OLD);
END;
$$;
CREATE TRIGGER tr_t24_storage_guard BEFORE UPDATE OR DELETE ON public.hidra_documents_storage_object
    FOR EACH ROW EXECUTE FUNCTION public.hidra_documents_t24_storage_guard();

-- Deferred consistency: every accepted version, pointer and custodian link seals together.
CREATE FUNCTION public.hidra_documents_t24_consistency() RETURNS trigger LANGUAGE plpgsql AS $$
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
          AND NOT EXISTS (SELECT 1 FROM public.hidra_documents_controlled_decision d
            WHERE d.document_id=docid AND d.version_id=ver.id))
    THEN RAISE EXCEPTION 'T24 accepted version without immutable receipt'; END IF;
    RETURN NULL;
END;
$$;
CREATE CONSTRAINT TRIGGER tr_t24_scope_consistency AFTER INSERT ON public.hidra_documents_controlled_scope
    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.hidra_documents_t24_consistency();
CREATE CONSTRAINT TRIGGER tr_t24_document_consistency AFTER UPDATE ON public.hidra_documents_document
    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.hidra_documents_t24_consistency();
CREATE CONSTRAINT TRIGGER tr_t24_version_consistency AFTER INSERT OR UPDATE ON public.hidra_documents_document_version
    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.hidra_documents_t24_consistency();
CREATE CONSTRAINT TRIGGER tr_t24_decision_consistency AFTER INSERT ON public.hidra_documents_controlled_decision
    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.hidra_documents_t24_consistency();
CREATE CONSTRAINT TRIGGER tr_t24_link_consistency AFTER INSERT ON public.hidra_documents_target_link
    DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.hidra_documents_t24_consistency();

CREATE FUNCTION public.hidra_documents_t24_no_truncate() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM public.hidra_documents_controlled_scope)
    THEN RAISE EXCEPTION 'T24 controlled historical Documents prohibit truncate'; END IF;
    RETURN NULL;
END;
$$;
CREATE TRIGGER tr_t24_scope_no_truncate BEFORE TRUNCATE ON public.hidra_documents_controlled_scope
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_documents_t24_no_truncate();
CREATE TRIGGER tr_t24_decision_no_truncate BEFORE TRUNCATE ON public.hidra_documents_controlled_decision
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_documents_t24_no_truncate();
CREATE TRIGGER tr_t24_document_no_truncate BEFORE TRUNCATE ON public.hidra_documents_document
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_documents_t24_no_truncate();
CREATE TRIGGER tr_t24_version_no_truncate BEFORE TRUNCATE ON public.hidra_documents_document_version
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_documents_t24_no_truncate();
CREATE TRIGGER tr_t24_storage_no_truncate BEFORE TRUNCATE ON public.hidra_documents_storage_object
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_documents_t24_no_truncate();
CREATE TRIGGER tr_t24_link_no_truncate BEFORE TRUNCATE ON public.hidra_documents_target_link
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_documents_t24_no_truncate();
