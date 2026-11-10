#!/usr/bin/env bash
# FD-02: one disposable PostgreSQL 16 + one live JVM at a time. Never deploys.
set -Eeuo pipefail
umask 077
repo="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
cd "$repo"
for program in java javac psql pg_isready python3 unzip sha256sum curl git; do
  command -v "$program" >/dev/null || { echo "Missing FD02 prerequisite: $program" >&2; exit 2; }
done
: "${FD02_PG_ADMIN_PASSWORD:?Isolated PostgreSQL admin password required}"
: "${FD02_PG_HOST:=127.0.0.1}"
: "${FD02_PG_PORT:=5432}"
: "${FD02_EVIDENCE_DIR:=${RUNNER_TEMP:-/tmp}/fd02-evidence}"
mkdir -p "$FD02_EVIDENCE_DIR"
work="$(mktemp -d)"
jvm_pid=''
cleanup() {
  if [[ -n "$jvm_pid" ]] && kill -0 "$jvm_pid" 2>/dev/null; then
    kill "$jvm_pid" 2>/dev/null || :
    wait "$jvm_pid" 2>/dev/null || :
  fi
  rm -rf "$work"
}
trap cleanup EXIT

head_sha="$(git rev-parse HEAD)"
[[ "$head_sha" =~ ^[0-9a-f]{40}$ ]] || { echo 'Invalid exact source SHA' >&2; exit 2; }
[[ -z "$(git status --porcelain --untracked-files=no)" ]] || { echo 'FD02 requires an unmodified checkout' >&2; exit 2; }
[[ "$(java -version 2>&1 | head -n 1)" == *'21.'* ]] || { echo 'FD02 requires Java 21' >&2; exit 2; }

# Synthesize unique credentials at runtime; no production secrets or database fixtures are stored in Git.
fd02_random() { python3 -c 'import secrets;print(secrets.token_hex(32))'; }
runtime_password="$(fd02_random)"
admin_password="$(fd02_random)"
signing_key="$(fd02_random)"
export FD02_ADMIN_USERNAME='fd02-admin' FD02_ADMIN_PASSWORD="$admin_password"
export FD02_BASE_URL='http://127.0.0.1:18765'
export PGHOST="$FD02_PG_HOST" PGPORT="$FD02_PG_PORT"
export PGPASSWORD="$FD02_PG_ADMIN_PASSWORD" PGUSER='postgres' PGDATABASE='postgres'

pg_isready -h "$PGHOST" -p "$PGPORT" -U postgres >/dev/null || { echo 'FD02 PostgreSQL is unavailable' >&2; exit 2; }
pg_version="$(psql -X -t -A -v ON_ERROR_STOP=1 -c 'SHOW server_version_num')"
[[ "$pg_version" =~ ^16[0-9]{4}$ ]] || { echo 'FD02 requires PostgreSQL 16' >&2; exit 2; }
# Only the isolated PostgreSQL bootstrap administrator provisions this disposable
# database and runs migration DDL. The Java application never uses these rights.
# pgcrypto's C-language objects must share the trusted migration owner required
# by the existing Identity commit-proof catalog invariant.
psql -X -q -v ON_ERROR_STOP=1 -v runtime_password="$runtime_password" <<'SQL'
CREATE ROLE fd02_runtime LOGIN NOSUPERUSER NOCREATEROLE NOCREATEDB PASSWORD :'runtime_password';
-- Permission-only, non-login Audit append role. The JVM still connects as
-- fd02_runtime, inheriting INSERT on exactly one Audit-owned table.
CREATE ROLE fd02_audit_append_writer NOLOGIN NOSUPERUSER NOCREATEROLE NOCREATEDB;
SQL
psql -X -q -v ON_ERROR_STOP=1 -c 'CREATE DATABASE fd02_qualification OWNER postgres'

