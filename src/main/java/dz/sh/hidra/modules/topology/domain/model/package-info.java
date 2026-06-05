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
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Topology domain model package.
 *
 */
/**
 * Topology domain model package.
 *
 * <p>Responsibility:
 * This package defines topology domain model package.
 *
 * <p>What belongs here:
 * domain models for PipelineSystem, Pipeline, Facility, TopologyNode, PipelineSegment, PipelineAppurtenance, TopologyConnection, and Equipment.
 *
 * <p>What is forbidden here:
 * JPA entities, REST DTOs, Spring annotations, repositories, identity users, organization employees, telemetry values, flow calculations, risk scores, or workflow tasks.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.domain.model;
