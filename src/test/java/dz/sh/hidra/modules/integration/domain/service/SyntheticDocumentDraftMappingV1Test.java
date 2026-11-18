/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticDocumentDraftMappingV1Test
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Test
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.service
 * @Description : Exact canonical title, digest and immutable attempt invariants.
 */
package dz.sh.hidra.modules.integration.domain.service;

import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Attempt;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Phase;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Scope;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SyntheticDocumentDraftMappingV1Test {
    @Test void canonicalTitlePreservesSingleFieldDigestAndEscapesPayload() {
        var first=SyntheticDocumentDraftMappingV1.projection("  Cafe\u0301 \"GZ2\"  ");
        var second=SyntheticDocumentDraftMappingV1.projection("Café \"GZ2\"");
        assertEquals(second,first);
        assertEquals("{\"titleFr\":\"Café \\\"GZ2\\\"\"}",first);
        assertEquals(SyntheticDocumentDraftMappingV1.sha256(first),
                SyntheticDocumentDraftMappingV1.sha256(second));
        assertNotEquals(SyntheticDocumentDraftMappingV1.mappingDigest("m","s","a","type","class"),
                SyntheticDocumentDraftMappingV1.mappingDigest("m","s","b","type","class"));
    }
    @Test void invalidControlAndOversizedValuesFailClosed() {
        assertThrows(IllegalArgumentException.class,()->SyntheticDocumentDraftMappingV1.title(" "));
        assertThrows(IllegalArgumentException.class,()->SyntheticDocumentDraftMappingV1.title("a\n"));
        assertThrows(IllegalArgumentException.class,()->SyntheticDocumentDraftMappingV1.title("a".repeat(161)));
        assertThrows(IllegalArgumentException.class,()->new Scope(" x","y"));
        assertThrows(IllegalArgumentException.class,()->SyntheticExchange.bounded("t\t",80));
    }
    @Test void attemptOrdinalsAndTerminalReceiverShapeAreStrict() {
        Instant now=Instant.now();
        assertThrows(IllegalArgumentException.class,()->new Attempt("id","ex",3,"actor","key",null,
                "run","msg","in",null,Phase.CLAIMED,null,null,now,now.plusSeconds(5),null));
        assertThrows(IllegalArgumentException.class,()->new Attempt("id","ex",1,"actor","key",null,
                "run","msg","in",null,Phase.SUCCEEDED,null,null,now,now.plusSeconds(5),now));
    }
}