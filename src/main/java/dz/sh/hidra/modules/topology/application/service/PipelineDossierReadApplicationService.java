/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineDossierReadApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Enforces live regional resource authorization for read-only pipeline dossiers.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyRegionalAccessContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyRegionalAccessContract.Request;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyRegionalAccessContract.ResourceKind;
import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineReadUseCase;
import dz.sh.hidra.modules.topology.application.port.in.PipelineDossierReadUseCase;
import dz.sh.hidra.modules.topology.application.port.in.PipelineDossierReadUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.PipelineDossierReadPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineDossierReadPort.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.BooleanSupplier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * T35 is intentionally not a replacement for the T07 publication authority.
 * No result or authority is cached between requests. Candidate identities
 * precede private-field hydration; every successful response rechecks the
 * exact parent, all contributing authorization decisions and source rows.
 */
@Service
public class PipelineDossierReadApplicationService implements PipelineDossierReadUseCase {
    private static final int BATCH = 250;
    private static final String UNAVAILABLE = "UNAVAILABLE";
    private static final String LIVE = "TOPOLOGY_LIVE_CATALOG";
    private final PipelineDossierReadPort read;
    private final TopologyRegionalAccessContract access;
    private final EngineeringBaselineReadUseCase publications;

    public PipelineDossierReadApplicationService(PipelineDossierReadPort read,
            TopologyRegionalAccessContract access, EngineeringBaselineReadUseCase publications) {
        this.read = Objects.requireNonNull(read);
        this.access = Objects.requireNonNull(access);
        this.publications = Objects.requireNonNull(publications);
    }

