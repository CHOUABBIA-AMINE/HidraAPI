/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineManifestCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Canonical bounded draft-manifest encoding with strict corruption and format rejection.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.Kind;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.Pin;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.Objects;
import java.util.UUID;

public final class EngineeringBaselineManifestCodec {
    public static final String FORMAT = "HIDRA_ENGINEERING_BASELINE_MANIFEST_V1";
    private final int maxPins;
    private final int maxBytes;
    public EngineeringBaselineManifestCodec(int maxPins, int maxBytes) {
        if (maxPins < 1 || maxBytes < 128) throw invalid();
        this.maxPins = maxPins;
        this.maxBytes = maxBytes;
    }
    public byte[] encode(EngineeringBaselineManifest value) {
        Objects.requireNonNull(value);
        if (value.pins().size() > maxPins) throw invalid();
        try {
            var bytes = new Bounded();
            var out = new DataOutputStream(bytes);
            text(out, FORMAT);
            out.writeLong(value.manifestId().getMostSignificantBits());
            out.writeLong(value.manifestId().getLeastSignificantBits());
            text(out, value.companyPartyId()); text(out, value.pipelineSystemId());
            out.writeLong(value.version()); instant(out, value.effectiveFrom());
            out.writeBoolean(value.effectiveUntil() != null);
            if (value.effectiveUntil() != null) instant(out, value.effectiveUntil());
            out.writeInt(value.pins().size());
            for (Pin pin : value.pins()) {
                text(out, pin.kind().name()); text(out, pin.ownerId()); text(out, pin.sourceId());
                text(out, pin.revisionId()); text(out, pin.sha256());
            }
            out.flush(); return bytes.toByteArray();
        } catch (IOException impossible) { throw new IllegalStateException(impossible); }
    }
    public EngineeringBaselineManifest decode(byte[] bytes) {
        if (bytes == null || bytes.length > maxBytes) throw invalid();
        try {
            var in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (!FORMAT.equals(text(in, 80))) throw invalid();
            var id = new UUID(in.readLong(), in.readLong());
            String company = text(in, 320), pipeline = text(in, 320);
            long version = in.readLong();
            Instant from = instant(in);
            int marker = in.readUnsignedByte();
            if (marker > 1) throw invalid();
            Instant until = marker == 1 ? instant(in) : null;
            int size = in.readInt();
            if (size < 0 || size > maxPins || size > in.available() / 20) throw invalid();
            var pins = new ArrayList<Pin>(size);
            for (int n = 0; n < size; n++)
                pins.add(new Pin(Kind.valueOf(text(in, 32)), text(in, 320), text(in, 320), text(in, 320), text(in, 64)));
            if (in.available() != 0) throw invalid();
            var value = new EngineeringBaselineManifest(id, company, pipeline, version, from, until, pins);
            if (!Arrays.equals(bytes, encode(value))) throw invalid();
            return value;
        } catch (IOException | java.time.DateTimeException invalidInput) {
            throw new IllegalArgumentException("Invalid canonical draft manifest", invalidInput);
        }
    }
    public String sha256(byte[] bytes) {
        Objects.requireNonNull(bytes);
        if (bytes.length > maxBytes) throw invalid();
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes)); }
        catch (NoSuchAlgorithmException impossible) { throw new IllegalStateException(impossible); }
    }
    private static void text(DataOutputStream out, String text) throws IOException {
        byte[] encoded = text.getBytes(StandardCharsets.UTF_8);
        out.writeInt(encoded.length); out.write(encoded);
    }
    private static String text(DataInputStream in, int maximum) throws IOException {
        int length = in.readInt();
        if (length < 0 || length > maximum || length > in.available()) throw invalid();
        return StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT)
                .decode(ByteBuffer.wrap(in.readNBytes(length))).toString();
    }
    private static void instant(DataOutputStream out, Instant instant) throws IOException {
        out.writeLong(instant.getEpochSecond()); out.writeInt(instant.getNano());
    }
    private static Instant instant(DataInputStream in) throws IOException {
        long seconds = in.readLong(); int nanos = in.readInt();
        if (nanos < 0 || nanos > 999_999_999 || nanos % 1000 != 0) throw invalid();
        return Instant.ofEpochSecond(seconds, nanos);
    }
    private final class Bounded extends ByteArrayOutputStream {
        @Override public synchronized void write(int value) {
            if (count >= maxBytes) throw invalid();
            super.write(value);
        }
        @Override public synchronized void write(byte[] bytes, int offset, int length) {
            Objects.checkFromIndexSize(offset, length, bytes.length);
            if (length > maxBytes - count) throw invalid();
            super.write(bytes, offset, length);
        }
    }
    private static IllegalArgumentException invalid() { return new IllegalArgumentException("Invalid canonical draft manifest"); }
}
