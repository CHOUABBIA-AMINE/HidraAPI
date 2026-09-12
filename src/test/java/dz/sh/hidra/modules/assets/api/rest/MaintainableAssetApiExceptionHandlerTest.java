/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAssetApiExceptionHandlerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest
 *
 * @Description : Verifies deterministic HTTP conflict semantics for stale maintainable assets.
 *
 */
package dz.sh.hidra.modules.assets.api.rest;

import dz.sh.hidra.modules.assets.domain.exception.MaintainableAssetConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaintainableAssetApiExceptionHandlerTest {

    @Test
    void mapsMaintainableAssetConflictToStable409ProblemCode() {
        MaintainableAssetApiExceptionHandler handler = new MaintainableAssetApiExceptionHandler();
        ProblemDetail detail = handler.conflict(new MaintainableAssetConflictException(
                "Maintainable asset changed after it was loaded. Refetch the asset before retrying."
        ));

        assertEquals(HttpStatus.CONFLICT.value(), detail.getStatus());
        assertEquals("ASSETS_MAINTAINABLE_ASSET_CONFLICT", detail.getTitle());
        assertEquals("ASSETS_MAINTAINABLE_ASSET_CONFLICT", detail.getProperties().get("code"));
        assertEquals("REFETCH", detail.getProperties().get("retry"));
    }
}
