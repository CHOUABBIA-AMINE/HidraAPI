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
 * @Package     : dz.sh.hidra.modules.topology.infrastructure
 *
 * @Description : Topology infrastructure layer boundary.
 *
 */
/**
 * Topology infrastructure layer boundary.
 *
 * <p>Responsibility:
 * This package defines topology infrastructure layer boundary.
 *
 * <p>What belongs here:
 * technical implementations for topology persistence and topology module wiring.
 *
 * <p>What is forbidden here:
 * domain business rules, REST controllers, request/response DTOs, identity implementation, organization implementation, measurement logic, flow calculations, or risk scoring.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.infrastructure;
