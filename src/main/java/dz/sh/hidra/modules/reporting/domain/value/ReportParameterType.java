/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportParameterType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.value
 *
 * @Description : Defines ReportParameterType values.
 *
 */
package dz.sh.hidra.modules.reporting.domain.value;

/**
 * Defines ReportParameterType values.
 */
public enum ReportParameterType {
    TEXT, NUMBER, BOOLEAN, DATE, DATE_TIME, DATE_RANGE, MODULE_REFERENCE, TOPOLOGY_ASSET_REFERENCE, ORGANIZATION_UNIT_REFERENCE, ENUM_REFERENCE
}
