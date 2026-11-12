/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCommissioningApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Coordinates authorized atomic commissioning and historical retrieval.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.port.in.TelemetryCommissioningUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryCommissioningAuthorizationPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryCommissioningPersistencePort;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryCommissioningPolicy;
import java.time.Instant;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TelemetryCommissioningApplicationService implements TelemetryCommissioningUseCase {
    private final TelemetryCommissioningPersistencePort store;
    private final TelemetryCommissioningAuthorizationPort authority;
    public TelemetryCommissioningApplicationService(TelemetryCommissioningPersistencePort store,
                                                    TelemetryCommissioningAuthorizationPort authority) {
        this.store=Objects.requireNonNull(store);
        this.authority=Objects.requireNonNull(authority);
    }
    @Override @Transactional
    public Snapshot enroll(Enroll cmd) {
        Objects.requireNonNull(cmd,"enrollment");
        if(cmd.requestId()==null)throw new InvalidTelemetryValueException("Request identity required.");
        TelemetryCommissioningPolicy.enroll(cmd.companyPartyId(),cmd.assetType(),cmd.assetId(),
                cmd.sourceCode(),cmd.deviceCode(),cmd.pointCode(),cmd.externalTagName(),
                cmd.validFrom(),cmd.validUntil());
        authority.requireCompanyManage(cmd.companyPartyId());
        authority.requireAsset(cmd.companyPartyId(),cmd.assetType(),cmd.assetId());
        return store.enroll(cmd);
    }
    @Override @Transactional
    public Snapshot rebind(Rebind cmd) {
        Objects.requireNonNull(cmd,"rebinding");
        if(cmd.requestId()==null)throw new InvalidTelemetryValueException("Request identity required.");
        TelemetryCommissioningPolicy.rebind(cmd.companyPartyId(),cmd.pointId(),cmd.assetType(),
                cmd.assetId(),cmd.externalTagName(),cmd.validFrom(),cmd.validUntil());
        authority.requireCompanyManage(cmd.companyPartyId());
        var original=store.effective(cmd.pointId(),Instant.now()).orElseThrow(
                ()->new AccessDeniedException("Protected telemetry resource unavailable."));
        authority.requireScopedManage(cmd.companyPartyId(),original.sourceId(),cmd.pointId());
        authority.requireAsset(cmd.companyPartyId(),original.assetType(),original.assetId());
        authority.requireAsset(cmd.companyPartyId(),cmd.assetType(),cmd.assetId());
        return store.rebind(cmd);
    }
    @Override @Transactional
    public Snapshot effective(String company,String pointId,Instant asOf) {
        TelemetryCommissioningPolicy.required("companyPartyId",company,80);
        TelemetryCommissioningPolicy.required("pointId",pointId,80);
        if(asOf==null)throw new InvalidTelemetryValueException("asOf is required.");
        authority.requireCompanyRead(company);
        var result=store.effective(pointId,asOf).orElseThrow(
                ()->new AccessDeniedException("Protected telemetry resource unavailable."));
        authority.requireScopedRead(company,result.sourceId(),pointId);
        authority.requireAsset(company,result.assetType(),result.assetId());
        return result;
    }
    @Override @Transactional
    public History history(String company,String pointId,int limit) {
        TelemetryCommissioningPolicy.required("companyPartyId",company,80);
        TelemetryCommissioningPolicy.required("pointId",pointId,80);
        if(limit<1||limit>50)throw new InvalidTelemetryValueException("History limit must be 1–50.");
        authority.requireCompanyRead(company);
        var values=store.history(pointId,limit+1);
        // Authorize every returned asset and source-point before returning even a count or hasMore.
        for(var result:values) {
            authority.requireScopedRead(company,result.sourceId(),pointId);
            authority.requireAsset(company,result.assetType(),result.assetId());
        }
        boolean more=values.size()>limit;
        return new History(more?values.subList(0,limit):values,more);
    }
}
