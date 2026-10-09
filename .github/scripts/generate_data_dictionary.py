#!/usr/bin/env python3
"""Read disposable-CI PostgreSQL metadata; render a source-linked dictionary.

No migration, schema reset or business-row export. PostgreSQL catalogs are the
physical authority; the deliberately bounded Java reader supplies mapping evidence.
Unsupported Java mappings fail instead of being silently omitted.
"""
import argparse
import hashlib
import json
import os
import re
import subprocess
from pathlib import Path

VERSION = 1
SHA = re.compile(r'^[0-9a-f]{40}$')
UNSUPPORTED = re.compile(r'@(Embedded\b|EmbeddedId\b|AttributeOverrides?\b|'
                         r'Inheritance\b|SecondaryTables?\b|JoinTable\b|'
                         r'ElementCollection\b|OneToMany\b|ManyToMany\b|Access\b|'
                         r'Formula\b|Convert\b)|\bclass\s+\w+\s+extends\b')

# One read-only snapshot, all non-system namespaces, no business SELECTs.
CATALOG_SQL = r"""
BEGIN TRANSACTION ISOLATION LEVEL REPEATABLE READ READ ONLY;
SELECT jsonb_build_object(
 'server_version', current_setting('server_version'),
 'database', current_database(),
 'history', (SELECT coalesce(jsonb_agg(jsonb_build_object(
    'rank', installed_rank, 'version', version, 'type', type,
    'script', script, 'checksum', checksum, 'success', success)
    ORDER BY installed_rank), '[]'::jsonb) FROM public.flyway_schema_history),
 'relations', (SELECT coalesce(jsonb_agg(jsonb_build_object(
    'schema', n.nspname, 'name', c.relname, 'kind', c.relkind,
    'comment', obj_description(c.oid, 'pg_class'),
    'view_definition', CASE WHEN c.relkind IN ('v','m') THEN pg_get_viewdef(c.oid, true) END,
    'sequence', (SELECT to_jsonb(q) - 'seqrelid' FROM pg_sequence q WHERE q.seqrelid=c.oid),
    'columns', (SELECT coalesce(jsonb_agg(jsonb_build_object(
       'position', a.attnum, 'name', a.attname,
       'sql_type', format_type(a.atttypid, a.atttypmod),
       'nullable', NOT a.attnotnull, 'identity', a.attidentity,
       'generated', a.attgenerated, 'default', pg_get_expr(d.adbin, d.adrelid),
       'comment', col_description(c.oid, a.attnum)) ORDER BY a.attnum), '[]'::jsonb)
       FROM pg_attribute a LEFT JOIN pg_attrdef d ON d.adrelid=a.attrelid AND d.adnum=a.attnum
       WHERE a.attrelid=c.oid AND a.attnum>0 AND NOT a.attisdropped),
    'constraints', (SELECT coalesce(jsonb_agg(jsonb_build_object(
       'name', k.conname, 'type', k.contype, 'definition', pg_get_constraintdef(k.oid, true),
       'validated', k.convalidated, 'deferrable', k.condeferrable,
       'initially_deferred', k.condeferred,
       'columns', (SELECT coalesce(jsonb_agg(a.attname ORDER BY x.ordinality), '[]'::jsonb)
          FROM unnest(k.conkey) WITH ORDINALITY x(num, ordinality)
          JOIN pg_attribute a ON a.attrelid=c.oid AND a.attnum=x.num),
       'reference', CASE WHEN k.contype='f' THEN jsonb_build_object(
          'schema', rn.nspname, 'table', rc.relname,
          'columns', (SELECT jsonb_agg(a.attname ORDER BY x.ordinality)
             FROM unnest(k.confkey) WITH ORDINALITY x(num, ordinality)
             JOIN pg_attribute a ON a.attrelid=k.confrelid AND a.attnum=x.num),
          'on_update', k.confupdtype, 'on_delete', k.confdeltype, 'match', k.confmatchtype) END)
       ORDER BY k.conname), '[]'::jsonb)
       FROM pg_constraint k LEFT JOIN pg_class rc ON rc.oid=k.confrelid
       LEFT JOIN pg_namespace rn ON rn.oid=rc.relnamespace WHERE k.conrelid=c.oid),
    'indexes', (SELECT coalesce(jsonb_agg(jsonb_build_object(
       'name', ic.relname, 'definition', pg_get_indexdef(i.indexrelid),
       'unique', i.indisunique, 'primary', i.indisprimary,
       'valid', i.indisvalid, 'ready', i.indisready) ORDER BY ic.relname), '[]'::jsonb)
       FROM pg_index i JOIN pg_class ic ON ic.oid=i.indexrelid WHERE i.indrelid=c.oid),
    'triggers', (SELECT coalesce(jsonb_agg(jsonb_build_object(
       'name', t.tgname, 'definition', pg_get_triggerdef(t.oid, true), 'enabled', t.tgenabled,
       'function_identity', pn.nspname || '.' || p.proname || '(' || pg_get_function_identity_arguments(p.oid) || ')',
       'function_definition', pg_get_functiondef(p.oid)) ORDER BY t.tgname), '[]'::jsonb)
       FROM pg_trigger t JOIN pg_proc p ON p.oid=t.tgfoid
       JOIN pg_namespace pn ON pn.oid=p.pronamespace WHERE t.tgrelid=c.oid AND NOT t.tgisinternal)
    ) ORDER BY n.nspname, c.relname), '[]'::jsonb)
    FROM pg_class c JOIN pg_namespace n ON n.oid=c.relnamespace
    WHERE n.nspname NOT IN ('pg_catalog','information_schema') AND n.nspname NOT LIKE 'pg_toast%'
      AND n.nspname NOT LIKE 'pg_temp_%' AND c.relkind IN ('r','p','v','m','f','S'))
);
COMMIT;
"""


