package dz.sh.hidra.modules.topology;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringGraphDraftRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringGraphStationWitnessPort;
import dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationService;
import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringNetworkDraftController;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcEngineeringGraphDraftRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcEngineeringGraphStationWitnessAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

/** Mandatory architecture regression; must not be weakened merely to satisfy CI. */
class EngineeringGraphDraftBoundaryGuardrailTest {
    @Test void theOneExistingEngineeringDraftAggregateMustImplementBothUseCases() {
        assertTrue(EngineeringGraphDraftUseCase.class.isAssignableFrom(EngineeringNetworkDraftApplicationService.class),
            "Graph authoring must reuse the compiled engineering draft aggregate owner");
    }

    @Test void adaptersImplementPortsInsteadOfOwningDomainAuthority() {
        assertTrue(EngineeringGraphDraftRepositoryPort.class.isAssignableFrom(JdbcEngineeringGraphDraftRepositoryAdapter.class));
        assertTrue(EngineeringGraphStationWitnessPort.class.isAssignableFrom(JdbcEngineeringGraphStationWitnessAdapter.class));
        assertTrue(EngineeringGraphDraft.class.isRecord());
        for (var field : EngineeringGraphDraft.class.getDeclaredFields())
            for (var annotation : field.getAnnotations())
                assertFalse(annotation.annotationType().getName().startsWith("jakarta.persistence"),
                    "Pure domain graph cannot become a JPA entity");
    }

    @Test void graphRestResponsesMustUseTransportOwnedRecords() {
        var type=dz.sh.hidra.modules.topology.api.rest.response.EngineeringGraphDraftResponses.Graph.class;
        for (var field:type.getRecordComponents()) {
            assertFalse(field.getType().equals(EngineeringGraphDraft.class),
                "REST graph must not serialize the domain aggregate itself");
        }
        assertTrue(dz.sh.hidra.modules.topology.api.rest.response.EngineeringGraphDraftResponses.Node.class.isRecord());
        assertTrue(dz.sh.hidra.modules.topology.api.rest.response.EngineeringGraphDraftResponses.Port.class.isRecord());
        assertTrue(dz.sh.hidra.modules.topology.api.rest.response.EngineeringGraphDraftResponses.Branch.class.isRecord());
        assertEquals(String.class,
            dz.sh.hidra.modules.topology.api.rest.response.EngineeringGraphDraftResponses.Node.class
                .getRecordComponents()[1].getType());
    }

    @Test void reviewedGraphRepositoryBeansMustRemainProxyableAndTransportDtosRemainDomainFree() throws Exception {
        assertFalse(java.lang.reflect.Modifier.isFinal(JdbcEngineeringGraphDraftRepositoryAdapter.class.getModifiers()));
        assertFalse(java.lang.reflect.Modifier.isFinal(JdbcEngineeringGraphStationWitnessAdapter.class.getModifiers()));
        var source = java.nio.file.Path.of("src/main/java/dz/sh/hidra/modules/topology/api/rest");
        for (String path : java.util.List.of("request/EngineeringGraphDraftRequests.java",
                                            "response/EngineeringGraphDraftResponses.java")) {
            String content = java.nio.file.Files.readString(source.resolve(path));
            assertFalse(content.contains("import dz.sh.hidra.modules.topology.domain."),
                    "REST transport records must not import domain representations");
        }
    }

    @Test void exactOwnedControllerRouteTuplesMustRemainNarrowlyBound() throws Exception {
        RequestMapping root=EngineeringNetworkDraftController.class.getAnnotation(RequestMapping.class);
        assertArrayEquals(new String[]{"/api/v1/topology/engineering-networks"},root.value());
        Method read=EngineeringNetworkDraftController.class.getMethod("readGraph",
            String.class,String.class,String.class,Long.class);
        Method put=EngineeringNetworkDraftController.class.getMethod("updateGraph",
            String.class,String.class,String.class,
            dz.sh.hidra.modules.topology.api.rest.request.EngineeringGraphDraftRequests.Update.class);
        assertArrayEquals(new String[]{"/{draftId}/graph"},read.getAnnotation(GetMapping.class).value());
        assertArrayEquals(new String[]{"/{draftId}/graph"},put.getAnnotation(PutMapping.class).value());
    }
}
