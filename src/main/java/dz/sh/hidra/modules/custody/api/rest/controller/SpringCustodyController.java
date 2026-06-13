/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringCustodyController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing custody REST endpoints.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.controller;

import dz.sh.hidra.modules.custody.api.rest.mapper.CustodyRestMapper;
import dz.sh.hidra.modules.custody.api.rest.request.CreateCustodyTransferTicketRequest;
import dz.sh.hidra.modules.custody.api.rest.request.OpenCustodyMeasurementPeriodRequest;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyMeasurementPeriodResponse;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyTransferTicketResponse;
import dz.sh.hidra.modules.custody.application.port.in.CreateCustodyTransferTicketUseCase;
import dz.sh.hidra.modules.custody.application.port.in.OpenCustodyMeasurementPeriodUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing custody REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/custody")
public final class SpringCustodyController implements CustodyController {

    private final OpenCustodyMeasurementPeriodUseCase openCustodyMeasurementPeriodUseCase;
    private final CreateCustodyTransferTicketUseCase createCustodyTransferTicketUseCase;

    public SpringCustodyController(
            OpenCustodyMeasurementPeriodUseCase openCustodyMeasurementPeriodUseCase,
            CreateCustodyTransferTicketUseCase createCustodyTransferTicketUseCase
    ) {
        this.openCustodyMeasurementPeriodUseCase = Objects.requireNonNull(openCustodyMeasurementPeriodUseCase, "OpenCustodyMeasurementPeriodUseCase must not be null.");
        this.createCustodyTransferTicketUseCase = Objects.requireNonNull(createCustodyTransferTicketUseCase, "CreateCustodyTransferTicketUseCase must not be null.");
    }


    @Override
    @PostMapping("/open-measurement-period")
    public CustodyMeasurementPeriodResponse openMeasurementPeriod(@Valid @RequestBody OpenCustodyMeasurementPeriodRequest request) {
        Objects.requireNonNull(request, "OpenCustodyMeasurementPeriodRequest must not be null.");
        return CustodyRestMapper.toResponse(openCustodyMeasurementPeriodUseCase.openMeasurementPeriod(CustodyRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/create-transfer-ticket")
    public CustodyTransferTicketResponse createTransferTicket(@Valid @RequestBody CreateCustodyTransferTicketRequest request) {
        Objects.requireNonNull(request, "CreateCustodyTransferTicketRequest must not be null.");
        return CustodyRestMapper.toResponse(createCustodyTransferTicketUseCase.createTransferTicket(CustodyRestMapper.toCommand(request)));
    }

}
