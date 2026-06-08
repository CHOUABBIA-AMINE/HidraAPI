-- WF-013 — db(workflow): add workflow catalog and core tables migration
--
-- Purpose:
--   Create the first workflow database schema for controlled vocabularies and core
--   operational process orchestration data.
--
-- Scope:
--   - Catalog entries and translations for workflow business taxonomy values.
--   - Workflow definitions, steps, and transitions.
--   - Workflow instances over neutral target references.
--   - Workflow tasks, assignments, actions, delegations, escalation rules, comments, and state history.
--
-- Boundary rules:
--   - Business taxonomy values are stored as catalog rows and foreign keys.
--   - Technical lifecycle/state columns may use CHECK constraints.
--   - User-facing workflow labels are multilingual, using name_ar, name_fr, and name_en.
--   - Workflow references external module objects through neutral target and actor snapshot columns only.
--   - Workflow must not own telemetry readings, topology assets, identity users, organization units, audit storage,
--     planning, monitoring, incidents, analytics, reporting, or notification data.

CREATE TABLE hidra_workflow_type_catalog (
    id                  VARCHAR(80) PRIMARY KEY,
    catalog_name        VARCHAR(80) NOT NULL,
    code                VARCHAR(120) NOT NULL,
    active              BOOLEAN NOT NULL DEFAULT TRUE,
    sort_order          INTEGER NOT NULL DEFAULT 0,
    system_defined      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_hidra_workflow_type_catalog_name_code UNIQUE (catalog_name, code),
    CONSTRAINT ck_hidra_workflow_type_catalog_sort_order CHECK (sort_order >= 0)
);

CREATE TABLE hidra_workflow_type_translation (
    id                  VARCHAR(80) PRIMARY KEY,
    type_id             VARCHAR(80) NOT NULL,
    locale              VARCHAR(10) NOT NULL,
    name                VARCHAR(160) NOT NULL,
    description         VARCHAR(500),
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_workflow_type_translation_type
        FOREIGN KEY (type_id)
        REFERENCES hidra_workflow_type_catalog (id),
    CONSTRAINT uk_hidra_workflow_type_translation_type_locale UNIQUE (type_id, locale)
);

CREATE INDEX idx_hidra_workflow_type_catalog_catalog_name
    ON hidra_workflow_type_catalog (catalog_name);

CREATE INDEX idx_hidra_workflow_type_catalog_code
    ON hidra_workflow_type_catalog (code);

CREATE INDEX idx_hidra_workflow_type_translation_type
    ON hidra_workflow_type_translation (type_id);

CREATE TABLE hidra_workflow_definition (
    id                  VARCHAR(80) PRIMARY KEY,
    code                VARCHAR(120) NOT NULL,
    name_ar             VARCHAR(160),
    name_fr             VARCHAR(160) NOT NULL,
    name_en             VARCHAR(160),
    type_id             VARCHAR(80) NOT NULL,
    status              VARCHAR(40) NOT NULL,
    version             INTEGER NOT NULL DEFAULT 1,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_hidra_workflow_definition_code_version UNIQUE (code, version),
    CONSTRAINT fk_hidra_workflow_definition_type
        FOREIGN KEY (type_id)
        REFERENCES hidra_workflow_type_catalog (id),
    CONSTRAINT ck_hidra_workflow_definition_status
        CHECK (status IN ('DRAFT', 'ACTIVE', 'INACTIVE', 'RETIRED')),
    CONSTRAINT ck_hidra_workflow_definition_version
        CHECK (version >= 1)
);

CREATE TABLE hidra_workflow_step (
    id                  VARCHAR(80) PRIMARY KEY,
    definition_id       VARCHAR(80) NOT NULL,
    code                VARCHAR(120) NOT NULL,
    name_ar             VARCHAR(160),
    name_fr             VARCHAR(160) NOT NULL,
    name_en             VARCHAR(160),
    step_order          INTEGER NOT NULL,
    mandatory           BOOLEAN NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_workflow_step_definition
        FOREIGN KEY (definition_id)
        REFERENCES hidra_workflow_definition (id),
    CONSTRAINT uk_hidra_workflow_step_definition_code UNIQUE (definition_id, code),
    CONSTRAINT uk_hidra_workflow_step_definition_order UNIQUE (definition_id, step_order),
    CONSTRAINT ck_hidra_workflow_step_order
        CHECK (step_order >= 0)
);

