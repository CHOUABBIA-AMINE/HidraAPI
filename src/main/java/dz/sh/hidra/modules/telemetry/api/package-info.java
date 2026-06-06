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
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api
 *
 * @Description : Telemetry API layer boundary.
 *
 */
/**
 * Telemetry API layer boundary.
 *
 * <p>Responsibility:
 * This package defines telemetry API layer boundary.
 *
 * <p>What belongs here:
 * REST request DTOs, response DTOs, mappers, controllers, and API configuration for telemetry endpoints.
 *
 * <p>What is forbidden here:
 * domain policy enforcement, persistence access, JPA entities, database migrations, or application business logic.
 *
 * <p>Boundary rules:
 * Telemetry owns industrial acquisition metadata, telemetry points, topology bindings, raw readings,
 * ingestion audit, quality references, catalog references, and localized labels. Telemetry must use
 * neutral references to topology assets and must not import topology domain or infrastructure classes.
 * Business taxonomy concepts must be modeled as catalog references, not Java enums.
 */
package dz.sh.hidra.modules.telemetry.api;
