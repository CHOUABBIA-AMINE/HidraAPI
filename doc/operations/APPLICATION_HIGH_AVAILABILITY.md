# HidraAPI Application High Availability Model

## Status

**APPROVED TARGET — HPR-P1-002**

Owner approval date: **2026-10-05**

Execution base: `ce662955a9615443be094859a68d10c029e94324`

Pre-task exact-head verification: GitHub Actions run #531 / run id `37330294217` completed **SUCCESS**.

This document defines the approved application-runtime high-availability model without choosing a hosting, orchestration, load-balancer, broker, cache, cloud, or virtualization product.

## 1. Scope

This decision covers HidraAPI application-process redundancy, request distribution, health-based traffic handling, node-local state constraints, realtime constraints, failure behavior, and planned maintenance behavior.

It does not define PostgreSQL HA, disaster recovery, deployment automation, or a specific infrastructure product. Those remain governed by later HPR tasks.

## 2. Current Repository Baseline

**CURRENT — repository verified**

HidraAPI currently provides:

- stateless Spring Security HTTP session policy;
- externally configurable application and management ports;
- graceful server shutdown;
- Actuator readiness and liveness probes;
- PostgreSQL-backed authoritative persistence;
- process-local Spring `simple` cache;
- an in-process Spring STOMP simple broker;
- application-local asynchronous notification executor configuration;
- product-neutral runtime configuration through environment/externalized properties.

These facts are the starting point. They do not themselves prove multi-node production HA.

## 3. Approved Application HA Topology

**APPROVED TARGET**

Production must run a minimum of **two simultaneously active HidraAPI application nodes**.

The nodes are peers from the HTTP service perspective. No node is designated as an application primary for ordinary REST/API traffic.

Traffic must be distributed through a **managed load-distribution layer**. The specific implementation product is intentionally not selected in HPR-P1-002.

```text
Clients
   |
   v
Managed load-distribution boundary
   |                 |
   v                 v
HidraAPI node A   HidraAPI node B
   |                 |
   +--------+--------+
            |
            v
     Shared dependencies
     including PostgreSQL
```

The diagram does not imply a particular appliance, reverse proxy, ingress controller, orchestrator, VM platform, container platform, or cloud service.

## 4. Request and Session Model

**APPROVED TARGET**

Ordinary REST/API traffic must remain stateless at the HTTP session layer.

REST traffic must **not require session affinity/sticky sessions** for correctness.

Authentication and authorization must continue to rely on the established bearer/JWT and application security model rather than server-local HTTP session state.

A node may serve any eligible REST request if it is ready and can access the required shared dependencies.

## 5. Readiness, Liveness, and Traffic Admission

### 5.1 Readiness

**APPROVED TARGET**

Readiness is the traffic-admission signal.

A HidraAPI node may receive new production traffic only while its readiness state is healthy.

If readiness becomes unhealthy, the load-distribution layer must stop sending new traffic to that node.

Readiness must reflect the application's ability to serve requests and must remain distinct from simple process existence.

### 5.2 Liveness

**APPROVED TARGET**

Liveness is the process-recovery signal.

An unhealthy liveness state indicates that the runtime/process should be restarted or otherwise recovered according to the selected infrastructure platform.

Liveness must not be used as a substitute for dependency-sensitive readiness.

### 5.3 Current health surface

**CURRENT — repository verified**

The application already enables Actuator readiness and liveness probes.

**NOT ESTABLISHED**

The exact infrastructure mechanism that consumes these probes is not yet selected.

## 6. Node Failure Behavior

**APPROVED TARGET**

If one application node fails:

1. the failed/unready node must stop receiving new traffic;
2. surviving ready nodes continue serving eligible traffic;
3. no REST correctness dependency may require recovery of the failed node's memory;
4. requests already executing on the failed node may fail; no global automatic retry policy is invented here;
5. database or external-dependency failure remains governed by that dependency's own availability model.

Application-node failure does not authorize bypassing security, validation, transaction, or audit controls.

## 7. Node-Local Cache Constraint

**CURRENT — repository verified**

The configured cache type is Spring `simple`, which is process-local.

**APPROVED TARGET**

Node-local cache contents must not be treated as authoritative shared state.

