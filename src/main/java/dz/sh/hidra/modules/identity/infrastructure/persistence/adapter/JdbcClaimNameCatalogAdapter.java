/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcClaimNameCatalogAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-16
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Reads bounded Identity mapping claim names in one non-authoritative PostgreSQL snapshot.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.ClaimNameCatalogPort;
import dz.sh.hidra.modules.identity.domain.value.ExternalMappingMode;
import dz.sh.hidra.modules.identity.domain.value.ExternalMappingStatus;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This selector deliberately does not acquire the protected-operation fence or certify
 * freshness. It is a private pre-verification hint; a later monotone, transactional mapping
 * epoch and actual source-verification-to-issuance proof are mandatory before any use for
 * authorization. A single SQL statement supplies one READ COMMITTED snapshot.
 */
@Component
public class JdbcClaimNameCatalogAdapter implements ClaimNameCatalogPort {
    private static final int MAX_MAPPING_ROWS = 1024;
    private static final String UNAVAILABLE = "External claim-name catalog unavailable.";

    private static final String SQL = """
        WITH mapping_rows AS (
            SELECT 'GROUP'::text AS family, status::text AS state,
                   mapping_mode::text AS mode, claim_name::text AS claim
              FROM public.hidra_identity_external_group_mapping
             WHERE identity_provider_id=?
            UNION ALL
            SELECT 'ROLE'::text, status::text, mapping_mode::text, claim_name::text
              FROM public.hidra_identity_external_role_mapping
             WHERE identity_provider_id=?
            UNION ALL
            SELECT 'PERMISSION'::text, status::text, mapping_mode::text, claim_name::text
              FROM public.hidra_identity_external_permission_mapping
             WHERE identity_provider_id=?
        ), bounded AS (
            SELECT family,state,mode,claim FROM mapping_rows
            ORDER BY family,state,mode,claim NULLS FIRST
            LIMIT 1025
        )
        SELECT family,state,mode,claim FROM (
            SELECT 'PROVIDER'::text AS family, 'ACTIVE'::text AS state,
                   NULL::text AS mode, NULL::text AS claim
              FROM public.hidra_identity_provider WHERE id=? AND status='ACTIVE'
            UNION ALL
            SELECT family,state,mode,claim FROM bounded
        ) selected
        ORDER BY family,state,mode,claim NULLS FIRST
        """;

    private record MappingRow(String family, String state, String mode, String claim) {}

    private final JdbcTemplate jdbc;

    public JdbcClaimNameCatalogAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    /**
     * One bounded provider-scoped read; no claim values, provider existence or raw SQL errors
     * can be exposed through the public contract. No external provider calls or writes.
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Selection select(String providerId) {
        try {
            validProviderId(providerId);
            List<MappingRow> rows =
                    jdbc.query(
                            SQL,
                            (rs, rowNum) ->
                                    new MappingRow(
                                            rs.getString(1),
                                            rs.getString(2),
                                            rs.getString(3),
                                            rs.getString(4)),
                            providerId,
                            providerId,
                            providerId,
                            providerId);
            if (rows.isEmpty() || rows.size() > MAX_MAPPING_ROWS + 1)
                throw invalid();
            boolean foundProvider = false;
            TreeSet<String> names = new TreeSet<>();
            for (MappingRow row : rows) {
                if ("PROVIDER".equals(row.family())) {
                    if (foundProvider || !"ACTIVE".equals(row.state())
                            || row.mode() != null || row.claim() != null)
                        throw invalid();
                    foundProvider = true;
                    continue;
                }
                ExternalMappingStatus status = ExternalMappingStatus.valueOf(
                        Objects.requireNonNull(row.state()));
                if (status == ExternalMappingStatus.CONFLICT)
                    throw invalid();
                ExternalMappingMode mode = ExternalMappingMode.valueOf(
                        Objects.requireNonNull(row.mode()));
                boolean relevant = switch (row.family()) {
                    case "GROUP" -> {
                        if (mode == ExternalMappingMode.DIRECT_GRANT) throw invalid();
                        yield mode == ExternalMappingMode.ASSERTION_ONLY;
                    }
                    case "ROLE", "PERMISSION" -> {
                        if (mode == ExternalMappingMode.ASSERTION_ONLY
                                || mode == ExternalMappingMode.SYNC_MEMBERSHIP)
                            throw invalid();
                        yield mode == ExternalMappingMode.DIRECT_GRANT;
                    }
                    default -> throw invalid();
                };
                if (status == ExternalMappingStatus.ACTIVE && relevant) {
                    // Selection validates exact Unicode/UTF-8 names via the port's
                    // ClaimCoverage-backed constructor below; never trim or infer names.
                    names.add(row.claim() == null ? "" : row.claim());
                    if (names.size() > 64) throw invalid();
                }
            }
            if (!foundProvider) throw invalid();
            return new Selection(new ArrayList<>(names));
        } catch (RuntimeException failure) {
            // Do not export PostgreSQL detail, provider existence, mapping names or claim values.
            throw invalid();
        }
    }

    private static void validProviderId(String id) {
        if (id == null || id.isBlank() || id.length() > 80
                || id.getBytes(StandardCharsets.UTF_8).length > 320)
            throw invalid();
        for (int i = 0; i < id.length(); i++) {
            char ch = id.charAt(i);
            if (Character.isISOControl(ch)) throw invalid();
            if (Character.isHighSurrogate(ch)) {
                if (++i >= id.length() || !Character.isLowSurrogate(id.charAt(i)))
                    throw invalid();
            } else if (Character.isLowSurrogate(ch)) throw invalid();
        }
    }

    private static IllegalStateException invalid() {
        return new IllegalStateException(UNAVAILABLE);
    }
}