# Migrate as the isolated privileged schema owner, not as the ordinary runtime
# account. Reuse the actual packaged Flyway/PostgreSQL libraries and original
# immutable migrations; no SQL replay or migration edits.
echo 'Building the pinned Java 21 artifact for FD02 ...'
bash ./mvnw -B -ntp -DskipTests package >"$work/build.log" 2>&1 || {
  echo 'FD02 Maven artifact build failed (inspect private runner build log)' >&2; exit 1;
}
jar="$(find target -maxdepth 1 -type f -name '*.jar' ! -name '*.original' | head -n 1)"
[[ -n "$jar" && -f "$jar" ]] || { echo 'No executable JAR was produced' >&2; exit 1; }
jar="$(realpath "$jar")"
jar_sha="$(sha256sum "$jar" | cut -d' ' -f1)"
mkdir -p "$work/artifact"
unzip -q "$jar" 'BOOT-INF/lib/*' -d "$work/artifact"
lib="$work/artifact/BOOT-INF/lib/*"
cat > "$work/Fd02Flyway.java" <<'JAVA'
import org.flywaydb.core.Flyway;
public final class Fd02Flyway {
    public static void main(String[] args) {
        Flyway migrations = Flyway.configure()
            .dataSource(System.getenv("FD02_JDBC_URL"),
                System.getenv("FD02_MIGRATOR_USER"), System.getenv("FD02_MIGRATOR_PASSWORD"))
            .locations("filesystem:" + args[0]).load();
        if ("migrate".equals(args[1])) migrations.migrate();
        migrations.validate();
        System.out.println("FD02_FLYWAY_APPLIED=" + migrations.info().applied().length);
    }
}
JAVA
javac -cp "$lib" "$work/Fd02Flyway.java"
export FD02_JDBC_URL="jdbc:postgresql://${PGHOST}:${PGPORT}/fd02_qualification"
export FD02_MIGRATOR_USER='postgres' FD02_MIGRATOR_PASSWORD="$FD02_PG_ADMIN_PASSWORD"
java -cp "$work:$lib" Fd02Flyway "$repo/src/main/resources/db/migration" migrate \
  >"$work/migrate.log" 2>&1 || { echo 'Flyway fresh-schema migration/validation failed' >&2; exit 1; }
flyway_applied="$(sed -n 's/^FD02_FLYWAY_APPLIED=//p' "$work/migrate.log" | tail -n 1)"
[[ "$flyway_applied" =~ ^[1-9][0-9]*$ ]] || { echo 'No Flyway migration history proved' >&2; exit 1; }

# Synthetic LOCAL and Audit catalog reference fixtures, never business transitions.
PGPASSWORD="$FD02_PG_ADMIN_PASSWORD" psql -X -q -v ON_ERROR_STOP=1 -h "$PGHOST" -p "$PGPORT" \
  -U postgres -d fd02_qualification <<'SQL'
DO $$ BEGIN
  IF (SELECT count(*) FROM public.hidra_identity_provider WHERE provider_type='LOCAL' AND status='ACTIVE') <> 0 THEN
    RAISE EXCEPTION 'Expected a fresh schema without active LOCAL providers';
  END IF;
END $$;
INSERT INTO public.hidra_identity_provider
 (id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at)
 VALUES ('fd02-local-provider','FD02_LOCAL','Synthetic FD02 LOCAL','LOCAL',false,false,'ACTIVE',now(),now());
-- The native bootstrap runner currently supplies these strings directly as
-- Audit eventTypeId and eventCategoryId. Its domain/persistence contracts
-- require matching active, correctly-familied catalog IDs.
DO $$ BEGIN
  IF EXISTS (
    SELECT 1 FROM public.hidra_audit_catalog_entry
    WHERE id IN ('IDENTITY_LOCAL_ADMIN_BOOTSTRAP', 'SECURITY_ADMINISTRATION')
  ) THEN
    RAISE EXCEPTION 'FD02 synthetic Audit catalog IDs unexpectedly pre-exist';
  END IF;
