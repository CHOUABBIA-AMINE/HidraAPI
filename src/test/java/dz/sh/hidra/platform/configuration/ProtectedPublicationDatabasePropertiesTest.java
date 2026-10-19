/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedPublicationDatabasePropertiesTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Verifies fail-closed credential configuration and secret redaction.
 *
 */
package dz.sh.hidra.platform.configuration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ProtectedPublicationDatabasePropertiesTest {
    @Test void disabledConfigurationDoesNotRequirePublisherSecrets(){
        assertFalse(new ProtectedPublicationDatabaseProperties(false,null,null,2).enabled());
    }
    @Test void enabledConfigurationRequiresDistinctPublisherCredential(){
        assertThrows(IllegalArgumentException.class,()->new ProtectedPublicationDatabaseProperties(true,null,null,2));
        assertThrows(IllegalArgumentException.class,()->new ProtectedPublicationDatabaseProperties(true," user","secret",2));
        assertThrows(IllegalArgumentException.class,()->new ProtectedPublicationDatabaseProperties(true,"user","",2));
    }
    @Test void rejectsInvalidPoolBounds(){
        for(int n:new int[]{0,-1,9})assertThrows(IllegalArgumentException.class,()->new ProtectedPublicationDatabaseProperties(false,null,null,n));
    }
    @Test void configurationRenderingRedactsSecrets(){
        var p=new ProtectedPublicationDatabaseProperties(true,"private-publisher","private-secret",2);
        assertFalse(p.toString().contains("private-secret"));assertFalse(p.toString().contains("private-publisher"));
    }
}
