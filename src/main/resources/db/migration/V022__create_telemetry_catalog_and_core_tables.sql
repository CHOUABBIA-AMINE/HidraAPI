-- TEL-013 — db(telemetry): add telemetry catalog and core tables migration
--
-- Purpose:
--   Create the first telemetry database schema for controlled vocabularies and core
--   industrial acquisition data.
--
-- Scope:
--   - Catalog entries and translations for telemetry business taxonomy values.
--   - Telemetry acquisition sources.
--   - Telemetry devices.
--   - Telemetry points.
--   - Telemetry point to topology asset bindings.
--   - Telemetry ingestion batches.
--   - Telemetry raw readings.
--
-- Boundary rules:
--   - Business taxonomy values are stored as catalog rows and foreign keys.
--   - Technical lifecycle/state columns may use CHECK constraints.
--   - User-facing telemetry labels are multilingual, using name_ar, name_fr, and name_en.
--   - Telemetry references topology assets through neutral snapshot columns only.

CREATE TABLE hidra_telemetry_type_catalog (
    id                  VARCHAR(80) PRIMARY KEY,
    catalog_name        VARCHAR(80) NOT NULL,
    code                VARCHAR(120) NOT NULL,
    active              BOOLEAN NOT NULL DEFAULT TRUE,
    sort_order          INTEGER NOT NULL DEFAULT 0,
    system_defined      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_hidra_telemetry_type_catalog_name_code UNIQUE (catalog_name, code),
    CONSTRAINT ck_hidra_telemetry_type_catalog_sort_order CHECK (sort_order >= 0)
);

CREATE TABLE hidra_telemetry_type_translation (
    id                  VARCHAR(80) PRIMARY KEY,
    type_id             VARCHAR(80) NOT NULL,
    locale              VARCHAR(10) NOT NULL,
    name                VARCHAR(160) NOT NULL,
    description         VARCHAR(500),
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_telemetry_type_translation_type
        FOREIGN KEY (type_id)
        REFERENCES hidra_telemetry_type_catalog (id),
    CONSTRAINT uk_hidra_telemetry_type_translation_type_locale UNIQUE (type_id, locale)
);

CREATE INDEX idx_hidra_telemetry_type_catalog_catalog_name
    ON hidra_telemetry_type_catalog (catalog_name);

CREATE INDEX idx_hidra_telemetry_type_catalog_code
    ON hidra_telemetry_type_catalog (code);

CREATE INDEX idx_hidra_telemetry_type_translation_type
    ON hidra_telemetry_type_translation (type_id);

CREATE TABLE hidra_telemetry_source (
    id                  VARCHAR(80) PRIMARY KEY,
    code                VARCHAR(120) NOT NULL,
    name_ar             VARCHAR(160),
    name_fr             VARCHAR(160) NOT NULL,
    name_en             VARCHAR(160),
    source_type_id      VARCHAR(80) NOT NULL,
    protocol_id         VARCHAR(80) NOT NULL,
    endpoint_uri        VARCHAR(500),
    external_reference  VARCHAR(200),
    status              VARCHAR(40) NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_hidra_telemetry_source_code UNIQUE (code),
    CONSTRAINT fk_hidra_telemetry_source_type
        FOREIGN KEY (source_type_id)
        REFERENCES hidra_telemetry_type_catalog (id),
    CONSTRAINT fk_hidra_telemetry_source_protocol
        FOREIGN KEY (protocol_id)
        REFERENCES hidra_telemetry_type_catalog (id),
    CONSTRAINT ck_hidra_telemetry_source_status
        CHECK (status IN ('PLANNED', 'ACTIVE', 'INACTIVE', 'RETIRED'))
);

CREATE TABLE hidra_telemetry_device (
    id                  VARCHAR(80) PRIMARY KEY,
    source_id           VARCHAR(80) NOT NULL,
    code                VARCHAR(120) NOT NULL,
    name_ar             VARCHAR(160),
    name_fr             VARCHAR(160) NOT NULL,
    name_en             VARCHAR(160),
    device_type_id      VARCHAR(80) NOT NULL,
    external_reference  VARCHAR(200),
    status              VARCHAR(40) NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_hidra_telemetry_device_code UNIQUE (code),
    CONSTRAINT fk_hidra_telemetry_device_source
        FOREIGN KEY (source_id)
        REFERENCES hidra_telemetry_source (id),
    CONSTRAINT fk_hidra_telemetry_device_type
        FOREIGN KEY (device_type_id)
        REFERENCES hidra_telemetry_type_catalog (id),
    CONSTRAINT ck_hidra_telemetry_device_status
        CHECK (status IN ('PLANNED', 'ACTIVE', 'INACTIVE', 'RETIRED'))
);

