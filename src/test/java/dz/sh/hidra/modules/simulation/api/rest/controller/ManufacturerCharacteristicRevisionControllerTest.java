package dz.sh.hidra.modules.simulation.api.rest.controller;

import dz.sh.hidra.modules.simulation.api.rest.ManufacturerCharacteristicRevisionApiExceptionHandler;
import dz.sh.hidra.modules.simulation.api.rest.mapper.ManufacturerCharacteristicRevisionRestMapper;
import dz.sh.hidra.modules.simulation.api.rest.request.ManufacturerCharacteristicRevisionRequests;
import dz.sh.hidra.modules.simulation.application.port.in.ManufacturerCharacteristicRevisionUseCase;
import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision;
import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevisionTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ManufacturerCharacteristicRevisionControllerTest {
    private static final Instant AT=ManufacturerCharacteristicRevisionTest.AT;
    private static final String SHA="a".repeat(64);
    private static final String ROOT="/api/v1/simulation/manufacturer-characteristics/m-source";
    private static ManufacturerCharacteristicRevision source() {
        return ManufacturerCharacteristicRevisionTest.fixture("v1",
                ManufacturerCharacteristicRevision.Origin.SYNTHETIC,
                ManufacturerCharacteristicRevisionTest.limits());
    }
    private static void authenticated() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("synthetic",null,List.of()));
    }
    @AfterEach void clear() { SecurityContextHolder.clearContext(); }

    @Test void unauthenticatedHandlerDoesNotReadSource() {
        var port=mock(ManufacturerCharacteristicRevisionUseCase.class);
        var controller=new ManufacturerCharacteristicRevisionController(port);
        assertThrows(AuthenticationCredentialsNotFoundException.class,()->
                controller.revision("m-source","v1","company-a"));
        verifyNoInteractions(port);
    }

    @Test void declaredReadOperationReturnsOnlyCommittedCertificateFreeResult() throws Exception {
        authenticated();
        var port=mock(ManufacturerCharacteristicRevisionUseCase.class);
        when(port.execute(any())).thenAnswer(call->{
            ManufacturerCharacteristicRevisionUseCase.Command command=call.getArgument(0);
            assertEquals(ManufacturerCharacteristicRevisionUseCase.Operation.READ_REVISION,command.operation());
            assertEquals("company-a",command.companyPartyId());
            assertEquals("m-source",command.sourceId());
            return new ManufacturerCharacteristicRevisionUseCase.Result(command.operation(),
                    "company-a","m-source","v1",SHA,source(),List.of(),AT,false);
        });
        var mvc=MockMvcBuilders.standaloneSetup(new ManufacturerCharacteristicRevisionController(port))
                .setControllerAdvice(new ManufacturerCharacteristicRevisionApiExceptionHandler()).build();
        var result=mvc.perform(MockMvcRequestBuilders.get(ROOT+"/revisions/v1")
                .param("companyPartyId","company-a")).andReturn();
        assertEquals(200,result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("m-source"));
        assertFalse(result.getResponse().getContentAsString().contains("issuerReference"));
    }

    @Test void historyUsesOnlyProtectedPagedUseCaseAndNeverReturnsTotalCount() throws Exception {
        authenticated();
        var port=mock(ManufacturerCharacteristicRevisionUseCase.class);
        when(port.pageHistory(any())).thenReturn(new ManufacturerCharacteristicRevisionUseCase.HistoryPageResult(
            List.of(new ManufacturerCharacteristicRevisionUseCase.RevisionSummary(
                "m-source","v1",SHA,AT,AT,null,false)),null,AT));
        var mvc=MockMvcBuilders.standaloneSetup(new ManufacturerCharacteristicRevisionController(port))
                .setControllerAdvice(new ManufacturerCharacteristicRevisionApiExceptionHandler()).build();
        var response=mvc.perform(MockMvcRequestBuilders.get(ROOT+"/history")
                .param("companyPartyId","company-a").param("size","10")).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertFalse(response.getContentAsString().contains("totalCount"));
        var args=org.mockito.ArgumentCaptor.forClass(ManufacturerCharacteristicRevisionUseCase.HistoryPageQuery.class);
        verify(port).pageHistory(args.capture());
        assertEquals("company-a",args.getValue().companyPartyId());
        assertEquals(Integer.valueOf(10),args.getValue().requestedSize());
        verify(port,never()).execute(any());
    }

    @Test void saveRejectsMismatchedPathBeforeOwnerInvocation() {
        authenticated();
        var port=mock(ManufacturerCharacteristicRevisionUseCase.class);
        var controller=new ManufacturerCharacteristicRevisionController(port);
        var original=ManufacturerCharacteristicRevisionRestMapper.toDto(source());
        assertThrows(IllegalArgumentException.class,()->controller.save("m-source","wrong",
                new ManufacturerCharacteristicRevisionRequests.Save("company-a",original)));
        verifyNoInteractions(port);
    }

    @Test void allFiveMethodsRequireIndependentPlatformMarker() throws Exception {
        var controller=ManufacturerCharacteristicRevisionController.class;
        for(String name:List.of("save","revision","history","certificate","qualify")){
            var methods=java.util.Arrays.stream(controller.getDeclaredMethods())
                    .filter(method->method.getName().equals(name)).toList();
            assertEquals(1,methods.size());
            assertTrue(methods.getFirst().isAnnotationPresent(
                    dz.sh.hidra.platform.permissions.NativeProtectedOperation.class));
        }
    }
}
