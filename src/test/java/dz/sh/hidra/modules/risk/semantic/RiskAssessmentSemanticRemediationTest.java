/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.semantic
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.semantic;

import dz.sh.hidra.modules.risk.application.command.*;
import dz.sh.hidra.modules.risk.application.port.out.RiskEvidenceLookupPort;
import dz.sh.hidra.modules.risk.domain.model.RiskAssessment;
import dz.sh.hidra.modules.risk.domain.value.*;
import dz.sh.hidra.modules.risk.infrastructure.persistence.adapter.JpaRiskAssessmentRepositoryAdapter;
import dz.sh.hidra.modules.risk.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.organization.application.contract.risk.RiskOrganizationReferenceContract;
import dz.sh.hidra.modules.topology.application.contract.risk.RiskTopologyScopeReferenceContract;
import dz.sh.hidra.modules.identity.application.contract.risk.RiskActorContract;
import dz.sh.hidra.modules.workflow.application.contract.risk.RiskAssessmentApprovalContract;
import dz.sh.hidra.modules.audit.application.contract.risk.RiskAssessmentAuditContract;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RiskAssessmentSemanticRemediationTest {
    static final Instant NOW=Instant.parse("2026-10-08T00:00:00Z");
    final RiskAssessmentJpaRepository assessments=mock(RiskAssessmentJpaRepository.class);
    final RiskAssessmentScopeJpaRepository scopes=mock(RiskAssessmentScopeJpaRepository.class);
    final RiskAssessmentScoringJpaRepository scoring=mock(RiskAssessmentScoringJpaRepository.class);
    final RiskCatalogEntryJpaRepository catalogs=mock(RiskCatalogEntryJpaRepository.class);
    final RiskMatrixCellJpaRepository cells=mock(RiskMatrixCellJpaRepository.class);
    final RiskMatrixJpaRepository matrices=mock(RiskMatrixJpaRepository.class);
    final RiskControlJpaRepository controls=mock(RiskControlJpaRepository.class);
    final RiskTreatmentPlanJpaRepository treatments=mock(RiskTreatmentPlanJpaRepository.class);
    final RiskEvidenceLinkJpaRepository evidence=mock(RiskEvidenceLinkJpaRepository.class);
    final RiskEvidenceLookupPort lookup=mock(RiskEvidenceLookupPort.class);
    final RiskOrganizationReferenceContract organization=mock(RiskOrganizationReferenceContract.class);
    final RiskTopologyScopeReferenceContract topology=mock(RiskTopologyScopeReferenceContract.class);
    final RiskActorContract actors=mock(RiskActorContract.class);
    final RiskAssessmentApprovalContract workflow=mock(RiskAssessmentApprovalContract.class);
    final RiskAssessmentAuditContract audit=mock(RiskAssessmentAuditContract.class);
    final EntityManager em=mock(EntityManager.class);
    final JpaRiskAssessmentRepositoryAdapter adapter=new JpaRiskAssessmentRepositoryAdapter(assessments,scopes,scoring,
            catalogs,cells,matrices,controls,treatments,evidence,lookup,organization,topology,actors,workflow,audit,em);
    @BeforeEach void setup() {
        when(actors.currentActor(any())).thenReturn(new RiskActorContract.Actor("actor","username","Actor"));
        when(assessments.saveAndFlush(any())).thenAnswer(i -> i.getArgument(0));
        catalog("type","RISK_ASSESSMENT_TYPE",true); catalog("method","RISK_METHODLOGY",true);
    }
    void catalog(String id,String family,boolean active) {
        when(catalogs.findLocked(id)).thenReturn(Optional.of(new RiskCatalogEntryJpaEntity(id,family,id,active,0,false,NOW,NOW)));
    }
    @Test void draftsRemainUnscoredButStructuredScopesAreMandatory() {
        var a=assessment(RiskAssessmentStatus.DRAFT);
        assertThat(a.inherentScore()).isNull();
        assertThatThrownBy(() -> adapter.create(a,List.of())).hasMessageContaining("structured scopes");
        verifyNoInteractions(em);
    }
    @Test void creationResolvesOwnerScopesAndPersistsParentAndChildrenTogether() {
        when(topology.resolve("PIPELINE","p")).thenReturn(Optional.of(new RiskTopologyScopeReferenceContract.ScopeView("p","P-1","Pipeline")));
        var a=adapter.create(assessment(RiskAssessmentStatus.DRAFT),List.of(new RiskAssessmentScopeInput("PIPELINE","p",null,null,null,true,null)));
        verify(em).persist(any(RiskAssessmentJpaEntity.class));
        var cap=org.mockito.ArgumentCaptor.forClass(RiskAssessmentScopeJpaEntity.class);verify(em).persist(cap.capture());
        assertThat(cap.getValue().riskAssessmentId()).isEqualTo(a.id());assertThat(cap.getValue().scopeCodeSnapshot()).isEqualTo("P-1");
        verify(em).flush();
    }
    @Test void unknownScopeAndWrongInactiveCatalogsCannotPersist() {
        catalog("type","OTHER",true);
        assertThatThrownBy(() -> adapter.create(assessment(RiskAssessmentStatus.DRAFT),List.of(new RiskAssessmentScopeInput("PIPELINE","p",null,null,null,true,null))))
                .hasMessageContaining("RISK_ASSESSMENT_TYPE");
        catalog("type","RISK_ASSESSMENT_TYPE",false);
        assertThatThrownBy(() -> adapter.create(assessment(RiskAssessmentStatus.DRAFT),List.of(new RiskAssessmentScopeInput("PIPELINE","p",null,null,null,true,null))))
                .hasMessageContaining("RISK_ASSESSMENT_TYPE");
        catalog("type","RISK_ASSESSMENT_TYPE",true);
        assertThatThrownBy(() -> adapter.create(assessment(RiskAssessmentStatus.DRAFT),List.of(new RiskAssessmentScopeInput("STATION","p",null,null,null,true,null))))
                .hasMessageContaining("Unsupported");verifyNoInteractions(em);
    }
    @Test void approvedDomainRequiresRealApprovalMetadata() {
        assertThatThrownBy(() -> assessment(RiskAssessmentStatus.APPROVED)).hasMessageContaining("actual reviewer");
        assertThatThrownBy(() -> assessment(RiskAssessmentStatus.ACTIVE)).hasMessageContaining("actual reviewer");
    }
    @Test void genericSaveCannotCreateOrEnterApprovalAndMissingEvidenceDeniesBeforeWorkflow() {
        assertThatThrownBy(() -> adapter.save(assessment(RiskAssessmentStatus.DRAFT))).isInstanceOf(NoSuchElementException.class);
        when(assessments.findLocked("a")).thenReturn(Optional.of(RiskPersistenceMapper.toEntity(assessment(RiskAssessmentStatus.UNDER_REVIEW))));
        assertThatThrownBy(() -> adapter.save(assessment(RiskAssessmentStatus.DRAFT))).hasMessageContaining("explicit submission");
        validScope();
        assertThatThrownBy(() -> adapter.approve(approval())).hasMessageContaining("must have owner-validated evidence");
        verifyNoInteractions(workflow,audit);
    }
    @Test void scoringUsesSelectedCellValueAndRequiresSameAssessmentResidualContext() {
        when(assessments.findLocked("a")).thenReturn(Optional.of(RiskPersistenceMapper.toEntity(assessment(RiskAssessmentStatus.DRAFT))));
        var cell=new RiskMatrixCellJpaEntity("cell","matrix","l","c",new BigDecimal("17.250000"),"r",null,false,false,false,NOW,NOW);
        when(cells.findLocked("cell")).thenReturn(Optional.of(cell));
        var matrix=mock(RiskMatrixJpaEntity.class);when(matrix.id()).thenReturn("matrix");when(matrix.version()).thenReturn("v3");when(matrix.status()).thenReturn(RiskMatrixStatus.ACTIVE);
        when(matrices.findLocked("matrix")).thenReturn(Optional.of(matrix));
        catalog("l","RISK_LIKELIHOOD_LEVEL",true);catalog("c","RISK_CONSEQUENCE_LEVEL",true);catalog("r","RISK_RATING",true);
        var saved=adapter.score(new ScoreRiskAssessmentCommand("a","cell",null,null,null,null,null));
        assertThat(saved.inherentScore()).isEqualByComparingTo("17.25");assertThat(saved.inherentRatingId()).isEqualTo("r");
        var cap=org.mockito.ArgumentCaptor.forClass(RiskAssessmentScoringJpaEntity.class);verify(scoring).save(cap.capture());
        assertThat(cap.getValue().inherentMatrixVersion()).isEqualTo("v3");
        assertThatThrownBy(() -> adapter.score(new ScoreRiskAssessmentCommand("a","cell","cell","CONTROL","foreign-control",null,null)))
                .hasMessageContaining("must belong to this assessment");
    }
    @Test void approvalStoresActualOwnerDecisionAndAuditReceiptAndAuditFailurePreventsParentSave() {
        when(assessments.findLocked("a")).thenReturn(Optional.of(RiskPersistenceMapper.toEntity(assessment(RiskAssessmentStatus.UNDER_REVIEW))));validScope();
        var link=new RiskEvidenceLinkJpaEntity("link","a","alarm","Alarm","e",null,null,null,null,null,NOW);
        when(evidence.findByRiskAssessmentId("a")).thenReturn(List.of(link));
        when(workflow.approve(any())).thenReturn(new RiskAssessmentApprovalContract.Approval("w","t","action","review","reviewer","Reviewer","actor","username","Actor",NOW));
        when(audit.appendApproved(any())).thenReturn("audit-receipt");
        var saved=adapter.approve(approval());assertThat(saved.status()).isEqualTo(RiskAssessmentStatus.APPROVED);
        assertThat(saved.approvedAt()).isEqualTo(NOW);assertThat(saved.auditReferenceId()).isEqualTo("audit-receipt");
        verify(lookup).validate(any());
        clearInvocations(assessments);when(audit.appendApproved(any())).thenThrow(new IllegalStateException("Audit unavailable"));
        assertThatThrownBy(() -> adapter.approve(approval())).hasMessageContaining("Audit unavailable");
        verify(assessments,never()).saveAndFlush(any());
    }
    void validScope() {
        when(scopes.findByRiskAssessmentId("a")).thenReturn(List.of(new RiskAssessmentScopeJpaEntity("s","a","PIPELINE","p",null,null,null,null,null,true,null,NOW)));
        when(topology.resolve("PIPELINE","p")).thenReturn(Optional.of(new RiskTopologyScopeReferenceContract.ScopeView("p",null,null)));
    }
    ApproveRiskAssessmentCommand approval() {return new ApproveRiskAssessmentCommand("a","w","t","transition","review",NOW,null,null,null,null);}
    private static RiskAssessment assessment(RiskAssessmentStatus status) {
        return new RiskAssessment("a", "register", "A-1", "Assessment", null, "type", "method", null, "scenario", status, NOW, null, null, "actor", "Actor", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, NOW, NOW);
    }
}
