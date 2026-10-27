/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuppliedFluidPropertyRevision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Preserves exact authored fluid facts independently of numerical qualification.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;
import java.math.BigDecimal;
import java.util.*;

/** Supplied declarations only; no conversion, EOS, normalization or qualification. */
public record SuppliedFluidPropertyRevision(UUID productDraftId,String companyPartyId,String localCode,
        String catalogProductId,long version,long productMetadataVersion,Classification classification,Facts facts) {
    public enum Classification { GAS, LIQUID }
    public enum Basis { MOLE_FRACTION, MASS_FRACTION }
    public enum Property { DENSITY, DYNAMIC_VISCOSITY, COMPRESSIBILITY_FACTOR }
    public enum Origin { DECLARED_PARAMETER, SYNTHETIC }
    public record Decimal(String lexeme) {
        public Decimal {if(lexeme==null || !lexeme.matches("(0|[1-9][0-9]{0,33})(\\.[0-9]{1,18})?"))throw invalid();}
        public BigDecimal value(){return new BigDecimal(lexeme);}
        public Decimal positive(){if(value().signum()<=0)throw invalid();return this;}
    }
    public record Source(Origin origin,String reference,String evidenceReference) {
        public Source {Objects.requireNonNull(origin);text(reference,80,true);text(evidenceReference,1000,false);}
    }
    public record MethodKey(String reference,String revision) {
        public MethodKey{text(reference,80,true);text(revision,80,true);}
    }
    public record Component(String reference,Decimal fraction) {
        public Component {text(reference,80,true);Objects.requireNonNull(fraction).positive();if(fraction.value().compareTo(BigDecimal.ONE)>0)throw invalid();}
    }
    public record Composition(Basis basis,List<Component> components,MethodKey method,Source source) {
        public Composition {Objects.requireNonNull(basis);components=copy(components,64,false);unique(components.stream().map(Component::reference).toList());
            if(components.stream().map(x->x.fraction().value()).reduce(BigDecimal.ZERO,BigDecimal::add).compareTo(BigDecimal.ONE)!=0)throw invalid();}
    }
    public record Condition(Decimal value,String unitId) {
        public Condition {Objects.requireNonNull(value).positive();text(unitId,80,true);}
    }
    public record Range(Decimal minimum,Decimal maximum,String unitId) {
        public Range {Objects.requireNonNull(minimum).positive();Objects.requireNonNull(maximum).positive();text(unitId,80,true);
            if(minimum.value().compareTo(maximum.value())>0)throw invalid();}
        public void contains(Condition c){if(!unitId.equals(c.unitId()) || c.value().value().compareTo(minimum.value())<0 || c.value().value().compareTo(maximum.value())>0)throw invalid();}
    }
    public record Observation(String observationId,String name,Property property,Decimal value,String unitId,
            Condition absolutePressure,Condition absoluteTemperature,MethodKey method,Source source) {
        public Observation {text(observationId,80,true);text(name,240,false);Objects.requireNonNull(property);Objects.requireNonNull(value).positive();
            text(unitId,80,true);Objects.requireNonNull(absolutePressure);Objects.requireNonNull(absoluteTemperature);}
    }
    public record Method(MethodKey key,Origin origin,Basis basis,List<String> components,List<Property> properties,
            Range absolutePressure,Range absoluteTemperature,String evidenceReference) {
        public Method {Objects.requireNonNull(key);Objects.requireNonNull(origin);Objects.requireNonNull(basis);
            components=Objects.requireNonNull(copy(components,64,true));components.forEach(x->text(x,80,true));unique(components);
            properties=Objects.requireNonNull(copy(properties,3,true));unique(properties);Objects.requireNonNull(absolutePressure);Objects.requireNonNull(absoluteTemperature);
            text(evidenceReference,1000,false);}
    }
    public record Facts(Composition composition,List<Observation> properties,List<Method> methods,Source source,String description) {
        public Facts {properties=copy(properties,32,true);methods=copy(methods,16,true);text(description,4000,false);
            if(properties!=null)unique(properties.stream().map(Observation::observationId).toList());
            if(methods!=null)unique(methods.stream().map(Method::key).toList());
            var declared=new HashMap<MethodKey,Method>();if(methods!=null)methods.forEach(m->declared.put(m.key(),m));
            if(composition!=null && composition.method()!=null && declared.containsKey(composition.method())) {
                var m=declared.get(composition.method());
                if(m.basis()!=composition.basis() || !m.components().containsAll(composition.components().stream().map(Component::reference).toList()))throw invalid();
                synthetic(composition.source(),m);
            }
            if(properties!=null)for(var p:properties)if(p.method()!=null && declared.containsKey(p.method())) {
                var m=declared.get(p.method());if(!m.properties().contains(p.property()))throw invalid();m.absolutePressure().contains(p.absolutePressure());m.absoluteTemperature().contains(p.absoluteTemperature());synthetic(p.source(),m);
            }
            if(source!=null && source.origin()==Origin.SYNTHETIC) {
                if(composition!=null && composition.source()!=null && composition.source().origin()!=Origin.SYNTHETIC)throw invalid();
                if(properties!=null && properties.stream().anyMatch(p->p.source()!=null && p.source().origin()!=Origin.SYNTHETIC))throw invalid();
                if(methods!=null && methods.stream().anyMatch(m->m.origin()!=Origin.SYNTHETIC))throw invalid();
            }
        }
        public void validate(Classification c) {
            Objects.requireNonNull(c);if(c==Classification.LIQUID && properties!=null && properties.stream().anyMatch(p->p.property()==Property.COMPRESSIBILITY_FACTOR))throw invalid();
        }
        public List<String> diagnostics() {
            var d=new ArrayList<String>();if(composition==null)d.add("COMPOSITION_NOT_AUTHORED");
            if(properties==null || properties.isEmpty())d.add("PROPERTIES_NOT_AUTHORED");
            if(methods==null || methods.isEmpty())d.add("METHODS_NOT_AUTHORED");
            if(source==null)d.add("SOURCE_NOT_AUTHORED");else if(source.evidenceReference()==null)d.add("SOURCE_EVIDENCE_NOT_AUTHORED");
            var keys=methods==null?Set.<MethodKey>of():new HashSet<>(methods.stream().map(Method::key).toList());
            if(composition!=null){if(composition.source()==null || composition.source().evidenceReference()==null)d.add("COMPOSITION_EVIDENCE_NOT_AUTHORED");if(composition.method()==null || !keys.contains(composition.method()))d.add("COMPOSITION_METHOD_NOT_AUTHORED");}
            if(properties!=null)for(var p:properties){if(p.source()==null || p.source().evidenceReference()==null)d.add("PROPERTY_EVIDENCE_NOT_AUTHORED:"+p.observationId());if(p.method()==null || !keys.contains(p.method()))d.add("PROPERTY_METHOD_NOT_AUTHORED:"+p.observationId());}
            if(methods!=null)for(var m:methods)if(m.evidenceReference()==null)d.add("METHOD_EVIDENCE_NOT_AUTHORED:"+m.key().reference()+":"+m.key().revision());
            return List.copyOf(d);
        }
    }
    public SuppliedFluidPropertyRevision {
        Objects.requireNonNull(productDraftId);text(companyPartyId,80,true);text(localCode,80,true);text(catalogProductId,80,true);
        if(version<=0 || productMetadataVersion<=0)throw invalid();Objects.requireNonNull(classification);Objects.requireNonNull(facts).validate(classification);
    }
    private static void synthetic(Source s,Method m){if(s!=null && s.origin()==Origin.SYNTHETIC && m.origin()!=Origin.SYNTHETIC)throw invalid();}
    private static <T> List<T> copy(List<T> values,int max,boolean empty){if(values==null){if(empty)return null;throw invalid();}if(values.size()>max || (!empty && values.isEmpty()) || values.stream().anyMatch(Objects::isNull))throw invalid();return List.copyOf(values);}
    private static void unique(List<?> values){if(new HashSet<>(values).size()!=values.size())throw invalid();}
    public static void text(String s,int max,boolean required){ProductEngineeringDraft.text(s,max,required);}
    private static IllegalArgumentException invalid(){return new IllegalArgumentException("Invalid supplied fluid declaration");}
}
