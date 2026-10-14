/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * AuthorizationExpressionStabilityCompilerTest @CreatedOn : 2025-06-26 @UpdatedOn :
 * 2026-10-14 @Type : Class @Layer : Identity Test @Module : identity @Package :
 * dz.sh.hidra.modules.identity.domain.service @Description : Verifies static syntax classification
 * and existing evaluator compatibility.
 */
package dz.sh.hidra.modules.identity.domain.service;

import static dz.sh.hidra.modules.identity.domain.service.AuthorizationExpressionStabilityCompiler.*;
import static org.assertj.core.api.Assertions.*;

import dz.sh.hidra.modules.identity.domain.policy.AuthorizationJson;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;

class AuthorizationExpressionStabilityCompilerTest {
  private final AuthorizationExpressionStabilityCompiler compiler =
      new AuthorizationExpressionStabilityCompiler();
  private final AuthorizationExpressionEvaluator evaluator = new AuthorizationExpressionEvaluator();

  private String eq(String attribute, String type, Object value) {
    return AuthorizationJson.write(
        Map.of("op", "eq", "attribute", attribute, "type", type, "value", value));
  }

  private String exists(String key) {
    return AuthorizationJson.write(Map.of("op", "exists", "attribute", key));
  }

  private void supported(String expression) {
    assertThat(compiler.compile(expression).classification())
        .isEqualTo(Classification.SUPPORTED_STATIC_SYNTAX);
    assertThat(compiler.compile(expression).reason()).isEqualTo(Reason.NONE);
  }

  private void rejected(String expression, Reason reason) {
    var result = compiler.compile(expression);
    assertThat(result.classification()).isEqualTo(Classification.UNSUPPORTED);
    assertThat(result.reason()).isEqualTo(reason);
    assertThat(result.referencedAttributes()).isEmpty();
  }

  @Test
  void nullExpressionIsUnconditionalSyntax() {
    supported(null);
    assertThat(compiler.compile(null).referencedAttributes()).isEmpty();
    rejected("null", Reason.MALFORMED_SYNTAX);
  }

  @Test
  void nestedStaticSyntaxCollectsAllDependencies() {
    String expression =
        "{\"op\":\"all\",\"args\":["
            + exists("subject.id")
            + ",{\"op\":\"any\",\"args\":["
            + exists("action.name")
            + ",{\"op\":\"not\",\"arg\":"
            + exists("resource.type")
            + "}]}]}";
    supported(expression);
    assertThat(compiler.compile(expression).referencedAttributes())
        .containsExactly("action.name", "resource.type", "subject.id");
  }

  @Test
  void clockReferenceIsRejectedAtEveryNestingLevel() {
    String clock = exists("context.evaluatedAt");
    for (String e :
        List.of(
            clock,
            "{\"op\":\"not\",\"arg\":" + clock + "}",
            "{\"op\":\"all\",\"args\":[" + exists("subject.id") + "," + clock + "]}",
            "{\"op\":\"any\",\"args\":[" + exists("subject.id") + "," + clock + "]}",
            clock.replace("evaluatedAt", "\\u0065valuatedAt")))
      rejected(e, Reason.CLOCK_DEPENDENT_REFERENCE);
  }

  @Test
  void unclassifiedReferencesCannotBeCallerAdmitted() {
    for (String key :
        List.of("subject.attributes.rank", "context.now", "resource.custom", "action.custom"))
      rejected(exists(key), Reason.UNCLASSIFIED_REFERENCE);
    for (String key :
        List.of(
            "subject.id",
            "subject.type",
            "subject.status",
            "subject.groupIds",
            "resource.type",
            "resource.referenceId",
            "action.permissionCode",
            "action.name",
            "context.scopeType",
            "context.scopeReferenceId")) supported(exists(key));
  }

  @Test
  void malformedAndDuplicateFieldsAreRejected() {
    for (String e :
        List.of(
            "",
            "[]",
            "{}",
            "{\"op\":\"exists\",\"attribute\":\"subject.id\",\"op\":\"exists\"}",
            "{\"op\":\"all\",\"args\":[]}",
            "{\"op\":\"any\",\"args\":[]}",
            "{\"op\":\"not\"}",
            "{\"op\":\"exists\"}",
            "{\"op\":\"eq\",\"attribute\":\"subject.id\",\"type\":\"STRING\"}",
            "{\"op\":\"exists\",\"attribute\":\"subject.id\",\"extra\":1}",
            "{\"op\":\"exists\",\"attribute\":\"bad\"}")) rejected(e, Reason.MALFORMED_SYNTAX);
  }

  @Test
  void unknownOperatorsAndTypesAreRejected() {
    rejected("{\"op\":\"before\",\"attribute\":\"subject.id\"}", Reason.UNSUPPORTED_OPERATOR);
    rejected(eq("subject.id", "JSON", "x"), Reason.UNSUPPORTED_TYPE);
  }

