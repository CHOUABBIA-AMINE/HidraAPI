/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoritativeDeviationResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.response
 *
 * @Description : Exposes a typed evaluation outcome and verified evidence lineage.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;

@Schema(name = "MonitoringAuthoritativeDeviationResponse")
public record AuthoritativeDeviationResponse(
        String id, String ruleId, long ruleRevision, Instant evaluatedAt, Instant recordedAt,
        String actorId, String unitId, String outcome, BigDecimal actual, BigDecimal expected,
        BigDecimal difference, String targetId, String targetRevisionId, String approvalId,
        Instant approvedAt, String trustedReadingId, String rawReadingId, String assessmentId, String bindingId,
        String qualityCodeId, String trustLevel, Instant observedAt, Instant trustedAt,
        String freshnessPolicyVersion) { }
