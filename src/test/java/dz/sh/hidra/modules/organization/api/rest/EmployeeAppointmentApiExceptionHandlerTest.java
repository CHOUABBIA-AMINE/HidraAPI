/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentApiExceptionHandlerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest
 *
 * @Description : Verifies fixed appointment error envelopes and strict advice scope.
 *
 */
package dz.sh.hidra.modules.organization.api.rest;


import dz.sh.hidra.modules.organization.api.rest.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.*;
class EmployeeAppointmentApiExceptionHandlerTest {
    @Test void admittedConflictsAndUnknownCompletionHaveFixedDistinctStatuses() {
        var advice=new EmployeeAppointmentApiExceptionHandler();
        var failure=org.mockito.Mockito.mock(dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor.ExecutionFailure.class);
        org.mockito.Mockito.when(failure.outcome()).thenReturn(dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT);
        org.mockito.Mockito.when(failure.getCause()).thenReturn(new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.Conflict());
        assertEquals(409,advice.execution(failure).getStatusCode().value());
        org.mockito.Mockito.when(failure.getCause()).thenReturn(new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.PaginationRequired());
        assertEquals(409,advice.execution(failure).getStatusCode().value());
        org.mockito.Mockito.when(failure.getCause()).thenReturn(new AccessDeniedException("private selector"));
        assertEquals(403,advice.execution(failure).getStatusCode().value());
        org.mockito.Mockito.when(failure.getCause()).thenReturn(new java.sql.SQLException("private storage details"));
        assertEquals(503,advice.execution(failure).getStatusCode().value());
        org.mockito.Mockito.when(failure.outcome()).thenReturn(dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor.Outcome.INDETERMINATE);
        assertEquals(503,advice.execution(failure).getStatusCode().value());
    }
    @Test void fixedErrorsNeverIncludeExceptionDetailsOrProtectedSelectors() {
        var advice=new EmployeeAppointmentApiExceptionHandler();
        for(int status:new int[]{400,401,403,409,503}) {
            var response=advice.failure(new EmployeeAppointmentApiExceptionHandler.ApiFailure(status,"FIXED"));
            assertEquals(status,response.getStatusCode().value());assertEquals("Appointment operation unavailable",response.getBody().getDetail());
        }
        assertFalse(advice.invalid(new IllegalArgumentException("private company selector")).getBody().getDetail().contains("company"));
        SecurityContextHolder.clearContext();assertEquals(401,advice.denied(new AccessDeniedException("private grant")).getStatusCode().value());
    }
    @Test void adviceIsLimitedToTheThreeRegisteredControllers() {
        var types=EmployeeAppointmentApiExceptionHandler.class.getAnnotation(RestControllerAdvice.class).assignableTypes();
        assertEquals(java.util.Set.of(EmployeeAppointmentLifecycleController.class,SpringOrganizationController.class,OrganizationAdministrationQueryController.class),java.util.Set.of(types));
        assertEquals(0,EmployeeAppointmentApiExceptionHandler.class.getAnnotation(RestControllerAdvice.class).basePackages().length);
    }
}
