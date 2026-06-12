/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordAssetConditionCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.command
 *
 * @Description : Command to record asset condition.
 *
 */
package dz.sh.hidra.modules.assets.application.command;

import dz.sh.hidra.modules.assets.domain.value.AssetConditionStatus;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Command to record asset condition.
 */
public record RecordAssetConditionCommand(
        String maintainableAssetId,
        AssetConditionStatus conditionStatus,
        String conditionTypeId,
        String sourceModule,
        String sourceReferenceId,
        String summary,
        BigDecimal conditionScore,
        Instant observedAt,
        String observedByActorId
) {
}
