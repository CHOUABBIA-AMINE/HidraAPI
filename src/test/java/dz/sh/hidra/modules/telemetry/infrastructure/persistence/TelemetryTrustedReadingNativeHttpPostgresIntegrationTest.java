/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTrustedReadingNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence
 *
 * @Description : Asserts T09 native PostgreSQL migration guards on an independent fresh schema.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers(disabledWithoutDocker = true)
class TelemetryTrustedReadingNativeHttpPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("hidra_t09_fresh_guard");

    @Test
    void freshSchemaInstallsTrustedUniquenessBindingIntegrityAndNoImplicitGrants() {
        Flyway.configure().dataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword())
                .load().migrate();
        JdbcTemplate jdbc=new JdbcTemplate(new DriverManagerDataSource(
                PG.getJdbcUrl(),PG.getUsername(),PG.getPassword()));
        for(String constraint:new String[]{
                "fk_t09_trusted_binding","uq_t09_one_trusted_per_raw",
                "uq_t09_one_assessment_per_raw"}) {
            assertEquals(1L,jdbc.queryForObject("""
                    SELECT count(*) FROM pg_catalog.pg_constraint c
                    JOIN pg_catalog.pg_namespace n ON n.oid=c.connamespace
                    WHERE n.nspname='public' AND c.conname=?
                    """,Long.class,constraint));
        }
        for(String trigger:new String[]{
                "trg_hmr053_trusted_creation","trg_t09_validate_binding",
                "trg_t09_assessment_immutable","trg_t09_trusted_delete_immutable"}) {
            assertEquals(1L,jdbc.queryForObject("""
                    SELECT count(*) FROM pg_catalog.pg_trigger
                    WHERE tgname=? AND NOT tgisinternal
                    """,Long.class,trigger));
        }
        assertEquals(1L,jdbc.queryForObject("""
                SELECT count(*) FROM information_schema.columns
                WHERE table_schema='public' AND table_name='hidra_telemetry_trusted_reading'
                  AND column_name='binding_id' AND is_nullable='YES'
                """,Long.class));
        assertEquals(2L,jdbc.queryForObject("""
                SELECT count(*) FROM hidra_identity_permission
                WHERE code IN ('telemetry:trusted-reading:qualify','telemetry:trusted-reading:read')
                  AND status='ACTIVE'
                """,Long.class));
        assertEquals(0L,jdbc.queryForObject("""
                SELECT count(*) FROM hidra_identity_user_permission_grant g
                JOIN hidra_identity_permission p ON p.id=g.permission_id
                WHERE p.code IN ('telemetry:trusted-reading:qualify','telemetry:trusted-reading:read')
                """,Long.class));
    }
}
