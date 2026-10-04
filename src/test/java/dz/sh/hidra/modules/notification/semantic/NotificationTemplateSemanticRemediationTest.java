/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTemplateSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Notification Test
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.semantic
 *
 * @Description : Verifies HMR-012 NotificationTemplate semantic remediation.
 *
 */
package dz.sh.hidra.modules.notification.semantic;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import dz.sh.hidra.modules.notification.domain.model.NotificationTemplate;
import dz.sh.hidra.modules.notification.domain.value.NotificationChannelType;
import dz.sh.hidra.modules.notification.domain.value.NotificationTemplateStatus;
import dz.sh.hidra.modules.notification.domain.value.NotificationTemplateVersionStatus;
import dz.sh.hidra.modules.notification.infrastructure.persistence.adapter.JpaNotificationTemplateRepositoryAdapter;
import dz.sh.hidra.modules.notification.infrastructure.persistence.entity.NotificationCatalogEntryJpaEntity;
import dz.sh.hidra.modules.notification.infrastructure.persistence.entity.NotificationChannelJpaEntity;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationCatalogEntryJpaRepository;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationChannelJpaRepository;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationTemplateJpaRepository;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationTemplateVersionJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NotificationTemplateSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void rejectsMissingFrenchNameTimestampsAndActiveCurrentVersion() {
        assertThatThrownBy(() -> template(" ", NotificationTemplateStatus.DRAFT, null, NOW, NOW, null))
                .isInstanceOf(InvalidNotificationValueException.class)
                .hasMessageContaining("French name");

        assertThatThrownBy(() -> template("Modèle", NotificationTemplateStatus.DRAFT, null, null, NOW, null))
                .isInstanceOf(InvalidNotificationValueException.class)
                .hasMessageContaining("createdAt");

        assertThatThrownBy(() -> template("Modèle", NotificationTemplateStatus.ACTIVE, null, NOW, NOW, null))
                .isInstanceOf(InvalidNotificationValueException.class)
                .hasMessageContaining("current version");
    }

    @Test
    void repositoryRejectsDuplicateCodeAndWrongCatalogFamily() {
        var templateRepository = mock(NotificationTemplateJpaRepository.class);
        var versionRepository = mock(NotificationTemplateVersionJpaRepository.class);
        var catalogRepository = mock(NotificationCatalogEntryJpaRepository.class);
        var channelRepository = mock(NotificationChannelJpaRepository.class);
        var adapter = new JpaNotificationTemplateRepositoryAdapter(
                templateRepository, versionRepository, catalogRepository, channelRepository
        );
        var model = template("Modèle", NotificationTemplateStatus.DRAFT, null, NOW, NOW, null);

        when(templateRepository.existsByCodeAndIdNot("TPL-1", "template-1")).thenReturn(true);
        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidNotificationValueException.class)
                .hasMessageContaining("unique");
        verify(templateRepository, never()).save(any());

        when(templateRepository.existsByCodeAndIdNot("TPL-1", "template-1")).thenReturn(false);
        when(catalogRepository.findById("type-1")).thenReturn(Optional.of(
                new NotificationCatalogEntryJpaEntity(
                        "type-1", "NOTIFICATION_CATEGORY", "TYPE", true, 1, true, NOW, NOW
                )
        ));
        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidNotificationValueException.class)
                .hasMessageContaining("TEMPLATE_TYPE");
    }

    @Test
    void activeTemplateRequiresActiveCurrentVersionAndActiveDefaultChannel() {
        var templateRepository = mock(NotificationTemplateJpaRepository.class);
        var versionRepository = mock(NotificationTemplateVersionJpaRepository.class);
        var catalogRepository = mock(NotificationCatalogEntryJpaRepository.class);
        var channelRepository = mock(NotificationChannelJpaRepository.class);
        var adapter = new JpaNotificationTemplateRepositoryAdapter(
                templateRepository, versionRepository, catalogRepository, channelRepository
        );
        var model = template("Modèle", NotificationTemplateStatus.ACTIVE, 2, NOW, NOW, "channel-1");

        when(catalogRepository.findById("type-1")).thenReturn(Optional.of(
                new NotificationCatalogEntryJpaEntity(
                        "type-1", "TEMPLATE_TYPE", "TYPE", true, 1, true, NOW, NOW
                )
        ));
        when(channelRepository.findById("channel-1")).thenReturn(Optional.of(
                new NotificationChannelJpaEntity(
                        "channel-1", "EMAIL", null, "Courriel", null,
                        NotificationChannelType.EMAIL, false, null,
                        true, false, true, true, null, NOW, NOW
                )
        ));

        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidNotificationValueException.class)
                .hasMessageContaining("ACTIVE NotificationChannel");

        when(channelRepository.findById("channel-1")).thenReturn(Optional.of(
                new NotificationChannelJpaEntity(
                        "channel-1", "EMAIL", null, "Courriel", null,
                        NotificationChannelType.EMAIL, true, null,
                        true, false, true, true, null, NOW, NOW
                )
        ));
        when(versionRepository.existsByTemplateIdAndVersionNumberAndStatus(
                "template-1", 2, NotificationTemplateVersionStatus.ACTIVE
        )).thenReturn(false);

        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidNotificationValueException.class)
                .hasMessageContaining("current version");
    }

    @Test
    void migrationEnforcesSelectionAndReferenceIntegrity() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_012__hmr_012_notification_notification_template.sql"
        ));

        assertThat(sql).contains("uk_hmr012_notification_template_code");
        assertThat(sql).contains("uk_hmr012_notification_template_version_number");
        assertThat(sql).contains("catalog_name = 'TEMPLATE_TYPE'");
        assertThat(sql).contains("catalog_name = 'NOTIFICATION_CATEGORY'");
        assertThat(sql).contains("default_channel_id must reference an ACTIVE channel");
        assertThat(sql).contains("trg_hmr012_notification_request_template_selection");
        assertThat(sql).contains("trg_hmr012_notification_message_template_selection");
        assertThat(sql).contains("A message using a template must reference the exact template version");
    }

    private static NotificationTemplate template(
            String nameFr,
            NotificationTemplateStatus status,
            Integer currentVersion,
            Instant createdAt,
            Instant updatedAt,
            String defaultChannelId
    ) {
        return new NotificationTemplate(
                "template-1",
                "TPL-1",
                null,
                nameFr,
                null,
                "type-1",
                null,
                defaultChannelId,
                status,
                currentVersion,
                true,
                createdAt,
                updatedAt
        );
    }
}
