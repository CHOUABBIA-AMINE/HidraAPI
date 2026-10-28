/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedExecutionBudgetPropertiesTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Verifies bounded startup execution policy without creating authority or changing shared defaults.
 *
 */
package dz.sh.hidra.platform.configuration;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class ProtectedExecutionBudgetPropertiesTest {
    @Test void missingConfigurationPreservesSharedDefault() {
        var value=ProtectedExecutionBudgetProperties.from(new MockEnvironment());
        assertEquals(500,ProtectedExecutionBudgetProperties.STANDARD_WORK_BUDGET_MILLIS);
        assertEquals(7500,value.appointmentWorkBudgetMillis());
        assertEquals(7_500_000_000L,value.appointmentWorkBudgetNanos());
        assertEquals(ProtectedExecutionBudgetProperties.defaults(),value);
    }
    @Test void boundedServerConfigurationUsesExactMilliseconds() {
        for(long millis:new long[]{1,500,3000,7500,10000}) {
            var value=ProtectedExecutionBudgetProperties.from(new MockEnvironment().withProperty(
                    ProtectedExecutionBudgetProperties.APPOINTMENT_PROPERTY,Long.toString(millis)));
            assertEquals(millis,value.appointmentWorkBudgetMillis());
            assertEquals(millis*1_000_000L,value.appointmentWorkBudgetNanos());
        }
    }
    @Test void zeroNegativeAndAboveCeilingFailWithoutFallback() {
        for(long millis:new long[]{0,-1,10001,Long.MAX_VALUE})
            assertThrows(IllegalArgumentException.class,()->ProtectedExecutionBudgetProperties.from(
                    new MockEnvironment().withProperty(ProtectedExecutionBudgetProperties.APPOINTMENT_PROPERTY,Long.toString(millis))));
    }
    @Test void malformedServerConfigurationFailsWithoutFallback() {
        assertThrows(RuntimeException.class,()->ProtectedExecutionBudgetProperties.from(new MockEnvironment()
                .withProperty(ProtectedExecutionBudgetProperties.APPOINTMENT_PROPERTY,"unbounded")));
    }
    @Test void custodyBudgetDefaultsAndOverridesRemainIndependent() {
        var defaults=ProtectedExecutionBudgetProperties.from(new MockEnvironment());
        assertEquals(1000,defaults.custodyWorkBudgetMillis());
        assertEquals(1_000_000_000L,defaults.custodyWorkBudgetNanos());
        for(long millis:new long[]{1,500,1000,7500,10000}) {
            var value=ProtectedExecutionBudgetProperties.from(new MockEnvironment()
                    .withProperty(ProtectedExecutionBudgetProperties.CUSTODY_PROPERTY,Long.toString(millis))
                    .withProperty(ProtectedExecutionBudgetProperties.APPOINTMENT_PROPERTY,"2000"));
            assertEquals(millis,value.custodyWorkBudgetMillis());
            assertEquals(millis*1_000_000L,value.custodyWorkBudgetNanos());
            assertEquals(2000,value.appointmentWorkBudgetMillis());
        }
        assertEquals(500,ProtectedExecutionBudgetProperties.STANDARD_WORK_BUDGET_MILLIS);
    }
    @Test void custodyMalformedOrUnboundedConfigurationFailsClosed() {
        for(String invalid:new String[]{"0","-1","10001",Long.toString(Long.MAX_VALUE),"unbounded","1.5"})
            assertThrows(RuntimeException.class,()->ProtectedExecutionBudgetProperties.from(new MockEnvironment()
                    .withProperty(ProtectedExecutionBudgetProperties.CUSTODY_PROPERTY,invalid)));
    }
    @Test void legacyAppointmentConstructorPreservesIndependentCustodyDefault() {
        var value=new ProtectedExecutionBudgetProperties(2000);
        assertEquals(2000,value.appointmentWorkBudgetMillis());
        assertEquals(1000,value.custodyWorkBudgetMillis());
        assertThrows(IllegalArgumentException.class,()->new ProtectedExecutionBudgetProperties(2000,0));
        assertThrows(IllegalArgumentException.class,()->new ProtectedExecutionBudgetProperties(0,1000));
    }
}
