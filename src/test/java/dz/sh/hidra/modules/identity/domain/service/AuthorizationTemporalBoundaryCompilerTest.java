/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationTemporalBoundaryCompilerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Verifies bounded temporal reduction and explicit unsupported outcomes.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import static dz.sh.hidra.modules.identity.domain.service.AuthorizationTemporalBoundaryCompiler.*;
import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;

class AuthorizationTemporalBoundaryCompilerTest {
  private final AuthorizationTemporalBoundaryCompiler compiler =
      new AuthorizationTemporalBoundaryCompiler();
  private final Instant s = Instant.parse("2026-10-14T12:00:00Z");

  private BoundaryInput window(String id, Kind kind, long from, long to) {
    return new BoundaryInput(id, kind, s.plusSeconds(from), s.plusSeconds(to));
  }

  private Compilation compile(BoundaryInput... inputs) {
    return compiler.compile(s, Arrays.asList(inputs));
  }

  private void rejected(Compilation r, Reason reason) {
    assertThat(r.disposition()).isEqualTo(Disposition.UNSUPPORTED);
    assertThat(r.reason()).isEqualTo(reason);
    assertThat(r.earliestFuture()).isEmpty();
    assertThat(r.contributors()).isEmpty();
  }

  @Test
  void earliestFutureIncludesInactiveWindowStarts() {
    var r =
        compile(
            window("future-deny", Kind.DIRECT_GRANT, 2, 5),
            window("current-permit", Kind.DIRECT_GRANT, -10, 20));
    assertThat(r.earliestFuture()).contains(s.plusSeconds(2));
    assertThat(r.contributors())
        .containsExactly(new Contributor("future-deny", Kind.DIRECT_GRANT, Endpoint.FROM));
  }

  @Test
  void startAndEndEqualityUseHalfOpenSemantics() {
    assertThat(compile(window("starting", Kind.DIRECT_GRANT, 0, 2)).earliestFuture())
        .contains(s.plusSeconds(2));
    assertThat(compile(window("ending", Kind.DIRECT_GRANT, -2, 0)).disposition())
        .isEqualTo(Disposition.NO_FUTURE_BOUNDARY);
  }

  @Test
  void pastWindowsDoNotInventFutureHorizon() {
    var r = compile(window("past", Kind.POLICY_VERSION, -5, -1));
    assertThat(r.disposition()).isEqualTo(Disposition.NO_FUTURE_BOUNDARY);
    assertThat(r.earliestFuture()).isEmpty();
  }

  @Test
  void nullableAttributeAndLockBoundariesAreExplicit() {
    var r =
        compile(
            new BoundaryInput("attr", Kind.ATTRIBUTE, null, s.plusSeconds(3)),
            new BoundaryInput("unlock", Kind.LOCKED_UNTIL, null, s.plusSeconds(2)),
            new BoundaryInput("absent-attr", Kind.ATTRIBUTE, null, null),
            new BoundaryInput("absent-lock", Kind.LOCKED_UNTIL, null, null));
    assertThat(r.earliestFuture()).contains(s.plusSeconds(2));
    assertThat(
            compile(new BoundaryInput("attr", Kind.ATTRIBUTE, s.plusSeconds(1), null))
                .earliestFuture())
        .contains(s.plusSeconds(1));
    rejected(compile(new BoundaryInput("lock", Kind.LOCKED_UNTIL, s, null)), Reason.INVALID_INPUT);
  }

  @Test
  void requiredGrantAndPolicyEndpointsAreValidated() {
    for (Kind kind : List.of(Kind.DIRECT_GRANT, Kind.GRANT_OR_MEMBERSHIP, Kind.POLICY_VERSION))
      rejected(
          compile(new BoundaryInput("missing", kind, null, s.plusSeconds(1))),
          Reason.INVALID_INPUT);
    rejected(
        compile(new BoundaryInput("direct", Kind.DIRECT_GRANT, s, null)), Reason.INVALID_INPUT);
    for (Kind kind : List.of(Kind.GRANT_OR_MEMBERSHIP, Kind.POLICY_VERSION))
      assertThat(compile(new BoundaryInput("open", kind, s.plusSeconds(1), null)).earliestFuture())
          .contains(s.plusSeconds(1));
    rejected(compile(window("empty", Kind.ATTRIBUTE, 1, 1)), Reason.INVALID_WINDOW);
    rejected(compile(window("reversed", Kind.POLICY_VERSION, 2, 1)), Reason.INVALID_WINDOW);
    rejected(compile(new BoundaryInput(" ", Kind.ATTRIBUTE, null, null)), Reason.INVALID_INPUT);
    rejected(
        compile(new BoundaryInput("x".repeat(161), Kind.ATTRIBUTE, null, null)),
        Reason.INVALID_INPUT);
    rejected(compile(new BoundaryInput("id", null, null, null)), Reason.INVALID_INPUT);
    rejected(compiler.compile(null, List.of()), Reason.INVALID_INPUT);
    rejected(compiler.compile(s, null), Reason.INVALID_INPUT);
    rejected(compile((BoundaryInput) null), Reason.INVALID_INPUT);
  }