  @Test
  void everyInLiteralMustMatchDeclaredType() {
    for (String e :
        List.of(
            "{\"op\":\"in\",\"attribute\":\"subject.id\",\"type\":\"STRING\",\"value\":[\"x\",1]}",
            "{\"op\":\"in\",\"attribute\":\"subject.id\",\"type\":\"STRING\",\"value\":[]}",
            "{\"op\":\"eq\",\"attribute\":\"subject.id\",\"type\":\"STRING\",\"value\":null}"))
      rejected(e, Reason.INVALID_LITERAL);
    for (var value :
        Map.of(
                "STRING",
                (Object) "x",
                "NUMBER",
                new BigDecimal("1.00"),
                "BOOLEAN",
                true,
                "DATE",
                "2026-10-14",
                "DATETIME",
                "2026-10-14T00:00:00Z")
            .entrySet()) {
      String e =
          AuthorizationJson.write(
              Map.of(
                  "op",
                  "in",
                  "attribute",
                  "subject.id",
                  "type",
                  value.getKey(),
                  "value",
                  List.of(value.getValue())));
      supported(e);
      assertThat(evaluator.evaluate(e, Map.of("subject.id", value.getValue())))
          .isEqualTo(AuthorizationExpressionEvaluator.Result.MATCH);
    }
    rejected(eq("subject.id", "NUMBER", "1"), Reason.INVALID_LITERAL);
    rejected(eq("subject.id", "BOOLEAN", "true"), Reason.INVALID_LITERAL);
  }

  @Test
  void dateAndDatetimeConstantsKeepEvaluatorSemantics() {
    for (var value : Map.of("DATE", "2026-10-14", "DATETIME", "2026-10-14T00:00:00Z").entrySet()) {
      String e = eq("subject.id", value.getKey(), value.getValue());
      supported(e);
      assertThat(evaluator.evaluate(e, Map.of("subject.id", value.getValue())))
          .isEqualTo(AuthorizationExpressionEvaluator.Result.MATCH);
    }
    String e = eq("subject.id", "DATETIME", "2026-10-14T00:00:00Z");
    assertThat(evaluator.evaluate(e, Map.of("subject.id", "2026-10-14T01:00:00+01:00")))
        .isEqualTo(AuthorizationExpressionEvaluator.Result.NO_MATCH);
    rejected(eq("subject.id", "DATE", "2026-02-30"), Reason.INVALID_LITERAL);
    rejected(eq("subject.id", "DATETIME", "tomorrow"), Reason.INVALID_LITERAL);
  }

  @Test
  void unsupportedBranchCannotHideBehindBooleanOutcome() {
    for (String op : List.of("all", "any")) {
      String e =
          "{\"op\":\""
              + op
              + "\",\"args\":["
              + exists("subject.id")
              + ","
              + exists("context.evaluatedAt")
              + "]}";
      rejected(e, Reason.CLOCK_DEPENDENT_REFERENCE);
    }
    rejected(
        "{\"op\":\"not\",\"arg\":" + exists("subject.attributes.hidden") + "}",
        Reason.UNCLASSIFIED_REFERENCE);
  }

  @Test
  void jsonResourceBoundsRemainEnforced() {
    rejected(" ".repeat(8193), Reason.MALFORMED_SYNTAX);
    String deep = exists("subject.id");
    for (int i = 0; i < 20; i++) deep = "{\"op\":\"not\",\"arg\":" + deep + "}";
    rejected(deep, Reason.MALFORMED_SYNTAX);
    String many =
        "{\"op\":\"all\",\"args\":["
            + String.join(",", Collections.nCopies(100, exists("subject.id")))
            + "]}";
    rejected(many, Reason.MALFORMED_SYNTAX);
    rejected(
        "{\"op\":\"eq\",\"attribute\":\"subject.id\",\"type\":\"NUMBER\",\"value\":1e1001}",
        Reason.MALFORMED_SYNTAX);
  }

  @Test
  void resultsAreImmutableAndDeterministic() {
    String e =
        "{\"op\":\"any\",\"args\":[" + exists("subject.id") + "," + exists("action.name") + "]}";
    var r = compiler.compile(e);
    assertThat(r).isEqualTo(compiler.compile(e));
    assertThatThrownBy(() -> r.referencedAttributes().add("context.now"))
        .isInstanceOf(UnsupportedOperationException.class);
    var mutable = new HashSet<>(Set.of("subject.id"));
    var copied = new Compilation(Classification.SUPPORTED_STATIC_SYNTAX, mutable, Reason.NONE);
    mutable.clear();
    assertThat(copied.referencedAttributes()).containsExactly("subject.id");
  }

  @Test
  void supportedSyntaxDoesNotOverrideIndeterminateEvaluation() {
    String e = eq("subject.id", "STRING", "x");
    supported(e);
    assertThat(evaluator.evaluate(e, Map.of()))
        .isEqualTo(AuthorizationExpressionEvaluator.Result.INDETERMINATE);
    assertThat(evaluator.evaluate(e, Map.of("subject.id", true)))
        .isEqualTo(AuthorizationExpressionEvaluator.Result.INDETERMINATE);
    assertThat(evaluator.evaluate(e, Map.of("subject.id", "y")))
        .isEqualTo(AuthorizationExpressionEvaluator.Result.NO_MATCH);
  }
}