END $$;
INSERT INTO public.hidra_audit_catalog_entry
  (id, catalog_name, code, active, sort_order, system_defined, created_at, updated_at)
VALUES
  ('IDENTITY_LOCAL_ADMIN_BOOTSTRAP', 'EVENT_TYPE', 'IDENTITY_LOCAL_ADMIN_BOOTSTRAP', true, 900, false, now(), now()),
  ('SECURITY_ADMINISTRATION', 'EVENT_CATEGORY', 'SECURITY_ADMINISTRATION', true, 901, false, now(), now());
REVOKE CREATE ON SCHEMA public FROM PUBLIC;
REVOKE CREATE ON SCHEMA public FROM fd02_runtime;
GRANT CONNECT ON DATABASE fd02_qualification TO fd02_runtime;
GRANT USAGE ON SCHEMA public TO fd02_runtime;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO fd02_runtime;
GRANT USAGE,SELECT ON ALL SEQUENCES IN SCHEMA public TO fd02_runtime;
SELECT format('GRANT INSERT,UPDATE,DELETE ON TABLE public.%I TO fd02_runtime;',tablename)
 FROM pg_catalog.pg_tables
 WHERE schemaname='public' AND tablename LIKE 'hidra_identity_%'
   AND tablename NOT LIKE 'hidra_identity_org_commit_%'\gexec
-- A separate NOLOGIN Audit capability controls the sole extra write required
-- by LocalAdministratorBootstrapRunner. No Audit UPDATE/DELETE/TRUNCATE,
-- no schema DDL, no table ownership, no role SET or membership delegation.
GRANT INSERT ON TABLE public.hidra_audit_event TO fd02_audit_append_writer;
GRANT fd02_audit_append_writer TO fd02_runtime
  WITH ADMIN FALSE, INHERIT TRUE, SET FALSE;
REVOKE UPDATE,DELETE,TRUNCATE ON TABLE public.hidra_audit_event
  FROM fd02_runtime, fd02_audit_append_writer;
-- Verify both authoritative catalog families and role membership/ACL shape.
DO $$ BEGIN
  IF (SELECT count(*) FROM public.hidra_audit_catalog_entry
      WHERE (id='IDENTITY_LOCAL_ADMIN_BOOTSTRAP' AND catalog_name='EVENT_TYPE'
         OR id='SECURITY_ADMINISTRATION' AND catalog_name='EVENT_CATEGORY')
        AND active AND NOT system_defined) <> 2 THEN
    RAISE EXCEPTION 'FD02 approved synthetic Audit taxonomy not effective';
  END IF;
  IF NOT EXISTS (
    SELECT 1 FROM pg_catalog.pg_auth_members m
      JOIN pg_catalog.pg_roles parent ON parent.oid=m.roleid
      JOIN pg_catalog.pg_roles member ON member.oid=m.member
    WHERE parent.rolname='fd02_audit_append_writer'
      AND member.rolname='fd02_runtime'
      AND m.inherit_option AND NOT m.set_option AND NOT m.admin_option
  ) OR EXISTS (
    SELECT 1 FROM pg_catalog.pg_roles
      WHERE rolname='fd02_audit_append_writer' AND rolcanlogin
  ) THEN
    RAISE EXCEPTION 'FD02 Audit append capability has unsafe membership/LOGIN';
  END IF;
  IF NOT has_table_privilege('fd02_runtime','public.hidra_audit_event','INSERT')
     OR has_table_privilege('fd02_runtime','public.hidra_audit_event','UPDATE')
     OR has_table_privilege('fd02_runtime','public.hidra_audit_event','DELETE')
     OR has_table_privilege('fd02_runtime','public.hidra_audit_event','TRUNCATE') THEN
    RAISE EXCEPTION 'FD02 runtime Audit capability exceeds INSERT-only';
  END IF;
