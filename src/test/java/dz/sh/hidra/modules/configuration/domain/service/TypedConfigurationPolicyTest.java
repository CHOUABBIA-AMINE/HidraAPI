/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TypedConfigurationPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.service
 *
 * @Description : Verifies the whole typed configuration foundation with synthetic evidence.
 *
 */
package dz.sh.hidra.modules.configuration.domain.service;


import dz.sh.hidra.modules.configuration.domain.value.ConfigurationValueType;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class TypedConfigurationPolicyTest {
    @Test void strictBooleanAndPlainBoundedDecimal() {
        assertEquals("true",TypedConfigurationPolicy.typed(ConfigurationValueType.BOOLEAN,"true"));
        assertEquals("12.5",TypedConfigurationPolicy.typed(ConfigurationValueType.NUMBER,"12.500"));
        for(String s:new String[]{"TRUE","1"," true ","yes"})assertThrows(IllegalArgumentException.class,()->TypedConfigurationPolicy.typed(ConfigurationValueType.BOOLEAN,s));
        for(String s:new String[]{"NaN","1e3","+1","01","0.1234567890123456789","9".repeat(39)})assertThrows(IllegalArgumentException.class,()->TypedConfigurationPolicy.typed(ConfigurationValueType.NUMBER,s));
    }
    @Test void unsupportedTypesFailEvenWithoutADefault() {
        for(var type:ConfigurationValueType.values())if(type!=ConfigurationValueType.STRING&&type!=ConfigurationValueType.NUMBER&&type!=ConfigurationValueType.BOOLEAN)
            assertThrows(IllegalArgumentException.class,()->TypedConfigurationPolicy.typed(type,null));
    }
    @Test void secretMaterialIsRejectedAcrossRawJsonAndDefaults() {
        for(String s:new String[]{"password=x","{\"token\":\"x\"}","{\"api_key\":\"x\"}","Bearer abc.def.ghi","-----BEGIN PRIVATE KEY-----"})
            assertThrows(RuntimeException.class,()->TypedConfigurationPolicy.noSecrets(s));
        assertDoesNotThrow(()->TypedConfigurationPolicy.noSecrets("ordinary-display-label"));
    }
    @Test void nonemptyHalfOpenWindowsAndExactMicroseconds() {
        var now=Instant.parse("2026-11-04T00:00:00Z");
        assertDoesNotThrow(()->TypedConfigurationPolicy.window(now,now.plusSeconds(1)));
        assertDoesNotThrow(()->TypedConfigurationPolicy.window(now,null));
        assertThrows(IllegalArgumentException.class,()->TypedConfigurationPolicy.window(now,now));
        assertThrows(IllegalArgumentException.class,()->TypedConfigurationPolicy.window(null,null));
        assertThrows(IllegalArgumentException.class,()->TypedConfigurationPolicy.microseconds(now.plusNanos(1)));
    }
    @Test void staleVersionAndInvalidEnvironmentReject() {
        var now=Instant.parse("2026-11-04T00:00:00Z");
        assertThrows(IllegalStateException.class,()->TypedConfigurationPolicy.version(now,now.minusSeconds(1)));
        for(String s:new String[]{" PROD ","", "prod/other","x".repeat(41)})assertThrows(IllegalArgumentException.class,()->TypedConfigurationPolicy.environment(s));
    }
    @Test void stringsAreBoundedAndNeverSilentlyTrimmed() {
        assertEquals("ordinary",TypedConfigurationPolicy.typed(ConfigurationValueType.STRING,"ordinary"));
        for(String s:new String[]{" x ","x\nq","x".repeat(2001)})assertThrows(IllegalArgumentException.class,()->TypedConfigurationPolicy.typed(ConfigurationValueType.STRING,s));
    }
}
