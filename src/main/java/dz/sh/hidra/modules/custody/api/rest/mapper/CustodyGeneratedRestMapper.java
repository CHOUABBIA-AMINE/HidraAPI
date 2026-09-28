/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.mapper
 *
 * @Description : Generates exact custody API/application boundary mappings at compile time.
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
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact custody boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CustodyGeneratedRestMapper {

    CustodyGeneratedRestMapper INSTANCE = Mappers.getMapper(CustodyGeneratedRestMapper.class);

    CreateCustodyTransferTicketCommand toCommand(CreateCustodyTransferTicketRequest request);

    OpenCustodyDiscrepancyCommand toCommand(OpenCustodyDiscrepancyRequest request);

    OpenCustodyMeasurementPeriodCommand toCommand(OpenCustodyMeasurementPeriodRequest request);

    CustodyDiscrepancyResponse toResponse(CustodyDiscrepancySummaryDto dto);

    CustodyMeasurementPeriodResponse toResponse(CustodyMeasurementPeriodSummaryDto dto);

    CustodyTransferTicketResponse toResponse(CustodyTransferTicketSummaryDto dto);
}
