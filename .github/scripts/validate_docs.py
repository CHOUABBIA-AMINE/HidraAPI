#!/usr/bin/env python3
"""Offline checks for canonical inventory, links, metadata and primary P2 controls."""
import argparse
import collections
import re
import unicodedata
from pathlib import Path
from urllib.parse import unquote, urlsplit
from validate_openapi_snapshot import DEFAULT_MANIFEST, load_json

STATUSES = {'DRAFT', 'CURRENT', 'TARGET', 'DEFERRED', 'SUPERSEDED', 'HISTORICAL',
            'EXECUTION_HISTORY', 'UNVERIFIED', 'BLOCKED-DECISION'}
TASK_STATUSES = {'PENDING', 'COMPLETED', 'BLOCKED', 'IN PROGRESS', 'PLANNED', 'SKIPPED'}


def without_fences(text):
    output, fence = [], None
    for line in text.splitlines():
        match = re.match(r'^\s{0,3}(`{3,}|~{3,})', line)
        if match:
            marker = match[1]
            if fence is None:
                fence = marker
            elif marker[0] == fence[0] and len(marker) >= len(fence):
                fence = None
            output.append('')
        else:
            output.append(line if fence is None else '')
    return '\n'.join(output)


def heading_slug(value):
    value = re.sub(r'!?\[([^\]]*)\]\([^)]*\)', r'\1', value)
    value = re.sub(r'<[^>]+>', '', value).lower()
    return ''.join(ch for ch in value if ch in '_- ' or unicodedata.category(ch)[0] in 'LNM').replace(' ', '-')


def anchors(text):
    text = without_fences(text)
    html = re.sub(r'(`+)(.*?)\1', '', text)
    result = set(re.findall(r'\b(?:id|name)=[\'"]([^\'"]+)[\'"]', html))
    heading_ids = set()
    counts = collections.Counter()
    lines = text.splitlines()
    for index, line in enumerate(lines):
        match = re.match(r'^\s{0,3}#{1,6}\s+(.+?)\s*#*$', line)
        value = match[1] if match else None
        if value is None and index + 1 < len(lines) and line.strip() and re.match(r'^\s{0,3}(?:=+|-+)\s*$', lines[index + 1]):
            value = line.strip()
        if value is not None:
            slug = heading_slug(value)
            count = counts[slug]
            candidate = slug + (f'-{count}' if count else '')
            while candidate in heading_ids:
                count += 1
                candidate = slug + f'-{count}'
            counts[slug] = count + 1
            heading_ids.add(candidate)
            result.add(candidate)
    return result


def reference_key(label):
    return ' '.join(label.split()).casefold()


def destination(value):
    value = value.strip()
    if value.startswith('<'):
        end = value.find('>')
        if end < 0:
            raise ValueError('Unclosed angle-bracket destination')
        return value[1:end]
    # A quoted optional title follows whitespace; escaped whitespace belongs to a path.
    value = re.split(r'(?<!\\)\s+[\'"]', value, maxsplit=1)[0]
    return re.sub(r'\\([\\ ()#])', r'\1', value.strip())


def links(text):
    text = without_fences(text)
    # Inline code is an example, not a link or reference definition.
    text = re.sub(r'(`+)(.*?)\1', lambda m: ' ' * len(m[0]), text)
    definitions = {}
    for match in re.finditer(r'^\s{0,3}\[([^\]]+)\]:\s*(.+)$', text, re.M):
        key = reference_key(match[1])
        if key in definitions:
            raise ValueError(f'Duplicate reference definition: {key}')
        definitions[key] = destination(match[2])
    text = re.sub(r'^\s{0,3}\[[^\]]+\]:.*$', '', text, flags=re.M)
    found, consumed = [], []
    for match in re.finditer(r'!?\[(?:\\.|[^\]\\])*\]\(', text):
        start, depth, quote, escaped = match.end(), 1, None, False
        end = start
        while end < len(text):
            char = text[end]
            if escaped:
                escaped = False
            elif char == '\\':
                escaped = True
            elif quote:
                if char == quote:
                    quote = None
            elif char in '\'"' and end > start and text[end - 1].isspace():
                quote = char
            elif char == '(':
                depth += 1
            elif char == ')':
                depth -= 1
                if depth == 0:
                    break
            end += 1
        if depth:
            raise ValueError('Unclosed Markdown link destination')
        found.append(destination(text[start:end]))
        consumed.append((match.start(), end + 1))
    masked = list(text)
    for start, end in consumed:
        masked[start:end] = ' ' * (end - start)
    text = ''.join(masked)
    for match in re.finditer(r'!?\[([^\]]+)\]\[([^\]]*)\]', text):
        key = reference_key(match[2] or match[1])
        if key not in definitions:
            raise ValueError(f'Unresolved reference link: {key}')
        found.append(definitions[key])
    # Defined shortcut references; ordinary bracketed prose is not a link.
    for match in re.finditer(r'(?<!\])\[([^\]]+)\](?!\[)', text):
        key = reference_key(match[1])
        if key in definitions and not any(a <= match.start() < b for a, b in
                ((m.start(), m.end()) for m in re.finditer(r'!?\[[^\]]+\]\[[^\]]*\]', text))):
            found.append(definitions[key])
    return found


