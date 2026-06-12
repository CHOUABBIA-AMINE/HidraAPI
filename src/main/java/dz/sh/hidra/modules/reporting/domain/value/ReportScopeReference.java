/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportScopeReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.value
 *
 * @Description : Neutral report scope reference.
 *
 */
package dz.sh.hidra.modules.reporting.domain.value;

/**
 * Neutral report scope reference.
 *
 * @param scopeType scope type
 * @param scopeReferenceId scope reference identifier
 * @param scopeCodeSnapshot scope code snapshot
 * @param scopeLabelSnapshot scope label snapshot
 */
public record ReportScopeReference(
        String scopeType,
        String scopeReferenceId,
        String scopeCodeSnapshot,
        String scopeLabelSnapshot
) {
}
