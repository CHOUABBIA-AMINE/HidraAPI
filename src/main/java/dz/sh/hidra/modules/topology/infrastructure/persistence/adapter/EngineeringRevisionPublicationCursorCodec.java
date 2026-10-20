/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationCursorCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Encodes untrusted context-bound descending history windows without granting authority.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.Cursor;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.Scope;
import java.io.*;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;
import static dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringRevisionPublicationCodec.*;

public final class EngineeringRevisionPublicationCursorCodec {
    public static final String FORMAT = "HIDRA_ENGINEERING_PUBLICATION_CURSOR_V1";
    public static final int MAX_CHARACTERS = 4096;
    public String encode(Cursor cursor) {
        Objects.requireNonNull(cursor);
        try {
            var bytes = new ByteArrayOutputStream(); var out = new DataOutputStream(bytes);
            text(out, FORMAT); scope(out, cursor.scope()); out.writeBoolean(cursor.asOf() != null);
            if (cursor.asOf() != null) { out.writeLong(cursor.asOf().getEpochSecond()); out.writeInt(cursor.asOf().getNano()); }
            out.writeLong(cursor.highWaterEpoch()); out.writeLong(cursor.beforeEpoch()); out.flush();
            String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes.toByteArray());
            if (token.length() > MAX_CHARACTERS) throw invalid();
            return token;
        } catch (IOException impossible) { throw new IllegalStateException(impossible); }
    }
    /** Scope/time are supplied independently from the freshly authorized command, never trusted from the token. */
    public Cursor decode(String token, Scope expectedScope, Instant expectedAsOf) {
        Objects.requireNonNull(expectedScope);
        if (token == null || token.isEmpty() || token.length() > MAX_CHARACTERS
                || !token.matches("[A-Za-z0-9_-]+")) throw invalid();
        byte[] bytes = Base64.getUrlDecoder().decode(token);
        if (!Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).equals(token)) throw invalid();
        try {
            var in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (!FORMAT.equals(text(in, 80))) throw invalid();
            Scope scope = scope(in);
            Instant asOf = null;
            if (marker(in)) {
                long seconds = in.readLong(); int nanos = in.readInt();
                if (nanos < 0 || nanos > 999999999 || nanos % 1000 != 0) throw invalid();
                asOf = Instant.ofEpochSecond(seconds, nanos);
            }
            var cursor = new Cursor(scope, asOf, in.readLong(), in.readLong());
            if (in.available() != 0 || !scope.equals(expectedScope) || !Objects.equals(asOf, expectedAsOf)
                    || !encode(cursor).equals(token)) throw invalid();
            return cursor;
        } catch (IOException | java.time.DateTimeException e) { throw new IllegalArgumentException("Invalid publication cursor", e); }
    }
    private static IllegalArgumentException invalid() { return new IllegalArgumentException("Invalid publication cursor"); }
}
