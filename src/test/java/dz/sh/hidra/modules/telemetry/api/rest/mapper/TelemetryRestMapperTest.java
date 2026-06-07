/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryRestMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.mapper
 *
 * @Description : REST mapper tests for telemetry request, query, and response mapping.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPageResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryReadingResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetrySourceResponse;
import dz.sh.hidra.modules.telemetry.api.rest.support.TelemetryRestTestFixtures;
import dz.sh.hidra.modules.telemetry.application.command.BindTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.ReceiveTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.command.RegisterTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryReadingsQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetrySourcesQuery;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;

/**
 * REST mapper tests for telemetry request, query, and response mapping.
 */
class TelemetryRestMapperTest {

    private final TelemetryRestMapper mapper = new TelemetryRestMapper();

    @Test
    void shouldMapRestRequestsToApplicationCommands() {
        RegisterTelemetrySourceCommand sourceCommand = mapper.toCommand(TelemetryRestTestFixtures.registerSourceRequest());
        RegisterTelemetryPointCommand pointCommand = mapper.toCommand(TelemetryRestTestFixtures.registerPointRequest());
        BindTelemetryPointCommand bindingCommand = mapper.toCommand(TelemetryRestTestFixtures.bindPointRequest());
        ReceiveTelemetryReadingCommand readingCommand = mapper.toCommand(TelemetryRestTestFixtures.receiveReadingRequest());

        assertEquals("SCADA-TRC-01", sourceCommand.code().value());
        assertEquals("Nom français", sourceCommand.name().nameFr());
        assertEquals("source-type-scada", sourceCommand.sourceType().id());
        assertEquals("PT-001", pointCommand.code().value());
        assertEquals("unit-bar", pointCommand.unit().id());
        assertEquals("PIPELINE", bindingCommand.topologyAssetReference().assetTypeCode().value());
        assertEquals(BigDecimal.valueOf(42.25), readingCommand.value().numericValue());
        assertEquals("quality-good", readingCommand.qualityCode().id());
    }

    @Test
    void shouldMapPathAndQueryParametersToApplicationQueries() {
        ListTelemetrySourcesQuery sourceQuery = mapper.toListTelemetrySourcesQuery(
                "SCADA",
                "source-type-scada",
                "SCADA",
                "protocol-opc-ua",
                "OPC_UA",
                "ACTIVE",
                1,
                25,
                "code",
                "DESC");

        ListTelemetryReadingsQuery readingQuery = mapper.toListTelemetryReadingsQuery(
                "point-001",
                "quality-good",
                "GOOD",
                "ACCEPTED",
                TelemetryRestTestFixtures.SOURCE_TIMESTAMP,
                TelemetryRestTestFixtures.RECEIVED_AT,
                "batch-001",
                0,
                20,
                "sourceTimestamp",
                "ASC");

        assertEquals("SCADA", sourceQuery.searchText());
        assertEquals("source-type-scada", sourceQuery.sourceType().id());
        assertEquals(TelemetrySourceStatus.ACTIVE, sourceQuery.status());
        assertEquals(1, sourceQuery.pageRequest().page());
        assertEquals(25, sourceQuery.pageRequest().size());
        assertEquals("point-001", readingQuery.pointId().value());
        assertEquals(TelemetryReadingState.ACCEPTED, readingQuery.state());
        assertEquals("batch-001", readingQuery.ingestionBatchId().value());
    }

    @Test
    void shouldMapApplicationDtosToRestResponses() {
        TelemetrySourceResponse sourceResponse = mapper.toResponse(TelemetryRestTestFixtures.sourceDto());
        TelemetryReadingResponse readingResponse = mapper.toResponse(TelemetryRestTestFixtures.readingDto());

        assertEquals("source-001", sourceResponse.id());
        assertEquals("Nom français", sourceResponse.name().nameFr());
        assertEquals("SCADA", sourceResponse.sourceType().code());
        assertEquals("reading-001", readingResponse.id());
        assertEquals(BigDecimal.valueOf(42.25), readingResponse.value().numericValue());
        assertNull(readingResponse.rejectionReason());
    }

    @Test
    void shouldMapApplicationPagesToRestPageResponses() {
        TelemetryPageResponse<TelemetrySourceResponse> response = mapper.toSourcePageResponse(
                TelemetryRestTestFixtures.page(TelemetryRestTestFixtures.sourceDto()));

        assertEquals(1, response.items().size());
        assertEquals("SCADA-TRC-01", response.items().get(0).code());
        assertEquals(Integer.valueOf(0), response.page());
        assertEquals(Integer.valueOf(20), response.size());
        assertEquals(1L, response.totalElements());
    }
}
