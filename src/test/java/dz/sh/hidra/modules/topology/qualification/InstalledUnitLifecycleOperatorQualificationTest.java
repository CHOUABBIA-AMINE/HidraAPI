/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleOperatorQualificationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.qualification
 *
 * @Description : Checks serial identity and uncertainty boundaries without operator or engineering approval claims.
 *
 */
package dz.sh.hidra.modules.topology.qualification;

import dz.sh.hidra.modules.topology.api.rest.mapper.InstalledUnitLifecycleRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.InstalledUnitLifecycleRequests.Register;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InstalledUnitLifecycleOperatorQualificationTest {
    private static InstalledUnit.Evidence known(String value,InstalledUnit.Verification classification) {
        return new InstalledUnit.Evidence(value,classification,"synthetic inventory record");
    }
    private static InstalledUnit unit(String id,InstalledUnit.Evidence party,InstalledUnit.Evidence model,InstalledUnit.Evidence serial) {
        return new InstalledUnit(id,"company","kind",party,model,serial,null);
    }
    @Test void repeatedMarkedSerialDoesNotCollapseIndependentPhysicalUnitIdentity() {
        var party=known("manufacturer",InstalledUnit.Verification.VERIFIED);
        var model=known("model",InstalledUnit.Verification.UNVERIFIED);
        var serial=known("serial-repeated",InstalledUnit.Verification.UNVERIFIED);
        var first=unit("unit-one",party,model,serial);
        var second=unit("unit-two",party,model,serial);
        assertNotEquals(first.id(),second.id());
        assertEquals(first.serialNumber(),second.serialNumber());
        var command1=InstalledUnitLifecycleRestMapper.register(first.id(),
                new Register("request-one","company","kind",party,model,serial,null));
        var command2=InstalledUnitLifecycleRestMapper.register(second.id(),
                new Register("request-two","company","kind",party,model,serial,null));
        assertNotEquals(command1.unitId(),command2.unitId());
        assertEquals(command1.registration().serialNumber(),command2.registration().serialNumber());
    }
    @Test void unknownProvenanceNeverManufacturesSerialOrModel() {
        var unknown=InstalledUnit.Evidence.unknown();
        var unit=unit("unit-one",unknown,unknown,unknown);
        var result=InstalledUnitLifecycleRestMapper.from(new InstalledUnitLifecycleUseCase.Result(unit,null,null,List.of(),false,null));
        assertEquals("UNKNOWN",result.unit().manufacturerParty().verification());
        assertNull(result.unit().serialNumber().value());
        assertNull(result.unit().manufacturerModel().sourceReference());
        assertThrows(IllegalArgumentException.class,()->new InstalledUnit.Evidence("invented",InstalledUnit.Verification.UNKNOWN,"invented"));
    }
    @Test void verificationLabelIsAuthoredEvidenceNotEngineeringQualification() {
        var party=known("manufacturer",InstalledUnit.Verification.VERIFIED);
        var model=known("model",InstalledUnit.Verification.VERIFIED);
        var serial=known("serial",InstalledUnit.Verification.VERIFIED);
        var unit=unit("unit-one",party,model,serial);
        var dto=InstalledUnitLifecycleRestMapper.from(new InstalledUnitLifecycleUseCase.Result(unit,null,null,List.of(),false,null));
        assertEquals("VERIFIED",dto.unit().manufacturerModel().verification());
        assertFalse(java.util.Arrays.stream(dto.getClass().getRecordComponents()).anyMatch(p->p.getName().equals("engineeringQualified")));
        assertThrows(RuntimeException.class,()->unit("unit-two",InstalledUnit.Evidence.unknown(),model,serial));
    }
    @Test void sourceAndAssetAssociationAreLabelsNotCrossAggregateMutation() {
        var source=known("identifier",InstalledUnit.Verification.UNVERIFIED);
        var unit=new InstalledUnit("unit","company","kind",source,InstalledUnit.Evidence.unknown(),source,"asset-reference");
        var result=InstalledUnitLifecycleRestMapper.from(new InstalledUnitLifecycleUseCase.Result(unit,null,null,List.of(),false,null));
        assertEquals("asset-reference",result.unit().maintainableAssetId());
        assertEquals("synthetic inventory record",result.unit().manufacturerParty().sourceReference());
        assertFalse(result.unit().manufacturerParty().verification().equals("QUALIFIED"));
    }
}
