/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakVerificationActionType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.value
 *
 * @Description : Defines LeakVerificationActionType values.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.value;

/**
 * Defines LeakVerificationActionType values.
 */
public enum LeakVerificationActionType {
    FIELD_CHECK, CONTROL_ROOM_CONFIRMATION, PRESSURE_BALANCE_REVIEW, FLOW_BALANCE_REVIEW, PATROL, DRONE_INSPECTION, THIRD_PARTY_CHECK, OTHER
}
