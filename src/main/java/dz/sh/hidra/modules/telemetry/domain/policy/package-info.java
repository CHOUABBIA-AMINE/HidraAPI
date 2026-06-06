/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : package-info
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : PackageInfo
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Telemetry domain policy package boundary.
 *
 */
/**
 * Telemetry domain policy package boundary.
 *
 * <p>Responsibility:
 * This package defines telemetry domain policy package boundary.
 *
 * <p>What belongs here:
 * pure telemetry business concepts, value objects, policies, services, catalogs, and domain models without framework dependencies.
 *
 * <p>What is forbidden here:
 * Spring, JPA, REST DTOs, repositories, platform infrastructure, topology implementation classes, flow calculation, risk scoring, analytics, or persistence code.
 *
 * <p>Boundary rules:
 * Telemetry owns industrial acquisition metadata, telemetry points, topology bindings, raw readings,
 * ingestion audit, quality references, catalog references, and localized labels. Telemetry must use
 * neutral references to topology assets and must not import topology domain or infrastructure classes.
 * Business taxonomy concepts must be modeled as catalog references, not Java enums.
 */
package dz.sh.hidra.modules.telemetry.domain.policy;
