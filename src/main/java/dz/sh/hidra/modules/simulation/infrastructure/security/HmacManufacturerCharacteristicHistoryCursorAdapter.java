package dz.sh.hidra.modules.simulation.infrastructure.security;

import dz.sh.hidra.modules.simulation.application.port.out.ManufacturerCharacteristicHistoryCursorPort;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/** Isolated, authenticated history navigation; no source, role or approval assertions. */
@Component
public final class HmacManufacturerCharacteristicHistoryCursorAdapter
        implements ManufacturerCharacteristicHistoryCursorPort {
    private static final String DOMAIN = "HIDRA_MANUFACTURER_HISTORY_CURSOR_V1";
    private static final byte[] DOMAIN_BYTES = DOMAIN.getBytes(StandardCharsets.US_ASCII);
    private static final int SIGNATURE_LENGTH = 32;
    private static final int MAX_TOKEN_LENGTH = 4_096;
    private static final long TTL_SECONDS = 900;
    private static final Base64.Encoder URL_ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder URL_DECODER = Base64.getUrlDecoder();
    private final Key current;
    private final Key previous;
    private final Clock clock;

    /**
     * No application-wide startup failure when an optional pagination credential is absent:
     * all protected paging attempts deny until its dedicated key is provisioned.
     */
    @org.springframework.beans.factory.annotation.Autowired
    public HmacManufacturerCharacteristicHistoryCursorAdapter(Environment environment) {
        this(
            environment.getProperty("hidra.simulation.manufacturer-history.cursor-current-key-id", "current"),
            environment.getProperty("hidra.simulation.manufacturer-history.cursor-current-key-base64"),
            environment.getProperty("hidra.simulation.manufacturer-history.cursor-previous-key-id"),
            environment.getProperty("hidra.simulation.manufacturer-history.cursor-previous-key-base64"),
            Clock.systemUTC());
    }
    public HmacManufacturerCharacteristicHistoryCursorAdapter(
            String currentId, String currentBase64, String previousId, String previousBase64, Clock clock) {
        this.clock = Objects.requireNonNull(clock, "Clock required");
        this.current = key(currentId, currentBase64, true);
        this.previous = key(previousId, previousBase64, false);
        if (previous != null && current != null && previous.id.equals(current.id))
            throw new IllegalArgumentException("Cursor rotation identifiers must differ");
        if (previous != null && current == null)
            throw new IllegalArgumentException("Cannot activate a previous key without a current key");
    }
    private static Key key(String id, String base64, boolean allowAbsent) {
        if (base64 == null || base64.isBlank()) {
            if (!allowAbsent && id != null && !id.isBlank())
                throw new IllegalArgumentException("Previous cursor key missing");
            return null;
        }
        if (id == null || !id.matches("[A-Za-z0-9_-]{1,32}")
                || !base64.matches("[A-Za-z0-9_-]{43,180}"))
            throw new IllegalArgumentException("Invalid cursor key configuration");
        try {
            byte[] bytes = URL_DECODER.decode(base64);
            if (bytes.length < 32 || bytes.length > 128
                    || !URL_ENCODER.encodeToString(bytes).equals(base64))
                throw new IllegalArgumentException("Invalid cursor key length/encoding");
            int nonzero = 0;
            for (byte b : bytes) nonzero |= b;
            if (nonzero == 0) throw new IllegalArgumentException("Empty cursor key material");
            Key value = new Key(id, new SecretKeySpec(bytes, "HmacSHA256"));
            Arrays.fill(bytes, (byte) 0);
            return value;
        } catch (IllegalArgumentException invalid) {
            throw new IllegalArgumentException("Invalid dedicated cursor secret", invalid);
        }
    }
    @Override public boolean available() { return current != null; }
    @Override public String encode(String company, String source, int size, Position position) {
        if (current == null) throw denied();
        scope(company, source, size);
        Objects.requireNonNull(position);
        try {
            Instant issued = clock.instant();
            var bytes = new ByteArrayOutputStream();
            var out = new DataOutputStream(bytes);
            out.writeUTF(DOMAIN);
            out.writeUTF(current.id);
            out.writeUTF(company);
            out.writeUTF(source);
            out.writeInt(size);
            out.writeLong(position.recordedAt().getEpochSecond());
            out.writeInt(position.recordedAt().getNano() / 1_000);
            out.writeUTF(position.revisionId());
            out.writeLong(issued.getEpochSecond());
            out.writeLong(Math.addExact(issued.getEpochSecond(), TTL_SECONDS));
            out.flush();
            byte[] frame = bytes.toByteArray(), signature = sign(current, frame);
            var wire = new byte[frame.length + signature.length];
            System.arraycopy(frame, 0, wire, 0, frame.length);
            System.arraycopy(signature, 0, wire, frame.length, signature.length);
            String token = URL_ENCODER.encodeToString(wire);
            if (token.length() > MAX_TOKEN_LENGTH) throw denied();
            return token;
        } catch (IOException | ArithmeticException invalid) { throw denied(); }
    }
    @Override public Position decode(String token, String company, String source, int size) {
        if (current == null) throw denied();
        scope(company, source, size);
        if (token == null || token.isBlank() || token.length() > MAX_TOKEN_LENGTH
                || !token.matches("[A-Za-z0-9_-]+")) throw denied();
        try {
            byte[] wire = URL_DECODER.decode(token);
            if (!URL_ENCODER.encodeToString(wire).equals(token)
                    || wire.length < SIGNATURE_LENGTH + 36) throw denied();
            byte[] frame = Arrays.copyOf(wire, wire.length - SIGNATURE_LENGTH);
            byte[] signature = Arrays.copyOfRange(wire, frame.length, wire.length);
            var check = new DataInputStream(new ByteArrayInputStream(frame));
            if (!DOMAIN.equals(check.readUTF())) throw denied();
            String id = check.readUTF();
            Key key = current.id.equals(id) ? current
                    : previous != null && previous.id.equals(id) ? previous : null;
            if (key == null || !MessageDigest.isEqual(signature, sign(key, frame))) throw denied();
            String actualCompany = check.readUTF(), actualSource = check.readUTF();
            int actualSize = check.readInt();
            Instant timestamp = Instant.ofEpochSecond(check.readLong(), Math.multiplyExact(check.readInt(), 1_000));
            Position position = new Position(timestamp, check.readUTF());
            long issued = check.readLong(), expires = check.readLong(), now = clock.instant().getEpochSecond();
            if (check.available() != 0 || !company.equals(actualCompany) || !source.equals(actualSource)
                    || size != actualSize || issued > now || now >= expires
                    || Math.subtractExact(expires, issued) != TTL_SECONDS
                    || !encodeFrameCanonical(frame, id, actualCompany, actualSource, actualSize,
                            position, issued, expires)) throw denied();
            return position;
        } catch (IOException | ArithmeticException | java.time.DateTimeException invalid) {
            throw denied();
        }
    }
    private static boolean encodeFrameCanonical(byte[] frame, String id, String company,
            String source, int size, Position position, long issued, long expires) throws IOException {
        var bytes = new ByteArrayOutputStream();
        var out = new DataOutputStream(bytes);
        out.writeUTF(DOMAIN); out.writeUTF(id); out.writeUTF(company); out.writeUTF(source);
        out.writeInt(size);
        out.writeLong(position.recordedAt().getEpochSecond());
        out.writeInt(position.recordedAt().getNano() / 1_000);
        out.writeUTF(position.revisionId());
        out.writeLong(issued); out.writeLong(expires); out.flush();
        return Arrays.equals(frame, bytes.toByteArray());
    }
    private static byte[] sign(Key key, byte[] frame) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key.secret);
            mac.update(DOMAIN_BYTES);
            mac.update((byte) 0);
            return mac.doFinal(frame);
        } catch (GeneralSecurityException unavailable) {
            throw new IllegalStateException("HMAC-SHA256 unavailable", unavailable);
        }
    }
    private static void scope(String company, String source, int size) {
        if (!exact(company) || !exact(source) || size < 1 || size > 10_000) throw denied();
    }
    private static boolean exact(String value) {
        return value != null && !value.isBlank() && value.equals(value.strip())
                && value.codePointCount(0, value.length()) <= 80
                && value.codePoints().noneMatch(Character::isISOControl);
    }
    private static IllegalArgumentException denied() {
        return new IllegalArgumentException("Invalid manufacturer history continuation");
    }
    private record Key(String id, SecretKeySpec secret) {}
}
