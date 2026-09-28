/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.mapper
 *
 * @Description : Maps custody REST models to application models.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.mapper;
import dz.sh.hidra.modules.custody.api.rest.request.CreateCustodyTransferTicketRequest;
import dz.sh.hidra.modules.custody.api.rest.request.OpenCustodyDiscrepancyRequest;
import dz.sh.hidra.modules.custody.api.rest.request.OpenCustodyMeasurementPeriodRequest;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyDiscrepancyResponse;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyMeasurementPeriodResponse;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyTransferTicketResponse;
import dz.sh.hidra.modules.custody.application.command.CreateCustodyTransferTicketCommand;
import dz.sh.hidra.modules.custody.application.command.OpenCustodyDiscrepancyCommand;
import dz.sh.hidra.modules.custody.application.command.OpenCustodyMeasurementPeriodCommand;
import dz.sh.hidra.modules.custody.application.dto.CustodyDiscrepancySummaryDto;
import dz.sh.hidra.modules.custody.application.dto.CustodyMeasurementPeriodSummaryDto;
import dz.sh.hidra.modules.custody.application.dto.CustodyTransferTicketSummaryDto;
import java.util.Objects;

/**
 * Maps custody REST models to application models.
 */
public final class CustodyRestMapper {

    private static final CustodyGeneratedRestMapper GENERATED = CustodyGeneratedRestMapper.INSTANCE;

    private CustodyRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateCustodyTransferTicketCommand toCommand(CreateCustodyTransferTicketRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateCustodyTransferTicketRequest must not be null."));
    }

    public static OpenCustodyDiscrepancyCommand toCommand(OpenCustodyDiscrepancyRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "OpenCustodyDiscrepancyRequest must not be null."));
    }

    public static OpenCustodyMeasurementPeriodCommand toCommand(OpenCustodyMeasurementPeriodRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "OpenCustodyMeasurementPeriodRequest must not be null."));
    }

    public static CustodyTransferTicketResponse toResponse(CustodyTransferTicketSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "CustodyTransferTicketSummaryDto must not be null."));
    }

    public static CustodyDiscrepancyResponse toResponse(CustodyDiscrepancySummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "CustodyDiscrepancySummaryDto must not be null."));
    }

    public static CustodyMeasurementPeriodResponse toResponse(CustodyMeasurementPeriodSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "CustodyMeasurementPeriodSummaryDto must not be null."));
    }
}
