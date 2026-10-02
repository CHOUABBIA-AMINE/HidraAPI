/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party
 *
 * @Description : Defines party module constants.
 *
 */
package dz.sh.hidra.modules.party;

/**
 * Party module constants.
 */
public final class PartyModule {

    public static final String MODULE_NAME = "party";
    public static final String TABLE_PREFIX = "hidra_party_";

    private PartyModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
