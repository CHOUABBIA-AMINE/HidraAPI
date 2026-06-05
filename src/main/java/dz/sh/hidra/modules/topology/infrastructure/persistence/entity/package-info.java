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
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Topology persistence entity package.
 *
 */
/**
 * Topology persistence entity package.
 *
 * <p>Responsibility:
 * This package defines topology persistence entity package.
 *
 * <p>What belongs here:
 * JPA entities mapped to hidra_topology_* tables for topology persistence.
 *
 * <p>What is forbidden here:
 * business behavior, REST annotations, application ports, domain policies, identity entities, organization entities, measurement entities, or flow entities.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;