  @Test
  void b1ExpiryAtOrBeforeSampleRejects() {
    for (long delta : List.of(-1L, 0L))
      rejected(
          compile(new BoundaryInput("session", Kind.B1_EXPIRY, null, s.plusSeconds(delta))),
          Reason.EXPIRED_B1);
    rejected(
        compile(new BoundaryInput("session", Kind.B1_EXPIRY, null, null)), Reason.INVALID_INPUT);
    rejected(
        compile(new BoundaryInput("session", Kind.B1_EXPIRY, s, s.plusSeconds(1))),
        Reason.INVALID_INPUT);
  }

  @Test
  void futureB1ExpiryIntersectsOtherWindows() {
    assertThat(
            compile(
                    new BoundaryInput("source", Kind.B1_EXPIRY, null, s.plusSeconds(2)),
                    window("grant", Kind.DIRECT_GRANT, -2, 5))
                .earliestFuture())
        .contains(s.plusSeconds(2));
    assertThat(
            compile(
                    new BoundaryInput("source", Kind.B1_EXPIRY, null, s.plusSeconds(5)),
                    window("future", Kind.POLICY_VERSION, 1, 3))
                .earliestFuture())
        .contains(s.plusSeconds(1));
  }

  @Test
  void duplicateAndOversizedInputsRejectWithoutPartialPlan() {
    var b = window("same", Kind.DIRECT_GRANT, -1, 3);
    rejected(compile(b, b), Reason.DUPLICATE_SOURCE);
    rejected(compiler.compile(s, Collections.nCopies(1025, b)), Reason.EXCESSIVE_INPUT);
    assertThat(
            compile(b, new BoundaryInput("same", Kind.ATTRIBUTE, null, s.plusSeconds(3)))
                .contributors())
        .hasSize(2);
    var full = new ArrayList<BoundaryInput>();
    for (int i = 0; i < 1024; i++)
      full.add(new BoundaryInput("id" + i, Kind.ATTRIBUTE, null, s.plusSeconds(i + 1)));
    assertThat(compiler.compile(s, full).earliestFuture()).contains(s.plusSeconds(1));
  }

  @Test
  void postgresPrecisionAndFiniteRangeAreRequired() {
    for (Instant invalid :
        List.of(
            s.plusNanos(1),
            Instant.MIN,
            Instant.MAX,
            Instant.ofEpochSecond(-210866803201L),
            Instant.ofEpochSecond(9224318016000L))) {
      rejected(compiler.compile(invalid, List.of()), Reason.UNREPRESENTABLE_TIME);
      rejected(
          compile(new BoundaryInput("time", Kind.ATTRIBUTE, null, invalid)),
          Reason.UNREPRESENTABLE_TIME);
    }
    assertThat(
            compile(new BoundaryInput("micro", Kind.ATTRIBUTE, null, s.plusNanos(1000)))
                .earliestFuture())
        .contains(s.plusNanos(1000));
    assertThat(compiler.compile(Instant.ofEpochSecond(-210866803200L), List.of()).disposition())
        .isEqualTo(Disposition.NO_FUTURE_BOUNDARY);
  }

  @Test
  void tiedContributorsAndResultsAreImmutable() {
    var inputs =
        new ArrayList<>(
            List.of(window("z", Kind.DIRECT_GRANT, -1, 2), window("a", Kind.POLICY_VERSION, 2, 3)));
    var r = compiler.compile(s, inputs);
    inputs.clear();
    assertThat(r.contributors())
        .containsExactly(
            new Contributor("a", Kind.POLICY_VERSION, Endpoint.FROM),
            new Contributor("z", Kind.DIRECT_GRANT, Endpoint.TO));
    assertThatThrownBy(() -> r.contributors().clear())
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void inputOrderingCannotChangeReduction() {
    var inputs =
        new ArrayList<>(
            List.of(
                window("z", Kind.DIRECT_GRANT, -1, 2),
                window("a", Kind.POLICY_VERSION, 2, 3),
                new BoundaryInput("source", Kind.B1_EXPIRY, null, s.plusSeconds(10))));
    var r = compiler.compile(s, inputs);
    Collections.reverse(inputs);
    assertThat(compiler.compile(s, inputs)).isEqualTo(r);
  }

  @Test
  void noFutureBoundaryIsNotUnlimitedAuthority() {
    var r = compiler.compile(s, List.of());
    assertThat(r.disposition()).isEqualTo(Disposition.NO_FUTURE_BOUNDARY);
    assertThat(r.earliestFuture()).isEmpty();
    assertThat(r.contributors()).isEmpty();
    assertThat(r.reason()).isEqualTo(Reason.NONE);
    assertThatThrownBy(
            () ->
                new Compilation(
                    Disposition.NO_FUTURE_BOUNDARY,
                    Optional.of(Instant.MAX),
                    List.of(),
                    Reason.NONE))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(
            () ->
                new Compilation(Disposition.UNSUPPORTED, Optional.empty(), List.of(), Reason.NONE))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
