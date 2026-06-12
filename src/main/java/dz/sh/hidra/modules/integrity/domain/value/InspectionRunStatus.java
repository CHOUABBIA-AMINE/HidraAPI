/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InspectionRunStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.value
 *
 * @Description : Defines InspectionRunStatus values.
 *
 */
package dz.sh.hidra.modules.integrity.domain.value;

/**
 * Defines InspectionRunStatus values.
 */
public enum InspectionRunStatus {
    PLANNED, RUNNING, COMPLETED, FAILED, CANCELLED
}
