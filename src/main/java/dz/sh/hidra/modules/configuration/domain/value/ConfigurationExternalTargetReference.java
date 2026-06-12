/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationExternalTargetReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.value
 *
 * @Description : Neutral external target reference.
 *
 */
package dz.sh.hidra.modules.configuration.domain.value;

/**
 * Neutral external target reference.
 *
 * @param referenceType reference type
 * @param referenceId reference identifier
 * @param referenceCodeSnapshot reference code snapshot
 * @param referenceLabelSnapshot reference label snapshot
 */
public record ConfigurationExternalTargetReference(
        String referenceType,
        String referenceId,
        String referenceCodeSnapshot,
        String referenceLabelSnapshot
) {
}
