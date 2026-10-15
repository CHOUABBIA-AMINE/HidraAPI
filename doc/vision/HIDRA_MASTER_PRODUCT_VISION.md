# Hidra — Master Product Vision

**Product:** Hidra — Hydrocarbon Intelligence for Data, Risk, and Analytics  
**Purpose:** Strategic product vision for hydrocarbon transportation intelligence  
**Product stewardship:** Sonatrach / TRC Digitalization Initiative

## Status

TARGET — enduring strategic product vision for Hidra.

---

## 1. Executive vision

**Hidra transforms hydrocarbon transportation data into trusted, validated, auditable and risk-aware operational intelligence.**

Hydrocarbon transportation depends on complex, geographically distributed pipeline systems, compression and pumping stations, measurement installations, people, procedures and industrial-control systems. Information about these systems often exists in different tools, operational disciplines and organizational records. Without a coherent view, teams can struggle to distinguish physical truth from assumptions, measurements from interpretations, and recommendations from verified outcomes.

Hidra is conceived as a **unified operational intelligence and engineering decision-support platform for Sonatrach's pipeline transportation activities**. It connects the physical network and its engineering definition to operational observations, plans, protective workflows, asset integrity, commercial measurement, simulation, risk, and management insight.

Its ambition is not simply to display data. It is to establish a trustworthy chain from **source evidence to operational context to explainable decisions**, while preserving human accountability and the authority of industrial safety and control systems.

### 1.1 Strategic mandate

Hidra aims to:

- Provide one coherent understanding of pipeline systems, stations, equipment and their relationships.
- Transform industrial measurements into quality-qualified, contextualized operational evidence.
- Connect engineering characteristics, operating plans and actual conditions.
- Improve early recognition, interpretation and response to deviations, suspected leaks and integrity risks.
- Support reproducible engineering analysis and simulation-informed decisions.
- Coordinate responsible people, permissions, approvals, incidents and corrective actions.
- Preserve traceability of measurements, revisions, judgments, actions and observed results.
- Deliver defensible reporting and insight without obscuring uncertainty or data provenance.

### 1.2 Enduring product proposition

> From every reliable operational fact, Hidra makes its source, physical context, meaning, accountable decision and consequences intelligible.

The platform should help answer: **What is this asset? What configuration applies? What has been observed? What does it imply? Which responses are possible? Who authorized the chosen response? What actually happened afterward?**

## 2. Product identity and experience

Hidra is one platform presented through complementary capabilities:

| Component | Product responsibility |
|---|---|
| **HidraAPI** | Governs business rules, source-of-truth records, security decisions, domain boundaries, engineering evidence, calculation services and authoritative integration contracts. |
| **HidraWEB** | Offers the operator and engineer experience: maps, forms, editors, trend views, operational workspaces, decision support and accessible exploration of trusted information. |
| **Connected industrial and enterprise systems** | Supply measurements, reference records, identity information and approved exchanges while retaining their independent operational authority. |

The platform should feel consistent across its experiences: a network identifier, revision, measurement context, user responsibility or decision should carry the same meaning wherever it appears.

**Hidra is not a SCADA replacement or an industrial actuator.** It operates above and alongside the automation environment, making information more coherent and decisions more accountable. Physical control, protective interlocks and safety-instrumented functions remain within their designated systems and authorized operational procedures.

## 3. Guiding principles

1. **Trusted data before intelligence.** Every consequential observation should have a known source, timestamp, unit, quality state and applicable context.
2. **Topology is the operational reference frame.** Readings, incidents, plans, asset conditions and simulation inputs are interpreted against a coherent physical network.
3. **One fact, one accountable owner.** Each business domain owns its authoritative records; other capabilities consume explicit, governed references rather than maintaining competing truths.
4. **Versioned engineering reality.** Physical configurations, surveyed geometry, equipment characteristics and product properties can change without erasing what was known or approved previously.
5. **Human judgment remains accountable.** The system may alert, explain, recommend and compare, but industrial decisions must retain appropriate human oversight.
6. **A command is not an outcome.** Recording an intention, approval or transmitted request must never be confused with independently observed physical execution.
7. **Security follows meaning.** Access depends on authenticated identity, organizational responsibility, ownership, scope and the requested business operation.
8. **Explainability over opacity.** Every significant prediction, risk score, simulation or recommendation should expose relevant inputs, assumptions, uncertainty and evidence.
9. **Interoperability without loss of authority.** Connected systems may contribute information, but integration must not bypass domain validation, provenance or security.
10. **Industrial safety by design.** Monitoring, integrity, leak detection, HSE, custody and cybersecurity are intrinsic product concerns, not peripheral dashboard features.
11. **Usability under operational pressure.** Interfaces should make stale data, denied actions, missing evidence and exceptional conditions clear, legible and accessible.
12. **Modularity with a unified product experience.** Distinct responsibilities and controlled interfaces protect the integrity of the whole platform.

