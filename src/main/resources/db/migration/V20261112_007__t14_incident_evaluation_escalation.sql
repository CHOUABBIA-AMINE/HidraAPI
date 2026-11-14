-- T14 Incident-owned human escalation from two qualified owner sources.
-- All cross-module IDs are scalars; NO foreign persistence owner FKs.
INSERT INTO public.hidra_incident_catalog_entry
    (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
VALUES ('t14-incident-escalation','INCIDENT_TIMELINE_ENTRY_TYPE',
        'HUMAN_SOURCE_ESCALATION',true,0,true,now(),now())
ON CONFLICT (id) DO NOTHING;

CREATE TABLE public.hidra_incident_evaluation_case (
  evaluation_id varchar(80) PRIMARY KEY CHECK (btrim(evaluation_id)<>''),
  incident_id varchar(80) NOT NULL UNIQUE REFERENCES public.hidra_incident(id) ON DELETE RESTRICT,
  company_id varchar(80) NOT NULL CHECK (btrim(company_id)<>''),
  asset_type varchar(80) NOT NULL CHECK (asset_type IN ('FACILITY','EQUIPMENT')),
  asset_id varchar(80) NOT NULL CHECK (btrim(asset_id)<>''),
  classification_id varchar(80) NOT NULL REFERENCES public.hidra_incident_catalog_entry(id),
  severity_id varchar(80) NOT NULL REFERENCES public.hidra_incident_catalog_entry(id),
  title_fr varchar(255) NOT NULL CHECK (btrim(title_fr)<>''),
  reason varchar(500) NOT NULL CHECK (btrim(reason)<>''),
  actor_id varchar(80) NOT NULL CHECK (btrim(actor_id)<>''),
  created_at timestamptz NOT NULL CHECK (isfinite(created_at)),
  UNIQUE (evaluation_id,incident_id)
);
CREATE INDEX ix_t14_incident_company_asset
 ON public.hidra_incident_evaluation_case(company_id,asset_type,asset_id);

CREATE TABLE public.hidra_incident_escalated_source (
  source_type varchar(40) NOT NULL CHECK (source_type IN ('ALARM','LEAK_CANDIDATE')),
  source_id varchar(80) NOT NULL CHECK (btrim(source_id)<>''),
  evaluation_id varchar(80) NOT NULL,
  incident_id varchar(80) NOT NULL REFERENCES public.hidra_incident(id) ON DELETE RESTRICT,
  actor_id varchar(80) NOT NULL CHECK (btrim(actor_id)<>''),
  reason varchar(500) NOT NULL CHECK (btrim(reason)<>''),
  escalated_at timestamptz NOT NULL CHECK (isfinite(escalated_at)),
  PRIMARY KEY (source_type,source_id),
  UNIQUE (evaluation_id,source_type),
  FOREIGN KEY (evaluation_id,incident_id)
       REFERENCES public.hidra_incident_evaluation_case(evaluation_id,incident_id) ON DELETE RESTRICT
);
CREATE INDEX ix_t14_escalation_timeline ON public.hidra_incident_escalated_source(incident_id,escalated_at);

CREATE FUNCTION public.t14_incident_escalation_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
  RAISE EXCEPTION 'T14: immutable human Incident escalation evidence';
END $$;
CREATE TRIGGER trg_t14_case_no_change BEFORE UPDATE OR DELETE ON public.hidra_incident_evaluation_case
  FOR EACH ROW EXECUTE FUNCTION public.t14_incident_escalation_immutable();
CREATE TRIGGER trg_t14_case_no_truncate BEFORE TRUNCATE ON public.hidra_incident_evaluation_case
  FOR EACH STATEMENT EXECUTE FUNCTION public.t14_incident_escalation_immutable();
CREATE TRIGGER trg_t14_source_no_change BEFORE UPDATE OR DELETE ON public.hidra_incident_escalated_source
  FOR EACH ROW EXECUTE FUNCTION public.t14_incident_escalation_immutable();
CREATE TRIGGER trg_t14_source_no_truncate BEFORE TRUNCATE ON public.hidra_incident_escalated_source
  FOR EACH STATEMENT EXECUTE FUNCTION public.t14_incident_escalation_immutable();

-- Guard correlation against a forged or moved Incident parent.
CREATE FUNCTION public.t14_incident_case_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM public.hidra_incident i WHERE i.id=NEW.incident_id
        AND i.status='OPEN' AND i.topology_asset_type_code=NEW.asset_type
        AND i.topology_asset_id=NEW.asset_id AND i.classification_id=NEW.classification_id
        AND i.severity_id=NEW.severity_id AND i.title=NEW.title_fr) THEN
    RAISE EXCEPTION 'T14: Incident case/evaluation correlation mismatch';
  END IF;
  RETURN NEW;
END $$;
CREATE TRIGGER trg_t14_case_insert_guard BEFORE INSERT ON public.hidra_incident_evaluation_case
 FOR EACH ROW EXECUTE FUNCTION public.t14_incident_case_guard();

-- Retain existing Incident evidence and timeline as immutable once linked.
CREATE FUNCTION public.t14_incident_evidence_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
  IF EXISTS (SELECT 1 FROM public.hidra_incident_escalated_source s WHERE
         s.incident_id=OLD.incident_id AND s.source_id=OLD.evidence_reference_id
         AND s.source_type=OLD.evidence_type) THEN
     RAISE EXCEPTION 'T14: linked Incident source evidence is immutable';
  END IF;
  IF TG_OP='DELETE' THEN RETURN OLD; END IF;
  RETURN NEW;
END $$;
CREATE TRIGGER trg_t14_existing_evidence_guard
 BEFORE UPDATE OR DELETE ON public.hidra_incident_evidence_link
 FOR EACH ROW EXECUTE FUNCTION public.t14_incident_evidence_guard();
