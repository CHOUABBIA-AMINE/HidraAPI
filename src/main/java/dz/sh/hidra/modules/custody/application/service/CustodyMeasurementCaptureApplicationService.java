/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyMeasurementCaptureApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.service
 * @Description : Coordinates signed scoped capture and immutable qualified custody receipts.
 */
package dz.sh.hidra.modules.custody.application.service;

import dz.sh.hidra.modules.custody.application.port.in.CustodyMeasurementCaptureUseCase;
import dz.sh.hidra.modules.custody.application.port.out.CustodyMeasurementCapturePort;
import dz.sh.hidra.modules.custody.domain.service.CustodyMeasurementCapturePolicy;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyMeasurementScopedAuthorizationContract;
import dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyIntervalEvidenceContract;
import dz.sh.hidra.modules.topology.application.contract.custody.CustodyMeasurementAssetOwnershipContract;
import dz.sh.hidra.modules.party.application.contract.custody.CustodyMeasurementPartyReferenceContract;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CustodyMeasurementCaptureApplicationService implements CustodyMeasurementCaptureUseCase {
    private static final String CAPTURE="custody:measurements:capture";
    private static final String READ="custody:measurements:read";
    private final CustodyMeasurementCapturePort storage;
    private final CustodyIntervalEvidenceContract telemetry;
    private final CustodyMeasurementAssetOwnershipContract ownership;
    private final CustodyMeasurementPartyReferenceContract parties;
    private final CustodyMeasurementScopedAuthorizationContract authorization;
    public CustodyMeasurementCaptureApplicationService(CustodyMeasurementCapturePort storage,
            CustodyIntervalEvidenceContract telemetry,CustodyMeasurementAssetOwnershipContract ownership,
            CustodyMeasurementPartyReferenceContract parties,
            CustodyMeasurementScopedAuthorizationContract authorization) {
        this.storage=storage;this.telemetry=telemetry;this.ownership=ownership;
        this.parties=parties;this.authorization=authorization;
    }
    @Override @Transactional
    public Receipt capture(String periodId,Command cmd) {
        CustodyMeasurementCapturePolicy.required(periodId);
        Objects.requireNonNull(cmd,"capture command");
        if(cmd.commandId()==null || cmd.productPublicationId()==null)
            throw new CustodyMeasurementCapturePolicy.Invalid("Exact command and product publication are required.");
        CustodyMeasurementCapturePolicy.required(cmd.meterRunSnapshotId());
        CustodyMeasurementCapturePolicy.required(cmd.measurementTypeId());
        CustodyMeasurementCapturePolicy.required(cmd.agreementPartyId());
        CustodyMeasurementCapturePolicy.required(cmd.unitId());
        CustodyMeasurementCapturePolicy.required(cmd.intervalEvidenceId());
        var scope=storage.scope(periodId).orElseThrow(CustodyMeasurementCaptureApplicationService::unavailable);
        String company=ownership.activeOwner(scope.assetType(),scope.assetId())
                .orElseThrow(CustodyMeasurementCaptureApplicationService::unavailable);
        authorization.require(CAPTURE,company,scope.transferPointId());
        String actor=authorization.actor();
        String hash=fingerprint(actor,periodId,cmd.commandId(),cmd.meterRunSnapshotId(),
            cmd.measurementTypeId(),cmd.productPublicationId(),cmd.agreementPartyId(),
            cmd.unitId(),cmd.intervalEvidenceId());
        var replay=storage.byCommand(periodId,cmd.commandId());
        if(replay.isPresent()) {
            var previous=replay.get();
            if(!previous.fingerprint().equals(hash) || !actor.equals(previous.receipt().actorId())
                    || !company.equals(previous.receipt().companyId())
                    || !scope.transferPointId().equals(previous.receipt().transferPointId()))
                throw new CustodyMeasurementCapturePolicy.Conflict("Command ID already belongs to different capture.");
            return previous.receipt().replayed();
        }
        var ctx=storage.lockContext(periodId,cmd.meterRunSnapshotId(),cmd.agreementPartyId())
                .orElseThrow(()->new CustodyMeasurementCapturePolicy.Invalid("Capture references are not currently eligible."));
        if(!scope.transferPointId().equals(ctx.transferPointId())
                || !scope.assetType().equals(ctx.companyAssetType())
                || !scope.assetId().equals(ctx.facilityId()))
            throw unavailable();
        if(!"FACILITY".equals(ctx.companyAssetType()))
            throw new CustodyMeasurementCapturePolicy.Invalid("Only an active facility scoped meter is supported.");
        if(!parties.active(ctx.partyId(),ctx.partyRoleId()))
            throw new CustodyMeasurementCapturePolicy.Invalid("Agreement party is not active.");
        if(!storage.measurementTypeActive(cmd.measurementTypeId()))
            throw new CustodyMeasurementCapturePolicy.Invalid("Measurement type is not active.");
        if(!storage.currentProduct(ctx,company,cmd.productPublicationId()))
            throw new CustodyMeasurementCapturePolicy.Invalid("Current product publication is ineligible.");
        var attestation=telemetry.qualified(cmd.intervalEvidenceId(),ctx.meterRunId(),
                ctx.meteringSystemId(),ctx.companyAssetType(),ctx.facilityId())
                .orElseThrow(()->new CustodyMeasurementCapturePolicy.Invalid("Qualified interval attestation unavailable."));
        if(!cmd.unitId().equals(attestation.unitId()))
            throw new CustodyMeasurementCapturePolicy.Invalid("Claimed unit does not match Telemetry.");
        CustodyMeasurementCapturePolicy.value(attestation.quantity());
        CustodyMeasurementCapturePolicy.interval(attestation.start(),attestation.end(),
                ctx.periodStart(),ctx.periodEnd());
        CustodyMeasurementCapturePolicy.effective(attestation.start(),attestation.end(),
                ctx.agreementStart(),ctx.agreementEnd());
        CustodyMeasurementCapturePolicy.effective(attestation.start(),attestation.end(),
                ctx.transferStart(),ctx.transferEnd());
        CustodyMeasurementCapturePolicy.effective(attestation.start(),attestation.end(),
                ctx.meterStart(),ctx.meterEnd());
        CustodyMeasurementCapturePolicy.effective(attestation.start(),attestation.end(),
                ctx.partyStart(),ctx.partyEnd());
        if(storage.sourceExists(periodId,ctx.meterRunId(),cmd.measurementTypeId(),attestation.id()))
            throw new CustodyMeasurementCapturePolicy.Conflict("This qualified interval is already captured.");
        var receipt=new Receipt(UUID.randomUUID().toString(),periodId,cmd.commandId(),
            ctx.meterRunId(),ctx.meteringSystemId(),cmd.measurementTypeId(),cmd.productPublicationId(),
            ctx.agreementPartyId(),company,ctx.transferPointId(),ctx.companyAssetType(),ctx.facilityId(),
            attestation.trustedReadingId(),attestation.rawReadingId(),attestation.assessmentId(),
            attestation.pointId(),attestation.bindingId(),attestation.id(),attestation.quantity(),
            attestation.unitId(),"OBSERVED",attestation.start(),attestation.end(),actor,
            Instant.now().truncatedTo(ChronoUnit.MICROS),false,false);
        storage.append(receipt,hash);
        return receipt;
    }
    @Override @Transactional
    public Receipt read(String periodId,String measurementId){
        CustodyMeasurementCapturePolicy.required(periodId);
        CustodyMeasurementCapturePolicy.required(measurementId);
        var r=storage.read(periodId,measurementId).orElseThrow(CustodyMeasurementCaptureApplicationService::unavailable);
        String owner=ownership.activeOwner(r.assetType(),r.assetId())
                .orElseThrow(CustodyMeasurementCaptureApplicationService::unavailable);
        if(!owner.equals(r.companyId()))throw unavailable();
        authorization.require(READ,owner,r.transferPointId());
        return r;
    }
    private static NoSuchElementException unavailable() {
        return new NoSuchElementException("Custody measurement unavailable.");
    }
    private static String fingerprint(Object... values){
        try {
            MessageDigest md=MessageDigest.getInstance("SHA-256");
            for(Object value:values){
                if(value==null){md.update((byte)0);continue;}
                md.update((byte)1);
                var bytes=value.toString().getBytes(StandardCharsets.UTF_8);
                md.update(ByteBuffer.allocate(4).putInt(bytes.length).array());
                md.update(bytes);
            }
            return HexFormat.of().formatHex(md.digest());
        } catch(NoSuchAlgorithmException e){throw new IllegalStateException("SHA-256 unavailable",e);}
    }
}
