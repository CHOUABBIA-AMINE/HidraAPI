/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : AbsoluteDeviationReportJsonV1Test
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Test
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.service
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.domain.service;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Projection;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AbsoluteDeviationReportJsonV1Test {
    @Test void canonicalBytesRetainVersionedSourceAndNormalizeDecimal() {
        var at=Instant.parse("2026-11-18T01:00:00Z");
        var p=new Projection("run","definition","1",new BigDecimal("5.000"),"bar",
                "evaluation","rule",1,"target","revision","approval","trusted","raw","assessment",
                "binding",at,at);
        byte[] data=AbsoluteDeviationReportJsonV1.render(p);
        assertArrayEquals(data,AbsoluteDeviationReportJsonV1.render(p));
        assertEquals("9a4e00bca4e308db53c018a9bb187c9545b661072a284eb3246c6b2877850a01",AbsoluteDeviationReportJsonV1.sha256(data));
        assertTrue(new String(data,StandardCharsets.UTF_8).endsWith("\n"));
        assertThrows(IllegalArgumentException.class,()->new Projection("run","v","1",
                new BigDecimal("1E+300"),"bar","evaluation","rule",1,"target","revision","approval",
                "trusted","raw","assessment","binding",at,at));
    }
}
