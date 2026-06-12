/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEvidenceLinkJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AlarmEvidenceLink.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.entity;

import dz.sh.hidra.modules.alarm.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AlarmEvidenceLink.
     */
    @Entity
    @Table(name = "hidra_alarm_evidence_link")
    public class AlarmEvidenceLinkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "alarm_id", nullable = false, length = 80)
    private String alarmId;

    @Enumerated(EnumType.STRING)
    @Column(name = "evidence_type", nullable = false, length = 80)
    private AlarmEvidenceType evidenceType;

    @Column(name = "evidence_reference_id", nullable = false, length = 80)
    private String evidenceReferenceId;

    @Column(name = "evidence_code_snapshot", nullable = true, length = 160)
    private String evidenceCodeSnapshot;

    @Column(name = "evidence_name_snapshot", nullable = true, length = 500)
    private String evidenceNameSnapshot;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AlarmEvidenceLinkJpaEntity() {
            // Required by JPA.
        }

        public AlarmEvidenceLinkJpaEntity(
                String id,
            String alarmId,
            AlarmEvidenceType evidenceType,
            String evidenceReferenceId,
            String evidenceCodeSnapshot,
            String evidenceNameSnapshot,
            String description,
            Instant createdAt
        ) {
            this.id = id;
        this.alarmId = alarmId;
        this.evidenceType = evidenceType;
        this.evidenceReferenceId = evidenceReferenceId;
        this.evidenceCodeSnapshot = evidenceCodeSnapshot;
        this.evidenceNameSnapshot = evidenceNameSnapshot;
        this.description = description;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String alarmId() {
        return alarmId;
    }


    public AlarmEvidenceType evidenceType() {
        return evidenceType;
    }


    public String evidenceReferenceId() {
        return evidenceReferenceId;
    }


    public String evidenceCodeSnapshot() {
        return evidenceCodeSnapshot;
    }


    public String evidenceNameSnapshot() {
        return evidenceNameSnapshot;
    }


    public String description() {
        return description;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
