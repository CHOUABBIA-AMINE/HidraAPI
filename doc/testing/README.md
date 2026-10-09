# HidraAPI Testing and Verification

## Status, applicability and ownership

CURRENT — canonical HPR-P2-011 documentation baseline. Verified source parent
`35d9d949aa773a4d754c22d00181330f579f752b`, on 2026-10-09. Module owners retain responsibility
for their own behavior/contracts; repository governance controls execution and
acceptance. No new named testing authority or production acceptance is invented.
All six documents inherit status, owner authority, source parent/date and precedence
from this index under the [documentation standard](../governance/DOCUMENTATION_STANDARD.md).
Individual TARGET requirements and NOT ESTABLISHED facts remain explicit.

## Canonical set

| Document | Purpose |
|---|---|
| [Test Strategy](TEST_STRATEGY.md) | Actual test populations, commands, prerequisites and evidence limits |
| [Architecture Testing](ARCHITECTURE_TESTING.md) | Enforced boundaries and the specific rule mechanisms |
| [Database Testing](DATABASE_TESTING.md) | Flyway versus targeted fixtures, Spring/JPA transactions, rollback and races |
| [API Testing](API_TESTING.md) | Controller/security scope, runtime contracts and compatibility |
| [Requirements Traceability](REQUIREMENTS_TRACEABILITY.md) | Platform evidence, 24-module navigation and 57 HMR links |

## Evidence and verification point

Current source, configuration, migrations and test assertions precede documentation.
The [roadmap](../roadmap/ULTIMATE_ROADMAP.md) selects execution; the [semantic catalogue](../domain/SEMANTIC_DECISIONS.md)
retains 123 subjects and lasting rules; [reconciliation](../model-remediation/RECONCILIATION.md)
retains the 57 completed HMR obligations. This set supplies evidence navigation,
not another status register. Legacy docs/** remains historical evidence.

Executable baseline `617c2eec812e3a5734957ee9fa0360f6f5613032` has prior
[production CI #604](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37841205677)
PASSED; documentation-only descendants do not constitute fresh full CI. Parent
[documentation CI #121](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37897171261)
PASSED. These results were rechecked for this task. No Maven/runtime, deployment,
source-data import or physical campaign was rerun locally; per-class report/skip
results were not inspected. The new documentation run follows publication.

## Unresolved evidence and TARGET work

Numeric branch/endpoint/performance coverage, universal end-to-end validation and
current deployed-data/physical acceptance are NOT ESTABLISHED by this set. Prior
[P1 exercise](../operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md) keeps its own
deployed SHA, 82-migration applicability and operator-supplied distinctions. The
current source has 139 migration versions; neither fixtures nor CI artifact validators
supply new physical evidence for that schema. HPR-P2-012 documentation drift controls are implemented;
HPR-P2-013 closure remains pending; P3 deferred and P2 open.

## HPR-P2-012 control update

Source parent `508337351eef03b82e2c6078a7c8523013efd063`, 2026-10-09. [Validation guide](../governance/DOCUMENTATION_VALIDATION.md) records the added suites/manifest/gates and verified generated snapshot refresh. Prior HPR-P2-011 test results remain historical; the current Maven clean-verify attempt blocked before compilation and new exact-head runtime CI is pending. No new per-class/physical execution result is invented.
