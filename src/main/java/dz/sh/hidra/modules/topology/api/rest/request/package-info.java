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
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Topology REST request DTO package.
 *
 */
/**
 * Topology REST request DTO package.
 *
 * <p>Responsibility:
 * This package defines topology rest request dto package.
 *
 * <p>What belongs here:
 * Bean Validation and OpenAPI documented request models for creating/listing topology assets.
 *
 * <p>What is forbidden here:
 * domain behavior, application services, persistence entities, repositories, identity data, organization data, measurement values, or flow results.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.api.rest.request;
