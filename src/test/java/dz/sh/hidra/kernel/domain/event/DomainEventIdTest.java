/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainEventIdTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.event
 *
 * @Description : Verifies domain event ID creation and invalid input rejection.
 *
 */
package dz.sh.hidra.kernel.domain.event;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DomainEventIdTest {

    @Test
    void shouldTrimProvidedValue() {
        DomainEventId eventId = DomainEventId.of("  event-1  ");

        assertThat(eventId.value()).isEqualTo("event-1");
    }

    @Test
    void shouldCreateGeneratedId() {
        DomainEventId eventId = DomainEventId.newId();

        assertThat(eventId.value()).isNotBlank();
    }

    @Test
    void shouldRejectBlankValue() {
        assertThatThrownBy(() -> DomainEventId.of("  "))
                .isInstanceOf(InvalidValueObjectException.class)
                .hasMessage("DomainEventId must not be blank.");
    }
}
