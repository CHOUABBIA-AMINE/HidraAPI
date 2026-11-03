/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FoundationIdentityAuthorizationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Resolves native current actor and global administration authority.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

public interface FoundationIdentityAuthorizationPort {
    /** Must prove a current native bearer, session and currently valid administrator grant. */
    String administratorActor();
    /** Must prove current native bearer; returns only a verified actor identity. */
    String authenticatedActor();
    boolean currentAdministrator(String actorId);
}