CREATE TABLE hidra_telemetry_point (
    id                              VARCHAR(80) PRIMARY KEY,
    device_id                       VARCHAR(80) NOT NULL,
    code                            VARCHAR(120) NOT NULL,
    name_ar                         VARCHAR(160),
    name_fr                         VARCHAR(160) NOT NULL,
    name_en                         VARCHAR(160),
    point_type_id                   VARCHAR(80) NOT NULL,
    signal_type_id                  VARCHAR(80) NOT NULL,
    unit_id                         VARCHAR(80),
    default_aggregation_method_id   VARCHAR(80),
    sampling_period_seconds         INTEGER,
    external_reference              VARCHAR(200),
    status                          VARCHAR(40) NOT NULL,
    created_at                      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at                      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_hidra_telemetry_point_code UNIQUE (code),
    CONSTRAINT fk_hidra_telemetry_point_device
        FOREIGN KEY (device_id)
        REFERENCES hidra_telemetry_device (id),
    CONSTRAINT fk_hidra_telemetry_point_type
        FOREIGN KEY (point_type_id)
        REFERENCES hidra_telemetry_type_catalog (id),
    CONSTRAINT fk_hidra_telemetry_point_signal_type
        FOREIGN KEY (signal_type_id)
        REFERENCES hidra_telemetry_type_catalog (id),
    CONSTRAINT fk_hidra_telemetry_point_unit
        FOREIGN KEY (unit_id)
        REFERENCES hidra_telemetry_type_catalog (id),
    CONSTRAINT fk_hidra_telemetry_point_aggregation_method
        FOREIGN KEY (default_aggregation_method_id)
        REFERENCES hidra_telemetry_type_catalog (id),
    CONSTRAINT ck_hidra_telemetry_point_status
        CHECK (status IN ('PLANNED', 'ACTIVE', 'INACTIVE', 'SUSPENDED', 'RETIRED')),
    CONSTRAINT ck_hidra_telemetry_point_sampling_period
        CHECK (sampling_period_seconds IS NULL OR sampling_period_seconds BETWEEN 1 AND 86400)
);

CREATE TABLE hidra_telemetry_point_binding (
    id                          VARCHAR(80) PRIMARY KEY,
    point_id                    VARCHAR(80) NOT NULL,
    topology_asset_type_code    VARCHAR(120) NOT NULL,
    topology_asset_id           VARCHAR(120) NOT NULL,
    topology_asset_code         VARCHAR(120) NOT NULL,
    topology_asset_name_snapshot VARCHAR(160),
    binding_role_id             VARCHAR(80) NOT NULL,
    active                      BOOLEAN NOT NULL DEFAULT TRUE,
    valid_from                  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    valid_to                    TIMESTAMPTZ,
    created_at                  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_hidra_telemetry_point_binding_point
        FOREIGN KEY (point_id)
        REFERENCES hidra_telemetry_point (id),
    CONSTRAINT fk_hidra_telemetry_point_binding_role
        FOREIGN KEY (binding_role_id)
        REFERENCES hidra_telemetry_type_catalog (id),
    CONSTRAINT ck_hidra_telemetry_point_binding_validity
        CHECK (valid_to IS NULL OR valid_to > valid_from),
    CONSTRAINT ck_hidra_telemetry_point_binding_active_window
        CHECK ((active = TRUE AND valid_to IS NULL) OR active = FALSE)
);

CREATE TABLE hidra_telemetry_ingestion_batch (
    id                  VARCHAR(80) PRIMARY KEY,
    source_id           VARCHAR(80) NOT NULL,
    correlation_id      VARCHAR(120),
    status              VARCHAR(40) NOT NULL,
    received_count      INTEGER NOT NULL DEFAULT 0,
    accepted_count      INTEGER NOT NULL DEFAULT 0,
    rejected_count      INTEGER NOT NULL DEFAULT 0,
    duplicate_count     INTEGER NOT NULL DEFAULT 0,
    quarantined_count   INTEGER NOT NULL DEFAULT 0,
    started_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    completed_at        TIMESTAMPTZ,
    failure_reason      VARCHAR(500),
    CONSTRAINT fk_hidra_telemetry_ingestion_batch_source
        FOREIGN KEY (source_id)
        REFERENCES hidra_telemetry_source (id),
    CONSTRAINT ck_hidra_telemetry_ingestion_batch_status
        CHECK (status IN ('RECEIVED', 'PROCESSING', 'COMPLETED', 'COMPLETED_WITH_ERRORS', 'FAILED')),
    CONSTRAINT ck_hidra_telemetry_ingestion_batch_counts
        CHECK (
            received_count >= 0
            AND accepted_count >= 0
            AND rejected_count >= 0
            AND duplicate_count >= 0
            AND quarantined_count >= 0
            AND accepted_count + rejected_count + duplicate_count + quarantined_count <= received_count
        ),
    CONSTRAINT ck_hidra_telemetry_ingestion_batch_time
        CHECK (completed_at IS NULL OR completed_at >= started_at),
    CONSTRAINT ck_hidra_telemetry_ingestion_batch_failure_reason
        CHECK ((status = 'FAILED' AND failure_reason IS NOT NULL) OR status <> 'FAILED')
);

