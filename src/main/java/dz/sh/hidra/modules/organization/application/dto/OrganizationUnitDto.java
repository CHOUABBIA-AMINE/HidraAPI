/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.dto
 *
 * @Description : Application DTO representing an organization unit.
 *
 */
package dz.sh.hidra.modules.organization.application.dto;

import java.time.Instant;

/**
 * Represents organization unit data returned by organization use cases.
 *
 * @param organizationUnitId organization unit identifier
 * @param code organization unit business code
 * @param name organization unit display name
 * @param status organization unit status
 * @param typeId organization unit type catalog identifier
 * @param typeCode organization unit type stable catalog code
 * @param parentId optional parent organization unit identifier
 * @param operationalScopeType optional neutral operational scope type
 * @param operationalScopeId optional neutral operational scope identifier
 * @param operationalScopeCode optional neutral operational scope business code
 * @param operationalScopeName optional neutral operational scope display name
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
public record OrganizationUnitDto(
        String organizationUnitId,
        String code,
        String name,
        String status,
        String typeId,
        String typeCode,
        String parentId,
        String operationalScopeType,
        String operationalScopeId,
        String operationalScopeCode,
        String operationalScopeName,
        Instant createdAt,
        Instant updatedAt) {
}
