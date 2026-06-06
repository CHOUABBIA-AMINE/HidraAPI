/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductTypeRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.mapper
 *
 * @Description : Maps product type catalog DTOs to REST responses.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;

import java.util.Objects;

import dz.sh.hidra.modules.topology.api.rest.response.ProductTypeResponse;
import dz.sh.hidra.modules.topology.application.dto.ProductTypeDto;

/**
 * Maps product type catalog DTOs to REST responses.
 *
 * <p>Business role:
 * Converts product type catalog application DTOs into API responses exposing Arabic, French, and
 * English labels as first-class fields.
 *
 * <p>Architecture role:
 * This mapper stays at the API boundary. Domain references still carry only id and code.
 */
public final class ProductTypeRestMapper {

    public ProductTypeResponse toResponse(ProductTypeDto dto) {
        Objects.requireNonNull(dto, "Product type DTO must not be null.");
        return new ProductTypeResponse(
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
