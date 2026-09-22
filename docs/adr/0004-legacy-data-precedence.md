# ADR-0004 — Scoped HyFlo data precedence and provisional deduplication

**Status:** Scoped owner-stated staging decision (recorded 2026-09-21; no blanket import/security approval).

## Context
HyFlo-Progress provides coded-table interpretation; HyFlo_DB and Segment 2026 contain overlapping but not necessarily identical infrastructure records. Some coded worksheet meanings conflict with the declared dictionary, and sources may include sensitive data.

## Decision
For overlapping **infrastructure** records, prefer Segment 2026 over HyFlo_DB. When a segment code repeats **within Segment 2026**, provisionally keep the first worksheet occurrence, preserving provenance of later entries for engineering review. Keep HyFlo_DB-only records as review candidates. Exclude Fiche Passation.xlsx from this workstream per the project owner's express scope decision. Do not apply Segment 2026 priority to unrelated geography, telemetry, security or other business-reference datasets.

## Consequences
This is a controlled staging/reconciliation rule, **not** proof of physical-asset identity, permission to publish operational records, schema compatibility or authorization to import. Resolve coded-table and topology conflicts; withhold legacy credentials. HDP-004 remains incomplete and G1 is unsigned; do not start HDP-005 based on this ADR.

## Source
[Data roadmap](../roadmap/data-provisioning.md), [source classification](../data-provisioning/source-classification.md), [HDP-004 issue #125](https://github.com/CHOUABBIA-AMINE/HidraAPI/issues/125). Original workbook owner selections were given in the project conversation; no row-level data is reproduced.
