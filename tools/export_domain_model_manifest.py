#!/usr/bin/env python3
"""Extract declared Java domain-model fields from this checkout, without reading runtime data."""
import base64
import json
import re
import zlib
from pathlib import Path

root = Path("src/main/java/dz/sh/hidra/modules")
items = []
errors = []
for path in sorted(root.glob("*/domain/model/*.java")):
    if path.name == "package-info.java":
        continue
    src = path.read_text(encoding="utf-8")
    module = path.parts[8] if path.parts[7] == "modules" else path.parts[7]
    name = path.stem
    # Target top-level type rather than a Javadoc @param or a nested declaration.
    match = re.search(r"\b(?:public\s+)?(record|class|interface|enum)\s+" + re.escape(name) + r"\b", src)
    if not match:
        errors.append([module, name, "declaration-not-found"])
        items.append([module, name, "unknown", [], path.as_posix()])
        continue
    kind = match.group(1)
    fields = []
    if kind == "record":
        start = src.find("(", match.end())
        if start == -1:
            errors.append([module, name, "record-components-not-found"])
        else:
            depth, end = 1, start + 1
            while end < len(src) and depth:
                if src[end] == "(":
                    depth += 1
                elif src[end] == ")":
                    depth -= 1
                end += 1
            content = src[start + 1:end - 1]
            components = []
            buf = []
            generic = parens = 0
            for char in content:
                if char == "<": generic += 1
                elif char == ">": generic -= 1
                elif char == "(": parens += 1
                elif char == ")": parens -= 1
                if char == "," and generic == 0 and parens == 0:
                    components.append("".join(buf).strip())
                    buf = []
                else:
                    buf.append(char)
            if "".join(buf).strip():
                components.append("".join(buf).strip())
            for component in components:
                component = re.sub(r"/\*.*?\*/", "", component, flags=re.S).strip()
                component = re.sub(r"@\w+(?:\([^)]*\))?\s*", "", component)
                field = re.search(r"(.+?)\s+([A-Za-z_]\w*)\s*$", component, flags=re.S)
                if not field:
                    errors.append([module, name, "unparsed-component"])
                    continue
                fields.append([field.group(2), " ".join(field.group(1).split())])
    elif kind == "class":
        # Only explicit instance field declarations; no inferred inherited fields or methods.
        body = src[match.end():]
        for f in re.finditer(r"(?m)^\s*(?:private|protected|public)\s+(?!static\b)(?:(?:final|transient|volatile)\s+)*([\w.<?>,\s\[\]]+?)\s+([A-Za-z_]\w*)\s*(?:=[^;]*)?;", body):
            fields.append([f.group(2), " ".join(f.group(1).split())])
    items.append([module, name, kind, fields, path.as_posix()])
payload = json.dumps({"schema": 1, "models": items, "errors": errors}, ensure_ascii=False, separators=(",", ":")).encode("utf-8")
coded = base64.b64encode(zlib.compress(payload, 9)).decode("ascii")
print(f"MODEL_CATALOG_COUNTS models={len(items)} fields={sum(len(x[3]) for x in items)} exceptions={len(errors)} chars={len(coded)}")
print("MODEL_CATALOG_BEGIN")
for i in range(0, len(coded), 2000):
    print(f"MODEL_CATALOG_CHUNK_{i // 2000:04d}={coded[i:i+2000]}")
print("MODEL_CATALOG_END")
