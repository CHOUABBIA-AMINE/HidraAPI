/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseWorkOrderReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.service
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.assets.application.service;

import dz.sh.hidra.modules.assets.application.contract.hse.HseWorkOrderReferenceContract;
import dz.sh.hidra.modules.assets.application.port.out.MaintenanceWorkOrderRepositoryPort;
import java.util.Objects;
import org.springframework.stereotype.Service;
@Service
public class HseWorkOrderReferenceQueryService implements HseWorkOrderReferenceContract {
    private final MaintenanceWorkOrderRepositoryPort repository;
    public HseWorkOrderReferenceQueryService(MaintenanceWorkOrderRepositoryPort repository) {this.repository=Objects.requireNonNull(repository);}
    public boolean exists(String id) {
        return id!=null && !id.isBlank() && repository.findById(id.trim()).filter(order -> id.trim().equals(order.id())).isPresent();
    }
}
