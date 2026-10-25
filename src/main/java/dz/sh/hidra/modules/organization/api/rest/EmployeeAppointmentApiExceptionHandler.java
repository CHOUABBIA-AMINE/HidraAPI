/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest
 *
 * @Description : Bounds appointment JSON and returns fixed scoped errors without native or storage evidence.
 *
 */
package dz.sh.hidra.modules.organization.api.rest;


import dz.sh.hidra.modules.organization.api.rest.controller.*;
import dz.sh.hidra.modules.organization.api.rest.request.AssignEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.request.EmployeeAppointmentLifecycleRequest;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult;
import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import tools.jackson.core.*;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.*;
import tools.jackson.databind.json.JsonMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.lang.reflect.Type;
import java.io.*;
import java.util.function.Supplier;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes={EmployeeAppointmentLifecycleController.class,SpringOrganizationController.class,OrganizationAdministrationQueryController.class})
public final class EmployeeAppointmentApiExceptionHandler extends RequestBodyAdviceAdapter {
    private static final JsonMapper STRICT=JsonMapper.builder(JsonFactory.builder()
            .enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION)
            .streamReadConstraints(StreamReadConstraints.builder().maxNestingDepth(8).build()).build())
            .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS).build();
    public static final class ApiFailure extends RuntimeException {
        private final int status;private final String code;
        public ApiFailure(int status,String code) {super("Appointment operation unavailable");this.status=status;this.code=code;}
    }
    public static void authenticated() {
        var a=SecurityContextHolder.getContext().getAuthentication();
        if(a==null || !a.isAuthenticated() || a instanceof AnonymousAuthenticationToken)throw new ApiFailure(401,"UNAUTHENTICATED");
    }
    public static <T> T selector(Supplier<T> create) {
        try{return create.get();}catch(InvalidOrganizationValueException|IllegalArgumentException e){throw new ApiFailure(400,"INVALID");}
    }
    @Override public boolean supports(MethodParameter p,Type type,Class<? extends HttpMessageConverter<?>> converter) {
        var t=p.getParameterType();return t==AssignEmployeeRequest.class || t.getEnclosingClass()==EmployeeAppointmentLifecycleRequest.class;
    }
    @Override public HttpInputMessage beforeBodyRead(HttpInputMessage input,MethodParameter p,Type type,
            Class<? extends HttpMessageConverter<?>> converter) throws IOException {
        byte[] bytes=input.getBody().readNBytes(16385);
        try {if(bytes.length>16384 || !STRICT.readTree(bytes).isObject())throw new IllegalArgumentException();}
        catch(RuntimeException failure){throw new ApiFailure(400,"INVALID");}
        return new HttpInputMessage() {
            @Override public InputStream getBody(){return new ByteArrayInputStream(bytes);}
            @Override public HttpHeaders getHeaders(){return input.getHeaders();}
        };
    }
    @ExceptionHandler(ApiFailure.class) public ResponseEntity<ProblemDetail> failure(ApiFailure e){return problem(e.status,e.code);}
    @ExceptionHandler(ProtectedTransactionExecutor.ExecutionFailure.class)
    public ResponseEntity<ProblemDetail> execution(ProtectedTransactionExecutor.ExecutionFailure e) {
        if(e.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE)return problem(503,"INDETERMINATE");
        boolean denied=false;boolean unavailable=false;
        for(Throwable c=e;c!=null;c=c.getCause()) {
            if(c instanceof EmployeeAppointmentResult.PaginationRequired)return problem(409,"PAGINATION_REQUIRED");
            if(c instanceof EmployeeAppointmentResult.Conflict || c instanceof InvalidOrganizationValueException)return problem(409,"CONFLICT");
            denied|=c instanceof AccessDeniedException;
            unavailable|=c instanceof java.sql.SQLException || c instanceof org.springframework.dao.DataAccessException;
        }
        return problem(denied && !unavailable?403:503,denied && !unavailable?"DENIED":"UNAVAILABLE");
    }
    @ExceptionHandler(AccessDeniedException.class) public ResponseEntity<ProblemDetail> denied(AccessDeniedException e) {
        var a=SecurityContextHolder.getContext().getAuthentication();
        return problem(a==null || !a.isAuthenticated() || a instanceof AnonymousAuthenticationToken?401:403,"DENIED");
    }
    @ExceptionHandler({HttpMessageNotReadableException.class,MissingServletRequestParameterException.class,MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception e){return problem(400,"INVALID");}
    private static ResponseEntity<ProblemDetail> problem(int status,String code) {
        var p=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status),"Appointment operation unavailable");
        p.setInstance(java.net.URI.create("urn:hidra:employee-appointment"));p.setProperty("code",code);
        if("PAGINATION_REQUIRED".equals(code))p.setProperty("paginationEndpoint","/api/v1/organization/assignments");
        return ResponseEntity.status(status).body(p);
    }
}
