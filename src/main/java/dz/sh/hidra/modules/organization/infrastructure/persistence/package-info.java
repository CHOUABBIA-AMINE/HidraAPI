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
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Organization persistence infrastructure boundary.
 *
 */

/**
 * Persistence package for organization storage concerns.
 *
 * <p>What belongs here:
 * JPA entities, persistence mappers, Spring Data repositories, and repository adapters for organization data.
 *
 * <p>What is forbidden here:
 * Domain logic, API DTOs, application use-case orchestration, and direct topology or identity aggregate dependencies.
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
package dz.sh.hidra.modules.organization.infrastructure.persistence;

