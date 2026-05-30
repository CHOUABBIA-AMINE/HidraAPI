/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraApplicationTests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Bootstrap Test
 * @Module      : bootstrap
 * @Package     : dz.sh.hidra
 *
 * @Description : Lightweight bootstrap smoke test that verifies the application class exists.
 *
 */
package dz.sh.hidra;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HidraApplicationTests {

    @Test
    void applicationClassShouldExist() {
        assertThat(HidraApplication.class).isNotNull();
    }
}
