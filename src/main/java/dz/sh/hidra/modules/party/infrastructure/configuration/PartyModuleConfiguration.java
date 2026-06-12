/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyModuleConfiguration
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.configuration
 *
 * @Description : Party infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.configuration;

/**
 * Party infrastructure configuration.
 */
public record PartyModuleConfiguration(
        boolean externalReferenceSynchronizationEnabled,
        boolean complianceScreeningEnabled,
        boolean riskSnapshotEnabled
) {

    public static PartyModuleConfiguration defaults() {
        return new PartyModuleConfiguration(false, true, true);
    }
}
