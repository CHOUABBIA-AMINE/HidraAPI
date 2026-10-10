/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterQualificationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.service
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.application.service;

import dz.sh.hidra.modules.simulation.application.port.out.*;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationEquipmentParameterRevisionRepositoryPort.*;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevisionTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class SimulationEquipmentParameterQualificationServiceTest {
    @Test void requiresActualApprovalAndBothOwnerAssessments(){var revisions=mock(SimulationEquipmentParameterRevisionRepositoryPort.class);var approvals=mock(SimulationEquipmentParameterApprovalEvidencePort.class);
        var sources=mock(SimulationEquipmentParameterRevisionQueryService.class);var service=new SimulationEquipmentParameterQualificationService(revisions,approvals,sources);
        var v=fixture("r1");when(revisions.findStored("equipment","r1")).thenReturn(Optional.of(new StoredRevision(v,"HIDRA_SIMULATION_EQUIPMENT_PARAMETERS_V1","c".repeat(64))));
        assertThrows(InvalidSimulationValueException.class,()->service.qualify("q","equipment","r1","i","t","a"));
        when(approvals.resolve(anyString(),any(),anyString(),anyString(),anyString(),any())).thenReturn(Optional.of(new SimulationEquipmentParameterApprovalEvidencePort.Evidence("i","t","a","definition",1,"type","purpose","actor","Synthetic",AT)));
        assertThrows(InvalidSimulationValueException.class,()->service.qualify("q","equipment","r1","i","t","a"));
        when(sources.compatible(eq(v),any(),isNull())).thenReturn(Optional.of(new SimulationEquipmentParameterRevisionQueryService.ResolvedSources(SimulationEquipmentParameterRevisionQueryServiceTest.fluid(),SimulationEquipmentParameterRevisionQueryServiceTest.network(false))));
        when(revisions.appendQualification(anyString(),anyString(),any())).thenAnswer(i->i.getArgument(2));var q=service.qualify("q","equipment","r1","i","t","a");assertEquals("actor",q.approverId());assertTrue(q.qualifiedAt().isAfter(AT));verify(sources).compatible(v,AT,null);
    }
}
