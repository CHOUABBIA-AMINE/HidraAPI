/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionPageDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.dto
 *
 * @Description : Paged application read model for alarm suppression evidence.
 *
 */
package dz.sh.hidra.modules.alarm.application.dto;

import java.util.List;

/**
 * Stable application-layer page of suppression evidence.
 */
public record AlarmSuppressionPageDto(
        List<AlarmSuppressionDto> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
}