END $$;
-- Granting broad table reads above must not expose the Identity commit-proof
-- key/provenance store or protected engineering internals. These are not
-- application-owned regular tables and their existing owner ACLs stay closed.
REVOKE ALL ON public.hidra_identity_org_commit_key_event,
    public.hidra_identity_org_commit_key_head,
    public.hidra_identity_org_commit_proof_use FROM fd02_runtime;
REVOKE ALL ON public.hidra_topology_engineering_baseline_revision FROM fd02_runtime;
REVOKE ALL ON public.hidra_topology_station_configuration_revision FROM fd02_runtime;
SQL

# SQL authority checks are narrowly targeted, not a substitute for module-level security CI.
if PGPASSWORD="$runtime_password" psql -X -q -v ON_ERROR_STOP=1 -h "$PGHOST" -p "$PGPORT" \
    -U fd02_runtime -d fd02_qualification -c 'CREATE TABLE public.fd02_forbidden (id integer)' \
    >"$work/denied-ddl.log" 2>&1; then
  echo 'FAIL: ordinary app unexpectedly possesses schema DDL privilege' >&2; exit 1
fi
if PGPASSWORD="$runtime_password" psql -X -q -v ON_ERROR_STOP=1 -h "$PGHOST" -p "$PGPORT" \
    -U fd02_runtime -d fd02_qualification \
    -c 'SELECT count(*) FROM public.hidra_topology_engineering_baseline_revision' \
    >"$work/denied-protected.log" 2>&1; then
  echo 'FAIL: ordinary app unexpectedly reads protected engineering source' >&2; exit 1
fi
if PGPASSWORD="$runtime_password" psql -X -q -v ON_ERROR_STOP=1 -h "$PGHOST" -p "$PGPORT" \
    -U fd02_runtime -d fd02_qualification \
    -c 'SELECT count(*) FROM public.hidra_identity_org_commit_key_head' \
    >"$work/denied-identity-key.log" 2>&1; then
  echo 'FAIL: ordinary app unexpectedly reads the Identity commit-proof key' >&2; exit 1
fi

# Prove fail-closed: even with the inherited Audit append role, the ordinary
# runtime cannot switch roles or mutate/delete append-only Audit records.
if PGPASSWORD="$runtime_password" psql -X -q -v ON_ERROR_STOP=1 -h "$PGHOST" -p "$PGPORT" \
    -U fd02_runtime -d fd02_qualification -c 'SET ROLE fd02_audit_append_writer' \
    >"$work/denied-audit-set-role.log" 2>&1; then
  echo 'FAIL: ordinary runtime can SET ROLE to Audit append capability' >&2; exit 1
fi
if PGPASSWORD="$runtime_password" psql -X -q -v ON_ERROR_STOP=1 -h "$PGHOST" -p "$PGPORT" \
    -U fd02_runtime -d fd02_qualification -c 'UPDATE public.hidra_audit_event SET action_code=action_code WHERE false' \
    >"$work/denied-audit-update.log" 2>&1; then
  echo 'FAIL: ordinary runtime can UPDATE immutable Audit evidence' >&2; exit 1
fi
if PGPASSWORD="$runtime_password" psql -X -q -v ON_ERROR_STOP=1 -h "$PGHOST" -p "$PGPORT" \
    -U fd02_runtime -d fd02_qualification -c 'DELETE FROM public.hidra_audit_event WHERE false' \
    >"$work/denied-audit-delete.log" 2>&1; then
  echo 'FAIL: ordinary runtime can DELETE immutable Audit evidence' >&2; exit 1
fi

