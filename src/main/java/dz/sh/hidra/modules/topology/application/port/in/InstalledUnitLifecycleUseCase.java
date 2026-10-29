/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Implements the protected installed-unit lifecycle within its owning boundary.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnitInstallation;
import java.time.Instant;
import java.util.*;

/** Typed owner use cases; descriptors assert scope but never prove authorization. */
public interface InstalledUnitLifecycleUseCase {
    enum Operation { REGISTER_UNIT,READ_UNIT,RECORD_INSTALLATION,CLOSE_INSTALLATION,RELOCATE,READ_INSTALLATION,READ_HISTORY,REPLACE }
    record Position(String positionId,String facilityId) {
        public Position { exact(positionId);exact(facilityId); }
    }
    record Command(Operation operation,String requestId,String companyPartyId,String unitId,
            String installationId,String replacementInstallationId,Position source,Position destination,
            InstalledUnit registration,Instant effectiveFrom,Instant effectiveUntil,String evidenceReference,long expectedRevision,String replacementUnitId) {
        public Command(Operation operation,String requestId,String companyPartyId,String unitId,
                String installationId,String replacementInstallationId,Position source,Position destination,
                InstalledUnit registration,Instant effectiveFrom,Instant effectiveUntil,String evidenceReference,long expectedRevision) {
            this(operation,requestId,companyPartyId,unitId,installationId,replacementInstallationId,source,destination,
                registration,effectiveFrom,effectiveUntil,evidenceReference,expectedRevision,null);
        }
        public Command {
            Objects.requireNonNull(operation);exact(companyPartyId);exact(unitId);
            if(requestId!=null)exact(requestId);if(installationId!=null)exact(installationId);
            if(replacementInstallationId!=null)exact(replacementInstallationId);
            if(replacementUnitId!=null)exact(replacementUnitId);
            if(evidenceReference!=null && (evidenceReference.isBlank() || evidenceReference.length()>65536
                || !evidenceReference.equals(evidenceReference.strip())))throw invalid();
            for(Instant time:new Instant[]{effectiveFrom,effectiveUntil})if(time!=null && time.getNano()%1000!=0)throw invalid();
            boolean register=operation==Operation.REGISTER_UNIT,record=operation==Operation.RECORD_INSTALLATION;
            boolean close=operation==Operation.CLOSE_INSTALLATION,move=operation==Operation.RELOCATE;
            boolean replace=operation==Operation.REPLACE;
            if(replace ? replacementUnitId==null || unitId.equals(replacementUnitId) || source==null || !source.equals(destination)
                : replacementUnitId!=null)throw invalid();
            boolean mutation=register||record||close||move||replace;
            if(mutation!=(requestId!=null) || expectedRevision<0)throw invalid();
            if(register || operation==Operation.READ_UNIT) {
                if(installationId!=null || replacementInstallationId!=null || source!=null || destination!=null
                    || effectiveFrom!=null || effectiveUntil!=null || evidenceReference!=null || expectedRevision!=0
                    || register!=(registration!=null))throw invalid();
                if(register && (!unitId.equals(registration.id()) || !companyPartyId.equals(registration.companyPartyId())))throw invalid();
            } else {
                if(installationId==null || registration!=null)throw invalid();
                if(record) {
                    if(source!=null || destination==null || replacementInstallationId!=null || effectiveFrom==null
                        || evidenceReference==null || expectedRevision!=0 || effectiveUntil!=null && !effectiveUntil.isAfter(effectiveFrom))throw invalid();
                } else if(close || move || replace) {
                    if(source==null || expectedRevision<1 || expectedRevision==Long.MAX_VALUE || effectiveUntil==null)throw invalid();
                    if(move || replace) {
                        if(destination==null || replacementInstallationId==null || replacementInstallationId.equals(installationId)
                            || !effectiveUntil.equals(effectiveFrom) || evidenceReference==null)throw invalid();
                    } else if(destination!=null || replacementInstallationId!=null || effectiveFrom!=null || evidenceReference!=null)throw invalid();
                } else if(source==null || destination!=null || replacementInstallationId!=null || effectiveFrom!=null
                    || effectiveUntil!=null || evidenceReference!=null || expectedRevision!=0)throw invalid();
            }
        }
        public boolean mutation(){return EnumSet.of(Operation.REGISTER_UNIT,Operation.RECORD_INSTALLATION,Operation.CLOSE_INSTALLATION,Operation.RELOCATE,Operation.REPLACE).contains(operation);}
    }
    record Result(InstalledUnit unit,InstalledUnitInstallation installation,InstalledUnitInstallation replacement,
            List<InstalledUnitInstallation> history,boolean replayed,InstalledUnit replacementUnit) {
        public Result(InstalledUnit unit,InstalledUnitInstallation installation,InstalledUnitInstallation replacement,
                List<InstalledUnitInstallation> history,boolean replayed) {this(unit,installation,replacement,history,replayed,null);}
        public Result {Objects.requireNonNull(unit);history=List.copyOf(history);}
        public Result replay(){return new Result(unit,installation,replacement,history,true,replacementUnit);}
    }
    Result execute(Command command);
    static void exact(String value) {
        if(value==null || value.isBlank() || !value.equals(value.strip()) || value.codePointCount(0,value.length())>80)throw invalid();
    }
    static IllegalArgumentException invalid(){return new IllegalArgumentException("Invalid installed-unit operation");}
}
