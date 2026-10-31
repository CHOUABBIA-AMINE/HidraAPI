package dz.sh.hidra.modules.simulation.qualification;

import dz.sh.hidra.modules.simulation.api.rest.controller.ManufacturerCharacteristicRevisionController;
import dz.sh.hidra.modules.simulation.api.rest.request.ManufacturerCharacteristicRevisionRequests;
import dz.sh.hidra.modules.simulation.application.port.in.ManufacturerCharacteristicRevisionUseCase;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** Operator qualification is only a protected, exact Workflow-backed use-case request. */
class ManufacturerCharacteristicOperatorQualificationTest {
    @AfterEach void clear(){SecurityContextHolder.clearContext();}
    private static ManufacturerCharacteristicRevisionRequests.Qualify suppliedApproval() {
        return new ManufacturerCharacteristicRevisionRequests.Qualify("company-a",
            new ManufacturerCharacteristicRevisionRequests.Approval("definition-a",1,
                "manufacturer-revision","QUALIFY","instance-a","task-a","action-a"));
    }
    @Test void qualificationPassesExactWorkflowEvidenceAndNeverClaimsEngineCapability() {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("synthetic",null,List.of()));
        var port=mock(ManufacturerCharacteristicRevisionUseCase.class);
        when(port.execute(any())).thenAnswer(call->{
            ManufacturerCharacteristicRevisionUseCase.Command command=call.getArgument(0);
            assertEquals(ManufacturerCharacteristicRevisionUseCase.Operation.QUALIFY,command.operation());
            assertEquals("company-a",command.companyPartyId());
            assertEquals("m-source",command.sourceId());
            assertEquals("v1",command.revisionId());
            assertEquals("action-a",command.approval().actionId());
            assertEquals("task-a",command.approval().taskId());
            assertEquals("instance-a",command.approval().instanceId());
            assertNull(command.suppliedRevision());
            return new ManufacturerCharacteristicRevisionUseCase.Result(command.operation(),
                command.companyPartyId(),command.sourceId(),command.revisionId(),
                "a".repeat(64),null,List.of(),Instant.parse("2026-10-31T10:00:00Z"),true);
        });
        var result=new ManufacturerCharacteristicRevisionController(port)
            .qualify("m-source","v1",suppliedApproval());
        assertTrue(result.workflowQualified());
        verify(port).execute(any());
    }
    @Test void unauthenticatedQualificationNeverInvokesWorkflowOwner() {
        var port=mock(ManufacturerCharacteristicRevisionUseCase.class);
        var controller=new ManufacturerCharacteristicRevisionController(port);
        assertThrows(AuthenticationCredentialsNotFoundException.class,()->
                controller.qualify("m-source","v1",suppliedApproval()));
        verifyNoInteractions(port);
    }
    @Test void missingApprovalRefusedBeforeNativeOwnerInvocation() {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("synthetic",null,List.of()));
        var port=mock(ManufacturerCharacteristicRevisionUseCase.class);
        var controller=new ManufacturerCharacteristicRevisionController(port);
        assertThrows(NullPointerException.class,()->controller.qualify("m-source","v1",
            new ManufacturerCharacteristicRevisionRequests.Qualify("company-a",null)));
        verifyNoInteractions(port);
    }
}
