\pset pager off
\timing on
SELECT pid,usename,application_name,state,now()-COALESCE(xact_start,query_start) AS elapsed,wait_event_type,wait_event,left(query,500) AS query
FROM pg_stat_activity
WHERE datname=current_database() AND pid<>pg_backend_pid()
AND ((state='active' AND query_start<now()-interval '30 seconds') OR (xact_start IS NOT NULL AND xact_start<now()-interval '60 seconds'))
ORDER BY elapsed DESC;
