/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryRestControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : Unit tests for telemetry REST controllers.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import dz.sh.hidra.modules.telemetry.api.rest.mapper.TelemetryRestMapper;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryCatalogResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryDeviceResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryIngestionBatchResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPageResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPointBindingResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPointResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryReadingResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetrySourceResponse;
import dz.sh.hidra.modules.telemetry.api.rest.support.TelemetryRestTestFixtures;
import dz.sh.hidra.modules.telemetry.application.port.in.AcceptTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.BindTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.CloseTelemetryPointBindingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.CompleteTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.DeactivateTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.DeactivateTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.FailTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetLatestTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryCatalogTypeUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryPointBindingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryCatalogTypesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryDevicesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryIngestionBatchesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryPointBindingsUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryPointsUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryReadingsUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetrySourcesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.MarkTelemetryIngestionBatchProcessingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.MarkTelemetryReadingDuplicateUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.QuarantineTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ReceiveTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RejectTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ResolveTelemetryCatalogTypeUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.StartTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.SuspendTelemetryPointUseCase;

/**
 * Unit tests for telemetry REST controllers.
 */
class TelemetryRestControllerTest {

    private final TelemetryRestMapper mapper = new TelemetryRestMapper();

    @Test
    void shouldRouteTelemetryCatalogControllerEndpoints() {
        TelemetryCatalogController controller = new TelemetryCatalogController(
                useCase(GetTelemetryCatalogTypeUseCase.class),
                useCase(ListTelemetryCatalogTypesUseCase.class),
                useCase(ResolveTelemetryCatalogTypeUseCase.class),
                mapper);

        ResponseEntity<TelemetryCatalogResponse> getResponse = controller.getCatalogType("catalog-point-type-pressure", "fr");
        ResponseEntity<TelemetryPageResponse<TelemetryCatalogResponse>> listResponse = controller.listCatalogTypes(
                "POINT_TYPE",
                "fr",
                true,
                0,
                20,
                "code",
                "ASC");
        ResponseEntity<TelemetryCatalogResponse> resolveResponse = controller.resolveCatalogType("POINT_TYPE", "PRESSURE", true, "fr");

        assertEquals("Pression", getResponse.getBody().resolvedName());
        assertEquals(1, listResponse.getBody().items().size());
        assertEquals("PRESSURE", resolveResponse.getBody().code());
    }

    @Test
    void shouldRouteTelemetrySourceControllerEndpoints() {
        TelemetrySourceController controller = new TelemetrySourceController(
                useCase(RegisterTelemetrySourceUseCase.class),
                useCase(ActivateTelemetrySourceUseCase.class),
                useCase(DeactivateTelemetrySourceUseCase.class),
                useCase(RetireTelemetrySourceUseCase.class),
                useCase(GetTelemetrySourceUseCase.class),
                useCase(ListTelemetrySourcesUseCase.class),
                mapper);

        assertEquals("SCADA-TRC-01", controller.registerSource(TelemetryRestTestFixtures.registerSourceRequest()).getBody().code());
        assertEquals("ACTIVE", controller.activateSource("source-001").getBody().status());
        assertEquals("ACTIVE", controller.deactivateSource("source-001").getBody().status());
        assertEquals("source-001", controller.getSource("source-001").getBody().id());
        assertEquals(1, controller.listSources("SCADA", null, null, null, null, "ACTIVE", 0, 20, "code", "ASC").getBody().items().size());
    }

    @Test
    void shouldRouteTelemetryDeviceAndPointControllerEndpoints() {
        TelemetryDeviceController deviceController = new TelemetryDeviceController(
                useCase(RegisterTelemetryDeviceUseCase.class),
                useCase(ActivateTelemetryDeviceUseCase.class),
                useCase(DeactivateTelemetryDeviceUseCase.class),
                useCase(RetireTelemetryDeviceUseCase.class),
                useCase(GetTelemetryDeviceUseCase.class),
                useCase(ListTelemetryDevicesUseCase.class),
                mapper);
        TelemetryPointController pointController = new TelemetryPointController(
                useCase(RegisterTelemetryPointUseCase.class),
                useCase(ActivateTelemetryPointUseCase.class),
                useCase(SuspendTelemetryPointUseCase.class),
                useCase(RetireTelemetryPointUseCase.class),
                useCase(GetTelemetryPointUseCase.class),
                useCase(ListTelemetryPointsUseCase.class),
                mapper);

        ResponseEntity<TelemetryDeviceResponse> deviceResponse = deviceController.registerDevice(TelemetryRestTestFixtures.registerDeviceRequest());
        ResponseEntity<TelemetryPointResponse> pointResponse = pointController.registerPoint(TelemetryRestTestFixtures.registerPointRequest());

        assertEquals("RTU-001", deviceResponse.getBody().code());
        assertEquals("ACTIVE", deviceController.activateDevice("source-001", "device-001").getBody().status());
        assertEquals(1, deviceController.listDevices("RTU", "source-001", null, null, "ACTIVE", 0, 20, "code", "ASC").getBody().items().size());
        assertEquals("PT-001", pointResponse.getBody().code());
        assertEquals("ACTIVE", pointController.activatePoint("device-001", "point-001").getBody().status());
        assertEquals("ACTIVE", pointController.suspendPoint("point-001").getBody().status());
        assertEquals(1, pointController.listPoints("PT", "device-001", null, null, null, null, "ACTIVE", 0, 20, "code", "ASC").getBody().items().size());
    }

