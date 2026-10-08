/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseWorkOrderReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.semantic
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.assets.semantic;

import dz.sh.hidra.modules.assets.application.service.HseWorkOrderReferenceQueryService;
import dz.sh.hidra.modules.assets.application.port.out.MaintenanceWorkOrderRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.MaintenanceWorkOrder;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class HseWorkOrderReferenceContractTest {
    final MaintenanceWorkOrderRepositoryPort repository=mock(MaintenanceWorkOrderRepositoryPort.class);
    final HseWorkOrderReferenceQueryService service=new HseWorkOrderReferenceQueryService(repository);
    @Test void absentAndMismatchedOwnerReferencesFailClosed() {
        assertFalse(service.exists(null));when(repository.findById("missing")).thenReturn(Optional.empty());assertFalse(service.exists("missing"));
        var order=mock(MaintenanceWorkOrder.class);when(order.id()).thenReturn("other");when(repository.findById("order")).thenReturn(Optional.of(order));assertFalse(service.exists("order"));
    }
    @Test void actualOwnerIdentityIsAcceptedWithoutInventedAssignmentRule() {
        var order=mock(MaintenanceWorkOrder.class);when(order.id()).thenReturn("order");when(repository.findById("order")).thenReturn(Optional.of(order));assertTrue(service.exists("order"));
    }
}
