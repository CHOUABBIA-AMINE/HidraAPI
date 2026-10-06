# HidraAPI Documentation Governance

## Status

CURRENT — canonical governance index.

## Purpose

This directory defines how HidraAPI documentation becomes canonical, how status and applicability are recorded, and how historical material remains usable without becoming current authority.

## Authority Order

1. `doc/roadmap/ULTIMATE_ROADMAP.md` controls platform-finalization sequencing and HPR task selection.
2. Current executable repository evidence controls implementation claims.
3. Canonical `doc/**` documents describe maintained current, target or deferred state according to their recorded status.
4. Legacy `docs/**` material is evidence/reference only unless reconciled into canonical documentation.

## Governance Controls

- `DOCUMENTATION_STANDARD.md` — required metadata and evidence rules.
- `DOCUMENT_LIFECYCLE.md` — lifecycle, preservation and supersession.
- `DOCUMENT_STATUS_MODEL.md` — allowed status vocabulary.
- `DOCUMENT_REGISTER.md` — canonical domain/status register and later-P2 readiness map.

## Metadata Rule

A canonical document must identify the required metadata directly or inherit it from an explicit containing index/register. The register must never upgrade an unverified implementation claim to CURRENT. Unknown ownership or applicability remains explicit rather than being inferred.

## Legacy Evidence Rule

The `docs/` estate is preserved in place. Canonical indexes may reference it for provenance, audit history, semantic review, ADR history, data-definition history or execution evidence, but legacy status text cannot select roadmap work or override the Ultimate Roadmap.

## Change Discipline

Governance/index work may describe existing canonical documents and register later work as planned. It must not manufacture the architecture, domain, module, API, database, data-governance or testing content assigned to HPR-P2-002..011.
