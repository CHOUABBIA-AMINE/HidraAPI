/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRestControllerEndpointBoundaryTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Reflection tests for topology REST controller endpoint boundaries.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Reflection tests for topology REST controller endpoint boundaries.
 *
 * <p>Business role:
 * Verifies topology exposes only the endpoints backed by current application inbound ports.
 *
 * <p>Architecture role:
 * Prevents REST controllers from inventing unsupported endpoints before use cases exist.
 *
 * <p>Validation:
 * Pipeline segments and topology connections expose create/list only. Equipment exposes register
 * only. Asset groups with get use cases expose create/get/list.
 */
class TopologyRestControllerEndpointBoundaryTest {

    @Test
    void shouldUseExpectedControllerBasePaths() {
        assertBasePath(PipelineSystemController.class, "/api/v1/topology/pipeline-systems");
        assertBasePath(PipelineController.class, "/api/v1/topology/pipelines");
        assertBasePath(FacilityController.class, "/api/v1/topology/facilities");
        assertBasePath(TopologyNodeController.class, "/api/v1/topology/nodes");
        assertBasePath(PipelineSegmentController.class, "/api/v1/topology/pipeline-segments");
        assertBasePath(PipelineAppurtenanceController.class, "/api/v1/topology/pipeline-appurtenances");
        assertBasePath(TopologyConnectionController.class, "/api/v1/topology/connections");
        assertBasePath(EquipmentController.class, "/api/v1/topology/equipment");
    }

    @Test
    void shouldExposeCreateGetAndListForAssetsWithGetUseCases() {
        assertEquals(1, countPostEndpoints(PipelineSystemController.class));
        assertEquals(2, countGetEndpoints(PipelineSystemController.class));
        assertTrue(hasPathVariableEndpoint(PipelineSystemController.class));

        assertEquals(1, countPostEndpoints(PipelineController.class));
        assertEquals(2, countGetEndpoints(PipelineController.class));
        assertTrue(hasPathVariableEndpoint(PipelineController.class));

        assertEquals(1, countPostEndpoints(FacilityController.class));
        assertEquals(2, countGetEndpoints(FacilityController.class));
        assertTrue(hasPathVariableEndpoint(FacilityController.class));

        assertEquals(1, countPostEndpoints(TopologyNodeController.class));
        assertEquals(2, countGetEndpoints(TopologyNodeController.class));
        assertTrue(hasPathVariableEndpoint(TopologyNodeController.class));

        assertEquals(1, countPostEndpoints(PipelineAppurtenanceController.class));
        assertEquals(2, countGetEndpoints(PipelineAppurtenanceController.class));
        assertTrue(hasPathVariableEndpoint(PipelineAppurtenanceController.class));
    }

    @Test
    void shouldNotInventUnsupportedGetEndpointsForSegmentsConnectionsOrEquipment() {
        assertEquals(1, countPostEndpoints(PipelineSegmentController.class));
        assertEquals(1, countGetEndpoints(PipelineSegmentController.class));
        assertFalse(hasPathVariableEndpoint(PipelineSegmentController.class));

        assertEquals(1, countPostEndpoints(TopologyConnectionController.class));
        assertEquals(1, countGetEndpoints(TopologyConnectionController.class));
        assertFalse(hasPathVariableEndpoint(TopologyConnectionController.class));

        assertEquals(1, countPostEndpoints(EquipmentController.class));
        assertEquals(0, countGetEndpoints(EquipmentController.class));
        assertFalse(hasPathVariableEndpoint(EquipmentController.class));
    }

    private static void assertBasePath(Class<?> controllerType, String expectedBasePath) {
        RequestMapping mapping = controllerType.getAnnotation(RequestMapping.class);
        assertEquals(expectedBasePath, mapping.value()[0]);
    }

    private static int countPostEndpoints(Class<?> controllerType) {
        int count = 0;
        for (Method method : controllerType.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostMapping.class)) {
                count++;
            }
        }
        return count;
    }

    private static int countGetEndpoints(Class<?> controllerType) {
        int count = 0;
        for (Method method : controllerType.getDeclaredMethods()) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                count++;
            }
        }
        return count;
    }

    private static boolean hasPathVariableEndpoint(Class<?> controllerType) {
        for (Method method : controllerType.getDeclaredMethods()) {
            for (java.lang.annotation.Annotation[] annotations : method.getParameterAnnotations()) {
                for (java.lang.annotation.Annotation annotation : annotations) {
                    if (annotation.annotationType().equals(PathVariable.class)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
