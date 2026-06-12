/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeasurementPeriodSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.dto
 *
 * @Description : Custody measurement period summary DTO.
 *
 */
package dz.sh.hidra.modules.custody.application.dto;

import dz.sh.hidra.modules.custody.domain.value.CustodyPeriodStatus;

import java.time.Instant;

/**
 * Custody measurement period summary DTO.
 */
public record CustodyMeasurementPeriodSummaryDto(
        String id,
        String periodCode,
        String agreementId,
        String transferPointId,
        Instant periodStart,
        Instant periodEnd,
        CustodyPeriodStatus status
) {
}
