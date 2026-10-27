/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuppliedFluidPropertyRevisionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.controller
 *
 * @Description : Provides strict protected supplied-fluid draft HTTP contracts.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.controller;

import dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase;
import dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.*;
import dz.sh.hidra.modules.custody.api.rest.request.SuppliedFluidPropertyRevisionRequests.Write;
import dz.sh.hidra.modules.custody.api.rest.request.ProductEngineeringDraftRequests;
import dz.sh.hidra.modules.custody.api.rest.response.SuppliedFluidPropertyRevisionResponses;
import dz.sh.hidra.modules.custody.api.rest.response.SuppliedFluidPropertyRevisionResponses.Draft;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MissingServletRequestParameterException;

@RestController
@RequestMapping(value="/api/v1/custody/engineering-products/{draftId}/properties",produces=MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name="hidraBearerJwt")
@ApiResponses({
    @ApiResponse(responseCode="400",description="Malformed, coerced, unsupported or contradictory declaration",content=@Content(mediaType="application/problem+json",schema=@Schema(implementation=ProblemDetail.class))),
    @ApiResponse(responseCode="401",description="Native authentication required",content=@Content),
    @ApiResponse(responseCode="403",description="Native access denied; missing/foreign targets are not disclosed",content=@Content(mediaType="application/problem+json",schema=@Schema(implementation=ProblemDetail.class))),
    @ApiResponse(responseCode="409",description="Authorized version, metadata binding or exact-request conflict",content=@Content(mediaType="application/problem+json",schema=@Schema(implementation=ProblemDetail.class))),
    @ApiResponse(responseCode="503",description="Current authority/unit/storage evidence unavailable or completion indeterminate; no automatic write retry",content=@Content(mediaType="application/problem+json",schema=@Schema(implementation=ProblemDetail.class)))
})
public class SuppliedFluidPropertyRevisionController {
    private final SuppliedFluidPropertyRevisionUseCase useCase;
    public SuppliedFluidPropertyRevisionController(SuppliedFluidPropertyRevisionUseCase useCase){this.useCase=Objects.requireNonNull(useCase);}
    @Operation(operationId="readSuppliedFluidPropertyDraft",summary="Read the current protected supplied-fluid revision with authored metadata binding")
    @ApiResponse(responseCode="200",description="Current supplied draft; stale metadata is diagnosed without reinterpretation",useReturnTypeSchema=true)
    @GetMapping
    public Draft read(@Parameter(schema=@Schema(type="string",format="uuid")) @PathVariable("draftId") String draftId,
            @RequestParam("companyPartyId") String companyPartyId,HttpServletRequest request) {
        scope(request,companyPartyId);
        return SuppliedFluidPropertyRevisionResponses.from(useCase.execute(new Command(Kind.READ,companyPartyId,ProductEngineeringDraftRequests.uuid(draftId),null,null,null,null)));
    }
    @Operation(operationId="writeSuppliedFluidPropertyDraft",summary="Append exact supplied declarations or replay an authorized original request")
    @ApiResponse(responseCode="201",description="First supplied revision created",content=@Content(mediaType="application/json",schema=@Schema(implementation=Draft.class)))
    @ApiResponse(responseCode="200",description="Successor or freshly authorized replay returning the current supplied revision",useReturnTypeSchema=true)
    @PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Draft> write(@Parameter(schema=@Schema(type="string",format="uuid")) @PathVariable("draftId") String draftId,
            @RequestParam("companyPartyId") String companyPartyId,@RequestBody Write body,HttpServletRequest request) {
        scope(request,companyPartyId);if(body==null)throw invalid();
        var result=useCase.execute(new Command(Kind.WRITE,companyPartyId,ProductEngineeringDraftRequests.uuid(draftId),ProductEngineeringDraftRequests.uuid(body.requestId()),
            body.expectedVersion(),body.expectedProductMetadataVersion(),body.facts()));
        return ResponseEntity.status(result.draft().version()==1 && !result.replayed()?201:200).body(SuppliedFluidPropertyRevisionResponses.from(result));
    }
    private static void scope(HttpServletRequest request,String company) {
        if(!request.getParameterMap().keySet().equals(Set.of("companyPartyId")) || request.getParameterValues("companyPartyId").length!=1)throw invalid();
        ProductEngineeringDraftRequests.exact(company,80,true);
    }
    private static IllegalArgumentException invalid(){return new IllegalArgumentException("Invalid supplied fluid request");}
    @ExceptionHandler(Failure.class)
    public ResponseEntity<ProblemDetail> failure(Failure failure) {
        HttpStatus status=switch(failure.reason()){case INVALID_REQUEST->HttpStatus.BAD_REQUEST;case DENIED->HttpStatus.FORBIDDEN;case CONFLICT->HttpStatus.CONFLICT;case UNAVAILABLE,INDETERMINATE->HttpStatus.SERVICE_UNAVAILABLE;};
        String detail=switch(failure.reason()){case INVALID_REQUEST->"Invalid supplied fluid request";case DENIED->"Supplied fluid access denied";case CONFLICT->"Supplied fluid version or request conflict";case UNAVAILABLE->"Supplied fluid service unavailable";case INDETERMINATE->"Supplied fluid completion is indeterminate";};
        var p=ProblemDetail.forStatusAndDetail(status,detail);p.setInstance(URI.create("urn:hidra:supplied-fluid-property-draft"));p.setProperty("code",failure.reason().name());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(p);
    }
    @ExceptionHandler({IllegalArgumentException.class,HttpMessageNotReadableException.class,MissingServletRequestParameterException.class})
    public ResponseEntity<ProblemDetail> malformed(Exception ignored) {
        var p=ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Invalid supplied fluid request");p.setInstance(URI.create("urn:hidra:supplied-fluid-property-draft"));p.setProperty("code","INVALID_REQUEST");
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(p);
    }
}
