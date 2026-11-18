/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaVerification
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 * @Description : Immutable independent verification outcome tied to exact HSE evidence.
 */
package dz.sh.hidra.modules.hse.domain.model;

import java.time.Instant;
public record HseCapaVerification(
    String id,String capaId,String evidenceId,Finding finding,String reason,
    String reviewerId,Instant verifiedAt) {
    public enum Finding { EFFECTIVE, INEFFECTIVE, INDETERMINATE }
    public HseCapaVerification {
        for(String v:new String[]{id,capaId,evidenceId,reason,reviewerId})
            if(v==null||v.isBlank())throw new IllegalArgumentException("Incomplete independent verification.");
        if(finding==null||verifiedAt==null)throw new IllegalArgumentException("Finding and time required.");
    }
    public boolean effective(){return finding==Finding.EFFECTIVE;}
}
