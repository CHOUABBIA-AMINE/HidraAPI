/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStepJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : JPA entity for workflow definition steps.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for workflow definition steps.
 *
 * <p>Architecture role:
 * Persistence-only workflow representation. It must not be exposed to domain, application, or REST
 * layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_workflow_step")
public class WorkflowStepJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "definition_id", nullable = false)
    private String definitionId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name_ar", nullable = true)
    private String nameAr;

    @Column(name = "name_fr", nullable = false)
    private String nameFr;

    @Column(name = "name_en", nullable = true)
    private String nameEn;

    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    @Column(name = "mandatory", nullable = false)
    private Boolean mandatory;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected WorkflowStepJpaEntity() {
        // Required by JPA.
    }

    public WorkflowStepJpaEntity(
            String id,
            String definitionId,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            Integer stepOrder,
            Boolean mandatory,
            Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.definitionId = definitionId;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.stepOrder = stepOrder;
        this.mandatory = mandatory;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
