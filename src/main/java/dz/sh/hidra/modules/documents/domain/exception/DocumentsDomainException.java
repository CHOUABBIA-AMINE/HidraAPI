/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsDomainException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.exception
 *
 * @Description : Base documents domain exception.
 *
 */
package dz.sh.hidra.modules.documents.domain.exception;

/**
 * Base exception for documents domain failures.
 */
public class DocumentsDomainException extends RuntimeException {

    private static final long serialVersionUID = 7121732276022913650L;

	public DocumentsDomainException(String message) {
        super(requireMessage(message));
    }

    public DocumentsDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Documents exception message must not be null or blank.");
        }
        return message.trim();
    }
}
