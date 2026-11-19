/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedAuditEvidence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Defines exact T28 governed scoped Audit evidence.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** Immutable, minimized evidence; no Audit payload or source-owner aggregate crosses this boundary. */
public final class ScopedAuditEvidence {
    private ScopedAuditEvidence() {}
    public static final class LimitExceeded extends RuntimeException {
        public LimitExceeded(){super("Bounded Audit export limit exceeded.");}
    }
    public static final class Conflict extends RuntimeException {
        public Conflict(){super("Audit export request key already has different criteria.");}
    }
    public static final String EVENT_TYPE="RISK_ASSESSMENT_APPROVED";
    public static final String PURPOSE="GZ2_SYNTHETIC_ASSURANCE";
    public static final String FORMAT="GZ2_AUDIT_EVIDENCE_JSON_V1";
    public static final int MAX_ROWS=1000, MAX_BYTES=262144;
    private static String id(String text,int max) {
        if(text==null||text.isBlank()||text.length()>max||!text.equals(text.trim())
                ||text.chars().anyMatch(c->c<32))throw new IllegalArgumentException("Invalid bounded Audit identifier.");
        return text;
    }
    public record Scope(String companyId,String facilityId) {
        public Scope { id(companyId,96);id(facilityId,120); }
    }
    public record Window(String eventType,Instant from,Instant to) {
        public Window {
            if(!EVENT_TYPE.equals(eventType)||from==null||to==null
                    ||from.getNano()%1000!=0||to.getNano()%1000!=0
                    ||!from.isBefore(to)||Duration.between(from,to).compareTo(Duration.ofDays(31))>0)
                throw new IllegalArgumentException("Invalid bounded Audit time or event type.");
        }
    }
    public record Event(String eventId,String eventType,String operation,String targetId,
                        String workflowActionId,Instant occurredAt) {
        public Event {id(eventId,80);id(targetId,120);id(workflowActionId,120);
            Objects.requireNonNull(occurredAt); }
    }
    public record Boundary(Instant occurredAt,String eventId) {
        public Boundary {Objects.requireNonNull(occurredAt);id(eventId,80);}
        public String cursor(){
            return "v1."+Base64.getUrlEncoder().withoutPadding().encodeToString(
                    (occurredAt.toString()+"\n"+eventId).getBytes(StandardCharsets.UTF_8));
        }
        public static Boundary parse(String cursor) {
            if(cursor==null)return null;
            if(cursor.length()>240||!cursor.matches("v1\\.[A-Za-z0-9_-]{1,230}"))
                throw new IllegalArgumentException("Invalid Audit cursor.");
            try {
                String value=new String(Base64.getUrlDecoder().decode(cursor.substring(3)),StandardCharsets.UTF_8);
                int p=value.indexOf('\n');
                if(p<1||value.indexOf('\n',p+1)!=-1)throw new IllegalArgumentException();
                Boundary b=new Boundary(Instant.parse(value.substring(0,p)),value.substring(p+1));
                if(!b.cursor().equals(cursor))throw new IllegalArgumentException();
                return b;
            }catch(RuntimeException ex){throw new IllegalArgumentException("Invalid Audit cursor.");}
        }
    }
    public record Page(List<Event> items,String nextCursor) {
        public Page{items=List.copyOf(items);}
    }
    public record Criteria(Scope scope,Window window,String requestKey,String purpose) {
        public Criteria {
            Objects.requireNonNull(scope);Objects.requireNonNull(window);
            id(requestKey,80);
            if(!PURPOSE.equals(purpose))throw new IllegalArgumentException("Unsupported Audit export purpose.");
        }
    }
    public record Manifest(UUID id,Scope scope,Window window,String purpose,String format,
                           String requester,int eventCount,int byteLength,String sha256,
                           UUID receiptId,Instant completedAt) {
        public Manifest {
            Objects.requireNonNull(id);Objects.requireNonNull(scope);Objects.requireNonNull(window);
            Objects.requireNonNull(completedAt);Objects.requireNonNull(receiptId);
            if(!PURPOSE.equals(purpose)||!FORMAT.equals(format)||eventCount<0||eventCount>MAX_ROWS
                    ||byteLength<0||byteLength>MAX_BYTES||sha256==null||!sha256.matches("[0-9a-f]{64}"))
                throw new IllegalArgumentException("Invalid frozen Audit manifest.");
        }
    }
    public record Artifact(Manifest manifest,byte[] bytes) {
        public Artifact{bytes=bytes.clone();}
        @Override public byte[] bytes(){return bytes.clone();}
    }
    public record ExportResult(Manifest manifest,boolean created){}
    public record ContentResult(int status,Artifact artifact) {
        public static ContentResult denied(){return new ContentResult(404,null);}
        public static ContentResult corrupt(){return new ContentResult(503,null);}
        public static ContentResult allowed(Artifact a){return new ContentResult(200,a);}
    }
}
