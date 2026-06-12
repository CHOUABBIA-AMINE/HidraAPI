/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyScopeReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.value
 *
 * @Description : Neutral topology scope reference.
 *
 */
package dz.sh.hidra.modules.planning.domain.value;

/**
 * Neutral topology scope reference.
 *
 * @param topologyScopeType topology scope type
 * @param topologyScopeId topology scope identifier
 * @param topologyScopeCode topology code snapshot
 * @param topologyScopeNameSnapshot topology name snapshot
 */
public record TopologyScopeReference(
        String topologyScopeType,
        String topologyScopeId,
        String topologyScopeCode,
        String topologyScopeNameSnapshot
) {
}
