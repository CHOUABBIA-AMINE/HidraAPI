package dz.sh.hidra.modules.simulation.api.rest.mapper;

import dz.sh.hidra.modules.simulation.api.rest.request.ManufacturerCharacteristicRevisionRequests;
import dz.sh.hidra.modules.simulation.api.rest.request.ManufacturerCharacteristicRevisionRequests.*;
import dz.sh.hidra.modules.simulation.api.rest.response.ManufacturerCharacteristicRevisionResponses;
import dz.sh.hidra.modules.simulation.application.port.in.ManufacturerCharacteristicRevisionUseCase;
import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision;
import java.util.List;
import java.util.Objects;

/** Explicit domain/transport mapping; no persistence model or inferred engineering qualification. */
public final class ManufacturerCharacteristicRevisionRestMapper {
    private ManufacturerCharacteristicRevisionRestMapper() {}

    public static ManufacturerCharacteristicRevision toDomain(Revision revision) {
        Objects.requireNonNull(revision, "revision");
        Objects.requireNonNull(revision.referenceConditions(), "reference conditions");
        Objects.requireNonNull(revision.family(), "family");
        var reference = new ManufacturerCharacteristicRevision.ReferenceConditions(
                revision.referenceConditions().pressurePascalsAbsolute(),
                revision.referenceConditions().temperatureKelvin(),
                revision.referenceConditions().methodReference());
        var certificate = revision.certificate()==null ? null : new ManufacturerCharacteristicRevision.CertificateEvidence(
                revision.certificate().issuerReference(), revision.certificate().certificateReference(),
                revision.certificate().certificateSha256(), revision.certificate().sourceId(),
                revision.certificate().revisionId(), revision.certificate().sourceDocumentSha256(),
                revision.certificate().manufacturerPartyId(), revision.certificate().manufacturerModelReference(),
                ManufacturerCharacteristicRevision.Kind.valueOf(revision.certificate().characteristicKind().name()),
                revision.certificate().validFrom(),
                revision.certificate().validUntil());
        ManufacturerCharacteristicRevision.Family family = switch(revision.family()) {
            case CompressorMap c -> new ManufacturerCharacteristicRevision.CompressorMap(
                require(c.speedLines()).stream().map(line->new ManufacturerCharacteristicRevision.SpeedLine(
                    line.speedRevolutionsPerMinute(), require(line.points()).stream().map(p->
                    new ManufacturerCharacteristicRevision.CompressorPoint(p.massFlowKilogramsPerSecond(),
                        p.specificHeadJoulesPerKilogram(),p.efficiencyFraction())).toList())).toList());
            case ValveMap v -> new ManufacturerCharacteristicRevision.ValveMap(
                require(v.openingLines()).stream().map(line->new ManufacturerCharacteristicRevision.OpeningLine(
                    line.openingFraction(),require(line.points()).stream().map(p->
                    new ManufacturerCharacteristicRevision.ValvePoint(p.differentialPressurePascals(),
                        p.massFlowKilogramsPerSecond())).toList())).toList());
            case ParameterTable t -> new ManufacturerCharacteristicRevision.ParameterTable(
                require(t.axes()).stream().map(ManufacturerCharacteristicRevisionRestMapper::axis).toList(),
                require(t.outputs()).stream().map(ManufacturerCharacteristicRevisionRestMapper::axis).toList(),
                require(t.rows()).stream().map(row->new ManufacturerCharacteristicRevision.TableRow(
                    row.coordinates(),row.values())).toList());
            case OperatingLimits l -> new ManufacturerCharacteristicRevision.OperatingLimits(
                require(l.limits()).stream().map(v->new ManufacturerCharacteristicRevision.Limit(
                    axis(v.axis()),v.minimumInclusive(),v.maximumInclusive(),v.evidenceReference())).toList());
        };
        return new ManufacturerCharacteristicRevision(revision.sourceId(),revision.revisionId(),
            revision.recordedAt(),revision.effectiveFrom(),revision.effectiveUntil(),
            ManufacturerCharacteristicRevision.Origin.valueOf(revision.origin().name()),
            revision.manufacturerPartyId(),revision.manufacturerModelReference(),revision.documentReference(),
            revision.documentSha256(),reference,certificate,family);
    }

