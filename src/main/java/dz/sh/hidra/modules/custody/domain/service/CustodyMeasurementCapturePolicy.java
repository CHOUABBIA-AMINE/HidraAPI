/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyMeasurementCapturePolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.service
 * @Description : Validates exact observed interval totals without conversion.
 */
package dz.sh.hidra.modules.custody.domain.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
public final class CustodyMeasurementCapturePolicy {
    private CustodyMeasurementCapturePolicy() {}
    public static class Invalid extends IllegalArgumentException {
        public Invalid(String message){super(message);}
    }
    public static class Conflict extends IllegalStateException {
        public Conflict(String message){super(message);}
    }
    public static void interval(Instant start,Instant end,Instant periodStart,Instant periodEnd){
        if(start==null || end==null || periodStart==null || periodEnd==null
            || !start.isBefore(end) || start.isBefore(periodStart) || end.isAfter(periodEnd))
            throw new Invalid("The attested interval must be entirely within the open period.");
    }
    public static void value(BigDecimal value){
        if(value==null || value.signum()<0 || value.scale()>6
            || value.precision()-value.scale()>12 || value.abs().compareTo(new BigDecimal("999999999999.999999"))>0)
            throw new Invalid("Observed quantity must be exact, nonnegative and within numeric(18,6).");
    }
    public static void effective(Instant start,Instant end,Instant from,Instant to){
        if(from!=null && start.isBefore(from) || to!=null && end.isAfter(to))
            throw new Invalid("The interval falls outside an effective source window.");
    }
    public static String required(String value){
        if(value==null || value.isBlank() || value.length()>80 || !value.equals(value.strip()))
            throw new Invalid("An exact nonempty reference is required.");
        return value;
    }
}
