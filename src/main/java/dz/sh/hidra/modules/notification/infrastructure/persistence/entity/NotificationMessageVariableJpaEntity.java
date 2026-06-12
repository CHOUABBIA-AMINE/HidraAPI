/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationMessageVariableJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationMessageVariable.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.entity;

import dz.sh.hidra.modules.notification.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for NotificationMessageVariable.
     */
    @Entity
    @Table(name = "hidra_notification_message_variable")
    public class NotificationMessageVariableJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "message_id", nullable = false, length = 80)
    private String messageId;

    @Column(name = "variable_name", nullable = false, length = 160)
    private String variableName;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false, length = 40)
    private NotificationValueType valueType;

    @Column(name = "value_snapshot", nullable = true, length = 2000)
    private String valueSnapshot;

    @Column(name = "masked", nullable = false)
    private boolean masked;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected NotificationMessageVariableJpaEntity() {
            // Required by JPA.
        }

        public NotificationMessageVariableJpaEntity(
                String id,
            String messageId,
            String variableName,
            NotificationValueType valueType,
            String valueSnapshot,
            boolean masked,
            Instant createdAt
        ) {
            this.id = id;
        this.messageId = messageId;
        this.variableName = variableName;
        this.valueType = valueType;
        this.valueSnapshot = valueSnapshot;
        this.masked = masked;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String messageId() {
        return messageId;
    }


    public String variableName() {
        return variableName;
    }


    public NotificationValueType valueType() {
        return valueType;
    }


    public String valueSnapshot() {
        return valueSnapshot;
    }


    public boolean masked() {
        return masked;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