    @Override
    @Transactional(readOnly = true)
    public Pipeline pipeline(String companyPartyId, String pipelineId) {
        Context c = context(companyPartyId, pipelineId);
        PipelineRow row = read.pipeline(c.association.pipelineId()).orElseThrow(PipelineDossierReadApplicationService::missing);
        c.checks.add(() -> Objects.equals(row, read.pipeline(c.association.pipelineId()).orElse(null)));
        String system = permit(c, ResourceKind.PIPELINE_SYSTEM, c.association.pipelineSystemId())
                ? c.association.pipelineSystemId() : null;
        Source source = live(row.id(), row.updatedAt());
        Pipeline result = new Pipeline(row.id(), row.code(), row.nameAr(), row.nameFr(), row.nameEn(),
                row.typeCode(), row.status(), system,
                quantity(row.nominalDiameter(), row.diameterUnit(), "NOMINAL_DIAMETER", UNAVAILABLE, source),
                quantity(row.designPressure(), row.pressureUnit(), "DESIGN_PRESSURE", UNAVAILABLE, source),
                quantity(row.declaredLengthKm(), "km", "DECLARED_LENGTH", UNAVAILABLE, source),
                source, associationSource(c.association));
        verify(c);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Section> sections(String companyPartyId, String pipelineId, Integer page, Integer size) {
        Context c = context(companyPartyId, pipelineId);
        int p = page(page), s = size(size);
        List<Section> found = new ArrayList<>();
        for (SectionCandidate candidate : allSections(c.association.pipelineId())) {
            if (!c.association.pipelineId().equals(candidate.pipelineId())
                    || !permit(c, ResourceKind.PIPELINE_SEGMENT, candidate.id())) continue;
            SectionRow row = read.section(candidate.id()).orElse(null);
            if (!consistent(candidate, row)) continue;
            c.checks.add(() -> Objects.equals(row, read.section(candidate.id()).orElse(null))
                    && consistent(candidate, row));
            found.add(section(c, row));
        }
        found.sort(Comparator.comparing(Section::id));
        Page<Section> result = page(found, p, s);
        verify(c);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Section section(String companyPartyId, String pipelineId, String sectionId) {
        Context c = context(companyPartyId, pipelineId);
        String id = selector(sectionId);
        require(c, ResourceKind.PIPELINE_SEGMENT, id);
        SectionRow row = read.section(id).orElseThrow(PipelineDossierReadApplicationService::missing);
        if (allSections(c.association.pipelineId()).stream()
                .noneMatch(v -> v.id().equals(id) && consistent(v, row))) throw missing();
        c.checks.add(() -> Objects.equals(row, read.section(id).orElse(null))
                && allSections(c.association.pipelineId()).stream().anyMatch(v -> v.id().equals(id) && consistent(v, row)));
        Section result = section(c, row);
        verify(c);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Connection> connections(String companyPartyId, String pipelineId, Integer page, Integer size) {
        Context c = context(companyPartyId, pipelineId);
        int p = page(page), s = size(size);
        List<Connection> found = new ArrayList<>();
        for (ConnectionCandidate candidate : allConnections(c.association.pipelineId())) {
            if (!permit(c, ResourceKind.PIPELINE_SEGMENT, candidate.sectionId())) continue;
            SectionRow parent = read.section(candidate.sectionId()).orElse(null);
            if (parent == null || !belongs(c, parent)
                    || !sameEndpoints(candidate.fromNodeId(), candidate.toNodeId(),
                            parent.fromNodeId(), parent.toNodeId())) continue;
            if (!eligibleNode(c, candidate.fromNodeId())
                    || !eligibleNode(c, candidate.toNodeId())) continue;
            ConnectionRow row = read.connection(candidate.id()).orElse(null);
            if (row == null || !Objects.equals(row.sectionId(), candidate.sectionId())
                    || !Objects.equals(row.fromNodeId(), candidate.fromNodeId())
                    || !Objects.equals(row.toNodeId(), candidate.toNodeId())) continue;
            c.checks.add(() -> Objects.equals(row, read.connection(candidate.id()).orElse(null))
                    && Objects.equals(parent, read.section(candidate.sectionId()).orElse(null))
                    && allSections(c.association.pipelineId()).stream()
                        .anyMatch(v -> v.id().equals(parent.id()) && consistent(v, parent))
                    && allConnections(c.association.pipelineId()).stream()
                        .anyMatch(v -> candidate.equals(v)));
            found.add(new Connection(row.id(), row.code(), row.type(), row.sectionId(),
                    row.fromNodeId(), row.toNodeId(), live(row.id(), row.updatedAt())));
        }
        found.sort(Comparator.comparing(Connection::id));
        Page<Connection> result = page(found, p, s);
        verify(c);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Equipment> equipment(String companyPartyId, String pipelineId, Integer page, Integer size) {
        Context c = context(companyPartyId, pipelineId);
        int p = page(page), s = size(size);
        Instant at = Instant.now();
        List<Equipment> found = new ArrayList<>();
        for (EquipmentCandidate candidate : allEquipment(c.association.pipelineId(), at)) {
            if (!permit(c, ResourceKind.PIPELINE_SEGMENT, candidate.sectionId())
                    || !permit(c, ResourceKind.EQUIPMENT, candidate.id())
                    || candidate.facilityId() != null
                        && !permit(c, ResourceKind.FACILITY, candidate.facilityId())) continue;
            SectionRow parent = read.section(candidate.sectionId()).orElse(null);
            if (parent == null || !belongs(c, parent)) continue;
            // A linked node cannot bypass a hidden or inconsistent facility.
            if (candidate.nodeId() != null) {
                NodeCandidate node = read.nodeCandidate(candidate.nodeId()).orElse(null);
                if (node == null || !Objects.equals(node.facilityId(), candidate.facilityId())
                        || node.facilityId() != null && !permit(c, ResourceKind.FACILITY, node.facilityId())) continue;
                c.checks.add(() -> Objects.equals(node, read.nodeCandidate(candidate.nodeId()).orElse(null)));
            }
            EquipmentRow row = read.equipment(candidate.id(), at).orElse(null);
            if (row == null || !candidate.id().equals(row.id())
                    || !Objects.equals(candidate.sectionId(), row.sectionId())
                    || !Objects.equals(candidate.facilityId(), row.facilityId())
                    || !Objects.equals(candidate.nodeId(), row.nodeId())) continue;
            c.checks.add(() -> Objects.equals(row, read.equipment(candidate.id(), Instant.now()).orElse(null))
                    && Objects.equals(parent, read.section(candidate.sectionId()).orElse(null))
                    && allSections(c.association.pipelineId()).stream()
                        .anyMatch(v -> v.id().equals(parent.id()) && consistent(v, parent))
                    && allEquipment(c.association.pipelineId(), Instant.now()).stream()
                        .anyMatch(v -> candidate.equals(v)));
            found.add(new Equipment(row.id(), row.code(), row.name(), row.typeCode(),
                    row.sectionId(), row.facilityId(), row.installedAt(),
                    live(row.id(), row.updatedAt())));
        }
        found.sort(Comparator.comparing(Equipment::id));
        Page<Equipment> result = page(found, p, s);
        verify(c);
        return result;
    }

    /**
     * T07 uses an independent protected transaction executor. Do not start a
     * dossier transaction around it, and never query its private storage.
     * HISTORY is used as an authorized existence probe: a missing current
     * receipt must not be confused with a denied CURRENT read.
     */
    @Override
    public Baseline baseline(String companyPartyId, String pipelineId) {
        Context c = context(companyPartyId, pipelineId);
        require(c, ResourceKind.PIPELINE_SYSTEM, c.association.pipelineSystemId());
        try {
            var history = publications.read(new EngineeringBaselineReadUseCase.Query(
                    EngineeringBaselineReadUseCase.Action.HISTORY, c.association.companyPartyId(),
                    c.association.pipelineSystemId(), null, null, Instant.now().plusSeconds(30), 1, null));
            if (history.history().isEmpty()) {
                verify(c);
                return new Baseline(Availability.UNAVAILABLE, "PIPELINE_SYSTEM",
                        c.association.pipelineSystemId(), null, null, null, null);
            }
            var result = publications.read(new EngineeringBaselineReadUseCase.Query(
                    EngineeringBaselineReadUseCase.Action.CURRENT, c.association.companyPartyId(),
                    c.association.pipelineSystemId(), null, null, null, 0, null));
            var receipt = result.primary();
            if (receipt == null || !receipt.submission().manifest().companyPartyId().equals(c.association.companyPartyId())
                    || !receipt.submission().manifest().pipelineSystemId().equals(c.association.pipelineSystemId()))
                throw missing();
            Baseline response = new Baseline(Availability.AVAILABLE, "PIPELINE_SYSTEM",
                    c.association.pipelineSystemId(), receipt.id(), receipt.epoch(),
                    receipt.submission().sha256(), receipt.publishedAt());
            verify(c);
            return response;
        } catch (EngineeringBaselineDraftFailure failure) {
            // T07 does not distinguish a hidden source pin from a missing receipt.
            if (failure.kind() == EngineeringBaselineDraftFailure.Kind.NOT_FOUND) throw missing();
            throw failure;
        } catch (AccessDeniedException denied) {
            throw missing();
        }
    }

    private Context context(String company, String pipelineId) {
        String companyId = company(company);
        String id = selector(pipelineId);
        Association association = read.association(companyId, id)
                .orElseThrow(PipelineDossierReadApplicationService::missing);
        Context c = new Context(association);
        require(c, ResourceKind.PIPELINE, id);
        return c;
    }

    private Section section(Context c, SectionRow row) {
        Source source = live(row.id(), row.updatedAt());
        return new Section(row.id(), row.code(), row.type(), row.flowDirection(),
                quantity(row.startKilometerPoint(), "km", "START_CHAINAGE", UNAVAILABLE, source),
                quantity(row.endKilometerPoint(), "km", "END_CHAINAGE", UNAVAILABLE, source),
                quantity(row.lengthKm(), "km", "SECTION_LENGTH", UNAVAILABLE, source),
                route(c, row.fromNodeId(), row.toNodeId()), source);
    }

    private Route route(Context c, String fromId, String toId) {
        if (!eligibleNode(c, fromId) || !eligibleNode(c, toId))
            return new Route(Availability.UNAVAILABLE, null, null, null, UNAVAILABLE);
        NodeRow from = read.node(fromId).orElse(null);
        NodeRow to = read.node(toId).orElse(null);
        if (from == null || to == null || from.longitude() == null || from.latitude() == null
                || to.longitude() == null || to.latitude() == null)
            return new Route(Availability.UNAVAILABLE, null, null, null, UNAVAILABLE);
        c.checks.add(() -> Objects.equals(from, read.node(fromId).orElse(null))
                && Objects.equals(to, read.node(toId).orElse(null)));
        return new Route(Availability.AVAILABLE, point(from), point(to),
                "degree", "EXACT_ENDPOINTS_ONLY");
    }

    private boolean eligibleNode(Context c, String id) {
        if (id == null) return false;
        NodeCandidate node = read.nodeCandidate(id).orElse(null);
        if (node == null || node.facilityId() != null
                && !permit(c, ResourceKind.FACILITY, node.facilityId())) return false;
        c.checks.add(() -> Objects.equals(node, read.nodeCandidate(id).orElse(null)));
        return true;
    }

    private static Point point(NodeRow row) {
        Source source = live(row.id(), row.updatedAt());
        return new Point(row.id(), row.longitude(), row.latitude(),
                quantity(row.elevationMeters(), "m", "ENDPOINT_ELEVATION", UNAVAILABLE, source), source);
    }

    private boolean belongs(Context c, SectionRow row) {
        return allSections(c.association.pipelineId()).stream().anyMatch(candidate -> consistent(candidate, row));
    }

    private static boolean consistent(SectionCandidate c, SectionRow row) {
        return row != null && c.id() != null && Objects.equals(c.id(), row.id())
                && Objects.equals(c.fromNodeId(), row.fromNodeId())
                && Objects.equals(c.toNodeId(), row.toNodeId());
    }

    private static boolean sameEndpoints(String a, String b, String from, String to) {
        return Objects.equals(a, from) && Objects.equals(b, to)
                || Objects.equals(a, to) && Objects.equals(b, from);
    }

    private List<SectionCandidate> allSections(String pipeline) {
        return collect(after -> read.sections(pipeline, after, BATCH), SectionCandidate::id);
    }
    private List<ConnectionCandidate> allConnections(String pipeline) {
        return collect(after -> read.connections(pipeline, after, BATCH), ConnectionCandidate::id);
    }
    private List<EquipmentCandidate> allEquipment(String pipeline, Instant at) {
        return collect(after -> read.equipment(pipeline, after, BATCH, at), EquipmentCandidate::id);
    }

    private static <T> List<T> collect(java.util.function.Function<String, List<T>> fetch,
            java.util.function.Function<T, String> id) {
        List<T> found = new ArrayList<>();
        String after = "";
        while (true) {
            List<T> batch = fetch.apply(after);
            if (batch.isEmpty()) return found;
            String cursor = after;
            if (batch.size() > BATCH || batch.stream().anyMatch(row ->
                    id.apply(row) == null || id.apply(row).compareTo(cursor) <= 0))
                throw new IllegalStateException("Invalid ordered dossier candidates");
            found.addAll(batch);
            after = id.apply(batch.getLast());
            if (batch.size() < BATCH) return found;
        }
    }

    private boolean permit(Context c, ResourceKind kind, String id) {
        if (id == null) return false;
        Request req = new Request(kind, id, c.association.companyPartyId());
        boolean allowed = access.permitted(req);
        if (allowed) c.authorities.add(kind.name() + ":" + id);
        return allowed;
    }

    private void require(Context c, ResourceKind kind, String id) {
        if (!permit(c, kind, id)) throw missing();
    }

    private void verify(Context c) {
        Association refreshed = read.association(c.association.companyPartyId(), c.association.pipelineId())
                .orElseThrow(PipelineDossierReadApplicationService::missing);
        if (!refreshed.equals(c.association)) throw missing();
        for (String evidence : c.authorities) {
            int sep = evidence.indexOf(':');
            ResourceKind kind = ResourceKind.valueOf(evidence.substring(0, sep));
            String resourceId = evidence.substring(sep + 1);
            if (!access.permitted(new Request(kind, resourceId, c.association.companyPartyId())))
                throw missing();
        }
        for (BooleanSupplier check : c.checks) if (!check.getAsBoolean()) throw missing();
        if (!c.association.equals(read.association(
                c.association.companyPartyId(), c.association.pipelineId()).orElse(null)))
            throw missing();
        if (!access.permitted(new Request(ResourceKind.PIPELINE,
                c.association.pipelineId(), c.association.companyPartyId()))) throw missing();
    }

    private static Source live(String id, Instant recorded) {
        return new Source(id, LIVE, UNAVAILABLE, recorded);
    }
    private static Source associationSource(Association association) {
        return new Source(association.pipelineId(), "PIPELINE_AUTHORING_ASSOCIATION",
                Long.toString(association.revision()), association.recordedAt());
    }
    private static Quantity quantity(BigDecimal value, String unit,
            String meaning, String basis, Source source) {
        if (value == null || unit == null || unit.isBlank())
            return new Quantity(Availability.UNAVAILABLE, null, null, meaning, basis, source);
        return new Quantity(Availability.AVAILABLE, value, unit, meaning, basis, source);
    }

    private static <T> Page<T> page(List<T> all, int page, int size) {
        long total = all.size();
        long start = (long) page * size;
        List<T> items = start >= total ? List.of()
                : List.copyOf(all.subList((int) start, (int) Math.min(total, start + size)));
        int totalPages = (int) ((total + size - 1) / size);
        return new Page<>(page, size, total, totalPages, ((long) page + 1) * size < total, items);
    }
    private static int page(Integer page) {
        if (page == null) return 0;
        if (page < 0) throw new IllegalArgumentException("Invalid page");
        return page;
    }
    private static int size(Integer size) {
        if (size == null) return 50;
        if (size < 1 || size > 100) throw new IllegalArgumentException("Invalid size");
        return size;
    }
    private static String company(String value) {
        if (value == null || !value.matches("[A-Za-z0-9][A-Za-z0-9_-]{0,79}"))
            throw new IllegalArgumentException("Invalid companyPartyId");
        return value;
    }
    private static String selector(String value) {
        if (value == null || !value.matches("[A-Za-z0-9][A-Za-z0-9_-]{0,79}"))
            throw new IllegalArgumentException("Invalid selector");
        return value;
    }
    private static NoSuchElementException missing() {
        return new NoSuchElementException("Pipeline dossier unavailable");
    }
    private static final class Context {
        final Association association;
        final Set<String> authorities = new LinkedHashSet<>();
        final List<BooleanSupplier> checks = new ArrayList<>();
        Context(Association association) { this.association = association; }
    }
}
