/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceRecommendationReferenceQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Validates owner-controlled MaintenanceWorkOrder references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.contract.assets.MaintenanceRecommendationReferenceContract;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityRecommendationJpaRepository;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public final class MaintenanceRecommendationReferenceQueryAdapter implements MaintenanceRecommendationReferenceContract {
    private final IntegrityRecommendationJpaRepository repository;
    public MaintenanceRecommendationReferenceQueryAdapter(IntegrityRecommendationJpaRepository repository) {this.repository=Objects.requireNonNull(repository);}
    @Override public boolean exists(String id) {return id!=null && !id.isBlank() && repository.existsById(id.trim());}
}
