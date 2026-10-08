-- HMR-105: no closure evidence is deleted, merged or fabricated.
LOCK TABLE hidra_alarm_closure IN SHARE ROW EXCLUSIVE MODE;
DO $$ BEGIN
    IF EXISTS(SELECT alarm_id FROM hidra_alarm_closure GROUP BY alarm_id HAVING count(*)>1) THEN
        RAISE EXCEPTION 'HMR-105: existing duplicate Alarm closures; owner remediation required';
    END IF;
    IF EXISTS(SELECT 1 FROM hidra_alarm_closure c LEFT JOIN hidra_alarm a ON a.id=c.alarm_id WHERE a.id IS NULL) THEN
        RAISE EXCEPTION 'HMR-105: dangling Alarm closure';
    END IF;
END $$;
ALTER TABLE hidra_alarm_closure ADD CONSTRAINT uq_hmr105_alarm_closure UNIQUE(alarm_id);
