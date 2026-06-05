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
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Topology command package.
 *
 */
/**
 * Topology command package.
 *
 * <p>Responsibility:
 * This package defines topology command package.
 *
 * <p>What belongs here:
 * immutable application input commands for creating topology resources.
 *
 * <p>What is forbidden here:
 * REST request DTOs, Bean Validation annotations, JPA annotations, business behavior, repository calls, identity implementation, or organization implementation.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.application.command;
