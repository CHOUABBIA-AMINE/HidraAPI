/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationModule
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization
 *
 * @Description : Defines organization module constants.
 *
 */
package dz.sh.hidra.modules.organization;

/**
 * Organization module constants.
 */
public final class OrganizationModule {

    public static final String MODULE_NAME = "organization";
    public static final String TABLE_PREFIX = "hidra_org_";

    private OrganizationModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
