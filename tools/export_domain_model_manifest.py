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


# Standalone OOXML writer: serializes code declarations only, no runtime records.
import zipfile
from collections import Counter
from xml.sax.saxutils import escape
from xml.etree import ElementTree

def col(n):
    out = ""
    while n:
        n, digit = divmod(n-1, 26)
        out = chr(65+digit) + out
    return out

def xmlsheet(rows, widths):
    last = col(max(map(len, rows)))
    p = ['<?xml version="1.0" encoding="UTF-8" standalone="yes"?>',
         '<worksheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main">',
         '<sheetViews><sheetView workbookViewId="0"><pane ySplit="1" topLeftCell="A2" activePane="bottomLeft" state="frozen"/></sheetView></sheetViews>',
         '<sheetFormatPr defaultRowHeight="17"/><cols>']
    for n,width in enumerate(widths,1):
        p.append(f'<col min="{n}" max="{n}" width="{width}" customWidth="1"/>')
    p.append('</cols><sheetData>')
    for i,row in enumerate(rows,1):
        p.append(f'<row r="{i}">')
        for j,value in enumerate(row,1):
            if value is None: continue
            cell=col(j)+str(i)
            st=' s="1"' if i==1 else ''
            if isinstance(value,int):
                p.append(f'<c r="{cell}"{st}><v>{value}</v></c>')
            else:
                p.append(f'<c r="{cell}" t="inlineStr"{st}><is><t xml:space="preserve">{escape(str(value))}</t></is></c>')
        p.append('</row>')
    p.extend(['</sheetData>',f'<autoFilter ref="A1:{last}{len(rows)}"/>','</worksheet>'])
    return ''.join(p)

BASE='f49cdd9c508f7f936abc91e7a59d84cb37e8aeb3'
baseurl='https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/'+BASE+'/'
mods=sorted(set(x[0] for x in items))
summary=[['Module','Models','Declared fields','Baseline']]
models=[['Module','Model','Declaration','Declared fields','Java source URL']]
fields=[['Module','Model','Field','Declared Java type','Declaration','Java source URL']]
tabs=[('README',[['HidraAPI model catalogue','Notes'],
    ['Repository baseline',BASE],
    ['Scope','Production Java domain models, 24 modules'],
    ['Declared models',len(items)],
    ['Declared fields',sum(len(x[3]) for x in items)],
    ['Java field types','Declared Java types, not PostgreSQL column types'],
    ['Import eligibility','This inventory does not approve source-data import'],
    ['Source files','Each model and field provides its exact pinned Java source path'],
    ['Data protection','Code declarations only; no source workbook rows or credentials']], [30,110])]
for mod in mods:
    m=[x for x in items if x[0]==mod]
    summary.append([mod,len(m),sum(len(x[3]) for x in m),BASE])
for mod,name,kind,parts,path in items:
    url=baseurl+path
    models.append([mod,name,kind,len(parts),url])
    for field,type_name in parts:
        fields.append([mod,name,field,type_name,kind,url])
tabs.extend([('Modules',summary,[23,16,22,47]),
             ('Models',models,[23,36,18,20,105]),
             ('Fields',fields,[23,36,35,36,18,105])])
for mod in mods:
    rows=[['Model','Field','Declared Java type','Declaration','Java source URL']]
    for module,name,kind,parts,path in items:
        if module==mod:
            rows.extend([name,f,t,kind,baseurl+path] for f,t in parts)
    tabs.append((mod,rows,[37,35,36,18,105]))
wb=['<?xml version="1.0" encoding="UTF-8" standalone="yes"?>',
'<workbook xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"><sheets>']
rels=['<?xml version="1.0" encoding="UTF-8" standalone="yes"?>',
'<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">']
types=['<?xml version="1.0" encoding="UTF-8" standalone="yes"?>',
'<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">',
'<Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>',
'<Default Extension="xml" ContentType="application/xml"/>',
'<Override PartName="/xl/workbook.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml"/>',
'<Override PartName="/xl/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml"/>']
sty='<?xml version="1.0" encoding="UTF-8" standalone="yes"?><styleSheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main"><fonts count="2"><font><sz val="10"/><name val="Aptos"/></font><font><b/><sz val="11"/><color rgb="FFFFFFFF"/><name val="Aptos"/></font></fonts><fills count="3"><fill><patternFill patternType="none"/></fill><fill><patternFill patternType="gray125"/></fill><fill><patternFill patternType="solid"><fgColor rgb="FF17324F"/><bgColor indexed="64"/></patternFill></fill></fills><borders count="1"><border><left/><right/><top/><bottom/><diagonal/></border></borders><cellStyleXfs count="1"><xf numFmtId="0" fontId="0" fillId="0" borderId="0"/></cellStyleXfs><cellXfs count="2"><xf numFmtId="0" fontId="0" fillId="0" borderId="0" xfId="0"/><xf numFmtId="0" fontId="1" fillId="2" borderId="0" xfId="0" applyFont="1" applyFill="1"/></cellXfs><cellStyles count="1"><cellStyle name="Normal" xfId="0" builtinId="0"/></cellStyles></styleSheet>'
output='HidraAPI_domain_models_fields_by_module.xlsx'
with zipfile.ZipFile(output,'w',compression=zipfile.ZIP_DEFLATED,compresslevel=7) as z:
    for n,(title,rows,widths) in enumerate(tabs,1):
        wb.append(f'<sheet name="{escape(title)}" sheetId="{n}" r:id="rId{n}"/>')
        rels.append(f'<Relationship Id="rId{n}" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet" Target="worksheets/sheet{n}.xml"/>')
        types.append(f'<Override PartName="/xl/worksheets/sheet{n}.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"/>')
        z.writestr(f'xl/worksheets/sheet{n}.xml',xmlsheet(rows,widths))
    wb.append('</sheets></workbook>')
    rels.append(f'<Relationship Id="rId{len(tabs)+1}" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/></Relationships>')
    types.append('</Types>')
    z.writestr('[Content_Types].xml',''.join(types))
    z.writestr('xl/workbook.xml',''.join(wb))
    z.writestr('xl/_rels/workbook.xml.rels',''.join(rels))
    z.writestr('xl/styles.xml',sty)
    z.writestr('_rels/.rels','<?xml version="1.0" encoding="UTF-8"?><Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships"><Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="xl/workbook.xml"/></Relationships>')
with zipfile.ZipFile(output) as z:
    assert z.testzip() is None
    assert len(z.namelist())==len(tabs)+5
    for part in z.namelist():
        if part.endswith('.xml'): ElementTree.fromstring(z.read(part))
print('XLSX_CATALOG_READY',json.dumps({'file':output,'sheets':len(tabs),'modules':len(mods),'models':len(items),'fields':len(fields)-1,'bytes':Path(output).stat().st_size,'issues':len(errors)}))
