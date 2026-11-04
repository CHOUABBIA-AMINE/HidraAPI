/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationAdministrationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.controller
 *
 * @Description : Implements the bounded typed configuration administration contract.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.controller;

import dz.sh.hidra.modules.configuration.application.port.in.ConfigurationAdministrationUseCase;
import dz.sh.hidra.modules.configuration.api.rest.request.ConfigurationAdministrationRequests;
import dz.sh.hidra.modules.configuration.api.rest.response.ConfigurationAdministrationResponses.*;
import java.time.Instant;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/configuration")
public class ConfigurationAdministrationController {
    private final ConfigurationAdministrationUseCase service;
    public ConfigurationAdministrationController(ConfigurationAdministrationUseCase service){this.service=java.util.Objects.requireNonNull(service);}
    @GetMapping("/namespaces") public List<Choice> namespaces(@RequestParam(defaultValue="100") int limit,@RequestParam(defaultValue="0") int offset) {return service.namespaces(limit,offset).stream().map(Choice::from).toList();}
    @GetMapping("/definitions") public List<Definition> definitions(@RequestParam String namespaceId,@RequestParam(defaultValue="100") int limit,@RequestParam(defaultValue="0") int offset) {return service.definitions(namespaceId,limit,offset).stream().map(Definition::from).toList();}
    @GetMapping("/definitions/{id}") public Definition definition(@PathVariable String id) {return Definition.from(service.definition(id));}
    @PostMapping("/definitions/{id}/activate") public Definition activateDefinition(@PathVariable String id,@Valid @RequestBody ConfigurationAdministrationRequests.Activation request) {return Definition.from(service.activateDefinition(id,request.expectedUpdatedAt()));}
    @GetMapping("/values") public List<Value> values(@RequestParam String definitionId,@RequestParam String environment,@RequestParam(defaultValue="100") int limit,@RequestParam(defaultValue="0") int offset) {return service.values(definitionId,environment,limit,offset).stream().map(Value::from).toList();}
    @GetMapping("/effective") public Effective effective(@RequestParam String definitionId,@RequestParam String environment,@RequestParam Instant instant) {return Effective.from(service.effective(definitionId,environment,instant));}
    @GetMapping("/feature-flags") public List<Flag> flags(@RequestParam(defaultValue="100") int limit,@RequestParam(defaultValue="0") int offset) {return service.flags(limit,offset).stream().map(Flag::from).toList();}
    @PostMapping("/feature-flags/{id}/activate") public Flag activateFlag(@PathVariable String id,@Valid @RequestBody ConfigurationAdministrationRequests.Activation request) {return Flag.from(service.activateFlag(id,request.expectedUpdatedAt()));}
    @PutMapping("/feature-flags/{id}/state") public FlagState setFlagState(@PathVariable String id,@Valid @RequestBody ConfigurationAdministrationRequests.FlagState request) {return FlagState.from(service.setFlagState(id,request.environment(),request.enabled(),request.expectedUpdatedAt()));}
    @GetMapping("/feature-flags/{id}/state") public FlagState flagState(@PathVariable String id,@RequestParam String environment) {return FlagState.from(service.flagState(id,environment));}
    @org.springframework.core.annotation.Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
    @org.springframework.web.bind.annotation.RestControllerAdvice(assignableTypes={ConfigurationAdministrationController.class,SpringConfigurationController.class})
    public static class Errors {
        @ExceptionHandler(dz.sh.hidra.modules.configuration.domain.exception.ConfigurationDomainException.class)
        public org.springframework.http.ProblemDetail invalid() {
            return problem(org.springframework.http.HttpStatus.BAD_REQUEST,"CONFIGURATION_INVALID","Configuration violates the admitted value or ownership policy.");
        }
        @ExceptionHandler({IllegalStateException.class,org.springframework.dao.DataIntegrityViolationException.class})
        public org.springframework.http.ProblemDetail conflict() {
            return problem(org.springframework.http.HttpStatus.CONFLICT,"CONFIGURATION_CONFLICT","Configuration state or version conflicts with the request.");
        }
        private static org.springframework.http.ProblemDetail problem(org.springframework.http.HttpStatus status,String code,String message) {
            var p=org.springframework.http.ProblemDetail.forStatusAndDetail(status,message);p.setTitle(code);p.setProperty("code",code);return p;
        }
    }
}
