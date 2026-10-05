\pset pager off
\timing on
SELECT installed_rank,version,description,type,script,checksum,installed_by,installed_on,execution_time,success
FROM flyway_schema_history ORDER BY installed_rank DESC LIMIT 100;
SELECT count(*) FILTER (WHERE success) AS successful_migrations,count(*) FILTER (WHERE NOT success) AS failed_migrations,max(installed_on) AS latest_install_time
FROM flyway_schema_history;
