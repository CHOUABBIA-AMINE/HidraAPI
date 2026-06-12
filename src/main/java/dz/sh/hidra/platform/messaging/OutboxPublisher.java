/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OutboxPublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.messaging
 *
 * @Description : Defines technical publication of outbox events.
 *
 */
package dz.sh.hidra.platform.messaging;

import dz.sh.hidra.platform.outbox.OutboxEventEntity;

/**
 * Publishes a technical outbox event to an external or internal messaging mechanism.
 */
public interface OutboxPublisher {

    PublicationResult publish(OutboxEventEntity event);
}
