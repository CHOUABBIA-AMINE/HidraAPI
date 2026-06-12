/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmCommentJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AlarmComment.
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
     * Database-backed JPA entity for AlarmComment.
     */
    @Entity
    @Table(name = "hidra_alarm_comment")
    public class AlarmCommentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "alarm_id", nullable = false, length = 80)
    private String alarmId;

    @Column(name = "comment_text", nullable = false, columnDefinition = "text")
    private String commentText;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 80)
    private AlarmCommentVisibility visibility;

    @Column(name = "created_by_actor_id", nullable = false, length = 80)
    private String createdByActorId;

    @Column(name = "created_by_display_name", nullable = true, length = 255)
    private String createdByDisplayName;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = true)
    private Instant updatedAt;

        protected AlarmCommentJpaEntity() {
            // Required by JPA.
        }

        public AlarmCommentJpaEntity(
                String id,
            String alarmId,
            String commentText,
            AlarmCommentVisibility visibility,
            String createdByActorId,
            String createdByDisplayName,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.alarmId = alarmId;
        this.commentText = commentText;
        this.visibility = visibility;
        this.createdByActorId = createdByActorId;
        this.createdByDisplayName = createdByDisplayName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String alarmId() {
        return alarmId;
    }


    public String commentText() {
        return commentText;
    }


    public AlarmCommentVisibility visibility() {
        return visibility;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public String createdByDisplayName() {
        return createdByDisplayName;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