## 4. The people Hidra serves

| Persona | Need and product value |
|---|---|
| Pipeline operator | Understand a system's operational condition, alarms and approved procedures; record observations and accountable actions. |
| Operations supervisor | Correlate deviations, operating constraints, incidents, decisions and follow-up across the network. |
| Survey and pipeline engineer | Maintain credible pipeline alignment, chainage, elevation, dimensions, connections and revision evidence. |
| Station and equipment engineer | Understand installed units, station connectivity, manufacturer characteristics and permissible operating envelopes. |
| Planner and dispatcher | Define and review operating plans, capacity assumptions, nominations and deviations from actual performance. |
| Simulation engineer | Prepare reproducible physical models, examine scenarios, understand numerical assumptions and compare results with measured reality. |
| Integrity and maintenance specialists | Relate operating history to degradation, inspection, assessment and maintenance execution. |
| HSE and incident teams | Govern safety observations, escalation, evidence, corrective actions and lessons learned. |
| Custody and measurement specialist | Reconcile product quantities and quality with traceable methods, instruments, adjustments and counterparties. |
| Identity and integration administrator | Maintain trusted access and approved connections without weakening business or industrial boundaries. |
| Auditor and compliance stakeholder | Reconstruct decisions, changes, source provenance, responsibilities and exceptions. |
| Executive and business leader | Examine reliable KPIs, risks, operational trends and explainable recommendations. |

## 5. Operational intelligence model

Hidra's product logic links **engineering truth**, **operational truth** and **decision truth**:

```text
  Organizational ownership and accountable identity
                         +
  Versioned network, stations, equipment and product definitions
                         +
  Qualified measurements, plans and reference evidence
                         |
                         v
             Coherent operational context
                         |
       +-----------------+-------------------+
       |                 |                   |
       v                 v                   v
  Monitoring and     Engineering         Planning, custody
  event detection    calculations        and risk analysis
       |                 |                   |
       +-----------------+-------------------+
                         v
          Explainable options and evidence
                         |
                         v
            Authorized human decision
                         |
                         v
            Requested operational action
                         |
                         v
           Independently observed outcome
                         |
                         v
            Audit, learning and improvement
```

Not every observation requires simulation, and not every incident requires an automatic recommendation. The chain instead establishes a common way to determine when available evidence is adequate, when it is uncertain and when accountable people must intervene.

## 6. Physical network and engineering information

### 6.1 Network topology

Hidra represents pipelines as connected physical systems, not merely lines on a map. The network model encompasses pipelines, segments, nodes, branches, interconnections, stations, operational boundaries, equipment relationships and measurement locations.

A trustworthy topology supports navigation, connectivity reasoning, isolation and impact analysis, planning, monitoring, engineering calculation and traceable network change.

### 6.2 Surveyed routes and geospatial context

Pipeline alignment requires explicit handling of coordinate reference systems, vertical datums, surveyed points, chainage/kilometric positioning, elevations, geometry precision and originating survey evidence. Cartographic simplification is suitable for display but must not silently replace authoritative survey data.

Engineers should be able to understand the relationship among map position, linear location, physical section, elevation profile and supporting source documentation.

### 6.3 Stations and installed equipment

Stations are modeled as interconnected assemblies: headers, manifolds, valves, compressors, pumps, regulators, meters, branches and other relevant installed units. Each unit has an identifiable role, physical connection and applicable technical characteristics.

A clear distinction is maintained between:

- **Installed engineering configuration:** where equipment is located, how it connects and how it behaves physically.
- **Maintainable assets:** inspections, conditions, service history, work orders and maintenance accountability.
- **Manufacturer and reference data:** curves, performance tables, design limits, units, assumptions and the evidence supporting them.

### 6.4 Engineering revisions and accepted configurations

The platform's engineering vision requires identifiable revisions of networks, profiles, pipe sections, station arrangements, manufacturer characteristics and product definitions. An engineering baseline binds together the exact relevant versions for a stated purpose.

Review, approval, supersession, comparison and historical reconstruction preserve the ability to explain which configuration supported a decision or a calculation. A new revision must not silently rewrite the past.

## 7. Products, fluid properties and commercial measurement

Hidra is conceived for hydrocarbon transportation systems whose engineering models and operating constraints depend on the transported product. Product identity, gas composition, fluid properties, units, measurement bases, property-calculation methods and qualification conditions must be explicit.

The product vision encompasses gas and other hydrocarbon transportation families as appropriate to their physical characteristics and industrial use cases. It does not presume that a single equation, table or measurement convention is universally valid.