export HIDRA_ENVIRONMENT='production' SPRING_PROFILES_ACTIVE='production'
export HIDRA_DATASOURCE_URL="$FD02_JDBC_URL" HIDRA_DATASOURCE_USERNAME='fd02_runtime'
export HIDRA_DATASOURCE_PASSWORD="$runtime_password"
export HIDRA_DATASOURCE_MIN_IDLE='1' HIDRA_DATASOURCE_MAX_POOL_SIZE='4'
export SPRING_FLYWAY_ENABLED='false'  # migrated/validated independently by Flyway, never the runtime actor
export SERVER_PORT='18765' MANAGEMENT_SERVER_PORT='18765'
export HIDRA_SECURITY_AUTHENTICATION_MODE='jwt'
export HIDRA_CORS_ALLOWED_ORIGINS='http://127.0.0.1:3000'
export HIDRA_JWT_HMAC_SECRET="$signing_key"
export HIDRA_JWT_TOKEN_ISSUER='http://127.0.0.1:18765/hidra-api'
export HIDRA_SECURITY_BOOTSTRAP_USERNAME="$FD02_ADMIN_USERNAME"
export HIDRA_SECURITY_BOOTSTRAP_PASSWORD="$admin_password"
export HIDRA_SECURITY_BOOTSTRAP_ENABLED='true'
export HIDRA_OPENAPI_ENABLED='false' HIDRA_SWAGGER_UI_ENABLED='false'

# Emit only a fixed-category diagnostic derived from private startup logs.
# Never upload/publish the JVM logs, exception messages, SQL literals or secrets.
fd02_startup_diagnostic() {
  python3 - "$work/jvm-${1}.log" "$2" <<'PY'
import json
from pathlib import Path
import re
import sys

body = Path(sys.argv[1]).read_text(encoding='utf-8', errors='replace')
exit_status = sys.argv[2]
if exit_status != 'TIMEOUT' and not re.fullmatch(r'[0-9]{1,3}', exit_status):
    raise ValueError('Unexpected process status')
if exit_status != 'TIMEOUT' and int(exit_status) > 255:
    raise ValueError('Unexpected process status')
low = body.lower()
rules = (
    ('JAR_NOT_EXECUTABLE', 'no main manifest attribute'),
    ('JAR_UNAVAILABLE', 'unable to access jarfile'),
    ('MAIN_CLASS_MISSING', 'could not find or load main class'),
    ('JAVA_CLASS_LINKAGE', 'noclassdeffounderror'),
    ('POSTGRES_ACCESS_DENIED', 'permission denied for'),
    ('POSTGRES_REFERENCE_FAILURE', 'violates foreign key constraint'),
    ('POSTGRES_CHECK_FAILURE', 'violates check constraint'),
    ('HIBERNATE_SCHEMA', 'schema-validation:'),
    ('SPRING_DATASOURCE', 'failed to configure a datasource'),
    ('SPRING_BINDING', 'failed to bind properties'),
    ('PRODUCTION_GUARD', 'production runtime requires'),
    ('PRODUCTION_GUARD', 'hidra_jwt_token_issuer must be'),
    ('SPRING_STARTUP_FAILURE', 'application failed to start'),
)
category = next((kind for kind, marker in rules if marker in low), None)
classes = re.findall(
    r'(?:Caused by:\s*|Exception in thread "[^"]*"\s+|^\s*)'
    r'(?:[A-Za-z_$][\w$]*\.)*([A-Z][A-Za-z0-9_$]{0,63}(?:Exception|Error))(?=[:\s]|$)',
    body, re.M)
classes = list(dict.fromkeys(classes))[-4:]
if category is None:
    category = ('NOT_READY_TIMEOUT' if exit_status == 'TIMEOUT'
                else 'PROCESS_TERMINATED' if int(exit_status) in (137, 143)
                else 'JAVA_EXCEPTION_UNCLASSIFIED' if classes
                else 'EMPTY_LOG' if not body.strip()
                else 'OTHER_NONEMPTY_LOG')
stage = ('GUARD' if 'HidraProductionStartupGuard' in body
         else 'ADMIN_BOOTSTRAP' if 'LocalAdministratorBootstrapRunner' in body
         else 'BEAN_CREATION' if 'BeanCreationException' in body
         else 'UNKNOWN')
summary = {'category': category, 'exit_status': exit_status,
           'exception_types': classes, 'stage': stage,
           'log_lines': min(len(body.splitlines()), 999999)}
print('FD02_STARTUP_DIAGNOSTIC=' + json.dumps(summary, sort_keys=True))
PY
}

