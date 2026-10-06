/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationExpressionEvaluator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import dz.sh.hidra.modules.identity.domain.policy.AuthorizationJson;
import java.math.BigDecimal;
import java.util.*;

/** Grammar v1: {op, attribute, type, value}, {op, args}, or {op:not, arg}. */
public final class AuthorizationExpressionEvaluator {
    public enum Result { MATCH, NO_MATCH, INDETERMINATE }
    public Result evaluate(String expression, Map<String,Object> attributes) {
        if (expression==null) return Result.MATCH;
        try { return predicate(AuthorizationJson.parse(expression),attributes)?Result.MATCH:Result.NO_MATCH; }
        catch (IllegalArgumentException ex) { return Result.INDETERMINATE; }
    }
    private boolean predicate(Object expression, Map<String,Object> attrs) {
        if (!(expression instanceof Map<?,?> m) || !(m.get("op") instanceof String op)) throw invalid();
        if (op.equals("all") || op.equals("any")) {
            fields(m,Set.of("op","args"));
            if (!(m.get("args") instanceof List<?> list) || list.isEmpty()) throw invalid();
            boolean result=op.equals("all");
            // Deliberately evaluate every branch: malformed/missing evidence never hides behind short circuiting.
            for(Object child:list) { boolean b=predicate(child,attrs); result=op.equals("all")?(result & b):(result | b); }
            return result;
        }
        if(op.equals("not")) { fields(m,Set.of("op","arg")); return !predicate(m.get("arg"),attrs); }
        if (!(m.get("attribute") instanceof String key) || !key.matches("(subject|resource|action|context)\\.[A-Za-z0-9_.-]+")) throw invalid();
        if(op.equals("exists")) { fields(m,Set.of("op","attribute")); return attrs.containsKey(key); }
        if (!op.equals("eq") && !op.equals("in")) throw invalid();
        fields(m,Set.of("op","attribute","type","value"));
        Object actual=attrs.get(key); if(actual==null) throw invalid();
        String type=Objects.toString(m.get("type"),""); Object expected=m.get("value");
        if(op.equals("eq")) return equal(type,actual,expected);
        if (!(expected instanceof List<?> list) || list.isEmpty()) throw invalid();
        boolean result=false;
        if(actual instanceof Collection<?> values) {
            for(Object a:values) for(Object v:list) result |= equal(type,a,v);
        } else for(Object v:list) result |= equal(type,actual,v);
        return result;
    }
    private boolean equal(String type,Object a,Object b) {
        boolean valid=switch(type) {
            case "STRING" -> a instanceof String && b instanceof String;
            case "NUMBER" -> a instanceof BigDecimal && b instanceof BigDecimal;
            case "BOOLEAN" -> a instanceof Boolean && b instanceof Boolean;
            case "DATE" -> a instanceof String && b instanceof String && date(a) && date(b);
            case "DATETIME" -> a instanceof String && b instanceof String && instant(a) && instant(b);
            default -> false;
        };
        if(!valid) throw invalid();
        if(a instanceof BigDecimal x && b instanceof BigDecimal y) return x.compareTo(y)==0;
        return a.equals(b);
    }
    private boolean date(Object v) { try { java.time.LocalDate.parse(v.toString()); return true; } catch(RuntimeException ex) { return false; } }
    private boolean instant(Object v) { try { java.time.Instant.parse(v.toString()); return true; } catch(RuntimeException ex) { return false; } }
    private void fields(Map<?,?> m,Set<String> fields) { if(!m.keySet().equals(fields)) throw invalid(); }
    private IllegalArgumentException invalid() { return new IllegalArgumentException("Unsupported policy expression or attribute evidence"); }
}
