/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedTopologyInstalledUnitAuthorizationParticipantTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Checks paired contract and mocked invocation mechanics without asserting database provenance.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import java.time.Instant;
import java.util.*;

class ProtectedTopologyInstalledUnitAuthorizationParticipantTest {
    static Request request(Operation op) {
        return new Request(op,"company","unit",
            op==Operation.REGISTER_UNIT || op==Operation.READ_UNIT ? null : "installation",
            op==Operation.RELOCATE ? "replacement" : null,
            switch(op) { case CLOSE_INSTALLATION, RELOCATE, READ_INSTALLATION, READ_HISTORY -> "source"; default -> null; },
            op==Operation.RECORD_INSTALLATION || op==Operation.RELOCATE ? "destination" : null);
    }
    @Test void sevenExactMappingsNeverAliasStationAuthority() {
        var codes=List.of("register","read","record-installation","close-installation","relocate","read-installation","read-history");
        for(int i=0;i<Operation.values().length;i++)
            assertThat(TopologyInstalledUnitAuthorizationContract.permissionCode(Operation.values()[i]))
                .isEqualTo("topology:installed-unit:"+codes.get(i));
    }
    @Test void operationShapesAndCompanyOnlyUnitReadsAreExact() {
        assertThat(request(Operation.REGISTER_UNIT).requirements()).hasSize(1);
        assertThat(request(Operation.READ_UNIT).requirements()).allMatch(r->r.scope()==Scope.COMPANY);
        for(var op:List.of(Operation.RECORD_INSTALLATION,Operation.CLOSE_INSTALLATION,Operation.READ_INSTALLATION,Operation.READ_HISTORY))
            assertThat(request(op).requirements()).hasSize(2);
        assertThatThrownBy(()->new Request(Operation.READ_UNIT,"company","unit","extra",null,null,null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Request(Operation.RECORD_INSTALLATION,"company","unit","i",null,"source",null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Request(Operation.RELOCATE,"company","unit","i","i","s","d")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Request(Operation.CLOSE_INSTALLATION,"company","unit","i",null,null,null)).isInstanceOf(IllegalArgumentException.class);
    }
    @Test void malformedAndOversizedReferencesCannotBeNormalizedIntoAuthority() {
        for(String id:List.of("", " ", " unit", "unit ", "x".repeat(81)))
            assertThatThrownBy(()->new Request(Operation.READ_UNIT,"company",id,null,null,null,null)).isInstanceOf(IllegalArgumentException.class);
        assertThat(new Request(Operation.READ_UNIT,"company","𐀀".repeat(80),null,null,null,null).requirements()).hasSize(1);
    }
    @Test void relocationBindsBothResourcesAndScopesInCanonicalOrder() {
        var r=request(Operation.RELOCATE);
        assertThat(r.requirements()).hasSize(6).doesNotHaveDuplicates();
        assertThat(r.requirements().stream().map(Requirement::resourceId).distinct()).containsExactly("installation","replacement");
        assertThat(r.requirements().stream().filter(x->x.scope()==Scope.FACILITY).map(Requirement::scopeReference).distinct())
            .containsExactly("destination","source");
        var same=new Request(Operation.RELOCATE,"company","unit","installation","replacement","same","same");
        assertThat(same.requirements()).hasSize(4);
        assertThat(same.sourceFacilityId()).isEqualTo(same.destinationFacilityId());
        assertThatThrownBy(()->same.requirements().clear()).isInstanceOf(UnsupportedOperationException.class);
    }
    @Test void denyDominatesIndeterminateAndNoScalarMintsAdmission() {
        var r=request(Operation.RELOCATE); var at=Instant.parse("2026-10-28T00:00:00Z");
        var decisions=new ArrayList<ResourceDecision>();
        for(var requirement:r.requirements())decisions.add(new ResourceDecision(requirement,Decision.PERMIT));
        decisions.set(0,new ResourceDecision(r.requirements().get(0),Decision.INDETERMINATE));
        var c=new Contribution(r,decisions,"binding","session","user",at,at.plusSeconds(1),1,1);
        assertThat(c.overallDecision()).isEqualTo(Decision.INDETERMINATE);
        decisions.set(1,new ResourceDecision(r.requirements().get(1),Decision.DENY));
        assertThat(new Contribution(r,decisions,"b","s","u",at,at.plusSeconds(1),1,1).overallDecision()).isEqualTo(Decision.DENY);
        assertThat(Admission.class.isInstance(c)).isFalse();
        assertThatThrownBy(()->new Contribution(r,List.of(),"b","s","u",at,at.plusSeconds(1),1,1)).isInstanceOf(IllegalArgumentException.class);
    }
    @Test void forgedTicketsAndMissingTransactionsCannotReachNativeReads() {
        var nativeOwner=mock(ProtectedOperationIdentityParticipant.class);
        var reader=mock(JdbcProtectedAuthorizationEvidenceReader.class);
        var adapter=new ProtectedTopologyInstalledUnitAuthorizationParticipant(nativeOwner,reader,mock(JdbcTemplate.class));
        assertThatThrownBy(()->adapter.acquire(new Prepared(){},request(Operation.READ_UNIT))).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()->adapter.contribute(new Admission(){})).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()->adapter.requireCurrent(new Admission(){})).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()->adapter.finalFences(null)).isInstanceOf(RuntimeException.class);
        verifyNoInteractions(nativeOwner,reader);
    }
}
