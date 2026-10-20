/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SurveyRouteProfileBoundaryGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology
 *
 * @Description : Keeps survey transport, private persistence and draft ownership bounded.
 *
 */
package dz.sh.hidra.modules.topology;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationService;
import dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcSurveyRouteProfileRepositoryAdapter;
import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringNetworkDraftController;
import java.nio.file.*;
import java.util.*;
class SurveyRouteProfileBoundaryGuardrailTest {
    @Test void surveyUsesExistingDraftOwnerAndTransportOwnedDtos() throws Exception {
        assertTrue(SurveyRouteProfileUseCase.class.isAssignableFrom(EngineeringNetworkDraftApplicationService.class));
        for(String kind:List.of("request/SurveyRouteProfileRequests.java","response/SurveyRouteProfileResponses.java")){
            var source=Files.readString(Path.of("src/main/java/dz/sh/hidra/modules/topology/api/rest/"+kind));assertFalse(source.contains("domain.model"));assertFalse(source.contains("infrastructure.persistence"));
        }
    }
    @Test void surveyRepositoryIsProxyableAndDomainStaysPure() throws Exception {
        assertFalse(java.lang.reflect.Modifier.isFinal(JdbcSurveyRouteProfileRepositoryAdapter.class.getModifiers()));
        var source=Files.readString(Path.of("src/main/java/dz/sh/hidra/modules/topology/domain/model/SurveyRouteProfile.java"));
        for(String dependency:List.of("org.springframework","jakarta.persistence","tools.jackson","dz.sh.hidra.platform"))assertFalse(source.contains(dependency));
    }
    @Test void surveyHasOnlyTheTwoRegisteredOperations(){
        var methods=Arrays.stream(EngineeringNetworkDraftController.class.getDeclaredMethods()).filter(m->m.getName().equals("readSurvey")||m.getName().equals("updateSurvey")).toList();assertEquals(2,methods.size());
        for(var m:methods){String[] path=m.getName().equals("readSurvey")?m.getAnnotation(org.springframework.web.bind.annotation.GetMapping.class).value():m.getAnnotation(org.springframework.web.bind.annotation.PutMapping.class).value();assertArrayEquals(new String[]{"/{draftId}/survey/{branchId}"},path);}
    }
}
