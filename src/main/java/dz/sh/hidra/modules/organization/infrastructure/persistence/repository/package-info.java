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
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Organization persistence repository boundary.
 *
 */

/**
 * Persistence repository package for organization database access.
 *
 * <p>What belongs here:
 * Spring Data repositories and adapters implementing organization application outbound repository ports.
 *
 * <p>What is forbidden here:
 * REST controllers, domain policies, API DTOs, and direct imports from identity or topology domain models.
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
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

