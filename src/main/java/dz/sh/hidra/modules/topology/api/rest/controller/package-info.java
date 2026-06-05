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
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Topology REST controller package.
 *
 */
/**
 * Topology REST controller package.
 *
 * <p>Responsibility:
 * This package defines topology rest controller package.
 *
 * <p>What belongs here:
 * thin controllers for pipeline systems, pipelines, facilities, nodes, pipeline segments, pipeline appurtenances, connections, and equipment.
 *
 * <p>What is forbidden here:
 * repositories, JPA entities, business rules, domain policies, persistence mappers, identity implementation, organization implementation, measurement logic, or flow calculations.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.api.rest.controller;
