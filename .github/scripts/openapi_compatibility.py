#!/usr/bin/env python3
"""Fail CI when the current OpenAPI contract contains unsupported breaking changes."""

from __future__ import annotations

import json
import sys
from pathlib import Path
from typing import Any

HTTP_METHODS = {"get", "put", "post", "delete", "options", "head", "patch", "trace"}


def load(path: str) -> dict[str, Any]:
    return json.loads(Path(path).read_text(encoding="utf-8"))


def resolve(doc: dict[str, Any], value: Any) -> Any:
    seen: set[str] = set()
    while isinstance(value, dict) and "$ref" in value:
        ref = value["$ref"]
        if not isinstance(ref, str) or not ref.startswith("#/"):
            return value
        if ref in seen:
            return value
        seen.add(ref)
        target: Any = doc
        for part in ref[2:].split("/"):
            part = part.replace("~1", "/").replace("~0", "~")
            target = target[part]
        value = target
    return value


def schema_type(doc: dict[str, Any], schema: Any) -> Any:
    schema = resolve(doc, schema)
    if not isinstance(schema, dict):
        return None
    return schema.get("type")


def compare_schema(
    base_doc: dict[str, Any],
    cur_doc: dict[str, Any],
    base_schema: Any,
    cur_schema: Any,
    where: str,
    mode: str,
    breaks: list[str],
    seen: set[tuple[int, int, str]],
) -> None:
    base_schema = resolve(base_doc, base_schema)
    cur_schema = resolve(cur_doc, cur_schema)
    if not isinstance(base_schema, dict) or not isinstance(cur_schema, dict):
        return

    key = (id(base_schema), id(cur_schema), mode)
    if key in seen:
        return
    seen.add(key)

    base_type = schema_type(base_doc, base_schema)
    cur_type = schema_type(cur_doc, cur_schema)
    if base_type and cur_type and base_type != cur_type:
        breaks.append(f"{where}: schema type changed from {base_type!r} to {cur_type!r}")
        return

    base_enum = base_schema.get("enum")
    cur_enum = cur_schema.get("enum")
    if isinstance(base_enum, list) and isinstance(cur_enum, list):
        if mode == "request":
            removed = [v for v in base_enum if v not in cur_enum]
            if removed:
                breaks.append(f"{where}: request enum values removed: {removed!r}")
        else:
            added = [v for v in cur_enum if v not in base_enum]
            if added:
                breaks.append(f"{where}: response enum values added: {added!r}")

    base_required = set(base_schema.get("required", []))
    cur_required = set(cur_schema.get("required", []))
    if mode == "request":
        newly_required = sorted(cur_required - base_required)
        if newly_required:
            breaks.append(f"{where}: request properties became required: {newly_required}")
    else:
        removed_required = sorted(base_required - cur_required)
        if removed_required:
            breaks.append(f"{where}: required response properties are no longer guaranteed: {removed_required}")

    base_props = base_schema.get("properties", {})
    cur_props = cur_schema.get("properties", {})
    if isinstance(base_props, dict) and isinstance(cur_props, dict):
        if mode == "response":
            removed_props = sorted(set(base_props) - set(cur_props))
            if removed_props:
                breaks.append(f"{where}: response properties removed: {removed_props}")
        for name in sorted(set(base_props) & set(cur_props)):
            compare_schema(
                base_doc,
                cur_doc,
                base_props[name],
                cur_props[name],
                f"{where}.{name}",
                mode,
                breaks,
                seen,
            )

    if "items" in base_schema and "items" in cur_schema:
        compare_schema(
            base_doc,
            cur_doc,
            base_schema["items"],
            cur_schema["items"],
            f"{where}[]",
            mode,
            breaks,
            seen,
        )

    for keyword in ("oneOf", "anyOf", "allOf"):
        base_items = base_schema.get(keyword)
        cur_items = cur_schema.get(keyword)
        if isinstance(base_items, list) and isinstance(cur_items, list):
            if len(base_items) != len(cur_items):
                breaks.append(
                    f"{where}: {keyword} alternative count changed "
                    f"from {len(base_items)} to {len(cur_items)}"
                )


def collect_parameters(doc: dict[str, Any], path_item: dict[str, Any], op: dict[str, Any]) -> dict[tuple[str, str], dict[str, Any]]:
    result: dict[tuple[str, str], dict[str, Any]] = {}
    for raw in list(path_item.get("parameters", [])) + list(op.get("parameters", [])):
        param = resolve(doc, raw)
        if not isinstance(param, dict):
            continue
        key = (str(param.get("in", "")), str(param.get("name", "")))
        result[key] = param
    return result


