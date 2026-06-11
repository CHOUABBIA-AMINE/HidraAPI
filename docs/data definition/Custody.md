# HIDRA Custody Module — Data Definition Document

```text
Document code : HIDRA-CUSTODY-DDD
Repository    : HidraAPI
Module        : custody
Package root  : dz.sh.hidra.modules.custody
Table prefix  : hidra_custody_*
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
UpdatedOn     : 2026-06-11
Status        : Active DDD reference
Version       : 1.1
```

---

## 1. Purpose

The `custody` module owns official accepted transfer records: custody points, agreements, parties by neutral reference, measurement periods, batches, metering-system snapshots, quantity calculations, quality evidence, transfer tickets, reconciliation, discrepancies, approvals, and document references.

Custody is not telemetry, not planning, not finance/ERP, and not SCADA.

---

## 2. Canonical implementation identity

```text
Module name   : custody
Package root  : dz.sh.hidra.modules.custody
Table prefix  : hidra_custody_*
```

Forbidden table prefixes:

```text
hidra_telemetry_*
hidra_planning_*
hidra_finance_*
hidra_erp_*
hidra_topology_*
```

---

## 3. Ownership

Custody owns:

```text
CustodyTransferPoint
CustodyAgreement
CustodyAgreementParty
CustodyMeasurementPeriod
CustodyBatch
CustodyMeteringSystem
CustodyMeterRunSnapshot
CustodyMeasurementSnapshot
CustodyQualitySample
CustodyQualityCertificate
CustodyQuantityCalculation
CustodyCorrectionFactor
CustodyTransferTicket
CustodyTicketLine
CustodyReconciliation
CustodyDiscrepancy
CustodyApprovalReference
CustodyDocumentReference
CustodyCatalogEntry
CustodyCatalogTranslation
```

Custody does not own:

```text
TelemetryReading
TrustedTelemetryReading
TelemetryPoint
OperationalPlan
PlanTarget
ExpectedFlowState
Pipeline
Facility
MeasurementLocation
Finance invoice
ERP posting
External legal party master data
WorkflowTask
AuditRecord
```

---

## 4. Boundary rule

```text
Telemetry says what was measured.
Planning says what was expected.
Custody says what was officially transferred and accepted.
Finance/ERP says what was invoiced or posted.
```

Finance/ERP remains blocked until a dedicated DDD exists.

---

## 5. Snapshot rule

Custody must store immutable snapshots of the telemetry, metering, quality, topology, agreement, and approval evidence used for official transfer decisions.

Allowed snapshot entities:

```text
CustodyMeterRunSnapshot
CustodyMeasurementSnapshot
CustodyQualitySample
CustodyQualityCertificate
CustodyQuantityCalculation
CustodyTransferTicket
```

Forbidden:

```text
Custody owns TelemetryReading
Custody owns OperationalPlan
Custody writes hidra_telemetry_* tables
Custody writes hidra_planning_* tables
Custody creates finance invoice/posting tables
```

---

## 6. Entity catalogue

| Entity | Table | Purpose |
|---|---|---|
| CustodyTransferPoint | `hidra_custody_transfer_point` | Official transfer point. |
| CustodyAgreement | `hidra_custody_agreement` | Agreement governing custody transfer. |
| CustodyAgreementParty | `hidra_custody_agreement_party` | Party reference in agreement. |
| CustodyMeasurementPeriod | `hidra_custody_measurement_period` | Fiscal/official measurement period. |
| CustodyBatch | `hidra_custody_batch` | Batch transferred under custody. |
| CustodyMeteringSystem | `hidra_custody_metering_system` | Metering system used for custody. |
| CustodyMeterRunSnapshot | `hidra_custody_meter_run_snapshot` | Meter-run snapshot. |
| CustodyMeasurementSnapshot | `hidra_custody_measurement_snapshot` | Accepted measurement snapshot. |
| CustodyQualitySample | `hidra_custody_quality_sample` | Quality sample. |
| CustodyQualityCertificate | `hidra_custody_quality_certificate` | Quality certificate reference. |
| CustodyQuantityCalculation | `hidra_custody_quantity_calculation` | Official quantity calculation. |
| CustodyCorrectionFactor | `hidra_custody_correction_factor` | Correction factor. |
| CustodyTransferTicket | `hidra_custody_transfer_ticket` | Official transfer ticket. |
| CustodyTicketLine | `hidra_custody_ticket_line` | Ticket line. |
| CustodyReconciliation | `hidra_custody_reconciliation` | Reconciliation record. |
| CustodyDiscrepancy | `hidra_custody_discrepancy` | Discrepancy record. |
| CustodyApprovalReference | `hidra_custody_approval_reference` | Workflow approval reference. |
| CustodyDocumentReference | `hidra_custody_document_reference` | Document reference. |
| CustodyCatalogEntry | `hidra_custody_catalog_entry` | Custody-owned catalog. |
| CustodyCatalogTranslation | `hidra_custody_catalog_translation` | Multilingual catalog labels. |

---

## 7. Documentation and annotation rule

Domain, application, and infrastructure custody models must not use `@Schema` or OpenAPI annotations.

`@Schema` is allowed only in custody API request/response models.
