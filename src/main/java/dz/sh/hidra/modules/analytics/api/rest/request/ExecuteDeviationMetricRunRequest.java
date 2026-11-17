/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExecuteDeviationMetricRunRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.request
 *
 * @Description : Validated bounded metric source and version request.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name="ExecuteDeviationMetricRunRequest")
public record ExecuteDeviationMetricRunRequest(
        @NotBlank @Size(max=80) String definitionVersionId,
        @NotBlank @Size(max=80) String evaluationId) { }
