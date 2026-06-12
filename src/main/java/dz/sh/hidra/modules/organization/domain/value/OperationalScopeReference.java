/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Neutral reference to an operational scope.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

/**
 * Neutral reference to an operational scope owned by another module.
 *
 * @param operationalScopeType scope type
 * @param operationalScopeId scope identifier
 * @param operationalScopeCode scope code snapshot
 * @param operationalScopeName scope name snapshot
 */
public record OperationalScopeReference(
        OperationalScopeType operationalScopeType,
        String operationalScopeId,
        String operationalScopeCode,
        String operationalScopeName
) {

    public OperationalScopeReference {
        operationalScopeId = normalize(operationalScopeId);
        operationalScopeCode = normalize(operationalScopeCode);
        operationalScopeName = normalize(operationalScopeName);
    }

    public static OperationalScopeReference none() {
        return new OperationalScopeReference(null, null, null, null);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
