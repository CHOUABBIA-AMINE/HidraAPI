# ADR-0006 — Remove disconnected domain-event/outbox scaffolding until a real integration requirement exists

**Status:** Accepted (HRA-030; recorded 2026-09-28).

## Context

The forensic Java-source audit and the attached source snapshot (SHA-256 `f91526a4c802cd4ae839afe22adbd5f82f83b7db8808a0d41c3cd7b2c671b338`) show two incompatible event abstractions:

- the kernel `DomainEvent` requires `DomainEventId eventId()`, `Instant occurredAt()`, and `String eventType()`;
- each of the 24 business-module `*DomainEvent` interfaces declares `String eventId()` and does not extend the kernel contract.

The same snapshot contains 100 concrete module event records, 24 module event-publisher ports, and 24 in-memory publisher implementations. Static reference inspection found no concrete module event record referenced outside its own declaration file and no state-changing application service emitting those records.

The platform outbox is also only a skeleton. `DomainEventSerializer`, `OutboxPublisher`, and `OutboxEventRepositoryPort` exist as contracts, but there is no serializer implementation, no outbox repository adapter, no publisher implementation, and no runtime wiring connecting module events to durable storage or publication.

Some modules also contain business records whose names include `Event` or `OutboxReference` (for example lifecycle/audit history models). Those are persisted business concepts and are not evidence that the generic domain-event/outbox pipeline is operational.

## Decision

Select **HRA Path B**.

HidraAPI will not implement a transactional domain-event/outbox pipeline merely to complete the existing skeleton. HRA-031B will remove the unreferenced module event records, module-specific event interfaces, event-publisher ports, in-memory publisher implementations, and unused platform outbox/messaging contracts after a final repository consumer verification.

Path A is explicitly rejected for the current repository state:

- HRA-031A is skipped;
- HRA-032A is skipped;
- HRA-033A is skipped.

No new module event or outbox abstraction may be added under the current architecture unless a concrete producer/consumer requirement is identified and a new ADR defines delivery semantics, transaction boundaries, serialization/versioning, retry/idempotency, observability, and ownership.

HRA-031B must not delete persisted lifecycle/audit business models solely because their names contain `Event`. It targets only the disconnected generic domain-event/publication scaffolding. The generic kernel event primitives are outside HRA-031B unless separately proven unused and authorized by a later roadmap task such as the static-orphan classification.

## Consequences

The codebase will no longer imply event-driven guarantees that do not exist at runtime. State-changing use cases remain synchronous unless an explicit future integration decision says otherwise.

Removing the skeleton reduces false architecture surface and avoids prematurely standardizing payload/version/delivery semantics without consumers. If durable integration events become necessary later, they must be introduced from an actual use case with end-to-end transactional tests rather than by reviving disconnected placeholders.

## Implementation

HRA-031B implemented this decision on 2026-09-28 after a final live-main consumer sweep. The removal was limited to the disconnected generic event/publication subgraph:

- 100 module event records;
- 24 module-specific event interfaces;
- 24 module event package descriptors;
- 24 module publisher ports;
- 24 in-memory module publisher implementations;
- 23 messaging package descriptors that became empty after publisher removal;
- 9 unused platform outbox/messaging contracts, models, enums and package descriptors.

The notification module's real `AsyncNotificationPushAdapter` remains. Persisted lifecycle/audit models, workflow audit-outbox-reference records, kernel event primitives, platform outbox configuration properties, and all applied Flyway migrations remain untouched.

## Evidence

- Attached source snapshot SHA-256: `f91526a4c802cd4ae839afe22adbd5f82f83b7db8808a0d41c3cd7b2c671b338`.
- Forensic audit finding: module event contracts are incompatible with the kernel `DomainEvent`; no module event emission was found; `DomainEventSerializer`, `OutboxPublisher`, and `OutboxEventRepositoryPort` have no implementations/incoming runtime users.
- Live `main` verification on 2026-09-28 confirmed `EmployeeRegisteredEvent` has no production reference outside its declaration and `OrganizationDomainEventPublisherPort` is referenced only by its in-memory publisher implementation/documentation.

## Source

[Repository remediation roadmap](../roadmap/repository-remediation.md), [forensic baseline](../architecture/forensic-static-audit-baseline.md), [AGENTS.md](../../AGENTS.md).