def unique_object(pairs):
    result = {}
    for key, value in pairs:
        if key in result:
            raise ValueError(f'Duplicate JSON key: {key}')
        result[key] = value
    return result


def load_json(path):
    def reject(value):
        raise ValueError(f'Nonfinite JSON value: {value}')
    return json.loads(Path(path).read_text(encoding='utf-8'),
                      object_pairs_hook=unique_object, parse_constant=reject)


def canonical(value):
    return (json.dumps(value, indent=2, sort_keys=True, ensure_ascii=False,
                       allow_nan=False) + '\n').encode('utf-8')


def digest(value):
    return hashlib.sha256(value).hexdigest()


def annotation_arguments(text, name):
    """Read balanced annotation arguments including quoted parentheses."""
    for match in re.finditer(r'@' + name + r'\s*\(', text):
        start = match.end()
        depth, quoted, escaped, i = 1, False, False, start
        while i < len(text) and depth:
            char = text[i]
            if quoted:
                if escaped:
                    escaped = False
                elif char == '\\':
                    escaped = True
                elif char == '"':
                    quoted = False
            elif char == '"':
                quoted = True
            elif char == '(':
                depth += 1
            elif char == ')':
                depth -= 1
            i += 1
        if depth:
            raise ValueError(f'Unclosed @{name}')
        yield text[start:i - 1], i


def named(arguments, name, default=None):
    match = re.search(r'\b' + name + r'\s*=\s*"([^"\\]*)"', arguments)
    return match[1] if match else default


def java_mapping(path, root):
    raw = path.read_text(encoding='utf-8')
    text = re.sub(r'/\*.*?\*/|//[^\n]*', '', raw, flags=re.S)
    if not re.search(r'@Entity\b', text):
        return None
    if UNSUPPORTED.search(text):
        raise ValueError(f'Unsupported JPA mapping (do not omit it): {path.relative_to(root)}')
    declarations = list(annotation_arguments(text, 'Table'))
    if len(declarations) != 1:
        raise ValueError(f'Expected one explicit @Table: {path.relative_to(root)}')
    table = named(declarations[0][0], 'name')
    schema = named(declarations[0][0], 'schema', 'public')
    if not table or not schema:
        raise ValueError(f'Missing physical mapping name: {path.relative_to(root)}')
    columns = []
    mapped_fields = set()
    for kind in ('Column', 'JoinColumn'):
        for args, end in annotation_arguments(text, kind):
            name = named(args, 'name')
            tail = text[end:text.find(';', end) + 1]
            field = re.search(r'\b(?:private|protected|public)\s+([\w.<>?, ]+)\s+(\w+)\s*;', tail)
            if not name or not field or '@Column' in tail or '@JoinColumn' in tail:
                raise ValueError(f'Unsupported/implicit @{kind} field: {path.relative_to(root)}')
            mapped_fields.add(field[2])
            attributes = dict(re.findall(r'\b(nullable|length|precision|scale|unique|insertable|updatable)\s*=\s*(true|false|\d+)', args))
            aliases = [c for c in columns if c['name'] == name]
            writable = attributes.get('insertable') != 'false' or attributes.get('updatable') != 'false'
            if aliases and writable and any(c['attributes'].get('insertable') != 'false'
                                           or c['attributes'].get('updatable') != 'false' for c in aliases):
                raise ValueError(f'Duplicate writable mapped column: {path.relative_to(root)}:{name}')
            columns.append({'name': name, 'field': field[2], 'java_type': field[1].strip(),
                            'annotation': kind, 'attributes': attributes,
                            'column_definition': named(args, 'columnDefinition')})
    fields = re.findall(r'\bprivate\s+(?!static\b)([\w.<>?, ]+)\s+(\w+)\s*;', text)
    if not columns or set(f[1] for f in fields) != mapped_fields:
        raise ValueError(f'Unmapped/unsupported persistent fields: {path.relative_to(root)}')
    return {'schema': schema, 'table': table, 'module': path.relative_to(root / 'src/main/java/dz/sh/hidra/modules').parts[0],
            'source': path.relative_to(root).as_posix(), 'columns': sorted(columns, key=lambda c: (c['name'], c['field']))}


