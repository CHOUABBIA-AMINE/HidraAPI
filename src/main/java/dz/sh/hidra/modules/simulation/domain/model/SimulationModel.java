/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationModel
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Reusable simulation model definition.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;

    /**
     * Reusable simulation model definition.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param modelTypeId modelTypeId
     * @param topologyScopeType topologyScopeType
     * @param topologyScopeId topologyScopeId
     * @param status status
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record SimulationModel(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String modelTypeId,
        String topologyScopeType,
        String topologyScopeId,
        SimulationModelStatus status,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public SimulationModel {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        modelTypeId = normalize(modelTypeId);
        topologyScopeType = normalize(topologyScopeType);
        topologyScopeId = normalize(topologyScopeId);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
