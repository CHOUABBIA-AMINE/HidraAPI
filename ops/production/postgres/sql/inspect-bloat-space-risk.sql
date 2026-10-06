\pset pager off
\timing on

-- Extension-free space-risk indicators. These are triage signals, not a physical
-- bloat percentage and not authorization to drop/rebuild an object.
SELECT
  schemaname,
  relname AS table_name,
  n_live_tup,
  n_dead_tup,
  CASE
    WHEN n_live_tup + n_dead_tup = 0 THEN 0
    ELSE round((100.0 * n_dead_tup / (n_live_tup + n_dead_tup))::numeric, 2)
  END AS dead_tuple_pct,
  pg_size_pretty(pg_total_relation_size(relid)) AS total_size,
  pg_total_relation_size(relid) AS total_size_bytes,
  last_autovacuum,
  last_vacuum,
  last_autoanalyze,
  last_analyze
FROM pg_stat_user_tables
ORDER BY
  CASE WHEN n_live_tup + n_dead_tup = 0 THEN 0
       ELSE 100.0 * n_dead_tup / (n_live_tup + n_dead_tup)
  END DESC,
  pg_total_relation_size(relid) DESC
LIMIT 100;

SELECT
  s.schemaname,
  s.relname AS table_name,
  s.indexrelname AS index_name,
  s.idx_scan,
  pg_relation_size(s.indexrelid) AS index_size_bytes,
  pg_total_relation_size(s.relid) AS table_total_size_bytes,
  CASE
    WHEN pg_total_relation_size(s.relid) = 0 THEN 0
    ELSE round((100.0 * pg_relation_size(s.indexrelid) / pg_total_relation_size(s.relid))::numeric, 2)
  END AS index_to_table_pct
FROM pg_stat_user_indexes s
ORDER BY pg_relation_size(s.indexrelid) DESC
LIMIT 100;