Custody and measurement processes link trusted source readings with metering context, product assumptions, measurement periods, reconciliation, transfer records, discrepancy investigation and accountable commercial evidence. A defensible result identifies not only a quantity but also its basis and lineage.

## 8. Telemetry, supervision and operational protection

### 8.1 Trusted telemetry

Measurements originating from SCADA, historians, metering systems and approved sources become useful operational intelligence through controlled source mapping, asset association, units, timestamps, quality, validation and provenance.

**Valid, uncertain, missing and stale are meaningfully different states.** An unavailable measurement must not be represented as an actual zero or as a confirmed safe operating condition.

### 8.2 Monitoring and alarms

Monitoring interprets observations against approved thresholds, operating plans, engineering envelopes and contextual rules. Alarm management governs alarm presentation, prioritization, acknowledgement, shelving, suppression, resolution and performance review, drawing on applicable industrial principles such as ISA-18.2.

The monitoring domain determines what condition is observed; the alarm domain owns how the alert is governed and handled.

### 8.3 Leak detection and incident management

Potential leaks and abnormal events require contextual evidence, correlation, classification, localization where justified, confidence/uncertainty, escalation and traceability. Relevant methods may include flow/pressure balance, statistical analysis and model-supported interpretation, each within its justified applicability.

Incident management governs triage, investigation, root-cause analysis, decisions, corrective actions, escalation and closure. The platform must distinguish a suspected condition, an investigated conclusion and a physically verified result.

### 8.4 Integrity, assets and HSE

Integrity addresses the pipeline's condition and its safe operating envelope. Asset management governs maintainability and maintenance execution. HSE provides safety and compliance evidence. These disciplines work from shared network context while retaining their respective authorities and responsibilities.

## 9. Planning, workflow and enterprise coordination

### 9.1 Plans and nominations

Hidra connects versioned operational expectations with trustworthy actual measurements. Capacity assumptions, operating targets, nominations, dispatch decisions and deviations are presented with the physical network and applicable time context.

### 9.2 Workflow and accountable decisions

Workflow coordinates human tasks, approvals, rejections, delegations, escalations and reasons. The business domain continues to own the underlying fact or engineering revision. The platform must make the difference between a pending request, a granted decision and a verified operational outcome unmistakable.

### 9.3 Documents and controlled exchange

Engineering drawings, procedures, certificates, inspection evidence and other controlled documents can be associated with their physical and business context. Integration supports governed interchange with industrial and enterprise systems through known mappings, retries, failure handling and audit trails.

A stored file is not automatically an accepted engineering dataset. Structured imports and exports should preserve meaning, validation, units, source references, ownership and transactional consistency.

### 9.4 Notifications and collaboration

Notifications help deliver alarms, assignments, decisions and time-sensitive operational messages through appropriate channels, while distinguishing message delivery from recipient acknowledgement and from completion of the underlying business action.

## 10. Hidra Simulation Core and Digital Twin

### 10.1 Calculation as a platform capability

**Hidra Simulation Core** expresses the ambition for a Hidra-owned, explainable engineering calculation capability. Its purpose is to reason over connected physical networks, fluid properties, equipment behavior, operating constraints and boundary conditions.

Commercial engineering software can serve as a reference for benchmarking and validation, without being the product's indispensable runtime calculation engine.

### 10.2 Engineering questions

Simulation and engineering analysis should help users explore questions such as:

- How do pressure, flow and operating conditions change across a connected pipeline system?
- What capacity or constraint becomes relevant under a different demand or supply scenario?
- How do station configurations, equipment performance and pipeline characteristics affect behavior?
- What assumptions, boundary conditions and uncertainties govern a predicted result?
- Which candidate operational responses deserve expert review, and what trade-offs do they imply?

Methods should be appropriate to product family and problem type, with clear distinction between steady-state and transient behavior, model fidelity and applicable physical regimes.

### 10.3 Digital Twin as an evidence relationship

A meaningful Digital Twin is more than a 3D map or a numerical model. It joins a governed physical-network definition, accepted engineering properties, trusted operational observations, reproducible calculations and a time-aware record of decisions and outcomes.

Its value lies in **explainable comparison between the modeled system and the observed system**. Uncertain, missing, simplified or unqualified data must remain visible rather than being hidden behind a convincing visualization.

### 10.4 Human-governed recommendations

Engineering recommendations present options, assumptions, constraints, anticipated effects and uncertainty for responsible review. They must not be confused with instructions already executed in the field. Hidra never derives permission for automatic actuation merely from a favorable simulation result.

## 11. Analytics, risk and intelligent assistance

Hidra brings together trusted history, monitoring evidence, asset condition, integrity assessments, incident context, custody measurements and simulation output to support:

