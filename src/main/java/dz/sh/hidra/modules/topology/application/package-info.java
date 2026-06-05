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
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application
 *
 * @Description : Topology application layer boundary.
 *
 */
/**
 * Topology application layer boundary.
 *
 * <p>Responsibility:
 * This package defines topology application layer boundary.
 *
 * <p>What belongs here:
 * use-case orchestration, commands, queries, DTOs, inbound ports, outbound ports, and application services.
 *
 * <p>What is forbidden here:
 * REST annotations, controllers, JPA entities, Spring Data repositories, infrastructure implementation details, identity implementation, organization implementation, or hydraulic calculations.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.application;
