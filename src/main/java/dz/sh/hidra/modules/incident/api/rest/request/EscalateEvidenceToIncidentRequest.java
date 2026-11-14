/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : EscalateEvidenceToIncidentRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Record
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.request
 * @Description : Client declaration with no caller-supplied actor or scope.
 */
package dz.sh.hidra.modules.incident.api.rest.request;

public record EscalateEvidenceToIncidentRequest(
        String sourceType, String sourceId, String classificationId,
        String severityId, String titleFr, String reason) {}
