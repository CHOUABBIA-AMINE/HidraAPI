/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingNativeScopeAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.security
 *
 * @Description : Joins locked authoritative Telemetry references with current Topology and Identity grants.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.security;


import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.Asset;
import dz.sh.hidra.modules.telemetry.application.port.out.AssetScopedTrustedObservationAuthorizationPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingScopeAuthorizationPort;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyRegionalAccessContract;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingIngestionReferencePort.Scope;
import dz.sh.hidra.modules.identity.application.contract.telemetry.TelemetryScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.telemetry.TelemetryTopologyOwnershipContract;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public final class TelemetryReadingNativeScopeAdapter implements TelemetryReadingScopeAuthorizationPort,
        AssetScopedTrustedObservationAuthorizationPort {
    private final TelemetryTopologyOwnershipContract topology;
    private final TelemetryScopedAuthorizationContract identity;
    private final TopologyRegionalAccessContract regional;
    private final TransactionTemplate readAuthorization;

    public TelemetryReadingNativeScopeAdapter(TelemetryTopologyOwnershipContract topology,
            TelemetryScopedAuthorizationContract identity, TopologyRegionalAccessContract regional,
            PlatformTransactionManager manager) {
        this.topology = Objects.requireNonNull(topology);
        this.identity = Objects.requireNonNull(identity);
        this.regional = Objects.requireNonNull(regional);
        this.readAuthorization = new TransactionTemplate(manager);
    }
    @Override
    public boolean permitsAsset(Asset asset) {
        var kind = switch (asset.type()) {
            case FACILITY -> TopologyRegionalAccessContract.ResourceKind.FACILITY;
            case EQUIPMENT -> TopologyRegionalAccessContract.ResourceKind.EQUIPMENT;
        };
        return Boolean.TRUE.equals(readAuthorization.execute(status ->
                regional.permitted(new TopologyRegionalAccessContract.Request(kind, asset.id(), asset.companyPartyId()))
                && topology.currentOwner(asset.type().name(), asset.id())
                        .filter(owner -> owner.companyPartyId().equals(asset.companyPartyId())
                                && owner.typeCode().equals(asset.type().name()) && owner.assetId().equals(asset.id()))
                        .isPresent()));
    }

    @Override
    public boolean permitsPoint(Asset asset, String sourceId, String pointId) {
        try {
            readAuthorization.executeWithoutResult(status ->
                    identity.require(TelemetryScopedAuthorizationContract.Operation.TRUSTED_READ,
                            asset.companyPartyId(), sourceId, pointId));
            return true;
        } catch (AccessDeniedException denied) {
            return false;
        }
    }

    @Override public void require(Scope scope,Operation operation) {
        authorize(scope,operation,null,false);
    }
    @Override public String requireActor(Scope scope,Operation operation,String companyPartyId) {
        if(companyPartyId==null||companyPartyId.isBlank())
            throw new AccessDeniedException("Protected telemetry resource unavailable.");
        return authorize(scope,operation,companyPartyId,true);
    }
    private String authorize(Scope scope,Operation operation,String claimedCompany,boolean captureActor) {
        if(scope==null||operation==null)
            throw new AccessDeniedException("Protected telemetry resource unavailable.");
        var owner=topology.currentOwner(scope.assetType(),scope.assetId())
                .orElseThrow(()->new AccessDeniedException("Protected telemetry resource unavailable."));
        if(!owner.typeCode().equals(scope.assetType()) || !owner.assetId().equals(scope.assetId())
                || (claimedCompany!=null&&!claimedCompany.equals(owner.companyPartyId())))
            throw new AccessDeniedException("Protected telemetry resource unavailable.");
        var nativeOperation=switch(operation) {
            case INGEST -> TelemetryScopedAuthorizationContract.Operation.INGEST;
            case READ -> TelemetryScopedAuthorizationContract.Operation.READ;
            case QUALIFY -> TelemetryScopedAuthorizationContract.Operation.QUALIFY;
            case TRUSTED_READ -> TelemetryScopedAuthorizationContract.Operation.TRUSTED_READ;
        };
        identity.require(nativeOperation,owner.companyPartyId(),scope.sourceId(),scope.pointId());
        return captureActor ? identity.currentActor() : null;
    }
    // Native commissioning reuses the sole reviewed direct Identity/Topology owner bridge.
    void requireCompanyManage(String company) {
        identity.require(TelemetryScopedAuthorizationContract.Operation.COMMISSION,company,null,null);
    }
    void requireCompanyRead(String company) {
        identity.require(TelemetryScopedAuthorizationContract.Operation.READ,company,null,null);
    }
    void requireScopedManage(String company,String source,String point) {
        identity.require(TelemetryScopedAuthorizationContract.Operation.COMMISSION,company,source,point);
    }
    void requireScopedRead(String company,String source,String point) {
        identity.require(TelemetryScopedAuthorizationContract.Operation.READ,company,source,point);
    }
    void requireAsset(String company,String type,String asset) {
        var owned=topology.currentOwner(type,asset).orElseThrow(
                ()->new AccessDeniedException("Protected telemetry resource unavailable."));
        if(!owned.companyPartyId().equals(company)
                || !owned.typeCode().equals(type) || !owned.assetId().equals(asset))
            throw new AccessDeniedException("Protected telemetry resource unavailable.");
    }
}
