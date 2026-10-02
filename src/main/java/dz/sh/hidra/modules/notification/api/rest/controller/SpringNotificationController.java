/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringNotificationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing notification REST endpoints.
 *
 */
package dz.sh.hidra.modules.notification.api.rest.controller;
import dz.sh.hidra.modules.notification.api.rest.mapper.NotificationRestMapper;
import dz.sh.hidra.modules.notification.api.rest.request.CreateNotificationMessageRequest;
import dz.sh.hidra.modules.notification.api.rest.request.ReceiveNotificationRequestRequest;
import dz.sh.hidra.modules.notification.api.rest.request.RecordDeliveryAttemptRequest;
import dz.sh.hidra.modules.notification.api.rest.response.NotificationDeliveryAttemptResponse;
import dz.sh.hidra.modules.notification.api.rest.response.NotificationMessageResponse;
import dz.sh.hidra.modules.notification.api.rest.response.NotificationRequestResponse;
import dz.sh.hidra.modules.notification.application.port.in.CreateNotificationMessageUseCase;
import dz.sh.hidra.modules.notification.application.port.in.ReceiveNotificationRequestUseCase;
import dz.sh.hidra.modules.notification.application.port.in.RecordDeliveryAttemptUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing notification REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/notification")
public class SpringNotificationController implements NotificationController {

    private final CreateNotificationMessageUseCase createNotificationMessageUseCase;
    private final ReceiveNotificationRequestUseCase receiveNotificationRequestUseCase;
    private final RecordDeliveryAttemptUseCase recordDeliveryAttemptUseCase;

    public SpringNotificationController(
            CreateNotificationMessageUseCase createNotificationMessageUseCase,
            ReceiveNotificationRequestUseCase receiveNotificationRequestUseCase,
            RecordDeliveryAttemptUseCase recordDeliveryAttemptUseCase
    ) {
        this.createNotificationMessageUseCase = Objects.requireNonNull(createNotificationMessageUseCase, "CreateNotificationMessageUseCase must not be null.");
        this.receiveNotificationRequestUseCase = Objects.requireNonNull(receiveNotificationRequestUseCase, "ReceiveNotificationRequestUseCase must not be null.");
        this.recordDeliveryAttemptUseCase = Objects.requireNonNull(recordDeliveryAttemptUseCase, "RecordDeliveryAttemptUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "notification",
                "mission", "Deliver asynchronous push notifications for operational events, workflows, and alerts.",
                "objectives", List.of(
                "Receive notification requests.",
                "Create notification messages.",
                "Record delivery attempts and evidence."
        ),
                "operations", List.of(
                "createNotificationMessage",
                "receiveNotificationRequest",
                "recordDeliveryAttempt"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/notification/messages",
                "POST /api/v1/notification/requests",
                "POST /api/v1/notification/delivery-attempts"
        )
        );
    }

    @Override
    @PostMapping({"/create-notification-message", "/messages"})
    public NotificationMessageResponse createNotificationMessage(@Valid @RequestBody CreateNotificationMessageRequest request) {
        Objects.requireNonNull(request, "CreateNotificationMessageRequest must not be null.");
        return NotificationRestMapper.toResponse(createNotificationMessageUseCase.createNotificationMessage(NotificationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/receive-notification-request", "/requests"})
    public NotificationRequestResponse receiveNotificationRequest(@Valid @RequestBody ReceiveNotificationRequestRequest request) {
        Objects.requireNonNull(request, "ReceiveNotificationRequestRequest must not be null.");
        return NotificationRestMapper.toResponse(receiveNotificationRequestUseCase.receiveNotificationRequest(NotificationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/record-delivery-attempt", "/delivery-attempts"})
    public NotificationDeliveryAttemptResponse recordDeliveryAttempt(@Valid @RequestBody RecordDeliveryAttemptRequest request) {
        Objects.requireNonNull(request, "RecordDeliveryAttemptRequest must not be null.");
        return NotificationRestMapper.toResponse(recordDeliveryAttemptUseCase.recordDeliveryAttempt(NotificationRestMapper.toCommand(request)));
    }

}
