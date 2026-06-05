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
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Topology application service package.
 *
 */
/**
 * Topology application service package.
 *
 * <p>Responsibility:
 * This package defines topology application service package.
 *
 * <p>What belongs here:
 * use-case implementations coordinating commands, queries, domain models, domain policies, and outbound ports.
 *
 * <p>What is forbidden here:
 * REST controllers, JPA entities, Spring Data repositories, direct database access, identity implementation, organization implementation, telemetry processing, or hydraulic calculations.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.application.service;
