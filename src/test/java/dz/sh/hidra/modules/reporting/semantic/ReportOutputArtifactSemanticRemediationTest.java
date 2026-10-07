/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportOutputArtifactSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.semantic
 *
 * @Description : Enforces Reporting execution and output integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.reporting.semantic;
import dz.sh.hidra.modules.reporting.application.command.GenerateReportArtifactCommand;
import dz.sh.hidra.modules.reporting.application.port.out.*;
import dz.sh.hidra.modules.reporting.application.service.ReportingApplicationService;
import dz.sh.hidra.modules.reporting.domain.model.*;
import dz.sh.hidra.modules.reporting.domain.value.*;
import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.documents.application.contract.reporting.ReportingDocumentReferenceContract;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
class ReportOutputArtifactSemanticRemediationTest {
    static final Instant NOW=Instant.parse("2026-10-07T00:00:00Z");
    final ReportRunRepositoryPort runs=mock(ReportRunRepositoryPort.class);
    final ReportOutputArtifactRepositoryPort artifacts=mock(ReportOutputArtifactRepositoryPort.class);
    final ReportingDocumentReferenceContract documents=mock(ReportingDocumentReferenceContract.class);
    ReportingApplicationService service(){return new ReportingApplicationService(mock(ReportDefinitionRepositoryPort.class),mock(ReportRequestRepositoryPort.class),runs,artifacts,q->false,(w,r)->false,id->false,mock(ReportQueueEvidencePort.class),documents);}
    static ReportOutputArtifact artifact(String storage,String document,String checksum){return new ReportOutputArtifact("artifact","run",ReportArtifactType.PRIMARY_REPORT,ReportFormat.PDF,"report.pdf","application/pdf",storage,document,checksum,1L,NOW,null,NOW);}
    GenerateReportArtifactCommand command(String storage,String document){return new GenerateReportArtifactCommand("run",ReportArtifactType.PRIMARY_REPORT,ReportFormat.PDF,"report.pdf","application/pdf",storage,document,"hash",1L,null);}
    void ready(){when(runs.findById("run")).thenReturn(Optional.of(new ReportRun("run","request","definition","version",ReportRunStatus.QUEUED,ReportRunMode.MANUAL,NOW,null,null,null,null,0L,0L,null,null,NOW,NOW)));when(artifacts.save(any())).thenAnswer(i->i.getArgument(0));}
    @Test void normalizedDocumentOrStorageReferenceIsRequired(){
        assertThrows(InvalidReportingValueException.class,()->artifact(null,null,"hash"));assertThrows(InvalidReportingValueException.class,()->artifact(" "," ","hash"));
        assertTrue(artifact(" storage ",null,"hash").reproducibleArtifact());assertEquals("document",artifact(null," document ","hash").documentReferenceId());
    }
    @Test void checksumRequirementIsPreserved(){assertThrows(InvalidReportingValueException.class,()->artifact("storage",null," "));}
    @Test void unknownRunCannotGenerateArtifact(){when(runs.findById("run")).thenReturn(Optional.empty());assertThrows(InvalidReportingValueException.class,()->service().generateReportArtifact(command("storage",null)));verify(artifacts,never()).save(any());verifyNoInteractions(documents);}
    @Test void knownRunWithoutDocumentsEvidenceIsRejected(){ready();assertThrows(InvalidReportingValueException.class,()->service().generateReportArtifact(command(" ",null)));verify(artifacts,never()).save(any());}
    @Test void eachSuppliedOwnerReferenceMustExist(){
        ready();when(documents.storageObjectExists("storage")).thenReturn(true);when(documents.documentExists("document")).thenReturn(false);
        assertThrows(InvalidReportingValueException.class,()->service().generateReportArtifact(command("storage","document")));
        when(documents.storageObjectExists("storage")).thenReturn(false);when(documents.documentExists("document")).thenReturn(true);
        assertThrows(InvalidReportingValueException.class,()->service().generateReportArtifact(command("storage","document")));verify(artifacts,never()).save(any());
    }
    @Test void storageDocumentAndBothAreLegalWithoutCompletedOnlyRule(){
        ready();when(documents.storageObjectExists("storage")).thenReturn(true);when(documents.documentExists("document")).thenReturn(true);
        assertNotNull(service().generateReportArtifact(command(" storage ",null)));assertNotNull(service().generateReportArtifact(command(null," document ")));
        assertNotNull(service().generateReportArtifact(command("storage","document")));verify(documents,times(2)).storageObjectExists("storage");verify(documents,times(2)).documentExists("document");
    }
}
