/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedSimulationManufacturerCharacteristicAuthorizationParticipantTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Checks the synthetic manufacturer characteristic contract and denial cases.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.simulation.SimulationManufacturerCharacteristicAuthorizationContract.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProtectedSimulationManufacturerCharacteristicAuthorizationParticipantTest {
    private final ProtectedOperationIdentityParticipant identity=mock(ProtectedOperationIdentityParticipant.class);
    private final JdbcProtectedAuthorizationEvidenceReader reader=mock(JdbcProtectedAuthorizationEvidenceReader.class);
    private final JdbcTemplate jdbc=mock(JdbcTemplate.class);
    private final ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant participant;
    ProtectedSimulationManufacturerCharacteristicAuthorizationParticipantTest(){
        when(jdbc.getDataSource()).thenReturn(mock(javax.sql.DataSource.class));
        participant=new ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant(identity,reader,jdbc);
    }
    @Test void absentAuthenticationFailsBeforeNativePreparation(){
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
        assertThrows(org.springframework.security.access.AccessDeniedException.class,participant::prepare);
        verifyNoInteractions(identity,reader);
    }
    @Test void nullAndForgedAdmissionsFailClosed(){
        assertThrows(org.springframework.security.access.AccessDeniedException.class,()->participant.contribute(null));
        assertThrows(org.springframework.security.access.AccessDeniedException.class,()->participant.requireCurrent(null));
    }
    @Test void requestRequiresExactResourceAndOperation(){
        assertThrows(IllegalArgumentException.class,()->new Request(Operation.SAVE,"company","source",null));
        assertThrows(IllegalArgumentException.class,()->new Request(Operation.LIST_HISTORY,"company","source","v1"));
        assertEquals("simulation:manufacturer-characteristic:qualify",permission(Operation.QUALIFY));
    }
    private static String permission(Operation op){
        return dz.sh.hidra.modules.identity.application.contract.simulation
            .SimulationManufacturerCharacteristicAuthorizationContract.permissionCode(op);
    }
}