def version_key(version):
    if not isinstance(version, str) or not re.fullmatch(r'\d+(?:[._]\d+)*', version):
        raise ValueError(f'Invalid migration version: {version!r}')
    parts = [int(x) for x in re.split(r'[._]', version)]
    while len(parts) > 1 and parts[-1] == 0:
        parts.pop()
    return tuple(parts)


def inventory(root):
    migrations, files, versions = [], {}, set()
    for path in sorted((root / 'src/main/resources/db/migration').glob('*.sql')):
        match = re.fullmatch(r'V(\d+(?:[._]\d+)*)__[a-z0-9_]+\.sql', path.name)
        if not match:
            raise ValueError(f'Unsupported migration filename: {path.name}')
        key = version_key(match[1])
        if key in versions:
            raise ValueError(f'Duplicate normalized migration version: {path.name}')
        versions.add(key)
        relative = path.relative_to(root).as_posix()
        files[relative] = digest(path.read_bytes())
        migrations.append({'version': match[1], 'script': path.name, 'source': relative,
                           'sha256': files[relative]})
    mappings = []
    for path in sorted((root / 'src/main/java/dz/sh/hidra/modules').rglob('*.java')):
        mapping = java_mapping(path, root)
        if mapping:
            mappings.append(mapping)
            files[mapping['source']] = digest(path.read_bytes())
    if not migrations or not mappings:
        raise ValueError('Empty migration/JPA source inventory')
    identities = [(m['schema'], m['table']) for m in mappings]
    if len(identities) != len(set(identities)):
        raise ValueError('Duplicate JPA table mapping')
    return {'migrations': sorted(migrations, key=lambda m: version_key(m['version'])),
            'mappings': mappings, 'files_sha256': dict(sorted(files.items())),
            'bundle_sha256': digest(canonical(files))}


