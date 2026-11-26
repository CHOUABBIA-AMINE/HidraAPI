/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyPeriodAdmission
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Immutable protected period opening evidence.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import java.time.Instant;
import java.util.UUID;

public record CustodyPeriodAdmission(String periodId,String companyId,String facilityId,
        String transferPointId,String agreementId,String periodCode,Instant periodStart,
        Instant periodEnd,UUID commandId,String actorId,Instant admittedAt,String fingerprint) {
    public CustodyPeriodAdmission {
        exact(periodId);exact(companyId);exact(facilityId);exact(transferPointId);
        exact(agreementId);exact(periodCode);exact(actorId);
        interval(periodStart,periodEnd);
        if(commandId==null||admittedAt==null||admittedAt.getNano()%1000!=0
                ||fingerprint==null||!fingerprint.matches("[0-9a-f]{64}"))
            throw new IllegalArgumentException("Invalid immutable Custody admission evidence.");
    }
    public static String exact(String value) {
        if(value==null||value.isBlank()||!value.equals(value.strip())||value.length()>80)
            throw new IllegalArgumentException("Exact Custody identifier required.");
        return value;
    }
    public static void interval(Instant start,Instant end) {
        if(start==null||end==null||!start.isBefore(end)||start.getNano()%1000!=0||end.getNano()%1000!=0)
            throw new IllegalArgumentException("A positive microsecond-precision Custody interval is required.");
    }
    public void requireWindow(Instant from,Instant to) {
        if((from!=null&&periodStart.isBefore(from))||(to!=null&&periodEnd.isAfter(to)))
            throw new IllegalArgumentException("Custody interval is outside the effective window.");
    }
}
