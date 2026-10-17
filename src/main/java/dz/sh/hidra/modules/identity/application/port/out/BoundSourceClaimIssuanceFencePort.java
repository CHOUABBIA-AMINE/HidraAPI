/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * BoundSourceClaimIssuanceFencePort @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type :
 * Interface @Layer : Application @Module : identity @Package :
 * dz.sh.hidra.modules.identity.application.port.out @Description : Defines physical issuance
 * fencing without exposing persistence infrastructure.
 */
package dz.sh.hidra.modules.identity.application.port.out;

/** Infrastructure must hold the complete owner fence until physical completion. */
public interface BoundSourceClaimIssuanceFencePort {
    record FenceStamp(int backendPid, long transactionId) {
        public FenceStamp {
            if (backendPid <= 0 || transactionId <= 0)
                throw new IllegalArgumentException("Invalid issuance transaction stamp.");
        }
    }

    FenceStamp prepare(String providerId, ClaimNameCatalogPort.VersionedSelection expected);

    void requireCurrent(FenceStamp stamp);
}
