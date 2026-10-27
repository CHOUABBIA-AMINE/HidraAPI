/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuppliedFluidPropertyRevisionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Preserves exact authored fluid facts independently of numerical qualification.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;
import static org.assertj.core.api.Assertions.*;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.*;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.SuppliedFluidPropertyRevisionCodec;
import dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.*;
import java.util.*;
import org.junit.jupiter.api.Test;
class SuppliedFluidPropertyRevisionTest {
    private final SuppliedFluidPropertyRevisionCodec codec=new SuppliedFluidPropertyRevisionCodec();
    private Decimal d(String value){return new Decimal(value);}
    private Composition mix(Basis b,String one,String two){return new Composition(b,List.of(new Component("A",d(one)),new Component("B",d(two))),null,null);}
    private Facts facts(Composition c){return new Facts(c,List.of(),List.of(),null,null);}
    private Range range(String unit){return new Range(d("1"),d("1000"),unit);}
    private Observation observation(Property p){return new Observation("value",null,p,d("1.200"),"unit",new Condition(d("100"),"P"),new Condition(d("300"),"T"),null,null);}
    @Test void exactDecimalsRetainLexemeAndScale(){assertThat(d("1.200").lexeme()).isEqualTo("1.200");assertThat(d("1.200").value().scale()).isEqualTo(3);for(String s:List.of("+1","-1","1e2"," 1","1 ","01",".1","1.","1.0000000000000000000","12345678901234567890123456789012345"))assertThatThrownBy(()->d(s)).isInstanceOf(IllegalArgumentException.class);}
    @Test void bothFractionBasesAreDeclaredForBothLabels(){for(var basis:Basis.values())for(var phase:Classification.values()){var f=facts(mix(basis,"0.2500","0.750"));f.validate(phase);assertThat(codec.decode(codec.encode(f))).isEqualTo(f);}}
    @Test void fractionsRequireExactPositiveUnitSum(){for(String[] pair:List.of(new String[]{"0","1"},new String[]{"0.3","0.6"},new String[]{"1.1","0.1"}))assertThatThrownBy(()->mix(Basis.MOLE_FRACTION,pair[0],pair[1])).isInstanceOf(IllegalArgumentException.class);assertThatThrownBy(()->new Composition(Basis.MASS_FRACTION,List.of(new Component("A",d("0.5")),new Component("A",d("0.5"))),null,null)).isInstanceOf(IllegalArgumentException.class);}
    @Test void liquidCompressibilityRejectsWithoutInventingGasUpperLimit(){var f=new Facts(null,List.of(observation(Property.COMPRESSIBILITY_FACTOR)),null,null,null);f.validate(Classification.GAS);assertThatThrownBy(()->f.validate(Classification.LIQUID)).isInstanceOf(IllegalArgumentException.class);var high=new Observation("Z",null,Property.COMPRESSIBILITY_FACTOR,d("3.00"),"dimensionless",new Condition(d("1"),"P"),new Condition(d("1"),"T"),null,null);new Facts(null,List.of(high),null,null,null).validate(Classification.GAS);}
    @Test void rangesRequirePositiveOrderedAbsoluteConditions(){assertThatThrownBy(()->new Range(d("2"),d("1"),"P")).isInstanceOf(IllegalArgumentException.class);assertThatThrownBy(()->new Condition(d("0"),"P")).isInstanceOf(IllegalArgumentException.class);assertThatThrownBy(()->range("P").contains(new Condition(d("10"),"other"))).isInstanceOf(IllegalArgumentException.class);}
    @Test void suppliedMethodCoverageAndApplicabilityCannotContradict(){var key=new MethodKey("method","1");var m=new Method(key,Origin.DECLARED_PARAMETER,Basis.MOLE_FRACTION,List.of("A"),List.of(Property.DENSITY),range("P"),range("T"),null);var c=new Composition(Basis.MASS_FRACTION,List.of(new Component("A",d("1"))),key,null);assertThatThrownBy(()->new Facts(c,null,List.of(m),null,null)).isInstanceOf(IllegalArgumentException.class);var p=new Observation("x",null,Property.DENSITY,d("1"),"density",new Condition(d("1001"),"P"),new Condition(d("300"),"T"),key,null);assertThatThrownBy(()->new Facts(null,List.of(p),List.of(m),null,null)).isInstanceOf(IllegalArgumentException.class);}
    @Test void missingEvidenceRemainsIncompleteAndMissingMethodDoesNotInventApproval(){var f=facts(mix(Basis.MOLE_FRACTION,"0.5","0.5"));assertThat(f.diagnostics()).contains("SOURCE_NOT_AUTHORED","COMPOSITION_EVIDENCE_NOT_AUTHORED","COMPOSITION_METHOD_NOT_AUTHORED");}
    @Test void syntheticCannotBeRelabeledDeclared(){var source=new Source(Origin.SYNTHETIC,"synthetic",null);var m=new Method(new MethodKey("method","1"),Origin.DECLARED_PARAMETER,Basis.MOLE_FRACTION,List.of(),List.of(),range("P"),range("T"),null);assertThatThrownBy(()->new Facts(null,null,List.of(m),source,null)).isInstanceOf(IllegalArgumentException.class);}
    @Test void nullEmptyUnicodeAndDecimalBytesRoundTripExactly(){var a=new Facts(null,null,null,null,"é🚀");var b=new Facts(null,List.of(),List.of(),null,"é🚀");assertThat(codec.decode(codec.encode(a))).isEqualTo(a);assertThat(codec.encode(a)).isNotEqualTo(codec.encode(b));var f=facts(mix(Basis.MASS_FRACTION,"0.50","0.500"));assertThat(codec.decode(codec.encode(f))).isEqualTo(f);assertThatThrownBy(()->new MethodKey(" bad","1")).isInstanceOf(IllegalArgumentException.class);assertThatThrownBy(()->new MethodKey("\uD800","1")).isInstanceOf(IllegalArgumentException.class);}
    @Test void codecRejectsTruncationUnknownVersionTrailingAndMalformedUtf8(){byte[] b=codec.encode(new Facts(null,null,null,null,"é"));for(int n=0;n<b.length;n++){byte[] shortBytes=Arrays.copyOf(b,n);assertThatThrownBy(()->codec.decode(shortBytes)).isInstanceOf(IllegalArgumentException.class);}byte[] v=b.clone();v[3]=2;assertThatThrownBy(()->codec.decode(v)).isInstanceOf(IllegalArgumentException.class);assertThatThrownBy(()->codec.decode(Arrays.copyOf(b,b.length+1))).isInstanceOf(IllegalArgumentException.class);byte[] u=b.clone();u[u.length-1]=(byte)255;assertThatThrownBy(()->codec.decode(u)).isInstanceOf(IllegalArgumentException.class);}
    @Test void collectionsAreBoundedUniqueAndDefensivelyCopied(){var p=observation(Property.DENSITY);assertThatThrownBy(()->new Facts(null,Collections.nCopies(33,p),null,null,null)).isInstanceOf(IllegalArgumentException.class);assertThatThrownBy(()->new Facts(null,List.of(p,p),null,null,null)).isInstanceOf(IllegalArgumentException.class);var list=new ArrayList<Observation>();list.add(p);var f=new Facts(null,list,null,null,null);list.clear();assertThat(f.properties()).hasSize(1);}
    @Test void requestReplayBindsExpectedVersionsAndExactDecimalBytes(){UUID p=UUID.randomUUID(),r=UUID.randomUUID();var a=facts(mix(Basis.MOLE_FRACTION,"0.5","0.5"));var b=facts(mix(Basis.MOLE_FRACTION,"0.50","0.50"));assertThat(codec.requestDigest(p,r,0,1,a)).isNotEqualTo(codec.requestDigest(p,r,0,1,b)).isNotEqualTo(codec.requestDigest(p,r,1,1,a)).isNotEqualTo(codec.requestDigest(p,r,0,2,a));assertThatThrownBy(()->new Command(Kind.WRITE,"company",p,r,-1L,1L,a)).isInstanceOf(IllegalArgumentException.class);}
}
