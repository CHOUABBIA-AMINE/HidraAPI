/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindAuditEventByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.query
 *
 * @Description : Query to find audit event by ID.
 *
 */
package dz.sh.hidra.modules.audit.application.query;

/**
 * Query to find audit event by ID.
 */
public record FindAuditEventByIdQuery(String auditEventId) {
}
