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
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api
 *
 * @Description : Organization API layer boundary.
 *
 */

/**
 * API layer for the organization module. It exposes organization capabilities to external clients without leaking domain models or persistence entities.
 *
 * <p>What belongs here:
 * REST controllers, REST request DTOs, REST response DTOs, and REST mappers under dedicated subpackages.
 *
 * <p>What is forbidden here:
 * Domain business rules, JPA repositories, persistence entities, platform security configuration, and direct topology or identity implementation dependencies.
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
package dz.sh.hidra.modules.organization.api;

