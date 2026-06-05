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
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Topology REST response DTO package.
 *
 */
/**
 * Topology REST response DTO package.
 *
 * <p>Responsibility:
 * This package defines topology rest response dto package.
 *
 * <p>What belongs here:
 * OpenAPI documented response models for topology resources returned by controllers.
 *
 * <p>What is forbidden here:
 * JPA entities, domain behavior, persistence details, identity implementation fields, organization implementation fields, telemetry values, risk scores, or workflow state.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.api.rest.response;
