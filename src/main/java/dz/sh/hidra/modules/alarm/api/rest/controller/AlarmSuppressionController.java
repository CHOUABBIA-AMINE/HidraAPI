/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.controller
 *
 * @Description : Publishes governed Alarm suppression create, release, detail, and query REST contracts.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.controller;

import dz.sh.hidra.modules.alarm.application.command.CreateAlarmSuppressionCommand;
import dz.sh.hidra.modules.alarm.application.command.ReleaseAlarmSuppressionCommand;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionDto;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmResourceAuthorizationPort;
import dz.sh.hidra.modules.alarm.domain.exception.AlarmResourceNotFoundException;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionPageDto;
import dz.sh.hidra.modules.alarm.application.port.in.AlarmSuppressionQueryUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.ManageAlarmSuppressionUseCase;
import dz.sh.hidra.modules.alarm.application.query.AlarmSuppressionQuery;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;
import dz.sh.hidra.platform.security.CurrentActorResolver;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;
import java.util.NoSuchElementException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alarm/suppressions")
public final class AlarmSuppressionController {

    private final ManageAlarmSuppressionUseCase manageUseCase;
    private final AlarmSuppressionQueryUseCase queryUseCase;
    private final CurrentActorResolver actorResolver;
    private final AlarmResourceAuthorizationPort scopedAuthorization;

    public AlarmSuppressionController(
            ManageAlarmSuppressionUseCase manageUseCase,
            AlarmSuppressionQueryUseCase queryUseCase,
            CurrentActorResolver actorResolver,
            AlarmResourceAuthorizationPort scopedAuthorization
    ) {
        this.manageUseCase = Objects.requireNonNull(manageUseCase);
        this.queryUseCase = Objects.requireNonNull(queryUseCase);
        this.actorResolver = Objects.requireNonNull(actorResolver);
        this.scopedAuthorization = Objects.requireNonNull(scopedAuthorization);
    }

    @PostMapping
    @org.springframework.transaction.annotation.Transactional(isolation = org.springframework.transaction.annotation.Isolation.SERIALIZABLE)
    public AlarmSuppressionDto create(
            @Valid @RequestBody CreateSuppressionRequest request,
            @RequestHeader(name = "X-Correlation-Id", required = false) String correlationId
    ) {
        if(request.scopeType()!=AlarmSuppressionScopeType.ALARM||request.alarmId()==null
                ||!request.alarmId().equals(request.scopeReferenceId()))throw new AlarmResourceNotFoundException();
        scopedAuthorization.requireAlarm("alarm:suppressions:execute",request.alarmId());
        return manageUseCase.createSuppression(new CreateAlarmSuppressionCommand(
                request.scopeType(),
                request.scopeReferenceId(),
                request.alarmId(),
                request.alarmTypeId(),
                request.topologyAssetTypeCode(),
                request.topologyAssetId(),
                request.suppressionReasonId(),
                request.reasonText(),
                actorResolver.currentActorId().value(),
                request.suppressedUntil(),
                request.workflowInstanceId(),
                correlationId
        ));
    }

    @PostMapping("/{suppressionId}/release")
    @org.springframework.transaction.annotation.Transactional(isolation = org.springframework.transaction.annotation.Isolation.SERIALIZABLE)
    public AlarmSuppressionDto release(
            @PathVariable String suppressionId,
            @RequestHeader(name = "X-Correlation-Id", required = false) String correlationId
    ) {
        checkedSuppression(suppressionId,"alarm:suppressions:execute");
        return manageUseCase.releaseSuppression(new ReleaseAlarmSuppressionCommand(
                suppressionId,
                actorResolver.currentActorId().value(),
                correlationId
        ));
    }

    @GetMapping("/{suppressionId}")
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public AlarmSuppressionDto suppression(@PathVariable String suppressionId) {
        return checkedSuppression(suppressionId,"alarm:suppressions:read");
    }

    @GetMapping
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public AlarmSuppressionPageDto suppressions(
            @RequestParam(required = false) String suppressionId,
            @RequestParam(required = false) AlarmSuppressionScopeType scopeType,
            @RequestParam(required = false) String scopeReferenceId,
            @RequestParam(required = false) String alarmId,
            @RequestParam(required = false) AlarmSuppressionStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        String selected=alarmId!=null&&!alarmId.isBlank()?alarmId:
                scopeType==AlarmSuppressionScopeType.ALARM?scopeReferenceId:null;
        if(selected==null||selected.isBlank()||(scopeType!=null&&scopeType!=AlarmSuppressionScopeType.ALARM)
                ||(scopeReferenceId!=null&&!scopeReferenceId.equals(selected)))
            return new AlarmSuppressionPageDto(List.of(),Math.max(0,page),Math.min(200,Math.max(1,size)),0,0,false);
        scopedAuthorization.requireAlarm("alarm:suppressions:read",selected);
        return queryUseCase.suppressions(new AlarmSuppressionQuery(
                suppressionId,AlarmSuppressionScopeType.ALARM,selected,selected,status,page,size
        ));
    }

    private AlarmSuppressionDto checkedSuppression(String suppressionId,String permission) {
        try {
            var evidence=queryUseCase.suppression(suppressionId);
            requireAlarmScoped(permission,evidence);
            return evidence;
        } catch(NoSuchElementException absent) {
            throw new AlarmResourceNotFoundException();
        }
    }
    private void requireAlarmScoped(String permission,AlarmSuppressionDto evidence) {
        if(evidence==null||evidence.scopeType()!=AlarmSuppressionScopeType.ALARM
                ||evidence.alarmId()==null||!evidence.alarmId().equals(evidence.scopeReferenceId()))
            throw new AlarmResourceNotFoundException();
        scopedAuthorization.requireAlarm(permission,evidence.alarmId());
    }

    public record CreateSuppressionRequest(
            @NotNull AlarmSuppressionScopeType scopeType,
            @NotBlank String scopeReferenceId,
            String alarmId,
            String alarmTypeId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            @NotBlank String suppressionReasonId,
            String reasonText,
            Instant suppressedUntil,
            String workflowInstanceId
    ) { }
}
