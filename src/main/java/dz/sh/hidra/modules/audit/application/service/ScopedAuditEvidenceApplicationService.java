/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedAuditEvidenceApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.service
 *
 * @Description : Transactional governed scoped Audit search, export and access evidence.
 *
 */
package dz.sh.hidra.modules.audit.application.service;

import dz.sh.hidra.modules.audit.application.port.in.ScopedAuditEvidenceUseCase;
import dz.sh.hidra.modules.audit.application.port.out.*;
import dz.sh.hidra.modules.audit.domain.model.ScopedAuditEvidence;
import dz.sh.hidra.modules.audit.domain.model.ScopedAuditEvidence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public final class ScopedAuditEvidenceApplicationService implements ScopedAuditEvidenceUseCase {
    private final ScopedAuditEvidenceRepositoryPort repository;
    private final ScopedAuditEvidenceAccessPort access;
    private final AuditExportEncodingPort encoder;
    public ScopedAuditEvidenceApplicationService(ScopedAuditEvidenceRepositoryPort repository,
            ScopedAuditEvidenceAccessPort access,AuditExportEncodingPort encoder) {
        this.repository=Objects.requireNonNull(repository);
        this.access=Objects.requireNonNull(access);
        this.encoder=Objects.requireNonNull(encoder);
    }
    private static String hash(byte[] bytes) {
        try {return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));}
        catch(NoSuchAlgorithmException error){throw new IllegalStateException("SHA-256 unavailable.");}
    }
    private static String locator(Scope scope,UUID exportId) {
        return hash((scope.companyId()+"\u001f"+scope.facilityId()+"\u001f"+exportId)
                .getBytes(StandardCharsets.UTF_8));
    }
    private String admit(Scope scope,boolean export) {
        String actor=access.nativeActor();
        if(!access.permitted(actor,scope,export))
            throw new NoSuchElementException("Protected Audit evidence unavailable.");
        return actor;
    }
    @Override @Transactional
    public Page search(Scope scope,Window window,Integer limit,String cursor) {
        Objects.requireNonNull(scope);Objects.requireNonNull(window);
        int count=limit==null?25:limit;
        if(count<1||count>100)throw new IllegalArgumentException("Invalid Audit page size.");
        Boundary after=Boundary.parse(cursor);
        String actor=admit(scope,false);
        List<Event> scan=repository.events(scope,window,after,count+1);
        boolean more=scan.size()>count;
        List<Event> items=more?List.copyOf(scan.subList(0,count)):List.copyOf(scan);
        String next=more?new Boundary(items.getLast().occurredAt(),items.getLast().eventId()).cursor():null;
        String digest=hash((scope.companyId()+"\u001f"+scope.facilityId()+"\u001f"
                +window.eventType()+"\u001f"+window.from()+"\u001f"+window.to()+"\u001f"
                +count+"\u001f"+(cursor==null?"":cursor)).getBytes(StandardCharsets.UTF_8));
        repository.receipt(scope,actor,"SEARCH","ALLOW",null,digest,UUID.randomUUID());
        return new Page(items,next);
    }
    @Override @Transactional
    public ExportResult createExport(Criteria criteria) {
        Objects.requireNonNull(criteria);
        String actor=admit(criteria.scope(),true);
        repository.lockRequest(criteria,actor);
        Optional<Manifest> existing=repository.existing(criteria,actor);
        if(existing.isPresent()) {
            Manifest original=existing.get();
            if(!criteria.scope().equals(original.scope())
                    ||!criteria.window().equals(original.window())
                    ||!criteria.purpose().equals(original.purpose()))
                throw new ScopedAuditEvidence.Conflict();
            repository.receipt(criteria.scope(),actor,"CREATE","ALLOW",original.id(),
                    locator(criteria.scope(),original.id()),UUID.randomUUID());
            return new ExportResult(original,false);
        }
        List<Event> events=repository.events(criteria.scope(),criteria.window(),null,
                ScopedAuditEvidence.MAX_ROWS+1);
        if(events.size()>ScopedAuditEvidence.MAX_ROWS)throw new ScopedAuditEvidence.LimitExceeded();
        byte[] bytes=encoder.encode(criteria,events);
        UUID receipt=UUID.randomUUID();
        Manifest frozen=repository.store(criteria,actor,bytes,events.size(),hash(bytes),receipt);
        repository.receipt(criteria.scope(),actor,"CREATE","ALLOW",frozen.id(),
                locator(criteria.scope(),frozen.id()),receipt);
        return new ExportResult(frozen,true);
    }
    @Override @Transactional
    public Manifest exportManifest(Scope scope,UUID exportId) {
        Objects.requireNonNull(exportId);
        String actor=admit(scope,true);
        Manifest manifest=repository.manifest(scope,actor,exportId)
                .orElseThrow(()->new NoSuchElementException("Protected Audit export unavailable."));
        repository.receipt(scope,actor,"MANIFEST","ALLOW",null,locator(scope,exportId),
                UUID.randomUUID());
        return manifest;
    }
    /** Return typed outcomes instead of throwing expected denial across the evidence transaction. */
    @Override @Transactional
    public ContentResult download(Scope scope,UUID exportId) {
        Objects.requireNonNull(exportId);
        String actor=access.nativeActor();
        String digest=locator(scope,exportId);
        if(!access.permitted(actor,scope,true)) {
            repository.receipt(scope,actor,"DOWNLOAD","DENY",null,digest,UUID.randomUUID());
            return ContentResult.denied();
        }
        Optional<Artifact> item=repository.artifact(scope,actor,exportId);
        if(item.isEmpty()) {
            repository.receipt(scope,actor,"DOWNLOAD","DENY",null,digest,UUID.randomUUID());
            return ContentResult.denied();
        }
        Artifact artifact=item.get();
        byte[] bytes=artifact.bytes();
        boolean valid=bytes.length==artifact.manifest().byteLength()
                &&hash(bytes).equals(artifact.manifest().sha256());
        if(!valid) {
            repository.receipt(scope,actor,"DOWNLOAD","INTEGRITY_FAILURE",exportId,digest,UUID.randomUUID());
            return ContentResult.corrupt();
        }
        repository.receipt(scope,actor,"DOWNLOAD","ALLOW",exportId,digest,UUID.randomUUID());
        return ContentResult.allowed(artifact);
    }
}
