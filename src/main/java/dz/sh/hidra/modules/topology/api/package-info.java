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
 * @Package     : dz.sh.hidra.modules.topology.api
 *
 * @Description : Topology API layer boundary.
 *
 */
/**
 * Topology API layer boundary.
 *
 * <p>Responsibility:
 * This package defines topology api layer boundary.
 *
 * <p>What belongs here:
 * API adapters that expose topology use cases to external clients.
 *
 * <p>What is forbidden here:
 * domain rules, application orchestration, persistence mapping, JPA entities, repositories, identity implementation, organization implementation, telemetry values, flow calculations, or risk scoring.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.api;
