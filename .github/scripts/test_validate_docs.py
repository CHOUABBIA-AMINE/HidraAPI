#!/usr/bin/env python3
"""Validate real miniature estates, broken navigation and governance mutations."""
import copy
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


if __name__ == '__main__':
    unittest.main()
