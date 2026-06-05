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
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Topology domain service package.
 *
 */
/**
 * Topology domain service package.
 *
 * <p>Responsibility:
 * This package defines topology domain service package.
 *
 * <p>What belongs here:
 * domain services that coordinate topology-only validation across multiple domain objects.
 *
 * <p>What is forbidden here:
 * Spring annotations, persistence access, repositories, REST DTOs, identity implementation, organization implementation, telemetry processing, flow calculation, or risk scoring.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.domain.service;
