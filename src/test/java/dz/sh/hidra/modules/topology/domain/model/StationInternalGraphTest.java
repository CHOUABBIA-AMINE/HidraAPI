/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Exercises synthetic immutable station graph domain boundaries.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.*;

class StationInternalGraphTest {
    @Test void preservesDistinctSyntheticStationUnitsAndImmutableInputs() {
        for (int station = 1; station <= 3; station++) {
            var units = new ArrayList<Unit>();
            for (int i = 1; i <= 3; i++) units.add(new Unit("unit-" + station + "-" + i,
                    "station-" + station, "in-" + i, "out-" + i));
            var graph = new StationInternalGraph("station-" + station, "synthetic-r1", Status.DRAFT,
                    List.of(new Header("s", HeaderKind.SUCTION, "s"), new Header("d", HeaderKind.DISCHARGE, "d")),
                    units, List.of());
            units.clear();
            assertEquals(3, graph.units().size());
            assertThrows(UnsupportedOperationException.class, () -> graph.units().clear());
            assertEquals(8, graph.portIds().size());
        }
    }

    @Test void rejectsInvalidIdentityDuplicateAndCrossStation() {
        assertThrows(InvalidTopologyValueException.class, () -> new Unit("u", "s", "p", "p"));
        assertThrows(InvalidTopologyValueException.class, () -> new Edge("e", "x", "x"));
        assertThrows(InvalidTopologyValueException.class, () -> new Header(" ", HeaderKind.SUCTION, "p"));
        assertThrows(InvalidTopologyValueException.class, () -> new StationInternalGraph("s", "r", Status.DRAFT,
                List.of(new Header("h", HeaderKind.SUCTION, "p"), new Header("h", HeaderKind.DISCHARGE, "q")),
                List.of(), List.of()));
        assertThrows(InvalidTopologyValueException.class, () -> new StationInternalGraph("s", "r", Status.DRAFT,
                List.of(), List.of(new Unit("u", "foreign", "in", "out")), List.of()));
    }
}
