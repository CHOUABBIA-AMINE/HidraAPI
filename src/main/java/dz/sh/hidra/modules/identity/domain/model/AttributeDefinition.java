/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AttributeDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Defines ABAC attributes.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Defines ABAC attributes.
 *
     * @param id id
 * @param code code
 * @param name name
 * @param attributeTarget attributeTarget
 * @param dataType dataType
 * @param description description
 * @param multiValued multiValued
 * @param sensitive sensitive
 * @param status status
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record AttributeDefinition(
        String id,
    String code,
    String name,
    AttributeTarget attributeTarget,
    AttributeDataType dataType,
    String description,
    boolean multiValued,
    boolean sensitive,
    PermissionStatus status,
    Instant createdAt,
    Instant updatedAt
) {

    public AttributeDefinition {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("AttributeDefinition id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidIdentityValueException("AttributeDefinition code must not be blank.");
        }
        // HRA-051 required: attributeTarget
        if (attributeTarget == null) {
            throw new InvalidIdentityValueException("AttributeDefinition attribute target must not be null.");
        }
        // HRA-051 required: dataType
        if (dataType == null) {
            throw new InvalidIdentityValueException("AttributeDefinition data type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("AttributeDefinition status must not be null.");
        }

    id = normalize(id);
    code = normalize(code);
    name = normalize(name);
    description = normalize(description);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
