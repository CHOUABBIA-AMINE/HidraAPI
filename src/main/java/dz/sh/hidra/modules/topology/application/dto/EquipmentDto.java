/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO representing topology equipment.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.time.Instant;

/**
 * Represents topology equipment data returned by topology use cases.
 *
 * <p>Business role:
 * This DTO describes optional physical equipment/component references attached to topology assets.
 * Detailed maintenance, inspection, reliability, telemetry, and workflow behavior belongs to future
 * modules.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent and does not expose persistence entities or REST
 * response models.
 *
 * <p>Validation:
 * This DTO is an output projection. Equipment validation is enforced by topology domain objects.
 *
 * <p>Usage:
 * Use this DTO as an application output and map it to REST responses in the API layer later.
 *
 * @param equipmentId equipment identifier
 * @param code business code
 * @param name display name
 * @param equipmentType equipment type
 * @param parentAssetType parent topology asset type
 * @param parentAssetId parent topology asset identifier
 * @param status lifecycle status
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
public record EquipmentDto(
        String equipmentId,
        String code,
        String name,
        String equipmentType,
        String parentAssetType,
        String parentAssetId,
        String status,
        Instant createdAt,
        Instant updatedAt) {
}
