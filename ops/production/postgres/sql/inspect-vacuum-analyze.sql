\pset pager off
\timing on
SELECT schemaname,relname,n_live_tup,n_dead_tup,
CASE WHEN n_live_tup+n_dead_tup=0 THEN 0 ELSE round((100.0*n_dead_tup/(n_live_tup+n_dead_tup))::numeric,2) END AS dead_tuple_pct,
last_vacuum,last_autovacuum,vacuum_count,autovacuum_count,last_analyze,last_autoanalyze,analyze_count,autoanalyze_count
FROM pg_stat_user_tables
ORDER BY dead_tuple_pct DESC,n_dead_tup DESC;
SELECT schemaname,relname,pg_size_pretty(pg_total_relation_size(relid)) AS total_size,seq_scan,idx_scan,n_live_tup,n_dead_tup
FROM pg_stat_user_tables
ORDER BY pg_total_relation_size(relid) DESC
LIMIT 50;
