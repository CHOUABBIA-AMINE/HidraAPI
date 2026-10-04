/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDefinitionStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.value
 *
 * @Description : Defines the persisted lifecycle of a report definition.
 *
 */
package dz.sh.hidra.modules.reporting.domain.value;

/**
 * Defines the persisted lifecycle of a report definition.
 */
public enum ReportDefinitionStatus {
    DRAFT,
    ACTIVE,
    RETIRED;

    public boolean usableForNewRequests() {
        return this == ACTIVE;
    }
}
