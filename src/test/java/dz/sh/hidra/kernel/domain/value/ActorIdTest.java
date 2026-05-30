/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ActorIdTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Verifies actor ID creation and invalid input rejection.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ActorIdTest {

    @Test
    void shouldTrimProvidedValue() {
        ActorId actorId = ActorId.of("  actor-1  ");

        assertThat(actorId.value()).isEqualTo("actor-1");
    }

    @Test
    void shouldCreateGeneratedId() {
        ActorId actorId = ActorId.newId();

        assertThat(actorId.value()).isNotBlank();
    }

    @Test
    void shouldRejectBlankValue() {
        assertThatThrownBy(() -> ActorId.of(""))
                .isInstanceOf(InvalidValueObjectException.class)
                .hasMessage("ActorId must not be blank.");
    }
}
