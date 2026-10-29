/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.mapper
 *
 * @Description : Maps exact HTTP descriptors into the already guarded installed-unit use case.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;

import dz.sh.hidra.modules.topology.api.rest.request.InstalledUnitLifecycleRequests.*;
import dz.sh.hidra.modules.topology.api.rest.response.InstalledUnitLifecycleResponses.*;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnitInstallation;
import java.util.Objects;

public final class InstalledUnitLifecycleRestMapper {
    private InstalledUnitLifecycleRestMapper() {}
    private static String exact(String value){InstalledUnitLifecycleUseCase.exact(value);return value;}
    private static Position position(String position,String facility){return new Position(exact(position),exact(facility));}
    private static Command command(Operation operation,String request,String company,String unit,String installation,
            String replacementInstallation,Position from,Position to,InstalledUnit registration,
            java.time.Instant starts,java.time.Instant ends,String provenance,long revision,String replacementUnit) {
        return new Command(operation,request,exact(company),exact(unit),installation,replacementInstallation,
                from,to,registration,starts,ends,provenance,revision,replacementUnit);
    }
    public static Command register(String unit,Register body) {
        Objects.requireNonNull(body);
        var identity=new InstalledUnit(exact(unit),exact(body.companyPartyId()),exact(body.equipmentKindId()),
                body.manufacturerParty(),body.manufacturerModel(),body.serialNumber(),body.maintainableAssetId());
        return command(Operation.REGISTER_UNIT,body.requestId(),body.companyPartyId(),unit,
                null,null,null,null,identity,null,null,null,0,null);
    }
    public static Command readUnit(String unit,String company) {
        return command(Operation.READ_UNIT,null,company,unit,null,null,null,null,null,null,null,null,0,null);
    }
    public static Command record(String unit,RecordInstallation body) {
        Objects.requireNonNull(body);
        return command(Operation.RECORD_INSTALLATION,body.requestId(),body.companyPartyId(),unit,
                exact(body.installationId()),null,null,position(body.positionId(),body.facilityId()),
                null,body.effectiveFrom(),body.effectiveUntil(),body.evidenceReference(),0,null);
    }
    public static Command close(String unit,String installation,Close body) {
        Objects.requireNonNull(body);
        return command(Operation.CLOSE_INSTALLATION,body.requestId(),body.companyPartyId(),unit,
                exact(installation),null,position(body.positionId(),body.facilityId()),null,null,
                null,body.effectiveUntil(),null,body.expectedRevision(),null);
    }
    public static Command relocate(String unit,String installation,Relocate body) {
        Objects.requireNonNull(body);
        return command(Operation.RELOCATE,body.requestId(),body.companyPartyId(),unit,
                exact(installation),exact(body.replacementInstallationId()),
                position(body.sourcePositionId(),body.sourceFacilityId()),
                position(body.destinationPositionId(),body.destinationFacilityId()),null,
                body.effectiveAt(),body.effectiveAt(),body.evidenceReference(),body.expectedRevision(),null);
    }
    public static Command replace(String unit,String installation,Replace body) {
        Objects.requireNonNull(body);
        Position at=position(body.positionId(),body.facilityId());
        return command(Operation.REPLACE,body.requestId(),body.companyPartyId(),unit,
                exact(installation),exact(body.replacementInstallationId()),at,at,null,
                body.effectiveAt(),body.effectiveAt(),body.evidenceReference(),body.expectedRevision(),exact(body.replacementUnitId()));
    }
    public static Command readInstallation(String unit,String installation,String company,String positionId,String facilityId) {
        return command(Operation.READ_INSTALLATION,null,company,unit,exact(installation),null,
                position(positionId,facilityId),null,null,null,null,null,0,null);
    }
    public static Command history(String unit,String installation,String company,String positionId,String facilityId) {
        return command(Operation.READ_HISTORY,null,company,unit,exact(installation),null,
                position(positionId,facilityId),null,null,null,null,null,0,null);
    }
    private static Evidence evidence(InstalledUnit.Evidence value) {
        return value==null?null:new Evidence(value.value(),value.verification().name(),value.sourceReference());
    }
    private static Unit unit(InstalledUnit value) {
        return value==null?null:new Unit(value.id(),value.companyPartyId(),value.equipmentKindId(),
                evidence(value.manufacturerParty()),evidence(value.manufacturerModel()),evidence(value.serialNumber()),
                value.maintainableAssetId());
    }
    private static Installation installation(InstalledUnitInstallation value) {
        return value==null?null:new Installation(value.installationId(),value.installedUnitId(),
                value.equipmentPositionId(),value.companyPartyId(),value.effectiveFrom(),
                value.effectiveUntil(),value.recordedAt(),value.evidenceReference(),value.revision());
    }
    public static Lifecycle from(InstalledUnitLifecycleUseCase.Result result) {
        Objects.requireNonNull(result);
        return new Lifecycle(unit(result.unit()),installation(result.installation()),
                installation(result.replacement()),result.history().stream().map(InstalledUnitLifecycleRestMapper::installation).toList(),
                result.replayed(),unit(result.replacementUnit()));
    }
}
