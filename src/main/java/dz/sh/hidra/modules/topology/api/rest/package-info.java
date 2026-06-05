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
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Topology REST API boundary.
 *
 */
/**
 * Topology REST API boundary.
 *
 * <p>Responsibility:
 * This package defines topology rest api boundary.
 *
 * <p>What belongs here:
 * REST-specific controllers, request/response DTOs, REST mappers, and REST configuration for topology endpoints.
 *
 * <p>What is forbidden here:
 * domain models, application services, repositories, JPA entities, Flyway migrations, identity logic, organization logic, measurement values, or hydraulic calculations.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.api.rest;