start_jvm() {
  # The CI migration owner and privileged PostgreSQL password belong to
  # fixture setup/validation only. Use a sanitized *child* environment;
  # the parent retains them for later independent Flyway revalidation.
  (
    unset FD02_PG_ADMIN_PASSWORD FD02_MIGRATOR_PASSWORD FD02_MIGRATOR_USER
    unset PGPASSWORD PGUSER PGDATABASE FD02_ADMIN_PASSWORD
    exec java -jar "$jar"
  ) >"$work/jvm-${1}.log" 2>&1 &
  jvm_pid="$!"
  for _ in $(seq 1 150); do
    if ! kill -0 "$jvm_pid" 2>/dev/null; then
      jvm_exit_status=0
      wait "$jvm_pid" || jvm_exit_status=$?
      jvm_pid=''
      fd02_startup_diagnostic "$1" "$jvm_exit_status" >&2
      echo "FD02 JVM ${1} exited before readiness; private log discarded on cleanup" >&2; exit 1
    fi
    if curl --silent --fail --max-time 2 "$FD02_BASE_URL/actuator/health/readiness" \
        | grep -q '"status":"UP"'; then
      # Prove process isolation without ever printing the environment values.
      [[ -r "/proc/$jvm_pid/environ" ]] || {
        echo 'FD02 cannot inspect JVM migration-credential isolation' >&2; exit 1;
      }
      if tr '\0' '\n' < "/proc/$jvm_pid/environ" | grep -E \
          '^(FD02_PG_ADMIN_PASSWORD|FD02_MIGRATOR_PASSWORD|FD02_MIGRATOR_USER|PGPASSWORD|PGUSER|PGDATABASE|FD02_ADMIN_PASSWORD)=' >/dev/null; then
        echo 'FAIL: privileged migration or redundant credential reached the application JVM' >&2; exit 1
      fi
      return 0
    fi
    sleep 2
  done
  fd02_startup_diagnostic "$1" TIMEOUT >&2
  echo "FD02 JVM ${1} did not become ready; private log retained only until cleanup" >&2; exit 1
}
stop_jvm() {
  [[ -n "$jvm_pid" ]] || return 1
  kill "$jvm_pid"
  for _ in $(seq 1 30); do
    if ! kill -0 "$jvm_pid" 2>/dev/null; then
      wait "$jvm_pid" 2>/dev/null || :
      jvm_pid=''
      return 0
    fi
    sleep 1
  done
  echo 'FD02 initial JVM could not stop cleanly' >&2; exit 1
}

state="$work/committed-ids.json"
start_jvm 'initial'
initial_pid="$jvm_pid"
# The initial bootstrap must have persisted exactly one Audit event with the
# catalog references accepted by the real JPA/trigger path, not synthetic SQL.
bootstrap_audit_count="$(PGPASSWORD="$FD02_PG_ADMIN_PASSWORD" psql -X -q -A -t -v ON_ERROR_STOP=1 \
  -h "$PGHOST" -p "$PGPORT" -U postgres -d fd02_qualification \
  -c "SELECT count(*) FROM public.hidra_audit_event
        WHERE source_component='LocalAdministratorBootstrapRunner'
          AND action_code='identity.local-admin.bootstrap'
          AND event_type_id='IDENTITY_LOCAL_ADMIN_BOOTSTRAP'
          AND event_category_id='SECURITY_ADMINISTRATION'")"