CREATE TABLE hidra_telemetry_reading (
    id                  VARCHAR(80) PRIMARY KEY,
    point_id            VARCHAR(80) NOT NULL,
    numeric_value       NUMERIC(38, 12),
    text_value          VARCHAR(500),
    boolean_value       BOOLEAN,
    quality_code_id     VARCHAR(80) NOT NULL,
    source_timestamp    TIMESTAMPTZ NOT NULL,
    received_at         TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    state               VARCHAR(40) NOT NULL,
    ingestion_batch_id  VARCHAR(80),
    correlation_id      VARCHAR(120),
    rejection_reason    VARCHAR(500),
    CONSTRAINT fk_hidra_telemetry_reading_point
        FOREIGN KEY (point_id)
        REFERENCES hidra_telemetry_point (id),
    CONSTRAINT fk_hidra_telemetry_reading_quality
        FOREIGN KEY (quality_code_id)
        REFERENCES hidra_telemetry_type_catalog (id),
    CONSTRAINT fk_hidra_telemetry_reading_batch
        FOREIGN KEY (ingestion_batch_id)
        REFERENCES hidra_telemetry_ingestion_batch (id),
    CONSTRAINT ck_hidra_telemetry_reading_state
        CHECK (state IN ('RECEIVED', 'ACCEPTED', 'REJECTED', 'DUPLICATE', 'QUARANTINED')),
    CONSTRAINT ck_hidra_telemetry_reading_exactly_one_value
        CHECK (
            ((numeric_value IS NOT NULL)::INTEGER
            + (text_value IS NOT NULL)::INTEGER
            + (boolean_value IS NOT NULL)::INTEGER) = 1
        ),
    CONSTRAINT ck_hidra_telemetry_reading_time
        CHECK (received_at >= source_timestamp),
    CONSTRAINT ck_hidra_telemetry_reading_rejection_reason
        CHECK ((state = 'REJECTED' AND rejection_reason IS NOT NULL) OR state <> 'REJECTED')
);

CREATE INDEX idx_hidra_telemetry_source_type
    ON hidra_telemetry_source (source_type_id);

CREATE INDEX idx_hidra_telemetry_source_protocol
    ON hidra_telemetry_source (protocol_id);

CREATE INDEX idx_hidra_telemetry_source_status
    ON hidra_telemetry_source (status);

CREATE INDEX idx_hidra_telemetry_device_source
    ON hidra_telemetry_device (source_id);

CREATE INDEX idx_hidra_telemetry_device_type
    ON hidra_telemetry_device (device_type_id);

CREATE INDEX idx_hidra_telemetry_device_status
    ON hidra_telemetry_device (status);

CREATE INDEX idx_hidra_telemetry_point_device
    ON hidra_telemetry_point (device_id);

CREATE INDEX idx_hidra_telemetry_point_type
    ON hidra_telemetry_point (point_type_id);

CREATE INDEX idx_hidra_telemetry_point_signal_type
    ON hidra_telemetry_point (signal_type_id);

CREATE INDEX idx_hidra_telemetry_point_status
    ON hidra_telemetry_point (status);

CREATE INDEX idx_hidra_telemetry_point_binding_point
    ON hidra_telemetry_point_binding (point_id);

CREATE INDEX idx_hidra_telemetry_point_binding_topology
    ON hidra_telemetry_point_binding (topology_asset_type_code, topology_asset_id);

CREATE INDEX idx_hidra_telemetry_point_binding_active
    ON hidra_telemetry_point_binding (point_id, active);

CREATE INDEX idx_hidra_telemetry_ingestion_batch_source
    ON hidra_telemetry_ingestion_batch (source_id);

CREATE INDEX idx_hidra_telemetry_ingestion_batch_status
    ON hidra_telemetry_ingestion_batch (status);

CREATE INDEX idx_hidra_telemetry_ingestion_batch_started
    ON hidra_telemetry_ingestion_batch (started_at);

CREATE INDEX idx_hidra_telemetry_reading_point_source_time
    ON hidra_telemetry_reading (point_id, source_timestamp DESC);

CREATE INDEX idx_hidra_telemetry_reading_quality
    ON hidra_telemetry_reading (quality_code_id);

CREATE INDEX idx_hidra_telemetry_reading_state
    ON hidra_telemetry_reading (state);

CREATE INDEX idx_hidra_telemetry_reading_batch
    ON hidra_telemetry_reading (ingestion_batch_id);

