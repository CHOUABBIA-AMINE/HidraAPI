/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineDossierReadUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Defines the six T35 read-only protected pipeline dossier queries and scalar results.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * T35 deliberately keeps owner observations separate from the T07 publication
 * receipt. A current live scalar is not a baseline-versioned hydraulic input.
 */
public interface PipelineDossierReadUseCase {
    enum Availability { AVAILABLE, UNAVAILABLE }

    record Source(String ownerEntityId, String sourceKind, String revision,
                  Instant recordedAt) { }

    record Quantity(Availability availability, BigDecimal value, String unit,
                    String meaning, String basis, Source source) { }

    record Point(String nodeId, BigDecimal longitudeDegrees,
                 BigDecimal latitudeDegrees, Quantity elevationMeters, Source source) { }

    record Route(Availability availability, Point from, Point to,
                 String coordinateUnit, String method) { }

    record Pipeline(String id, String code, String nameAr, String nameFr,
                    String nameEn, String typeCode, String status, String pipelineSystemId,
                    Quantity nominalDiameter, Quantity designPressure,
                    Quantity declaredLength, Source catalogSource, Source associationSource) { }

    record Section(String id, String code, String type, String flowDirection,
                   Quantity startKilometerPoint, Quantity endKilometerPoint,
                   Quantity length, Route route, Source source) { }

    record Connection(String id, String code, String type, String sectionId,
                      String fromNodeId, String toNodeId, Source source) { }

    record Equipment(String id, String code, String name, String typeCode,
                     String sectionId, String facilityId, Instant installedAt,
                     Source source) { }

    record Page<T>(int page, int size, long totalElements, int totalPages,
                   boolean hasNext, List<T> items) {
        public Page { items = List.copyOf(items); }
    }

    record Baseline(Availability availability, String scopeType,
                    String pipelineSystemId, UUID receiptId, Long epoch,
                    String manifestDigest, Instant publishedAt) { }

    Pipeline pipeline(String companyPartyId, String pipelineId);
    Page<Section> sections(String companyPartyId, String pipelineId, Integer page, Integer size);
    Section section(String companyPartyId, String pipelineId, String sectionId);
    Page<Connection> connections(String companyPartyId, String pipelineId, Integer page, Integer size);
    Page<Equipment> equipment(String companyPartyId, String pipelineId, Integer page, Integer size);
    Baseline baseline(String companyPartyId, String pipelineId);
}
