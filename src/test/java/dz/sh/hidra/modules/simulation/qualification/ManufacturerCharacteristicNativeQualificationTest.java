/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicNativeQualificationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.qualification
 *
 * @Description : Checks the synthetic manufacturer characteristic contract and denial cases.
 *
 */
package dz.sh.hidra.modules.simulation.qualification;

import dz.sh.hidra.modules.simulation.application.port.out.ManufacturerCharacteristicRevisionRepositoryPort.Key;
import dz.sh.hidra.modules.simulation.application.service.ProtectedManufacturerCharacteristicAdmissionCoordinator;
import dz.sh.hidra.modules.identity.application.contract.simulation.SimulationManufacturerCharacteristicAuthorizationContract;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManufacturerCharacteristicNativeQualificationTest {
    @Test void exactCompanySourceRevisionAndPayloadDomainSeparation(){
        String payload="a".repeat(64);
        var one=new Key("company-A","source","v1");
        var otherCompany=new Key("company-B","source","v1");
        var otherRevision=new Key("company-A","source","v2");
        var oneDigest=ProtectedManufacturerCharacteristicAdmissionCoordinator.targetDigestStatic(one,payload);
        assertTrue(oneDigest.matches("[0-9a-f]{64}"));
        assertEquals(oneDigest,ProtectedManufacturerCharacteristicAdmissionCoordinator.targetDigestStatic(one,payload));
        assertNotEquals(oneDigest,ProtectedManufacturerCharacteristicAdmissionCoordinator.targetDigestStatic(otherCompany,payload));
        assertNotEquals(oneDigest,ProtectedManufacturerCharacteristicAdmissionCoordinator.targetDigestStatic(otherRevision,payload));
        assertNotEquals(oneDigest,ProtectedManufacturerCharacteristicAdmissionCoordinator.targetDigestStatic(one,"b".repeat(64)));
        assertThrows(RuntimeException.class,()->ProtectedManufacturerCharacteristicAdmissionCoordinator.targetDigestStatic(one,"not-sha"));
    }
    @Test void operationPermissionsAreDistinctAndNeverGenericWorkflowPermissions(){
        var types=SimulationManufacturerCharacteristicAuthorizationContract.Operation.values();
        var values=java.util.Arrays.stream(types)
            .map(SimulationManufacturerCharacteristicAuthorizationContract::permissionCode).toList();
        assertEquals(5,values.stream().distinct().count());
        assertTrue(values.stream().allMatch(s->s.startsWith("simulation:")));
        assertTrue(values.contains("simulation:manufacturer-certificate:read"));
        assertFalse(values.contains("workflow:*"));
        assertEquals("MANUFACTURER_CHARACTERISTIC_REVISION",
            SimulationManufacturerCharacteristicAuthorizationContract.RESOURCE_TYPE);
    }
}
