/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringLocalExchangeController;
import dz.sh.hidra.modules.topology.api.rest.request.EngineeringLocalExchangeRequests.Submission;
import dz.sh.hidra.modules.topology.application.model.*;
import java.io.*;
import java.lang.reflect.Type;
import org.springframework.core.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import tools.jackson.core.*;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.*;
import tools.jackson.databind.json.JsonMapper;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes=EngineeringLocalExchangeController.class)
public final class EngineeringLocalExchangeApiExceptionHandler extends RequestBodyAdviceAdapter {
    public static final int MAX_BODY_BYTES=25165824;
    private static final JsonMapper STRICT=JsonMapper.builder(JsonFactory.builder()
        .enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION)
        .streamReadConstraints(StreamReadConstraints.builder().maxNestingDepth(8).maxStringLength(22369624).build()).build())
        .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS).build();
    public static final class Unauthenticated extends RuntimeException { }
    public static void authenticated(){var a=SecurityContextHolder.getContext().getAuthentication();
        if(a==null || !a.isAuthenticated() || a instanceof AnonymousAuthenticationToken)throw new Unauthenticated();}
    @Override public boolean supports(MethodParameter p,Type t,Class<? extends HttpMessageConverter<?>> converter){return p.getContainingClass()==EngineeringLocalExchangeController.class && p.getParameterType()==byte[].class;}
    @Override public HttpInputMessage beforeBodyRead(HttpInputMessage input,MethodParameter p,Type t,Class<? extends HttpMessageConverter<?>> converter)throws IOException{
        byte[] bytes=input.getBody().readNBytes(MAX_BODY_BYTES+1);
        try{if(bytes.length>MAX_BODY_BYTES || !STRICT.readTree(bytes).isObject())throw new IllegalArgumentException();}
        catch(RuntimeException e){throw new IllegalArgumentException("Invalid exchange JSON");}
        return new HttpInputMessage(){public InputStream getBody(){return new ByteArrayInputStream(bytes);}public HttpHeaders getHeaders(){return input.getHeaders();}};
    }
    public static Submission submission(byte[] bytes){try{return STRICT.readValue(bytes,Submission.class);}catch(RuntimeException invalid){throw new IllegalArgumentException("Invalid exchange JSON");}}
    @ExceptionHandler(Unauthenticated.class) public ResponseEntity<ProblemDetail> anonymous(Unauthenticated e){return problem(401,"UNAUTHENTICATED");}
    @ExceptionHandler(AccessDeniedException.class) public ResponseEntity<ProblemDetail> denied(AccessDeniedException e){
        var a=SecurityContextHolder.getContext().getAuthentication();
        return problem(a==null || !a.isAuthenticated() || a instanceof AnonymousAuthenticationToken?401:403,"DENIED");}
    @ExceptionHandler(EngineeringNetworkDraftFailure.class) public ResponseEntity<ProblemDetail> nativeFailure(EngineeringNetworkDraftFailure e){
        int status=switch(e.reason()){case DENIED->403;case NOT_FOUND->404;case CONFLICT->409;case UNAVAILABLE,INDETERMINATE->503;};
        return problem(status,e.reason().name());}
    @ExceptionHandler(EngineeringLocalExchangeFailure.class) public ResponseEntity<ProblemDetail> conflict(EngineeringLocalExchangeFailure e){return problem(409,e.reason().name());}
    @ExceptionHandler({IllegalArgumentException.class,HttpMessageNotReadableException.class,MissingServletRequestParameterException.class,MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception e){return problem(400,"INVALID");}
    private static ResponseEntity<ProblemDetail> problem(int status,String code){
        var p=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status),"Engineering local exchange unavailable");
        p.setInstance(java.net.URI.create("urn:hidra:engineering-local-exchange"));p.setProperty("code",code);return ResponseEntity.status(status).body(p);
    }
}
