/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name        : AlarmResourceAuthorizationAdapterTest
 * @UpdatedOn   : 2026-11-08
 * @Type        : Test
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.security
 * @Description : Verifies paired Alarm scope filtering and indistinguishable denial.
 */
package dz.sh.hidra.modules.alarm.infrastructure.security;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmRepositoryPort;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmResourceAuthorizationPort;
import dz.sh.hidra.modules.alarm.domain.exception.AlarmResourceNotFoundException;
import dz.sh.hidra.modules.identity.application.contract.alarm.AlarmScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract;
import dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract.OwnedAsset;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlarmResourceAuthorizationAdapterTest {
    private final AlarmTopologyOwnershipContract owners=mock(AlarmTopologyOwnershipContract.class);
    private final AlarmScopedAuthorizationContract grants=mock(AlarmScopedAuthorizationContract.class);
    private final AlarmRepositoryPort alarms=mock(AlarmRepositoryPort.class);
    private final AlarmResourceAuthorizationAdapter adapter=new AlarmResourceAuthorizationAdapter(owners,grants,alarms);

    @Test void anOutOfScopeActorCannotAccessAnExistingOwnedAsset(){
        when(owners.currentOwner("FACILITY","f")).thenReturn(Optional.of(new OwnedAsset("FACILITY","f","company-a")));
        assertThrows(AlarmResourceNotFoundException.class,()->adapter.requireAsset("alarm:alarms:read","FACILITY","f"));
        verify(grants).permitted("alarm:alarms:read","company-a","FACILITY","f");
    }
    @Test void distinctCompaniesAndAssetsAreIndependentlyFilteredBeforeDatabasePage(){
        when(owners.boundedOwnedAssets()).thenReturn(List.of(
            new OwnedAsset("FACILITY","a","company-a"),new OwnedAsset("FACILITY","b","company-a"),
            new OwnedAsset("EQUIPMENT","c","company-b")));
        when(grants.permitted("alarm:alarms:read","company-a","FACILITY","a")).thenReturn(true);
        assertEquals(List.of(new AlarmResourceAuthorizationPort.AssetRef("FACILITY","a")),
            adapter.permittedAssets("alarm:alarms:read"));
    }
    @Test void unsupportedAndMissingTargetsHaveIndistinguishableDeniedOutcome(){
        assertEquals("Resource not found.",assertThrows(AlarmResourceNotFoundException.class,
            ()->adapter.requireAsset("alarm:alarms:read","PIPELINE_SYSTEM","gz2")).getMessage());
        assertEquals("Resource not found.",assertThrows(AlarmResourceNotFoundException.class,
            ()->adapter.requireAlarm("alarm:alarms:read","missing")).getMessage());
    }
    @Test void permittedAcknowledgementRequiresAnExplicitOperationGrant(){
        when(owners.currentOwner("EQUIPMENT","e")).thenReturn(Optional.of(new OwnedAsset("EQUIPMENT","e","company-a")));
        when(grants.permitted("alarm:alarms:execute","company-a","EQUIPMENT","e")).thenReturn(true);
        assertDoesNotThrow(()->adapter.requireAsset("alarm:alarms:execute","EQUIPMENT","e"));
        assertThrows(AlarmResourceNotFoundException.class,()->adapter.requireAsset("alarm:alarms:read","EQUIPMENT","e"));
    }
}
