/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentTargetLookupServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.application.service;
import dz.sh.hidra.modules.documents.application.contract.target.DocumentsOwnedTargetLookup;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DocumentTargetLookupServiceTest {
    DocumentsOwnedTargetLookup owner(boolean exists){return new DocumentsOwnedTargetLookup(){
        public String module(){return "topology";}public Set<String> targetTypeCodes(){return Set.of("PIPELINE");}
        public Optional<Target> resolve(String type,String id){return exists?Optional.of(new Target(id,"OWNER","Owner label")):Optional.empty();}
    };}
    @Test void unknownMissingAndAmbiguousOwnersDeny(){
        assertThrows(IllegalArgumentException.class,()->new DocumentTargetLookupService(List.of()).requireTarget("topology","PIPELINE","p"));
        assertThrows(IllegalArgumentException.class,()->new DocumentTargetLookupService(List.of(owner(false))).requireTarget("topology","PIPELINE","p"));
        assertThrows(IllegalArgumentException.class,()->new DocumentTargetLookupService(List.of(owner(true),owner(true))).requireTarget("topology","PIPELINE","p"));
    }
    @Test void canonicalIdentityRequiredAndSnapshotsComeFromOwner(){
        var service=new DocumentTargetLookupService(List.of(owner(true)));
        assertEquals("OWNER",service.requireTarget("topology","PIPELINE"," p ").code());
        assertThrows(IllegalArgumentException.class,()->service.requireTarget("topology",null,"p"));
        assertThrows(IllegalArgumentException.class,()->service.requireTarget("incidents","PIPELINE","p"));
        var wrong=new DocumentsOwnedTargetLookup(){public String module(){return "topology";}public Set<String> targetTypeCodes(){return Set.of("PIPELINE");}public Optional<Target> resolve(String t,String id){return Optional.of(new Target("other",null,null));}};
        assertThrows(IllegalArgumentException.class,()->new DocumentTargetLookupService(List.of(wrong)).requireTarget("topology","PIPELINE","p"));
    }
}
