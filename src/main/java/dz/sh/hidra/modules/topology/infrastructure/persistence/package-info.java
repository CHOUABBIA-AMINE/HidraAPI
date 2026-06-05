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
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Topology persistence boundary.
 *
 */
/**
 * Topology persistence boundary.
 *
 * <p>Responsibility:
 * This package defines topology persistence boundary.
 *
 * <p>What belongs here:
 * persistence-specific adapters, entities, mappers, and repositories for topology data.
 *
 * <p>What is forbidden here:
 * domain business behavior, REST DTOs, application commands, identity persistence, organization persistence, measurement persistence, flow persistence, or risk persistence.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;