[[ "$bootstrap_audit_count" == '1' ]] || {
  echo 'FD02 native bootstrap Audit event not persisted exactly once' >&2; exit 1;
}
# The existing HMR-095 trigger must also reject an UPDATE by the disposable
# schema owner, proving immutability is enforced by the database itself rather
# than merely by the ordinary runtime's missing UPDATE privilege.
if PGPASSWORD="$FD02_PG_ADMIN_PASSWORD" psql -X -q -v ON_ERROR_STOP=1 -h "$PGHOST" -p "$PGPORT" \
    -U postgres -d fd02_qualification \
    -c "UPDATE public.hidra_audit_event SET action_code=action_code
          WHERE source_component='LocalAdministratorBootstrapRunner'
            AND action_code='identity.local-admin.bootstrap'" \
    >"$work/denied-audit-trigger.log" 2>&1; then
  echo 'FAIL: immutable Audit event trigger allowed schema-owner UPDATE' >&2; exit 1
fi
python3 scripts/qualification/fd02_native_http_checks.py --phase initial --state "$state" \
  --output "$FD02_EVIDENCE_DIR/initial.json"
stop_jvm
HIDRA_SECURITY_BOOTSTRAP_ENABLED='false'
export HIDRA_SECURITY_BOOTSTRAP_ENABLED
start_jvm 'restart'
[[ "$jvm_pid" != "$initial_pid" ]] || { echo 'FD02 process identity did not change' >&2; exit 1; }
python3 scripts/qualification/fd02_native_http_checks.py --phase restart --state "$state" \
  --output "$FD02_EVIDENCE_DIR/restart.json"
java -cp "$work:$lib" Fd02Flyway "$repo/src/main/resources/db/migration" validate \
  >"$work/revalidate.log" 2>&1 || { echo 'Flyway post-restart validation failed' >&2; exit 1; }

# Public, nonsecret evidence only. No raw connection parameters, credentials, tokens or log bodies.
FD02_EXPECTED_SHA="$head_sha" FD02_JAR_SHA="$jar_sha" FD02_PG_VERSION="$pg_version" \
FD02_MIGRATIONS="$flyway_applied" python3 - <<'PY'
import json, os
from pathlib import Path
root=Path(os.environ['FD02_EVIDENCE_DIR'])
a=json.loads((root/'initial.json').read_text())
b=json.loads((root/'restart.json').read_text())
assert a['business_id']==b['business_id'] and a['administrator_id']==b['administrator_id']
evidence={'task':'HAPI-932/FD-02','qualification':'synthetic-single-node',
          'source_sha':os.environ['FD02_EXPECTED_SHA'],'jar_sha256':os.environ['FD02_JAR_SHA'],
          'postgres_server_version_num':os.environ['FD02_PG_VERSION'],
          'flyway_applied_migrations':int(os.environ['FD02_MIGRATIONS']),
          'flyway_validated_pre_and_post_restart':True,
          'isolated_privileged_schema_migration_and_separate_non_superuser_runtime':True,
          'ordinary_runtime_ddl_and_protected_read_denied':True,
          'synthetic_audit_catalog_families_verified':True,
          'separate_nologin_inherited_insert_only_audit_role_verified':True,
          'runtime_audit_update_delete_and_set_role_denied':True,
          'native_bootstrap_audit_insert_count':1,
          'audit_immutable_trigger_denied_schema_owner_update':True,
          'privileged_migration_credentials_absent_from_jvm_environ':True,
          'bootstrap_disabled_on_second_os_process':True,
          'same_ids_after_new_os_process':True,
          'http_checks_initial':a['case_count'],'http_checks_after_restart':b['case_count'],
          'runtime_business_acceptance':'not-qualified-by-smoke'}
(root/'evidence.json').write_text(json.dumps(evidence,sort_keys=True,indent=2)+'\n')
print('FD02 single-node qualification PASS; source='+evidence['source_sha']+' redacted evidence at '+str(root))
PY
