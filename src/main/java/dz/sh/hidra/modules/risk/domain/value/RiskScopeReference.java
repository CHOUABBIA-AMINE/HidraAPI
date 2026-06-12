/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskScopeReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.value
 *
 * @Description : Neutral risk scope reference.
 *
 */
package dz.sh.hidra.modules.risk.domain.value;

/**
 * Neutral risk scope reference.
 *
 * @param scopeType scope type
 * @param scopeId scope identifier
 * @param scopeCodeSnapshot scope code snapshot
 * @param scopeLabelSnapshot scope label snapshot
 */
public record RiskScopeReference(
        String scopeType,
        String scopeId,
        String scopeCodeSnapshot,
        String scopeLabelSnapshot
) {
}
