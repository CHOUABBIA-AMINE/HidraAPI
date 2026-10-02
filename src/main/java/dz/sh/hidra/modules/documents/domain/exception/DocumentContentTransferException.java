/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentContentTransferException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.exception
 *
 * @Description : Deterministic documents failure for binary-content transfer contracts.
 *
 */
package dz.sh.hidra.modules.documents.domain.exception;

public final class DocumentContentTransferException extends DocumentsDomainException {

    private static final long serialVersionUID = 6910369097525729747L;

    private final Reason reason;

    public DocumentContentTransferException(Reason reason, String message) {
        super(message);
        this.reason = reason;
    }

    public DocumentContentTransferException(Reason reason, String message, Throwable cause) {
        super(message, cause);
        this.reason = reason;
    }

    public Reason reason() {
        return reason;
    }

    public enum Reason {
        INVALID_CONTENT,
        NOT_FOUND,
        STORAGE_FAILURE
    }
}
