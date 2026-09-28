/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRun id must not be blank.");
        }
        // HRA-051 required: profileId
        if (profileId == null || profileId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRun profile id must not be blank.");
        }
        // HRA-051 required: methodId
        if (methodId == null || methodId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRun method id must not be blank.");
        }
        // HRA-051 required: runCode
        if (runCode == null || runCode.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRun run code must not be blank.");
        }
        // HRA-051 required: evaluationStart
        if (evaluationStart == null) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRun evaluation start must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRun status must not be null.");
        }

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