CREATE TABLE hidra_workflow_transition (
    id                  VARCHAR(80) PRIMARY KEY,
    definition_id       VARCHAR(80) NOT NULL,
    from_step_id        VARCHAR(80) NOT NULL,
    to_step_id          VARCHAR(80) NOT NULL,
    decision            VARCHAR(40) NOT NULL,
    reason_required     BOOLEAN NOT NULL DEFAULT FALSE,
    comment_required    BOOLEAN NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_workflow_transition_definition
        FOREIGN KEY (definition_id)
        REFERENCES hidra_workflow_definition (id),
    CONSTRAINT fk_hidra_workflow_transition_from_step
        FOREIGN KEY (from_step_id)
        REFERENCES hidra_workflow_step (id),
    CONSTRAINT fk_hidra_workflow_transition_to_step
        FOREIGN KEY (to_step_id)
        REFERENCES hidra_workflow_step (id),
    CONSTRAINT uk_hidra_workflow_transition_definition_from_decision UNIQUE (definition_id, from_step_id, decision),
    CONSTRAINT ck_hidra_workflow_transition_decision
        CHECK (decision IN (
            'APPROVE',
            'REJECT',
            'REQUEST_CORRECTION',
            'CORRECT',
            'RETURN',
            'DELEGATE',
            'ESCALATE',
            'CANCEL',
            'COMMENT'
        )),
    CONSTRAINT ck_hidra_workflow_transition_distinct_steps
        CHECK (from_step_id <> to_step_id)
);

CREATE TABLE hidra_workflow_instance (
    id                          VARCHAR(80) PRIMARY KEY,
    definition_id               VARCHAR(80) NOT NULL,
    definition_version          INTEGER NOT NULL,
    target_module               VARCHAR(80) NOT NULL,
    target_type_id              VARCHAR(80) NOT NULL,
    target_id                   VARCHAR(120) NOT NULL,
    target_code_snapshot        VARCHAR(120),
    target_label_snapshot       VARCHAR(240),
    status                      VARCHAR(40) NOT NULL,
    current_step_id             VARCHAR(80),
    started_by_actor_id         VARCHAR(80) NOT NULL,
    started_by_username_snapshot VARCHAR(120),
    started_by_display_name_snapshot VARCHAR(160) NOT NULL,
    started_by_role_code_snapshot VARCHAR(80),
    started_at                  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    completed_at                TIMESTAMPTZ,
    cancelled_at                TIMESTAMPTZ,
    correlation_id              VARCHAR(120),
    created_at                  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_workflow_instance_definition
        FOREIGN KEY (definition_id)
        REFERENCES hidra_workflow_definition (id),
    CONSTRAINT fk_hidra_workflow_instance_target_type
        FOREIGN KEY (target_type_id)
        REFERENCES hidra_workflow_type_catalog (id),
    CONSTRAINT fk_hidra_workflow_instance_current_step
        FOREIGN KEY (current_step_id)
        REFERENCES hidra_workflow_step (id),
    CONSTRAINT uk_hidra_workflow_instance_open_target
        UNIQUE (target_module, target_type_id, target_id, status),
    CONSTRAINT ck_hidra_workflow_instance_definition_version
        CHECK (definition_version >= 1),
    CONSTRAINT ck_hidra_workflow_instance_status
        CHECK (status IN ('DRAFT', 'STARTED', 'IN_PROGRESS', 'WAITING', 'COMPLETED', 'CANCELLED', 'FAILED')),
    CONSTRAINT ck_hidra_workflow_instance_completion_time
        CHECK (completed_at IS NULL OR completed_at >= started_at),
    CONSTRAINT ck_hidra_workflow_instance_cancellation_time
        CHECK (cancelled_at IS NULL OR cancelled_at >= started_at)
);

