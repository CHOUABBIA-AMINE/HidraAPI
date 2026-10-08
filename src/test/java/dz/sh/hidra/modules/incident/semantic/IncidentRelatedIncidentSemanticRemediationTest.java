/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRelatedIncidentSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.semantic
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.incident.semantic;

import dz.sh.hidra.modules.incident.domain.model.IncidentRelatedIncident;
import dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class IncidentRelatedIncidentSemanticRemediationTest {
    @Test void selfLinksAreRejectedAfterNormalization() {assertThrows(InvalidIncidentValueException.class,() -> new IncidentRelatedIncident("link"," incident ","incident","relationship",null,"actor",Instant.now()));}
    @Test void creationTimeIsMandatory() {assertThrows(InvalidIncidentValueException.class,() -> new IncidentRelatedIncident("link","a","b","relationship",null,"actor",null));}
    @Test void preservesDirectionalRelationshipAndOptionalComment() {var x=new IncidentRelatedIncident("link","b","a","relationship",null,"actor",Instant.now());assertEquals("b",x.incidentId());assertNull(x.comment());}
}
