\pset pager off
\timing on
SELECT schemaname,relname AS table_name,indexrelname AS index_name,idx_scan,pg_size_pretty(pg_relation_size(indexrelid)) AS index_size,pg_relation_size(indexrelid) AS index_size_bytes
FROM pg_stat_user_indexes
ORDER BY pg_relation_size(indexrelid) DESC
LIMIT 100;
SELECT schemaname,relname AS table_name,indexrelname AS index_name,idx_scan,pg_size_pretty(pg_relation_size(indexrelid)) AS index_size
FROM pg_stat_user_indexes
WHERE idx_scan=0 AND pg_relation_size(indexrelid)>=16*1024*1024
ORDER BY pg_relation_size(indexrelid) DESC;
