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
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Telemetry inbound application port package boundary.
 *
 */
/**
 * Telemetry inbound application port package boundary.
 *
 * <p>Responsibility:
 * This package defines telemetry inbound application port package boundary.
 *
 * <p>What belongs here:
 * commands, queries, DTOs, ports, and use case services that orchestrate telemetry domain behavior.
 *
 * <p>What is forbidden here:
 * JPA entities, REST controllers, database migrations, raw infrastructure clients, direct topology implementation imports, or duplicated domain policies.
 *
 * <p>Boundary rules:
 * Telemetry owns industrial acquisition metadata, telemetry points, topology bindings, raw readings,
 * ingestion audit, quality references, catalog references, and localized labels. Telemetry must use
 * neutral references to topology assets and must not import topology domain or infrastructure classes.
 * Business taxonomy concepts must be modeled as catalog references, not Java enums.
 */
package dz.sh.hidra.modules.telemetry.application.port.in;
