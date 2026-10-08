/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentScoringJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import jakarta.persistence.*;
@Entity
@Table(name="hidra_risk_assessment_scoring")
public class RiskAssessmentScoringJpaEntity {
    @Id
    @Column(name="assessment_id", length=80, nullable=false)
    private String assessmentId;
    @Column(name="inherent_cell_id", length=80, nullable=false)
    private String inherentCellId;
    @Column(name="inherent_matrix_id", length=80, nullable=false)
    private String inherentMatrixId;
    @Column(name="inherent_matrix_version", length=80, nullable=false)
    private String inherentMatrixVersion;
    @Column(name="residual_cell_id", length=80, nullable=true)
    private String residualCellId;
    @Column(name="residual_matrix_id", length=80, nullable=true)
    private String residualMatrixId;
    @Column(name="residual_matrix_version", length=80, nullable=true)
    private String residualMatrixVersion;
    @Column(name="residual_context_type", length=80, nullable=true)
    private String residualContextType;
    @Column(name="residual_context_id", length=80, nullable=true)
    private String residualContextId;
    protected RiskAssessmentScoringJpaEntity() {}
    public RiskAssessmentScoringJpaEntity(String assessmentId, String inherentCellId, String inherentMatrixId, String inherentMatrixVersion, String residualCellId, String residualMatrixId, String residualMatrixVersion, String residualContextType, String residualContextId) {
        this.assessmentId=assessmentId;
        this.inherentCellId=inherentCellId;
        this.inherentMatrixId=inherentMatrixId;
        this.inherentMatrixVersion=inherentMatrixVersion;
        this.residualCellId=residualCellId;
        this.residualMatrixId=residualMatrixId;
        this.residualMatrixVersion=residualMatrixVersion;
        this.residualContextType=residualContextType;
        this.residualContextId=residualContextId;
    }
    public String assessmentId() { return assessmentId; }
    public String inherentCellId() { return inherentCellId; }
    public String inherentMatrixId() { return inherentMatrixId; }
    public String inherentMatrixVersion() { return inherentMatrixVersion; }
    public String residualCellId() { return residualCellId; }
    public String residualMatrixId() { return residualMatrixId; }
    public String residualMatrixVersion() { return residualMatrixVersion; }
    public String residualContextType() { return residualContextType; }
    public String residualContextId() { return residualContextId; }
}
