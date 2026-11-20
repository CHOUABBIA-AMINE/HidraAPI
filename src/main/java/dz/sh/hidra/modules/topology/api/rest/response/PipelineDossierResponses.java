/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineDossierResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Defines allowlisted T35 browser DTOs without leaking owner grants or source pins.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import dz.sh.hidra.modules.topology.application.port.in.PipelineDossierReadUseCase;
import dz.sh.hidra.modules.topology.application.port.in.PipelineDossierReadUseCase.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class PipelineDossierResponses {
    private PipelineDossierResponses() {}

    @Schema(description="Owner provenance; live catalog revision is explicitly UNAVAILABLE")
    public record SourceView(String ownerEntityId, String sourceKind, String revision, Instant recordedAt) {}
    public record QuantityView(String availability, BigDecimal value, String unit,
                               String meaning, String basis, SourceView source) {}
    public record PointView(String nodeId, BigDecimal longitudeDegrees,
                            BigDecimal latitudeDegrees, QuantityView elevationMeters, SourceView source) {}
    public record RouteView(String availability, PointView from, PointView to,
                            String coordinateUnit, String method) {}
    public record PipelineView(String id, String code, String nameAr, String nameFr,
                               String nameEn, String typeCode, String status, String pipelineSystemId,
                               QuantityView nominalDiameter, QuantityView designPressure,
                               QuantityView declaredLength, SourceView catalogSource,
                               SourceView associationSource) {}
    public record SectionView(String id, String code, String type, String flowDirection,
                              QuantityView startKilometerPoint, QuantityView endKilometerPoint,
                              QuantityView length, RouteView route, SourceView source) {}
    public record ConnectionView(String id, String code, String type, String sectionId,
                                 String fromNodeId, String toNodeId, SourceView source) {}
    public record EquipmentView(String id, String code, String name, String typeCode,
                                String sectionId, String facilityId, Instant installedAt,
                                SourceView source) {}
    public record SectionsPage(int page, int size, long totalElements, int totalPages,
                               boolean hasNext, List<SectionView> items) {}
    public record ConnectionsPage(int page, int size, long totalElements, int totalPages,
                                  boolean hasNext, List<ConnectionView> items) {}
    public record EquipmentPage(int page, int size, long totalElements, int totalPages,
                                boolean hasNext, List<EquipmentView> items) {}
    public record BaselineView(String availability, String scopeType, String pipelineSystemId,
                               UUID receiptId, Long epoch, String manifestDigest, Instant publishedAt) {}

    public static PipelineView pipeline(PipelineDossierReadUseCase.Pipeline p) {
        return new PipelineView(p.id(),p.code(),p.nameAr(),p.nameFr(),p.nameEn(),p.typeCode(),
                p.status(),p.pipelineSystemId(),quantity(p.nominalDiameter()),
                quantity(p.designPressure()),quantity(p.declaredLength()),
                source(p.catalogSource()),source(p.associationSource()));
    }
    public static SectionView section(Section p) {
        return new SectionView(p.id(),p.code(),p.type(),p.flowDirection(),
                quantity(p.startKilometerPoint()),quantity(p.endKilometerPoint()),
                quantity(p.length()),route(p.route()),source(p.source()));
    }
    public static SectionsPage sections(Page<Section> p) {
        return new SectionsPage(p.page(),p.size(),p.totalElements(),p.totalPages(),
                p.hasNext(),p.items().stream().map(PipelineDossierResponses::section).toList());
    }
    public static ConnectionsPage connections(Page<Connection> p) {
        return new ConnectionsPage(p.page(),p.size(),p.totalElements(),p.totalPages(),
                p.hasNext(),p.items().stream().map(v -> new ConnectionView(v.id(),v.code(),
                        v.type(),v.sectionId(),v.fromNodeId(),v.toNodeId(),source(v.source()))).toList());
    }
    public static EquipmentPage equipment(Page<Equipment> p) {
        return new EquipmentPage(p.page(),p.size(),p.totalElements(),p.totalPages(),p.hasNext(),
                p.items().stream().map(v -> new EquipmentView(v.id(),v.code(),v.name(),
                        v.typeCode(),v.sectionId(),v.facilityId(),v.installedAt(),
                        source(v.source()))).toList());
    }
    public static BaselineView baseline(Baseline p) {
        return new BaselineView(p.availability().name(),p.scopeType(),p.pipelineSystemId(),
                p.receiptId(),p.epoch(),p.manifestDigest(),p.publishedAt());
    }
    private static SourceView source(Source p) {
        return p==null ? null : new SourceView(p.ownerEntityId(),p.sourceKind(),p.revision(),p.recordedAt());
    }
    private static QuantityView quantity(Quantity p) {
        return p==null ? null : new QuantityView(p.availability().name(),p.value(),p.unit(),
                p.meaning(),p.basis(),source(p.source()));
    }
    private static PointView point(Point p) {
        return p==null ? null : new PointView(p.nodeId(),p.longitudeDegrees(),
                p.latitudeDegrees(),quantity(p.elevationMeters()),source(p.source()));
    }
    private static RouteView route(Route p) {
        return p==null ? null : new RouteView(p.availability().name(),
                point(p.from()),point(p.to()),p.coordinateUnit(),p.method());
    }
}
