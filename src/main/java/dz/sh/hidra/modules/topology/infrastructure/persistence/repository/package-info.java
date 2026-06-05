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
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : Topology Spring Data repository package.
 *
 */
/**
 * Topology Spring Data repository package.
 *
 * <p>Responsibility:
 * This package defines topology spring data repository package.
 *
 * <p>What belongs here:
 * Spring Data repositories for hidra_topology_* JPA entities.
 *
 * <p>What is forbidden here:
 * domain repositories, application outbound port definitions, REST DTOs, business rules, identity repositories, organization repositories, or measurement repositories.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;
