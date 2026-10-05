\pset pager off
\timing on
SELECT current_database() AS database_name,pg_size_pretty(pg_database_size(current_database())) AS database_size,pg_database_size(current_database()) AS database_size_bytes;
SELECT nspname AS schema_name,pg_size_pretty(sum(pg_total_relation_size(c.oid))) AS total_size
FROM pg_class c JOIN pg_namespace n ON n.oid=c.relnamespace
WHERE c.relkind IN ('r','m') AND n.nspname NOT IN ('pg_catalog','information_schema')
GROUP BY nspname ORDER BY sum(pg_total_relation_size(c.oid)) DESC;
SELECT schemaname,relname,pg_size_pretty(pg_total_relation_size(relid)) AS total_size,pg_total_relation_size(relid) AS total_size_bytes
FROM pg_stat_user_tables
ORDER BY pg_total_relation_size(relid) DESC LIMIT 50;