def validate_catalog(catalog, source):
    if not catalog.get('server_version') or catalog.get('database') != 'hidra_test':
        raise ValueError('Missing engine provenance or wrong disposable database')
    history = catalog.get('history')
    if not isinstance(history, list) or len(history) != len(source['migrations']):
        raise ValueError('Incomplete/unexpected Flyway history')
    ranks, versions = set(), set()
    for actual, expected in zip(history, source['migrations']):
        if (actual.get('success') is not True or actual.get('type') != 'SQL'
                or actual.get('script') != expected['script']
                or version_key(actual.get('version')) != version_key(expected['version'])
                or type(actual.get('checksum')) is not int):
            raise ValueError(f'Failed/mismatched migration identity: {expected["script"]}')
        key = version_key(actual['version'])
        if actual.get('rank') in ranks or key in versions:
            raise ValueError('Duplicate Flyway history rank/version')
        ranks.add(actual['rank'])
        versions.add(key)
    relations = catalog.get('relations')
    if not isinstance(relations, list) or not relations:
        raise ValueError('Empty/incomplete catalog')
    by_name = {}
    for relation in relations:
        key = (relation.get('schema'), relation.get('name'))
        if not all(isinstance(x, str) and x for x in key) or key in by_name:
            raise ValueError('Missing/duplicate physical relation identity')
        if relation.get('kind') not in ('r', 'p', 'v', 'm', 'S'):
            raise ValueError(f'Unsupported relation kind: {key}')
        by_name[key] = relation
        for required in ('columns', 'constraints', 'indexes', 'triggers'):
            if not isinstance(relation.get(required), list):
                raise ValueError(f'Missing catalog array: {key}:{required}')
        names, positions = set(), set()
        if not relation['columns']:
            raise ValueError(f'Missing physical columns: {key}')
        for col in relation['columns']:
            if (not col.get('name') or not col.get('sql_type')
                    or type(col.get('nullable')) is not bool
                    or type(col.get('position')) is not int
                    or col['name'] in names or col['position'] in positions):
                raise ValueError(f'Invalid/duplicate column metadata: {key}')
            names.add(col['name'])
            positions.add(col['position'])
        for group in ('constraints', 'indexes', 'triggers'):
            item_names = set()
            for item in relation[group]:
                if not item.get('name') or not item.get('definition') or item['name'] in item_names:
                    raise ValueError(f'Incomplete/duplicate {group}: {key}')
                item_names.add(item['name'])
                if group == 'constraints':
                    if type(item.get('validated')) is not bool or not isinstance(item.get('columns'), list):
                        raise ValueError(f'Incomplete constraint metadata: {key}')
                    if not set(item['columns']) <= names:
                        raise ValueError(f'Constraint uses missing column: {key}')
                if group == 'indexes' and (item.get('valid') is not True or item.get('ready') is not True):
                    raise ValueError(f'Invalid/unready index: {key}')
                if group == 'triggers' and not item.get('function_definition'):
                    raise ValueError(f'Missing trigger function evidence: {key}')
    for key, relation in by_name.items():
        for constraint in relation['constraints']:
            if constraint.get('type') == 'f':
                ref = constraint.get('reference') or {}
                target = by_name.get((ref.get('schema'), ref.get('table')))
                if (not target or not isinstance(ref.get('columns'), list)
                        or len(ref['columns']) != len(constraint['columns'])
                        or not set(ref['columns']) <= {c['name'] for c in target['columns']}):
                    raise ValueError(f'Incomplete/dangling composite FK metadata: {key}')
    for mapping in source['mappings']:
        key = (mapping['schema'], mapping['table'])
        relation = by_name.get(key)
        if not relation or relation['kind'] not in ('r', 'p'):
            raise ValueError(f'Missing physical JPA table: {key}')
        if not {c['name'] for c in mapping['columns']} <= {c['name'] for c in relation['columns']}:
            raise ValueError(f'Missing physical JPA column: {key}')
    return by_name


def collect(root, source_sha):
    if not SHA.fullmatch(source_sha):
        raise ValueError('Expected exact lowercase source SHA')
    if (os.environ.get('GITHUB_ACTIONS') != 'true' or os.environ.get('PGHOST') != '127.0.0.1'
            or os.environ.get('PGPORT') != '5432' or os.environ.get('PGDATABASE') != 'hidra_test'
            or os.environ.get('PGUSER') != 'hidra'):
        raise ValueError('Collection is restricted to the designated local disposable Actions database')
    actual = subprocess.run(['git', '-C', str(root), 'rev-parse', 'HEAD'],
                            check=True, text=True, capture_output=True).stdout.strip()
    if actual != source_sha:
        raise ValueError('Checkout/source SHA mismatch')
    source = inventory(root)
    env = dict(os.environ, PGOPTIONS='-c default_transaction_read_only=on -c statement_timeout=60000')
    completed = subprocess.run(['psql', '-X', '-qAt', '-v', 'ON_ERROR_STOP=1'],
                               input=CATALOG_SQL, text=True, capture_output=True,
                               env=env, timeout=120)
    if completed.returncode:
        # Do not echo command/environment/server error strings into public artifacts.
        raise ValueError('Read-only psql catalog collection failed; inspect isolated CI service state')
    catalog = json.loads(completed.stdout, object_pairs_hook=unique_object)
    validate_catalog(catalog, source)
    return {'format_version': VERSION, 'source_sha': source_sha, 'source': source, 'catalog': catalog}


def validate_document(document, root):
    if document.get('format_version') != VERSION or not SHA.fullmatch(document.get('source_sha', '')):
        raise ValueError('Invalid schema artifact format/provenance')
    current = inventory(root)
    if document.get('source') != current:
        raise ValueError('Migration/JPA source drift since catalog capture')
    return validate_catalog(document['catalog'], current)