INSERT INTO hidra_telemetry_type_catalog (
    id,
    catalog_name,
    code,
    active,
    sort_order,
    system_defined
)
VALUES
    ('telemetry-source-type-scada', 'SOURCE_TYPE', 'SCADA', TRUE, 10, TRUE),
    ('telemetry-source-type-historian', 'SOURCE_TYPE', 'HISTORIAN', TRUE, 20, TRUE),
    ('telemetry-source-type-plc-gateway', 'SOURCE_TYPE', 'PLC_GATEWAY', TRUE, 30, TRUE),
    ('telemetry-source-type-iot-gateway', 'SOURCE_TYPE', 'IOT_GATEWAY', TRUE, 40, TRUE),
    ('telemetry-source-type-manual-import', 'SOURCE_TYPE', 'MANUAL_IMPORT', TRUE, 50, TRUE),
    ('telemetry-device-type-plc', 'DEVICE_TYPE', 'PLC', TRUE, 10, TRUE),
    ('telemetry-device-type-rtu', 'DEVICE_TYPE', 'RTU', TRUE, 20, TRUE),
    ('telemetry-device-type-meter', 'DEVICE_TYPE', 'METER', TRUE, 30, TRUE),
    ('telemetry-device-type-transmitter', 'DEVICE_TYPE', 'TRANSMITTER', TRUE, 40, TRUE),
    ('telemetry-device-type-gateway', 'DEVICE_TYPE', 'GATEWAY', TRUE, 50, TRUE),
    ('telemetry-point-type-pressure', 'POINT_TYPE', 'PRESSURE', TRUE, 10, TRUE),
    ('telemetry-point-type-temperature', 'POINT_TYPE', 'TEMPERATURE', TRUE, 20, TRUE),
    ('telemetry-point-type-flow-rate', 'POINT_TYPE', 'FLOW_RATE', TRUE, 30, TRUE),
    ('telemetry-point-type-level', 'POINT_TYPE', 'LEVEL', TRUE, 40, TRUE),
    ('telemetry-point-type-valve-position', 'POINT_TYPE', 'VALVE_POSITION', TRUE, 50, TRUE),
    ('telemetry-point-type-density', 'POINT_TYPE', 'DENSITY', TRUE, 60, TRUE),
    ('telemetry-point-type-viscosity', 'POINT_TYPE', 'VISCOSITY', TRUE, 70, TRUE),
    ('telemetry-point-type-status', 'POINT_TYPE', 'STATUS', TRUE, 80, TRUE),
    ('telemetry-signal-type-numeric', 'SIGNAL_TYPE', 'NUMERIC', TRUE, 10, TRUE),
    ('telemetry-signal-type-text', 'SIGNAL_TYPE', 'TEXT', TRUE, 20, TRUE),
    ('telemetry-signal-type-boolean', 'SIGNAL_TYPE', 'BOOLEAN', TRUE, 30, TRUE),
    ('telemetry-unit-bar', 'UNIT', 'BAR', TRUE, 10, TRUE),
    ('telemetry-unit-celsius', 'UNIT', 'CELSIUS', TRUE, 20, TRUE),
    ('telemetry-unit-m3-h', 'UNIT', 'M3_H', TRUE, 30, TRUE),
    ('telemetry-unit-percent', 'UNIT', 'PERCENT', TRUE, 40, TRUE),
    ('telemetry-unit-kg-m3', 'UNIT', 'KG_M3', TRUE, 50, TRUE),
    ('telemetry-unit-cp', 'UNIT', 'CP', TRUE, 60, TRUE),
    ('telemetry-quality-good', 'QUALITY_CODE', 'GOOD', TRUE, 10, TRUE),
    ('telemetry-quality-bad', 'QUALITY_CODE', 'BAD', TRUE, 20, TRUE),
    ('telemetry-quality-uncertain', 'QUALITY_CODE', 'UNCERTAIN', TRUE, 30, TRUE),
    ('telemetry-quality-substituted', 'QUALITY_CODE', 'SUBSTITUTED', TRUE, 40, TRUE),
    ('telemetry-quality-manual', 'QUALITY_CODE', 'MANUAL', TRUE, 50, TRUE),
    ('telemetry-quality-missing', 'QUALITY_CODE', 'MISSING', TRUE, 60, TRUE),
    ('telemetry-protocol-opc-ua', 'PROTOCOL', 'OPC_UA', TRUE, 10, TRUE),
    ('telemetry-protocol-modbus', 'PROTOCOL', 'MODBUS', TRUE, 20, TRUE),
    ('telemetry-protocol-mqtt', 'PROTOCOL', 'MQTT', TRUE, 30, TRUE),
    ('telemetry-protocol-historian-api', 'PROTOCOL', 'HISTORIAN_API', TRUE, 40, TRUE),
    ('telemetry-protocol-manual', 'PROTOCOL', 'MANUAL', TRUE, 50, TRUE),
    ('telemetry-aggregation-none', 'AGGREGATION_METHOD', 'NONE', TRUE, 10, TRUE),
    ('telemetry-aggregation-average', 'AGGREGATION_METHOD', 'AVERAGE', TRUE, 20, TRUE),
    ('telemetry-aggregation-min', 'AGGREGATION_METHOD', 'MIN', TRUE, 30, TRUE),
    ('telemetry-aggregation-max', 'AGGREGATION_METHOD', 'MAX', TRUE, 40, TRUE),
    ('telemetry-aggregation-sum', 'AGGREGATION_METHOD', 'SUM', TRUE, 50, TRUE),
    ('telemetry-aggregation-last', 'AGGREGATION_METHOD', 'LAST', TRUE, 60, TRUE),
    ('telemetry-binding-role-primary', 'BINDING_ROLE', 'PRIMARY_MEASUREMENT', TRUE, 10, TRUE),
    ('telemetry-binding-role-secondary', 'BINDING_ROLE', 'SECONDARY_MEASUREMENT', TRUE, 20, TRUE),
    ('telemetry-binding-role-control', 'BINDING_ROLE', 'CONTROL_SIGNAL', TRUE, 30, TRUE),
    ('telemetry-binding-role-status', 'BINDING_ROLE', 'STATUS_SIGNAL', TRUE, 40, TRUE);

