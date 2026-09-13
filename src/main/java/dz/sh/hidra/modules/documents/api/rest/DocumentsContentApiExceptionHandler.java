/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsContentApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest
 *
 * @Description : Stable HTTP problem mapping for documents binary-content transfer failures.
 *
 */
package dz.sh.hidra.modules.documents.api.rest;

import dz.sh.hidra.modules.documents.api.rest.controller.SpringDocumentsController;
import dz.sh.hidra.modules.documents.domain.exception.DocumentContentTransferException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = SpringDocumentsController.class)
public final class DocumentsContentApiExceptionHandler {

    @ExceptionHandler(DocumentContentTransferException.class)
    public ProblemDetail handle(DocumentContentTransferException exception) {
        HttpStatus status = switch (exception.reason()) {
            case INVALID_CONTENT -> HttpStatus.BAD_REQUEST;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case STORAGE_FAILURE -> HttpStatus.SERVICE_UNAVAILABLE;
        };
        String code = switch (exception.reason()) {
            case INVALID_CONTENT -> "DOCUMENTS_CONTENT_INVALID";
            case NOT_FOUND -> "DOCUMENTS_CONTENT_NOT_FOUND";
            case STORAGE_FAILURE -> "DOCUMENTS_CONTENT_STORAGE_FAILURE";
        };
        ProblemDetail detail = ProblemDetail.forStatus(status);
        detail.setTitle(code);
        detail.setDetail(exception.getMessage());
        detail.setProperty("code", code);
        return detail;
    }
}
