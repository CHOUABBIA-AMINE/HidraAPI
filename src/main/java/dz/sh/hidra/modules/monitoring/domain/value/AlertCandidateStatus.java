/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlertCandidateStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.value
 *
 * @Description : Defines AlertCandidateStatus values.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.value;

/**
 * Defines AlertCandidateStatus values.
 */
public enum AlertCandidateStatus {
    NEW, QUALIFIED, DISMISSED, ESCALATED, EXPIRED
}
