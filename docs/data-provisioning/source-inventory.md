# HyFloAPI legacy source inventory — HDP-002

> **Execution status: In Progress / partial inventory.** Source Git-tree manifest, accessible small SQL metadata and CSV cardinality are recorded; full workbook worksheet names/row counts, binary attachment content and the two large SQL dumps require an approved byte-capable extraction method. **Do not mark HDP-002 complete or start HDP-003 until these evidence gaps are resolved or formally accepted in the roadmap.** This file contains metadata only, not credentials, industrial rows, workbook samples or SQL data.

## 1. Source provenance and extraction

- Source repository: [`CHOUABBIA-AMINE/HyFloAPI`](https://github.com/CHOUABBIA-AMINE/HyFloAPI); repository custody: GitHub account `CHOUABBIA-AMINE`; **business owner, collection authority, license and permission to redistribute remain unknown**.
- Pinned Git commit (not a business effective date): [`f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/commit/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b).
- Scope: `src/main/resources/data/**` and `src/main/resources/extras/**`; complete recursive Git tree at pinned SHA reported `truncated=false`.
- Tree result: **31 blobs/files**, **1 nested directory** (`data/LPL/`); the two requested root directories are not counted as files.
- Extraction method used: connected GitHub API recursive Git-tree metadata (path, byte size, Git **blob SHA-1**); UTF-8 file reads of accessible small text/SQL and coordinate CSV, followed by static metadata parsing. Git blob SHA-1 is a *Git object identity/content checksum*, **not** a conventional SHA-256 of the file. Separate SHA-256 digests must be computed during approved byte-capable extraction if required.
- Binary workbook/archive/document reads: the available GitHub text connector rejects binary content; workbook sheet names/row counts, internal document metadata and file-level SHA-256 have **not** been verified. Two multi-megabyte SQL sources returned empty text via this connector and remain uninspected. No legacy SQL or script was executed, and no target database was accessed.
- Candidate source snapshot dates: filename dates and text export headers are **unverified source claims**, not authoritative effective dates. The `1-11-2026` suffix appears in two filenames and must not be parsed as a validated date or precedence marker. The pinned source Git commit is not proof of the data's period of applicability.
- Preliminary access classification: treat all unreviewed industrial/operational Excel, SQL, CSV and attachments as **restricted pending data-owner/security review**, irrespective of the repository visibility. No source rows or sensitive values are reproduced here.

## 2. Recursive manifest (31 files)

The SHA column is the original source repository Git **blob** SHA-1. Sizes are bytes reported by Git. Each path links to the pinned original source file. `data/LPL/` contains three files shown below. Root directory metadata and any empty untracked folders are outside a Git-tree manifest.

| Source path under `src/main/resources/` | Format | Bytes | Source blob SHA-1 | Access / preliminary observation |
|---|---|---:|---|---|
| [`data/Algerian Infrastructure Database.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/Algerian%20Infrastructure%20Database.xlsx) | XLSX | 130045 | `8b7256a3274071b406ff4a02e8a13e4625bf5232` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`data/Commits HyFlo -- AI.docx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/Commits%20HyFlo%20--%20AI.docx) | DOCX | 45217 | `b21424b2f60204c29c0ff57056155654f49e33f1` | Binary attachment: content uninspected; classify/review access before use. |
| [`data/Data Network.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/Data%20Network.xlsx) | XLSX | 223714 | `90495ab6a2ee86c7ea56d794b753b59c21fe7434` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`data/data_only.sql`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/data_only.sql) | SQL | 226994 | `89c34674447b4e5a683705d018eb8442776601a1` | SQL text inspected; 50 distinct referenced table identifiers; 50 INSERT statements (NOT row count). |
| [`data/Fiche Passation.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/Fiche%20Passation.xlsx) | XLSX | 2117120 | `9da334fe5e5904b836d0c568a5a67f74b76ba1f3` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`data/hyflo_db.sql`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/hyflo_db.sql) | SQL | 3097082 | `6e0abfb75b396d3a9ab8f7df17a27843aef01129` | Large SQL: text fetch returned empty; table names and row counts unverified. |
| [`data/HyFlo_db.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/HyFlo_db.xlsx) | XLSX | 557747 | `7a75ab4de6edcf3880ef3ab9db25455e5ea536ca` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`data/hyflo_v01.sql`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/hyflo_v01.sql) | SQL | 237139 | `00cee72bf0753fcfd3f9102be51a53ed6c6ad4d6` | SQL text inspected; 56 distinct referenced table identifiers; 37 INSERT statements (NOT row count). |
| [`data/hyflo_v03 SDR.sql`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/hyflo_v03%20SDR.sql) | SQL | 274688 | `35d26ff6f8dbc9cb126befb7f4d7766caee4ce6e` | SQL text inspected; 62 distinct referenced table identifiers; 33 INSERT statements (NOT row count). |
| [`data/hyflo_v03.1.sql`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/hyflo_v03.1.sql) | SQL | 275617 | `5c45d99c9b875ebc28b7526755350757c12b2345` | SQL text inspected; 57 distinct referenced table identifiers; 42 INSERT statements (NOT row count). |
| [`data/hyflo_v03.sql`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/hyflo_v03.sql) | SQL | 278543 | `036cbf0a1fd1e7be3ad8f5865c0ae6d265a73c36` | SQL text inspected; 62 distinct referenced table identifiers; 38 INSERT statements (NOT row count). |
| [`data/hyflo_ws_01.sql`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/hyflo_ws_01.sql) | SQL | 337040 | `308788197be696cebeb868a5b97ee641dcfb8a6a` | SQL text inspected; 76 distinct referenced table identifiers; 54 INSERT statements (NOT row count). |
| [`data/hyflo-progress 1-11-2026.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/hyflo-progress%201-11-2026.xlsx) | XLSX | 633635 | `1b14f129190df45d1d79923a912faebedd451e0b` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`data/hyflo-progress.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/hyflo-progress.xlsx) | XLSX | 592327 | `c0e274aa2fdac53da6eb26ac2f2be8cbb231f6ab` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`data/iaas_db.sql`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/iaas_db.sql) | SQL | 3272382 | `1201c2e94cf115890975d9feeb5fe4fe0b09f78a` | Large SQL: text fetch returned empty; table names and row counts unverified. |
| [`data/Locations 1-11-2026.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/Locations%201-11-2026.xlsx) | XLSX | 22586 | `88e2f49af9a3454f80e8fb4314159c8a3ddfd616` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`data/Locations.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/Locations.xlsx) | XLSX | 40709 | `1bec90ddd917f2b7c83aa4c5cc3fcbf023de51d8` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`data/LPL/Code.png`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/LPL/Code.png) | PNG | 178361 | `6e1b2c196afc80f559743ea4046b010b5f415b57` | Binary attachment: content uninspected; classify/review access before use. |
| [`data/LPL/Code.txt`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/LPL/Code.txt) | TXT | 57 | `335d9c15f2753e924a104375c77cf9d8c36aa1d4` | RESTRICTED: plaintext credential indicators verified; do not echo/import; incident response needed. |
| [`data/LPL/Notification.pdf`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/LPL/Notification.pdf) | PDF | 142020 | `3ffb909d858ce1c1f0ed73f2cd9630724dcc3766` | Binary attachment: content uninspected; classify/review access before use. |
| [`data/Plan 2026 BSR.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/Plan%202026%20BSR.xlsx) | XLSX | 1105768 | `93ab2225caaa4c0455826ad498bb933895ab26fc` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`data/Primary key.txt`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/Primary%20key.txt) | TXT | 139 | `be7f2b873e5ff88f7b9511dcd0fcbf3d687c1e9f` | Text source: format/semantics need approved review; no business owner established. |
| [`data/roadmap.txt`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/roadmap.txt) | TXT | 510 | `9be7c714a3ef5e763e6377938de2777e8197ae3e` | Text source: format/semantics need approved review; no business owner established. |
| [`data/Segment 2026.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/Segment%202026.xlsx) | XLSX | 653442 | `db9fc7ee5d68552f774d3551fbcaf6d3de548129` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`data/Segment.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/Segment.xlsx) | XLSX | 71104 | `a08db204436843c268a48a4a63936fb4f63916d5` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`data/test.txt`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/test.txt) | TXT | 0 | `e69de29bb2d1d6434b8b29ae775ad8c2e48c5391` | Empty file (0 bytes); no rows. |
| [`data/Vendor.txt`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/data/Vendor.txt) | TXT | 5514 | `9657e1f61dc41409633de175b2e5bc5a8835ba18` | Text source: format/semantics need approved review; no business owner established. |
| [`extras/algeria_pipeline_coordinates_with_locations.csv`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/extras/algeria_pipeline_coordinates_with_locations.csv) | CSV | 30375 | `5da535a86aeec174cb0a013c7d7e5793fc516701` | Header parsed; 370 data records, 9 columns; CRS and source lineage unverified. |
| [`extras/geocode_pipelines.py`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/extras/geocode_pipelines.py) | Python | 6050 | `5d1305654bdbac6f5c6ccb6a017742d3d9a63995` | Source code; not executed, not a data table; review dependencies and geocoding sources before use. |
| [`extras/hyflo-progress.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/extras/hyflo-progress.xlsx) | XLSX | 569408 | `50099aa7b898c47fdde98da2b51326928ebb39b5` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |
| [`extras/Locations.xlsx`](https://github.com/CHOUABBIA-AMINE/HyFloAPI/blob/f4dc6aa6a9a6f08e78df8146d4b40c336be41c9b/src/main/resources/extras/Locations.xlsx) | XLSX | 74682 | `70e7aa402a3052f6a6f76c4f8c2a463714bcd0fd` | Binary workbook: worksheet names, counts, content/date/sensitivity NOT extracted by available UTF-8 connector. |

## 3. Parsed coordinate CSV (metadata only)

Source: `extras/algeria_pipeline_coordinates_with_locations.csv`, blob `5da535a86aeec174cb0a013c7d7e5793fc516701`.

- Header (9 columns): `FID`, `Point_Number`, `Longitude`, `Latitude`, `Location`, `Commune_Name`, `Commune_Code`, `Wilaya_Name`, `Wilaya_Code`.
- Parsed **370 nonempty data records**, excluding header; **0 malformed-width records** by CSV quoting/column-count parser. The file's physical line count is not used as a record count.
- The fields suggest a pipeline-point table with a source `FID`, point order, coordinates and stated commune/wilaya names and codes. **No geometric topology, accuracy, CRS, administrative correctness, or WGS84 designation has been confirmed.**
- Numeric coordinate ranges and potential outliers require reviewed domain/geographical checks in HDP-007; no coordinates are adopted as authoritative in HDP-002.
- Confidentiality and organizational release approval are outstanding. CSV record counts are preliminary syntactic inventory, not import-ready or deduplicated business counts.

## 4. SQL source inventories

The source `data_only.sql` declares a MariaDB dump. The other readable named SQL snapshots contain phpMyAdmin/MySQL-family export headers. **Treat every SQL file as inert text:** never execute legacy dumps against HidraAPI PostgreSQL, and do not assume SQL versions in filenames are ordered canonical releases.

Regular-expression static extraction identifies quoted/unquoted simple `CREATE TABLE` and `INSERT INTO`/`REPLACE INTO` table identifiers. These are *referenced identifiers*, not verified schema ownership, row counts, complete SQL grammar analysis, or an approved list of target tables. Large SQL dumps whose content is unavailable remain explicit gaps.

### `data_only.sql`

Header family: MariaDB/MySQL-family export header. Header-supplied timestamp (unverified source claim): not found. `0` distinct CREATE TABLE identifiers; `50` distinct INSERT/REPLACE target identifiers; `50` INSERT statements. **Do not mistake INSERT-statement count for the number of rows.**

Identified table names (50; derived from static SQL patterns, not SQL execution):

```text
r_t000201_t000203
r_t000202_t000201
r_t000202_t000203
r_t000203_t000204
t_00_01_01
t_00_02_01
t_00_02_02
t_00_02_03
t_00_02_04
t_00_02_05
t_00_04_01
t_01_01_01
t_01_02_01
t_01_02_02
t_01_02_03
t_01_02_04
t_01_02_05
t_01_02_06
t_01_03_01
t_01_03_02
t_01_03_03
t_01_03_04
t_02_01_01
t_02_01_02
t_02_01_03
t_02_01_04
t_02_01_05
t_02_01_06
t_02_01_07
t_02_01_08
t_02_02_01
t_02_02_02
t_02_02_03
t_02_02_04
t_02_02_05
t_02_03_01
t_02_03_02
t_02_03_03
t_02_03_04
t_02_03_05
t_02_03_07
t_02_03_08
t_03_01_01
t_03_01_02
t_03_02_01
t_03_02_02
t_03_02_03
t_03_02_04
t_03_02_05
t_03_02_06
```

### `hyflo_db.sql`

**Unavailable:** GitHub text-file connector yielded empty content for this large SQL blob; table/row inventory pending approved binary/streaming extraction.

### `hyflo_v01.sql`

Header family: MariaDB/MySQL-family export header. Header-supplied timestamp (unverified source claim): `-- Généré le : sam. 10 jan. 2026 à 22:11`. `56` distinct CREATE TABLE identifiers; `36` distinct INSERT/REPLACE target identifiers; `37` INSERT statements. **Do not mistake INSERT-statement count for the number of rows.**

Identified table names (56; derived from static SQL patterns, not SQL execution):

```text
r_t000201_t000203
r_t000202_t000201
r_t000202_t000203
r_t000203_t000204
r_t020303_t020307
r_t020304_t020307
r_t020305_t020201
r_t020305_t020204
r_t020305_t020307
r_t020307_t010205
t_00_01_01
t_00_02_01
t_00_02_02
t_00_02_03
t_00_02_04
t_00_02_05
t_00_03_01
t_00_04_01
t_01_01_01
t_01_02_01
t_01_02_02
t_01_02_03
t_01_02_04
t_01_02_05
t_01_03_01
t_01_03_02
t_01_03_03
t_01_03_04
t_02_01_01
t_02_01_02
t_02_01_03
t_02_01_04
t_02_01_05
t_02_01_06
t_02_01_07
t_02_01_08
t_02_02_01
t_02_02_02
t_02_02_03
t_02_02_04
t_02_02_05
t_02_03_01
t_02_03_02
t_02_03_03
t_02_03_04
t_02_03_05
t_02_03_06
t_02_03_07
t_02_03_08
t_02_03_09
t_02_04_01
t_02_04_02
t_02_04_03
t_02_04_04
t_02_04_05
t_02_04_06
```

### `hyflo_v03 SDR.sql`

Header family: MariaDB/MySQL-family export header. Header-supplied timestamp (unverified source claim): `-- Generation Time: Jan 15, 2026 at 01:18 PM`. `62` distinct CREATE TABLE identifiers; `32` distinct INSERT/REPLACE target identifiers; `33` INSERT statements. **Do not mistake INSERT-statement count for the number of rows.**

Identified table names (62; derived from static SQL patterns, not SQL execution):

```text
r_t000201_t000203
r_t000202_t000201
r_t000202_t000203
r_t000203_t000204
r_t020303_t020307
r_t020304_t020302
r_t020304_t020307
r_t020305_t020201
r_t020305_t020204
r_t020305_t020308
r_t020306_t020201
r_t020306_t020204
r_t020308_t010206
t_00_01_01
t_00_02_01
t_00_02_02
t_00_02_03
t_00_02_04
t_00_02_05
t_00_03_01
t_00_04_01
t_01_01_01
t_01_02_01
t_01_02_02
t_01_02_03
t_01_02_04
t_01_02_05
t_01_02_06
t_01_03_01
t_01_03_02
t_01_03_03
t_01_03_04
t_02_01_01
t_02_01_02
t_02_01_03
t_02_01_04
t_02_01_05
t_02_01_06
t_02_01_07
t_02_01_08
t_02_01_09
t_02_02_01
t_02_02_02
t_02_02_03
t_02_02_04
t_02_02_05
t_02_03_01
t_02_03_02
t_02_03_03
t_02_03_04
t_02_03_05
t_02_03_06
t_02_03_07
t_02_03_08
t_02_03_09
t_02_03_10
t_02_04_01
t_02_04_02
t_02_04_03
t_02_04_04
t_02_04_05
t_02_04_06
```

### `hyflo_v03.1.sql`

Header family: MariaDB/MySQL-family export header. Header-supplied timestamp (unverified source claim): `-- Généré le : jeu. 22 jan. 2026 à 00:34`. `57` distinct CREATE TABLE identifiers; `41` distinct INSERT/REPLACE target identifiers; `42` INSERT statements. **Do not mistake INSERT-statement count for the number of rows.**

Identified table names (57; derived from static SQL patterns, not SQL execution):

```text
r_t000201_t000203
r_t000202_t000201
r_t000202_t000203
r_t000203_t000204
r_t020303_t020308
r_t020304_t020302
r_t020304_t020308
r_t020305_t020201
r_t020305_t020204
r_t020305_t020308
r_t020306_t020201
r_t020306_t020204
r_t020308_t010206
t_00_01_01
t_00_02_01
t_00_02_02
t_00_02_03
t_00_02_04
t_00_02_05
t_00_03_01
t_00_04_01
t_01_01_01
t_01_02_01
t_01_02_02
t_01_02_03
t_01_02_04
t_01_02_05
t_01_02_06
t_01_02_07
t_01_03_01
t_01_03_02
t_01_03_03
t_01_03_04
t_02_01_01
t_02_01_02
t_02_01_03
t_02_01_04
t_02_01_05
t_02_01_06
t_02_01_07
t_02_01_08
t_02_01_09
t_02_02_01
t_02_02_02
t_02_02_03
t_02_02_04
t_02_02_05
t_02_03_01
t_02_03_02
t_02_03_03
t_02_03_04
t_02_03_05
t_02_03_06
t_02_03_07
t_02_03_08
t_02_03_09
t_02_03_10
```

### `hyflo_v03.sql`

Header family: MariaDB/MySQL-family export header. Header-supplied timestamp (unverified source claim): `-- Généré le : lun. 19 jan. 2026 à 01:06`. `62` distinct CREATE TABLE identifiers; `37` distinct INSERT/REPLACE target identifiers; `38` INSERT statements. **Do not mistake INSERT-statement count for the number of rows.**

Identified table names (62; derived from static SQL patterns, not SQL execution):

```text
r_t000201_t000203
r_t000202_t000201
r_t000202_t000203
r_t000203_t000204
r_t020303_t020308
r_t020304_t020302
r_t020304_t020308
r_t020305_t020201
r_t020305_t020204
r_t020305_t020308
r_t020306_t020201
r_t020306_t020204
r_t020308_t010206
t_00_01_01
t_00_02_01
t_00_02_02
t_00_02_03
t_00_02_04
t_00_02_05
t_00_03_01
t_00_04_01
t_01_01_01
t_01_02_01
t_01_02_02
t_01_02_03
t_01_02_04
t_01_02_05
t_01_02_06
t_01_03_01
t_01_03_02
t_01_03_03
t_01_03_04
t_02_01_01
t_02_01_02
t_02_01_03
t_02_01_04
t_02_01_05
t_02_01_06
t_02_01_07
t_02_01_08
t_02_01_09
t_02_02_01
t_02_02_02
t_02_02_03
t_02_02_04
t_02_02_05
t_02_03_01
t_02_03_02
t_02_03_03
t_02_03_04
t_02_03_05
t_02_03_06
t_02_03_07
t_02_03_08
t_02_03_09
t_02_03_10
t_02_04_01
t_02_04_02
t_02_04_03
t_02_04_04
t_02_04_05
t_02_04_06
```

### `hyflo_ws_01.sql`

Header family: MariaDB/MySQL-family export header. Header-supplied timestamp (unverified source claim): `-- Generation Time: Feb 04, 2026 at 02:50 PM`. `76` distinct CREATE TABLE identifiers; `53` distinct INSERT/REPLACE target identifiers; `54` INSERT statements. **Do not mistake INSERT-statement count for the number of rows.**

Identified table names (76; derived from static SQL patterns, not SQL execution):

```text
r_t000201_t000203
r_t000202_t000201
r_t000202_t000203
r_t000203_t000204
r_t020303_t020308
r_t020304_t020302
r_t020304_t020308
r_t020305_t020201
r_t020305_t020204
r_t020305_t020308
r_t020306_t020201
r_t020306_t020204
r_t020308_t010206
r_t020308_t020205
t_00_00_01
t_00_01_01
t_00_02_01
t_00_02_02
t_00_02_03
t_00_02_04
t_00_02_05
t_00_03_01
t_00_04_01
t_00_05_01
t_00_05_02
t_01_01_01
t_01_02_01
t_01_02_02
t_01_02_03
t_01_02_04
t_01_02_05
t_01_02_06
t_01_02_07
t_01_03_01
t_01_03_02
t_01_03_03
t_01_03_04
t_02_01_01
t_02_01_02
t_02_01_03
t_02_01_04
t_02_01_05
t_02_01_06
t_02_01_07
t_02_01_08
t_02_01_09
t_02_02_01
t_02_02_02
t_02_02_03
t_02_02_04
t_02_02_05
t_02_03_01
t_02_03_02
t_02_03_03
t_02_03_04
t_02_03_05
t_02_03_06
t_02_03_07
t_02_03_08
t_02_03_09
t_02_03_10
t_03_01_01
t_03_01_02
t_03_02_01
t_03_02_02
t_03_02_03
t_03_02_04
t_03_02_05
t_03_02_06
t_03_02_07
t_03_03_01
t_03_03_02
t_03_03_03
t_03_03_04
t_03_03_05
t_03_03_06
```

### `iaas_db.sql`

**Unavailable:** GitHub text-file connector yielded empty content for this large SQL blob; table/row inventory pending approved binary/streaming extraction.

## 5. Provenance, duplicate/version and sensitivity register

| Source family | Verified existence | Provenance/authority status | Action for HDP-002 completion or later decision |
|---|---|---|---|
| `Locations.xlsx` in `data/` and `extras/`, plus `Locations 1-11-2026.xlsx` | Three distinct blobs; the repeated basename represents **different file contents** (distinct blob SHA-1). | Effective dates, sheet names, row counts, provenance and precedence not established. | Byte-level workbook inspection and worksheet manifest; owner decision about versions belongs to HDP-004/HDP-006, not this task. |
| `hyflo-progress.xlsx` in `data/` and `extras/` plus `hyflo-progress 1-11-2026.xlsx` | Three distinct blobs; no deduplication/precedence decision supported. | Possible operating/history/reporting meaning is filename inference only; content uninspected. | Inspect workbook tabs and content sensitivity; do not import as reference by default. |
| `Segment.xlsx` / `Segment 2026.xlsx`, `Data Network.xlsx`, `Algerian Infrastructure Database.xlsx` | Distinct workbook blobs. | Likely industrial/topology candidates based only on names; actual record/field sets and business owner not verified. | Review exact tabs/row counts, source authority and protection before target mapping. |
| `hyflo_*.sql`, `iaas_db.sql`, `data_only.sql` | Multiple distinct SQL dumps and divergent static table-identifier inventories where readable. | MariaDB/MySQL-family legacy text; canonical snapshot and row-level overlaps not established. | Approved stream/byte extraction for large SQL; inspect all tables and counts; later choose precedence and map to current HidraAPI schema. |
| `Fiche Passation.xlsx`, `Plan 2026 BSR.xlsx` | Distinct workbooks. | Operational/transmission/planning meaning is **not verified** by filename; may include personal or restricted operational data. | Require content classification and business-owner authorization; no operational records imported under this reference-inventory task. |
| `data/LPL/Code.txt` | **Confirmed** plaintext user-code/password-labelled fields in source text. | **Restricted credential exposure** in source repository; contents deliberately excluded. | Notify source owner/security, revoke/rotate any live credential and review exposure/history under a **separate authorized security task**; never include credential values in HidraAPI, logs, fixtures or this inventory. |
| `data/LPL/Code.png`, `data/LPL/Notification.pdf` | Binary source files exist. | Contents uninspected; may corroborate sensitive LPL details. | Restricted review required; do not publish copies or assume safe. |
| `extras/geocode_pipelines.py` | Python source exists; **not executed**. | Algorithm, network dependencies, external geocoding provider and coordinate source/CRS unverified. | Conduct security/provenance review before any use; no generated coordinates accepted. |
| `Primary key.txt`, `Vendor.txt`, `roadmap.txt`, `test.txt`, `Commits HyFlo -- AI.docx` | Supporting reference/documentation files exist; `test.txt` is 0 bytes. | Not automatically importable datasets; detailed semantics and document content not fully reviewed. | Record as supporting evidence only; restrict document and vendor information pending classification. |

**Explicit uncertainties and stop conditions:** no named accountable business data owner yet; no approved sensitivity/licensing review; no approved source-version precedence; workbook tab lists and row counts unavailable in the current connected text-only environment; `hyflo_db.sql` and `iaas_db.sql` table/row inventories pending reliable byte/stream access. Further source inspection must not expose or reproduce credential material.

## 6. HDP-002 validation and state

- [x] Verified source repository pinned commit and complete recursive Git tree (`truncated=false`), 31 files including nested `LPL/`.
- [x] Recorded per-file path, type, byte size and source blob SHA-1 for **every** Git-tracked file in both scoped directories.
- [x] Parsed accessible SQL source files for static table identifiers and statement counts; flagged inaccessible SQL separately.
- [x] Parsed the coordinate CSV header and syntactic row count without releasing individual rows.
- [x] Documented known credential exposure without copying credential values, and identified binary/unreviewed/conflicting source families.
- [ ] Extract real worksheet/tab names and row counts from all Excel workbooks using a reviewed, authorized binary-capable reader.
- [ ] Inspect two large SQL files sufficiently to list their SQL table identifiers and obtain verified counts where feasible.
- [ ] Confirm source ownership, distribution permissions and sensitivity classification with responsible source owner.
- [ ] Record raw-byte SHA-256 digests if required by downstream import design.
- [ ] Mark HDP-002 completed only when missing evidence is resolved or explicitly accepted as a documented limitation by an authorized reviewer.

**Validation performed:** deterministic manifest cardinality check (31 blobs / one nested directory), verified CSV parser (370 nonempty records / nine fields / zero malformed widths), source Git SHA checks and static SQL inspections; documentation-only change, so **Maven tests and PostgreSQL/Flyway migrations were not run**. No source files or secrets were copied into HidraAPI. GitHub PR/CI evidence belongs to the PR and should be recorded only after runs complete.

**Next:** finish HDP-002 source inspection and security authorization; proceed to HDP-003 only after HDP-002 is completed and merged.
