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
import dz.sh.hidra.modules.custody.api.rest.request.OpenCustodyDiscrepancyRequest;
import dz.sh.hidra.modules.custody.api.rest.request.OpenCustodyMeasurementPeriodRequest;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyDiscrepancyResponse;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyMeasurementPeriodResponse;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyTransferTicketResponse;
import dz.sh.hidra.modules.custody.application.port.in.CreateCustodyTransferTicketUseCase;
import dz.sh.hidra.modules.custody.application.port.in.OpenCustodyDiscrepancyUseCase;
import dz.sh.hidra.modules.custody.application.port.in.OpenCustodyMeasurementPeriodUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing custody REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/custody")
public class SpringCustodyController implements CustodyController {

    private final CreateCustodyTransferTicketUseCase createCustodyTransferTicketUseCase;
    private final OpenCustodyDiscrepancyUseCase openCustodyDiscrepancyUseCase;
    private final OpenCustodyMeasurementPeriodUseCase openCustodyMeasurementPeriodUseCase;

    public SpringCustodyController(
            CreateCustodyTransferTicketUseCase createCustodyTransferTicketUseCase,
            OpenCustodyDiscrepancyUseCase openCustodyDiscrepancyUseCase,
            OpenCustodyMeasurementPeriodUseCase openCustodyMeasurementPeriodUseCase
    ) {
        this.createCustodyTransferTicketUseCase = Objects.requireNonNull(createCustodyTransferTicketUseCase, "CreateCustodyTransferTicketUseCase must not be null.");
        this.openCustodyDiscrepancyUseCase = Objects.requireNonNull(openCustodyDiscrepancyUseCase, "OpenCustodyDiscrepancyUseCase must not be null.");
        this.openCustodyMeasurementPeriodUseCase = Objects.requireNonNull(openCustodyMeasurementPeriodUseCase, "OpenCustodyMeasurementPeriodUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "custody",
                "mission", "Support custody measurement periods, transfer tickets, and discrepancy handling.",
                "objectives", List.of(
                "Open custody measurement periods.",
                "Create custody transfer tickets.",
                "Open custody discrepancies for reconciliation."
        ),
                "operations", List.of(
                "createTransferTicket",
                "openDiscrepancy",
                "openMeasurementPeriod"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/custody/transfer-tickets",
                "POST /api/v1/custody/discrepancies",
                "POST /api/v1/custody/measurement-periods"
        )
        );
    }

    @Override
    @PostMapping({"/create-transfer-ticket", "/transfer-tickets"})
    public CustodyTransferTicketResponse createTransferTicket(@Valid @RequestBody CreateCustodyTransferTicketRequest request) {
        Objects.requireNonNull(request, "CreateCustodyTransferTicketRequest must not be null.");
        return CustodyRestMapper.toResponse(createCustodyTransferTicketUseCase.createTransferTicket(CustodyRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/open-discrepancy", "/discrepancies"})
    public CustodyDiscrepancyResponse openDiscrepancy(@Valid @RequestBody OpenCustodyDiscrepancyRequest request) {
        Objects.requireNonNull(request, "OpenCustodyDiscrepancyRequest must not be null.");
        return CustodyRestMapper.toResponse(openCustodyDiscrepancyUseCase.openDiscrepancy(CustodyRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/open-measurement-period", "/measurement-periods"})
    public CustodyMeasurementPeriodResponse openMeasurementPeriod(@Valid @RequestBody OpenCustodyMeasurementPeriodRequest request) {
        Objects.requireNonNull(request, "OpenCustodyMeasurementPeriodRequest must not be null.");
        return CustodyRestMapper.toResponse(openCustodyMeasurementPeriodUseCase.openMeasurementPeriod(CustodyRestMapper.toCommand(request)));
    }

}
