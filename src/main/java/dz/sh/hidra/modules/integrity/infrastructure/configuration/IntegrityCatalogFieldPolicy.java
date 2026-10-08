/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCatalogFieldPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.configuration
 *
 * @Description : Validates IntegrityCase provenance through explicit owner-controlled references.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.configuration;

import jakarta.persistence.EntityManager;
import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import java.util.Objects;
import org.springframework.stereotype.Component;
/** Field roles are not catalog families; an owner-approved mapping is mandatory. */
@Component
public class IntegrityCatalogFieldPolicy {
    private final EntityManager entityManager;
    public IntegrityCatalogFieldPolicy(EntityManager entityManager) {this.entityManager=Objects.requireNonNull(entityManager);}
    public String requiredFamily(String fieldRole,boolean activeRequired) {
        var rows=entityManager.createNativeQuery("SELECT catalog_name FROM hidra_integrity_catalog_field_policy WHERE field_role=:role AND (:eligible=false OR active=true) FOR SHARE")
                .setParameter("role",fieldRole).setParameter("eligible",activeRequired).getResultList();
        if(rows.size()!=1 || !(rows.get(0) instanceof String family) || family.isBlank())
            throw new InvalidIntegrityValueException("Exactly one eligible owner-approved Integrity catalog mapping required: "+fieldRole);
        return family;
    }
}
