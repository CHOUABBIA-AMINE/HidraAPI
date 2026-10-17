/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * BoundSourceClaimEvidenceRepositoryPort @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type :
 * Interface @Layer : Application @Module : identity @Package :
 * dz.sh.hidra.modules.identity.application.port.out @Description : Defines nonauthoritative append
 * and exact structural lookup of bound claim evidence.
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.value.BoundSourceClaimEvidence;

import java.util.Optional;

/** Storage equality is not private-origin or current-authorization verification. */
public interface BoundSourceClaimEvidenceRepositoryPort {
    void append(BoundSourceClaimEvidence evidence);

    Optional<BoundSourceClaimEvidence> findByBindingId(String bindingId);
}
