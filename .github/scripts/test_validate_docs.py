#!/usr/bin/env python3
"""Validate real miniature estates, broken navigation and governance mutations."""
import copy
import hashlib
import sys
import tempfile
import unittest
from pathlib import Path
sys.dont_write_bytecode = True
import validate_docs as validator


class DocumentationTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.files = ['doc/README.md', 'doc/governance/DOCUMENT_REGISTER.md',
                      'doc/roadmap/ULTIMATE_ROADMAP.md', 'doc/modules/README.md',
                      'doc/modules/demo.md', 'doc/old.md']
        for rel in self.files:
            path = self.root / rel
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text('# Fixture\n\n## Status\n\nCURRENT — fixture\n\nOwner authority; source baseline; TARGET; unknown; verified 2026-10-09.\n')
        (self.root / 'src/modules/demo').mkdir(parents=True)
        self.append('doc/modules/README.md', '\n[demo](demo.md)\n')
        self.append('doc/README.md', '\n[Modules](modules/README.md)\n')
        self.append('doc/governance/DOCUMENT_REGISTER.md', '\n| `doc/modules/**` | CURRENT | fixture |\n| Documentation CI drift controls | CURRENT | fixture |\n')
        self.write('doc/old.md', '# Old\n\n## Status\n\n**APPROVED TARGET — P1 fixture**\n')
        self.write_roadmap()
        self.manifest = {'documents': {p: {'status': 'CURRENT', 'metadata_from': ['doc/README.md']} for p in self.files},
                         'legacy_statuses': {'doc/old.md': {'line': 'APPROVED TARGET — P1 fixture', 'rationale': 'Preserved fixture'}},
                         'metadata_signals': {'owner': r'owner|authority', 'source': 'source|baseline', 'verification': 'verified'},
                         'indexes': {'doc/README.md': ['doc/modules/README.md']},
                         'modules': ['demo'], 'module_source': 'src/modules',
                         'register': 'doc/governance/DOCUMENT_REGISTER.md', 'roadmap': 'doc/roadmap/ULTIMATE_ROADMAP.md',
                         'register_areas': {'doc/modules/**': 'CURRENT'}, 'current_indexes': ['doc/README.md', 'doc/modules/README.md']}
        self.manifest['documents']['doc/old.md']['status'] = 'LEGACY-P1'
        self.manifest['documents']['doc/roadmap/ULTIMATE_ROADMAP.md']['status'] = 'ACTIVE'

    def write(self, rel, text):
        (self.root / rel).write_text(text)

    def append(self, rel, text):
        path = self.root / rel
        path.write_text(path.read_text() + text)

    def write_roadmap(self, closed=False, missing=None):
        text = '# Roadmap\n\n## 1. Control Status\n\n| Status | ACTIVE |\n\n## 2. Registry\n\n### Phase P2 — Test\n\n'
        for i in range(1, 14):
            status = 'COMPLETED' if closed or i < 13 else 'PENDING'
            if i == missing:
                status = 'PENDING'
            text += f'| HPR-P2-{i:03} | {status} | D | Doc | E | `message` | dep |\n'
        text += '\n### Phase P3 — Test\n\n## 6. Immediate Next Execution\n\n`HPR-P2-013 — closure`\n\n## 7. History\n'
        self.write('doc/roadmap/ULTIMATE_ROADMAP.md', text)

    def check(self):
        return validator.validate(self.root, self.manifest)

    def test_valid_estate_and_deliberate_closure(self):
        self.assertEqual(self.check()['modules'], 1)
        self.write_roadmap(closed=True)
        self.check()

    def test_inventory_drift_empty_utf8_and_conflicts(self):
        path = self.root / 'doc/modules/demo.md'
        original = path.read_bytes()
        for payload in (b'', b'\xff', b'<<<<<<< ours\n'):
            path.write_bytes(payload)
            with self.subTest(payload=payload), self.assertRaises((ValueError, UnicodeError)):
                self.check()
        path.write_bytes(original)
        self.write('doc/extra.md', '# Extra')
        with self.assertRaisesRegex(ValueError, 'inventory'):
            self.check()

    def test_link_missing_case_escape_and_anchor(self):
        for dest in ('missing.md', 'Modules/demo.md', '../../outside.md', 'modules/demo.md#missing'):
            original = (self.root / 'doc/README.md').read_text()
            self.append('doc/README.md', f'\n[bad]({dest})\n')
            with self.subTest(dest=dest), self.assertRaises(ValueError):
                self.check()
            self.write('doc/README.md', original)

    def test_percent_encoding_parentheses_and_explicit_duplicate_anchors(self):
        path = self.root / 'doc/a (b).md'
        path.write_text('# **One**\n# One\n<a id="explicit"></a>\n')
        text = '[one](a%20%28b%29.md#one-1)\n[two](a%20%28b%29.md#explicit)\n[three](a (b).md "Title")'
        self.assertEqual(validator.validate_links(self.root, self.root / 'doc/README.md', text), 3)
        self.assertEqual(validator.anchors('# One\n# One\n# One'), {'one', 'one-1', 'one-2'})
        self.assertEqual(validator.anchors('# One\n# One-1\n# One'), {'one', 'one-1', 'one-2'})
        self.assertEqual(validator.anchors('# [Été](https://example.test)\n`<a id="ignored">`'), {'été'})

    def test_reference_links_fences_inline_code_and_external_urls(self):
        text = '[one][ref]\n[ref]: modules/demo.md\n`[ignored](missing.md)`\n```md\n[ignored](missing.md)\n```\n[mail](mailto:example@example.test)\n[web](https://example.test)'
        self.assertEqual(validator.validate_links(self.root, self.root / 'doc/README.md', text), 1)
        for text in ('[x][missing]', '[r]: modules/demo.md\n[R]: modules/demo.md'):
            with self.subTest(text=text), self.assertRaises(ValueError):
                validator.links(text)

    def test_generated_dictionary_constraint_regex_is_not_a_reference_link(self):
        dictionary = self.root / 'doc/database/DATA_DICTIONARY.md'
        dictionary.parent.mkdir(parents=True, exist_ok=True)
        sql = ("- `hidra_identity_org_commit_key_head_key_id_check`: "
               "CHECK (key_id::text ~ '^[a-zA-Z0-9][a-zA-Z0-9._-]{0,63}$'::text)\n"
               "  - Function evidence: CHECK '^[1-9][0-9]{0,18}$'\n")
        actual = sql + "[reviewed owner](../modules/demo.md)\n"
        self.assertEqual(validator.validate_links(self.root, dictionary, actual), 1)
        # An ordinary document must still reject the same false-link-shaped SQL.
        with self.assertRaisesRegex(ValueError, 'Unresolved reference link'):
            validator.validate_links(self.root, self.root / 'doc/README.md', sql)
        # Evidence links stay checked, even in the narrowly exempt dictionary.
        with self.assertRaisesRegex(ValueError, 'Missing/case-mismatched target'):
            validator.validate_links(self.root, dictionary, actual + "[missing](missing.md)\n")
        with self.assertRaisesRegex(ValueError, 'Unresolved reference link'):
            validator.validate_links(self.root, dictionary, actual + "[broken][missing]\n")

    def test_generated_physical_sql_evidence_is_not_markdown_reference(self):
        # A canonical source-evidence line is rendered SQL, including regex
        # character classes; they are not Markdown reference links.
        text = (
            "  - Function evidence: CREATE FUNCTION f() AS $function$<br>"
            "IF v !~ '^[1-9][0-9]{0,18}$' THEN RETURN false; END;<br>$function$<br>\n"
            "[real](modules/demo.md)\n"
        )
        self.assertEqual(
            validator.validate_links(self.root, self.root / 'doc/README.md', text), 1
        )
        # A genuine missing Markdown reference remains a hard failure next
        # to the identical opaque SQL line. No generic reference bypass.
        with self.assertRaisesRegex(ValueError, 'Unresolved reference link'):
            validator.links(text + "[broken][missing]\n")
        with self.assertRaisesRegex(ValueError, 'Unresolved reference link'):
            validator.links("[1-9][0-9]\n")
        # Only the precise canonical evidence prefix is exempt.
        with self.assertRaisesRegex(ValueError, 'Unresolved reference link'):
            validator.links("  - Other evidence: [1-9][0-9]\n")

    def test_unknown_status_and_path_specific_historical_exception(self):
        self.append('doc/old.md', '\nHistorical provenance stays intact.\n')
        self.check()
        self.write('doc/modules/demo.md', '# Fixture\n\n## Status\n\nMAGIC — invalid\n')
        with self.assertRaisesRegex(ValueError, 'status'):
            self.check()
        self.write('doc/modules/demo.md', '# Fixture\n\n## Status\n\nCURRENT — fixture\n')
        self.write('doc/old.md', '# Old\n\n## Status\n\nAPPROVED TARGET — changed\n')
        with self.assertRaisesRegex(ValueError, 'historical status'):
            self.check()

    def test_missing_metadata_inheritance(self):
        self.manifest['documents']['doc/old.md']['metadata_from'] = ['missing.md']
        with self.assertRaisesRegex(ValueError, 'metadata inheritance'):
            self.check()

    def test_index_and_module_drift(self):
        self.write('doc/modules/README.md', '# Modules\n\n## Status\n\nCURRENT\n')
        with self.assertRaisesRegex(ValueError, 'module index'):
            self.check()
        self.append('doc/modules/README.md', '[demo](demo.md)\n[demo](demo.md)')
        with self.assertRaisesRegex(ValueError, 'duplicate module index'):
            self.check()
        self.write('doc/modules/README.md', '# Modules\n\n## Status\n\nCURRENT\n[demo](demo.md)')
        (self.root / 'src/modules/extra').mkdir()
        with self.assertRaisesRegex(ValueError, 'module registry'):
            self.check()

    def test_register_status_and_required_navigation(self):
        p = self.root / 'doc/governance/DOCUMENT_REGISTER.md'
        p.write_text(p.read_text().replace('| `doc/modules/**` | CURRENT', '| `doc/modules/**` | TARGET'))
        with self.assertRaisesRegex(ValueError, 'register area'):
            self.check()
        p.write_text(p.read_text().replace('| `doc/modules/**` | TARGET', '| `doc/modules/**` | CURRENT'))
        self.write('doc/README.md', '# Index\n\n## Status\n\nCURRENT\nOwner source baseline TARGET unknown verified.\n')
        with self.assertRaisesRegex(ValueError, 'navigation'):
            self.check()

    def test_duplicate_registry_next_and_premature_closure(self):
        p = self.root / 'doc/roadmap/ULTIMATE_ROADMAP.md'
        original = p.read_text()
        p.write_text(original.replace('### Phase P3', '| HPR-P2-001 | COMPLETED | D | Doc | E | m | dep |\n### Phase P3'))
        with self.assertRaisesRegex(ValueError, 'duplicate'):
            self.check()
        p.write_text(original.replace('`HPR-P2-013 — closure`', '`HPR-P2-012 — wrong`'))
        with self.assertRaisesRegex(ValueError, 'Immediate next'):
            self.check()
        self.write_roadmap(closed=True, missing=11)
        with self.assertRaisesRegex(ValueError, 'closure'):
            self.check()


