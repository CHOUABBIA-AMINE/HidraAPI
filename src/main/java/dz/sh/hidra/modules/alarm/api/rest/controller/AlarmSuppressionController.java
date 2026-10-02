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

    public AlarmSuppressionController(
            ManageAlarmSuppressionUseCase manageUseCase,
            AlarmSuppressionQueryUseCase queryUseCase,
            CurrentActorResolver actorResolver
    ) {
        this.manageUseCase = Objects.requireNonNull(manageUseCase);
        this.queryUseCase = Objects.requireNonNull(queryUseCase);
        this.actorResolver = Objects.requireNonNull(actorResolver);
    }

    @PostMapping
    public AlarmSuppressionDto create(
            @Valid @RequestBody CreateSuppressionRequest request,
            @RequestHeader(name = "X-Correlation-Id", required = false) String correlationId
    ) {
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
    public AlarmSuppressionDto release(
            @PathVariable String suppressionId,
            @RequestHeader(name = "X-Correlation-Id", required = false) String correlationId
    ) {
        return manageUseCase.releaseSuppression(new ReleaseAlarmSuppressionCommand(
                suppressionId,
                actorResolver.currentActorId().value(),
                correlationId
        ));
    }

    @GetMapping("/{suppressionId}")
    public AlarmSuppressionDto suppression(@PathVariable String suppressionId) {
        return queryUseCase.suppression(suppressionId);
    }

    @GetMapping
    public AlarmSuppressionPageDto suppressions(
            @RequestParam(required = false) String suppressionId,
            @RequestParam(required = false) AlarmSuppressionScopeType scopeType,
            @RequestParam(required = false) String scopeReferenceId,
            @RequestParam(required = false) String alarmId,
            @RequestParam(required = false) AlarmSuppressionStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.suppressions(new AlarmSuppressionQuery(
                suppressionId, scopeType, scopeReferenceId, alarmId, status, page, size
        ));
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