def compare_operation(
    base_doc: dict[str, Any],
    cur_doc: dict[str, Any],
    path: str,
    method: str,
    base_path_item: dict[str, Any],
    cur_path_item: dict[str, Any],
    base_op: dict[str, Any],
    cur_op: dict[str, Any],
    breaks: list[str],
) -> None:
    where = f"{method.upper()} {path}"

    base_params = collect_parameters(base_doc, base_path_item, base_op)
    cur_params = collect_parameters(cur_doc, cur_path_item, cur_op)

    removed_params = sorted(set(base_params) - set(cur_params))
    if removed_params:
        breaks.append(f"{where}: parameters removed from contract: {removed_params}")

    for key in sorted(set(cur_params) - set(base_params)):
        if cur_params[key].get("required") is True:
            breaks.append(f"{where}: new required parameter {key}")

    for key in sorted(set(base_params) & set(cur_params)):
        before = base_params[key]
        after = cur_params[key]
        if before.get("required") is not True and after.get("required") is True:
            breaks.append(f"{where}: parameter became required: {key}")
        compare_schema(
            base_doc,
            cur_doc,
            before.get("schema", {}),
            after.get("schema", {}),
            f"{where} parameter {key}",
            "request",
            breaks,
            set(),
        )

    base_body = resolve(base_doc, base_op.get("requestBody", {}))
    cur_body = resolve(cur_doc, cur_op.get("requestBody", {}))
    if isinstance(base_body, dict) and isinstance(cur_body, dict):
        if base_body.get("required") is not True and cur_body.get("required") is True:
            breaks.append(f"{where}: request body became required")
        base_content = base_body.get("content", {})
        cur_content = cur_body.get("content", {})
        if isinstance(base_content, dict) and isinstance(cur_content, dict):
            removed_types = sorted(set(base_content) - set(cur_content))
            if removed_types:
                breaks.append(f"{where}: request content types removed: {removed_types}")
            for media in sorted(set(base_content) & set(cur_content)):
                compare_schema(
                    base_doc,
                    cur_doc,
                    base_content[media].get("schema", {}),
                    cur_content[media].get("schema", {}),
                    f"{where} request {media}",
                    "request",
                    breaks,
                    set(),
                )
    elif not base_body and isinstance(cur_body, dict) and cur_body.get("required") is True:
        breaks.append(f"{where}: new required request body")

    base_responses = base_op.get("responses", {})
    cur_responses = cur_op.get("responses", {})
    if isinstance(base_responses, dict) and isinstance(cur_responses, dict):
        removed_codes = sorted(set(base_responses) - set(cur_responses))
        if removed_codes:
            breaks.append(f"{where}: response codes removed: {removed_codes}")

        for code in sorted(set(base_responses) & set(cur_responses)):
            before = resolve(base_doc, base_responses[code])
            after = resolve(cur_doc, cur_responses[code])
            if not isinstance(before, dict) or not isinstance(after, dict):
                continue
            base_content = before.get("content", {})
            cur_content = after.get("content", {})
            if isinstance(base_content, dict) and isinstance(cur_content, dict):
                removed_types = sorted(set(base_content) - set(cur_content))
                if removed_types:
                    breaks.append(f"{where} response {code}: content types removed: {removed_types}")
                for media in sorted(set(base_content) & set(cur_content)):
                    compare_schema(
                        base_doc,
                        cur_doc,
                        base_content[media].get("schema", {}),
                        cur_content[media].get("schema", {}),
                        f"{where} response {code} {media}",
                        "response",
                        breaks,
                        set(),
                    )

    base_security = base_op.get("security", base_doc.get("security"))
    cur_security = cur_op.get("security", cur_doc.get("security"))
    if base_security == [] and cur_security not in (None, []):
        breaks.append(f"{where}: operation changed from public to authenticated")


def main() -> int:
    if len(sys.argv) != 3:
        print("usage: openapi_compatibility.py BASE.json CURRENT.json", file=sys.stderr)
        return 2

    base_doc = load(sys.argv[1])
    cur_doc = load(sys.argv[2])
    breaks: list[str] = []

    base_paths = base_doc.get("paths", {})
    cur_paths = cur_doc.get("paths", {})
    if not isinstance(base_paths, dict) or not isinstance(cur_paths, dict):
        print("OpenAPI document does not contain a valid paths object.", file=sys.stderr)
        return 2

    for path in sorted(set(base_paths) - set(cur_paths)):
        breaks.append(f"path removed: {path}")

    for path in sorted(set(base_paths) & set(cur_paths)):
        base_item = resolve(base_doc, base_paths[path])
        cur_item = resolve(cur_doc, cur_paths[path])
        if not isinstance(base_item, dict) or not isinstance(cur_item, dict):
            continue

        base_methods = {m for m in base_item if m.lower() in HTTP_METHODS}
        cur_methods = {m for m in cur_item if m.lower() in HTTP_METHODS}

        for method in sorted(base_methods - cur_methods):
            breaks.append(f"operation removed: {method.upper()} {path}")

        for method in sorted(base_methods & cur_methods):
            base_op = resolve(base_doc, base_item[method])
            cur_op = resolve(cur_doc, cur_item[method])
            if isinstance(base_op, dict) and isinstance(cur_op, dict):
                compare_operation(
                    base_doc,
                    cur_doc,
                    path,
                    method,
                    base_item,
                    cur_item,
                    base_op,
                    cur_op,
                    breaks,
                )

    if breaks:
        print("OpenAPI backward-compatibility check FAILED:", file=sys.stderr)
        for item in breaks:
            print(f" - {item}", file=sys.stderr)
        return 1

    print("OpenAPI backward-compatibility check passed: no supported breaking changes detected.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
