/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationTransformationRuleJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationTransformationRule.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for IntegrationTransformationRule.
     */
    @Entity
    @Table(name = "hidra_integration_transformation_rule")
    public class IntegrationTransformationRuleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "mapping_profile_id", nullable = false, length = 80)
    private String mappingProfileId;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "rule_type_id", nullable = false, length = 80)
    private String ruleTypeId;

    @Column(name = "expression", nullable = true, length = 2000)
    private String expression;

    @Column(name = "configuration_json", nullable = true, columnDefinition = "jsonb")
    private String configurationJson;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrationTransformationRuleJpaEntity() {
            // Required by JPA.
        }

        public IntegrationTransformationRuleJpaEntity(
                String id,
            String mappingProfileId,
            String code,
            String ruleTypeId,
            String expression,
            String configurationJson,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.mappingProfileId = mappingProfileId;
        this.code = code;
        this.ruleTypeId = ruleTypeId;
        this.expression = expression;
        this.configurationJson = configurationJson;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String mappingProfileId() {
        return mappingProfileId;
    }


    public String code() {
        return code;
    }


    public String ruleTypeId() {
        return ruleTypeId;
    }


    public String expression() {
        return expression;
    }


    public String configurationJson() {
        return configurationJson;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
