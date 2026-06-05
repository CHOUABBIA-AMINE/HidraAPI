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
 * @Package     : dz.sh.hidra.modules.topology.domain.policy
 *
 * @Description : Topology domain policy package.
 *
 */
/**
 * Topology domain policy package.
 *
 * <p>Responsibility:
 * This package defines topology domain policy package.
 *
 * <p>What belongs here:
 * pure policies for asset status transitions, connectivity rules, facility rules, and pipeline appurtenance rules.
 *
 * <p>What is forbidden here:
 * Spring beans, repositories, database queries, controllers, JPA entities, identity logic, organization logic, measurement logic, or side effects.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.domain.policy;
