/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductEngineeringDraftController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.controller
 *
 * @Description : Provides company-owned catalog-linked product metadata drafts.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.controller;

import dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase;
import dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.*;
import dz.sh.hidra.modules.custody.api.rest.request.ProductEngineeringDraftRequests;
import dz.sh.hidra.modules.custody.api.rest.request.ProductEngineeringDraftRequests.Create;
import dz.sh.hidra.modules.custody.api.rest.request.ProductEngineeringDraftRequests.Update;
import dz.sh.hidra.modules.custody.api.rest.response.ProductEngineeringDraftResponses;
import dz.sh.hidra.modules.custody.api.rest.response.ProductEngineeringDraftResponses.Draft;
import dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft;
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
@RequestMapping(value="/api/v1/custody/engineering-products",produces=MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name="hidraBearerJwt")
@ApiResponses({
    @ApiResponse(responseCode="400",description="Malformed, unknown, coerced or invalid request field",content=@Content(mediaType="application/problem+json",schema=@Schema(implementation=ProblemDetail.class))),
    @ApiResponse(responseCode="401",description="Native authentication required",content=@Content),
    @ApiResponse(responseCode="403",description="Denied or unavailable company-bound target; missing/foreign identities are not disclosed",content=@Content(mediaType="application/problem+json",schema=@Schema(implementation=ProblemDetail.class))),
    @ApiResponse(responseCode="409",description="Authorized version, local code or creation-request conflict",content=@Content(mediaType="application/problem+json",schema=@Schema(implementation=ProblemDetail.class))),
    @ApiResponse(responseCode="503",description="Authority/storage unavailable or completion indeterminate; do not automatically retry writes",content=@Content(mediaType="application/problem+json",schema=@Schema(implementation=ProblemDetail.class)))
})
public class ProductEngineeringDraftController {
    private final ProductEngineeringDraftUseCase useCase;
    public ProductEngineeringDraftController(ProductEngineeringDraftUseCase useCase){this.useCase=Objects.requireNonNull(useCase);}

    @Operation(operationId="createProductEngineeringDraft",summary="Create or replay an authorized company-local product metadata draft")
    @ApiResponse(responseCode="201",description="New draft created",content=@Content(mediaType="application/json",schema=@Schema(implementation=Draft.class)))
    @ApiResponse(responseCode="200",description="Freshly authorized exact replay; returns current metadata without reverting later revisions",useReturnTypeSchema=true)
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Draft> create(@RequestBody Create body,HttpServletRequest request) {
        if(body==null || !request.getParameterMap().isEmpty())throw invalid();
        var result=useCase.execute(new Command(Kind.CREATE,body.companyPartyId(),null,body.localCode(),body.catalogProductId(),
                ProductEngineeringDraftRequests.uuid(body.creationRequestId()),null,metadata(body.name(),body.description(),body.evidenceReference(),body.classification())));
        return ResponseEntity.status(result.replayed()?200:201).body(ProductEngineeringDraftResponses.from(result));
    }
    @Operation(operationId="readProductEngineeringDraft",summary="Read current authorized product metadata; no public history or export")
    @ApiResponse(responseCode="200",description="Current company-local draft metadata",useReturnTypeSchema=true)
    @GetMapping("/{draftId}")
    public Draft read(@Parameter(schema=@Schema(type="string",format="uuid")) @PathVariable("draftId") String draftId,
            @RequestParam("companyPartyId") String companyPartyId,HttpServletRequest request) {
        scope(request,companyPartyId);
        return ProductEngineeringDraftResponses.from(useCase.execute(new Command(Kind.READ,companyPartyId,
                ProductEngineeringDraftRequests.uuid(draftId),null,null,null,null,null)));
    }
    @Operation(operationId="updateProductEngineeringDraft",summary="Append authorized product metadata at an exact expected version")
    @ApiResponse(responseCode="200",description="Current successor metadata draft",useReturnTypeSchema=true)
    @PutMapping(value="/{draftId}",consumes=MediaType.APPLICATION_JSON_VALUE)
    public Draft update(@Parameter(schema=@Schema(type="string",format="uuid")) @PathVariable("draftId") String draftId,
            @RequestParam("companyPartyId") String companyPartyId,@RequestBody Update body,HttpServletRequest request) {
        scope(request,companyPartyId);if(body==null)throw invalid();
        return ProductEngineeringDraftResponses.from(useCase.execute(new Command(Kind.UPDATE,companyPartyId,
                ProductEngineeringDraftRequests.uuid(draftId),null,null,null,body.expectedVersion(),
                metadata(body.name(),body.description(),body.evidenceReference(),body.classification()))));
    }
    private static ProductEngineeringDraft.Metadata metadata(String name,String description,String evidence,
            ProductEngineeringDraftRequests.Classification classification) {
        return new ProductEngineeringDraft.Metadata(name,description,evidence,classification==null?null:
                ProductEngineeringDraft.Classification.valueOf(classification.name()));
    }
    private static void scope(HttpServletRequest request,String company) {
        if(!request.getParameterMap().keySet().equals(Set.of("companyPartyId")) || request.getParameterValues("companyPartyId").length!=1)throw invalid();
        ProductEngineeringDraftRequests.exact(company,80,true);
    }
    private static IllegalArgumentException invalid(){return new IllegalArgumentException("Invalid product draft request");}

    @ExceptionHandler(Failure.class)
    public ResponseEntity<ProblemDetail> failure(Failure failure) {
        HttpStatus status=switch(failure.reason()){case DENIED->HttpStatus.FORBIDDEN;case CONFLICT->HttpStatus.CONFLICT;case UNAVAILABLE,INDETERMINATE->HttpStatus.SERVICE_UNAVAILABLE;};
        String detail=switch(failure.reason()){case DENIED->"Product draft access denied";case CONFLICT->"Product draft version or creation request conflict";case UNAVAILABLE->"Product draft service unavailable";case INDETERMINATE->"Product draft completion is indeterminate";};
        var problem=ProblemDetail.forStatusAndDetail(status,detail);problem.setInstance(URI.create("urn:hidra:product-engineering-draft"));problem.setProperty("code",failure.reason().name());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problem);
    }
    @ExceptionHandler({IllegalArgumentException.class,HttpMessageNotReadableException.class,MissingServletRequestParameterException.class})
    public ResponseEntity<ProblemDetail> malformed(Exception ignored) {
        var problem=ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Invalid product draft request");
        problem.setInstance(URI.create("urn:hidra:product-engineering-draft"));problem.setProperty("code","INVALID_REQUEST");
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problem);
    }
}
