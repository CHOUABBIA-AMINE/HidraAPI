/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OutboxEventStoreTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.events.outbox
 *
 * @Description : Verifies outbox event store behavior.
 *
 */
package dz.sh.hidra.platform.events.outbox;

import dz.sh.hidra.platform.events.serialization.SerializedDomainEvent;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OutboxEventStoreTest {

    @Test
    void shouldPersistPendingOutboxEvent() {
        OutboxEventRepository repository = mock(OutboxEventRepository.class);
        when(repository.save(any(OutboxEventEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        OutboxEventStore store = new OutboxEventStore(repository);
        SerializedDomainEvent event = new SerializedDomainEvent("event-1", "test.event", Instant.parse("2026-01-01T00:00:00Z"), "{}");

        OutboxEventEntity stored = store.store(event);

        assertThat(stored.eventId()).isEqualTo("event-1");
        assertThat(stored.status()).isEqualTo(OutboxEventStatus.PENDING);
        assertThat(stored.retryCount()).isZero();
    }
}
