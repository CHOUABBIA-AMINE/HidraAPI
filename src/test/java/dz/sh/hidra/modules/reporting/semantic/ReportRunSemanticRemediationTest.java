/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRunSemanticRemediationTest
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
import dz.sh.hidra.modules.reporting.application.command.QueueReportRunCommand;
import dz.sh.hidra.modules.reporting.application.port.out.*;
import dz.sh.hidra.modules.reporting.application.service.ReportingApplicationService;
import dz.sh.hidra.modules.reporting.domain.model.*;
import dz.sh.hidra.modules.reporting.domain.value.*;
import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
class ReportRunSemanticRemediationTest {
    static final Instant NOW=Instant.parse("2026-10-07T00:00:00Z");
    final ReportDefinitionRepositoryPort definitions=mock(ReportDefinitionRepositoryPort.class);
    final ReportRequestRepositoryPort requests=mock(ReportRequestRepositoryPort.class);
    final ReportRunRepositoryPort runs=mock(ReportRunRepositoryPort.class);
    final ReportQueueEvidencePort evidence=mock(ReportQueueEvidencePort.class);
    ReportRequest request(ReportRequestStatus status){return new ReportRequest("request","definition","actor","user","Actor","ROLE",null,null,NOW,"purpose",status,null,"workflow",NOW,NOW);}
    ReportDefinition definition(ReportDefinitionStatus status,boolean approval,boolean restricted){return new ReportDefinition("definition","code",null,"Rapport",null,"category","reporting",null,status,null,approval,restricted,NOW,NOW);}
    ReportingApplicationService service(boolean access,boolean approval){return new ReportingApplicationService(definitions,requests,runs,mock(ReportOutputArtifactRepositoryPort.class),q->access,(w,r)->approval,id->true,evidence);}
    void ready(ReportRequestStatus status,boolean approval,boolean restricted){
        when(requests.findById("request")).thenReturn(Optional.of(request(status)));
        when(definitions.findById("definition")).thenReturn(Optional.of(definition(ReportDefinitionStatus.ACTIVE,approval,restricted)));
        when(evidence.eligibleTemplate("version","definition")).thenReturn(true);
        when(evidence.requiredParametersPresent("request","definition")).thenReturn(true);
        when(runs.save(any())).thenAnswer(i->i.getArgument(0));
    }
    QueueReportRunCommand command(){return new QueueReportRunCommand("request","definition","version",ReportRunMode.MANUAL,null);}
    @Test void terminalEvidenceIsRequired(){
        assertThrows(InvalidReportingValueException.class,()->run(ReportRunStatus.COMPLETED,null,null));
        assertThrows(InvalidReportingValueException.class,()->run(ReportRunStatus.FAILED,null," "));
        assertNotNull(run(ReportRunStatus.COMPLETED,NOW,null));assertNotNull(run(ReportRunStatus.FAILED,null,"failure"));
        assertNotNull(run(ReportRunStatus.CANCELLED,null,null));
    }
    static ReportRun run(ReportRunStatus status,Instant completed,String reason){return new ReportRun("run","request","definition","version",status,ReportRunMode.MANUAL,NOW,null,completed,null,reason,0L,0L,null,null,NOW,NOW);}
    @Test void legalNonApprovalAndApprovedQueuesPersist(){
        ready(ReportRequestStatus.SUBMITTED,false,false);assertEquals(ReportRunStatus.QUEUED,service(true,true).queueReportRun(command()).status());
        ready(ReportRequestStatus.APPROVED,true,false);assertNotNull(service(true,true).queueReportRun(command()));
    }
    @Test void allNonQueueableRequestStatesFail(){
        for(var status:ReportRequestStatus.values())if(status!=ReportRequestStatus.SUBMITTED && status!=ReportRequestStatus.APPROVED){
            ready(status,false,false);assertThrows(InvalidReportingValueException.class,()->service(true,true).queueReportRun(command()));
        }
        verify(runs,never()).save(any());
    }
    @Test void missingRequestDefinitionOrMismatchedDefinitionFails(){
        ready(ReportRequestStatus.SUBMITTED,false,false);when(requests.findById("request")).thenReturn(Optional.empty());
        assertThrows(InvalidReportingValueException.class,()->service(true,true).queueReportRun(command()));
        ready(ReportRequestStatus.SUBMITTED,false,false);when(definitions.findById("definition")).thenReturn(Optional.empty());
        assertThrows(InvalidReportingValueException.class,()->service(true,true).queueReportRun(command()));
        ready(ReportRequestStatus.SUBMITTED,false,false);var wrong=new QueueReportRunCommand("request","other","version",ReportRunMode.MANUAL,null);
        when(definitions.findById("other")).thenReturn(Optional.of(new ReportDefinition("other","other",null,"Other",null,"category","reporting",null,ReportDefinitionStatus.ACTIVE,null,false,false,NOW,NOW)));
        assertThrows(InvalidReportingValueException.class,()->service(true,true).queueReportRun(wrong));verify(runs,never()).save(any());
    }
    @Test void inactiveDefinitionAndInvalidTemplateFail(){
        ready(ReportRequestStatus.SUBMITTED,false,false);when(definitions.findById("definition")).thenReturn(Optional.of(definition(ReportDefinitionStatus.RETIRED,false,false)));
        assertThrows(InvalidReportingValueException.class,()->service(true,true).queueReportRun(command()));
        ready(ReportRequestStatus.SUBMITTED,false,false);when(evidence.eligibleTemplate("version","definition")).thenReturn(false);
        assertThrows(InvalidReportingValueException.class,()->service(true,true).queueReportRun(command()));verify(runs,never()).save(any());
    }
    @Test void requiredConcreteParametersCannotBeBypassed(){
        ready(ReportRequestStatus.SUBMITTED,false,false);when(evidence.requiredParametersPresent("request","definition")).thenReturn(false);
        assertThrows(InvalidReportingValueException.class,()->service(true,true).queueReportRun(command()));verify(runs,never()).save(any());
    }
    @Test void approvalRequiresStateAndOwnerConfirmation(){
        ready(ReportRequestStatus.SUBMITTED,true,false);assertThrows(InvalidReportingValueException.class,()->service(true,true).queueReportRun(command()));
        ready(ReportRequestStatus.APPROVED,true,false);assertThrows(InvalidReportingValueException.class,()->service(true,false).queueReportRun(command()));verify(runs,never()).save(any());
    }
    @Test void restrictedAccessUsesPersistedRequesterScope(){
        ready(ReportRequestStatus.SUBMITTED,false,true);
        when(requests.accessPoliciesForDefinition("definition")).thenReturn(List.of(new ReportRequestRepositoryPort.AccessPolicyView("ACTOR","actor","REPORT_READ")));
        assertThrows(InvalidReportingValueException.class,()->service(false,true).queueReportRun(command()));
        var svc=new ReportingApplicationService(definitions,requests,runs,mock(ReportOutputArtifactRepositoryPort.class),q->{assertEquals("actor",q.actorId());assertEquals("REPORT_READ",q.permissionCode());return true;},(w,r)->true,id->true,evidence);
        assertNotNull(svc.queueReportRun(command()));
    }
}
