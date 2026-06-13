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
import dz.sh.hidra.modules.notification.api.rest.response.NotificationMessageResponse;
import dz.sh.hidra.modules.notification.api.rest.response.NotificationRequestResponse;
import dz.sh.hidra.modules.notification.application.port.in.CreateNotificationMessageUseCase;
import dz.sh.hidra.modules.notification.application.port.in.ReceiveNotificationRequestUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing notification REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/notification")
public class SpringNotificationController implements NotificationController {

    private final ReceiveNotificationRequestUseCase receiveNotificationRequestUseCase;
    private final CreateNotificationMessageUseCase createNotificationMessageUseCase;

    public SpringNotificationController(
            ReceiveNotificationRequestUseCase receiveNotificationRequestUseCase,
            CreateNotificationMessageUseCase createNotificationMessageUseCase
    ) {
        this.receiveNotificationRequestUseCase = Objects.requireNonNull(receiveNotificationRequestUseCase, "ReceiveNotificationRequestUseCase must not be null.");
        this.createNotificationMessageUseCase = Objects.requireNonNull(createNotificationMessageUseCase, "CreateNotificationMessageUseCase must not be null.");
    }


    @Override
    @PostMapping("/receive-notification-request")
    public NotificationRequestResponse receiveNotificationRequest(@Valid @RequestBody ReceiveNotificationRequestRequest request) {
        Objects.requireNonNull(request, "ReceiveNotificationRequestRequest must not be null.");
        return NotificationRestMapper.toResponse(receiveNotificationRequestUseCase.receiveNotificationRequest(NotificationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/create-notification-message")
    public NotificationMessageResponse createNotificationMessage(@Valid @RequestBody CreateNotificationMessageRequest request) {
        Objects.requireNonNull(request, "CreateNotificationMessageRequest must not be null.");
        return NotificationRestMapper.toResponse(createNotificationMessageUseCase.createNotificationMessage(NotificationRestMapper.toCommand(request)));
    }

}