- Operational and management KPIs with clear definitions and lineage.
- Comparison of observed performance with plans and expectations.
- Cross-disciplinary risk assessments and prioritized attention.
- Historical trend analysis, anomaly investigation and explanation.
- Reproducible reporting for operations, engineering, management and audit.
- Contextual assistance that makes relevant evidence easier to discover and evaluate.

AI-assisted analysis, where appropriate, follows the same principles as the rest of the product: explicit evidence, transparent limitations, authorized data access, reproducibility and human accountability. A persuasive automated explanation is not itself authoritative operational truth.

Environmental intelligence, emissions-related indicators and industrial cybersecurity posture are also part of Hidra's holistic intelligence ambition, subject to clear domain ownership and meaningful source evidence.

## 12. Data architecture and trust

Hidra distinguishes several forms of information, each with different responsibilities:

| Information family | Essential meaning |
|---|---|
| Authoritative business records | Identity, organizational structure, plans, assets, incidents, engineering definitions and domain decisions. |
| Operational observations | Timestamped source readings with units, quality, validity and asset context. |
| Engineering revision evidence | Accepted physical/configuration/product definitions, provenance, relationships and historical identity. |
| Audit and decision evidence | Accountable actor, operation, time, reason, affected subject, relevant revision and outcome. |
| Integration and delivery evidence | Source and destination systems, exchange state, failures, retries and delivery acknowledgements. |
| Analytical projections | Derived views, KPIs, reports and insights that can be reconstructed from authoritative sources. |

An explicit distinction between **source time, effective time, observation time, decision time and recording time** helps avoid misleading interpretations of history.

Sensitive information deserves proportional minimization, access restriction, secure retention and auditable handling. Data lifecycle and historical reconstruction must remain compatible with applicable Sonatrach policies and legal obligations.

## 13. Security, governance and industrial boundaries

Hidra's security vision encompasses authenticated identity, least privilege, organizational and company scope, role- and policy-based authority, segregation of duties, auditable decisions, controlled integration, traceable changes and safe handling of exceptional conditions.

The platform supports an industrial environment but does not assume ownership of physical control. SCADA, PLC/RTU, safety-instrumented systems, industrial networks and emergency shutdown functions have their own designated authorities and safety boundaries.

Cybersecurity visibility and integration governance should strengthen—not bypass—industrial segmentation, identity management, secure transport, monitoring and organizational accountability.

The interface should make access-denied states, conflicting changes, stale data, unavailable evidence and operational uncertainty understandable without exposing sensitive internal detail.

## 14. Measures of product value

The product's success can be judged through enduring outcomes:

1. **Network intelligibility:** people can identify an asset, its connections and relevant approved engineering configuration.
2. **Measurement confidence:** operational readings show their source, time, units, quality and physical context.
3. **Reproducibility:** an engineering conclusion can be reconstructed from its exact inputs, methods and assumptions.
4. **Accountability:** meaningful decisions reveal who made them, with what authority and supporting evidence.
5. **Operational continuity:** deviations, alarms, incidents and corrective actions remain connected across responsible disciplines.
6. **Maintenance relevance:** integrity and observed condition inform prioritized inspection and maintenance action.
7. **Custody defensibility:** product/measurement assumptions and reconciliations can be explained and reviewed.
8. **Risk awareness:** operational and strategic risks are visible with meaningful confidence and consequence context.
9. **Explainable intelligence:** derived insight reveals source evidence, uncertainty and methodological limitations.
10. **Safe boundaries:** software recommendations are not mistaken for industrial-control commands or observed effects.
11. **Operator usability:** information and decisions remain accessible and understandable during routine and exceptional operation.
12. **Shared understanding:** engineers, operators, supervisors and management interpret key objects and measurements consistently.

## 15. What Hidra is not

To retain strategic clarity, Hidra is not intended to be:

- A replacement for SCADA or safety-instrumented control.
- An autonomous valve, compressor or pump actuation system.
- A universal historian or generic enterprise data lake.
- A generic ERP, EAM or business-process-management suite.
- A black-box AI decision-maker.
- A decorative map or dashboard without authoritative operational meaning.
- A thin runtime wrapper around third-party engineering calculation software.
- A system that treats a model prediction, an operator request and a measured outcome as equivalent facts.

Hidra may interoperate with specialized systems without assuming their safety-critical or business authority.

## 16. The enduring guiding question

> **Does this strengthen Sonatrach's ability to understand, operate, protect and improve hydrocarbon transportation through trusted, validated, auditable and risk-aware intelligence?**

That question expresses the identity of Hidra as a product: a shared, explainable system of operational understanding in which engineering evidence and human accountability are as important as information display.
