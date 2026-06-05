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
 * @Package     : dz.sh.hidra.modules.topology
 *
 * @Description : Topology module root boundary.
 *
 */
/**
 * Topology module root boundary.
 *
 * <p>Responsibility:
 * This package defines topology module root boundary.
 *
 * <p>What belongs here:
 * the topology module root, physical hydrocarbon network ownership rules, and layer-level package boundaries.
 *
 * <p>What is forbidden here:
 * behavior classes, API contracts, persistence entities, migrations, identity users, organization employees, telemetry values, hydraulic calculations, risk scores, workflow approvals, or miscellaneous shared code.
 *
 * <p>Boundary rules:
 * Topology owns physical facilities, pipelines, nodes, pipeline appurtenances, equipment references, and network connectivity. Organization owns facility/station-as-organization-unit and people assignments. Identity owns users, roles, permissions, and access policies. Future measurement owns telemetry/time-series values. Future flow owns hydraulic calculations.
 */
package dz.sh.hidra.modules.topology;
