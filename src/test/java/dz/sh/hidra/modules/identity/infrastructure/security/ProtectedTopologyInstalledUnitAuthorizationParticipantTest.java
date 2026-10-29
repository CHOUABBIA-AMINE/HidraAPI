/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedTopologyInstalledUnitAuthorizationParticipantTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
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
        for(int i=0;i<codes.size();i++)
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
    @Test void replacementRequiresTwoDistinctResourceBoundPermissionCodes() {
        assertThat(TopologyInstalledUnitAuthorizationContract.permissionCode(Operation.REPLACE,"INSTALLED_UNIT"))
            .isEqualTo("topology:installed-unit:replace-unit");
        assertThat(TopologyInstalledUnitAuthorizationContract.permissionCode(Operation.REPLACE,"INSTALLED_UNIT_INSTALLATION"))
            .isEqualTo("topology:installed-unit:replace-installation");
        assertThatThrownBy(()->TopologyInstalledUnitAuthorizationContract.permissionCode(Operation.REPLACE)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->TopologyInstalledUnitAuthorizationContract.permissionCode(Operation.REPLACE,"STATION_GRAPH_REVISION")).isInstanceOf(IllegalArgumentException.class);
        for(var op:List.of(Operation.REGISTER_UNIT,Operation.READ_UNIT,Operation.RECORD_INSTALLATION,Operation.CLOSE_INSTALLATION,Operation.RELOCATE,Operation.READ_INSTALLATION,Operation.READ_HISTORY)) {
            String type=op==Operation.REGISTER_UNIT || op==Operation.READ_UNIT?"INSTALLED_UNIT":"INSTALLED_UNIT_INSTALLATION";
            assertThat(TopologyInstalledUnitAuthorizationContract.permissionCode(op,type)).isEqualTo(TopologyInstalledUnitAuthorizationContract.permissionCode(op));
            assertThatThrownBy(()->TopologyInstalledUnitAuthorizationContract.permissionCode(op,type.equals("INSTALLED_UNIT")?"INSTALLED_UNIT_INSTALLATION":"INSTALLED_UNIT")).isInstanceOf(IllegalArgumentException.class);
        }
    }
    @Test void replacementBindsBothUnitsAndInstallationsWithSixImmutableRequirements() {
        var r=new Request(Operation.REPLACE,"company","old-unit","old-install","new-install","facility","facility","new-unit");
        assertThat(r.requirements()).hasSize(6).doesNotHaveDuplicates();
        assertThat(r.requirements().stream().filter(x->x.resourceType().equals("INSTALLED_UNIT")).toList())
            .containsExactly(new Requirement("INSTALLED_UNIT","new-unit",Scope.COMPANY,"company"),new Requirement("INSTALLED_UNIT","old-unit",Scope.COMPANY,"company"));
        assertThat(r.requirements().stream().filter(x->x.resourceType().equals("INSTALLED_UNIT_INSTALLATION")).map(Requirement::resourceId).distinct())
            .containsExactly("new-install","old-install");
        assertThat(r.requirements().stream().filter(x->x.scope()==Scope.FACILITY)).hasSize(2).allMatch(x->x.scopeReference().equals("facility"));
        assertThatThrownBy(()->r.requirements().clear()).isInstanceOf(UnsupportedOperationException.class);
        var sameText=new Request(Operation.REPLACE,"company","one","one","two","facility","facility","two");
        assertThat(sameText.requirements()).hasSize(6); // Cross-type identity text never collapses two authority targets.
    }
    @Test void malformedReplacementAndLegacySecondUnitDescriptorsAreRejected() {
        for(String unit:Arrays.asList(null,"old-unit","", " padded", "x".repeat(81)))
            assertThatThrownBy(()->new Request(Operation.REPLACE,"company","old-unit","old-install","new-install","facility","facility",unit)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Request(Operation.REPLACE,"company","old-unit",null,"new-install","facility","facility","new-unit")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Request(Operation.REPLACE,"company","old-unit","old-install","old-install","facility","facility","new-unit")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Request(Operation.REPLACE,"company","old-unit","old-install","new-install",null,"facility","new-unit")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Request(Operation.REPLACE,"company","old-unit","old-install","new-install","left","right","new-unit")).isInstanceOf(IllegalArgumentException.class);
        for(var op:List.of(Operation.REGISTER_UNIT,Operation.READ_UNIT,Operation.RECORD_INSTALLATION,Operation.CLOSE_INSTALLATION,Operation.RELOCATE,Operation.READ_INSTALLATION,Operation.READ_HISTORY)) {
            var old=request(op);
            assertThatThrownBy(()->new Request(op,old.companyPartyId(),old.unitId(),old.installationId(),old.replacementInstallationId(),old.sourceFacilityId(),old.destinationFacilityId(),"new-unit")).isInstanceOf(IllegalArgumentException.class);
        }
    }
    @Test void legacyRequestConstructorRetainsExactSevenOperationDescriptors() {
        for(var op:List.of(Operation.REGISTER_UNIT,Operation.READ_UNIT,Operation.RECORD_INSTALLATION,Operation.CLOSE_INSTALLATION,Operation.RELOCATE,Operation.READ_INSTALLATION,Operation.READ_HISTORY)) {
            var old=request(op);
            var current=new Request(op,old.companyPartyId(),old.unitId(),old.installationId(),old.replacementInstallationId(),old.sourceFacilityId(),old.destinationFacilityId(),null);
            assertThat(old).isEqualTo(current);assertThat(old.requirements()).isEqualTo(current.requirements());assertThat(old.replacementUnitId()).isNull();
        }
    }
    @Test void replacementDenialOfAnyRequiredTargetDominatesOtherDecisions() {
        var r=new Request(Operation.REPLACE,"company","old-unit","old-install","new-install","facility","facility","new-unit");var at=Instant.parse("2026-10-29T00:00:00Z");
        for(var denied:r.requirements()) {
            var decisions=r.requirements().stream().map(q->new ResourceDecision(q,q.equals(denied)?Decision.DENY:Decision.PERMIT)).toList();
            assertThat(new Contribution(r,decisions,"binding","session","user",at,at.plusSeconds(1),1,1).overallDecision()).isEqualTo(Decision.DENY);
        }
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
