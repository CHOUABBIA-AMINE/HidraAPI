/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-26
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringLocalExchangeUseCase;
import dz.sh.hidra.modules.topology.api.rest.request.EngineeringLocalExchangeRequests.Submission;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringLocalExchangeResponses.Result;
import dz.sh.hidra.modules.topology.api.rest.mapper.EngineeringLocalExchangeRestMapper;
import dz.sh.hidra.modules.topology.api.rest.EngineeringLocalExchangeApiExceptionHandler;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Objects;

@io.swagger.v3.oas.annotations.responses.ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="400", description="Malformed or invalid request", content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json", schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="401", description="Authentication required; filter or owner response", content=@io.swagger.v3.oas.annotations.media.Content),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="403", description="Current native authority denies access", content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json", schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="404", description="Authorized target unavailable", content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json", schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="409", description="Version, request, graph or publication conflict", content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json", schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="503", description="Unavailable authority or indeterminate completion; do not automatically retry", content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json", schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class)))
})
@RestController
@RequestMapping("/api/v1/topology/engineering-networks/{draftId}/exchanges/{editor}")
public final class EngineeringLocalExchangeController {
    private final EngineeringLocalExchangeUseCase owner;
    public EngineeringLocalExchangeController(EngineeringLocalExchangeUseCase owner){this.owner=Objects.requireNonNull(owner);}
    private Result call(EngineeringLocalExchangeUseCase.Operation op,String editor,String company,String network,String draft,String branch,Long version,Submission body){
        EngineeringLocalExchangeApiExceptionHandler.authenticated();
        return EngineeringLocalExchangeRestMapper.response(owner.execute(EngineeringLocalExchangeRestMapper.command(op,editor,company,network,draft,branch,version,body)));
    }
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200", description="Successful operation", useReturnTypeSchema=true)
    @Operation(operationId="exportEngineeringLocalExchange")
    @GetMapping("/export") public Result export(@PathVariable("draftId") String draft,@PathVariable("editor") String editor,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,
            @RequestParam(value="branchId",required=false) String branch,@RequestParam(value="version",required=false) Long version){
        return call(EngineeringLocalExchangeUseCase.Operation.EXPORT,editor,company,network,draft,branch,version,null);
    }
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200", description="Successful operation", useReturnTypeSchema=true)
    @Operation(operationId="dryRunEngineeringLocalExchange")
    @PostMapping(value="/dry-runs",consumes="application/json") public Result dryRun(@PathVariable("draftId") String draft,@PathVariable("editor") String editor,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,
            @RequestParam(value="branchId",required=false) String branch,@RequestParam(value="version",required=false) Long version,@io.swagger.v3.oas.annotations.parameters.RequestBody(content=@io.swagger.v3.oas.annotations.media.Content(schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=Submission.class))) @RequestBody byte[] bytes){
        return call(EngineeringLocalExchangeUseCase.Operation.DRY_RUN,editor,company,network,draft,branch,version,EngineeringLocalExchangeApiExceptionHandler.submission(bytes));
    }
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200", description="Successful operation", useReturnTypeSchema=true)
    @Operation(operationId="importEngineeringLocalExchange")
    @PostMapping(value="/imports",consumes="application/json") public Result importOne(@PathVariable("draftId") String draft,@PathVariable("editor") String editor,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,
            @RequestParam(value="branchId",required=false) String branch,@RequestParam(value="version",required=false) Long version,@io.swagger.v3.oas.annotations.parameters.RequestBody(content=@io.swagger.v3.oas.annotations.media.Content(schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=Submission.class))) @RequestBody byte[] bytes){
        return call(EngineeringLocalExchangeUseCase.Operation.IMPORT,editor,company,network,draft,branch,version,EngineeringLocalExchangeApiExceptionHandler.submission(bytes));
    }
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200", description="Successful operation", useReturnTypeSchema=true)
    @Operation(operationId="readEngineeringLocalExchangeCapabilities")
    @GetMapping("/capabilities") public Result capabilities(@PathVariable("draftId") String draft,@PathVariable("editor") String editor,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,
            @RequestParam(value="branchId",required=false) String branch,@RequestParam(value="version",required=false) Long version){
        return call(EngineeringLocalExchangeUseCase.Operation.CAPABILITIES,editor,company,network,draft,branch,version,null);
    }
}