CREATE TABLE hidra_workflow_task (
    id                              VARCHAR(80) PRIMARY KEY,
    instance_id                     VARCHAR(80) NOT NULL,
    step_id                         VARCHAR(80) NOT NULL,
    status                          VARCHAR(40) NOT NULL,
    assigned_actor_id               VARCHAR(80),
    assigned_actor_username_snapshot VARCHAR(120),
    assigned_actor_display_name_snapshot VARCHAR(160),
    assigned_organization_unit_id   VARCHAR(80),
    assigned_organization_unit_name_snapshot VARCHAR(160),
    assigned_role_code_snapshot     VARCHAR(80),
    priority_id                     VARCHAR(80),
    due_at                          TIMESTAMPTZ,
    claimed_by_actor_id             VARCHAR(80),
    claimed_by_username_snapshot    VARCHAR(120),
    claimed_by_display_name_snapshot VARCHAR(160),
    claimed_by_role_code_snapshot   VARCHAR(80),
    claimed_at                      TIMESTAMPTZ,
    completed_by_actor_id           VARCHAR(80),
    completed_by_username_snapshot  VARCHAR(120),
    completed_by_display_name_snapshot VARCHAR(160),
    completed_by_role_code_snapshot VARCHAR(80),
    completed_at                    TIMESTAMPTZ,
    created_at                      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at                      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_workflow_task_instance
        FOREIGN KEY (instance_id)
        REFERENCES hidra_workflow_instance (id),
    CONSTRAINT fk_hidra_workflow_task_step
        FOREIGN KEY (step_id)
        REFERENCES hidra_workflow_step (id),
    CONSTRAINT fk_hidra_workflow_task_priority
        FOREIGN KEY (priority_id)
        REFERENCES hidra_workflow_type_catalog (id),
    CONSTRAINT ck_hidra_workflow_task_status
        CHECK (status IN (
            'OPEN',
            'CLAIMED',
            'IN_REVIEW',
            'APPROVED',
            'REJECTED',
            'RETURNED',
            'DELEGATED',
            'ESCALATED',
            'CANCELLED',
            'EXPIRED'
        )),
    CONSTRAINT ck_hidra_workflow_task_assignment_target
        CHECK (assigned_actor_id IS NOT NULL OR assigned_organization_unit_id IS NOT NULL),
    CONSTRAINT ck_hidra_workflow_task_claim_pair
        CHECK (
            (claimed_by_actor_id IS NULL AND claimed_at IS NULL)
            OR (claimed_by_actor_id IS NOT NULL AND claimed_at IS NOT NULL)
        ),
    CONSTRAINT ck_hidra_workflow_task_completion_pair
        CHECK (
            (completed_by_actor_id IS NULL AND completed_at IS NULL)
            OR (completed_by_actor_id IS NOT NULL AND completed_at IS NOT NULL)
        ),
    CONSTRAINT ck_hidra_workflow_task_completed_after_created
        CHECK (completed_at IS NULL OR completed_at >= created_at)
);

CREATE TABLE hidra_workflow_assignment (
    id                              VARCHAR(80) PRIMARY KEY,
    task_id                         VARCHAR(80) NOT NULL,
    actor_id                        VARCHAR(80),
    actor_username_snapshot         VARCHAR(120),
    actor_display_name_snapshot     VARCHAR(160),
    role_code_snapshot              VARCHAR(80),
    organization_unit_id            VARCHAR(80),
    organization_unit_name_snapshot VARCHAR(160),
    status                          VARCHAR(40) NOT NULL,
    assigned_at                     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at                      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_workflow_assignment_task
        FOREIGN KEY (task_id)
        REFERENCES hidra_workflow_task (id),
    CONSTRAINT ck_hidra_workflow_assignment_target
        CHECK (actor_id IS NOT NULL OR organization_unit_id IS NOT NULL),
    CONSTRAINT ck_hidra_workflow_assignment_status
        CHECK (status IN ('ASSIGNED', 'CLAIMED', 'RELEASED', 'COMPLETED', 'DELEGATED', 'ESCALATED', 'CANCELLED')),
    CONSTRAINT ck_hidra_workflow_assignment_updated_time
        CHECK (updated_at >= assigned_at)
);

