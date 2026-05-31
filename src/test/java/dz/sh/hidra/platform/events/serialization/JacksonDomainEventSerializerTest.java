/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JacksonDomainEventSerializerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.events.serialization
 *
 * @Description : Verifies domain event serialization.
 *
 */
package dz.sh.hidra.platform.events.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.event.DomainEventId;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JacksonDomainEventSerializerTest {

    @Test
    void shouldSerializeKernelDomainEvent() {
        JacksonDomainEventSerializer serializer = new JacksonDomainEventSerializer(new ObjectMapper().findAndRegisterModules());
        DomainEvent event = new TestDomainEvent(DomainEventId.of("event-1"), Instant.parse("2026-01-01T00:00:00Z"), "test.event");

        SerializedDomainEvent serialized = serializer.serialize(event);

        assertThat(serialized.eventId()).isEqualTo("event-1");
        assertThat(serialized.eventType()).isEqualTo("test.event");
        assertThat(serialized.payload()).contains("test.event");
    }

    private record TestDomainEvent(DomainEventId eventId, Instant occurredAt, String eventType) implements DomainEvent {
    }
}
