/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationSourceReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.value
 *
 * @Description : Neutral source module reference.
 *
 */
package dz.sh.hidra.modules.integration.domain.value;

/**
 * Neutral source module reference.
 *
 * @param sourceModule source module name
 * @param sourceTypeCode source type code
 * @param sourceId source identifier
 * @param sourceCodeSnapshot source code snapshot
 * @param sourceLabelSnapshot source label snapshot
 */
public record IntegrationSourceReference(
        String sourceModule,
        String sourceTypeCode,
        String sourceId,
        String sourceCodeSnapshot,
        String sourceLabelSnapshot
) {
}
