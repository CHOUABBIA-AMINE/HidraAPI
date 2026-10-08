-- HMR-072: local optional references; preserve cross-module scalar ownership.
ALTER TABLE hidra_integrity_assessment ADD CONSTRAINT fk_hmr072_program_id
    FOREIGN KEY (program_id) REFERENCES hidra_integrity_program(id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_assessment VALIDATE CONSTRAINT fk_hmr072_program_id;
