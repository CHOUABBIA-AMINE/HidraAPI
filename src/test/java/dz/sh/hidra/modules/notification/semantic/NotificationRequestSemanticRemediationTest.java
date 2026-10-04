/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRequestSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Notification Test
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.semantic
 *
 * @Description : Verifies HMR-035 NotificationRequest semantic enforcement.
 *
 */
package dz.sh.hidra.modules.notification.semantic;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import dz.sh.hidra.modules.notification.domain.model.NotificationRequest;
import dz.sh.hidra.modules.notification.domain.value.NotificationRequestStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NotificationRequestSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void requiresSourceModuleAndSourceEventType() {
        assertThatThrownBy(() -> request(null, "ALARM_RAISED"))
                .isInstanceOf(InvalidNotificationValueException.class)
                .hasMessageContaining("source module");

        assertThatThrownBy(() -> request("alarms", " "))
                .isInstanceOf(InvalidNotificationValueException.class)
                .hasMessageContaining("source event type");
    }

    @Test
    void migrationProtectsCatalogFamiliesAndOwnedReferences() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_035__hmr_035_notification_notification_request.sql"
        ));

        assertThat(sql)
                .contains("NOTIFICATION_CATEGORY")
                .contains("NOTIFICATION_PRIORITY")
                .contains("REFERENCES hidra_notification_policy (id)")
                .contains("REFERENCES hidra_notification_template (id)")
                .contains("REFERENCES hidra_notification_template_version (id)")
                .doesNotContain("FOREIGN KEY (template_version_id, template_id)");
    }

    private static NotificationRequest request(String sourceModule, String sourceEventType) {
        return new NotificationRequest(
                "request-1",
                sourceModule,
                sourceEventType,
                "event-1",
                null,
                null,
                null,
                null,
                "category-1",
                null,
                null,
                null,
                null,
                null,
                null,
                NOW,
                null,
                null,
                NotificationRequestStatus.RECEIVED,
                null,
                NOW,
                NOW
        );
    }
}
