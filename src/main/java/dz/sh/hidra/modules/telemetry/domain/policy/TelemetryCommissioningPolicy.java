/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCommissioningPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Pure Java commissioning and half-open interval invariants.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.policy;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import java.time.Instant;

public final class TelemetryCommissioningPolicy {
    private TelemetryCommissioningPolicy() { }
    public static String required(String field, String value, int max) {
        if(value==null || value.isBlank() || !value.equals(value.strip()) || value.length()>max)
            throw new InvalidTelemetryValueException("Invalid commissioning field: "+field);
        return value;
    }
    public static void interval(Instant from, Instant until) {
        if(from==null || !from.equals(from.truncatedTo(java.time.temporal.ChronoUnit.MICROS))
                || until!=null && (!until.equals(until.truncatedTo(java.time.temporal.ChronoUnit.MICROS))
                                || !until.isAfter(from)))
            throw new InvalidTelemetryValueException("Invalid half-open commissioning validity window.");
    }
    public static void enroll(String company, String type, String asset,
                              String sourceCode, String deviceCode, String pointCode,
                              String tag, Instant from, Instant until) {
        required("companyPartyId",company,80);
        required("assetId",asset,80);
        if(!"FACILITY".equals(type) && !"EQUIPMENT".equals(type))
            throw new InvalidTelemetryValueException("Unsupported authoritative asset family.");
        required("sourceCode",sourceCode,80);
        required("deviceCode",deviceCode,80);
        required("pointCode",pointCode,80);
        required("externalTagName",tag,500);
        interval(from,until);
    }
    public static void rebind(String company,String point,String type,String asset,
                              String tag,Instant from,Instant until) {
        required("companyPartyId",company,80);
        required("pointId",point,80);
        required("assetId",asset,80);
        if(!"FACILITY".equals(type) && !"EQUIPMENT".equals(type))
            throw new InvalidTelemetryValueException("Unsupported authoritative asset family.");
        required("externalTagName",tag,500);
        interval(from,until);
    }
    public static boolean overlaps(Instant leftFrom,Instant leftUntil,
                                   Instant rightFrom,Instant rightUntil) {
        return (leftUntil==null || rightFrom.isBefore(leftUntil))
                && (rightUntil==null || leftFrom.isBefore(rightUntil));
    }
}
