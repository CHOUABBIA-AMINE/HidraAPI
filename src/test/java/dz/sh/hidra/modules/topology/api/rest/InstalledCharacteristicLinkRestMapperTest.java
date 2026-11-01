/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkRestMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Proves exact wire-only draft mapping and nonqualified outputs.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.mapper.InstalledCharacteristicLinkRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.InstalledCharacteristicLinkRequests.CharacteristicLinkDraftSave;
import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InstalledCharacteristicLinkRestMapperTest {
    private static final Instant AT=Instant.parse("2026-01-01T00:00:00Z");
    private static final String HASH="a".repeat(64);
    private static CharacteristicLinkDraftSave save(){
        return new CharacteristicLinkDraftSave("company","req-1",0L,1L,"source","rev-1",HASH,
            "COMPRESSOR_MAP",AT,null,AT,"synthetic:evidence",null);
    }
    @Test void mapsExactDraftWithoutApprovalOrSourcePayload(){
        var c=InstalledCharacteristicLinkRestMapper.save("unit","link",save());
        assertEquals(Operation.SAVE_DRAFT,c.operation());
        assertEquals("unit",c.installedUnitId());
        assertEquals("link",c.linkId());
        assertEquals("rev-1",c.draft().sourceRevisionId());
        assertEquals(HASH,c.draft().sourceSha256());
        assertNull(c.draft().installationId());
        var p=InstalledCharacteristicLinkRestMapper.from(new Result(c.draft(),List.of(),false,null));
        assertEquals("DRAFT",p.link().state());
        assertEquals(1L,p.link().linkRevision());
    }
    @Test void rejectsMalformedCanonicalDigestAndInvalidKind(){
        var s=save();
        assertThrows(RuntimeException.class,()->InstalledCharacteristicLinkRestMapper.save("u","l",
            new CharacteristicLinkDraftSave(s.companyPartyId(),s.requestId(),s.expectedEpoch(),s.linkRevision(),
                s.sourceId(),s.sourceRevisionId(),"NOT-A-DIGEST",s.kind(),s.effectiveFrom(),
                s.effectiveUntil(),s.recordedAt(),s.evidenceReference(),s.installationId())));
        assertThrows(RuntimeException.class,()->InstalledCharacteristicLinkRestMapper.save("u","l",
            new CharacteristicLinkDraftSave(s.companyPartyId(),s.requestId(),s.expectedEpoch(),s.linkRevision(),
                s.sourceId(),s.sourceRevisionId(),s.sourceSha256(),"APPROVED",
                s.effectiveFrom(),s.effectiveUntil(),s.recordedAt(),s.evidenceReference(),s.installationId())));
    }
    @Test void rejectsStaleRevisionAndMissingRequestId(){
        var s=save();
        assertThrows(RuntimeException.class,()->InstalledCharacteristicLinkRestMapper.save("u","l",
            new CharacteristicLinkDraftSave(s.companyPartyId(),s.requestId(),2L,1L,s.sourceId(),s.sourceRevisionId(),
                s.sourceSha256(),s.kind(),s.effectiveFrom(),s.effectiveUntil(),s.recordedAt(),
                s.evidenceReference(),s.installationId())));
        assertThrows(RuntimeException.class,()->InstalledCharacteristicLinkRestMapper.save("u","l",
            new CharacteristicLinkDraftSave(s.companyPartyId(),null,0L,1L,s.sourceId(),s.sourceRevisionId(),
                s.sourceSha256(),s.kind(),s.effectiveFrom(),s.effectiveUntil(),s.recordedAt(),
                s.evidenceReference(),s.installationId())));
    }
    @Test void exactHistoricalRevisionAndExclusiveCursorAreBounded(){
        assertEquals(7L,InstalledCharacteristicLinkRestMapper.revision("u","l","c",7L).revision());
        var c=InstalledCharacteristicLinkRestMapper.history("u","l","c",5,8L);
        assertEquals(5,c.limit());assertEquals(8L,c.beforeRevision());
        assertThrows(RuntimeException.class,()->InstalledCharacteristicLinkRestMapper.history("u","l","c",101,null));
        assertThrows(RuntimeException.class,()->InstalledCharacteristicLinkRestMapper.revision("u","l","c",0L));
    }
    @Test void authorizedReadOutputsImmutableHistoryAndNeverQualifies(){
        var link=InstalledCharacteristicLinkRestMapper.save("unit","link",save()).draft();
        var result=InstalledCharacteristicLinkRestMapper.from(new Result(null,List.of(link),false,null));
        assertEquals(1,result.history().size());
        assertEquals("DRAFT",result.history().getFirst().state());
        assertThrows(UnsupportedOperationException.class,()->result.history().clear());
    }
}
