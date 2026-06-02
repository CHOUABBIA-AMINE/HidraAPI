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
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Organization application service boundary.
 *
 */

/**
 * Application service package for organization use-case orchestration.
 *
 * <p>What belongs here:
 * Focused services implementing inbound ports and coordinating domain models, policies, and outbound ports.
 *
 * <p>What is forbidden here:
 * REST controllers, JPA repository direct access, topology asset management, identity implementation logic, and giant all-purpose services.
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
package dz.sh.hidra.modules.organization.application.service;

