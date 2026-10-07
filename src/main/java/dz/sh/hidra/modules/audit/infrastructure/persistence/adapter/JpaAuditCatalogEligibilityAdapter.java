/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditCatalogEligibilityAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Enforces Audit evidence integrity and explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;
import dz.sh.hidra.modules.audit.application.port.out.AuditCatalogEligibilityPort;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditCatalogEntryJpaRepository;
import dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException;
import org.springframework.stereotype.Component;
@Component
public final class JpaAuditCatalogEligibilityAdapter implements AuditCatalogEligibilityPort {
    private final AuditCatalogEntryJpaRepository repository;
    public JpaAuditCatalogEligibilityAdapter(AuditCatalogEntryJpaRepository repository) {this.repository=java.util.Objects.requireNonNull(repository);}
    public void requireActive(String id, String family) {
        var row=repository.findById(id).orElseThrow(()->new InvalidAuditValueException("Audit catalog reference is unknown."));
        if(!row.active() || !family.equals(row.catalogName())) throw new InvalidAuditValueException("Audit catalog reference has wrong family or is inactive.");
    }
}
