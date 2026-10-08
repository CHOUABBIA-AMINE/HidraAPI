/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceRecommendationReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.semantic
 *
 * @Description : Validates owner-controlled MaintenanceWorkOrder references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.integrity.semantic;

import dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter.MaintenanceRecommendationReferenceQueryAdapter;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityRecommendationJpaRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class MaintenanceRecommendationReferenceContractTest {
    @Test void onlyActualIntegrityRecommendationResolves() {
        var repository=mock(IntegrityRecommendationJpaRepository.class);var service=new MaintenanceRecommendationReferenceQueryAdapter(repository);
        assertFalse(service.exists("missing"));assertFalse(service.exists(" "));
        when(repository.existsById("recommendation")).thenReturn(true);assertTrue(service.exists(" recommendation "));
    }
}
