/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : WorkflowInboxApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.service
 * @Description : Transactional assignment inbox, recipient keyset reads and immutable acknowledgement use cases.
 */
package dz.sh.hidra.modules.notification.application.service;

import dz.sh.hidra.modules.notification.application.contract.reporting.ReportReviewTaskInboxContract;
import dz.sh.hidra.modules.notification.application.port.in.WorkflowInboxUseCase;
import dz.sh.hidra.modules.notification.application.port.out.WorkflowInboxAccessPort;
import dz.sh.hidra.modules.notification.application.port.out.WorkflowInboxRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.WorkflowInboxItem;
import dz.sh.hidra.modules.notification.domain.model.WorkflowInboxItem.Scope;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowInboxApplicationService implements WorkflowInboxUseCase,ReportReviewTaskInboxContract {
    private static final int DEFAULT_LIMIT=25,MAX_LIMIT=100;
    private final WorkflowInboxRepositoryPort repository;
    private final WorkflowInboxAccessPort access;
    public WorkflowInboxApplicationService(WorkflowInboxRepositoryPort repository,WorkflowInboxAccessPort access) {
        this.repository=Objects.requireNonNull(repository); this.access=Objects.requireNonNull(access);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void assigned(Assignment event) {
        Objects.requireNonNull(event);
        var item=new WorkflowInboxItem(UUID.randomUUID().toString(),
                WorkflowInboxItem.EVENT,WorkflowInboxItem.MESSAGE,
                event.companyId(),event.facilityId(),event.recipientActorId(),
                event.workflowTaskId(),event.assignedAt(),Instant.now().truncatedTo(ChronoUnit.MICROS),null);
        repository.deliver(item);
    }
    @Override @Transactional(readOnly=true)
    public Page list(Scope scope,Integer requestedLimit,String opaqueCursor) {
        String actor=access.require(scope,WorkflowInboxAccessPort.Action.READ);
        int limit=requestedLimit==null?DEFAULT_LIMIT:requestedLimit;
        if (limit<1 || limit>MAX_LIMIT) throw new IllegalArgumentException("Invalid inbox page limit.");
        var before=decode(opaqueCursor);
        List<WorkflowInboxItem> rows=repository.list(scope,actor,limit+1,before);
        boolean more=rows.size()>limit;
        List<WorkflowInboxItem> visible=more?List.copyOf(rows.subList(0,limit)):List.copyOf(rows);
        return new Page(visible,more?encode(visible.getLast()):null);
    }
    @Override @Transactional(readOnly=true)
    public WorkflowInboxItem detail(Scope scope,String notificationId) {
        String actor=access.require(scope,WorkflowInboxAccessPort.Action.READ);
        return repository.detail(scope,actor,WorkflowInboxItem.uuid(notificationId),false)
                .orElseThrow(WorkflowInboxApplicationService::hidden);
    }
    @Override @Transactional
    public WorkflowInboxItem acknowledge(Scope scope,String notificationId) {
        String actor=access.require(scope,WorkflowInboxAccessPort.Action.READ);
        if (!actor.equals(access.require(scope,WorkflowInboxAccessPort.Action.ACKNOWLEDGE))) throw hidden();
        String id=WorkflowInboxItem.uuid(notificationId);
        repository.detail(scope,actor,id,true).orElseThrow(WorkflowInboxApplicationService::hidden);
        repository.acknowledge(scope,actor,id);
        return repository.detail(scope,actor,id,false).orElseThrow(WorkflowInboxApplicationService::hidden);
    }
    private static String encode(WorkflowInboxItem item) {
        String tuple="1|"+item.deliveredAt()+"|"+item.id();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tuple.getBytes(StandardCharsets.UTF_8));
    }
    private static WorkflowInboxRepositoryPort.Cursor decode(String cursor) {
        if (cursor==null) return null;
        if (cursor.isBlank() || cursor.length()>192 || !cursor.matches("[A-Za-z0-9_-]+"))
            throw new IllegalArgumentException("Invalid inbox cursor.");
        try {
            byte[] value=Base64.getUrlDecoder().decode(cursor);
            if (value.length>128 || !Base64.getUrlEncoder().withoutPadding().encodeToString(value).equals(cursor))
                throw new IllegalArgumentException("Noncanonical inbox cursor.");
            String[] tuple=new String(value,StandardCharsets.UTF_8).split("\\|",-1);
            if (tuple.length!=3 || !"1".equals(tuple[0])) throw new IllegalArgumentException("Cursor version");
            Instant at=Instant.parse(tuple[1]);
            if (!at.equals(at.truncatedTo(ChronoUnit.MICROS))
                    || !at.toString().equals(tuple[1])) throw new IllegalArgumentException("Cursor precision");
            return new WorkflowInboxRepositoryPort.Cursor(at,WorkflowInboxItem.uuid(tuple[2]));
        } catch (RuntimeException invalid) {
            throw new IllegalArgumentException("Invalid inbox cursor.",invalid);
        }
    }
    private static NoSuchElementException hidden() {
        return new NoSuchElementException("Protected inbox unavailable.");
    }
}
