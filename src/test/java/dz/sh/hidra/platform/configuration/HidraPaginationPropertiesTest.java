/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraPaginationPropertiesTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-31
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Verifies fail-fast shared pagination policy and unchanged resource-owned caps.
 *
 */
package dz.sh.hidra.platform.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.junit.jupiter.api.Assertions.*;

class HidraPaginationPropertiesTest {
    private static MockEnvironment configured(String defaultSize, String maxSize, String cursorLength) {
        return new MockEnvironment()
                .withProperty(HidraPaginationProperties.DEFAULT_SIZE_PROPERTY, defaultSize)
                .withProperty(HidraPaginationProperties.MAX_SIZE_PROPERTY, maxSize)
                .withProperty(HidraPaginationProperties.MAX_CURSOR_LENGTH_PROPERTY, cursorLength);
    }

    @Test void requiredPropertiesFailClosedWhenMissing() {
        assertThrows(IllegalStateException.class,
                () -> HidraPaginationProperties.from(new MockEnvironment()));
        assertThrows(IllegalStateException.class,
                () -> HidraPaginationProperties.from(new MockEnvironment()
                        .withProperty(HidraPaginationProperties.DEFAULT_SIZE_PROPERTY, "50")));
    }

    @Test void immutableStartupPolicyUsesExplicitEnvironmentValues() {
        var environment = configured("50", "200", "4096");
        var policy = HidraPaginationProperties.from(environment);
        assertEquals(50, policy.defaultSize());
        assertEquals(200, policy.maxSize());
        assertEquals(4096, policy.maxCursorLength());
        environment.setProperty(HidraPaginationProperties.DEFAULT_SIZE_PROPERTY, "20");
        assertEquals(50, policy.defaultSize()); // No unsupported live reload.
        assertEquals(20, HidraPaginationProperties.from(environment).defaultSize());
    }

    @Test void malformedPropertiesNeverBecomeUnlimited() {
        for (String invalid : new String[]{"", "unbounded", "-1", "1.5", "2147483648"}) {
            assertThrows(RuntimeException.class,
                    () -> HidraPaginationProperties.from(configured(invalid, "200", "4096")));
            assertThrows(RuntimeException.class,
                    () -> HidraPaginationProperties.from(configured("50", invalid, "4096")));
            assertThrows(RuntimeException.class,
                    () -> HidraPaginationProperties.from(configured("50", "200", invalid)));
        }
    }

    @Test void zeroNegativeAndReversedBoundsFailFast() {
        for (int invalid : new int[]{0, -1}) {
            assertThrows(IllegalArgumentException.class,
                    () -> new HidraPaginationProperties(invalid, 200, 4096));
            assertThrows(IllegalArgumentException.class,
                    () -> new HidraPaginationProperties(50, invalid, 4096));
            assertThrows(IllegalArgumentException.class,
                    () -> new HidraPaginationProperties(50, 200, invalid));
        }
        assertThrows(IllegalArgumentException.class,
                () -> new HidraPaginationProperties(201, 200, 4096));
    }

    @Test void finiteConfigurationCeilingsRejectExcessiveValues() {
        assertThrows(IllegalArgumentException.class,
                () -> HidraPaginationProperties.from(configured("50", "10001", "4096")));
        assertThrows(IllegalArgumentException.class,
                () -> HidraPaginationProperties.from(configured("50", "200", "4097")));
        assertEquals(10_000, new HidraPaginationProperties(50, 10_000, 4096).maxSize());
    }

    @Test void effectivePageSizeNeverOverridesResourceLimits() {
        var policy = HidraPaginationProperties.from(configured("50", "500", "4096"));
        assertEquals(50, policy.resolveSize(null, 200));
        assertEquals(100, policy.resolveSize(100, 100));
        assertThrows(IllegalArgumentException.class, () -> policy.resolveSize(101, 100));
        assertThrows(IllegalArgumentException.class, () -> policy.resolveSize(501, 1000));
        assertThrows(IllegalArgumentException.class, () -> policy.resolveSize(0, 200));
        assertThrows(IllegalArgumentException.class, () -> policy.resolveSize(null, 49));
        assertThrows(IllegalArgumentException.class, () -> policy.resolveSize(10, 0));
    }

    @Test void cursorsStayBoundedWithoutBecomingAuthorization() {
        var policy = HidraPaginationProperties.from(configured("50", "200", "4096"));
        assertDoesNotThrow(() -> policy.validateCursor(null, 2048));
        assertDoesNotThrow(() -> policy.validateCursor("opaque-cursor", 2048));
        assertThrows(IllegalArgumentException.class,
                () -> policy.validateCursor("x".repeat(2049), 2048));
        assertThrows(IllegalArgumentException.class,
                () -> policy.validateCursor("x".repeat(4097), 8000));
        assertThrows(IllegalArgumentException.class,
                () -> policy.validateCursor(" ", 2048));
        assertThrows(IllegalArgumentException.class,
                () -> policy.validateCursor("abc\nxyz", 2048));
        assertThrows(IllegalArgumentException.class,
                () -> policy.validateCursor(null, 0));
    }

    @Test void existingSpringConfigurationSuppliesOneTypedBean() {
        var environment = configured("40", "200", "2048");
        var configuration = new HidraTransactionConfiguration();
        assertEquals(new HidraPaginationProperties(40, 200, 2048),
                configuration.hidraPaginationProperties(environment));
    }

    @Test void profileSpecificEnvironmentValuesRequireReconstruction() {
        var environment = configured("50", "200", "4096");
        environment.setActiveProfiles("dev");
        var dev = HidraPaginationProperties.from(environment);
        environment.setActiveProfiles("production");
        environment.setProperty(HidraPaginationProperties.MAX_SIZE_PROPERTY, "100");
        var production = HidraPaginationProperties.from(environment);
        assertEquals(200, dev.maxSize());
        assertEquals(100, production.maxSize());
        assertEquals(200, new HidraPaginationProperties(50, 200, 4096).maxSize());
    }
}
