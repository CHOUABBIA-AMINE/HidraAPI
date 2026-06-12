/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsInsightSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.dto
 *
 * @Description : Analytics insight summary DTO.
 *
 */
package dz.sh.hidra.modules.analytics.application.dto;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsInsightStatus;

import java.math.BigDecimal;

/**
 * Analytics insight summary DTO.
 */
public record AnalyticsInsightSummaryDto(
        String id,
        String insightType,
        String subjectAreaId,
        String scopeType,
        String scopeId,
        String title,
        String severityId,
        BigDecimal confidenceScore,
        AnalyticsInsightStatus status
) {
}
