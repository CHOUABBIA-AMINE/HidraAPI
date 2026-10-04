/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationTemplateRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationTemplate.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationTemplateRepositoryPort;
import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import dz.sh.hidra.modules.notification.domain.model.NotificationTemplate;
import dz.sh.hidra.modules.notification.domain.value.NotificationTemplateStatus;
import dz.sh.hidra.modules.notification.domain.value.NotificationTemplateVersionStatus;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationCatalogEntryJpaRepository;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationChannelJpaRepository;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationTemplateJpaRepository;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationTemplateVersionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationTemplate.
 */
@Component
public class JpaNotificationTemplateRepositoryAdapter implements NotificationTemplateRepositoryPort {

    private final NotificationTemplateJpaRepository repository;
    private final NotificationTemplateVersionJpaRepository versionRepository;
    private final NotificationCatalogEntryJpaRepository catalogRepository;
    private final NotificationChannelJpaRepository channelRepository;

    public JpaNotificationTemplateRepositoryAdapter(
            NotificationTemplateJpaRepository repository,
            NotificationTemplateVersionJpaRepository versionRepository,
            NotificationCatalogEntryJpaRepository catalogRepository,
            NotificationChannelJpaRepository channelRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "NotificationTemplateJpaRepository must not be null.");
        this.versionRepository = Objects.requireNonNull(
                versionRepository,
                "NotificationTemplateVersionJpaRepository must not be null."
        );
        this.catalogRepository = Objects.requireNonNull(
                catalogRepository,
                "NotificationCatalogEntryJpaRepository must not be null."
        );
        this.channelRepository = Objects.requireNonNull(
                channelRepository,
                "NotificationChannelJpaRepository must not be null."
        );
    }

    @Override
    public NotificationTemplate save(NotificationTemplate model) {
        Objects.requireNonNull(model, "NotificationTemplate must not be null.");
        if (repository.existsByCodeAndIdNot(model.code(), model.id())) {
            throw new InvalidNotificationValueException("NotificationTemplate code must be unique.");
        }
        requireCatalogFamily(model.templateTypeId(), "TEMPLATE_TYPE", "template type");
        if (model.categoryId() != null) {
            requireCatalogFamily(model.categoryId(), "NOTIFICATION_CATEGORY", "category");
        }
        if (model.defaultChannelId() != null) {
            var channel = channelRepository.findById(model.defaultChannelId())
                    .orElseThrow(() -> new InvalidNotificationValueException(
                            "NotificationTemplate default channel must reference an existing NotificationChannel."
                    ));
            if (!channel.active()) {
                throw new InvalidNotificationValueException(
                        "NotificationTemplate default channel must reference an ACTIVE NotificationChannel."
                );
            }
        }
        validateCurrentVersion(model);
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationTemplate> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByCode(String code) {
        return code != null && !code.isBlank() && repository.existsByCode(code.trim());
    }

    private void requireCatalogFamily(String id, String family, String label) {
        var entry = catalogRepository.findById(id)
                .orElseThrow(() -> new InvalidNotificationValueException(
                        "NotificationTemplate " + label + " must reference an existing notification catalog entry."
                ));
        if (!family.equals(entry.catalogName())) {
            throw new InvalidNotificationValueException(
                    "NotificationTemplate " + label + " must belong to catalog family " + family + "."
            );
        }
    }

    private void validateCurrentVersion(NotificationTemplate model) {
        if (model.currentVersion() == null) {
            return;
        }
        boolean valid = model.status() == NotificationTemplateStatus.ACTIVE
                ? versionRepository.existsByTemplateIdAndVersionNumberAndStatus(
                        model.id(),
                        model.currentVersion(),
                        NotificationTemplateVersionStatus.ACTIVE
                )
                : versionRepository.existsByTemplateIdAndVersionNumber(
                        model.id(),
                        model.currentVersion()
                );
        if (!valid) {
            throw new InvalidNotificationValueException(
                    "NotificationTemplate current version must belong to the template"
                            + (model.status() == NotificationTemplateStatus.ACTIVE
                            ? " and be ACTIVE."
                            : ".")
            );
        }
    }
}
