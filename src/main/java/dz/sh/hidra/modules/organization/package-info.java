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
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization
 *
 * @Description : Root organization package boundary.
 *
 */

/**
 * Root package for the organization module. It defines the module boundary for employees, organization units, positions, assignments, reporting lines, station-as-organization-unit representation, and operational responsibility structure.
 *
 * <p>What belongs here:
 * Subpackages dedicated to API, application, domain, and infrastructure concerns for the organization bounded context.
 *
 * <p>What is forbidden here:
 * Identity users, identity roles, identity permissions, topology station assets, Spring Security plumbing, shared/common utility packages, identityaccess packages, and SupervisorAssignment.
 *
 * <p>Boundary rules:
 * {@literal modules.identity} owns users, identity roles, permissions, and access policies.
 * {@literal modules.organization} owns employees, organization units, positions, assignments,
 * and reporting lines. Future {@literal modules.topology} owns physical stations and topology
 * assets. Organization may reference topology later only through {@literal OperationalScopeReference}.
 * Platform owns technical infrastructure and Spring Security plumbing.
 *
 * <p>Station rule:
 * Station-as-organization-unit belongs to organization when it represents people,
 * responsibility, and operational reporting. The physical station asset belongs to topology.
 *
 * <p>Reporting rule:
 * Matrix reporting is represented later through {@literal ReportingLine}, not through
 * {@literal SupervisorAssignment}.
 */
package dz.sh.hidra.modules.organization;

