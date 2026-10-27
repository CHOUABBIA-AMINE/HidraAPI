-- HAPI-596A: empty configured source-publication policy, no assignments or decisions.
CREATE TABLE public.hidra_workflow_custody_publication_policy (
 company_party_id varchar(80) PRIMARY KEY,
 definition_id varchar(80) NOT NULL,
 definition_version integer NOT NULL,
 target_type_id varchar(80) NOT NULL,
 purpose_id varchar(80) NOT NULL,
 active boolean NOT NULL,
 created_at timestamptz NOT NULL,
 updated_at timestamptz NOT NULL,
 CONSTRAINT hidra_publication_policy_definition_fk FOREIGN KEY(definition_id,definition_version) REFERENCES public.hidra_workflow_definition(id,version),
 CONSTRAINT hidra_publication_policy_type_fk FOREIGN KEY(target_type_id) REFERENCES public.hidra_workflow_type_catalog(id),
 CONSTRAINT hidra_publication_policy_purpose_fk FOREIGN KEY(purpose_id) REFERENCES public.hidra_workflow_type_catalog(id),
 CONSTRAINT hidra_publication_policy_company_ck CHECK(company_party_id=btrim(company_party_id) AND length(company_party_id)>0),
 CONSTRAINT hidra_publication_policy_version_ck CHECK(definition_version>0),
 CONSTRAINT hidra_publication_policy_time_ck CHECK(updated_at>=created_at)
);
REVOKE INSERT,UPDATE,DELETE,TRUNCATE,TRIGGER,REFERENCES ON public.hidra_workflow_custody_publication_policy FROM PUBLIC;
