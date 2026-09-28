/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationFieldMapping
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Source-to-target field mapping.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
/**
     * Source-to-target field mapping.
     *
         * @param id id
     * @param mappingProfileId mappingProfileId
     * @param sourcePath sourcePath
     * @param targetPath targetPath
     * @param dataType dataType
     * @param required required
     * @param defaultValue defaultValue
     * @param unitCode unitCode
     * @param transformationRuleId transformationRuleId
     * @param displayOrder displayOrder
     * @param active active
     */
    public record IntegrationFieldMapping(
            String id,
        String mappingProfileId,
        String sourcePath,
        String targetPath,
        String dataType,
        boolean required,
        String defaultValue,
        String unitCode,
        String transformationRuleId,
        int displayOrder,
        boolean active
    ) {

        public IntegrationFieldMapping {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationFieldMapping id must not be blank.");
        }
        // HRA-051 required: mappingProfileId
        if (mappingProfileId == null || mappingProfileId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationFieldMapping mapping profile id must not be blank.");
        }

        id = normalize(id);
        mappingProfileId = normalize(mappingProfileId);
        sourcePath = normalize(sourcePath);
        targetPath = normalize(targetPath);
        dataType = normalize(dataType);
        defaultValue = normalize(defaultValue);
        unitCode = normalize(unitCode);
        transformationRuleId = normalize(transformationRuleId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
