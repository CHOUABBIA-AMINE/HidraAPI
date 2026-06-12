/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionRun
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Detection evaluation run.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.value.*;
import java.time.Instant;

    /**
     * Detection evaluation run.
     *
         * @param id id
     * @param profileId profileId
     * @param methodId methodId
     * @param runCode runCode
     * @param evaluationStart evaluationStart
     * @param evaluationEnd evaluationEnd
     * @param status status
     * @param candidateCount candidateCount
     * @param failureReason failureReason
     * @param correlationId correlationId
     * @param createdAt createdAt
     */
    public record LeakDetectionRun(
            String id,
        String profileId,
        String methodId,
        String runCode,
        Instant evaluationStart,
        Instant evaluationEnd,
        LeakDetectionRunStatus status,
        int candidateCount,
        String failureReason,
        String correlationId,
        Instant createdAt
    ) {

        public LeakDetectionRun {
        id = normalize(id);
        profileId = normalize(profileId);
        methodId = normalize(methodId);
        runCode = normalize(runCode);
        failureReason = normalize(failureReason);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
