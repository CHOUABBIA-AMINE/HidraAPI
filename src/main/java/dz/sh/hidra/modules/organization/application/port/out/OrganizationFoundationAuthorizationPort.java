/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationFoundationAuthorizationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Coordinates the bounded Organization foundation contract.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

public interface OrganizationFoundationAuthorizationPort {
    /** Current native administrator; route grants and membership are insufficient. */
    String administrator();
    /** Validates an optional existing login reference without inventing employee ownership. */
    void requireActiveIdentity(String identityUserReference);
}
