-- HidraAPI Organization module controlled vocabulary correction.
-- COR-016 — refactor(organization): convert organization unit type to catalog entity.
-- Adds multilingual organization unit type catalog tables, backfills hidra_org_unit.unit_type_id,
-- and removes the old enum-style unit_type varchar column.

CREATE TABLE IF NOT EXISTS hidra_org_unit_type (
    id VARCHAR(80) PRIMARY KEY,
    code VARCHAR(80) NOT NULL,
    status VARCHAR(40) NOT NULL,
    sort_order INTEGER NOT NULL,
    system_defined BOOLEAN NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_hidra_org_unit_type_code UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS hidra_org_unit_type_translation (
    id VARCHAR(80) PRIMARY KEY,
    unit_type_id VARCHAR(80) NOT NULL,
    locale VARCHAR(10) NOT NULL,
    name VARCHAR(160) NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_hidra_org_unit_type_translation_type
        FOREIGN KEY (unit_type_id)
        REFERENCES hidra_org_unit_type (id),
    CONSTRAINT uk_hidra_org_unit_type_translation_locale UNIQUE (unit_type_id, locale)
);

INSERT INTO hidra_org_unit_type (id, code, status, sort_order, system_defined, created_at, updated_at) VALUES
('organization-out-company', 'COMPANY', 'ACTIVE', 10, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-division', 'DIVISION', 'ACTIVE', 20, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-direction', 'DIRECTION', 'ACTIVE', 30, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-department', 'DEPARTMENT', 'ACTIVE', 40, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-region', 'REGION', 'ACTIVE', 50, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-area', 'AREA', 'ACTIVE', 60, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-district', 'DISTRICT', 'ACTIVE', 70, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-station', 'STATION', 'ACTIVE', 80, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-team', 'TEAM', 'ACTIVE', 90, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-project-team', 'PROJECT_TEAM', 'ACTIVE', 100, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-other', 'OTHER', 'ACTIVE', 110, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (code) DO NOTHING;

INSERT INTO hidra_org_unit_type_translation (id, unit_type_id, locale, name, description, created_at, updated_at) VALUES
('organization-out-company-en', 'organization-out-company', 'en', 'Company', 'Company-level organization unit.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-company-fr', 'organization-out-company', 'fr', 'Société', 'Unité organisationnelle de niveau société.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-company-ar', 'organization-out-company', 'ar', 'شركة', 'وحدة تنظيمية على مستوى الشركة.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-division-en', 'organization-out-division', 'en', 'Division', 'Division-level organization unit.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-division-fr', 'organization-out-division', 'fr', 'Division', 'Unité organisationnelle de niveau division.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-division-ar', 'organization-out-division', 'ar', 'قسم رئيسي', 'وحدة تنظيمية على مستوى القسم الرئيسي.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-direction-en', 'organization-out-direction', 'en', 'Direction', 'Direction-level organization unit.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-direction-fr', 'organization-out-direction', 'fr', 'Direction', 'Unité organisationnelle de niveau direction.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-direction-ar', 'organization-out-direction', 'ar', 'مديرية', 'وحدة تنظيمية على مستوى المديرية.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-department-en', 'organization-out-department', 'en', 'Department', 'Department-level organization unit.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-department-fr', 'organization-out-department', 'fr', 'Département', 'Unité organisationnelle de niveau département.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-department-ar', 'organization-out-department', 'ar', 'قسم', 'وحدة تنظيمية على مستوى القسم.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-region-en', 'organization-out-region', 'en', 'Region', 'Operational region organization unit.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-region-fr', 'organization-out-region', 'fr', 'Région', 'Unité organisationnelle de région opérationnelle.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-region-ar', 'organization-out-region', 'ar', 'منطقة', 'وحدة تنظيمية لمنطقة تشغيلية.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-area-en', 'organization-out-area', 'en', 'Area', 'Operational area organization unit.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-area-fr', 'organization-out-area', 'fr', 'Zone', 'Unité organisationnelle de zone opérationnelle.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-area-ar', 'organization-out-area', 'ar', 'ناحية', 'وحدة تنظيمية لناحية تشغيلية.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-district-en', 'organization-out-district', 'en', 'District', 'Operational district organization unit.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-district-fr', 'organization-out-district', 'fr', 'District', 'Unité organisationnelle de district opérationnel.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-district-ar', 'organization-out-district', 'ar', 'مقاطعة', 'وحدة تنظيمية لمقاطعة تشغيلية.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-station-en', 'organization-out-station', 'en', 'Station', 'Station as an organization unit for people and responsibility.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-station-fr', 'organization-out-station', 'fr', 'Station', 'Station comme unité organisationnelle pour les personnes et responsabilités.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-station-ar', 'organization-out-station', 'ar', 'محطة', 'محطة كوحدة تنظيمية للأفراد والمسؤوليات.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-team-en', 'organization-out-team', 'en', 'Team', 'Team-level organization unit.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-team-fr', 'organization-out-team', 'fr', 'Équipe', 'Unité organisationnelle de niveau équipe.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-team-ar', 'organization-out-team', 'ar', 'فريق', 'وحدة تنظيمية على مستوى الفريق.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-project-team-en', 'organization-out-project-team', 'en', 'Project Team', 'Temporary project team organization unit.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-project-team-fr', 'organization-out-project-team', 'fr', 'Équipe projet', 'Unité organisationnelle temporaire de projet.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-project-team-ar', 'organization-out-project-team', 'ar', 'فريق مشروع', 'وحدة تنظيمية مؤقتة لفريق مشروع.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-other-en', 'organization-out-other', 'en', 'Other', 'Other organization unit type.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-other-fr', 'organization-out-other', 'fr', 'Autre', 'Autre type d’unité organisationnelle.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('organization-out-other-ar', 'organization-out-other', 'ar', 'أخرى', 'نوع آخر من الوحدات التنظيمية.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (unit_type_id, locale) DO NOTHING;

ALTER TABLE hidra_org_unit
    ADD COLUMN IF NOT EXISTS unit_type_id VARCHAR(80);

UPDATE hidra_org_unit unit
SET unit_type_id = unit_type.id
FROM hidra_org_unit_type unit_type
WHERE unit.unit_type_id IS NULL
  AND unit.unit_type = unit_type.code;

ALTER TABLE hidra_org_unit
    ALTER COLUMN unit_type_id SET NOT NULL;

ALTER TABLE hidra_org_unit
    ADD CONSTRAINT fk_hidra_org_unit_type
        FOREIGN KEY (unit_type_id)
        REFERENCES hidra_org_unit_type (id);

DROP INDEX IF EXISTS idx_hidra_org_unit_type;

CREATE INDEX IF NOT EXISTS idx_hidra_org_unit_type_id
    ON hidra_org_unit (unit_type_id);

ALTER TABLE hidra_org_unit
    DROP COLUMN IF EXISTS unit_type;

CREATE INDEX IF NOT EXISTS idx_hidra_org_unit_type_status
    ON hidra_org_unit_type (status);

CREATE INDEX IF NOT EXISTS idx_hidra_org_unit_type_translation_locale
    ON hidra_org_unit_type_translation (locale);
