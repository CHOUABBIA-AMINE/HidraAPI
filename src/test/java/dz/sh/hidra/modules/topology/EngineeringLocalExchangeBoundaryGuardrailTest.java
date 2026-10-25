/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeBoundaryGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology;

import dz.sh.hidra.modules.topology.application.service.EngineeringLocalExchangeApplicationService;
import dz.sh.hidra.modules.topology.application.port.in.*;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringLocalExchangeCodecPort;
import dz.sh.hidra.modules.topology.api.rest.request.EngineeringLocalExchangeRequests;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringLocalExchangeResponses;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.*;
import java.util.*;
import java.nio.file.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EngineeringLocalExchangeBoundaryGuardrailTest {
    @Test void applicationCoordinatesOnlyExistingInboundOwnersAndCodecPort(){
        assertEquals(Set.of(EngineeringNetworkDraftUseCase.class,EngineeringGraphDraftUseCase.class,SurveyRouteProfileUseCase.class,EngineeringLocalExchangeCodecPort.class),
            new HashSet<>(Arrays.stream(EngineeringLocalExchangeApplicationService.class.getDeclaredFields()).map(java.lang.reflect.Field::getType).toList()));
    }
    @Test void exchangeHasNoSqlJpaTransactionPermitOrIdentityTicketAccess() throws Exception {
        String text=Files.readString(Path.of("src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringLocalExchangeApplicationService.java"));
        for(String forbidden:List.of("JdbcTemplate","EntityManager","DataSource","ProtectedTransactionExecutor","@Transactional","modules.identity","Admission","Permit"))assertFalse(text.contains(forbidden),forbidden);
        for(var method:EngineeringLocalExchangeCodecPort.class.getMethods())assertTrue(Set.of("encode","decode").contains(method.getName()));
    }
    @Test void restModelsDoNotExposeDomainOrAuthorityObjects(){
        for(var type:List.of(EngineeringLocalExchangeRequests.Envelope.class,EngineeringLocalExchangeRequests.Submission.class,EngineeringLocalExchangeResponses.Envelope.class,EngineeringLocalExchangeResponses.Result.class))
            for(var c:type.getRecordComponents())assertFalse(c.getType().getName().contains(".domain.") || c.getType().getName().contains(".persistence.") || c.getName().matches(".*(actor|credential|ticket|grant|token).*"));
    }
    @Test void existingFormatsSqlGuardsAndBudgetsRemainUnchanged() throws Exception {
        assertEquals("HIDRA_ENGINEERING_METADATA_V1",EngineeringNetworkDraftCodec.FORMAT);
        assertEquals("HIDRA_ENGINEERING_GRAPH_DRAFT_V1",EngineeringGraphDraftCodec.FORMAT);
        assertEquals("HIDRA_ENGINEERING_SURVEY_DRAFT_V1",SurveyRouteProfileCodec.FORMAT);
        String source=Files.readString(Path.of("src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringLocalExchangeApplicationService.java"));
        assertFalse(source.contains("setWorkBudget"));assertFalse(source.contains("finalGuard("));assertFalse(source.contains("beginProvisional("));
        assertFalse(source.contains("INSERT "));assertFalse(source.contains("SELECT "));
    }
}
