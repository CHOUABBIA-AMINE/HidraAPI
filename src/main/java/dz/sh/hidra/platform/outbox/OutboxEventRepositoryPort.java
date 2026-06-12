/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OutboxEventRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.outbox
 *
 * @Description : Defines the technical outbox repository port.
 *
 */
package dz.sh.hidra.platform.outbox;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Technical repository port for platform outbox events.
 *
 * <p>Concrete persistence implementation belongs to the platform persistence adapter.</p>
 */
public interface OutboxEventRepositoryPort {

    OutboxEventEntity save(OutboxEventEntity event);

    Optional<OutboxEventEntity> findById(UUID id);

    List<OutboxEventEntity> findPending(int limit);
}
