#!/usr/bin/env python3
"""Offline adverse catalog/mapping fixtures; not proof of a migrated database."""
import copy
import importlib.util
import json
import tempfile
import unittest
from types import SimpleNamespace
from pathlib import Path
from unittest.mock import patch

spec = importlib.util.spec_from_file_location('dictionary', Path(__file__).with_name('generate_data_dictionary.py'))
d = importlib.util.module_from_spec(spec)
spec.loader.exec_module(d)


class DictionaryTest(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.migrations = self.root / 'src/main/resources/db/migration'
        self.migrations.mkdir(parents=True)
        (self.migrations / 'V1_001__parent.sql').write_text('CREATE TABLE hidra_test_parent (id varchar(80));\n')
        (self.migrations / 'V1_002__child.sql').write_text('CREATE TABLE hidra_test_child (id varchar(80));\n')
        self.java = self.root / 'src/main/java/dz/sh/hidra/modules/test/infrastructure/persistence/entity/Parent.java'
        self.java.parent.mkdir(parents=True)
        self.java.write_text('@Entity @Table(name="hidra_test_parent") public class Parent {\n'
                             '@Id @Column(name="id", nullable=false, length=80) private String id;\n}')
        self.source = d.inventory(self.root)
        self.catalog = {'server_version': '16.10', 'database': 'hidra_test', 'history': [
            {'rank': i + 1, 'version': m['version'].replace('_', '.'), 'type': 'SQL',
             'script': m['script'], 'checksum': 100 + i, 'success': True}
            for i, m in enumerate(self.source['migrations'])],
            'relations': [self.relation('hidra_test_parent'), self.relation('hidra_test_child')]}
        self.doc = {'format_version': 1, 'source_sha': 'a' * 40, 'source': self.source, 'catalog': self.catalog}

    @staticmethod
    def relation(name):
        return {'schema': 'public', 'name': name, 'kind': 'r', 'comment': None,
                'columns': [{'position': 1, 'name': 'id', 'sql_type': 'character varying(80)',
                             'nullable': False, 'default': None, 'identity': '', 'generated': '', 'comment': None}],
                'constraints': [], 'indexes': [], 'triggers': [], 'view_definition': None, 'sequence': None}

    def valid(self):
        return d.validate_catalog(self.catalog, self.source)

    def test_partial_and_failed_history_fail(self):
        self.catalog['history'].pop()
        with self.assertRaisesRegex(ValueError, 'history'):
            self.valid()
        self.catalog['history'].append({'success': False})
        with self.assertRaisesRegex(ValueError, 'migration identity'):
            self.valid()

    def test_script_version_and_checksum_identity_fail(self):
        for field, value in [('script', 'wrong.sql'), ('version', '9'), ('checksum', None), ('checksum', True)]:
            with self.subTest(field=field, value=value):
                catalog = copy.deepcopy(self.catalog)
                catalog['history'][0][field] = value
                with self.assertRaises(ValueError):
                    d.validate_catalog(catalog, self.source)

    def test_duplicate_history_and_relations_fail(self):
        self.catalog['history'][1]['rank'] = self.catalog['history'][0]['rank']
        with self.assertRaisesRegex(ValueError, 'Duplicate Flyway'):
            self.valid()
        self.catalog['history'][1]['rank'] = 2
        self.catalog['relations'].append(copy.deepcopy(self.catalog['relations'][0]))
        with self.assertRaisesRegex(ValueError, 'duplicate physical'):
            self.valid()

    def test_missing_table_column_and_catalog_category_fail(self):
        for mutate in [lambda c: c['relations'].pop(0),
                       lambda c: c['relations'][0]['columns'][0].update(name='different'),
                       lambda c: c['relations'][0].pop('indexes')]:
            catalog = copy.deepcopy(self.catalog)
            mutate(catalog)
            with self.assertRaises(ValueError):
                d.validate_catalog(catalog, self.source)

    def test_duplicate_columns_and_unknown_nullable_fail(self):
        self.catalog['relations'][0]['columns'].append(copy.deepcopy(self.catalog['relations'][0]['columns'][0]))
        with self.assertRaisesRegex(ValueError, 'column metadata'):
            self.valid()
        self.catalog['relations'][0]['columns'].pop()
        self.catalog['relations'][0]['columns'][0]['nullable'] = None
        with self.assertRaisesRegex(ValueError, 'column metadata'):
            self.valid()

    def test_composite_fk_order_and_check_definition_preserved(self):
        for relation in self.catalog['relations']:
            relation['columns'].append(dict(relation['columns'][0], position=2, name='revision'))
        child = self.catalog['relations'][1]
        child['constraints'] = [
            {'name': 'fk_pair', 'type': 'f', 'validated': True, 'columns': ['revision', 'id'],
             'definition': 'FOREIGN KEY (revision,id) REFERENCES hidra_test_parent(revision,id)',
             'reference': {'schema': 'public', 'table': 'hidra_test_parent', 'columns': ['revision', 'id'], 'on_delete': 'r'}},
            {'name': 'check_pair', 'type': 'c', 'validated': False, 'columns': ['revision'],
             'definition': 'CHECK (revision > 0) NOT VALID', 'reference': None}]
        text = d.render(self.doc, self.root)
        self.assertIn('FOREIGN KEY (revision,id)', text)
        self.assertIn('NOT VALID', text)
        self.assertIn('"columns": ["revision", "id"]', text)
        child['constraints'][0]['reference']['columns'].pop()
        with self.assertRaisesRegex(ValueError, 'composite FK'):
            self.valid()

    def test_invalid_indexes_and_missing_trigger_functions_fail(self):
        relation = self.catalog['relations'][0]
        relation['indexes'] = [{'name': 'ix', 'definition': 'CREATE INDEX ix ON hidra_test_parent(id)', 'valid': False, 'ready': True}]
        with self.assertRaisesRegex(ValueError, 'Invalid/unready'):
            self.valid()
        relation['indexes'][0]['valid'] = True
        relation['triggers'] = [{'name': 'guard', 'definition': 'CREATE TRIGGER guard BEFORE UPDATE ...'}]
        with self.assertRaisesRegex(ValueError, 'function evidence'):
            self.valid()

    def test_unsupported_java_mapping_and_unmapped_field_fail(self):
        original = self.java.read_text()
        for text in [original.replace('private String id;', '@Embedded private String id;'),
                     original.replace('public class Parent', 'public class Parent extends Base'),
                     original.replace('private String id;', 'private String id; private String implicit;')]:
            self.java.write_text(text)
            with self.assertRaises(ValueError):
                d.inventory(self.root)

    def test_readonly_alias_retained_but_duplicate_writer_fails(self):
        self.java.write_text(self.java.read_text().replace('private String id;',
            'private String id; @JoinColumn(name="id", insertable=false, updatable=false) private Other ref;'))
        mapping = d.inventory(self.root)['mappings'][0]
        self.assertEqual(['id', 'ref'], [c['field'] for c in mapping['columns']])
        self.java.write_text(self.java.read_text().replace('insertable=false', 'insertable=true'))
        with self.assertRaisesRegex(ValueError, 'Duplicate writable'):
            d.inventory(self.root)

    def test_quoted_parentheses_and_normalized_duplicate_version(self):
        self.java.write_text(self.java.read_text().replace('length=80', 'columnDefinition="numeric(18,6)"'))
        self.assertEqual('numeric(18,6)', d.inventory(self.root)['mappings'][0]['columns'][0]['column_definition'])
        (self.migrations / 'V1_1__duplicate.sql').write_text('SELECT 1;')
        with self.assertRaisesRegex(ValueError, 'Duplicate normalized'):
            d.inventory(self.root)

    def test_source_digest_drift_fail(self):
        (self.migrations / 'V1_002__child.sql').write_text('ALTER TABLE hidra_test_child ADD quantity numeric;')
        with self.assertRaisesRegex(ValueError, 'source drift'):
            d.validate_document(self.doc, self.root)

    def test_unresolved_and_unknown_ownership_fail_final(self):
        with self.assertRaisesRegex(ValueError, 'Unresolved physical'):
            d.render(self.doc, self.root, final=True)
        override = {'public.hidra_test_child': {'owner': 'test', 'purpose': 'Reviewed fixture child state.',
                                              'evidence': ['src/main/resources/db/migration/V1_002__child.sql']}}
        self.assertIn('CURRENT', d.render(self.doc, self.root, override, final=True))
        override['public.hidra_test_child']['evidence'] = ['../outside']
        with self.assertRaisesRegex(ValueError, 'evidence path'):
            d.render(self.doc, self.root, override, final=True)

    def test_render_deterministic_and_only_sha_line_ignored(self):
        one = d.render(self.doc, self.root)
        self.catalog['relations'].reverse()
        self.assertEqual(one, d.render(self.doc, self.root))
        two = one.replace('a' * 40, 'b' * 40)
        self.assertEqual(d.comparable_markdown(one), d.comparable_markdown(two))
        self.assertNotEqual(d.comparable_markdown(one), d.comparable_markdown(one.replace('varying(80)', 'varying(160)')))

    def test_duplicate_json_and_nonfinite_fail(self):
        path = self.root / 'input.json'
        for text in ['{"x":1,"x":2}', '{"x":NaN}']:
            path.write_text(text)
            with self.assertRaises(ValueError):
                d.load_json(path)

    def test_collect_rejects_non_disposable_connection_before_subprocess(self):
        with patch.dict(d.os.environ, {'GITHUB_ACTIONS': 'true', 'PGHOST': 'production'}, clear=True), \
                patch.object(d.subprocess, 'run') as run:
            with self.assertRaisesRegex(ValueError, 'designated local disposable'):
                d.collect(self.root, 'a' * 40)
            run.assert_not_called()

    def test_collect_uses_readonly_snapshot_and_exact_checkout(self):
        env = {'GITHUB_ACTIONS': 'true', 'PGHOST': '127.0.0.1', 'PGPORT': '5432',
               'PGDATABASE': 'hidra_test', 'PGUSER': 'hidra'}
        responses = [SimpleNamespace(stdout='a' * 40 + '\n'),
                     SimpleNamespace(returncode=0, stdout=json.dumps(self.catalog))]
        with patch.dict(d.os.environ, env, clear=True), patch.object(d.subprocess, 'run', side_effect=responses) as run:
            document = d.collect(self.root, 'a' * 40)
            self.assertEqual(self.doc, document)
            kwargs = run.call_args.kwargs
            self.assertIn('REPEATABLE READ READ ONLY', kwargs['input'])
            self.assertIn('default_transaction_read_only=on', kwargs['env']['PGOPTIONS'])
            self.assertEqual(['psql', '-X', '-qAt', '-v', 'ON_ERROR_STOP=1'], run.call_args.args[0])
        with patch.dict(d.os.environ, env, clear=True), patch.object(d.subprocess, 'run', return_value=SimpleNamespace(stdout='b' * 40)) as run:
            with self.assertRaisesRegex(ValueError, 'Checkout/source SHA'):
                d.collect(self.root, 'a' * 40)
            self.assertEqual(1, run.call_count)

    def test_physical_type_and_trigger_definition_drift_is_visible(self):
        relation = self.catalog['relations'][0]
        relation['triggers'] = [{'name': 'guard', 'definition': 'CREATE TRIGGER guard BEFORE UPDATE ...',
                                'function_definition': 'CREATE FUNCTION guard() RETURNS trigger AS $$ BEGIN RETURN NEW; END $$;'}]
        before = d.render(self.doc, self.root)
        relation['columns'][0]['sql_type'] = 'text'
        relation['triggers'][0]['function_definition'] = 'CREATE FUNCTION guard() RETURNS trigger AS $$ BEGIN RAISE EXCEPTION \'denied\'; END $$;'
        after = d.render(self.doc, self.root)
        self.assertNotEqual(d.comparable_markdown(before), d.comparable_markdown(after))
        self.assertIn('denied', after)


if __name__ == '__main__':
    unittest.main()
