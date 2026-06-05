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
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Topology application DTO package.
 *
 */
/**
 * Topology application DTO package.
 *
 * <p>Responsibility:
 * This package defines topology application dto package.
 *
 * <p>What belongs here:
 * application-level result DTOs used by topology use cases.
 *
 * <p>What is forbidden here:
 * Swagger annotations, JPA annotations, persistence entities, REST-only response models, business behavior, or infrastructure details.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.application.dto;
