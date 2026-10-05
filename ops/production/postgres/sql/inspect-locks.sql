\pset pager off
\timing on
SELECT blocked.pid AS blocked_pid,blocked.usename AS blocked_user,blocking.pid AS blocking_pid,blocking.usename AS blocking_user,
blocked.wait_event_type,blocked.wait_event,now()-blocked.query_start AS blocked_for,now()-blocking.query_start AS blocking_for,
left(blocked.query,400) AS blocked_query,left(blocking.query,400) AS blocking_query
FROM pg_stat_activity blocked
JOIN LATERAL unnest(pg_blocking_pids(blocked.pid)) AS blocker_pid(pid) ON true
JOIN pg_stat_activity blocking ON blocking.pid=blocker_pid.pid
ORDER BY blocked_for DESC;
