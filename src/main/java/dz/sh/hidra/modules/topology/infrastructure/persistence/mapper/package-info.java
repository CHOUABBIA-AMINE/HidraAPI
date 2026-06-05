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
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.mapper
 *
 * @Description : Topology persistence mapper package.
 *
 */
/**
 * Topology persistence mapper package.
 *
 * <p>Responsibility:
 * This package defines topology persistence mapper package.
 *
 * <p>What belongs here:
 * deterministic mapping between topology domain/application objects and topology JPA entities.
 *
 * <p>What is forbidden here:
 * repository calls, REST mapping, business decisions, identity mapping, organization mapping, or side effects.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.mapper;
