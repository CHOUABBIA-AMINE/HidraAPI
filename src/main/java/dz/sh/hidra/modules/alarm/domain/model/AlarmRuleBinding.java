/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmRuleBinding
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Mapping between monitoring candidates/rules and formal alarm classification.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import java.time.Instant;

    /**
     * Mapping between monitoring candidates/rules and formal alarm classification.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param monitoringRuleId monitoringRuleId
     * @param monitoringThresholdId monitoringThresholdId
     * @param candidateTypeId candidateTypeId
     * @param alarmTypeId alarmTypeId
     * @param defaultSeverityId defaultSeverityId
     * @param defaultPriorityId defaultPriorityId
     * @param autoRaise autoRaise
     * @param requiresOperatorConfirmation requiresOperatorConfirmation
     * @param active active
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AlarmRuleBinding(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String monitoringRuleId,
        String monitoringThresholdId,
        String candidateTypeId,
        String alarmTypeId,
        String defaultSeverityId,
        String defaultPriorityId,
        boolean autoRaise,
        boolean requiresOperatorConfirmation,
        boolean active,
        Instant effectiveFrom,
        Instant effectiveTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AlarmRuleBinding {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        monitoringRuleId = normalize(monitoringRuleId);
        monitoringThresholdId = normalize(monitoringThresholdId);
        candidateTypeId = normalize(candidateTypeId);
        alarmTypeId = normalize(alarmTypeId);
        defaultSeverityId = normalize(defaultSeverityId);
        defaultPriorityId = normalize(defaultPriorityId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
