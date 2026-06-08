# Hidra V2 Scope — Integration

```text
Document code : HIDRA-V2-SCOPE
Repository    : HidraAPI
Namespace     : dz.sh.hidra
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Document type : Product scope definition
Version       : V2
Phase         : Horizon 3 — Integration
Author        : Abir MEDJERAB
CreatedOn     : 2025-06-26
UpdatedOn     : 2026-06-07
Status        : Draft for discussion
```

---

## 1. Purpose

This document defines the scope of **Hidra V2**.

Hidra V2 starts after V1 and V1.1 have established:

```text
trusted operational data
workflow validation
planning
monitoring
incidents
audit hardening
basic operational visibility
```

V2 connects Hidra to industrial and enterprise systems.

---

## 2. V2 name

```text
Hidra V2 — Integration
```

---

## 3. V2 objective

V2 must connect Hidra reliably to external systems without corrupting domain ownership.

V2 answers:

```text
Which external system produced this data?
Was the import successful?
Was the external reference preserved?
Can failed ingestion be retried?
Can data be mapped safely?
Can Hidra exchange information with industrial systems without bypassing validation?
Can enterprise IAM and notification channels be connected?
```

---

## 4. Required preconditions

Do not start V2 before V1.1 is accepted.

Required V1.1 baseline:

```text
kernel/platform foundation remains stable
identity remains the actor and access source

planning exists
monitoring exists
incidents exist
audit hardening exists
basic dashboards/read views exist
full tests pass
```

V2 depends on stable operational boundaries.

---

## 5. Included modules and capabilities

V2 includes:

```text
integration context
SCADA integration readiness
historian integration
OPC UA readiness
MQTT readiness
REST integration APIs
file import/export
batch ingestion jobs
enterprise IAM integration
notification channel integration
external references
mapping rules
retry/dead-letter handling
integration audit
```

---

## 6. Integration context scope

### 6.1 Integration purpose

Integration manages external systems, connectors, ingestion jobs, mappings, retries, and external references.

### 6.2 Integration owns

```text
ExternalSystem
ConnectorConfiguration
IngestionJob
IntegrationMapping
ExternalReference
RetryPolicy
DeadLetterRecord
IntegrationRun
IntegrationRunStatus
IntegrationError
```

### 6.3 Integration references

```text
telemetry source reference
telemetry ingestion batch reference
identity actor reference
organization unit reference
audit event reference
notification delivery reference
```

### 6.4 Integration does not own

```text
telemetry readings
workflow decisions
topology assets
monitoring rules
incidents
analytics projections
```

---

## 7. SCADA integration scope

V2 includes SCADA integration readiness and controlled data exchange.

Allowed V2 SCADA scope:

```text
read-only data ingestion
external tag reference mapping
source/device/point mapping
timestamp and quality mapping
external system metadata
raw payload preservation
failed ingestion traceability
```

Not allowed in V2:

```text
SCADA control commands
valve or pump actuation
replacement of industrial control systems
unvalidated direct write into trusted operational state
```

---

## 8. Historian integration scope

V2 may integrate with historian systems.

Allowed scope:

```text
historian reference registry
historian tag mapping
historian value import
historian timestamp/quality preservation
batch backfill
external reference tracking
```

Not allowed:

```text
replacing historian storage
high-volume time-series optimization unless required
analytics-first historian mining before trusted ingestion
```

---

## 9. OPC UA and MQTT readiness

V2 should prepare connector abstractions for:

```text
OPC UA
MQTT
REST polling
file drop ingestion
manual import
```

V2 may implement one or more production connectors only if a concrete operational need is selected.

Do not implement all connector types blindly.

---

## 10. Enterprise IAM integration

V2 may connect Hidra identity to enterprise IAM.

Scope:

```text
external identity provider reference
user synchronization readiness
role/group mapping
login federation readiness
external user id preservation
security audit events
```

Non-goals:

```text
complete identity governance platform
full enterprise IAM replacement
uncontrolled automatic privilege assignment
```

---

## 11. Notification channel integration

V2 may connect notification delivery channels.

Scope:

```text
email channel
SMS channel readiness
future push/webhook channel readiness
delivery tracking
template mapping
delivery failure audit
```

Important rule:

```text
Notification does not own workflow, monitoring, alert, or incident business rules.
Notification delivers messages requested by those modules.
```

---

## 12. File import/export scope

V2 may include controlled file import/export.

Allowed:

```text
CSV import/export
Excel import/export
validated import preview
mapping template
import errors
import audit trail
export from trusted read models
```

Rejected:

```text
silent imports
imports bypassing validation
exports from untrusted operational data without status marker
```

---

## 13. Integration acceptance criteria

V2 is accepted when:

```text
external systems can be registered
connector configurations can be managed
ingestion jobs can be started and tracked
external references are preserved
failed records are traceable
retries are controlled
dead-letter records exist
integration actions are auditable
telemetry ingestion still goes through telemetry rules
workflow validation is not bypassed
notification channels can be called by producer modules
```

---

## 14. V2 explicit non-goals

V2 must not attempt:

```text
SCADA control
automatic industrial actuation
full historian replacement
enterprise data lake replacement
complex streaming platform unless required by load
AI analytics
digital twin simulation
microservices decomposition by default
```

---

## 15. V2 version gate

V2 is complete when:

```text
Hidra can exchange data reliably with selected industrial and enterprise systems.
External references are preserved.
Failed integration events are traceable and retryable.
Domain validation is not bypassed.
Audit captures integration actions.
```

---

## 16. Output of V2

At the end of V2, Hidra should support:

```text
trusted operational core
operations modules
industrial data exchange
enterprise identity/channel readiness
reliable ingestion tracking
external reference preservation
integration audit
```

This prepares Hidra for V3 intelligence.
