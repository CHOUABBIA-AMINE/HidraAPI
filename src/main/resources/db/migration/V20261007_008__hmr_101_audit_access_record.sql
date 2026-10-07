-- HMR-101: nullable local evidence references and immutable access records.
ALTER TABLE hidra_audit_access_record ADD CONSTRAINT hmr101_event_fk FOREIGN KEY(audit_event_id) REFERENCES hidra_audit_event(id);
ALTER TABLE hidra_audit_access_record ADD CONSTRAINT hmr101_export_fk FOREIGN KEY(export_request_id) REFERENCES hidra_audit_export_request(id);
CREATE TRIGGER hmr101_access_immutable BEFORE UPDATE OR DELETE ON hidra_audit_access_record FOR EACH ROW EXECUTE FUNCTION hmr095_forbid_evidence_mutation();
