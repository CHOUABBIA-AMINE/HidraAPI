/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Authenticated owner-dispatched only routes and protected existence handling.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase;
import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.*;
import dz.sh.hidra.modules.topology.application.model.InstalledCharacteristicLinkFailure;
import dz.sh.hidra.modules.topology.api.rest.request.InstalledCharacteristicLinkRequests.CharacteristicLinkDraftSave;
import dz.sh.hidra.modules.topology.api.rest.request.InstalledCharacteristicLinkRequests.CharacteristicLinkApprovalCommand;
import dz.sh.hidra.modules.topology.api.rest.request.InstalledCharacteristicLinkRequests.CharacteristicLinkWorkflowReference;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class InstalledCharacteristicLinkControllerTest {
    private final InstalledCharacteristicLinkUseCase owner=mock(InstalledCharacteristicLinkUseCase.class);
    private final InstalledCharacteristicLinkController controller=new InstalledCharacteristicLinkController(owner);
    private static final Instant AT=Instant.parse("2026-01-01T00:00:00Z");
    private static final String HASH="a".repeat(64);
    @AfterEach void cleanup(){SecurityContextHolder.clearContext();}
    private static void auth(){SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken("operator",null,List.of()));}
    private static CharacteristicLinkDraftSave save(){return new CharacteristicLinkDraftSave("company","req",0L,1L,"src","rev",HASH,
        "VALVE_MAP",AT,null,AT,"synthetic:evidence",null);}
    private static InstalledCharacteristicLink value(){
        return new InstalledCharacteristicLink("link",1L,"company","unit","src","rev",HASH,
            InstalledCharacteristicLink.Kind.VALVE_MAP,AT,null,AT,"synthetic:evidence",null);
    }
    @Test void anonymousCannotInvokeAnyProtectedReadOrWrite(){
        assertThrows(InstalledCharacteristicLinkFailure.class,()->controller.current("unit","link","company"));
        assertThrows(InstalledCharacteristicLinkFailure.class,()->controller.save("unit","link",save()));
        assertThrows(InstalledCharacteristicLinkFailure.class,()->controller.approve("unit","link",1,approval()));
        assertThrows(InstalledCharacteristicLinkFailure.class,()->controller.approvedRevision("unit","link",1,"company"));
        assertThrows(InstalledCharacteristicLinkFailure.class,()->controller.approvalHistory("unit","link","company",5,null));
        verifyNoInteractions(owner);
    }
    private static CharacteristicLinkApprovalCommand approval(){
        return new CharacteristicLinkApprovalCommand("company","req-approval",1L,
            new CharacteristicLinkWorkflowReference("def",1,"type","purpose","instance","task","action"));
    }
    @Test void authenticatedSaveRequiresOriginalOwnerAndReturnsDraftOnly(){
        auth();when(owner.execute(any())).thenReturn(new Result(value(),List.of(),false,null));
        var reply=controller.save("unit","link",save());
        assertEquals("DRAFT",reply.link().state());
        verify(owner).execute(argThat(c->c.operation()==Operation.SAVE_DRAFT
                &&c.companyPartyId().equals("company")&&c.expectedEpoch()==0L));
    }
    @Test void authorizedCurrentAndHistoricalReadsRequireOwnerReturnedRows(){
        auth();when(owner.execute(any())).thenReturn(new Result(null,List.of(),false,null));
        assertEquals(InstalledCharacteristicLinkFailure.Kind.NOT_FOUND,
            assertThrows(InstalledCharacteristicLinkFailure.class,
                ()->controller.current("unit","link","company")).kind());
        assertEquals(InstalledCharacteristicLinkFailure.Kind.NOT_FOUND,
            assertThrows(InstalledCharacteristicLinkFailure.class,
                ()->controller.revision("unit","link",1L,"company")).kind());
        verify(owner,times(2)).execute(any());
    }
    @Test void historyWithoutRowsDisclosesNothingAndUsesExclusiveCursor(){
        auth();when(owner.execute(any())).thenReturn(new Result(null,List.of(),false,null));
        var reply=controller.history("unit","link","company",5,3L);
        assertNull(reply.link());assertTrue(reply.history().isEmpty());
        verify(owner).execute(argThat(c->c.operation()==Operation.LIST_HISTORY
            &&c.limit()==5&&c.beforeRevision()==3L));
    }
    @Test void exactWorkflowDescriptorIsNeverAnApprovalClaim(){
        auth();
        when(owner.execute(any())).thenReturn(new Result(null,List.of(),false,null));
        assertEquals(InstalledCharacteristicLinkFailure.Kind.NOT_FOUND,
            assertThrows(InstalledCharacteristicLinkFailure.class,
                ()->controller.approve("unit","link",1,approval())).kind());
        verify(owner).execute(argThat(c->c.operation()==Operation.APPROVE
            && c.expectedEpoch()==1L && c.revision()==1L
            && c.approval().actionId().equals("action")));
    }
    @Test void protectedReadUsesIndependentApprovalOperations(){
        auth();
        when(owner.execute(any())).thenReturn(new Result(null,List.of(),false,null));
        assertEquals(InstalledCharacteristicLinkFailure.Kind.NOT_FOUND,
            assertThrows(InstalledCharacteristicLinkFailure.class,
                ()->controller.approvedRevision("unit","link",1,"company")).kind());
        var page=controller.approvalHistory("unit","link","company",5,3L);
        assertTrue(page.history().isEmpty());
        verify(owner).execute(argThat(c->c.operation()==Operation.LIST_APPROVAL_HISTORY
            && c.limit()==5 && c.beforeRevision()==3L));
    }
    @Test void malformedWorkflowDescriptorRejectsBeforeOwner(){
        auth();
        var invalid=new CharacteristicLinkApprovalCommand("company","req",1L,
            new CharacteristicLinkWorkflowReference("def",0,"type","purpose","instance","task","action"));
        assertThrows(IllegalArgumentException.class,()->controller.approve("unit","link",1,invalid));
        verifyNoInteractions(owner);
    }
}
