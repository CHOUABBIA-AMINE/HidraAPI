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
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.configuration
 *
 * @Description : Topology infrastructure configuration package.
 *
 */
/**
 * Topology infrastructure configuration package.
 *
 * <p>Responsibility:
 * This package defines topology infrastructure configuration package.
 *
 * <p>What belongs here:
 * Spring bean wiring for topology application services, policies, domain services, adapters, and mappers where explicit wiring is required.
 *
 * <p>What is forbidden here:
 * REST endpoint definitions, business rule implementation, JPA entity behavior, identity configuration, organization configuration, or platform-wide security plumbing.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.infrastructure.configuration;
