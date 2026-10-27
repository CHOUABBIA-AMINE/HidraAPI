/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProtectedFluidUnitContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.contract.custody
 *
 * @Description : Provides protected owner evidence for explicitly admitted fluid units.
 *
 */
package dz.sh.hidra.modules.telemetry.application.contract.custody;

import java.time.Instant;
import java.util.*;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;

/** Owner facts only: these snapshots are never an authorization permit or a conversion. */
public interface CustodyProtectedFluidUnitContract {
    enum Role { DENSITY, DYNAMIC_VISCOSITY, COMPRESSIBILITY_FACTOR, ABSOLUTE_PRESSURE, ABSOLUTE_TEMPERATURE }
    record Need(Role role,String unitId) implements Comparable<Need> {
        public Need {Objects.requireNonNull(role);exact(unitId,80);}
        @Override public int compareTo(Need other){int n=unitId.compareTo(other.unitId);return n!=0?n:role.compareTo(other.role);}
    }
    record Unit(Need need,String code,String symbol,String dimension,Instant updatedAt) {
        public Unit {Objects.requireNonNull(need);exact(code,80);exact(symbol,160);exact(dimension,80);Objects.requireNonNull(updatedAt);}
    }
    record Contribution(List<Unit> units,int backendPid,long transactionId) {
        public Contribution {units=List.copyOf(units);if(backendPid<=0 || transactionId<=0)throw new IllegalArgumentException("Actual unit transaction required");}
    }
    interface Admission {}
    Admission acquire(List<Need> needs);
    Contribution read(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    static List<Need> exactNeeds(List<Need> needs) {
        if(needs==null || needs.size()>128 || needs.stream().anyMatch(Objects::isNull)
                || new HashSet<>(needs).size()!=needs.size())throw new IllegalArgumentException("Bounded unique unit requests required");
        return needs.stream().sorted().toList();
    }
    static void exact(String value,int max) {
        if(value==null || value.isBlank() || !value.equals(value.strip()) || value.indexOf(0)>=0
                || value.codePointCount(0,value.length())>max)throw new IllegalArgumentException("Exact unit text required");
        for(int n=0;n<value.length();n++){char c=value.charAt(n);if(Character.isHighSurrogate(c)){
            if(++n>=value.length() || !Character.isLowSurrogate(value.charAt(n)))throw new IllegalArgumentException("Invalid unit Unicode");
        }else if(Character.isLowSurrogate(c))throw new IllegalArgumentException("Invalid unit Unicode");}
    }
}
