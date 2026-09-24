-- ORG-037 | Read-only PostgreSQL legacy operational-scope assessment.
-- Run with a read-only DB role against an APPROVED COPY, not production without approval.
-- Requires the three tables created by V20260611_002__create_organization_tables.sql.
-- Every statement is SELECT; no row-level identifier is printed. Outputs counts only.
-- This script cannot verify external target existence, current code/name or owner lifecycle.

-- 1) Per source table: empty/partial tuples, GLOBAL with target and unknown scope types.
WITH legacy AS (
    SELECT 'hidra_org_unit'::text AS source_table,
           operational_scope_type AS scope_type, operational_scope_id AS scope_id,
           operational_scope_code AS scope_code, operational_scope_name AS scope_name
      FROM hidra_org_unit
    UNION ALL
    SELECT 'hidra_org_employee_assignment', operational_scope_type,
           operational_scope_id, operational_scope_code, operational_scope_name
      FROM hidra_org_employee_assignment
    UNION ALL
    SELECT 'hidra_org_responsibility_assignment', operational_scope_type,
           operational_scope_id, operational_scope_code, operational_scope_name
      FROM hidra_org_responsibility_assignment
), normalized AS (
    SELECT source_table, NULLIF(BTRIM(scope_type), '') AS t,
           NULLIF(BTRIM(scope_id), '') AS target,
           NULLIF(BTRIM(scope_code), '') AS code,
           NULLIF(BTRIM(scope_name), '') AS name
      FROM legacy
)
SELECT source_table,
       COUNT(*) AS total_rows,
       COUNT(*) FILTER (WHERE t IS NULL AND target IS NULL AND code IS NULL AND name IS NULL) AS empty_tuple,
       COUNT(*) FILTER (WHERE (t IS NULL) <> (target IS NULL)) AS partial_type_target,
       COUNT(*) FILTER (WHERE t IS NULL AND target IS NULL AND (code IS NOT NULL OR name IS NOT NULL)) AS labels_without_identity,
       COUNT(*) FILTER (WHERE t = 'GLOBAL' AND (target IS NOT NULL OR code IS NOT NULL OR name IS NOT NULL)) AS global_with_target_or_labels,
       COUNT(*) FILTER (WHERE t IS NOT NULL AND t NOT IN
          ('GLOBAL','ORGANIZATION_UNIT','PIPELINE_SYSTEM','PIPELINE','FACILITY','EQUIPMENT','CUSTOM')) AS unknown_type,
       COUNT(*) FILTER (WHERE t = 'CUSTOM') AS custom_requires_owner_registration,
       COUNT(*) FILTER (WHERE t NOT IN ('GLOBAL','CUSTOM') AND t IS NOT NULL AND target IS NOT NULL) AS candidate_entity_targets
  FROM normalized
 GROUP BY source_table
 ORDER BY source_table;

-- 2) Registry identity collisions: repeat typed targets in legacy rows are NOT
-- automatically erroneous (they may be many units assigned to the same scope).
-- Counts the distinct normalized typed target keys per source without disclosing keys.
WITH legacy AS (
    SELECT 'hidra_org_unit'::text AS source_table, operational_scope_type AS t, operational_scope_id AS target FROM hidra_org_unit
    UNION ALL
    SELECT 'hidra_org_employee_assignment', operational_scope_type, operational_scope_id FROM hidra_org_employee_assignment
    UNION ALL
    SELECT 'hidra_org_responsibility_assignment', operational_scope_type, operational_scope_id FROM hidra_org_responsibility_assignment
), keys AS (
    SELECT source_table, NULLIF(BTRIM(t),'') AS t, NULLIF(BTRIM(target),'') AS target FROM legacy
)
SELECT source_table,
       COUNT(*) FILTER (WHERE t IS NOT NULL AND target IS NOT NULL) AS rows_with_typed_target,
       COUNT(DISTINCT (t, target)) FILTER (WHERE t IS NOT NULL AND target IS NOT NULL) AS distinct_typed_targets
  FROM keys
 GROUP BY source_table ORDER BY source_table;

-- 3) Candidate exact-repeat responsibility assignments with overlapping ACTIVE
-- half-open windows [valid_from,valid_to). This is a count of PAIRS, not rows.
-- A NULL valid_to means unbounded. Review assignee/role/status policy before deciding.
SELECT COUNT(*) AS overlapping_active_responsibility_pairs
  FROM hidra_org_responsibility_assignment a
  JOIN hidra_org_responsibility_assignment b
    ON a.id < b.id
   AND a.assignee_type IS NOT DISTINCT FROM b.assignee_type
   AND a.assignee_id IS NOT DISTINCT FROM b.assignee_id
   AND a.responsibility_type IS NOT DISTINCT FROM b.responsibility_type
   AND NULLIF(BTRIM(a.operational_scope_type),'') IS NOT DISTINCT FROM NULLIF(BTRIM(b.operational_scope_type),'')
   AND NULLIF(BTRIM(a.operational_scope_id),'') IS NOT DISTINCT FROM NULLIF(BTRIM(b.operational_scope_id),'')
 WHERE a.status = 'ACTIVE' AND b.status = 'ACTIVE'
   AND a.valid_from < COALESCE(b.valid_to, 'infinity'::timestamptz)
   AND b.valid_from < COALESCE(a.valid_to, 'infinity'::timestamptz);

-- 4) Invalid or incomplete validity windows in responsibility history.
SELECT COUNT(*) AS total_responsibilities,
       COUNT(*) FILTER (WHERE valid_from IS NULL) AS missing_start,
       COUNT(*) FILTER (WHERE valid_to IS NOT NULL AND valid_to <= valid_from) AS invalid_end,
       COUNT(*) FILTER (WHERE NULLIF(BTRIM(assignee_type),'') IS NULL OR NULLIF(BTRIM(assignee_id),'') IS NULL) AS missing_assignee,
       COUNT(*) FILTER (WHERE NULLIF(BTRIM(operational_scope_type),'') IS NULL OR NULLIF(BTRIM(operational_scope_id),'') IS NULL) AS missing_type_or_target
  FROM hidra_org_responsibility_assignment;

-- 5) Per-table scope type distribution, without disclosing operational IDs.
WITH legacy AS (
    SELECT 'unit'::text AS source_table, operational_scope_type AS scope_type FROM hidra_org_unit
    UNION ALL
    SELECT 'employee_assignment', operational_scope_type FROM hidra_org_employee_assignment
    UNION ALL
    SELECT 'responsibility_assignment', operational_scope_type FROM hidra_org_responsibility_assignment
)
SELECT source_table, COALESCE(NULLIF(BTRIM(scope_type),''), '<empty>') AS scope_type,
       COUNT(*) AS row_count
  FROM legacy
 GROUP BY source_table, COALESCE(NULLIF(BTRIM(scope_type),''), '<empty>')
 ORDER BY source_table, scope_type;
