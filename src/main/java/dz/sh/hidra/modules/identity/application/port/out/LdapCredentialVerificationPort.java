/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LdapCredentialVerificationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Defines LDAP/Active Directory credential verification without exposing Spring LDAP to the application layer.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.application.model.VerifiedDirectoryIdentity;
import java.util.Optional;

/**
 * Outbound credential-verification port for LDAP-compatible directories.
 */
public interface LdapCredentialVerificationPort {

    Optional<VerifiedDirectoryIdentity> verify(String principal, String credentials);
}
