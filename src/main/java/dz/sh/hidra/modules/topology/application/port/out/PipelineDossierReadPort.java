/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineDossierReadPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Provides scoped scalar Topology identities and authorized-only dossier hydration.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * The first-stage candidate methods return only structural identities. Fields
 * and coordinates must never be loaded until the application has checked
 * current exact Identity authorization for the subject and its dependencies.
 */
public interface PipelineDossierReadPort {
    record Association(String companyPartyId, String pipelineId,
                       String pipelineSystemId, long revision, Instant recordedAt) { }

    record PipelineRow(String id, String code, String nameAr, String nameFr,
                       String nameEn, String typeCode, String status,
                       BigDecimal nominalDiameter, String diameterUnit,
                       BigDecimal designPressure, String pressureUnit,
                       BigDecimal declaredLengthKm, Instant updatedAt) { }

    record SectionCandidate(String id, String pipelineId,
                            String fromNodeId, String toNodeId) { }

    record SectionRow(String id, String code, String type, String flowDirection,
                      String fromNodeId, String toNodeId,
                      BigDecimal startKilometerPoint, BigDecimal endKilometerPoint,
                      BigDecimal lengthKm, Instant updatedAt) { }

    record NodeCandidate(String id, String facilityId) { }

    record NodeRow(String id, String facilityId, BigDecimal longitude,
                   BigDecimal latitude, BigDecimal elevationMeters, Instant updatedAt) { }

    record ConnectionCandidate(String id, String sectionId,
                               String fromNodeId, String toNodeId) { }

    record ConnectionRow(String id, String code, String type, String sectionId,
                         String fromNodeId, String toNodeId, Instant updatedAt) { }

    record EquipmentCandidate(String id, String sectionId,
                              String facilityId, String nodeId) { }

    record EquipmentRow(String id, String code, String name, String typeCode,
                        String sectionId, String facilityId, String nodeId,
                        Instant installedAt, Instant updatedAt) { }

    Optional<Association> association(String companyPartyId, String pipelineId);
    Optional<PipelineRow> pipeline(String pipelineId);
    List<SectionCandidate> sections(String pipelineId, String afterId, int size);
    Optional<SectionRow> section(String sectionId);
    Optional<NodeCandidate> nodeCandidate(String nodeId);
    Optional<NodeRow> node(String nodeId);
    List<ConnectionCandidate> connections(String pipelineId, String afterId, int size);
    Optional<ConnectionRow> connection(String connectionId);
    List<EquipmentCandidate> equipment(String pipelineId, String afterId, int size, Instant at);
    Optional<EquipmentRow> equipment(String equipmentId, Instant at);
}
