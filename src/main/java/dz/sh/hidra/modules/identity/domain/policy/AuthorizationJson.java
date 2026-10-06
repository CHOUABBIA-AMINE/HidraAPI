/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationJson
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.policy
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.domain.policy;

import java.math.BigDecimal;
import java.util.*;

/** Limited JSON codec for policy documents; rejects duplicate fields and oversized input. */
public final class AuthorizationJson {
    private AuthorizationJson() { }
    public static Object parse(String input) {
        if (input == null || input.length() > 8192) throw new IllegalArgumentException("JSON size");
        Parser p = new Parser(input);
        Object value = p.value(0);
        p.space();
        if (p.i != input.length()) throw new IllegalArgumentException("JSON trailing input");
        return value;
    }
    public static String write(Object value) {
        if (value == null) return "null";
        if (value instanceof String s) {
            StringBuilder b = new StringBuilder("\"");
            for (int i=0;i<s.length();i++) {
                char c=s.charAt(i);
                if (c=='"' || c=='\\') b.append('\\').append(c);
                else if (c<32) b.append(String.format("\\u%04x",(int)c));
                else b.append(c);
            }
            return b.append('"').toString();
        }
        if (value instanceof Boolean || value instanceof BigDecimal || value instanceof Integer) return value.toString();
        if (value instanceof Collection<?> c) return "["+String.join(",",c.stream().map(AuthorizationJson::write).toList())+"]";
        if (value instanceof Map<?,?> m) {
            List<String> fields = new ArrayList<>();
            m.keySet().stream().map(Object::toString).sorted().forEach(k -> fields.add(write(k)+":"+write(m.get(k))));
            return "{"+String.join(",",fields)+"}";
        }
        throw new IllegalArgumentException("Unsupported JSON type");
    }
    private static final class Parser {
        final String s; int i; int nodes;
        Parser(String s) { this.s=s; }
        void space() { while (i<s.length() && " \t\r\n".indexOf(s.charAt(i))>=0) i++; }
        char take() { if (i>=s.length()) throw new IllegalArgumentException("JSON end"); return s.charAt(i++); }
        void expect(char c) { space(); if (take()!=c) throw new IllegalArgumentException("JSON punctuation"); }
        Object value(int depth) {
            if (depth>16 || ++nodes>256) throw new IllegalArgumentException("JSON complexity");
            space(); if (i>=s.length()) throw new IllegalArgumentException("JSON empty");
            char c=s.charAt(i);
            if (c=='"') return string();
            if (c=='{') {
                i++; Map<String,Object> m=new LinkedHashMap<>(); space();
                if (i<s.length() && s.charAt(i)=='}') { i++; return m; }
                do {
                    space(); String k=string(); expect(':'); Object v=value(depth+1);
                    if (m.containsKey(k)) throw new IllegalArgumentException("Duplicate JSON field");
                    m.put(k,v); space(); c=take(); if (c=='}') return m;
                    if (c!=',') throw new IllegalArgumentException("JSON object");
                } while (true);
            }
            if (c=='[') {
                i++; List<Object> a=new ArrayList<>(); space();
                if (i<s.length() && s.charAt(i)==']') { i++; return a; }
                do { a.add(value(depth+1)); space(); c=take(); if (c==']') return a;
                    if (c!=',') throw new IllegalArgumentException("JSON array"); } while (true);
            }
            for (String literal:List.of("true","false","null")) if(s.startsWith(literal,i)) {
                i+=literal.length(); return literal.equals("null")?null:Boolean.valueOf(literal);
            }
            int start=i; while(i<s.length() && "-+0123456789.eE".indexOf(s.charAt(i))>=0) i++;
            String n=s.substring(start,i);
            if(!n.matches("-?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE][+-]?[0-9]+)?")) throw new IllegalArgumentException("JSON number");
            BigDecimal number=new BigDecimal(n);
            if (Math.abs((long)number.scale())>1000) throw new IllegalArgumentException("JSON number scale");
            return number.stripTrailingZeros();
        }
        String string() {
            if(take()!='"') throw new IllegalArgumentException("JSON string");
            StringBuilder b=new StringBuilder();
            while(true) {
                char c=take(); if(c=='"') return b.toString();
                if(c<32) throw new IllegalArgumentException("JSON control character");
                if(c=='\\') {
                    char e=take();
                    switch(e) {
                        case '"','\\','/' -> b.append(e);
                        case 'b' -> b.append('\b'); case 'f' -> b.append('\f');
                        case 'n' -> b.append('\n'); case 'r' -> b.append('\r'); case 't' -> b.append('\t');
                        case 'u' -> { if(i+4>s.length()) throw new IllegalArgumentException("JSON unicode");
                            b.append((char)Integer.parseInt(s.substring(i,i+4),16)); i+=4; }
                        default -> throw new IllegalArgumentException("JSON escape");
                    }
                } else b.append(c);
            }
        }
    }
}
