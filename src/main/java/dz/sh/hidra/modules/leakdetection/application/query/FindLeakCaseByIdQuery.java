/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindLeakCaseByIdQuery
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.query
 *
 * @Description : Query to find leak case by ID.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.query;

public record FindLeakCaseByIdQuery(String caseId) {
}
