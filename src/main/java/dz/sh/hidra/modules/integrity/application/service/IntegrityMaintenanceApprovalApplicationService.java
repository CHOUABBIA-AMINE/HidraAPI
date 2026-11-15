/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityMaintenanceApprovalApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.service
 * @Description : T18 native-scoped Integrity/Assets governed work-order operations.
 */
package dz.sh.hidra.modules.integrity.application.service;

import dz.sh.hidra.modules.integrity.application.port.in.IntegrityMaintenanceApprovalUseCase;
import dz.sh.hidra.modules.integrity.application.port.out.IntegrityMaintenanceApprovalStorePort;
import dz.sh.hidra.modules.integrity.application.contract.assets.ApprovedMaintenanceHandoffContract;
import dz.sh.hidra.modules.integrity.domain.service.IntegrityMaintenanceApprovalGuard;
import dz.sh.hidra.modules.identity.application.contract.integrity.IntegrityMaintenanceScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.integrity.IntegrityMaintenanceTopologyOwnershipContract;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HexFormat;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IntegrityMaintenanceApprovalApplicationService
        implements IntegrityMaintenanceApprovalUseCase,ApprovedMaintenanceHandoffContract {
    private final IntegrityMaintenanceApprovalStorePort store;
    private final IntegrityMaintenanceScopedAuthorizationContract identity;
    private final IntegrityMaintenanceTopologyOwnershipContract topology;
    private final IntegrityMaintenanceApprovalGuard guard=new IntegrityMaintenanceApprovalGuard();
    public IntegrityMaintenanceApprovalApplicationService(IntegrityMaintenanceApprovalStorePort store,
            IntegrityMaintenanceScopedAuthorizationContract identity,
            IntegrityMaintenanceTopologyOwnershipContract topology){
        this.store=Objects.requireNonNull(store);this.identity=Objects.requireNonNull(identity);
        this.topology=Objects.requireNonNull(topology);
    }
    @Override @Transactional
    public View approve(String findingId,Command c){
        Objects.requireNonNull(c);
        String finding=IntegrityMaintenanceApprovalGuard.required(findingId,80);
        String cmd=IntegrityMaintenanceApprovalGuard.required(c.commandId(),80);
        String recommendation=IntegrityMaintenanceApprovalGuard.required(c.recommendationId(),80);
        String caseId=IntegrityMaintenanceApprovalGuard.required(c.caseId(),80);
        String reason=IntegrityMaintenanceApprovalGuard.required(c.reason(),1000);
        var source=store.lockSource(finding,recommendation,caseId);
        String company=currentCompany(source.assetType(),source.assetId());
        identity.require("integrity:maintenance:approve",company,source.assetType(),source.assetId());
        String actor=identity.currentActor();
        String digest=digest(cmd,recommendation,caseId,finding,reason);
        var replay=store.byCommand(cmd);
        if(replay.isPresent()){
            var old=replay.orElseThrow();
            if(!old.commandDigest().equals(digest)||!old.approverId().equals(actor)
                    ||!old.findingId().equals(finding))throw new IllegalStateException("Integrity approval command conflict.");
            return view(old,true);
        }
        guard.requireEligible(source.runStatus(),source.recommendationStatus(),
                source.caseStatus(),source.findingDefect(),source.recommendationDefect(),
                source.caseDefect(),source.defectId(),source.findingId(),source.defectFinding(),
                source.runType(),source.runAsset(),source.findingType(),source.findingAsset(),
                source.defectType(),source.defectAsset(),source.caseType(),source.caseAsset());
        return view(store.approve(source,company,actor,reason,cmd,digest,Instant.now()),false);
    }
    @Override @Transactional
    public View read(String findingId,String approvalId) {
        var a=store.byId(IntegrityMaintenanceApprovalGuard.required(approvalId,80))
                .orElseThrow(()->new NoSuchElementException("Protected Integrity approval unavailable."));
        if(!a.findingId().equals(findingId))throw new NoSuchElementException("Protected Integrity approval unavailable.");
        String company=currentCompany(a.assetType(),a.assetId());
        if(!company.equals(a.companyId()))throw new NoSuchElementException("Protected Integrity approval unavailable.");
        identity.require("integrity:maintenance:read",company,a.assetType(),a.assetId());
        return view(a,false);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Approved current(String approvalId) {
        var a=store.byId(IntegrityMaintenanceApprovalGuard.required(approvalId,80))
                .orElseThrow(()->new NoSuchElementException("Protected Integrity approval unavailable."));
        var s=store.lockApprovedSource(a.recommendationId(),a.findingId(),a.caseId());
        if(!"APPROVED".equals(s.recommendationStatus())
                ||!Objects.equals(a.defectId(),s.defectId())
                ||!Objects.equals(a.assetType(),s.assetType())
                ||!Objects.equals(a.assetId(),s.assetId())
                ||!Objects.equals(a.sourceVersion(),s.sourceVersion())
                ||!a.companyId().equals(currentCompany(a.assetType(),a.assetId())))
            throw new NoSuchElementException("Integrity handoff has stale source evidence.");
        return new Approved(a.approvalId(),a.recommendationId(),a.findingId(),
                a.caseId(),a.defectId(),a.assetType(),a.assetId(),a.companyId(),
                s.title(),a.approverId(),a.sourceVersion());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<String> approvedRecommendation(String recommendationId) {
        if(recommendationId==null||recommendationId.isBlank())return Optional.empty();
        return store.byRecommendation(recommendationId).map(IntegrityMaintenanceApprovalStorePort.Approval::approvalId);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void link(String approvalId,String workOrderId) {
        current(approvalId);
        store.link(approvalId,workOrderId);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void recordVerification(String approvalId,String workOrderId,String executionId,
            String verificationId,String actorId,String verdict,String evidence,Instant at) {
        current(approvalId);
        store.recordVerification(approvalId,workOrderId,executionId,verificationId,actorId,verdict,evidence,at);
    }
    private String currentCompany(String type,String id){
        return topology.currentOwner(type,id).map(IntegrityMaintenanceTopologyOwnershipContract.Owned::companyId)
                .orElseThrow(()->new NoSuchElementException("Protected Integrity asset unavailable."));
    }
    private static View view(IntegrityMaintenanceApprovalStorePort.Approval a,boolean replay){
        return new View(a.approvalId(),a.findingId(),a.caseId(),a.defectId(),
                a.recommendationId(),a.assetType(),a.assetId(),a.companyId(),a.approverId(),
                a.approvedAt(),a.reason(),a.sourceVersion(),a.workOrderId(),
                a.verificationId(),a.verificationStatus(),replay);
    }
    private static String digest(String... parts){
        StringBuilder b=new StringBuilder();
        for(String p:parts)b.append(p.length()).append(':').append(p);
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(b.toString().getBytes(StandardCharsets.UTF_8)));}
        catch(NoSuchAlgorithmException e){throw new IllegalStateException(e);}
    }
}
