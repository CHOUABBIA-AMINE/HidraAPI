/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAssetRegisteredEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.event
 *
 * @Description : Published when a maintainable asset is registered.
 *
 */
package dz.sh.hidra.modules.assets.domain.event;

import java.time.Instant;

/**
 * Published when a maintainable asset is registered.
 */
public record MaintainableAssetRegisteredEvent(
        String eventId,
    String maintainableAssetId,
    Instant occurredAt
) implements AssetsDomainEvent {

    @Override
    public String eventType() {
        return "MaintainableAssetRegisteredEvent";
    }
}