    public static Revision toDto(ManufacturerCharacteristicRevision source) {
        Objects.requireNonNull(source, "source");
        var r = source.referenceConditions();
        var reference = new ReferenceConditions(r.pressurePascalsAbsolute(),r.temperatureKelvin(),
                r.methodReference());
        var c=source.certificate();
        var certificate=c==null?null:new Certificate(c.issuerReference(),c.certificateReference(),
                c.certificateSha256(),c.sourceId(),c.revisionId(),c.sourceDocumentSha256(),
                c.manufacturerPartyId(),c.manufacturerModelReference(),Kind.valueOf(c.characteristicKind().name()),
                c.validFrom(),c.validUntil());
        Family family = switch(source.family()) {
            case ManufacturerCharacteristicRevision.CompressorMap x -> new CompressorMap(
                x.speedLines().stream().map(line->new SpeedLine(line.speedRevolutionsPerMinute(),
                    line.points().stream().map(p->new CompressorPoint(p.massFlowKilogramsPerSecond(),
                        p.specificHeadJoulesPerKilogram(),p.efficiencyFraction())).toList())).toList());
            case ManufacturerCharacteristicRevision.ValveMap x -> new ValveMap(
                x.openingLines().stream().map(line->new OpeningLine(line.openingFraction(),
                    line.points().stream().map(p->new ValvePoint(p.differentialPressurePascals(),
                        p.massFlowKilogramsPerSecond())).toList())).toList());
            case ManufacturerCharacteristicRevision.ParameterTable x -> new ParameterTable(
                x.axes().stream().map(ManufacturerCharacteristicRevisionRestMapper::axisDto).toList(),
                x.outputs().stream().map(ManufacturerCharacteristicRevisionRestMapper::axisDto).toList(),
                x.rows().stream().map(row->new TableRow(row.coordinates(),row.values())).toList());
            case ManufacturerCharacteristicRevision.OperatingLimits x -> new OperatingLimits(
                x.limits().stream().map(v->new Limit(axisDto(v.axis()),
                    v.minimumInclusive(),v.maximumInclusive(),v.evidenceReference())).toList());
        };
        return new Revision(source.sourceId(),source.revisionId(),source.recordedAt(),
            source.effectiveFrom(),source.effectiveUntil(),Origin.valueOf(source.origin().name()),
            source.manufacturerPartyId(),
            source.manufacturerModelReference(),source.documentReference(),source.documentSha256(),
            reference,certificate,family);
    }

    private static ManufacturerCharacteristicRevision.Axis axis(Axis source) {
        Objects.requireNonNull(source,"axis");
        return new ManufacturerCharacteristicRevision.Axis(
                ManufacturerCharacteristicRevision.Quantity.valueOf(source.quantity().name()),
                ManufacturerCharacteristicRevision.Unit.valueOf(source.unit().name()));
    }
    private static Axis axisDto(ManufacturerCharacteristicRevision.Axis a) {
        return new Axis(Quantity.valueOf(a.quantity().name()), Unit.valueOf(a.unit().name()));
    }
    private static <T> List<T> require(List<T> items) {
        return Objects.requireNonNull(items,"missing bounded collection");
    }
    public static ManufacturerCharacteristicRevisionResponses.Receipt receipt(
            ManufacturerCharacteristicRevisionUseCase.Result r) {
        return new ManufacturerCharacteristicRevisionResponses.Receipt(r.sourceId(),r.revisionId(),
            r.payloadSha256(),r.completedAt(),r.workflowQualified());
    }
    public static ManufacturerCharacteristicRevisionResponses.Revision revision(
            ManufacturerCharacteristicRevisionUseCase.Result r) {
        return new ManufacturerCharacteristicRevisionResponses.Revision(r.sourceId(),r.revisionId(),
            r.payloadSha256(),toDto(Objects.requireNonNull(r.revision())),r.completedAt(),
            r.workflowQualified());
    }
    public static ManufacturerCharacteristicRevisionResponses.Certificate certificate(
            ManufacturerCharacteristicRevisionUseCase.Result r) {
        var revision=Objects.requireNonNull(r.revision());
        var dto=toDto(revision);
        return new ManufacturerCharacteristicRevisionResponses.Certificate(r.sourceId(),r.revisionId(),
            r.payloadSha256(),dto.certificate(),r.completedAt(),r.workflowQualified());
    }
    public static ManufacturerCharacteristicRevisionResponses.HistoryPage history(
            ManufacturerCharacteristicRevisionUseCase.HistoryPageResult r) {
        return new ManufacturerCharacteristicRevisionResponses.HistoryPage(
            r.history().stream().map(v->new ManufacturerCharacteristicRevisionResponses.Summary(
                v.sourceId(),v.revisionId(),v.payloadSha256(),v.recordedAt(),v.effectiveFrom(),
                v.effectiveUntil(),v.workflowQualified())).toList(),r.nextCursor(),r.completedAt());
    }
}
