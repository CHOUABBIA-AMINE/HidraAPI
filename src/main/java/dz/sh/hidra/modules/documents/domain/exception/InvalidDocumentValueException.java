/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidDocumentValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.exception
 *
 * @Description : Invalid document value exception.
 *
 */
package dz.sh.hidra.modules.documents.domain.exception;

/**
 * Raised when a document value is invalid.
 */
public class InvalidDocumentValueException extends DocumentsDomainException {

    private static final long serialVersionUID = -7531995165728078938L;

	public InvalidDocumentValueException(String message) {
        super(message);
    }
}
