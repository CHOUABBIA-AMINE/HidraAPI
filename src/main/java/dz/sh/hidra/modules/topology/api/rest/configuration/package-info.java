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
 * @Package     : dz.sh.hidra.modules.topology.api.rest.configuration
 *
 * @Description : Topology REST configuration package.
 *
 */
/**
 * Topology REST configuration package.
 *
 * <p>Responsibility:
 * This package defines topology rest configuration package.
 *
 * <p>What belongs here:
 * REST API bean wiring such as a TopologyRestMapper bean when the mapper is a plain class.
 *
 * <p>What is forbidden here:
 * application service wiring, repository wiring, security plumbing, persistence adapters, business decisions, identity implementation, or organization implementation.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.api.rest.configuration;
