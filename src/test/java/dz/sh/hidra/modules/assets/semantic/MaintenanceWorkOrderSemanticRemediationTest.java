/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.semantic
 *
 * @Description : Validates owner-controlled MaintenanceWorkOrder references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.assets.semantic;

import dz.sh.hidra.modules.assets.domain.model.MaintenanceWorkOrder;
import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import dz.sh.hidra.modules.assets.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.identity.application.contract.assets.MaintenanceWorkOrderActorReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.assets.MaintenanceWorkOrderWorkflowReferenceContract;
import dz.sh.hidra.modules.integrity.application.contract.assets.MaintenanceRecommendationReferenceContract;
import dz.sh.hidra.modules.organization.application.contract.assets.AssetsOrganizationUnitReferenceContract;
import java.time.Instant;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class MaintenanceWorkOrderSemanticRemediationTest {
    final MaintenancePlanJpaRepository maintenancePlanId=mock(MaintenancePlanJpaRepository.class);
    MaintenanceWorkOrderReferenceValidation validation(MaintenanceWorkOrderActorReferenceContract actors,MaintenanceWorkOrderWorkflowReferenceContract workflow,MaintenanceRecommendationReferenceContract recommendations,AssetsOrganizationUnitReferenceContract units) {return new MaintenanceWorkOrderReferenceValidation(actors,workflow,recommendations,units,maintenancePlanId);}
    MaintenanceWorkOrderReferenceValidation permissive() {return validation((id,at)->true,(id,target)->true, id -> true, id -> true);}
    static MaintenanceWorkOrder model(Map<String,String> references) {
        try {
            var components=MaintenanceWorkOrder.class.getRecordComponents(); var types=new Class<?>[components.length]; var values=new Object[components.length];
            for(int i=0;i<components.length;i++) {var c=components[i]; types[i]=c.getType();
                if(c.getType()==String.class) values[i]=references.containsKey(c.getName())?references.get(c.getName()):switch(c.getName()) {case "id","workOrderNumber","maintainableAssetId","workOrderTypeId" -> c.getName().equals("id")?"target":"required"; case "title" -> "Title"; default -> null;};
                else if(c.getType()==Instant.class) values[i]=Instant.parse("2026-10-08T00:00:00Z");
                else if(c.getType().isEnum()) values[i]=Enum.valueOf(c.getType().asSubclass(Enum.class),"DRAFT");
            }
            return MaintenanceWorkOrder.class.getDeclaredConstructor(types).newInstance(values);
        } catch(InvocationTargetException e) {throw (RuntimeException)e.getCause();} catch(ReflectiveOperationException e) {throw new AssertionError(e);}
    }
    @Test void nullOptionalReferencesRemainAllowed() {assertDoesNotThrow(() -> permissive().validate(model(Map.of()),null));}
    @Test void missingLocalReferencesFailClosed() {
        assertThrows(InvalidAssetsValueException.class,() -> permissive().validate(model(Map.of("maintenancePlanId","missing")),null));
    }
    @Test void populatedActorsMustResolveThroughIdentity() {
        assertThrows(InvalidAssetsValueException.class,() -> validation((id,at)->false,(id,target)->true, id -> true, id -> true).validate(model(Map.of("assignedActorId","missing")),null));
        assertThrows(InvalidAssetsValueException.class,() -> validation((id,at)->false,(id,target)->true, id -> true, id -> true).validate(model(Map.of("createdByActorId","missing")),null));
    }
    @Test void wrongWorkflowContextFailsClosed() {assertThrows(InvalidAssetsValueException.class,() -> validation((id,at)->true,(id,target)->false, id -> true, id -> true).validate(model(Map.of("workflowInstanceId","workflow")),null));}
    @Test void ownerFailurePropagates() {assertThrows(IllegalStateException.class,() -> validation((id,at)->{throw new IllegalStateException("owner unavailable");},(id,target)->true, id -> true, id -> true).validate(model(Map.of("assignedActorId","actor")),null));}
    @Test void unchangedHistoricalActorAndWorkflowArePreserved() {
        var history=model(Map.of("assignedActorId","historical","workflowInstanceId","historical-workflow"));
        assertDoesNotThrow(() -> validation((id,at)->false,(id,target)->false, id -> true, id -> true).validate(history,history));
    }
    @Test void changedHistoricalActorRequiresFreshEligibility() {
        assertThrows(InvalidAssetsValueException.class,() -> validation((id,at)->false,(id,target)->true, id -> true, id -> true).validate(model(Map.of("assignedActorId","new-actor")),model(Map.of("assignedActorId","historical"))));
    }
    @Test void adapterDeniesBeforePersisting() {
        var repository=mock(MaintenanceWorkOrderJpaRepository.class); when(repository.findByIdForUpdate("target")).thenReturn(Optional.empty());
        var adapter=new JpaMaintenanceWorkOrderRepositoryAdapter(repository,validation((id,at)->false,(id,target)->true, id -> true, id -> true));
        assertThrows(InvalidAssetsValueException.class,() -> adapter.save(model(Map.of("assignedActorId","missing"))));
        verify(repository,never()).saveAndFlush(any());
    }
    @Test void recommendationAndUnitAreOwnerValidated() {
        assertThrows(InvalidAssetsValueException.class,() -> validation((id,at)->true,(id,target)->true,id->false,id->true).validate(model(Map.of("sourceRecommendationId","missing")),null));
        assertThrows(InvalidAssetsValueException.class,() -> validation((id,at)->true,(id,target)->true,id->true,id->false).validate(model(Map.of("assignedOrganizationUnitId","missing")),null));
    }
    @Test void blankTitleFailsBeforePersistence() {assertThrows(InvalidAssetsValueException.class,() -> model(Map.of("title"," ")));}
    @Test void localPlanExistenceDoesNotInventAssetCorrelation() {when(maintenancePlanId.existsById("plan")).thenReturn(true);assertDoesNotThrow(() -> permissive().validate(model(Map.of("maintenancePlanId","plan")),null));}
}