class TaskRegisterTests(unittest.TestCase):
    def setUp(self):
        self.start = '<!-- task-register:payload:start -->\n'
        self.end = '<!-- task-register:payload:end -->\n'
        self.config = {'index': 'index.md', 'task_count': 3,
                       'phases': {'HPS-001': 'TASKS_HPS-001.md', 'HPS-002': 'TASKS_HPS-002.md'},
                       'tables': ['roadmap.md', 'phases.md'],
                       'archives': {'archive.md': hashlib.sha256(b'original history\n').hexdigest()}}
        self.texts = {'index.md': '', 'roadmap.md': '', 'phases.md': '',
                      'TASKS_HPS-001.md': self.start, 'TASKS_HPS-002.md': self.start,
                      'archive.md': self.start + 'original history\n' + self.end}
        for i in range(1, 4):
            phase = 'HPS-001' if i < 3 else 'HPS-002'
            task, anchor, path = f'HAPI-{i:03}', f'hapi-{i:03}', f'TASKS_{phase}.md'
            self.texts[path] += (f'<a id="{anchor}"></a>\n## {task} — Fixture\n\n'
                                 f'Phase: {phase} — Fixture\nImplementation Status: PARTIAL\nPlanning Status: BLOCKED\n\n')
            self.texts['index.md'] += f'<a id="{anchor}"></a>\n[{task}]({path}#{anchor})\n\n'
            for table in self.config['tables']:
                self.texts[table] += f'| {phase} | [{task}]({path}#{anchor}) | PARTIAL | BLOCKED |\n'
        for path in self.config['phases'].values():
            self.texts[path] += self.end

    def check(self):
        return validator.validate_task_register(self.texts, self.config)

    def test_split_registry_accepts_evidence_and_synchronized_status_updates(self):
        self.assertEqual(self.check(), {'tasks': 3, 'phases': 2})
        path = 'TASKS_HPS-001.md'
        self.texts[path] = self.texts[path].replace(self.end, 'New owner evidence.\n' + self.end)
        self.check()
        for path in ['TASKS_HPS-001.md', 'TASKS_HPS-002.md', 'roadmap.md', 'phases.md']:
            self.texts[path] = self.texts[path].replace('PARTIAL', 'VERIFIED').replace('BLOCKED', 'COMPLETED')
        self.check()

    def test_missing_duplicate_and_wrong_phase_task_rejected(self):
        path, original = 'TASKS_HPS-002.md', self.texts['TASKS_HPS-002.md']
        for changed in (self.start + self.end, original.replace('003', '001'),
                        original.replace('Phase: HPS-002', 'Phase: HPS-001')):
            with self.subTest(changed=changed), self.assertRaisesRegex(ValueError, 'Task register'):
                self.texts[path] = changed
                self.check()
        self.texts[path] = original

    def test_legacy_anchor_directory_missing_duplicate_and_wrong_target_rejected(self):
        original = self.texts['index.md']
        for changed in ('', original + original, original.replace('hapi-001)', 'hapi-003)', 1)):
            with self.subTest(changed=changed), self.assertRaisesRegex(ValueError, 'compatibility'):
                self.texts['index.md'] = changed
                self.check()

    def test_table_target_status_phase_and_membership_drift_rejected(self):
        original = self.texts['roadmap.md']
        for changed in (original.replace('PARTIAL', 'VERIFIED', 1),
                        original.replace('TASKS_HPS-001.md', 'TASKS_HPS-002.md', 1),
                        original.replace('| HPS-001 |', '| HPS-002 |', 1),
                        original + original, ''):
            with self.subTest(changed=changed), self.assertRaisesRegex(ValueError, 'table'):
                self.texts['roadmap.md'] = changed
                self.check()

    def test_immutable_history_and_payload_boundary_mutation_rejected(self):
        original = self.texts['archive.md']
        for changed in (original.replace('original', 'rewritten'), original + self.end, 'no boundaries'):
            with self.subTest(changed=changed), self.assertRaisesRegex(ValueError, 'archive|boundaries'):
                self.texts['archive.md'] = changed
                self.check()

    def test_index_and_phase_size_regression_rejected(self):
        original = self.texts['index.md']
        self.texts['index.md'] += 'x' * (256 * 1024)
        with self.assertRaisesRegex(ValueError, 'index exceeds'):
            self.check()
        self.texts['index.md'] = original
        self.texts['TASKS_HPS-001.md'] += 'x' * (4 * 1024 * 1024)
        with self.assertRaisesRegex(ValueError, 'phase exceeds'):
            self.check()

    def test_unknown_status_is_rejected_even_if_tables_agree(self):
        for path in ['TASKS_HPS-001.md', 'TASKS_HPS-002.md', 'roadmap.md', 'phases.md']:
            self.texts[path] = self.texts[path].replace('BLOCKED', 'MAGIC')
        with self.assertRaisesRegex(ValueError, 'invalid task status'):
            self.check()


if __name__ == '__main__':
    unittest.main()