CREATE TABLE hidra_workflow_action (
    id                              VARCHAR(80) PRIMARY KEY,
    instance_id                     VARCHAR(80) NOT NULL,
    task_id                         VARCHAR(80),
    action_type                     VARCHAR(40) NOT NULL,
    decision                        VARCHAR(40),
    reason_id                       VARCHAR(80),
    decision_note                   VARCHAR(2000),
    comment_text                    VARCHAR(2000),
    actor_id                        VARCHAR(80) NOT NULL,
    actor_username_snapshot         VARCHAR(120),
    actor_display_name_snapshot     VARCHAR(160) NOT NULL,
    actor_role_code_snapshot        VARCHAR(80),
    organization_unit_id            VARCHAR(80),
    organization_unit_name_snapshot VARCHAR(160),
    organization_role_code_snapshot VARCHAR(80),
    correlation_id                  VARCHAR(120),
    acted_at                        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_workflow_action_instance
        FOREIGN KEY (instance_id)
        REFERENCES hidra_workflow_instance (id),
    CONSTRAINT fk_hidra_workflow_action_task
        FOREIGN KEY (task_id)
        REFERENCES hidra_workflow_task (id),
    CONSTRAINT fk_hidra_workflow_action_reason
        FOREIGN KEY (reason_id)
        REFERENCES hidra_workflow_type_catalog (id),
    CONSTRAINT ck_hidra_workflow_action_type
        CHECK (action_type IN (
            'START',
            'ASSIGN',
            'CLAIM',
            'APPROVE',
            'REJECT',
            'REQUEST_CORRECTION',
            'CORRECT',
            'RETURN',
            'DELEGATE',
            'ESCALATE',
            'CANCEL',
            'COMMENT',
            'COMPLETE'
        )),
    CONSTRAINT ck_hidra_workflow_action_decision
        CHECK (
            decision IS NULL
            OR decision IN (
                'APPROVE',
                'REJECT',
                'REQUEST_CORRECTION',
                'CORRECT',
                'RETURN',
                'DELEGATE',
                'ESCALATE',
                'CANCEL',
                'COMMENT'
            )
        ),
    CONSTRAINT ck_hidra_workflow_action_reason_required
        CHECK (
            decision IS NULL
            OR decision NOT IN ('REJECT', 'REQUEST_CORRECTION', 'RETURN', 'DELEGATE', 'ESCALATE', 'CANCEL')
            OR reason_id IS NOT NULL
        ),
    CONSTRAINT ck_hidra_workflow_action_comment_required
        CHECK (
            decision IS NULL
            OR decision <> 'REQUEST_CORRECTION'
            OR comment_text IS NOT NULL
        )
);

CREATE TABLE hidra_workflow_delegation (
    id                              VARCHAR(80) PRIMARY KEY,
    task_id                         VARCHAR(80) NOT NULL,
    from_actor_id                   VARCHAR(80) NOT NULL,
    from_actor_username_snapshot    VARCHAR(120),
    from_actor_display_name_snapshot VARCHAR(160) NOT NULL,
    from_actor_role_code_snapshot   VARCHAR(80),
    to_actor_id                     VARCHAR(80),
    to_actor_username_snapshot      VARCHAR(120),
    to_actor_display_name_snapshot  VARCHAR(160),
    to_actor_role_code_snapshot     VARCHAR(80),
    to_organization_unit_id         VARCHAR(80),
    to_organization_unit_name_snapshot VARCHAR(160),
    to_organization_role_code_snapshot VARCHAR(80),
    reason_id                       VARCHAR(80) NOT NULL,
    delegated_at                    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_workflow_delegation_task
        FOREIGN KEY (task_id)
        REFERENCES hidra_workflow_task (id),
    CONSTRAINT fk_hidra_workflow_delegation_reason
        FOREIGN KEY (reason_id)
        REFERENCES hidra_workflow_type_catalog (id),
    CONSTRAINT ck_hidra_workflow_delegation_target
        CHECK (to_actor_id IS NOT NULL OR to_organization_unit_id IS NOT NULL),
    CONSTRAINT ck_hidra_workflow_delegation_actor_distinct
        CHECK (to_actor_id IS NULL OR to_actor_id <> from_actor_id)
);

CREATE TABLE hidra_workflow_escalation_rule (
    id                              VARCHAR(80) PRIMARY KEY,
    definition_id                   VARCHAR(80) NOT NULL,
    step_id                         VARCHAR(80) NOT NULL,
    after_duration_seconds          INTEGER NOT NULL,
    escalate_to_actor_id            VARCHAR(80),
    escalate_to_actor_username_snapshot VARCHAR(120),
    escalate_to_actor_display_name_snapshot VARCHAR(160),
    escalate_to_actor_role_code_snapshot VARCHAR(80),
    escalate_to_organization_unit_id VARCHAR(80),
    escalate_to_organization_unit_name_snapshot VARCHAR(160),
    escalate_to_organization_role_code_snapshot VARCHAR(80),
    active                          BOOLEAN NOT NULL DEFAULT TRUE,
    created_at                      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at                      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_workflow_escalation_rule_definition
        FOREIGN KEY (definition_id)
        REFERENCES hidra_workflow_definition (id),
    CONSTRAINT fk_hidra_workflow_escalation_rule_step
        FOREIGN KEY (step_id)
        REFERENCES hidra_workflow_step (id),
    CONSTRAINT ck_hidra_workflow_escalation_rule_duration
        CHECK (after_duration_seconds > 0),
    CONSTRAINT ck_hidra_workflow_escalation_rule_target
        CHECK (escalate_to_actor_id IS NOT NULL OR escalate_to_organization_unit_id IS NOT NULL),
    CONSTRAINT ck_hidra_workflow_escalation_rule_updated_time
        CHECK (updated_at >= created_at)
);

