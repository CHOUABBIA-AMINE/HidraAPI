/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationCursorCodecTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Checks canonical context and time binding plus strict descending history boundaries.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.*;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EngineeringRevisionPublicationCursorCodecTest {
    private static final Scope SCOPE = new Scope(UUID.fromString("00000000-0000-0000-0000-000000000001"), "company", "network");
    private static final Instant TIME = Instant.parse("2026-10-20T00:00:00.123456Z");
    private final EngineeringRevisionPublicationCursorCodec codec = new EngineeringRevisionPublicationCursorCodec();

    @Test void roundTripsContextBoundHighWaterCursor() {
        for (Instant asOf : Arrays.asList(null, TIME)) {
            var original = new Cursor(SCOPE, asOf, 200, 145);
            String token = codec.encode(original);
            assertTrue(token.matches("[A-Za-z0-9_-]+"));
            assertEquals(original, codec.decode(token, SCOPE, asOf));
            assertEquals(token, codec.encode(codec.decode(token, SCOPE, asOf)));
            assertEquals(200, codec.decode(token, SCOPE, asOf).highWaterEpoch());
        }
    }

    @Test void rejectsMalformedOversizedAndNoncanonicalCursor() {
        String token = codec.encode(new Cursor(SCOPE, TIME, 200, 145));
        for (String invalid : Arrays.asList(null, "", token + "=", " ", "a".repeat(4097), "***", token.substring(1))) {
            assertThrows(IllegalArgumentException.class, () -> codec.decode(invalid, SCOPE, TIME));
        }
        byte[] bytes = Base64.getUrlDecoder().decode(token);
        for (int n = 0; n < bytes.length; n++) {
            String truncated = Base64.getUrlEncoder().withoutPadding().encodeToString(Arrays.copyOf(bytes, n));
            assertThrows(IllegalArgumentException.class, () -> codec.decode(truncated, SCOPE, TIME));
        }
        byte[] trailing = Arrays.copyOf(bytes, bytes.length + 1);
        assertThrows(IllegalArgumentException.class, () -> codec.decode(
                Base64.getUrlEncoder().withoutPadding().encodeToString(trailing), SCOPE, TIME));
        byte[] unknown = bytes.clone(); unknown[4] = 'X';
        assertThrows(IllegalArgumentException.class, () -> codec.decode(
                Base64.getUrlEncoder().withoutPadding().encodeToString(unknown), SCOPE, TIME));
        byte[] impossible = bytes.clone(); ByteBuffer.wrap(impossible).putLong(impossible.length - 8, 201);
        assertThrows(IllegalArgumentException.class, () -> codec.decode(
                Base64.getUrlEncoder().withoutPadding().encodeToString(impossible), SCOPE, TIME));
        // Change unused Base64 tail bits while retaining the decoded bytes.
        String shortToken = codec.encode(new Cursor(SCOPE, null, 1, 1));
        assertEquals(3, shortToken.length() % 4);
        {
            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
            int last = alphabet.indexOf(shortToken.charAt(shortToken.length() - 1));
            String alias = shortToken.substring(0, shortToken.length() - 1) + alphabet.charAt(last | 1);
            assertArrayEquals(Base64.getUrlDecoder().decode(shortToken), Base64.getUrlDecoder().decode(alias));
            assertThrows(IllegalArgumentException.class, () -> codec.decode(alias, SCOPE, null));
        }
    }

    @Test void rejectsCrossContextFilterAndAsOfSubstitution() {
        String token = codec.encode(new Cursor(SCOPE, TIME, 200, 145));
        for (Scope foreign : List.of(new Scope(UUID.randomUUID(), "company", "network"),
                new Scope(SCOPE.contextId(), "other", "network"), new Scope(SCOPE.contextId(), "company", "other"))) {
            assertThrows(IllegalArgumentException.class, () -> codec.decode(token, foreign, TIME));
        }
        assertThrows(IllegalArgumentException.class, () -> codec.decode(token, SCOPE, null));
        assertThrows(IllegalArgumentException.class, () -> codec.decode(token, SCOPE, TIME.plusNanos(1000)));
        String unfiltered = codec.encode(new Cursor(SCOPE, null, 200, 145));
        assertThrows(IllegalArgumentException.class, () -> codec.decode(unfiltered, SCOPE, TIME));
        assertThrows(IllegalArgumentException.class, () -> new Cursor(SCOPE, TIME.plusNanos(1), 200, 145));
    }

    @Test void preservesStrictDescendingEpochBoundary() {
        Cursor cursor = codec.decode(codec.encode(new Cursor(SCOPE, TIME, 200, 145)), SCOPE, TIME);
        assertTrue(cursor.includes(144)); assertFalse(cursor.includes(145));
        assertFalse(cursor.includes(201)); assertFalse(cursor.includes(0));
        Cursor next = cursor.advance(100);
        assertEquals(200, next.highWaterEpoch()); assertEquals(100, next.beforeEpoch());
        assertTrue(next.includes(99)); assertFalse(next.includes(100)); assertFalse(next.includes(144));
        assertThrows(IllegalArgumentException.class, () -> cursor.advance(145));
        assertThrows(IllegalArgumentException.class, () -> cursor.advance(146));
        assertThrows(IllegalArgumentException.class, () -> cursor.advance(0));
        assertThrows(IllegalArgumentException.class, () -> new Cursor(SCOPE, TIME, 99, 100));
        assertThrows(IllegalArgumentException.class, () -> new Cursor(SCOPE, TIME, 0, 0));
    }
}