def ownership(document, overrides, root):
    mapping = {(m['schema'], m['table']): m for m in document['source']['mappings']}
    relations = {(r['schema'], r['name']) for r in document['catalog']['relations']}
    if not isinstance(overrides, dict) or set(overrides) - {'.'.join(x) for x in relations}:
        raise ValueError('Ownership override references unknown relation')
    owners, unresolved = {}, []
    allowed = {m['module'] for m in mapping.values()} | {'platform', 'flyway'}
    for key in sorted(relations):
        identity = '.'.join(key)
        mapped = mapping.get(key)
        override = overrides.get(identity)
        if override is not None:
            if (not isinstance(override, dict) or override.get('owner') not in allowed
                    or not isinstance(override.get('purpose'), str) or not override['purpose'].strip()
                    or not isinstance(override.get('evidence'), list) or not override['evidence']):
                raise ValueError(f'Incomplete ownership/semantic evidence: {identity}')
            if mapped and override['owner'] != mapped['module']:
                raise ValueError(f'Ownership contradicts JPA module: {identity}')
            for evidence in override['evidence']:
                if (not isinstance(evidence, str) or Path(evidence).is_absolute()
                        or '..' in Path(evidence).parts or not (root / evidence).is_file()):
                    raise ValueError(f'Invalid ownership evidence path: {identity}')
            owners[key] = override
        elif mapped:
            owners[key] = {'owner': mapped['module'],
                           'purpose': f'Persisted {Path(mapped["source"]).stem} state; semantic authority remains with {mapped["module"]}.',
                           'evidence': [mapped['source']]}
        else:
            unresolved.append(identity)
            owners[key] = {'owner': 'UNRESOLVED', 'purpose': 'Owner/semantic review required.', 'evidence': []}
    return owners, unresolved


def cell(value):
    return str(value if value is not None else '—').replace('&', '&amp;').replace('<', '&lt;').replace('>', '&gt;').replace('|', '&#124;').replace('\n', '<br>')


def render(document, root, overrides=None, final=False):
    validate_document(document, root)
    owners, unresolved = ownership(document, overrides or {}, root)
    if final and unresolved:
        raise ValueError('Unresolved physical ownership: ' + ', '.join(unresolved))
    mappings = {(m['schema'], m['table']): m for m in document['source']['mappings']}
    status = 'CURRENT' if final else 'DRAFT — NOT CURRENT; catalog artifact awaiting ownership/content review'
    out = ['# HidraAPI Physical Data Dictionary', '', '## Status', '', status, '', '## Generation basis', '',
           f'Captured source SHA: `{document["source_sha"]}`',
           f'PostgreSQL server: `{document["catalog"]["server_version"]}`; disposable database: `hidra_test`.',
           f'Generator format: {VERSION}; source bundle SHA-256: `{document["source"]["bundle_sha256"]}`.',
           f'Migrations: {len(document["source"]["migrations"])}; JPA mappings: {len(mappings)}; '
           f'catalog relations: {len(document["catalog"]["relations"])}; unresolved owners: {len(unresolved)}.', '',
           'Catalog facts describe the full source migration chain in disposable CI, not deployed data.',
           'No business rows or production acceptance are established. Ownership/source links are separate from SQL facts.', '',
           '## Physical relations', '']
    sql = [(m, (root / m['source']).read_text(encoding='utf-8')) for m in document['source']['migrations']]
    for relation in sorted(document['catalog']['relations'], key=lambda r: (r['schema'], r['name'])):
        key = (relation['schema'], relation['name'])
        owner = owners[key]
        mapped = mappings.get(key)
        columns = {}
        for col in mapped['columns'] if mapped else []:
            columns.setdefault(col['name'], []).append(col)
        out.extend([f'### {key[0]}.{key[1]}', '', f'Owner: **{cell(owner["owner"])}**; relation kind: `{relation["kind"]}`.',
                    '', cell(owner['purpose']), ''])
        for path in owner['evidence']:
            out.append(f'- Evidence: [{Path(path).name}](../../{path})')
        mentions = [m for m, text in sql if re.search(r'(?<![A-Za-z0-9_])' + re.escape(key[1]) + r'(?![A-Za-z0-9_])', text)]
        for m in mentions:
            out.append(f'- Migration mention (not inferred introduction): [{m["script"]}](../../{m["source"]})')
        out.extend(['', '| Column | PostgreSQL type | Nullable | Default / identity / generated | JPA field/type and declared attributes | Comment |', '|---|---|---|---|---|---|'])
        for col in sorted(relation['columns'], key=lambda c: c['position']):
            java = columns.get(col['name'], [])
            details = '; '.join(f'{j["field"]}: {j["java_type"]}; {json.dumps(j["attributes"], sort_keys=True)}; columnDefinition={j["column_definition"]}' for j in java) if java else 'No current mapped Java column'
            default = f'{col.get("default")}; identity={col.get("identity", "")}; generated={col.get("generated", "")}'
            out.append('| ' + ' | '.join(cell(v) for v in (col['name'], col['sql_type'], col['nullable'], default, details, col.get('comment'))) + ' |')
        for group in ('constraints', 'indexes', 'triggers'):
            out.extend(['', f'#### {group.title()}', ''])
            if not relation[group]:
                out.append('None captured in this catalog category.')
            for item in sorted(relation[group], key=lambda x: x['name']):
                out.extend([f'- `{cell(item["name"])}`: {cell(item["definition"])}'])
                rest = {k: v for k, v in item.items() if k not in ('name', 'definition', 'function_definition')}
                out.append('  - Catalog attributes: ' + cell(json.dumps(rest, sort_keys=True, ensure_ascii=False)))
                if 'function_definition' in item:
                    out.append('  - Function evidence: ' + cell(item['function_definition']))
        for prop in ('view_definition', 'sequence', 'comment'):
            if relation.get(prop) is not None:
                out.extend(['', f'{prop}: ' + cell(json.dumps(relation[prop], sort_keys=True))])
        out.append('')
    return '\n'.join(out).rstrip() + '\n'


