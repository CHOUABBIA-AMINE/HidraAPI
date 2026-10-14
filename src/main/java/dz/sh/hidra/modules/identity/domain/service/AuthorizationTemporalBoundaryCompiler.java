/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationTemporalBoundaryCompiler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Reduces supplied temporal boundaries without establishing coverage or permission.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import java.time.Instant;
import java.util.*;

/** Pure Identity-private reduction; a candidate is not an authority or complete owner inventory. */
public final class AuthorizationTemporalBoundaryCompiler {
  public enum Kind {
    DIRECT_GRANT,
    GRANT_OR_MEMBERSHIP,
    POLICY_VERSION,
    ATTRIBUTE,
    LOCKED_UNTIL,
    B1_EXPIRY
  }

  public enum Endpoint {
    FROM,
    TO
  }

  public enum Disposition {
    BOUNDED_CANDIDATE,
    NO_FUTURE_BOUNDARY,
    UNSUPPORTED
  }

  public enum Reason {
    NONE,
    INVALID_INPUT,
    INVALID_WINDOW,
    UNREPRESENTABLE_TIME,
    DUPLICATE_SOURCE,
    EXCESSIVE_INPUT,
    EXPIRED_B1
  }

  public record BoundaryInput(String sourceId, Kind kind, Instant from, Instant to) {}

  public record Contributor(String sourceId, Kind kind, Endpoint endpoint) {}

  public record Compilation(
      Disposition disposition,
      Optional<Instant> earliestFuture,
      List<Contributor> contributors,
      Reason reason) {
    public Compilation {
      Objects.requireNonNull(disposition);
      Objects.requireNonNull(earliestFuture);
      Objects.requireNonNull(reason);
      contributors = List.copyOf(Objects.requireNonNull(contributors));
      if (disposition == Disposition.BOUNDED_CANDIDATE) {
        if (earliestFuture.isEmpty() || contributors.isEmpty() || reason != Reason.NONE)
          throw new IllegalArgumentException("Invalid candidate");
      } else if (earliestFuture.isPresent()
          || !contributors.isEmpty()
          || (disposition == Disposition.UNSUPPORTED) == (reason == Reason.NONE)) {
        throw new IllegalArgumentException("Invalid empty compilation");
      }
    }
  }

  private record SourceKey(String id, Kind kind) {}

  private static final Comparator<Contributor> ORDER =
      Comparator.comparing(Contributor::sourceId)
          .thenComparing(c -> c.kind().name())
          .thenComparing(c -> c.endpoint().name());

  public Compilation compile(Instant sampledAt, List<BoundaryInput> inputs) {
    if (sampledAt == null || inputs == null) return rejected(Reason.INVALID_INPUT);
    if (!representable(sampledAt)) return rejected(Reason.UNREPRESENTABLE_TIME);
    if (inputs.size() > 1024) return rejected(Reason.EXCESSIVE_INPUT);
    final List<BoundaryInput> snapshot;
    try {
      snapshot = List.copyOf(inputs);
    } catch (NullPointerException ex) {
      return rejected(Reason.INVALID_INPUT);
    }
    Set<SourceKey> seen = new HashSet<>();
    Instant earliest = null;
    List<Contributor> ties = new ArrayList<>();
    for (BoundaryInput input : snapshot) {
      if (!validId(input.sourceId()) || input.kind() == null) return rejected(Reason.INVALID_INPUT);
      if (!seen.add(new SourceKey(input.sourceId(), input.kind())))
        return rejected(Reason.DUPLICATE_SOURCE);
      if ((input.from() != null && !representable(input.from()))
          || (input.to() != null && !representable(input.to())))
        return rejected(Reason.UNREPRESENTABLE_TIME);
      switch (input.kind()) {
        case DIRECT_GRANT -> {
          if (input.from() == null || input.to() == null) return rejected(Reason.INVALID_INPUT);
        }
        case GRANT_OR_MEMBERSHIP, POLICY_VERSION -> {
          if (input.from() == null) return rejected(Reason.INVALID_INPUT);
        }
        case LOCKED_UNTIL -> {
          if (input.from() != null) return rejected(Reason.INVALID_INPUT);
        }
        case B1_EXPIRY -> {
          if (input.from() != null || input.to() == null) return rejected(Reason.INVALID_INPUT);
          if (!input.to().isAfter(sampledAt)) return rejected(Reason.EXPIRED_B1);
        }
        case ATTRIBUTE -> {}
      }
      if (input.from() != null && input.to() != null && !input.from().isBefore(input.to()))
        return rejected(Reason.INVALID_WINDOW);
      for (Endpoint endpoint : Endpoint.values()) {
        Instant value = endpoint == Endpoint.FROM ? input.from() : input.to();
        if (value == null || !value.isAfter(sampledAt)) continue;
        if (earliest == null || value.isBefore(earliest)) {
          earliest = value;
          ties.clear();
        }
        if (value.equals(earliest))
          ties.add(new Contributor(input.sourceId(), input.kind(), endpoint));
      }
    }
    if (earliest == null)
      return new Compilation(
          Disposition.NO_FUTURE_BOUNDARY, Optional.empty(), List.of(), Reason.NONE);
    ties.sort(ORDER);
    return new Compilation(Disposition.BOUNDED_CANDIDATE, Optional.of(earliest), ties, Reason.NONE);
  }

  private Compilation rejected(Reason reason) {
    return new Compilation(Disposition.UNSUPPORTED, Optional.empty(), List.of(), reason);
  }

  private boolean validId(String id) {
    return id != null
        && !id.isBlank()
        && id.equals(id.strip())
        && id.codePointCount(0, id.length()) <= 160
        && id.codePoints().noneMatch(Character::isISOControl);
  }

  private boolean representable(Instant value) {
    return value.getEpochSecond() >= -210866803200L
        && value.getEpochSecond() < 9224318016000L
        && value.getNano() % 1000 == 0;
  }
}
