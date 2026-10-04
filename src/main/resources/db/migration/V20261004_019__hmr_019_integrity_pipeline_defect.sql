-- HMR-019: PipelineDefect semantic remediation.
-- Existing migrations remain immutable.

ALTER TABLE hidra_integrity_pipeline_defect
    ADD CONSTRAINT fk_hmr019_integrity_pipeline_defect_source_finding
    FOREIGN KEY (source_finding_id)
    REFERENCES hidra_integrity_inspection_finding (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_integrity_pipeline_defect
    VALIDATE CONSTRAINT fk_hmr019_integrity_pipeline_defect_source_finding;
