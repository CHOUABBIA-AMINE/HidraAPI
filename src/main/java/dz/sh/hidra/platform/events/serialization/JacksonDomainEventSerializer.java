/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JacksonDomainEventSerializer
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.events.serialization
 *
 * @Description : Jackson-based serializer for kernel domain event payloads.
 *
 */
package dz.sh.hidra.platform.events.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.platform.exception.InfrastructureException;
import org.springframework.stereotype.Component;

@Component
public class JacksonDomainEventSerializer implements DomainEventSerializer {

    private final ObjectMapper objectMapper;

    public JacksonDomainEventSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public SerializedDomainEvent serialize(DomainEvent domainEvent) {
        if (domainEvent == null) {
            throw new IllegalArgumentException("Domain event must not be null.");
        }
        return new SerializedDomainEvent(
                domainEvent.eventId().value(),
                domainEvent.eventType(),
                domainEvent.occurredAt(),
                payload(domainEvent));
    }

    private String payload(DomainEvent domainEvent) {
        try {
            return objectMapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException exception) {
            throw new InfrastructureException("Domain event serialization failed.", exception);
        }
    }
}
