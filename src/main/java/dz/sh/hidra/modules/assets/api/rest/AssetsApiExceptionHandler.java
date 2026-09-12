/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest
 *
 * @Description : Stable HTTP mapping for maintainable asset concurrency conflicts.
 *
 */
package dz.sh.hidra.modules.assets.api.rest;

import dz.sh.hidra.modules.assets.api.rest.controller.SpringAssetsController;
import dz.sh.hidra.modules.assets.domain.exception.MaintainableAssetConflictException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = SpringAssetsController.class)
public final class AssetsApiExceptionHandler {

    @ExceptionHandler(MaintainableAssetConflictException.class)
    public ProblemDetail conflict(MaintainableAssetConflictException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setTitle("ASSETS_MAINTAINABLE_ASSET_CONFLICT");
        detail.setDetail(exception.getMessage());
        detail.setProperty("code", "ASSETS_MAINTAINABLE_ASSET_CONFLICT");
        return detail;
    }
}
