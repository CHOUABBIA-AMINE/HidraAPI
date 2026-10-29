/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Transport-owned installed-unit and immutable installation evidence responses.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

public final class InstalledUnitLifecycleResponses {
    private InstalledUnitLifecycleResponses() {}
    @Schema(name="InstalledUnitEvidenceResponse")
    public record Evidence(String value,String verification,String sourceReference) {}
    @Schema(name="InstalledUnitIdentityResponse")
    public record Unit(String id,String companyPartyId,String equipmentKindId,
            Evidence manufacturerParty,Evidence manufacturerModel,Evidence serialNumber,String maintainableAssetId) {}
    @Schema(name="InstalledUnitInstallationResponse")
    public record Installation(String installationId,String installedUnitId,String equipmentPositionId,
            String companyPartyId,Instant effectiveFrom,Instant effectiveUntil,
            Instant recordedAt,String evidenceReference,long revision) {}
    @Schema(name="InstalledUnitLifecycleResponse")
    public record Lifecycle(Unit unit,Installation installation,Installation replacement,
            List<Installation> history,boolean replayed,Unit replacementUnit) {
        public Lifecycle {history=List.copyOf(history);}
    }
}