def local_target(root, parent, dest):
    if dest.startswith('//') or urlsplit(dest).scheme:
        return None
    path, _, fragment = dest.partition('#')
    target = (parent / unquote(path)).resolve() if path else parent
    root = root.resolve()
    try:
        relative = target.relative_to(root)
    except ValueError as error:
        raise ValueError(f'Link escapes repository: {dest}') from error
    if not target.exists():
        raise ValueError(f'Missing/case-mismatched target: {dest}')
    # Explicit case validation also applies on case-insensitive development systems.
    cursor = root
    for part in relative.parts:
        if part not in {p.name for p in cursor.iterdir()}:
            raise ValueError(f'Case-mismatched target: {dest}')
        cursor = cursor / part
    return target, unquote(fragment)


def validate_links(root, path, text, anchor_cache=None):
    anchor_cache = {} if anchor_cache is None else anchor_cache
    count = 0
    for dest in links(text):
        if not dest:
            raise ValueError(f'{path}: empty link destination')
        if dest.startswith('#'):
            resolved = (path, unquote(dest[1:]))
        else:
            resolved = local_target(root, path.parent, dest)
        if resolved is None:
            continue
        target, fragment = resolved
        if fragment and target.suffix.lower() == '.md':
            if target not in anchor_cache:
                anchor_cache[target] = anchors(target.read_text(encoding='utf-8'))
            if fragment not in anchor_cache[target]:
                raise ValueError(f'{path.relative_to(root)}: missing anchor {dest}')
        count += 1
    return count


def document_status(text):
    match = re.search(r'^## Status[^\n]*\n+(.*?)(?=\n## |\Z)', text, re.M | re.S)
    if not match:
        raise ValueError('Missing leading Status section')
    return match[1].strip().splitlines()[0].strip('* ').strip()


def primary_p2(text):
    match = re.search(r'^### Phase P2 .*?\n(.*?)(?=^### Phase P3 )', text, re.M | re.S)
    if not match:
        raise ValueError('Missing primary P2 registry')
    rows = {}
    for line in match[1].splitlines():
        if not line.startswith('| HPR-P2-'):
            continue
        cells = [x.strip() for x in line.strip('|').split('|')]
        if len(cells) != 7 or cells[0] in rows:
            raise ValueError('Malformed or duplicate P2 row')
        status = re.split(r'\s+—|\s+- ', cells[1], maxsplit=1)[0].upper()
        if status not in TASK_STATUSES:
            raise ValueError(f'Invalid P2 task status: {status}')
        rows[cells[0]] = status
    if set(rows) != {f'HPR-P2-{i:03}' for i in range(1, 14)}:
        raise ValueError('P2 registry missing/unregistered codes')
    if rows['HPR-P2-013'] == 'COMPLETED':
        if any(value != 'COMPLETED' for value in rows.values()):
            raise ValueError('P2 closure precedes prerequisite completion')
    else:
        next_section = re.search(r'^## 6\. Immediate Next Execution\n(.*?)(?=^### |^## )', text, re.M | re.S)
        pending = next(code for code, value in sorted(rows.items()) if value != 'COMPLETED')
        if not next_section or f'`{pending} — ' not in next_section[1]:
            raise ValueError('Immediate next task disagrees with primary P2 registry')
    return rows


