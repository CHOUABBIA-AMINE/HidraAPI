/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTypeTranslationJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for telemetry catalog translations.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryTypeTranslationJpaEntity;

/**
 * Spring Data repository for telemetry catalog translations.
 */
public interface TelemetryTypeTranslationJpaRepository extends JpaRepository<TelemetryTypeTranslationJpaEntity, String> {

    List<TelemetryTypeTranslationJpaEntity> findByTypeId(String typeId);

    Optional<TelemetryTypeTranslationJpaEntity> findByTypeIdAndLocale(String typeId, String locale);
}
