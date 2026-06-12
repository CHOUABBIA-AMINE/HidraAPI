/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SyncTriggerType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Defines identity SyncTriggerType values.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Identity SyncTriggerType values.
 */
public enum SyncTriggerType {
    SCHEDULED, MANUAL, LOGIN_JIT, SYSTEM
}
