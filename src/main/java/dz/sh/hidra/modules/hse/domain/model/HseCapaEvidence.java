/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaEvidence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 * @Description : Immutable exact accepted Documents version and provenance attached to a governed CAPA.
 */
package dz.sh.hidra.modules.hse.domain.model;

import java.time.Instant;
public record HseCapaEvidence(
    String id, String capaId, String documentId, String versionId, String acceptanceId,
    String sha256, long contentLength, String companyId, String facilityId,
    String actorId, Instant attachedAt) {
    public HseCapaEvidence {
        for (String v : new String[]{id,capaId,documentId,versionId,acceptanceId,sha256,
            companyId,facilityId,actorId}) if(v==null||v.isBlank())throw new IllegalArgumentException("Incomplete CAPA evidence.");
        if(!sha256.matches("[0-9a-f]{64}")||contentLength<0||attachedAt==null)
            throw new IllegalArgumentException("Invalid immutable CAPA binary evidence.");
    }
}
