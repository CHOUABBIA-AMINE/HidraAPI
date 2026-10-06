/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDeliveryAttemptSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Notification Test
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.semantic
 *
 * @Description : Verifies create-only delivery evidence and permanent-failure retry policy.
 *
 */
package dz.sh.hidra.modules.notification.semantic;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import dz.sh.hidra.modules.notification.domain.model.NotificationDeliveryAttempt;
import dz.sh.hidra.modules.notification.domain.model.NotificationMessage;
import dz.sh.hidra.modules.notification.domain.value.DeliveryAttemptStatus;
import dz.sh.hidra.modules.notification.domain.value.NotificationMessageStatus;
import dz.sh.hidra.modules.notification.infrastructure.persistence.adapter.JpaNotificationDeliveryAttemptRepositoryAdapter;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationDeliveryAttemptJpaRepository;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationMessageJpaRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class NotificationDeliveryAttemptSemanticRemediationTest {
    private static final Instant NOW = Instant.parse("2026-10-06T00:00:00Z");
    private final NotificationDeliveryAttemptJpaRepository repository = mock(NotificationDeliveryAttemptJpaRepository.class);
    private final NotificationMessageJpaRepository messages = mock(NotificationMessageJpaRepository.class);
    private final EntityManager entities = mock(EntityManager.class);
    private final JpaNotificationDeliveryAttemptRepositoryAdapter adapter =
            new JpaNotificationDeliveryAttemptRepositoryAdapter(repository, messages, entities);

    @Test
    void rejectsAutomaticRetryForPermanentFailureAndCancellation() {
        for (var status : new DeliveryAttemptStatus[]{DeliveryAttemptStatus.FAILED_PERMANENT, DeliveryAttemptStatus.CANCELLED}) {
            assertThatThrownBy(() -> attempt(status, NOW.plusSeconds(60)))
                .isInstanceOf(InvalidNotificationValueException.class).hasMessageContaining("automatic retry");
            assertThat(attempt(status, null).permanentFailure()).isTrue();
        }
        assertThat(attempt(DeliveryAttemptStatus.FAILED_TEMPORARY, NOW.plusSeconds(60)).nextRetryAt()).isNotNull();
    }
    @Test
    void rejectsMissingMessageAndMismatchedChannelBeforePersist() {
        when(messages.findById("message")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> adapter.save(attempt(DeliveryAttemptStatus.SENT, null)))
            .isInstanceOf(InvalidNotificationValueException.class).hasMessageContaining("existing message");
        when(messages.findById("message")).thenReturn(Optional.of(NotificationPersistenceMapper.toEntity(message("other"))));
        assertThatThrownBy(() -> adapter.save(attempt(DeliveryAttemptStatus.SENT, null)))
            .isInstanceOf(InvalidNotificationValueException.class).hasMessageContaining("channel");
        verifyNoInteractions(entities);
    }
    @Test
    void rejectsExistingAttemptInsteadOfMerging() {
        when(messages.findById("message")).thenReturn(Optional.of(NotificationPersistenceMapper.toEntity(message("channel"))));
        when(repository.existsById("attempt")).thenReturn(true);
        assertThatThrownBy(() -> adapter.save(attempt(DeliveryAttemptStatus.SENT, null)))
            .isInstanceOf(InvalidNotificationValueException.class).hasMessageContaining("append-only");
        verifyNoInteractions(entities);
        verify(repository, never()).save(any());
    }
    @Test
    void persistsAndFlushesNewAttemptWithoutGenericSave() {
        when(messages.findById("message")).thenReturn(Optional.of(NotificationPersistenceMapper.toEntity(message("channel"))));
        var attempt = attempt(DeliveryAttemptStatus.SENT, null);
        assertThat(adapter.save(attempt)).isEqualTo(attempt);
        var order = inOrder(entities);
        order.verify(entities).persist(any()); order.verify(entities).flush();
        verify(repository, never()).save(any()); verify(entities, never()).merge(any());
    }
    private NotificationDeliveryAttempt attempt(DeliveryAttemptStatus status, Instant retry) {
        return new NotificationDeliveryAttempt("attempt", "message", 1, "channel", "provider", null,
            status, NOW, null, null, null, retry, null, NOW);
    }
    private NotificationMessage message(String channel) {
        return new NotificationMessage("message", "request", "recipient", channel, null, null, null,
            null, "manual", null, null, null, NotificationMessageStatus.READY, null, null, NOW, NOW);
    }
}
