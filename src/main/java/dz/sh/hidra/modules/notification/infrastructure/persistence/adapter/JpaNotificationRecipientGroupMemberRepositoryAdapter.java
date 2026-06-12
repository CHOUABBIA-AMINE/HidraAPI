/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationRecipientGroupMemberRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationRecipientGroupMember.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationRecipientGroupMemberRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationRecipientGroupMember;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationRecipientGroupMemberJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationRecipientGroupMember.
 */
@Component
public class JpaNotificationRecipientGroupMemberRepositoryAdapter implements NotificationRecipientGroupMemberRepositoryPort {

    private final NotificationRecipientGroupMemberJpaRepository repository;

    public JpaNotificationRecipientGroupMemberRepositoryAdapter(NotificationRecipientGroupMemberJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationRecipientGroupMemberJpaRepository must not be null.");
    }

    @Override
    public NotificationRecipientGroupMember save(NotificationRecipientGroupMember model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationRecipientGroupMember> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
