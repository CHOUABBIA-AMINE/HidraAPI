/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationNamespaceJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for ConfigurationNamespace.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.repository;

import dz.sh.hidra.modules.configuration.infrastructure.persistence.entity.ConfigurationNamespaceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ConfigurationNamespace.
 */
@Repository
public interface ConfigurationNamespaceJpaRepository extends JpaRepository<ConfigurationNamespaceJpaEntity, String> {
}