CREATE TABLE hidra_workflow_comment (
    id                              VARCHAR(80) PRIMARY KEY,
    instance_id                     VARCHAR(80) NOT NULL,
    task_id                         VARCHAR(80),
    actor_id                        VARCHAR(80) NOT NULL,
    actor_username_snapshot         VARCHAR(120),
    actor_display_name_snapshot     VARCHAR(160) NOT NULL,
    actor_role_code_snapshot        VARCHAR(80),
    comment_text                    VARCHAR(2000) NOT NULL,
    commented_at                    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_workflow_comment_instance
        FOREIGN KEY (instance_id)
        REFERENCES hidra_workflow_instance (id),
    CONSTRAINT fk_hidra_workflow_comment_task
        FOREIGN KEY (task_id)
        REFERENCES hidra_workflow_task (id)
);

CREATE TABLE hidra_workflow_state_history (
    id                              VARCHAR(80) PRIMARY KEY,
    instance_id                     VARCHAR(80) NOT NULL,
    task_id                         VARCHAR(80),
    from_step_id                    VARCHAR(80),
    to_step_id                      VARCHAR(80),
    from_status                     VARCHAR(40),
    to_status                       VARCHAR(40) NOT NULL,
    actor_id                        VARCHAR(80) NOT NULL,
    actor_username_snapshot         VARCHAR(120),
    actor_display_name_snapshot     VARCHAR(160) NOT NULL,
    actor_role_code_snapshot        VARCHAR(80),
    changed_at                      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_workflow_state_history_instance
        FOREIGN KEY (instance_id)
        REFERENCES hidra_workflow_instance (id),
    CONSTRAINT fk_hidra_workflow_state_history_task
        FOREIGN KEY (task_id)
        REFERENCES hidra_workflow_task (id),
    CONSTRAINT fk_hidra_workflow_state_history_from_step
        FOREIGN KEY (from_step_id)
        REFERENCES hidra_workflow_step (id),
    CONSTRAINT fk_hidra_workflow_state_history_to_step
        FOREIGN KEY (to_step_id)
        REFERENCES hidra_workflow_step (id),
    CONSTRAINT ck_hidra_workflow_state_history_from_status
        CHECK (
            from_status IS NULL
            OR from_status IN ('DRAFT', 'STARTED', 'IN_PROGRESS', 'WAITING', 'COMPLETED', 'CANCELLED', 'FAILED')
        ),
    CONSTRAINT ck_hidra_workflow_state_history_to_status
        CHECK (to_status IN ('DRAFT', 'STARTED', 'IN_PROGRESS', 'WAITING', 'COMPLETED', 'CANCELLED', 'FAILED'))
);

CREATE INDEX idx_hidra_workflow_definition_type
    ON hidra_workflow_definition (type_id);

CREATE INDEX idx_hidra_workflow_definition_status
    ON hidra_workflow_definition (status);

CREATE INDEX idx_hidra_workflow_step_definition
    ON hidra_workflow_step (definition_id);

CREATE INDEX idx_hidra_workflow_transition_definition
    ON hidra_workflow_transition (definition_id);

CREATE INDEX idx_hidra_workflow_transition_from_step
    ON hidra_workflow_transition (from_step_id);

CREATE INDEX idx_hidra_workflow_instance_definition
    ON hidra_workflow_instance (definition_id);

CREATE INDEX idx_hidra_workflow_instance_target
    ON hidra_workflow_instance (target_module, target_type_id, target_id);

CREATE INDEX idx_hidra_workflow_instance_status
    ON hidra_workflow_instance (status);

CREATE INDEX idx_hidra_workflow_instance_current_step
    ON hidra_workflow_instance (current_step_id);

CREATE INDEX idx_hidra_workflow_instance_started_by
    ON hidra_workflow_instance (started_by_actor_id);

CREATE INDEX idx_hidra_workflow_task_instance
    ON hidra_workflow_task (instance_id);

CREATE INDEX idx_hidra_workflow_task_step
    ON hidra_workflow_task (step_id);

CREATE INDEX idx_hidra_workflow_task_status
    ON hidra_workflow_task (status);

