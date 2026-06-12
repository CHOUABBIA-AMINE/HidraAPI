/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetConditionRecordedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.event
 *
 * @Description : Published when an asset condition record is captured.
 *
 */
package dz.sh.hidra.modules.assets.domain.event;

import java.time.Instant;

/**
 * Published when an asset condition record is captured.
 */
public record AssetConditionRecordedEvent(
        String eventId,
    String conditionRecordId,
    String maintainableAssetId,
    Instant occurredAt
) implements AssetsDomainEvent {

    @Override
    public String eventType() {
        return "AssetConditionRecordedEvent";
    }
}
