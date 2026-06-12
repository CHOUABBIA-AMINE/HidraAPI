/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindCustodyTransferTicketByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.query
 *
 * @Description : Query to find custody transfer ticket by ID.
 *
 */
package dz.sh.hidra.modules.custody.application.query;

/**
 * Query to find custody transfer ticket by ID.
 */
public record FindCustodyTransferTicketByIdQuery(String transferTicketId) {
}
