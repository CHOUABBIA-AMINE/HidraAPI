\pset pager off
\timing on
SELECT now() AS observed_at,pid,usename,application_name,client_addr,backend_type,state,wait_event_type,wait_event,xact_start,query_start,state_change,left(query,500) AS query
FROM pg_stat_activity
WHERE datname=current_database()
ORDER BY CASE WHEN state='active' THEN 0 ELSE 1 END, query_start NULLS LAST;
