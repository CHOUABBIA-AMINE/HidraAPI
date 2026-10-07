-- HMR-093: correct local output lineage; Documents references remain owner-validated IDs.
ALTER TABLE hidra_reporting_output_artifact DROP CONSTRAINT fk_hra111_reporting_009;
ALTER TABLE hidra_reporting_output_artifact ADD CONSTRAINT hmr093_run_fk FOREIGN KEY(report_run_id) REFERENCES hidra_reporting_run(id);
ALTER TABLE hidra_reporting_output_artifact ADD CONSTRAINT hmr093_documents_reference CHECK(
    (storage_object_reference_id IS NULL OR length(btrim(storage_object_reference_id))>0)
    AND (document_reference_id IS NULL OR length(btrim(document_reference_id))>0)
    AND (storage_object_reference_id IS NOT NULL OR document_reference_id IS NOT NULL));
