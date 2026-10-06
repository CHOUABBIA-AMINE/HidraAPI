\pset pager off
\timing on

SELECT
  n.nspname AS schema_name,
  t.relname AS table_name,
  i.relname AS index_name,
  ix.indisvalid AS is_valid,
  ix.indisready AS is_ready,
  ix.indislive AS is_live,
  ix.indisunique AS is_unique,
  ix.indisprimary AS is_primary,
  pg_size_pretty(pg_relation_size(i.oid)) AS index_size,
  pg_relation_size(i.oid) AS index_size_bytes
FROM pg_index ix
JOIN pg_class i ON i.oid = ix.indexrelid
JOIN pg_class t ON t.oid = ix.indrelid
JOIN pg_namespace n ON n.oid = t.relnamespace
WHERE n.nspname NOT IN ('pg_catalog', 'information_schema')
ORDER BY
  ix.indisvalid ASC,
  ix.indisready ASC,
  pg_relation_size(i.oid) DESC,
  n.nspname,
  t.relname,
  i.relname;

SELECT
  n.nspname AS schema_name,
  t.relname AS table_name,
  i.relname AS index_name,
  ix.indisvalid AS is_valid,
  ix.indisready AS is_ready,
  ix.indislive AS is_live
FROM pg_index ix
JOIN pg_class i ON i.oid = ix.indexrelid
JOIN pg_class t ON t.oid = ix.indrelid
JOIN pg_namespace n ON n.oid = t.relnamespace
WHERE n.nspname NOT IN ('pg_catalog', 'information_schema')
  AND (NOT ix.indisvalid OR NOT ix.indisready OR NOT ix.indislive)
ORDER BY n.nspname, t.relname, i.relname;
