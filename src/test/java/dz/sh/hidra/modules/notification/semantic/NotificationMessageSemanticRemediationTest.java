/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationMessageSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Notification Test
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.semantic
 *
 * @Description : Verifies composition validation before persistence or dispatch.
 *
 */
package dz.sh.hidra.modules.notification.semantic;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import dz.sh.hidra.modules.notification.domain.model.NotificationMessage;
import dz.sh.hidra.modules.notification.domain.value.NotificationMessageStatus;
import dz.sh.hidra.modules.notification.infrastructure.persistence.adapter.JpaNotificationMessageRepositoryAdapter;
import dz.sh.hidra.modules.notification.infrastructure.persistence.entity.NotificationMessageJpaEntity;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationMessageJpaRepository;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class NotificationMessageSemanticRemediationTest {
    @Test
    void rejectsInvalidCompositionBeforeAnyWrite() {
        var repository = mock(NotificationMessageJpaRepository.class);
        var adapter = new JpaNotificationMessageRepositoryAdapter(repository);
        assertThatThrownBy(() -> adapter.save(message()))
                .isInstanceOf(InvalidNotificationValueException.class);
        verify(repository).hasValidComposition("message", "request", "recipient", null, null, null, "READY");
        verify(repository, never()).saveAndFlush(any());
    }

    @Test
    void flushesValidMessageBeforeReturningToDispatchCaller() {
        var repository = mock(NotificationMessageJpaRepository.class);
        when(repository.hasValidComposition("message", "request", "recipient", null, null, null, "READY"))
                .thenReturn(true);
        when(repository.saveAndFlush(any(NotificationMessageJpaEntity.class))).thenAnswer(i -> i.getArgument(0));
        var saved = new JpaNotificationMessageRepositoryAdapter(repository).save(message());
        assertThat(saved).isEqualTo(message());
        var order = inOrder(repository);
        order.verify(repository).hasValidComposition("message", "request", "recipient", null, null, null, "READY");
        order.verify(repository).saveAndFlush(any());
    }

    @Test
    void rejectedReadyMessageNeverReachesAsyncPush() {
        var repository = mock(NotificationMessageJpaRepository.class);
        var push = mock(dz.sh.hidra.modules.notification.application.port.out.NotificationAsyncPushPort.class);
        var service = new dz.sh.hidra.modules.notification.application.service.NotificationApplicationService(
                mock(dz.sh.hidra.modules.notification.application.port.out.NotificationRequestRepositoryPort.class),
                new JpaNotificationMessageRepositoryAdapter(repository),
                mock(dz.sh.hidra.modules.notification.application.port.out.NotificationDeliveryAttemptRepositoryPort.class), push);
        var command = new dz.sh.hidra.modules.notification.application.command.CreateNotificationMessageCommand(
                "request", "recipient", "channel", "template", "version", null, null, "rendered",
                null, null, null, null, null);
        assertThatThrownBy(() -> service.createNotificationMessage(command))
                .isInstanceOf(InvalidNotificationValueException.class);
        verifyNoInteractions(push);
    }

    private NotificationMessage message() {
        var now = Instant.parse("2026-10-06T00:00:00Z");
        return new NotificationMessage("message", "request", "recipient", "channel", null, null,
                null, null, "manual content", null, null, null, NotificationMessageStatus.READY,
                null, null, now, now);
    }
}
