/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeApiExceptionHandlerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringLocalExchangeController;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import java.util.*;
import org.junit.jupiter.api.*;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.junit.jupiter.api.Assertions.*;

class EngineeringLocalExchangeApiExceptionHandlerTest {
    private final EngineeringLocalExchangeApiExceptionHandler handler=new EngineeringLocalExchangeApiExceptionHandler();
    @AfterEach void clear(){SecurityContextHolder.clearContext();}
    @Test void fixedErrorsDoNotExposeSelectorsOrExceptionMessages(){
        var p=handler.invalid(new IllegalArgumentException("private-company-secret")).getBody();
        assertEquals("urn:hidra:engineering-local-exchange",p.getInstance().toString());assertFalse(p.toString().contains("private-company-secret"));
        assertEquals("INVALID",p.getProperties().get("code"));
    }
    @Test void mapsNativeConflictsDenialsAndIndeterminateCompletionExactly(){
        for(var reason:EngineeringNetworkDraftFailure.Reason.values()){
            int expected=switch(reason){case DENIED->403;case NOT_FOUND->404;case CONFLICT->409;case UNAVAILABLE,INDETERMINATE->503;};
            assertEquals(expected,handler.nativeFailure(new EngineeringNetworkDraftFailure(reason)).getStatusCode().value());
        }
        assertThrows(EngineeringLocalExchangeApiExceptionHandler.Unauthenticated.class,EngineeringLocalExchangeApiExceptionHandler::authenticated);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("fixture",null,List.of()));
        assertDoesNotThrow(EngineeringLocalExchangeApiExceptionHandler::authenticated);
    }
    @Test void adviceAndBodyBoundsApplyOnlyToTheExchangeController(){
        assertArrayEquals(new Class<?>[]{EngineeringLocalExchangeController.class},EngineeringLocalExchangeApiExceptionHandler.class.getAnnotation(RestControllerAdvice.class).assignableTypes());
        var method=Arrays.stream(EngineeringLocalExchangeController.class.getMethods()).filter(m->m.getName().equals("dryRun")).findFirst().orElseThrow();
        assertTrue(handler.supports(new MethodParameter(method,method.getParameterCount()-1),byte[].class,ByteArrayHttpMessageConverter.class));
        assertFalse(handler.supports(new MethodParameter(method,0),String.class,ByteArrayHttpMessageConverter.class));
        assertEquals(25165824,EngineeringLocalExchangeApiExceptionHandler.MAX_BODY_BYTES);
    }
}
