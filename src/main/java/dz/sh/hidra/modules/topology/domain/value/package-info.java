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
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Topology value object package.
 *
 */
/**
 * Topology value object package.
 *
 * <p>Responsibility:
 * This package defines topology value object package.
 *
 * <p>What belongs here:
 * immutable identifiers, codes, names, statuses, facility/appurtenance/valve/node/product types, coordinates, dimensions, KP values, and neutral references.
 *
 * <p>What is forbidden here:
 * Spring annotations, JPA annotations, REST annotations, repositories, mutable DTOs, identity implementation, or organization implementation.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.domain.value;
