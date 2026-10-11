/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphRevisionValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Reports deterministic structural revision qualification diagnostics.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.Qualification;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Status;
import java.util.List;
import java.util.TreeSet;

/** This is source qualification only; it performs no database or actor approval. */
public final class StationInternalGraphRevisionValidator {
    private final StationInternalGraphValidator graphValidator = new StationInternalGraphValidator();

    public record Report(List<String> diagnostics) {
        public Report { diagnostics = List.copyOf(diagnostics); }
        public boolean qualified() { return diagnostics.isEmpty(); }
    }

    public Report inspect(StationInternalGraphRevision revision) {
        if (revision == null) throw new InvalidTopologyValueException("Station graph revision required.");
        var diagnostics = new TreeSet<String>();
        if (revision.qualification() == Qualification.DRAFT)
            diagnostics.add("REVISION_DRAFT");
        if (revision.graph().status() != Status.COMPLETE)
            diagnostics.add("GRAPH_INCOMPLETE");
        if (revision.evidenceReference() == null)
            diagnostics.add("EVIDENCE_MISSING");
        var graphReport = graphValidator.inspect(revision.graph());
        for (var diagnostic : graphReport.diagnostics())
            diagnostics.add("GRAPH:" + diagnostic);
        if (revision.qualification() == Qualification.STRUCTURALLY_QUALIFIED && !diagnostics.isEmpty())
            throw new InvalidTopologyValueException("Unqualified station graph revision: " + diagnostics);
        return new Report(List.copyOf(diagnostics));
    }
}