def validate(root, manifest):
    root = Path(root).resolve()
    inventory = manifest['documents']
    actual = {str(p.relative_to(root)) for p in (root / 'doc').rglob('*.md')}
    if not actual or actual != set(inventory):
        raise ValueError(f'Canonical inventory drift: {sorted(actual ^ set(inventory))}')
    texts, link_count, anchor_cache = {}, 0, {}
    for rel in sorted(actual):
        path = root / rel
        if not path.resolve().is_relative_to(root):
            raise ValueError(f'{rel}: document escapes repository')
        text = path.read_text(encoding='utf-8')
        if not text.strip():
            raise ValueError(f'{rel}: empty document')
        if any(line.lstrip().startswith(('<<<<<<<', '=======', '>>>>>>>')) for line in text.splitlines()):
            raise ValueError(f'{rel}: conflict marker')
        texts[rel] = text
        link_count += validate_links(root, path, text, anchor_cache)
    for rel, entry in inventory.items():
        text = texts[rel]
        if entry['status'] == 'ACTIVE':
            block = re.search(r'^## 1\. Control Status\n(.*?)(?=^## )', text, re.M | re.S)
            if rel != manifest['roadmap'] or not block or '| Status | ACTIVE |' not in block[1]:
                raise ValueError('Invalid roadmap control status')
        else:
            line = document_status(text)
            if entry['status'] == 'LEGACY-P1':
                exception = manifest['legacy_statuses'].get(rel)
                if not exception or not exception.get('rationale') or line != exception['line']:
                    raise ValueError(f'{rel}: unapproved historical status phrase')
            elif entry['status'] not in STATUSES or not re.match(re.escape(entry['status']) + r'\b', line):
                raise ValueError(f'{rel}: invalid/drifting document status')
        sources = entry['metadata_from']
        if not sources or any(source not in texts for source in sources):
            raise ValueError(f'{rel}: missing declared metadata inheritance')
        inherited = '\n'.join([text] + [texts[source] for source in sources])
        for field, pattern in manifest['metadata_signals'].items():
            if not re.search(pattern, inherited, re.I):
                raise ValueError(f'{rel}: missing {field} metadata signal')
    for index, targets in manifest['indexes'].items():
        if index not in texts or len(targets) != len(set(targets)):
            raise ValueError('Missing/duplicate index inventory')
        # Actual links or literal canonical path mentions count as navigation.
        destinations = links(texts[index])
        resolved = set()
        for dest in destinations:
            if dest.startswith('#'):
                continue
            target = local_target(root, (root / index).parent, dest)
            if target:
                resolved.add(str(target[0].relative_to(root)))
        for target in targets:
            if target not in texts:
                raise ValueError(f'{index}: missing registered document {target}')
            if target not in resolved and f'`{target}`' not in texts[index] and f'`{Path(target).name}`' not in texts[index]:
                raise ValueError(f'{index}: missing navigation for {target}')
    modules = manifest['modules']
    actual_modules = {p.name for p in (root / manifest['module_source']).iterdir() if p.is_dir()}
    if set(modules) != actual_modules or len(modules) != len(set(modules)):
        raise ValueError('Implemented module registry drift')
    module_docs = {str(p.relative_to(root)) for p in (root / 'doc/modules').glob('*.md') if p.name != 'README.md'}
    if module_docs != {f'doc/modules/{module}.md' for module in modules}:
        raise ValueError('Missing/unregistered module documents')
    module_index = texts['doc/modules/README.md']
    for module in modules:
        if len(re.findall(r'\]\(' + re.escape(module) + r'\.md\)', module_index)) != 1:
            raise ValueError('Missing/duplicate module index target: ' + module)
        if inventory[f'doc/modules/{module}.md']['status'] != 'CURRENT':
            raise ValueError('Module document must be CURRENT: ' + module)
    register = texts[manifest['register']]
    for area, expected in manifest['register_areas'].items():
        rows = [line for line in register.splitlines() if line.startswith(f'| `{area}` |')]
        if len(rows) != 1 or rows[0].split('|')[2].strip() != expected:
            raise ValueError('Canonical register area/status drift: ' + area)
    for index in manifest['current_indexes']:
        if inventory[index]['status'] != 'CURRENT':
            raise ValueError('Index/register CURRENT disagreement: ' + index)
    rows = primary_p2(texts[manifest['roadmap']])
    register_rows = [line for line in register.splitlines() if line.startswith('| Documentation CI drift controls |')]
    required = 'CURRENT' if rows['HPR-P2-012'] == 'COMPLETED' else 'PARTIAL'
    if len(register_rows) != 1 or register_rows[0].split('|')[2].strip() != required:
        raise ValueError('Documentation CI register disagrees with P2 task')
    return {'documents': len(actual), 'relative_links': link_count, 'modules': len(modules), 'p2_rows': len(rows)}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--root', type=Path, default=Path('.'))
    parser.add_argument('--manifest', default=DEFAULT_MANIFEST)
    args = parser.parse_args()
    try:
        result = validate(args.root, load_json(args.root / args.manifest))
        print('Documentation validation passed: ' + str(result))
        return 0
    except (ValueError, OSError, KeyError, TypeError, StopIteration) as error:
        print(f'Documentation validation FAILED: {error}')
        return 1


if __name__ == '__main__':
    raise SystemExit(main())
