/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCatalogFieldPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.configuration
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.configuration;

import jakarta.persistence.EntityManager;
import java.util.Objects;
import org.springframework.stereotype.Component;
/** Explicit owner-approved metadata; no inferred catalog family or permissive default. */
@Component
public class HseCatalogFieldPolicy {
    private final EntityManager entityManager;
    public HseCatalogFieldPolicy(EntityManager entityManager) {this.entityManager=Objects.requireNonNull(entityManager);}
    public String requiredFamily(String fieldRole) {return requiredFamily(fieldRole,true);}
    public String requiredFamily(String fieldRole,boolean activeRequired) {
        var rows=entityManager.createNativeQuery("SELECT catalog_name FROM hidra_hse_catalog_field_policy WHERE field_role=:role AND (:eligible=false OR active=true) FOR SHARE")
                .setParameter("role",fieldRole).setParameter("eligible",activeRequired).getResultList();
        if(rows.size()!=1 || !(rows.get(0) instanceof String family) || family.isBlank())
            throw new IllegalArgumentException("Exactly one active owner-approved HSE catalog mapping required: "+fieldRole);
        return family;
    }
}