CREATE INDEX idx_hidra_workflow_task_assigned_actor
    ON hidra_workflow_task (assigned_actor_id);

CREATE INDEX idx_hidra_workflow_task_assigned_org
    ON hidra_workflow_task (assigned_organization_unit_id);

CREATE INDEX idx_hidra_workflow_task_due_at
    ON hidra_workflow_task (due_at);

CREATE INDEX idx_hidra_workflow_assignment_task
    ON hidra_workflow_assignment (task_id);

CREATE INDEX idx_hidra_workflow_assignment_actor
    ON hidra_workflow_assignment (actor_id);

CREATE INDEX idx_hidra_workflow_assignment_org
    ON hidra_workflow_assignment (organization_unit_id);

CREATE INDEX idx_hidra_workflow_action_instance
    ON hidra_workflow_action (instance_id);

CREATE INDEX idx_hidra_workflow_action_task
    ON hidra_workflow_action (task_id);

CREATE INDEX idx_hidra_workflow_action_actor
    ON hidra_workflow_action (actor_id);

CREATE INDEX idx_hidra_workflow_action_type
    ON hidra_workflow_action (action_type);

CREATE INDEX idx_hidra_workflow_action_decision
    ON hidra_workflow_action (decision);

CREATE INDEX idx_hidra_workflow_action_reason
    ON hidra_workflow_action (reason_id);

CREATE INDEX idx_hidra_workflow_action_acted_at
    ON hidra_workflow_action (acted_at);

CREATE INDEX idx_hidra_workflow_delegation_task
    ON hidra_workflow_delegation (task_id);

CREATE INDEX idx_hidra_workflow_delegation_from_actor
    ON hidra_workflow_delegation (from_actor_id);

CREATE INDEX idx_hidra_workflow_escalation_rule_definition
    ON hidra_workflow_escalation_rule (definition_id);

CREATE INDEX idx_hidra_workflow_escalation_rule_step
    ON hidra_workflow_escalation_rule (step_id);

CREATE INDEX idx_hidra_workflow_escalation_rule_active
    ON hidra_workflow_escalation_rule (active);

CREATE INDEX idx_hidra_workflow_comment_instance
    ON hidra_workflow_comment (instance_id);

CREATE INDEX idx_hidra_workflow_comment_task
    ON hidra_workflow_comment (task_id);

CREATE INDEX idx_hidra_workflow_state_history_instance
    ON hidra_workflow_state_history (instance_id);

CREATE INDEX idx_hidra_workflow_state_history_task
    ON hidra_workflow_state_history (task_id);

INSERT INTO hidra_workflow_type_catalog (
    id,
    catalog_name,
    code,
    active,
    sort_order,
    system_defined
)
VALUES
    ('workflow-type-telemetry-validation', 'WORKFLOW_TYPE', 'TELEMETRY_VALIDATION', TRUE, 10, TRUE),
    ('workflow-target-type-telemetry-reading', 'TARGET_TYPE', 'TELEMETRY_READING', TRUE, 10, TRUE),
    ('workflow-priority-low', 'PRIORITY', 'LOW', TRUE, 10, TRUE),
    ('workflow-priority-normal', 'PRIORITY', 'NORMAL', TRUE, 20, TRUE),
    ('workflow-priority-high', 'PRIORITY', 'HIGH', TRUE, 30, TRUE),
    ('workflow-priority-critical', 'PRIORITY', 'CRITICAL', TRUE, 40, TRUE),
    ('workflow-reason-value-suspicious', 'DECISION_REASON', 'VALUE_SUSPICIOUS', TRUE, 10, TRUE),
    ('workflow-reason-out-of-range', 'DECISION_REASON', 'OUT_OF_RANGE', TRUE, 20, TRUE),
    ('workflow-reason-missing-context', 'DECISION_REASON', 'MISSING_CONTEXT', TRUE, 30, TRUE),
    ('workflow-reason-data-incomplete', 'DECISION_REASON', 'DATA_INCOMPLETE', TRUE, 40, TRUE),
    ('workflow-reason-duplicate-reading', 'DECISION_REASON', 'DUPLICATE_READING', TRUE, 50, TRUE),
    ('workflow-reason-operator-error', 'DECISION_REASON', 'OPERATOR_ERROR', TRUE, 60, TRUE),
    ('workflow-reason-supervisor-review', 'DECISION_REASON', 'SUPERVISOR_REVIEW_REQUIRED', TRUE, 70, TRUE),
    ('workflow-reason-workload-balance', 'DELEGATION_REASON', 'WORKLOAD_BALANCE', TRUE, 10, TRUE),
    ('workflow-reason-actor-unavailable', 'DELEGATION_REASON', 'ACTOR_UNAVAILABLE', TRUE, 20, TRUE),
    ('workflow-reason-timeout', 'ESCALATION_REASON', 'TIMEOUT', TRUE, 10, TRUE),
    ('workflow-reason-operational-escalation', 'ESCALATION_REASON', 'OPERATIONAL_ESCALATION', TRUE, 20, TRUE);