A cache entry may remain node-local only when stale/divergent values cannot change business correctness, authorization truth, audit truth, or safety-relevant operational behavior.

If a future cache becomes correctness-sensitive across nodes, an approved cross-node cache/state strategy must be implemented and verified before relying on it.

No distributed-cache product is selected by this decision.

## 8. Realtime WebSocket/STOMP Constraint

**CURRENT — repository verified**

`HidraRealtimeConfiguration` uses Spring's in-process STOMP simple broker.

**APPROVED TARGET**

The current simple broker must not be represented as clustered realtime HA.

Before HidraAPI claims multi-node realtime availability, the realtime path must use an approved external/shared broker or equivalent mechanism providing explicit cross-node message distribution and connection behavior.

Until that is implemented and verified, one of the following operational constraints is required:

- realtime traffic is explicitly confined to a single active realtime node while REST remains multi-node; or
- realtime capability is disabled for the multi-node production deployment.

The selected constraint and eventual broker technology belong to implementation/deployment decisions; HPR-P1-002 selects neither product nor protocol extension.

REST session affinity must not be introduced merely to compensate for the current in-process broker.

## 9. Asynchronous and Background Work

**CURRENT — repository verified**

Notification asynchronous push uses process-local executor configuration. The platform outbox is not established as enabled in production.

**APPROVED TARGET**

Any background work that may execute on multiple application nodes must have explicit duplicate-execution and ownership semantics before being considered HA-safe.

Node-local executors must not silently become a distributed job scheduler.

Where duplicate execution would be unsafe, later implementation must introduce an approved coordination/persistence mechanism before multi-node execution is enabled.

No scheduler, queue, broker, or leader-election product is selected here.

## 10. Planned Maintenance and Rolling Change

**APPROVED TARGET**

Planned removal of an application node must follow:

1. make the node unready for new traffic;
2. stop routing new requests to the node;
3. permit in-flight work to drain within the selected operational timeout;
4. use graceful application shutdown;
5. perform maintenance/change;
6. restart and verify liveness;
7. verify readiness and required dependency connectivity;
8. restore traffic only after readiness succeeds.

The exact drain timeout and deployment mechanism remain implementation parameters for the eventual runtime platform and deployment runbook.

## 11. Capacity and Degraded Mode

**APPROVED TARGET**

The architecture requires at least two active nodes, but HPR-P1-002 does not assert that one surviving node can handle 100% of production peak load.

Capacity sufficient for defined degraded operation must be established through workload/capacity evidence before production readiness is claimed.

No request-rate, CPU, memory, concurrency, or autoscaling values are invented here.

## 12. Ownership

**APPROVED TARGET**

Platform/Operations owns the runtime load-distribution and node lifecycle mechanism.

Application Engineering owns HidraAPI readiness/liveness correctness, graceful-shutdown behavior, and application-side removal of unsafe node-local correctness assumptions.

Security retains authority over security-boundary requirements and emergency containment under the approved incident-governance model.

Specific product teams and personal on-call contacts are not invented here.

## 13. Acceptance Criteria

Application HA may be claimed only when evidence demonstrates at minimum:

- two or more HidraAPI nodes can run simultaneously;
- requests are distributed only to ready nodes;
- ordinary REST requests do not rely on sticky sessions;
- removal/failure of one node stops new traffic to it;
- surviving node(s) continue serving within approved capacity;
- graceful drain/shutdown works for planned maintenance;
- correctness does not depend on process-local cache contents;
- realtime is explicitly constrained or uses a verified shared/cluster-capable distribution mechanism;
- background work has safe multi-node execution semantics;
- failover/maintenance behavior is exercised and recorded under later P1 survivability verification.

## 14. Explicitly Not Selected

HPR-P1-002 does **not** select Kubernetes, OpenShift, Docker, a VM platform, a cloud provider, a load balancer product, reverse proxy, ingress controller, distributed-cache product, broker product, scheduler/leader-election product, or autoscaling rules.

Those choices require later approved deployment/infrastructure decisions.

## 15. Production Readiness

The application HA **target model is approved**, but implementation and measured failover evidence are not yet established.

Therefore production readiness remains **NOT ESTABLISHED**.

HPR-P1-006 and HPR-P1-012 must later integrate and verify the application and database HA models before survivability can be closed.
