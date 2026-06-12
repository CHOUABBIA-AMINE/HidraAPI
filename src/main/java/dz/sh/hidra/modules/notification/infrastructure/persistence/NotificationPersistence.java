/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence
 *
 * @Description : Notification database table constants.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence;

/**
 * Notification database table constants.
 */
public final class NotificationPersistence {

    public static final String NOTIFICATION_TEMPLATE_TABLE = "hidra_notification_template";
    public static final String NOTIFICATION_TEMPLATE_VERSION_TABLE = "hidra_notification_template_version";
    public static final String NOTIFICATION_TEMPLATE_TRANSLATION_TABLE = "hidra_notification_template_translation";
    public static final String NOTIFICATION_CHANNEL_TABLE = "hidra_notification_channel";
    public static final String NOTIFICATION_RECIPIENT_PROFILE_TABLE = "hidra_notification_recipient_profile";
    public static final String NOTIFICATION_CONTACT_POINT_TABLE = "hidra_notification_contact_point";
    public static final String NOTIFICATION_RECIPIENT_GROUP_TABLE = "hidra_notification_recipient_group";
    public static final String NOTIFICATION_RECIPIENT_GROUP_MEMBER_TABLE = "hidra_notification_recipient_group_member";
    public static final String NOTIFICATION_PREFERENCE_TABLE = "hidra_notification_preference";
    public static final String NOTIFICATION_POLICY_TABLE = "hidra_notification_policy";
    public static final String NOTIFICATION_REQUEST_TABLE = "hidra_notification_request";
    public static final String NOTIFICATION_REQUEST_RECIPIENT_TABLE = "hidra_notification_request_recipient";
    public static final String NOTIFICATION_MESSAGE_TABLE = "hidra_notification_message";
    public static final String NOTIFICATION_MESSAGE_VARIABLE_TABLE = "hidra_notification_message_variable";
    public static final String NOTIFICATION_BATCH_TABLE = "hidra_notification_batch";
    public static final String NOTIFICATION_DELIVERY_ATTEMPT_TABLE = "hidra_notification_delivery_attempt";
    public static final String NOTIFICATION_STATUS_HISTORY_TABLE = "hidra_notification_status_history";
    public static final String NOTIFICATION_RETRY_POLICY_TABLE = "hidra_notification_retry_policy";
    public static final String NOTIFICATION_SUPPRESSION_RULE_TABLE = "hidra_notification_suppression_rule";
    public static final String NOTIFICATION_SCHEDULE_TABLE = "hidra_notification_schedule";
    public static final String NOTIFICATION_ACKNOWLEDGEMENT_TABLE = "hidra_notification_acknowledgement";
    public static final String NOTIFICATION_EVIDENCE_LINK_TABLE = "hidra_notification_evidence_link";
    public static final String NOTIFICATION_CATALOG_ENTRY_TABLE = "hidra_notification_catalog_entry";
    public static final String NOTIFICATION_CATALOG_TRANSLATION_TABLE = "hidra_notification_catalog_translation";

    private NotificationPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
