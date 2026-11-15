/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyMeasurementCaptureRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : REST Adapter
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.request
 * @Description : Validates exact capture reference identifiers and assertion unit.
 */
package dz.sh.hidra.modules.custody.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
public record CustodyMeasurementCaptureRequest(@NotNull UUID commandId,
        @NotBlank String meterRunSnapshotId,@NotBlank String measurementTypeId,
        @NotNull UUID productPublicationId,@NotBlank String agreementPartyId,
        @NotBlank String unitId,@NotBlank String intervalEvidenceId) {}
