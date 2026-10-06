#!/usr/bin/env python3
from pathlib import Path

roadmap = Path("doc/roadmap/ULTIMATE_ROADMAP.md").read_text(encoding="utf-8")
evidence = Path("doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md").read_text(encoding="utf-8")

required_roadmap = [
    "| HPR-P1-012 | COMPLETED — P1 CLOSED.",
    "| HPR-P1-029 | COMPLETED — production-equivalent evidence campaign executed 2026-10-06",
    "Final disposition: **PASS — 12 / 12 P1 checks VERIFIED**.",
    "| Production readiness | ESTABLISHED FOR P1",
    "| HPR-P1-015 | COMPLETED —",
    "| HPR-P1-016 | COMPLETED —",
    "| HPR-P1-017 | COMPLETED —",
    "| HPR-P1-018 | COMPLETED —",
    "| HPR-P1-019 | COMPLETED —",
    "| HPR-P1-020 | COMPLETED —",
]
required_evidence = [
    "**COMPLETED — 2026-10-06**",
    "RPO: **15 seconds**",
    "RTO: **37 minutes**",
    "each application node was failed and rejoined sequentially",
    "independent repo2 restore",
]

missing = [x for x in required_roadmap if x not in roadmap]
missing += [f"evidence:{x}" for x in required_evidence if x not in evidence]
if missing:
    raise SystemExit("P1 closure evidence validation failed; missing: " + ", ".join(missing))

print("P1 closure evidence validation passed.")
