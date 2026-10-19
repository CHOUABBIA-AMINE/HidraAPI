/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedPublicationDatabaseProperties
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Defines opt-in private publication credentials without an alternate database endpoint.
 *
 */
package dz.sh.hidra.platform.configuration;

import org.springframework.core.env.Environment;

public record ProtectedPublicationDatabaseProperties(boolean enabled,String username,String password,int maximumPoolSize) {
    public ProtectedPublicationDatabaseProperties {
        if(maximumPoolSize<1 || maximumPoolSize>8)throw new IllegalArgumentException("Invalid publication pool bound");
        if(enabled && (username==null || username.isBlank() || !username.equals(username.strip())
            || password==null || password.isBlank()))
            throw new IllegalArgumentException("Publication credentials required");
    }
    public static ProtectedPublicationDatabaseProperties from(Environment env) {
        String p="hidra.platform.persistence.publication.";
        return new ProtectedPublicationDatabaseProperties(env.getProperty(p+"enabled",Boolean.class,false),
            env.getProperty(p+"username"),env.getProperty(p+"password"),
            env.getProperty(p+"maximum-pool-size",Integer.class,2));
    }
    @Override public String toString(){return "ProtectedPublicationDatabaseProperties[redacted]";}
}
