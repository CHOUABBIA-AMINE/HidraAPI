/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphRevisionRepositoryPortTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Tests stored station graph metadata shape without inventing a persistence adapter.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationInternalGraphRevisionRepositoryPortTest {
    private static final String FORMAT = "HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1";
    private StationInternalGraphRevision revision() {
        var graph = new StationInternalGraph("synthetic-station", "r1", StationInternalGraph.Status.DRAFT,
                List.of(), List.of(), List.of());
        return new StationInternalGraphRevision(graph.facilityId(), graph.revisionId(), graph,
                StationInternalGraphRevision.SourceClassification.SYNTHETIC, Instant.EPOCH, Instant.EPOCH,
                null, null, null, StationInternalGraphRevision.Qualification.DRAFT);
    }
    @Test void retainsExactRevisionAndValidMetadataWithoutAuthenticityClaim() {
        var r = revision(); var stored = new StationInternalGraphRevisionRepositoryPort.StoredRevision(r, FORMAT, "a".repeat(64));
        assertSame(r, stored.revision()); assertEquals(FORMAT, stored.payloadFormat()); assertEquals("a".repeat(64), stored.sha256());
    }
    @Test void rejectsNullRevisionAndWrongFormat() {
        assertThrows(InvalidTopologyValueException.class,
                () -> new StationInternalGraphRevisionRepositoryPort.StoredRevision(null, FORMAT, "a".repeat(64)));
        for (String format : Arrays.asList(null, "", "HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1", FORMAT + " "))
            assertThrows(InvalidTopologyValueException.class,
                    () -> new StationInternalGraphRevisionRepositoryPort.StoredRevision(revision(), format, "a".repeat(64)));
    }
    @Test void rejectsMalformedDigestShape() {
        for (String digest : Arrays.asList(null, "", "a".repeat(63), "a".repeat(65), "A".repeat(64), "g".repeat(64), " ".repeat(64)))
            assertThrows(InvalidTopologyValueException.class,
                    () -> new StationInternalGraphRevisionRepositoryPort.StoredRevision(revision(), FORMAT, digest));
    }
}
