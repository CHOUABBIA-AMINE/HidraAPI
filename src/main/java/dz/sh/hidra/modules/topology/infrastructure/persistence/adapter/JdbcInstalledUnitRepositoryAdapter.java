/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcInstalledUnitRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persists private installed-unit identity and versioned effective installation evidence.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.InstalledUnitRepositoryPort;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit.Evidence;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit.Verification;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnitInstallation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Owner-private storage only. Never expose this adapter as REST or a foreign module
 * contract. Callers must coordinate protected permission and atomic transactions.
 */
@Repository
public final class JdbcInstalledUnitRepositoryAdapter implements InstalledUnitRepositoryPort {
    private final JdbcTemplate jdbc;

    public JdbcInstalledUnitRepositoryAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    @Override
    @Transactional
    public InstalledUnit register(InstalledUnit unit) {
        Objects.requireNonNull(unit, "Physical unit required");
        jdbc.update("""
            INSERT INTO hidra_topology_installed_unit (
              id,company_party_id,equipment_kind_id,
              manufacturer_party_value,manufacturer_party_verification,manufacturer_party_source,
              manufacturer_model_value,manufacturer_model_verification,manufacturer_model_source,
              serial_value,serial_verification,serial_source,maintainable_asset_id)
            VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT (id) DO NOTHING
            """,
            unit.id(),unit.companyPartyId(),unit.equipmentKindId(),
            unit.manufacturerParty().value(),unit.manufacturerParty().verification().name(),unit.manufacturerParty().sourceReference(),
            unit.manufacturerModel().value(),unit.manufacturerModel().verification().name(),unit.manufacturerModel().sourceReference(),
            unit.serialNumber().value(),unit.serialNumber().verification().name(),unit.serialNumber().sourceReference(),
            unit.maintainableAssetId());
        var stored = findUnit(unit.companyPartyId(),unit.id())
                .orElseThrow(() -> invalid("Physical identity is owned by another company."));
        if (!unit.equals(stored)) throw invalid("Conflicting immutable physical identity.");
        return stored;
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<InstalledUnit> findUnit(String companyPartyId, String unitId) {
        return jdbc.query("""
            SELECT * FROM hidra_topology_installed_unit WHERE company_party_id=? AND id=?
            """, (rs,n) -> new InstalledUnit(rs.getString("id"),rs.getString("company_party_id"),
                rs.getString("equipment_kind_id"),
                evidence(rs,"manufacturer_party"),evidence(rs,"manufacturer_model"),evidence(rs,"serial"),
                rs.getString("maintainable_asset_id")),
            exact(companyPartyId),exact(unitId)).stream().findFirst();
    }

    @Override
    @Transactional
    public InstalledUnitInstallation record(InstalledUnitInstallation installation) {
        Objects.requireNonNull(installation, "Installation required");
        jdbc.update("""
            INSERT INTO hidra_topology_installed_unit_installation (
                installation_id,unit_id,position_id,company_party_id,
                effective_from,effective_until,recorded_at,evidence_reference,revision)
            VALUES (?,?,?,?,?,?,?,?,?) ON CONFLICT (installation_id) DO NOTHING
            """,
            installation.installationId(),installation.installedUnitId(),installation.equipmentPositionId(),
            installation.companyPartyId(),sqlInstant(installation.effectiveFrom()),sqlInstant(installation.effectiveUntil()),
            sqlInstant(installation.recordedAt()),installation.evidenceReference(),installation.revision());
        var stored = findInstallation(installation.companyPartyId(),installation.installationId())
                .orElseThrow(() -> invalid("Installation identity is not available in company scope."));
        if (!installation.equals(stored)) throw invalid("Conflicting immutable installation identity.");
        return stored;
    }

    @Override
    @Transactional
    public InstalledUnitInstallation close(String companyPartyId, String installationId,
                                           long expectedRevision, Instant effectiveUntil) {
        if (effectiveUntil == null || expectedRevision<1) throw invalid("Expected revision and close time required.");
        int count=jdbc.update("""
            UPDATE hidra_topology_installed_unit_installation
            SET effective_until=?, revision=revision+1
            WHERE company_party_id=? AND installation_id=? AND revision=? AND effective_until IS NULL
            """, sqlInstant(effectiveUntil),exact(companyPartyId),exact(installationId),expectedRevision);
        if(count!=1)throw invalid("Stale or absent open installation.");
        return findInstallation(companyPartyId,installationId).orElseThrow();
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<InstalledUnitInstallation> findInstallation(String companyPartyId,String installationId) {
        return jdbc.query("""
            SELECT * FROM hidra_topology_installed_unit_installation
            WHERE company_party_id=? AND installation_id=?
            """, (rs,n)->readInstallation(rs),exact(companyPartyId),exact(installationId))
            .stream().findFirst();
    }

    @Override
    @Transactional(readOnly=true)
    public List<InstalledUnitInstallation> history(String companyPartyId,String installationId) {
        return List.copyOf(jdbc.query("""
            SELECT * FROM hidra_topology_installed_unit_installation_event
            WHERE company_party_id=? AND installation_id=? ORDER BY revision
            """,(rs,n)->readInstallation(rs),exact(companyPartyId),exact(installationId)));
    }

    private static Evidence evidence(ResultSet row,String prefix) throws SQLException {
        return new Evidence(row.getString(prefix+"_value"),
                Verification.valueOf(row.getString(prefix+"_verification")),
                row.getString(prefix+"_source"));
    }
    private static InstalledUnitInstallation readInstallation(ResultSet rs) throws SQLException {
        return new InstalledUnitInstallation(rs.getString("installation_id"),rs.getString("unit_id"),
            rs.getString("position_id"),rs.getString("company_party_id"),
            instant(rs,"effective_from"),instant(rs,"effective_until"),
            instant(rs,"recorded_at"),rs.getString("evidence_reference"),rs.getLong("revision"));
    }
    private static Instant instant(ResultSet rs,String column) throws SQLException {
        OffsetDateTime value=rs.getObject(column,OffsetDateTime.class);
        return value==null?null:value.toInstant();
    }
    private static java.sql.Timestamp sqlInstant(Instant value) {
        return value==null?null:java.sql.Timestamp.from(value);
    }
    private static String exact(String id) {
        if (id==null || id.isBlank() || !id.equals(id.trim()))
            throw invalid("Exact company-scoped identity required.");
        return id;
    }
    private static InvalidTopologyValueException invalid(String reason) {
        return new InvalidTopologyValueException(reason);
    }
}