def comparable_markdown(text):
    # The only ignored difference is the validated capture source commit line.
    return re.sub(r'^Captured source SHA: `[0-9a-f]{40}`$', 'Captured source SHA: `<verified>`', text, flags=re.M)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--root', type=Path, default=Path('.'))
    parser.add_argument('--collect', type=Path, help='Write read-only CI catalog artifact')
    parser.add_argument('--source-sha')
    parser.add_argument('--input', type=Path, help='Previously captured catalog JSON')
    parser.add_argument('--render', type=Path, help='Write deterministic Markdown')
    parser.add_argument('--check', type=Path, help='Require equality to reviewed CURRENT dictionary')
    parser.add_argument('--ownership', type=Path, help='Source-linked reviewed relation overrides')
    args = parser.parse_args()
    try:
        root = args.root.resolve()
        if bool(args.collect) == bool(args.input) or not args.source_sha:
            raise ValueError('Use either --collect or --input, with the expected --source-sha')
        if args.check and not args.ownership:
            raise ValueError('--check requires reviewed --ownership metadata')
        document = collect(root, args.source_sha) if args.collect else load_json(args.input)
        if document.get('source_sha') != args.source_sha:
            raise ValueError('Artifact/capture source SHA mismatch')
        validate_document(document, root)
        if args.collect:
            args.collect.parent.mkdir(parents=True, exist_ok=True)
            args.collect.write_bytes(canonical(document))
        overrides = load_json(args.ownership) if args.ownership else {}
        markdown = render(document, root, overrides, final=bool(args.ownership))
        if args.check and comparable_markdown(args.check.read_text(encoding='utf-8')) != comparable_markdown(markdown):
            raise ValueError('Physical dictionary drift; review regenerated metadata/content')
        if args.render:
            args.render.parent.mkdir(parents=True, exist_ok=True)
            args.render.write_text(markdown, encoding='utf-8')
        _, unresolved = ownership(document, overrides, root)
        summary = {'source_sha': document['source_sha'], 'source_bundle_sha256': document['source']['bundle_sha256'],
                   'server_version': document['catalog']['server_version'], 'migrations': len(document['source']['migrations']),
                   'jpa_mappings': len(document['source']['mappings']), 'relations': len(document['catalog']['relations']),
                   'unresolved_owners': unresolved, 'catalog_sha256': digest(canonical(document)),
                   'dictionary_sha256': digest(markdown.encode('utf-8')), 'reviewed_dictionary_checked': bool(args.check)}
        print(json.dumps(summary, sort_keys=True))
        return 0
    except (ValueError, KeyError, TypeError, OSError, subprocess.SubprocessError) as error:
        print(f'Data dictionary FAILED: {error}')
        return 1


if __name__ == '__main__':
    raise SystemExit(main())
