/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyLifecyclePolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.service
 *
 * @Description : Owns Party lifecycle, metadata bounds and exact version invariants.
 *
 */
package dz.sh.hidra.modules.party.domain.service;

import dz.sh.hidra.modules.party.domain.value.PartyStatus;
import java.time.Instant;
public final class PartyLifecyclePolicy {
    private PartyLifecyclePolicy() { }
    public static void transition(PartyStatus current,boolean activate) {
        if(activate ? current!=PartyStatus.DRAFT : current!=PartyStatus.ACTIVE)
            throw new IllegalStateException("Unsupported Party lifecycle transition.");
    }
    public static void expected(Instant expected,Instant actual) {
        if(expected==null || actual==null || !expected.equals(actual))
            throw new IllegalStateException("Stale Party version.");
    }
    public static void reason(String value) { bounded(value,2000,true); }
    public static void registration(String code,String type,String country,String jurisdiction,String primaryRole) {
        bounded(code,120,true);bounded(type,80,true);bounded(country,3,true);
        bounded(jurisdiction,120,false);bounded(primaryRole,120,false);
    }
    public static void metadata(String legal,String trade,String shortName) {
        bounded(legal,255,true);bounded(trade,255,false);bounded(shortName,160,false);
    }
    private static void bounded(String value,int max,boolean required) {
        if((required && (value==null || value.isBlank())) || (value!=null && value.strip().length()>max))
            throw new IllegalArgumentException("Invalid bounded Party value.");
    }
}
