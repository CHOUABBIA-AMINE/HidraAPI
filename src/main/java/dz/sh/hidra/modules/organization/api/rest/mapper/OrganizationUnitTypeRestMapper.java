/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.mapper
 *
 * @Description : Maps organization unit type catalog DTOs to REST responses.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.mapper;

import java.util.Objects;

import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitTypeResponse;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitTypeDto;

/**
 * Maps organization unit type catalog DTOs to REST responses.
 *
 * <p>Business role:
 * Converts organization unit type catalog application DTOs into API responses exposing Arabic,
 * French, and English labels as first-class fields.
 *
 * <p>Architecture role:
 * This mapper stays at the API boundary. Domain references still carry only id and code.
 */
public final class OrganizationUnitTypeRestMapper {

    public OrganizationUnitTypeResponse toResponse(OrganizationUnitTypeDto dto) {
        Objects.requireNonNull(dto, "Organization unit type DTO must not be null.");
        return new OrganizationUnitTypeResponse(
                dto.id(),
                dto.code(),
                dto.nameAr(),
                dto.nameFr(),
                dto.nameEn(),
                dto.descriptionAr(),
                dto.descriptionFr(),
                dto.descriptionEn(),
                dto.status(),
                dto.sortOrder(),
                dto.systemDefined(),
                dto.createdAt(),
                dto.updatedAt());
    }
}
