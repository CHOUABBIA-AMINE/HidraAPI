#!/usr/bin/env bash
set -euo pipefail
python3 - <<'PY'
from pathlib import Path
p = Path('docs/roadmap/authentication.md')
s = p.read_text()
repls = [
('| Status | Active — AUTH-030 final authentication gap-closure verification in progress |',
 '| Status | Completed — AUTH-030 authentication gap closure verified |'),
('| AUTH-030 | `docs(authentication): finalize authentication gap closure checklist` | Record executable evidence and remaining optional cleanup | In Progress — executable closure evidence recorded; final `mvn -q clean verify` pending |',
 '| AUTH-030 | `docs(authentication): finalize authentication gap closure checklist` | Record executable evidence and remaining optional cleanup | Completed — closure checklist fully evidenced; PR CI run 307 passed repository and acceptance `mvn -q clean verify` plus OpenAPI publication/artifact upload |'),
('In Progress — executable evidence for every required authentication gap-closure criterion is recorded below. Final closure remains pending until this AUTH-030 documentation state itself passes `mvn -q clean verify` in repository CI.',
 'Completed — executable evidence for every required authentication gap-closure criterion is recorded below, and AUTH-030 PR CI run 307 passed the required final repository and acceptance `mvn -q clean verify` gates.'),
('| `mvn -q clean verify` passes for final closure | AUTH-030 CI | PENDING — must pass on the final closure documentation state before AUTH-030 is marked Completed |',
 '| `mvn -q clean verify` passes for final closure | AUTH-030 PR CI run 307 on closure-documentation commit `ea29d1747f6f3dd2a5b0cb89cd0a12fa4003819e` | PASS — repository compile/tests/full `clean verify`, acceptance compile/tests/`clean verify`, deterministic OpenAPI publication, and artifact upload all succeeded |'),
('Items 1-15 are backed by AUTH-002 through AUTH-029 implementation/test evidence recorded in this roadmap.\nItem 16 remains the AUTH-030 final clean verification gate and is intentionally not marked complete yet.\nNo mandatory authentication gap remains open based on the recorded executable evidence; section 20 items remain explicit optional/non-goal work.',
 'Items 1-15 are backed by AUTH-002 through AUTH-029 implementation/test evidence recorded in this roadmap.\nItem 16 is backed by AUTH-030 PR CI run 307, which passed the final repository and acceptance `mvn -q clean verify` gates.\nAll 16 definition-of-done items are therefore evidenced. No mandatory authentication gap remains open; section 20 items remain explicit optional/non-goal work requiring separate architecture decisions/roadmaps.'),
('PENDING — execute repository CI for the AUTH-030 documentation state before final closure.',
 'PASS — AUTH-030 PR CI run 307 completed repository compile/tests/full `mvn -q clean verify`, acceptance compile/tests/`mvn -q clean verify`, deterministic OpenAPI publication, and artifact upload successfully for closure-documentation commit `ea29d1747f6f3dd2a5b0cb89cd0a12fa4003819e`.')
]
for old,new in repls:
    if old not in s:
        raise SystemExit(f'anchor not found: {old[:80]}')
    s = s.replace(old,new,1)
old = '''## 22. Next task\n\nExecute only:\n\n```text\nAUTH-030 — docs(authentication): finalize authentication gap closure checklist\n```\n\nAUTH-029 now protects the authentication architecture/secret boundaries with executable tests and CI evidence. Do not execute work beyond AUTH-030 as part of the final closure task.'''
new = '''## 22. Roadmap completion\n\n```text\nAuthentication gap closure is complete through AUTH-030.\nNo further AUTH-* task is defined by this roadmap.\n```\n\nAll mandatory closure criteria and the final `mvn -q clean verify` gate are evidenced above. Any future work listed in section 20, or any new authentication capability, requires a separate approved roadmap/architecture decision rather than continuation beyond AUTH-030.'''
if old not in s:
    raise SystemExit('next-task anchor not found')
s = s.replace(old,new,1)
p.write_text(s)
PY

git config user.name "Aminosoft"
git config user.email "147345045+CHOUABBIA-AMINE@users.noreply.github.com"
git add docs/roadmap/authentication.md
git commit -m "docs(authentication): finalize authentication gap closure checklist"
git push origin HEAD

git rm .github/auth-030-finalize.sh .github/workflows/auth-030-finalize.yml
git commit -m "docs(authentication): finalize authentication gap closure checklist"
git push origin HEAD
