/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmCatalogValidation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.validation
 *
 * @Description : Enforces Alarm-owned transactional lifecycle evidence and integrity.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.validation;

import dz.sh.hidra.modules.alarm.domain.model.Alarm;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmCatalogEntryJpaRepository;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class AlarmCatalogValidation {
    private final AlarmCatalogEntryJpaRepository entries;
    public AlarmCatalogValidation(AlarmCatalogEntryJpaRepository entries) {this.entries = Objects.requireNonNull(entries);}
    public void validate(Alarm alarm) {
        requireFamily(alarm.alarmTypeId(), "ALARM_TYPE");
        requireFamily(alarm.severityId(), "ALARM_SEVERITY");
        if (alarm.priorityId() != null) requireFamily(alarm.priorityId(), "ALARM_PRIORITY");
    }
    public void requireShelvingReason(String id) {requireFamily(id, "SHELVING_REASON");}
    public void requireFamily(String id, String family) {
        var entry = entries.findByIdForShare(id)
                .orElseThrow(() -> new IllegalArgumentException("Unknown Alarm catalog entry: " + id));
        if (!family.equals(entry.catalogName())) throw new IllegalArgumentException("Alarm catalog family must be " + family);
    }
}
