/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductRevisionPublicationController
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

import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.Command;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.Kind;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.Selection;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.Failure;
import dz.sh.hidra.modules.custody.api.rest.request.ProductEngineeringDraftRequests;
import dz.sh.hidra.modules.custody.api.rest.request.ProductRevisionPublicationRequests;
import dz.sh.hidra.modules.custody.api.rest.response.ProductRevisionPublicationResponses.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import java.util.*;
import java.time.Instant;

@RestController
@RequestMapping(value="/api/v1/custody/engineering-products/{draftId}/publications",produces=MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name="hidraBearerJwt")
@ApiResponses({@ApiResponse(responseCode="400",description="Malformed request"),@ApiResponse(responseCode="401",description="Native authentication required"),@ApiResponse(responseCode="403",description="Native denied or foreign/missing target"),@ApiResponse(responseCode="409",description="Authorized source/head/replay conflict"),@ApiResponse(responseCode="503",description="Evidence unavailable or completion indeterminate; no automatic retry")})
public class ProductRevisionPublicationController {
    private final ProductRevisionPublicationUseCase useCase;
    public ProductRevisionPublicationController(ProductRevisionPublicationUseCase useCase){this.useCase=Objects.requireNonNull(useCase);}
    @Operation(operationId="prepareProductPublication",summary="Prepare the exact current metadata and supplied-source envelope for Workflow review")
    @ApiResponse(responseCode="201",description="Candidate prepared",useReturnTypeSchema=true) @ApiResponse(responseCode="200",description="Exact replay",useReturnTypeSchema=true)
    @PostMapping(value="/candidates",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> prepare(@PathVariable("draftId") String id,@RequestParam("companyPartyId") String company,@RequestBody ProductRevisionPublicationRequests.Prepare body,HttpServletRequest request){query(request,Set.of("companyPartyId"),Set.of("companyPartyId"));return run(command(Kind.PREPARE,id,company,body.requestId(),body.metadataVersion(),body.suppliedVersion(),null,null,0,null,null,null,0,null),true);}
    @Operation(operationId="readProductPublicationCandidate",summary="Read an exact prepared envelope under fresh native scope")
    @GetMapping("/candidates/{digest}") public Result candidate(@PathVariable("draftId") String id,@RequestParam("companyPartyId") String company,@PathVariable("digest") String digest,HttpServletRequest request){query(request,Set.of("companyPartyId"),Set.of("companyPartyId"));ProductRevisionPublicationRequests.digest(digest);return result(useCase.execute(command(Kind.READ_CANDIDATE,id,company,null,0,0,digest,null,0,null,null,null,0,null)));}
    @Operation(operationId="publishProductPublication",summary="Publish the approved exact candidate and compare-and-set the head")
    @ApiResponse(responseCode="201",description="Publication created",useReturnTypeSchema=true) @ApiResponse(responseCode="200",description="Exact replay",useReturnTypeSchema=true)
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> publish(@PathVariable("draftId") String id,@RequestParam("companyPartyId") String company,@RequestBody ProductRevisionPublicationRequests.Publish body,HttpServletRequest request){query(request,Set.of("companyPartyId"),Set.of("companyPartyId"));return run(command(Kind.PUBLISH,id,company,body.requestId(),0,0,body.candidateDigest(),null,body.expectedEpoch(),body.expectedPublicationId(),selection(body.approval()),null,0,null),true);}
    @Operation(operationId="withdrawProductPublication",summary="Withdraw the selected publication without requiring live unit/approval eligibility")
    @PostMapping(value="/{publicationId}/withdraw",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> withdraw(@PathVariable("draftId") String id,@RequestParam("companyPartyId") String company,@PathVariable("publicationId") String publication,@RequestBody ProductRevisionPublicationRequests.Withdraw body,HttpServletRequest request){query(request,Set.of("companyPartyId"),Set.of("companyPartyId"));return run(command(Kind.WITHDRAW,id,company,body.requestId(),0,0,null,publication,body.expectedEpoch(),body.expectedPublicationId(),null,null,0,null),false);}
    @Operation(operationId="reactivateProductPublication",summary="Reactivate the same snapshot with fresh applicable approval and owner eligibility")
    @PostMapping(value="/{publicationId}/reactivate",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> reactivate(@PathVariable("draftId") String id,@RequestParam("companyPartyId") String company,@PathVariable("publicationId") String publication,@RequestBody ProductRevisionPublicationRequests.Reactivate body,HttpServletRequest request){query(request,Set.of("companyPartyId"),Set.of("companyPartyId"));return run(command(Kind.REACTIVATE,id,company,body.requestId(),0,0,null,publication,body.expectedEpoch(),body.expectedPublicationId(),selection(body.approval()),null,0,null),false);}
    @Operation(operationId="readExactProductPublication",summary="Read an immutable historical snapshot; no current eligibility certificate")
    @GetMapping("/{publicationId}") public Result exact(@PathVariable("draftId") String id,@RequestParam("companyPartyId") String company,@PathVariable("publicationId") String publication,HttpServletRequest request){query(request,Set.of("companyPartyId"),Set.of("companyPartyId"));return read(Kind.READ_EXACT,id,company,null,publication,null,0,null);}
    @Operation(operationId="readCurrentProductPublication",summary="Read the committed current head and historical snapshot, including withdrawn state")
    @GetMapping("/current") public Result current(@PathVariable("draftId") String id,@RequestParam("companyPartyId") String company,HttpServletRequest request){query(request,Set.of("companyPartyId"),Set.of("companyPartyId"));return read(Kind.READ_CURRENT,id,company,null,null,null,0,null);}
    @Operation(operationId="readEffectiveProductPublication",summary="Read the committed selection at an explicit database-time instant without fallback")
    @GetMapping("/effective") public Result effective(@PathVariable("draftId") String id,@RequestParam("companyPartyId") String company,@RequestParam("asOf") String asOf,HttpServletRequest request){query(request,Set.of("companyPartyId","asOf"),Set.of("companyPartyId","asOf"));return read(Kind.READ_EFFECTIVE,id,company,null,null,ProductRevisionPublicationRequests.instant(asOf),0,null);}
    @Operation(operationId="readProductPublicationHistory",summary="Read bounded event history with a scope/asOf/high-water-bound cursor")
    @GetMapping("/history") public Result history(@PathVariable("draftId") String id,@RequestParam("companyPartyId") String company,@RequestParam("asOf") String asOf,@RequestParam("limit") String limit,@RequestParam(value="cursor",required=false) String cursor,HttpServletRequest request){query(request,Set.of("companyPartyId","asOf","limit","cursor"),Set.of("companyPartyId","asOf","limit"));if(!limit.matches("[1-9][0-9]{0,2}") || Integer.parseInt(limit)>100 || cursor!=null&&(cursor.isEmpty() || cursor.length()>4096))throw invalid();return read(Kind.READ_HISTORY,id,company,null,null,ProductRevisionPublicationRequests.instant(asOf),Integer.parseInt(limit),cursor);}
    @Operation(operationId="readProductPublicationReceipt",summary="Read the original actor-bound request receipt and separately observed head")
    @GetMapping("/requests/{requestId}") public Result receipt(@PathVariable("draftId") String id,@RequestParam("companyPartyId") String company,@PathVariable("requestId") String requestId,HttpServletRequest request){query(request,Set.of("companyPartyId"),Set.of("companyPartyId"));return read(Kind.READ_RECEIPT,id,company,requestId,null,null,0,null);}
    private Result read(Kind k,String id,String company,String request,String publication,Instant at,int limit,String cursor){return result(useCase.execute(command(k,id,company,request,0,0,null,publication,0,null,null,at,limit,cursor)));}
    private ResponseEntity<Result> run(Command c,boolean creation){var r=useCase.execute(c);return ResponseEntity.status(creation&&!r.replayed()?201:200).body(result(r));}
    private static Selection selection(ProductRevisionPublicationRequests.Approval a){return new Selection(a.instanceId(),a.taskId(),a.actionId());}
    private static UUID uuid(String s){return s==null?null:ProductEngineeringDraftRequests.uuid(s);}
    private static Command command(Kind k,String id,String company,String request,long mv,long fv,String digest,String publication,long epoch,String expected,Selection a,Instant at,int limit,String cursor){ProductEngineeringDraftRequests.exact(company,80,true);return new Command(k,ProductEngineeringDraftRequests.uuid(id),company,uuid(request),mv,fv,digest,uuid(publication),epoch,uuid(expected),a,at,limit,cursor);}
    private static void query(HttpServletRequest r,Set<String> allowed,Set<String> required){if(!allowed.containsAll(r.getParameterMap().keySet()) || !r.getParameterMap().keySet().containsAll(required))throw invalid();for(var v:r.getParameterMap().values())if(v==null || v.length!=1)throw invalid();}
    private static IllegalArgumentException invalid(){return new IllegalArgumentException("Invalid product publication request");}
    @ExceptionHandler(Failure.class) public ResponseEntity<ProblemDetail> failure(Failure f){var status=switch(f.reason()){case INVALID_REQUEST->HttpStatus.BAD_REQUEST;case DENIED->HttpStatus.FORBIDDEN;case CONFLICT->HttpStatus.CONFLICT;case UNAVAILABLE,INDETERMINATE->HttpStatus.SERVICE_UNAVAILABLE;};return problem(status,f.reason().name());}
    @ExceptionHandler({IllegalArgumentException.class,java.time.DateTimeException.class,HttpMessageNotReadableException.class,MissingServletRequestParameterException.class}) public ResponseEntity<ProblemDetail> malformed(Exception ignored){return problem(HttpStatus.BAD_REQUEST,"INVALID_REQUEST");}
    private static ResponseEntity<ProblemDetail> problem(HttpStatus s,String code){var p=ProblemDetail.forStatusAndDetail(s,"Product publication "+(s==HttpStatus.FORBIDDEN?"access denied":s==HttpStatus.CONFLICT?"conflict":s==HttpStatus.SERVICE_UNAVAILABLE?"unavailable or completion indeterminate":"invalid request"));p.setProperty("code",code);return ResponseEntity.status(s).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(p);}
    private static Result result(ProductRevisionPublicationUseCase.Result r){var p=r.publication();var t=r.receipt();return new Result(envelope(r.candidate()),r.candidateDigest(),p==null?null:new Publication(p.publicationId(),envelope(p.envelope()),approval(p.approval()),false),head(r.observedHead()),head(r.selectedHead()),t==null?null:new Receipt(scope(t.scope()),t.actorId(),t.requestId(),t.commandSha256(),t.kind(),t.candidateDigest(),event(t.event()),t.recordedAt()),r.history().stream().map(ProductRevisionPublicationController::event).toList(),r.nextCursor(),r.replayed());}
    private static Scope scope(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Scope s){return new Scope(s.productDraftId(),s.companyPartyId(),s.catalogProductId());}
    private static Component component(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Component s){return new Component(s.version(),s.format(),s.sha256(),s.base64());}
    private static Envelope envelope(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Envelope s){if(s==null)return null;var p=s.policy();return new Envelope(scope(s.scope()),component(s.metadata()),component(s.supplied()),s.suppliedMetadataVersion(),s.classification(),s.diagnostics(),s.units().stream().map(u->new Unit(u.unitId(),u.role(),u.code(),u.symbol(),u.dimension(),u.updatedAt())).toList(),new Policy(p.definitionId(),p.definitionVersion(),p.targetTypeId(),p.purposeId()),false);}
    private static Approval approval(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Approval s){return s==null?null:new Approval(s.digest(),s.instanceId(),s.taskId(),s.actionId(),s.historyId(),s.actorId(),s.actorDisplayName(),s.actedAt());}
    private static Head head(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Head s){return s==null?null:new Head(scope(s.scope()),s.publicationId(),s.epoch(),s.state().name(),s.eventId(),s.effectiveAt());}
    private static Event event(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Event s){return s==null?null:new Event(s.eventId(),scope(s.scope()),s.epoch(),s.action().name(),s.priorPublicationId(),s.publicationId(),s.state().name(),s.requestId(),s.commandSha256(),s.effectiveAt(),approval(s.approval()));}
}