INSERT INTO hidra_workflow_type_translation (
    id,
    type_id,
    locale,
    name,
    description
)
VALUES
    ('workflow-type-telemetry-validation-ar', 'workflow-type-telemetry-validation', 'ar', 'تحقق القياسات', 'سير عمل للتحقق من قراءات القياس'),
    ('workflow-type-telemetry-validation-fr', 'workflow-type-telemetry-validation', 'fr', 'Validation télémétrie', 'Workflow de validation des lectures de télémétrie'),
    ('workflow-type-telemetry-validation-en', 'workflow-type-telemetry-validation', 'en', 'Telemetry validation', 'Workflow for validating telemetry readings'),

    ('workflow-target-type-telemetry-reading-ar', 'workflow-target-type-telemetry-reading', 'ar', 'قراءة القياس', 'هدف سير العمل الخاص بقراءة القياس'),
    ('workflow-target-type-telemetry-reading-fr', 'workflow-target-type-telemetry-reading', 'fr', 'Lecture télémétrie', 'Cible workflow représentant une lecture de télémétrie'),
    ('workflow-target-type-telemetry-reading-en', 'workflow-target-type-telemetry-reading', 'en', 'Telemetry reading', 'Workflow target representing a telemetry reading'),

    ('workflow-priority-low-ar', 'workflow-priority-low', 'ar', 'منخفضة', 'أولوية منخفضة'),
    ('workflow-priority-low-fr', 'workflow-priority-low', 'fr', 'Basse', 'Priorité basse'),
    ('workflow-priority-low-en', 'workflow-priority-low', 'en', 'Low', 'Low priority'),
    ('workflow-priority-normal-ar', 'workflow-priority-normal', 'ar', 'عادية', 'أولوية عادية'),
    ('workflow-priority-normal-fr', 'workflow-priority-normal', 'fr', 'Normale', 'Priorité normale'),
    ('workflow-priority-normal-en', 'workflow-priority-normal', 'en', 'Normal', 'Normal priority'),
    ('workflow-priority-high-ar', 'workflow-priority-high', 'ar', 'مرتفعة', 'أولوية مرتفعة'),
    ('workflow-priority-high-fr', 'workflow-priority-high', 'fr', 'Haute', 'Priorité haute'),
    ('workflow-priority-high-en', 'workflow-priority-high', 'en', 'High', 'High priority'),
    ('workflow-priority-critical-ar', 'workflow-priority-critical', 'ar', 'حرجة', 'أولوية حرجة'),
    ('workflow-priority-critical-fr', 'workflow-priority-critical', 'fr', 'Critique', 'Priorité critique'),
    ('workflow-priority-critical-en', 'workflow-priority-critical', 'en', 'Critical', 'Critical priority'),

    ('workflow-reason-value-suspicious-ar', 'workflow-reason-value-suspicious', 'ar', 'قيمة مشبوهة', 'القيمة تبدو غير موثوقة'),
    ('workflow-reason-value-suspicious-fr', 'workflow-reason-value-suspicious', 'fr', 'Valeur suspecte', 'La valeur semble non fiable'),
    ('workflow-reason-value-suspicious-en', 'workflow-reason-value-suspicious', 'en', 'Suspicious value', 'The value appears unreliable'),
    ('workflow-reason-out-of-range-ar', 'workflow-reason-out-of-range', 'ar', 'خارج النطاق', 'القيمة خارج النطاق المتوقع'),
    ('workflow-reason-out-of-range-fr', 'workflow-reason-out-of-range', 'fr', 'Hors plage', 'La valeur est hors de la plage attendue'),
    ('workflow-reason-out-of-range-en', 'workflow-reason-out-of-range', 'en', 'Out of range', 'The value is outside the expected range'),
    ('workflow-reason-missing-context-ar', 'workflow-reason-missing-context', 'ar', 'سياق مفقود', 'السياق التشغيلي غير مكتمل'),
    ('workflow-reason-missing-context-fr', 'workflow-reason-missing-context', 'fr', 'Contexte manquant', 'Le contexte opérationnel est incomplet'),
    ('workflow-reason-missing-context-en', 'workflow-reason-missing-context', 'en', 'Missing context', 'The operational context is incomplete'),
    ('workflow-reason-data-incomplete-ar', 'workflow-reason-data-incomplete', 'ar', 'بيانات غير مكتملة', 'البيانات المطلوبة غير مكتملة'),
    ('workflow-reason-data-incomplete-fr', 'workflow-reason-data-incomplete', 'fr', 'Données incomplètes', 'Les données requises sont incomplètes'),
    ('workflow-reason-data-incomplete-en', 'workflow-reason-data-incomplete', 'en', 'Incomplete data', 'Required data is incomplete'),
    ('workflow-reason-duplicate-reading-ar', 'workflow-reason-duplicate-reading', 'ar', 'قراءة مكررة', 'القراءة تبدو مكررة'),
    ('workflow-reason-duplicate-reading-fr', 'workflow-reason-duplicate-reading', 'fr', 'Lecture dupliquée', 'La lecture semble dupliquée'),
    ('workflow-reason-duplicate-reading-en', 'workflow-reason-duplicate-reading', 'en', 'Duplicate reading', 'The reading appears duplicated'),
    ('workflow-reason-operator-error-ar', 'workflow-reason-operator-error', 'ar', 'خطأ المشغل', 'تصحيح مطلوب بسبب خطأ إدخال أو تشغيل'),
    ('workflow-reason-operator-error-fr', 'workflow-reason-operator-error', 'fr', 'Erreur opérateur', 'Correction requise suite à une erreur opérateur ou de saisie'),
    ('workflow-reason-operator-error-en', 'workflow-reason-operator-error', 'en', 'Operator error', 'Correction required because of operator or entry error'),
    ('workflow-reason-supervisor-review-ar', 'workflow-reason-supervisor-review', 'ar', 'مراجعة المشرف', 'مراجعة المشرف مطلوبة'),
    ('workflow-reason-supervisor-review-fr', 'workflow-reason-supervisor-review', 'fr', 'Revue superviseur', 'Une revue superviseur est requise'),
    ('workflow-reason-supervisor-review-en', 'workflow-reason-supervisor-review', 'en', 'Supervisor review', 'Supervisor review is required'),

    ('workflow-reason-workload-balance-ar', 'workflow-reason-workload-balance', 'ar', 'توازن عبء العمل', 'تفويض بسبب عبء العمل'),
    ('workflow-reason-workload-balance-fr', 'workflow-reason-workload-balance', 'fr', 'Équilibrage de charge', 'Délégation liée à la charge de travail'),
    ('workflow-reason-workload-balance-en', 'workflow-reason-workload-balance', 'en', 'Workload balance', 'Delegation due to workload balance'),
    ('workflow-reason-actor-unavailable-ar', 'workflow-reason-actor-unavailable', 'ar', 'الفاعل غير متاح', 'تفويض بسبب عدم توفر الفاعل'),
    ('workflow-reason-actor-unavailable-fr', 'workflow-reason-actor-unavailable', 'fr', 'Acteur indisponible', 'Délégation liée à l’indisponibilité de l’acteur'),
    ('workflow-reason-actor-unavailable-en', 'workflow-reason-actor-unavailable', 'en', 'Actor unavailable', 'Delegation because the actor is unavailable'),

    ('workflow-reason-timeout-ar', 'workflow-reason-timeout', 'ar', 'انتهاء المهلة', 'تصعيد بسبب تجاوز المهلة'),
    ('workflow-reason-timeout-fr', 'workflow-reason-timeout', 'fr', 'Délai dépassé', 'Escalade suite au dépassement du délai'),
    ('workflow-reason-timeout-en', 'workflow-reason-timeout', 'en', 'Timeout', 'Escalation because the expected delay was exceeded'),
    ('workflow-reason-operational-escalation-ar', 'workflow-reason-operational-escalation', 'ar', 'تصعيد تشغيلي', 'تصعيد لأسباب تشغيلية'),
    ('workflow-reason-operational-escalation-fr', 'workflow-reason-operational-escalation', 'fr', 'Escalade opérationnelle', 'Escalade pour raison opérationnelle'),
    ('workflow-reason-operational-escalation-en', 'workflow-reason-operational-escalation', 'en', 'Operational escalation', 'Escalation for operational reasons');
