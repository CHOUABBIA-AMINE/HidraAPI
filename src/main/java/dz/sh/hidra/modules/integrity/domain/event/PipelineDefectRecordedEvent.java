/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineDefectRecordedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.event
 *
 * @Description : Published when a pipeline defect is recorded.
 *
 */
package dz.sh.hidra.modules.integrity.domain.event;

import java.time.Instant;

/**
 * Published when a pipeline defect is recorded.
 */
public record PipelineDefectRecordedEvent(
        String eventId,
    String defectId,
    Instant occurredAt
) implements IntegrityDomainEvent {

    @Override
    public String eventType() {
        return "PipelineDefectRecordedEvent";
    }
}
