/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvidenceLinkedAlarmRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.request
 *
 * @Description : Validates explicit operator selection of an existing Monitoring evaluation.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EvidenceLinkedAlarmRequest(
        @NotBlank @Size(max = 80) String evaluationId,
        @NotBlank @Size(max = 80) String alarmTypeId,
        @NotBlank @Size(max = 80) String severityId,
        @NotBlank @Size(max = 255) String titleFr) { }
