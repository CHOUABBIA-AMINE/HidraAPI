/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationRequestRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationRequest.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationRequestRepositoryPort;
import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import dz.sh.hidra.modules.notification.domain.model.NotificationRequest;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationRequestJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationRequest.
 */
@Component
public class JpaNotificationRequestRepositoryAdapter implements NotificationRequestRepositoryPort {

    private final NotificationRequestJpaRepository repository;

    public JpaNotificationRequestRepositoryAdapter(NotificationRequestJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationRequestJpaRepository must not be null.");
    }

    @Override
    public NotificationRequest save(NotificationRequest model) {
        validateReferences(model);
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationRequest> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }

    @Override
    public boolean isCatalogEntryInFamily(String entryId, String catalogName) {
        return entryId != null
                && catalogName != null
                && repository.isCatalogEntryInFamily(entryId, catalogName);
    }

    @Override
    public boolean policyExists(String policyId) {
        return policyId != null && repository.policyExists(policyId);
    }

    @Override
    public boolean templateExists(String templateId) {
        return templateId != null && repository.templateExists(templateId);
    }

    @Override
    public boolean templateVersionExists(String templateVersionId) {
        return templateVersionId != null && repository.templateVersionExists(templateVersionId);
    }

    private void validateReferences(NotificationRequest model) {
        Objects.requireNonNull(model, "NotificationRequest must not be null.");
        if (!isCatalogEntryInFamily(model.categoryId(), "NOTIFICATION_CATEGORY")) {
            throw new InvalidNotificationValueException(
                    "NotificationRequest category must reference NOTIFICATION_CATEGORY."
            );
        }
        if (model.priorityId() != null
                && !isCatalogEntryInFamily(model.priorityId(), "NOTIFICATION_PRIORITY")) {
            throw new InvalidNotificationValueException(
                    "NotificationRequest priority must reference NOTIFICATION_PRIORITY."
            );
        }
        if (model.policyId() != null && !policyExists(model.policyId())) {
            throw new InvalidNotificationValueException(
                    "NotificationRequest policy must reference an existing NotificationPolicy."
            );
        }
        if (model.templateId() != null && !templateExists(model.templateId())) {
            throw new InvalidNotificationValueException(
                    "NotificationRequest template must reference an existing NotificationTemplate."
            );
        }
        if (model.templateVersionId() != null && !templateVersionExists(model.templateVersionId())) {
            throw new InvalidNotificationValueException(
                    "NotificationRequest template version must reference an existing NotificationTemplateVersion."
            );
        }
    }
}
