/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopeType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Defines identity ScopeType values.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Identity ScopeType values.
 */
public enum ScopeType {
    GLOBAL, ORGANIZATION_UNIT, PIPELINE_SYSTEM, PIPELINE, FACILITY, EQUIPMENT, CUSTOM
}
