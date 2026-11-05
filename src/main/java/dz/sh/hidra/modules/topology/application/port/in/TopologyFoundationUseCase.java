package dz.sh.hidra.modules.topology.application.port.in;

import java.time.Instant;
import java.util.*;

/** Company-scoped setup; never an engine, maintenance aggregate, or permission issuer. */
public interface TopologyFoundationUseCase {
    enum Kind { DISCOVER, CREATE_SYSTEM, REGISTER_FACILITY, ACTIVATE_FACILITY, REGISTER_POSITION, POSITIONS, INSTALLATIONS }
    enum Catalog { SYSTEM_TYPES, FACILITY_TYPES, PIPELINE_TYPES, EQUIPMENT_KINDS, SYSTEMS, FACILITIES }
    record Command(Kind kind,String companyPartyId,String facilityId,UUID requestId,String resourceId,
            String code,String name,String typeId,String facilityKind,Instant expectedUpdatedAt,String reason,
            Catalog catalog,int offset,int limit) {
        public Command {
            Objects.requireNonNull(kind);exact(companyPartyId,80);
            if(facilityId!=null)exact(facilityId,80);
            if(offset<0 || offset>100000 || limit<1 || limit>200)throw new IllegalArgumentException("Bounded scan required");
            if(mutation(kind)) {
                Objects.requireNonNull(requestId);exact(resourceId,80);exact(reason,1024);
                if(kind!=Kind.ACTIVATE_FACILITY){exact(code,120);exact(name,255);exact(typeId,80);}
                else {Objects.requireNonNull(expectedUpdatedAt);if(expectedUpdatedAt.getNano()%1000!=0)throw new IllegalArgumentException("PostgreSQL generation precision required");}
            }
            if(kind==Kind.REGISTER_FACILITY){exact(facilityKind,80);dz.sh.hidra.modules.topology.domain.value.FacilityKind.valueOf(facilityKind);}
            if(Set.of(Kind.DISCOVER,Kind.CREATE_SYSTEM).contains(kind) && facilityId!=null)throw new IllegalArgumentException("Company setup target required");
            if(kind==Kind.DISCOVER)Objects.requireNonNull(catalog);
            if(Set.of(Kind.REGISTER_FACILITY,Kind.ACTIVATE_FACILITY,Kind.REGISTER_POSITION,Kind.POSITIONS,Kind.INSTALLATIONS).contains(kind))Objects.requireNonNull(facilityId);
            if(Set.of(Kind.REGISTER_FACILITY,Kind.ACTIVATE_FACILITY).contains(kind) && !facilityId.equals(resourceId))throw new IllegalArgumentException("Facility identity mismatch");
        }
        public boolean mutation(){return mutation(kind);}
        private static boolean mutation(Kind k){return k==Kind.CREATE_SYSTEM || k==Kind.REGISTER_FACILITY || k==Kind.ACTIVATE_FACILITY || k==Kind.REGISTER_POSITION;}
    }
    record Item(String id,String code,String name,String kind,String status,Instant updatedAt) {}
    /** Private candidates leave the adapter only for independent native unit admission. */
    record InstallationCandidate(String unitId,String installationId,String positionId,String facilityId) {}
    record Installation(String unitId,String installationId,String positionId,String facilityId,long revision,Instant effectiveFrom,Instant effectiveUntil) {}
    record Result(List<Item> items,List<InstallationCandidate> candidates,List<Installation> installations,Integer nextOffset,boolean replayed) {
        public Result {items=List.copyOf(items);candidates=List.copyOf(candidates);installations=List.copyOf(installations);}
    }
    Result executeFoundation(Command command);
    static String exact(String s,int max){if(s==null || s.isBlank() || !s.equals(s.strip()) || s.codePointCount(0,s.length())>max || s.codePoints().anyMatch(Character::isISOControl))throw new IllegalArgumentException("Exact bounded input required");return s;}
}
