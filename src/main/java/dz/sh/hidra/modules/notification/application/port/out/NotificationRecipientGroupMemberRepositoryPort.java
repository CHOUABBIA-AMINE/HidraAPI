/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRecipientGroupMemberRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Repository port for NotificationRecipientGroupMember.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.NotificationRecipientGroupMember;

import java.util.Optional;

/**
 * Repository port for NotificationRecipientGroupMember.
 */
public interface NotificationRecipientGroupMemberRepositoryPort {

    NotificationRecipientGroupMember save(NotificationRecipientGroupMember model);

    Optional<NotificationRecipientGroupMember> findById(String id);
}
