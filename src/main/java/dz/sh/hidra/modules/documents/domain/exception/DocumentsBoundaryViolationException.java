/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsBoundaryViolationException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.exception
 *
 * @Description : Documents boundary violation exception.
 *
 */
package dz.sh.hidra.modules.documents.domain.exception;

/**
 * Raised when documents attempts to own foreign business records, audit evidence, workflow routing, or storage internals.
 */
public class DocumentsBoundaryViolationException extends DocumentsDomainException {

    private static final long serialVersionUID = 8768731218584429916L;

	public DocumentsBoundaryViolationException(String message) {
        super(message);
    }
}
