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
 * @Package     : dz.sh.hidra.modules.topology.domain.exception
 *
 * @Description : Topology domain exception package.
 *
 */
/**
 * Topology domain exception package.
 *
 * <p>Responsibility:
 * This package defines topology domain exception package.
 *
 * <p>What belongs here:
 * topology-specific domain exceptions and validation exceptions.
 *
 * <p>What is forbidden here:
 * Spring exceptions, REST response models, JPA exceptions, repository classes, identity exceptions, organization exceptions, or infrastructure error handling.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology.domain.exception;
