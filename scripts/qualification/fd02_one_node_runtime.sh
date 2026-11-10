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
migration_password="$(fd02_random)"
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
# Only the isolated admin performs initial CREATE ROLE/DB. It is never a runtime identity.
psql -X -q -v ON_ERROR_STOP=1 -v migrator_password="$migration_password" \
     -v runtime_password="$runtime_password" <<'SQL'
CREATE ROLE fd02_migrator LOGIN NOSUPERUSER NOCREATEROLE NOCREATEDB PASSWORD :'migrator_password';
CREATE ROLE fd02_runtime LOGIN NOSUPERUSER NOCREATEROLE NOCREATEDB PASSWORD :'runtime_password';
SQL
psql -X -q -v ON_ERROR_STOP=1 -c 'CREATE DATABASE fd02_qualification OWNER fd02_migrator'

# Migrate with an independent, non-superuser database owner via the very same
# production artifact's Flyway/PostgreSQL libraries; no SQL replay of migrations.
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
export FD02_MIGRATOR_USER='fd02_migrator' FD02_MIGRATOR_PASSWORD="$migration_password"
java -cp "$work:$lib" Fd02Flyway "$repo/src/main/resources/db/migration" migrate \
  >"$work/migrate.log" 2>&1 || { echo 'Flyway fresh-schema migration/validation failed' >&2; exit 1; }
flyway_applied="$(sed -n 's/^FD02_FLYWAY_APPLIED=//p' "$work/migrate.log" | tail -n 1)"
[[ "$flyway_applied" =~ ^[1-9][0-9]*$ ]] || { echo 'No Flyway migration history proved' >&2; exit 1; }

# A single synthetic LOCAL provider is a reference fixture, not a business state transition.
PGPASSWORD="$migration_password" psql -X -q -v ON_ERROR_STOP=1 -h "$PGHOST" -p "$PGPORT" \
  -U fd02_migrator -d fd02_qualification <<'SQL'
DO $$ BEGIN
  IF (SELECT count(*) FROM public.hidra_identity_provider WHERE provider_type='LOCAL' AND status='ACTIVE') <> 0 THEN
    RAISE EXCEPTION 'Expected a fresh schema without active LOCAL providers';
  END IF;
END $$;
INSERT INTO public.hidra_identity_provider
 (id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at)
 VALUES ('fd02-local-provider','FD02_LOCAL','Synthetic FD02 LOCAL','LOCAL',false,false,'ACTIVE',now(),now());
REVOKE CREATE ON SCHEMA public FROM PUBLIC;
REVOKE CREATE ON SCHEMA public FROM fd02_runtime;
GRANT CONNECT ON DATABASE fd02_qualification TO fd02_runtime;
GRANT USAGE ON SCHEMA public TO fd02_runtime;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO fd02_runtime;
GRANT USAGE,SELECT ON ALL SEQUENCES IN SCHEMA public TO fd02_runtime;
SELECT format('GRANT INSERT,UPDATE,DELETE ON TABLE public.%I TO fd02_runtime;',tablename)
 FROM pg_catalog.pg_tables
 WHERE schemaname='public' AND tablename LIKE 'hidra_identity_%'\gexec
-- Explicitly preserve denial of protected engineering internals from the ordinary runtime connection.
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

start_jvm() {
  java -jar "$jar" >"$work/jvm-${1}.log" 2>&1 &
  jvm_pid="$!"
  for _ in $(seq 1 150); do
    if ! kill -0 "$jvm_pid" 2>/dev/null; then
      echo "FD02 JVM ${1} exited before readiness (inspect private runner log)" >&2; exit 1
    fi
    if curl --silent --fail --max-time 2 "$FD02_BASE_URL/actuator/health/readiness" \
        | grep -q '"status":"UP"'; then
      return 0
    fi
    sleep 2
  done
  echo "FD02 JVM ${1} did not become ready" >&2; exit 1
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
          'separate_non_superuser_migration_and_runtime_roles':True,
          'ordinary_runtime_ddl_and_protected_read_denied':True,
          'bootstrap_disabled_on_second_os_process':True,
          'same_ids_after_new_os_process':True,
          'http_checks_initial':a['case_count'],'http_checks_after_restart':b['case_count'],
          'runtime_business_acceptance':'not-qualified-by-smoke'}
(root/'evidence.json').write_text(json.dumps(evidence,sort_keys=True,indent=2)+'\n')
print('FD02 single-node qualification PASS; source='+evidence['source_sha']+' redacted evidence at '+str(root))
PY
