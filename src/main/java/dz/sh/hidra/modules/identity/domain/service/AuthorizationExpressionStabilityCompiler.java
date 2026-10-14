/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * AuthorizationExpressionStabilityCompiler @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-14 @Type :
 * Class @Layer : Domain @Module : identity @Package :
 * dz.sh.hidra.modules.identity.domain.service @Description : Classifies protected static expression
 * syntax without establishing authority.
 */
package dz.sh.hidra.modules.identity.domain.service;

import dz.sh.hidra.modules.identity.domain.policy.AuthorizationJson;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

/** Identity-private syntax analysis; supported syntax is never a permit or validity interval. */
public final class AuthorizationExpressionStabilityCompiler {
  public enum Classification {
    SUPPORTED_STATIC_SYNTAX,
    UNSUPPORTED
  }

  public enum Reason {
    NONE,
    MALFORMED_SYNTAX,
    UNSUPPORTED_OPERATOR,
    UNSUPPORTED_TYPE,
    CLOCK_DEPENDENT_REFERENCE,
    UNCLASSIFIED_REFERENCE,
    INVALID_LITERAL
  }

  public record Compilation(
      Classification classification, Set<String> referencedAttributes, Reason reason) {
    public Compilation {
      Objects.requireNonNull(classification);
      Objects.requireNonNull(reason);
      referencedAttributes =
          Collections.unmodifiableSortedSet(
              new TreeSet<>(Objects.requireNonNull(referencedAttributes)));
      if (classification == Classification.UNSUPPORTED) {
        if (reason == Reason.NONE || !referencedAttributes.isEmpty())
          throw new IllegalArgumentException("Unsupported compilation must have no partial plan");
      } else if (reason != Reason.NONE) {
        throw new IllegalArgumentException("Supported compilation must have no rejection");
      }
    }
  }

  private static final Set<String> FACTS =
      Set.of(
          "subject.id",
          "subject.type",
          "subject.status",
          "subject.groupIds",
          "resource.type",
          "resource.referenceId",
          "action.permissionCode",
          "action.name",
          "context.scopeType",
          "context.scopeReferenceId");

  public Compilation compile(String expression) {
    Set<String> references = new TreeSet<>();
    try {
      if (expression != null) visit(AuthorizationJson.parse(expression), references);
      return new Compilation(Classification.SUPPORTED_STATIC_SYNTAX, references, Reason.NONE);
    } catch (Rejected ex) {
      return rejected(ex.reason);
    } catch (IllegalArgumentException ex) {
      return rejected(Reason.MALFORMED_SYNTAX);
    }
  }

  private Compilation rejected(Reason reason) {
    return new Compilation(Classification.UNSUPPORTED, Set.of(), reason);
  }

  private void visit(Object node, Set<String> references) {
    if (!(node instanceof Map<?, ?> fields) || !(fields.get("op") instanceof String op))
      throw reject(Reason.MALFORMED_SYNTAX);
    if (op.equals("all") || op.equals("any")) {
      exact(fields, Set.of("op", "args"));
      if (!(fields.get("args") instanceof List<?> children) || children.isEmpty())
        throw reject(Reason.MALFORMED_SYNTAX);
      // Analyze every branch, independent of any eventual Boolean outcome.
      Rejected first = null;
      for (Object child : children) {
        try {
          visit(child, references);
        } catch (Rejected ex) {
          if (first == null) first = ex;
        }
      }
      if (first != null) throw first;
      return;
    }
    if (op.equals("not")) {
      exact(fields, Set.of("op", "arg"));
      visit(fields.get("arg"), references);
      return;
    }
    if (!Set.of("exists", "eq", "in").contains(op)) throw reject(Reason.UNSUPPORTED_OPERATOR);
    exact(
        fields,
        op.equals("exists")
            ? Set.of("op", "attribute")
            : Set.of("op", "attribute", "type", "value"));
    if (!(fields.get("attribute") instanceof String key)
        || !key.matches("(subject|resource|action|context)\\.[A-Za-z0-9_.-]+"))
      throw reject(Reason.MALFORMED_SYNTAX);
    if (key.equals("context.evaluatedAt")) throw reject(Reason.CLOCK_DEPENDENT_REFERENCE);
    if (!FACTS.contains(key)) throw reject(Reason.UNCLASSIFIED_REFERENCE);
    references.add(key);
    if (op.equals("exists")) return;
    if (!(fields.get("type") instanceof String type)
        || !Set.of("STRING", "NUMBER", "BOOLEAN", "DATE", "DATETIME").contains(type))
      throw reject(Reason.UNSUPPORTED_TYPE);
    Object value = fields.get("value");
    if (op.equals("eq")) literal(type, value);
    else {
      if (!(value instanceof List<?> values) || values.isEmpty())
        throw reject(Reason.INVALID_LITERAL);
      for (Object item : values) literal(type, item);
    }
  }

  private void literal(String type, Object value) {
    boolean valid =
        switch (type) {
          case "STRING" -> value instanceof String;
          case "NUMBER" -> value instanceof BigDecimal;
          case "BOOLEAN" -> value instanceof Boolean;
          case "DATE", "DATETIME" -> validTime(type, value);
          default -> false;
        };
    if (!valid) throw reject(Reason.INVALID_LITERAL);
  }

  private boolean validTime(String type, Object value) {
    if (!(value instanceof String text)) return false;
    try {
      if (type.equals("DATE")) LocalDate.parse(text);
      else Instant.parse(text);
      return true;
    } catch (DateTimeException ex) {
      return false;
    }
  }

  private void exact(Map<?, ?> fields, Set<String> expected) {
    if (!fields.keySet().equals(expected)) throw reject(Reason.MALFORMED_SYNTAX);
  }

  private Rejected reject(Reason reason) {
    return new Rejected(reason);
  }

  private static final class Rejected extends IllegalArgumentException {
    private final Reason reason;

    private Rejected(Reason reason) {
      super(reason.name());
      this.reason = reason;
    }
  }
}
