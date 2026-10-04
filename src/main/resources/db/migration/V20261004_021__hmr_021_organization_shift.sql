-- HMR-021: Shift semantic remediation.
-- Existing migrations remain immutable.
-- Base schema already requires start_time/end_time/timezone to be non-null.
-- These checks align blank-string persistence with the canonical domain requiredness.

ALTER TABLE hidra_org_shift
    ADD CONSTRAINT ck_hmr021_shift_start_time_nonblank
    CHECK (btrim(start_time) <> '')
    NOT VALID;

ALTER TABLE hidra_org_shift
    ADD CONSTRAINT ck_hmr021_shift_end_time_nonblank
    CHECK (btrim(end_time) <> '')
    NOT VALID;

ALTER TABLE hidra_org_shift
    ADD CONSTRAINT ck_hmr021_shift_timezone_nonblank
    CHECK (btrim(timezone) <> '')
    NOT VALID;

ALTER TABLE hidra_org_shift
    VALIDATE CONSTRAINT ck_hmr021_shift_start_time_nonblank;

ALTER TABLE hidra_org_shift
    VALIDATE CONSTRAINT ck_hmr021_shift_end_time_nonblank;

ALTER TABLE hidra_org_shift
    VALIDATE CONSTRAINT ck_hmr021_shift_timezone_nonblank;