    @Test
    void shouldRouteTelemetryPointBindingControllerEndpoints() {
        TelemetryPointBindingController controller = new TelemetryPointBindingController(
                useCase(BindTelemetryPointUseCase.class),
                useCase(CloseTelemetryPointBindingUseCase.class),
                useCase(GetTelemetryPointBindingUseCase.class),
                useCase(ListTelemetryPointBindingsUseCase.class),
                mapper);

        ResponseEntity<TelemetryPointBindingResponse> bindResponse = controller.bindPoint(TelemetryRestTestFixtures.bindPointRequest());

        assertEquals("binding-001", bindResponse.getBody().id());
        assertEquals("PIPELINE", bindResponse.getBody().topologyAssetReference().assetTypeCode());
        assertEquals("binding-001", controller.closeBinding("binding-001", TelemetryRestTestFixtures.closeBindingRequest()).getBody().id());
        assertEquals("binding-001", controller.getBinding("binding-001").getBody().id());
        assertEquals(1, controller.listBindings("point-001", "PIPELINE", "pipeline-001", null, null, true, 0, 20, "validFrom", "DESC").getBody().items().size());
    }

    @Test
    void shouldRouteTelemetryReadingControllerEndpoints() {
        TelemetryReadingController controller = new TelemetryReadingController(
                useCase(ReceiveTelemetryReadingUseCase.class),
                useCase(AcceptTelemetryReadingUseCase.class),
                useCase(RejectTelemetryReadingUseCase.class),
                useCase(QuarantineTelemetryReadingUseCase.class),
                useCase(MarkTelemetryReadingDuplicateUseCase.class),
                useCase(GetTelemetryReadingUseCase.class),
                useCase(GetLatestTelemetryReadingUseCase.class),
                useCase(ListTelemetryReadingsUseCase.class),
                mapper);

        ResponseEntity<TelemetryReadingResponse> receiveResponse = controller.receiveReading(TelemetryRestTestFixtures.receiveReadingRequest());

        assertEquals("reading-001", receiveResponse.getBody().id());
        assertEquals("ACCEPTED", controller.acceptReading("reading-001").getBody().state());
        assertEquals("ACCEPTED", controller.rejectReading("reading-001", TelemetryRestTestFixtures.rejectReadingRequest()).getBody().state());
        assertEquals("ACCEPTED", controller.quarantineReading("reading-001", TelemetryRestTestFixtures.quarantineReadingRequest()).getBody().state());
        assertEquals("ACCEPTED", controller.markDuplicate("reading-001").getBody().state());
        assertEquals("reading-001", controller.getLatestReading("point-001").getBody().id());
        assertEquals(1, controller.listReadings("point-001", "quality-good", "GOOD", "ACCEPTED", null, null, "batch-001", 0, 20, "sourceTimestamp", "DESC").getBody().items().size());
    }

    @Test
    void shouldRouteTelemetryIngestionBatchControllerEndpoints() {
        TelemetryIngestionBatchController controller = new TelemetryIngestionBatchController(
                useCase(StartTelemetryIngestionBatchUseCase.class),
                useCase(MarkTelemetryIngestionBatchProcessingUseCase.class),
                useCase(CompleteTelemetryIngestionBatchUseCase.class),
                useCase(FailTelemetryIngestionBatchUseCase.class),
                useCase(GetTelemetryIngestionBatchUseCase.class),
                useCase(ListTelemetryIngestionBatchesUseCase.class),
                mapper);

        ResponseEntity<TelemetryIngestionBatchResponse> startResponse = controller.startBatch(TelemetryRestTestFixtures.startBatchRequest());

        assertEquals("batch-001", startResponse.getBody().id());
        assertEquals("COMPLETED", controller.markProcessing("batch-001").getBody().status());
        assertEquals("COMPLETED", controller.completeBatch("batch-001", TelemetryRestTestFixtures.completeBatchRequest()).getBody().status());
        assertEquals("COMPLETED", controller.failBatch("batch-001", TelemetryRestTestFixtures.failBatchRequest()).getBody().status());
        assertEquals("batch-001", controller.getBatch("batch-001").getBody().id());
        assertEquals(1, controller.listBatches("source-001", "COMPLETED", null, null, 0, 20, "startedAt", "DESC").getBody().items().size());
    }

    private static <T> T useCase(Class<T> type) {
        return TelemetryRestTestFixtures.useCase(type);
    }
}
