/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DefectStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.value
 *
 * @Description : Defines DefectStatus values.
 *
 */
package dz.sh.hidra.modules.integrity.domain.value;

/**
 * Defines DefectStatus values.
 */
public enum DefectStatus {
    OPEN, UNDER_ASSESSMENT, MONITORED, RECOMMENDED_FOR_REPAIR, REPAIRED, CLOSED, DISMISSED
}
