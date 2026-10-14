/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationTemporalBoundaryPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Proves real PostgreSQL timestamp parity for supplied boundary candidates only.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static dz.sh.hidra.modules.identity.domain.service.AuthorizationTemporalBoundaryCompiler.*;
import static org.assertj.core.api.Assertions.*;

import dz.sh.hidra.modules.identity.domain.service.AuthorizationTemporalBoundaryCompiler;
import java.sql.*;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
class AuthorizationTemporalBoundaryPostgresIntegrationTest {
  @Container
  static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16");

  private final AuthorizationTemporalBoundaryCompiler compiler =
      new AuthorizationTemporalBoundaryCompiler();
  private final Instant s = Instant.parse("2026-10-14T12:00:00.123456Z");

  private Connection connection() throws SQLException {
    return DriverManager.getConnection(
        POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
  }

  private Instant roundTrip(Connection c, Instant value) throws SQLException {
    try (var p = c.prepareStatement("SELECT ?::timestamptz")) {
      p.setTimestamp(1, Timestamp.from(value));
      try (var r = p.executeQuery()) {
        assertThat(r.next()).isTrue();
        return r.getTimestamp(1).toInstant();
      }
    }
  }

  private boolean inInterval(Connection c, Instant t, Instant sample, Instant upper)
      throws SQLException {
    try (var p =
        c.prepareStatement(
            "SELECT ?::timestamptz >= ?::timestamptz AND ?::timestamptz < ?::timestamptz")) {
      p.setTimestamp(1, Timestamp.from(t));
      p.setTimestamp(2, Timestamp.from(sample));
      p.setTimestamp(3, Timestamp.from(t));
      p.setTimestamp(4, Timestamp.from(upper));
      try (var r = p.executeQuery()) {
        assertThat(r.next()).isTrue();
        return r.getBoolean(1);
      }
    }
  }

  @Test
  void postgresHalfOpenEqualityMatchesCompiler() throws Exception {
    var r =
        compiler.compile(
            s,
            List.of(
                new BoundaryInput(
                    "future-deny", Kind.DIRECT_GRANT, s.plusNanos(2000), s.plusSeconds(3)),
                new BoundaryInput("source", Kind.B1_EXPIRY, null, s.plusSeconds(5))));
    Instant u = r.earliestFuture().orElseThrow();
    try (var c = connection()) {
      assertThat(inInterval(c, s, s, u)).isTrue();
      assertThat(inInterval(c, u.minusNanos(1000), s, u)).isTrue();
      assertThat(inInterval(c, u, s, u)).isFalse();
      assertThat(inInterval(c, u.plusNanos(1000), s, u)).isFalse();
      assertThat(inInterval(c, s.minusNanos(1000), s, u)).isFalse();
    }
    assertThat(
            compiler
                .compile(
                    u,
                    List.of(new BoundaryInput("starting", Kind.DIRECT_GRANT, u, u.plusSeconds(3))))
                .earliestFuture())
        .contains(u.plusSeconds(3));
  }

  @Test
  void postgresMicrosecondRoundTripsPreserveCandidate() throws Exception {
    try (var c = connection();
        var independent = connection()) {
      int first, second;
      try (var p = c.createStatement();
          var r = p.executeQuery("SELECT pg_backend_pid()")) {
        r.next();
        first = r.getInt(1);
      }
      try (var p = independent.createStatement();
          var r = p.executeQuery("SELECT pg_backend_pid()")) {
        r.next();
        second = r.getInt(1);
      }
      assertThat(first).isNotEqualTo(second);
      Instant u = s.plusNanos(1000);
      assertThat(roundTrip(c, s)).isEqualTo(s);
      assertThat(roundTrip(independent, u)).isEqualTo(u);
      assertThat(
              compiler
                  .compile(
                      roundTrip(c, s),
                      List.of(
                          new BoundaryInput(
                              "micro", Kind.B1_EXPIRY, null, roundTrip(independent, u))))
                  .earliestFuture())
          .contains(u);
    }
  }

  @Test
  void postgresSessionTimeZoneCannotChangeInstantBoundary() throws Exception {
    try (var a = connection();
        var b = connection()) {
      try (var p = a.createStatement()) {
        p.execute("SET TIME ZONE 'Africa/Algiers'");
      }
      try (var p = b.createStatement()) {
        p.execute("SET TIME ZONE 'America/New_York'");
      }
      assertThat(roundTrip(a, s)).isEqualTo(roundTrip(b, s)).isEqualTo(s);
      try (var p = b.prepareStatement("SELECT ?::timestamptz = ?::timestamptz")) {
        p.setString(1, "2026-10-14T13:00:00.123456+01:00");
        p.setString(2, "2026-10-14T12:00:00.123456Z");
        try (var r = p.executeQuery()) {
          r.next();
          assertThat(r.getBoolean(1)).isTrue();
        }
      }
      var input = List.of(new BoundaryInput("expiry", Kind.B1_EXPIRY, null, s.plusNanos(1000)));
      assertThat(compiler.compile(roundTrip(a, s), input))
          .isEqualTo(compiler.compile(roundTrip(b, s), input));
    }
  }

  @Test
  void postgresRejectsNonfiniteOwnerBoundariesAtCompilerBoundary() throws Exception {
    try (var c = connection()) {
      for (String value : List.of("infinity", "-infinity")) {
        try (var p = c.prepareStatement("SELECT isfinite(?::timestamptz), ?::timestamptz")) {
          p.setString(1, value);
          p.setString(2, value);
          try (var r = p.executeQuery()) {
            r.next();
            assertThat(r.getBoolean(1)).isFalse();
            var result =
                compiler.compile(
                    s,
                    List.of(
                        new BoundaryInput(
                            "nonfinite", Kind.ATTRIBUTE, null, r.getTimestamp(2).toInstant())));
            assertThat(result.disposition()).isEqualTo(Disposition.UNSUPPORTED);
            assertThat(result.reason()).isEqualTo(Reason.UNREPRESENTABLE_TIME);
            assertThat(result.earliestFuture()).isEmpty();
          }
        }
      }
      assertThat(
              compiler
                  .compile(
                      s,
                      List.of(
                          new BoundaryInput(
                              "finite", Kind.B1_EXPIRY, null, roundTrip(c, s.plusNanos(1000)))))
                  .earliestFuture())
          .contains(s.plusNanos(1000));
    }
  }
}
