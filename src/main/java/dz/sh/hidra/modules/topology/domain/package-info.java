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
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain
 *
 * @Description : Topology domain layer boundary.
 *
 */
/**
 * Topology domain layer boundary.
 *
 * <p>Responsibility:
 * This package defines topology domain layer boundary.
 *
 * <p>What belongs here:
 * pure topology business concepts for physical facilities, pipelines, nodes, appurtenances, connectivity, equipment references, policies, services, values, and exceptions.
 *
 * <p>What is forbidden here:
 * Spring, JPA, REST DTOs, repositories, platform infrastructure, identity implementation, organization implementation, telemetry values, flow calculations, risk scoring, workflow approvals, or persistence code.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.domain;
