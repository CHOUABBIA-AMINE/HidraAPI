/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Encodes bounded canonical publication envelopes while validating original source formats.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.*;
import java.util.*;

public final class EngineeringRevisionPublicationCodec {
    public static final String FORMAT = "HIDRA_ENGINEERING_PUBLICATION_V1";
    public static final int MAX_BYTES = EngineeringRevisionPublication.MAX_BYTES;

    private static final class Bounded extends ByteArrayOutputStream {
        @Override public synchronized void write(int value) {
            if (count >= MAX_BYTES) throw invalid();
            super.write(value);
        }
        @Override public synchronized void write(byte[] value, int offset, int length) {
            if (length > MAX_BYTES - count) throw invalid();
            super.write(value, offset, length);
        }
    }

    public byte[] encode(Snapshot snapshot) {
        validateSources(snapshot);
        try {
            var bytes = new Bounded();
            var out = new DataOutputStream(bytes);
            text(out, FORMAT); scope(out, snapshot.scope()); uuid(out, snapshot.draftId());
            out.writeLong(snapshot.version());
            component(out, snapshot.metadata());
            out.writeBoolean(snapshot.graph() != null);
            if (snapshot.graph() != null) component(out, snapshot.graph());
            out.writeInt(snapshot.surveys().size());
            for (Survey survey : snapshot.surveys()) {
                text(out, survey.branchId()); out.writeBoolean(survey.source() != null);
                if (survey.source() != null) {
                    component(out, survey.source()); out.writeLong(survey.graphVersion()); text(out, survey.graphSha256());
                }
                findings(out, survey.diagnostics());
            }
            findings(out, snapshot.diagnostics());
            out.writeBoolean(false);
            out.flush();
            return bytes.toByteArray();
        } catch (IOException impossible) { throw new IllegalStateException(impossible); }
    }

    public Snapshot decode(byte[] bytes, Scope expectedScope) {
        Objects.requireNonNull(expectedScope);
        if (bytes == null || bytes.length > MAX_BYTES) throw invalid();
        try {
            var in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (!FORMAT.equals(text(in, 80))) throw invalid();
            Scope scope = scope(in);
            if (!scope.equals(expectedScope)) throw invalid();
            UUID draftId = uuid(in); long version = in.readLong();
            Component metadata = component(in, 32768);
            Component graph = marker(in) ? component(in, EngineeringGraphDraftCodec.MAX_BYTES) : null;
            int size = count(in, EngineeringGraphDraft.MAX_BRANCHES, 9);
            var surveys = new ArrayList<Survey>(size);
            for (int n = 0; n < size; n++) {
                String branch = text(in, 80);
                Component source = null; Long graphVersion = null; String graphDigest = null;
                if (marker(in)) {
                    source = component(in, SurveyRouteProfileCodec.MAX_BYTES);
                    graphVersion = in.readLong(); graphDigest = text(in, 64);
                }
                surveys.add(new Survey(branch, source, graphVersion, graphDigest, findings(in)));
            }
            var diagnostics = findings(in);
            if (marker(in) || in.available() != 0) throw invalid();
            var result = new Snapshot(scope, draftId, version, metadata, graph, surveys, diagnostics);
            if (!Arrays.equals(bytes, encode(result))) throw invalid();
            return result;
        } catch (IOException e) { throw new IllegalArgumentException("Invalid publication encoding", e); }
    }

    private static void validateSources(Snapshot snapshot) {
        Objects.requireNonNull(snapshot);
        Component metadata = snapshot.metadata();
        if (!EngineeringNetworkDraftCodec.FORMAT.equals(metadata.format())) throw invalid();
        var values = new EngineeringNetworkDraftCodec().decode(payload(metadata));
        if (values.title() == null && !snapshot.diagnostics().contains("TITLE_NOT_SUPPLIED")
                || values.evidenceReference() == null && !snapshot.diagnostics().contains("EVIDENCE_NOT_SUPPLIED"))
            throw invalid();
        if (snapshot.graph() == null) return;
        if (!EngineeringGraphDraftCodec.FORMAT.equals(snapshot.graph().format())) throw invalid();
        var graph = new EngineeringGraphDraftCodec().decode(payload(snapshot.graph()));
        var pipes = graph.branches().stream().filter(b -> b.kind() == EngineeringGraphDraft.BranchKind.PIPE)
                .map(EngineeringGraphDraft.Branch::id).toList();
        if (!pipes.equals(snapshot.surveys().stream().map(Survey::branchId).toList())) throw invalid();
        if (!snapshot.diagnostics().containsAll(graph.diagnostics())) throw invalid();
        for (Survey survey : snapshot.surveys()) {
            if (survey.source() == null) continue;
            if (!SurveyRouteProfileCodec.FORMAT.equals(survey.source().format())) throw invalid();
            var profile = new SurveyRouteProfileCodec().decode(payload(survey.source()));
            if (!survey.branchId().equals(profile.branchId())) throw invalid();
        }
    }

    private static byte[] payload(Component component) { return Base64.getDecoder().decode(component.base64()); }
    private static void component(DataOutputStream out, Component value) throws IOException {
        out.writeLong(value.version()); text(out, value.format()); text(out, value.sha256());
        byte[] bytes = payload(value); out.writeInt(bytes.length); out.write(bytes);
    }
    private static Component component(DataInputStream in, int maximum) throws IOException {
        long version = in.readLong(); String format = text(in, 80), digest = text(in, 64);
        int size = count(in, maximum, 1);
        return new Component(version, format, digest, Base64.getEncoder().encodeToString(in.readNBytes(size)));
    }
    private static void findings(DataOutputStream out, List<String> values) throws IOException {
        out.writeInt(values.size()); for (String value : values) text(out, value);
    }
    private static List<String> findings(DataInputStream in) throws IOException {
        int size = count(in, 64, 5); var values = new ArrayList<String>(size);
        for (int n = 0; n < size; n++) values.add(text(in, 80));
        return List.copyOf(values);
    }
    static void scope(DataOutputStream out, Scope value) throws IOException {
        uuid(out, value.contextId()); text(out, value.companyPartyId()); text(out, value.pipelineSystemId());
    }
    static Scope scope(DataInputStream in) throws IOException { return new Scope(uuid(in), text(in, 320), text(in, 320)); }
    static void uuid(DataOutputStream out, UUID value) throws IOException {
        out.writeLong(value.getMostSignificantBits()); out.writeLong(value.getLeastSignificantBits());
    }
    static UUID uuid(DataInputStream in) throws IOException { return new UUID(in.readLong(), in.readLong()); }
    static void text(DataOutputStream out, String value) throws IOException {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8); out.writeInt(bytes.length); out.write(bytes);
    }
    static String text(DataInputStream in, int maximum) throws IOException {
        int size = count(in, maximum, 1);
        return StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(in.readNBytes(size))).toString();
    }
    static boolean marker(DataInputStream in) throws IOException {
        int value = in.readUnsignedByte(); if (value > 1) throw invalid(); return value == 1;
    }
    static int count(DataInputStream in, int maximum, int minimumBytes) throws IOException {
        int value = in.readInt();
        if (value < 0 || value > maximum || value > in.available() / minimumBytes) throw invalid();
        return value;
    }
    private static IllegalArgumentException invalid() { return new IllegalArgumentException("Invalid publication encoding"); }
}
