/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticExchangesController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.controller
 * @Description : T26 bounded synthetic exchange REST contract.
 */
package dz.sh.hidra.modules.integration.api.rest.controller;
import dz.sh.hidra.modules.integration.api.rest.mapper.SyntheticExchangeRestMapper;
import dz.sh.hidra.modules.integration.api.rest.request.SyntheticExchangeRequests.T26SubmitExchange;
import dz.sh.hidra.modules.integration.api.rest.request.SyntheticExchangeRequests.T26ReplayExchange;
import dz.sh.hidra.modules.integration.api.rest.response.SyntheticExchangeResponses;
import dz.sh.hidra.modules.integration.application.port.in.SyntheticExchangeUseCase;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Scope;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/integration/companies/{companyId}/facilities/{facilityId}")
public final class SyntheticExchangesController {
    private final SyntheticExchangeUseCase exchange;
    public SyntheticExchangesController(SyntheticExchangeUseCase exchange) {
        this.exchange=Objects.requireNonNull(exchange);
    }
    @GetMapping("/synthetic-mappings/{profileId}")
    @NativeProtectedOperation @Operation(operationId="readT26SyntheticMapping")
    public SyntheticExchangeResponses.T26SyntheticMapping mapping(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String profileId) {
        return SyntheticExchangeRestMapper.mapping(exchange.mapping(new Scope(companyId,facilityId),profileId));
    }
    @PostMapping("/synthetic-exchanges")
    @NativeProtectedOperation @Operation(operationId="submitT26SyntheticExchange")
    public ResponseEntity<SyntheticExchangeResponses.T26SyntheticExchange> submit(
            @PathVariable String companyId,@PathVariable String facilityId,
            @Valid @RequestBody T26SubmitExchange command) {
        var receipt=exchange.submit(new Scope(companyId,facilityId),SyntheticExchangeRestMapper.submit(command));
        return ResponseEntity.status(receipt.replayed()?200:201).body(SyntheticExchangeRestMapper.exchange(receipt));
    }
    @GetMapping("/synthetic-exchanges/{exchangeId}")
    @NativeProtectedOperation @Operation(operationId="readT26SyntheticExchange")
    public SyntheticExchangeResponses.T26SyntheticExchange detail(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String exchangeId) {
        return SyntheticExchangeRestMapper.exchange(exchange.detail(new Scope(companyId,facilityId),exchangeId));
    }
    @GetMapping("/synthetic-exchanges/{exchangeId}/attempts")
    @NativeProtectedOperation @Operation(operationId="listT26SyntheticExchangeAttempts")
    public SyntheticExchangeResponses.T26SyntheticAttempts attempts(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String exchangeId) {
        return SyntheticExchangeRestMapper.attempts(
                exchange.attempts(new Scope(companyId,facilityId),exchangeId));
    }
    @PostMapping("/synthetic-exchanges/{exchangeId}/replay")
    @NativeProtectedOperation @Operation(operationId="replayT26SyntheticExchange")
    public SyntheticExchangeResponses.T26SyntheticExchange replay(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String exchangeId,
            @Valid @RequestBody T26ReplayExchange command) {
        return SyntheticExchangeRestMapper.exchange(exchange.replay(new Scope(companyId,facilityId),
                exchangeId,SyntheticExchangeRestMapper.replay(command)));
    }
}