INSERT INTO hidra_telemetry_type_translation (
    id,
    type_id,
    locale,
    name,
    description
)
VALUES
    ('telemetry-source-type-scada-ar', 'telemetry-source-type-scada', 'ar', 'سكادا', 'وصف سكادا'),
    ('telemetry-source-type-scada-fr', 'telemetry-source-type-scada', 'fr', 'SCADA', 'Description SCADA'),
    ('telemetry-source-type-scada-en', 'telemetry-source-type-scada', 'en', 'SCADA', 'SCADA description'),
    ('telemetry-source-type-historian-ar', 'telemetry-source-type-historian', 'ar', 'المؤرخ الصناعي', 'وصف المؤرخ الصناعي'),
    ('telemetry-source-type-historian-fr', 'telemetry-source-type-historian', 'fr', 'Historien industriel', 'Description Historien industriel'),
    ('telemetry-source-type-historian-en', 'telemetry-source-type-historian', 'en', 'Historian', 'Historian description'),
    ('telemetry-source-type-plc-gateway-ar', 'telemetry-source-type-plc-gateway', 'ar', 'بوابة PLC', 'وصف بوابة PLC'),
    ('telemetry-source-type-plc-gateway-fr', 'telemetry-source-type-plc-gateway', 'fr', 'Passerelle PLC', 'Description Passerelle PLC'),
    ('telemetry-source-type-plc-gateway-en', 'telemetry-source-type-plc-gateway', 'en', 'PLC gateway', 'PLC gateway description'),
    ('telemetry-source-type-iot-gateway-ar', 'telemetry-source-type-iot-gateway', 'ar', 'بوابة إنترنت الأشياء', 'وصف بوابة إنترنت الأشياء'),
    ('telemetry-source-type-iot-gateway-fr', 'telemetry-source-type-iot-gateway', 'fr', 'Passerelle IoT', 'Description Passerelle IoT'),
    ('telemetry-source-type-iot-gateway-en', 'telemetry-source-type-iot-gateway', 'en', 'IoT gateway', 'IoT gateway description'),
    ('telemetry-source-type-manual-import-ar', 'telemetry-source-type-manual-import', 'ar', 'استيراد يدوي', 'وصف استيراد يدوي'),
    ('telemetry-source-type-manual-import-fr', 'telemetry-source-type-manual-import', 'fr', 'Import manuel', 'Description Import manuel'),
    ('telemetry-source-type-manual-import-en', 'telemetry-source-type-manual-import', 'en', 'Manual import', 'Manual import description'),
    ('telemetry-device-type-plc-ar', 'telemetry-device-type-plc', 'ar', 'وحدة PLC', 'وصف وحدة PLC'),
    ('telemetry-device-type-plc-fr', 'telemetry-device-type-plc', 'fr', 'PLC', 'Description PLC'),
    ('telemetry-device-type-plc-en', 'telemetry-device-type-plc', 'en', 'PLC', 'PLC description'),
    ('telemetry-device-type-rtu-ar', 'telemetry-device-type-rtu', 'ar', 'وحدة RTU', 'وصف وحدة RTU'),
    ('telemetry-device-type-rtu-fr', 'telemetry-device-type-rtu', 'fr', 'RTU', 'Description RTU'),
    ('telemetry-device-type-rtu-en', 'telemetry-device-type-rtu', 'en', 'RTU', 'RTU description'),
    ('telemetry-device-type-meter-ar', 'telemetry-device-type-meter', 'ar', 'عداد', 'وصف عداد'),
    ('telemetry-device-type-meter-fr', 'telemetry-device-type-meter', 'fr', 'Compteur', 'Description Compteur'),
    ('telemetry-device-type-meter-en', 'telemetry-device-type-meter', 'en', 'Meter', 'Meter description'),
    ('telemetry-device-type-transmitter-ar', 'telemetry-device-type-transmitter', 'ar', 'مرسل', 'وصف مرسل'),
    ('telemetry-device-type-transmitter-fr', 'telemetry-device-type-transmitter', 'fr', 'Transmetteur', 'Description Transmetteur'),
    ('telemetry-device-type-transmitter-en', 'telemetry-device-type-transmitter', 'en', 'Transmitter', 'Transmitter description'),
    ('telemetry-device-type-gateway-ar', 'telemetry-device-type-gateway', 'ar', 'بوابة', 'وصف بوابة'),
    ('telemetry-device-type-gateway-fr', 'telemetry-device-type-gateway', 'fr', 'Passerelle', 'Description Passerelle'),
    ('telemetry-device-type-gateway-en', 'telemetry-device-type-gateway', 'en', 'Gateway', 'Gateway description'),
    ('telemetry-point-type-pressure-ar', 'telemetry-point-type-pressure', 'ar', 'ضغط', 'وصف ضغط'),
    ('telemetry-point-type-pressure-fr', 'telemetry-point-type-pressure', 'fr', 'Pression', 'Description Pression'),
    ('telemetry-point-type-pressure-en', 'telemetry-point-type-pressure', 'en', 'Pressure', 'Pressure description'),
    ('telemetry-point-type-temperature-ar', 'telemetry-point-type-temperature', 'ar', 'حرارة', 'وصف حرارة'),
    ('telemetry-point-type-temperature-fr', 'telemetry-point-type-temperature', 'fr', 'Température', 'Description Température'),
    ('telemetry-point-type-temperature-en', 'telemetry-point-type-temperature', 'en', 'Temperature', 'Temperature description'),
    ('telemetry-point-type-flow-rate-ar', 'telemetry-point-type-flow-rate', 'ar', 'معدل التدفق', 'وصف معدل التدفق'),
    ('telemetry-point-type-flow-rate-fr', 'telemetry-point-type-flow-rate', 'fr', 'Débit', 'Description Débit'),
    ('telemetry-point-type-flow-rate-en', 'telemetry-point-type-flow-rate', 'en', 'Flow rate', 'Flow rate description'),
    ('telemetry-point-type-level-ar', 'telemetry-point-type-level', 'ar', 'مستوى', 'وصف مستوى'),
    ('telemetry-point-type-level-fr', 'telemetry-point-type-level', 'fr', 'Niveau', 'Description Niveau'),
    ('telemetry-point-type-level-en', 'telemetry-point-type-level', 'en', 'Level', 'Level description'),
    ('telemetry-point-type-valve-position-ar', 'telemetry-point-type-valve-position', 'ar', 'موضع الصمام', 'وصف موضع الصمام'),
    ('telemetry-point-type-valve-position-fr', 'telemetry-point-type-valve-position', 'fr', 'Position de vanne', 'Description Position de vanne'),
    ('telemetry-point-type-valve-position-en', 'telemetry-point-type-valve-position', 'en', 'Valve position', 'Valve position description'),
    ('telemetry-point-type-density-ar', 'telemetry-point-type-density', 'ar', 'كثافة', 'وصف كثافة'),
    ('telemetry-point-type-density-fr', 'telemetry-point-type-density', 'fr', 'Densité', 'Description Densité'),
    ('telemetry-point-type-density-en', 'telemetry-point-type-density', 'en', 'Density', 'Density description'),
    ('telemetry-point-type-viscosity-ar', 'telemetry-point-type-viscosity', 'ar', 'لزوجة', 'وصف لزوجة'),
    ('telemetry-point-type-viscosity-fr', 'telemetry-point-type-viscosity', 'fr', 'Viscosité', 'Description Viscosité'),
    ('telemetry-point-type-viscosity-en', 'telemetry-point-type-viscosity', 'en', 'Viscosity', 'Viscosity description'),
    ('telemetry-point-type-status-ar', 'telemetry-point-type-status', 'ar', 'حالة', 'وصف حالة'),
    ('telemetry-point-type-status-fr', 'telemetry-point-type-status', 'fr', 'État', 'Description État'),
    ('telemetry-point-type-status-en', 'telemetry-point-type-status', 'en', 'Status', 'Status description'),
    ('telemetry-signal-type-numeric-ar', 'telemetry-signal-type-numeric', 'ar', 'رقمي', 'وصف رقمي'),
    ('telemetry-signal-type-numeric-fr', 'telemetry-signal-type-numeric', 'fr', 'Numérique', 'Description Numérique'),
    ('telemetry-signal-type-numeric-en', 'telemetry-signal-type-numeric', 'en', 'Numeric', 'Numeric description'),
    ('telemetry-signal-type-text-ar', 'telemetry-signal-type-text', 'ar', 'نصي', 'وصف نصي'),
    ('telemetry-signal-type-text-fr', 'telemetry-signal-type-text', 'fr', 'Texte', 'Description Texte'),
    ('telemetry-signal-type-text-en', 'telemetry-signal-type-text', 'en', 'Text', 'Text description'),
    ('telemetry-signal-type-boolean-ar', 'telemetry-signal-type-boolean', 'ar', 'منطقي', 'وصف منطقي'),
    ('telemetry-signal-type-boolean-fr', 'telemetry-signal-type-boolean', 'fr', 'Booléen', 'Description Booléen'),
    ('telemetry-signal-type-boolean-en', 'telemetry-signal-type-boolean', 'en', 'Boolean', 'Boolean description'),
    ('telemetry-unit-bar-ar', 'telemetry-unit-bar', 'ar', 'بار', 'وصف بار'),
    ('telemetry-unit-bar-fr', 'telemetry-unit-bar', 'fr', 'bar', 'Description bar'),
    ('telemetry-unit-bar-en', 'telemetry-unit-bar', 'en', 'bar', 'bar description'),
    ('telemetry-unit-celsius-ar', 'telemetry-unit-celsius', 'ar', 'درجة مئوية', 'وصف درجة مئوية'),
    ('telemetry-unit-celsius-fr', 'telemetry-unit-celsius', 'fr', '°C', 'Description °C'),
    ('telemetry-unit-celsius-en', 'telemetry-unit-celsius', 'en', '°C', '°C description'),
    ('telemetry-unit-m3-h-ar', 'telemetry-unit-m3-h', 'ar', 'متر مكعب في الساعة', 'وصف متر مكعب في الساعة'),
    ('telemetry-unit-m3-h-fr', 'telemetry-unit-m3-h', 'fr', 'm³/h', 'Description m³/h'),
    ('telemetry-unit-m3-h-en', 'telemetry-unit-m3-h', 'en', 'm³/h', 'm³/h description'),
    ('telemetry-unit-percent-ar', 'telemetry-unit-percent', 'ar', 'نسبة مئوية', 'وصف نسبة مئوية'),
    ('telemetry-unit-percent-fr', 'telemetry-unit-percent', 'fr', '%', 'Description %'),
    ('telemetry-unit-percent-en', 'telemetry-unit-percent', 'en', '%', '% description'),
    ('telemetry-unit-kg-m3-ar', 'telemetry-unit-kg-m3', 'ar', 'كغ/م³', 'وصف كغ/م³'),
    ('telemetry-unit-kg-m3-fr', 'telemetry-unit-kg-m3', 'fr', 'kg/m³', 'Description kg/m³'),
    ('telemetry-unit-kg-m3-en', 'telemetry-unit-kg-m3', 'en', 'kg/m³', 'kg/m³ description'),
    ('telemetry-unit-cp-ar', 'telemetry-unit-cp', 'ar', 'سنتيبواز', 'وصف سنتيبواز'),
    ('telemetry-unit-cp-fr', 'telemetry-unit-cp', 'fr', 'cP', 'Description cP'),
    ('telemetry-unit-cp-en', 'telemetry-unit-cp', 'en', 'cP', 'cP description'),
    ('telemetry-quality-good-ar', 'telemetry-quality-good', 'ar', 'جيد', 'وصف جيد'),
    ('telemetry-quality-good-fr', 'telemetry-quality-good', 'fr', 'Bonne', 'Description Bonne'),
    ('telemetry-quality-good-en', 'telemetry-quality-good', 'en', 'Good', 'Good description'),
    ('telemetry-quality-bad-ar', 'telemetry-quality-bad', 'ar', 'سيئ', 'وصف سيئ'),
    ('telemetry-quality-bad-fr', 'telemetry-quality-bad', 'fr', 'Mauvaise', 'Description Mauvaise'),
    ('telemetry-quality-bad-en', 'telemetry-quality-bad', 'en', 'Bad', 'Bad description'),
    ('telemetry-quality-uncertain-ar', 'telemetry-quality-uncertain', 'ar', 'غير مؤكد', 'وصف غير مؤكد'),
    ('telemetry-quality-uncertain-fr', 'telemetry-quality-uncertain', 'fr', 'Incertaine', 'Description Incertaine'),
    ('telemetry-quality-uncertain-en', 'telemetry-quality-uncertain', 'en', 'Uncertain', 'Uncertain description'),
    ('telemetry-quality-substituted-ar', 'telemetry-quality-substituted', 'ar', 'مستبدل', 'وصف مستبدل'),
    ('telemetry-quality-substituted-fr', 'telemetry-quality-substituted', 'fr', 'Substituée', 'Description Substituée'),
    ('telemetry-quality-substituted-en', 'telemetry-quality-substituted', 'en', 'Substituted', 'Substituted description'),
    ('telemetry-quality-manual-ar', 'telemetry-quality-manual', 'ar', 'يدوي', 'وصف يدوي'),
    ('telemetry-quality-manual-fr', 'telemetry-quality-manual', 'fr', 'Manuelle', 'Description Manuelle'),
    ('telemetry-quality-manual-en', 'telemetry-quality-manual', 'en', 'Manual', 'Manual description'),
    ('telemetry-quality-missing-ar', 'telemetry-quality-missing', 'ar', 'مفقود', 'وصف مفقود'),
    ('telemetry-quality-missing-fr', 'telemetry-quality-missing', 'fr', 'Manquante', 'Description Manquante'),
    ('telemetry-quality-missing-en', 'telemetry-quality-missing', 'en', 'Missing', 'Missing description'),
    ('telemetry-protocol-opc-ua-ar', 'telemetry-protocol-opc-ua', 'ar', 'OPC UA', 'وصف OPC UA'),
    ('telemetry-protocol-opc-ua-fr', 'telemetry-protocol-opc-ua', 'fr', 'OPC UA', 'Description OPC UA'),
    ('telemetry-protocol-opc-ua-en', 'telemetry-protocol-opc-ua', 'en', 'OPC UA', 'OPC UA description'),
    ('telemetry-protocol-modbus-ar', 'telemetry-protocol-modbus', 'ar', 'مودباص', 'وصف مودباص'),
    ('telemetry-protocol-modbus-fr', 'telemetry-protocol-modbus', 'fr', 'Modbus', 'Description Modbus'),
    ('telemetry-protocol-modbus-en', 'telemetry-protocol-modbus', 'en', 'Modbus', 'Modbus description'),
    ('telemetry-protocol-mqtt-ar', 'telemetry-protocol-mqtt', 'ar', 'MQTT', 'وصف MQTT'),
    ('telemetry-protocol-mqtt-fr', 'telemetry-protocol-mqtt', 'fr', 'MQTT', 'Description MQTT'),
    ('telemetry-protocol-mqtt-en', 'telemetry-protocol-mqtt', 'en', 'MQTT', 'MQTT description'),
    ('telemetry-protocol-historian-api-ar', 'telemetry-protocol-historian-api', 'ar', 'واجهة المؤرخ', 'وصف واجهة المؤرخ'),
    ('telemetry-protocol-historian-api-fr', 'telemetry-protocol-historian-api', 'fr', 'API historien', 'Description API historien'),
    ('telemetry-protocol-historian-api-en', 'telemetry-protocol-historian-api', 'en', 'Historian API', 'Historian API description'),
    ('telemetry-protocol-manual-ar', 'telemetry-protocol-manual', 'ar', 'يدوي', 'وصف يدوي'),
    ('telemetry-protocol-manual-fr', 'telemetry-protocol-manual', 'fr', 'Manuel', 'Description Manuel'),
    ('telemetry-protocol-manual-en', 'telemetry-protocol-manual', 'en', 'Manual', 'Manual description'),
    ('telemetry-aggregation-none-ar', 'telemetry-aggregation-none', 'ar', 'بدون', 'وصف بدون'),
    ('telemetry-aggregation-none-fr', 'telemetry-aggregation-none', 'fr', 'Aucune', 'Description Aucune'),
    ('telemetry-aggregation-none-en', 'telemetry-aggregation-none', 'en', 'None', 'None description'),
    ('telemetry-aggregation-average-ar', 'telemetry-aggregation-average', 'ar', 'متوسط', 'وصف متوسط'),
    ('telemetry-aggregation-average-fr', 'telemetry-aggregation-average', 'fr', 'Moyenne', 'Description Moyenne'),
    ('telemetry-aggregation-average-en', 'telemetry-aggregation-average', 'en', 'Average', 'Average description'),
    ('telemetry-aggregation-min-ar', 'telemetry-aggregation-min', 'ar', 'حد أدنى', 'وصف حد أدنى'),
    ('telemetry-aggregation-min-fr', 'telemetry-aggregation-min', 'fr', 'Minimum', 'Description Minimum'),
    ('telemetry-aggregation-min-en', 'telemetry-aggregation-min', 'en', 'Minimum', 'Minimum description'),
    ('telemetry-aggregation-max-ar', 'telemetry-aggregation-max', 'ar', 'حد أقصى', 'وصف حد أقصى'),
    ('telemetry-aggregation-max-fr', 'telemetry-aggregation-max', 'fr', 'Maximum', 'Description Maximum'),
    ('telemetry-aggregation-max-en', 'telemetry-aggregation-max', 'en', 'Maximum', 'Maximum description'),
    ('telemetry-aggregation-sum-ar', 'telemetry-aggregation-sum', 'ar', 'مجموع', 'وصف مجموع'),
    ('telemetry-aggregation-sum-fr', 'telemetry-aggregation-sum', 'fr', 'Somme', 'Description Somme'),
    ('telemetry-aggregation-sum-en', 'telemetry-aggregation-sum', 'en', 'Sum', 'Sum description'),
    ('telemetry-aggregation-last-ar', 'telemetry-aggregation-last', 'ar', 'آخر قيمة', 'وصف آخر قيمة'),
    ('telemetry-aggregation-last-fr', 'telemetry-aggregation-last', 'fr', 'Dernière valeur', 'Description Dernière valeur'),
    ('telemetry-aggregation-last-en', 'telemetry-aggregation-last', 'en', 'Last value', 'Last value description'),
    ('telemetry-binding-role-primary-ar', 'telemetry-binding-role-primary', 'ar', 'قياس رئيسي', 'وصف قياس رئيسي'),
    ('telemetry-binding-role-primary-fr', 'telemetry-binding-role-primary', 'fr', 'Mesure principale', 'Description Mesure principale'),
    ('telemetry-binding-role-primary-en', 'telemetry-binding-role-primary', 'en', 'Primary measurement', 'Primary measurement description'),
    ('telemetry-binding-role-secondary-ar', 'telemetry-binding-role-secondary', 'ar', 'قياس ثانوي', 'وصف قياس ثانوي'),
    ('telemetry-binding-role-secondary-fr', 'telemetry-binding-role-secondary', 'fr', 'Mesure secondaire', 'Description Mesure secondaire'),
    ('telemetry-binding-role-secondary-en', 'telemetry-binding-role-secondary', 'en', 'Secondary measurement', 'Secondary measurement description'),
    ('telemetry-binding-role-control-ar', 'telemetry-binding-role-control', 'ar', 'إشارة تحكم', 'وصف إشارة تحكم'),
    ('telemetry-binding-role-control-fr', 'telemetry-binding-role-control', 'fr', 'Signal de commande', 'Description Signal de commande'),
    ('telemetry-binding-role-control-en', 'telemetry-binding-role-control', 'en', 'Control signal', 'Control signal description'),
    ('telemetry-binding-role-status-ar', 'telemetry-binding-role-status', 'ar', 'إشارة حالة', 'وصف إشارة حالة'),
    ('telemetry-binding-role-status-fr', 'telemetry-binding-role-status', 'fr', 'Signal d''état', 'Description Signal d''état'),
    ('telemetry-binding-role-status-en', 'telemetry-binding-role-status', 'en', 'Status signal', 'Status signal description');
