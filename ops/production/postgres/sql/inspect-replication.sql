\pset pager off
\timing on
SELECT pg_is_in_recovery() AS is_recovery,pg_current_wal_lsn() AS current_wal_lsn;
SELECT pid,application_name,client_addr,state,sync_state,write_lag,flush_lag,replay_lag,sent_lsn,write_lsn,flush_lsn,replay_lsn
FROM pg_stat_replication ORDER BY application_name;
SELECT slot_name,slot_type,active,restart_lsn,confirmed_flush_lsn,wal_status
FROM pg_replication_slots ORDER BY slot_name;
