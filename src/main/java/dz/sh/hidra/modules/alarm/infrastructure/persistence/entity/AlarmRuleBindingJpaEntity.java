/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmRuleBindingJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AlarmRuleBinding.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AlarmRuleBinding.
     */
    @Entity
    @Table(name = "hidra_alarm_rule_binding")
    public class AlarmRuleBindingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "monitoring_rule_id", nullable = true, length = 80)
    private String monitoringRuleId;

    @Column(name = "monitoring_threshold_id", nullable = true, length = 80)
    private String monitoringThresholdId;

    @Column(name = "candidate_type_id", nullable = true, length = 80)
    private String candidateTypeId;

    @Column(name = "alarm_type_id", nullable = false, length = 80)
    private String alarmTypeId;

    @Column(name = "default_severity_id", nullable = false, length = 80)
    private String defaultSeverityId;

    @Column(name = "default_priority_id", nullable = true, length = 80)
    private String defaultPriorityId;

    @Column(name = "auto_raise", nullable = false)
    private boolean autoRaise;

    @Column(name = "requires_operator_confirmation", nullable = false)
    private boolean requiresOperatorConfirmation;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "effective_from", nullable = false)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AlarmRuleBindingJpaEntity() {
            // Required by JPA.
        }

        public AlarmRuleBindingJpaEntity(
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
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.monitoringRuleId = monitoringRuleId;
        this.monitoringThresholdId = monitoringThresholdId;
        this.candidateTypeId = candidateTypeId;
        this.alarmTypeId = alarmTypeId;
        this.defaultSeverityId = defaultSeverityId;
        this.defaultPriorityId = defaultPriorityId;
        this.autoRaise = autoRaise;
        this.requiresOperatorConfirmation = requiresOperatorConfirmation;
        this.active = active;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String monitoringRuleId() {
        return monitoringRuleId;
    }


    public String monitoringThresholdId() {
        return monitoringThresholdId;
    }


    public String candidateTypeId() {
        return candidateTypeId;
    }


    public String alarmTypeId() {
        return alarmTypeId;
    }


    public String defaultSeverityId() {
        return defaultSeverityId;
    }


    public String defaultPriorityId() {
        return defaultPriorityId;
    }


    public boolean autoRaise() {
        return autoRaise;
    }


    public boolean requiresOperatorConfirmation() {
        return requiresOperatorConfirmation;
    }


    public boolean active() {
        return active;
    }


    public Instant effectiveFrom() {
        return effectiveFrom;
    }


    public Instant effectiveTo() {
        return effectiveTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
