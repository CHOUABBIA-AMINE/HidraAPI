/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfigurationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Protected installed-station configuration ownership boundary.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration;
import java.util.List;
import java.util.Objects;

/** Private application API, never an HTTP authority or field command. */
public interface StationInstalledConfigurationUseCase {
    enum Operation { SAVE, READ_CURRENT, READ_REVISION, LIST_HISTORY }
    record Command(Operation operation,String companyPartyId,String facilityId,String revisionId,
                   StationInstalledConfiguration declaration,int limit) {
        public Command {
            Objects.requireNonNull(operation);
            exact(companyPartyId);exact(facilityId);
            if(operation==Operation.SAVE) {
                if(declaration==null || !companyPartyId.equals(declaration.companyPartyId())
                  || !facilityId.equals(declaration.facilityId()) || !Objects.equals(revisionId,declaration.revisionId()))
                    throw new IllegalArgumentException("Exact save declaration and revision required");
                exact(revisionId);
            } else if(operation==Operation.READ_REVISION) {
                if(declaration!=null)throw new IllegalArgumentException("Unexpected read declaration");
                exact(revisionId);
            } else if(revisionId!=null || declaration!=null)
                throw new IllegalArgumentException("Unexpected configuration data");
            if(operation==Operation.LIST_HISTORY ? limit<1 || limit>100 : limit!=0)
                throw new IllegalArgumentException("Bounded history limit required");
        }
        private static void exact(String value) {
            if(value==null || value.isBlank() || !value.equals(value.strip())
                    || value.codePointCount(0,value.length())>80
                    || value.codePoints().anyMatch(Character::isISOControl))
                throw new IllegalArgumentException("Exact bounded reference required");
        }
        @Override public String toString(){return "Command[protected station configuration descriptors]";}
    }
    record Result(StationInstalledConfiguration declaration,List<StationInstalledConfiguration> history,boolean replayed) {
        public Result { history=List.copyOf(Objects.requireNonNull(history)); }
        @Override public String toString(){return "Result[protected station configuration]";}
    }
    Result execute(Command command);
}
