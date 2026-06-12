/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AttributeDefinitionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AttributeDefinition.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.identity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AttributeDefinition.
     */
    @Entity
    @Table(name = "hidra_identity_attribute_definition")
    public class AttributeDefinitionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "attribute_target", nullable = false, length = 80)
    private AttributeTarget attributeTarget;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false, length = 80)
    private AttributeDataType dataType;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "multi_valued", nullable = false)
    private boolean multiValued;

    @Column(name = "sensitive", nullable = false)
    private boolean sensitive;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PermissionStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AttributeDefinitionJpaEntity() {
            // Required by JPA.
        }

        public AttributeDefinitionJpaEntity(
                String id,
            String code,
            String name,
            AttributeTarget attributeTarget,
            AttributeDataType dataType,
            String description,
            boolean multiValued,
            boolean sensitive,
            PermissionStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.name = name;
        this.attributeTarget = attributeTarget;
        this.dataType = dataType;
        this.description = description;
        this.multiValued = multiValued;
        this.sensitive = sensitive;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String name() {
        return name;
    }


    public AttributeTarget attributeTarget() {
        return attributeTarget;
    }


    public AttributeDataType dataType() {
        return dataType;
    }


    public String description() {
        return description;
    }


    public boolean multiValued() {
        return multiValued;
    }


    public boolean sensitive() {
        return sensitive;
    }


    public PermissionStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
