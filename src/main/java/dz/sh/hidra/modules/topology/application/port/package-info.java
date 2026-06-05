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
 * @Package     : dz.sh.hidra.modules.topology.application.port
 *
 * @Description : Topology application port package.
 *
 */
/**
 * Topology application port package.
 *
 * <p>Responsibility:
 * This package defines topology application port package.
 *
 * <p>What belongs here:
 * port boundary documentation and subpackages for inbound and outbound application interfaces.
 *
 * <p>What is forbidden here:
 * concrete adapters, Spring Data repositories, JPA entities, REST controllers, mappers with side effects, identity implementation, or organization implementation.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.application.port;
