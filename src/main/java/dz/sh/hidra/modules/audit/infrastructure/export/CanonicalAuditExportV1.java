/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CanonicalAuditExportV1
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.export
 *
 * @Description : Bounded T28 governed Risk approval scoped Audit evidence.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.export;

import dz.sh.hidra.modules.audit.application.port.out.AuditExportEncodingPort;
import dz.sh.hidra.modules.audit.domain.model.ScopedAuditEvidence;
import dz.sh.hidra.modules.audit.domain.model.ScopedAuditEvidence.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

/** Explicit JSON field order; no arbitrary Audit payload, user or request metadata. */
@Component
public final class CanonicalAuditExportV1 implements AuditExportEncodingPort {
    private static final JsonMapper JSON=JsonMapper.builder().build();
    @Override public byte[] encode(Criteria criteria,List<Event> ordered) {
        if(ordered.size()>ScopedAuditEvidence.MAX_ROWS)
            throw new ScopedAuditEvidence.LimitExceeded();
        var root=new LinkedHashMap<String,Object>();
        root.put("format",ScopedAuditEvidence.FORMAT);
        root.put("purpose",ScopedAuditEvidence.PURPOSE);
        root.put("companyId",criteria.scope().companyId());
        root.put("facilityId",criteria.scope().facilityId());
        root.put("eventType",criteria.window().eventType());
        root.put("from",criteria.window().from().toString());
        root.put("to",criteria.window().to().toString());
        root.put("eventCount",ordered.size());
        root.put("events",ordered.stream().map(event -> {
            var item=new LinkedHashMap<String,Object>();
            item.put("eventId",event.eventId());
            item.put("eventType",event.eventType());
            item.put("operation",event.operation());
            item.put("targetId",event.targetId());
            item.put("workflowActionId",event.workflowActionId());
            item.put("occurredAt",event.occurredAt().toString());
            return item;
        }).toList());
        byte[] output=(JSON.writeValueAsString(root)+"\n").getBytes(StandardCharsets.UTF_8);
        if(output.length>ScopedAuditEvidence.MAX_BYTES)
            throw new ScopedAuditEvidence.LimitExceeded();
        return output;
    }
}
