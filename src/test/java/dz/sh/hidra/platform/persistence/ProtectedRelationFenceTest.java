/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedRelationFenceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence
 *
 * @Description : Checks that relation requirements remain immutable validated data without SQL authority.
 *
 */
package dz.sh.hidra.platform.persistence;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ProtectedRelationFenceTest {
    @Test void rejectsUnqualifiedOrUnsafeRelationNames(){
        for(String n:new String[]{"table","public.x;DELETE","public.x--","other.x","public.\"x\""})
            assertThrows(IllegalArgumentException.class,()->new ProtectedRelationFence(n,ProtectedRelationFence.Mode.SHARE));
    }
    @Test void rejectsUnsupportedOrMissingLockMode(){
        assertThrows(IllegalArgumentException.class,()->new ProtectedRelationFence("public.x",null));
        assertThrows(IllegalArgumentException.class,()->ProtectedRelationFence.Mode.valueOf("ACCESS_EXCLUSIVE"));
    }
    @Test void preservesExactImmutableRequirementData(){
        var f=new ProtectedRelationFence("public.hidra_identity_user",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE);
        assertEquals("public.hidra_identity_user",f.relation());assertEquals("ShareRowExclusiveLock",f.mode().catalog());
        assertTrue(ProtectedRelationFence.class.isRecord());assertEquals(f,new ProtectedRelationFence(f.relation(),f.mode()));
    }
}
