/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SurveyRouteProfileCodecTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Tests canonical survey bytes, malformed payloads and bounded allocation.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile;
import java.math.*;
import java.util.*;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class SurveyRouteProfileCodecTest {
    private final SurveyRouteProfileCodec codec=new SurveyRouteProfileCodec();

    private static SurveyRouteProfile profile(){return new SurveyRouteProfile("parallel-a",
        new SurveyRouteProfile.Crs("SYNTHETIC","crs",List.of(new SurveyRouteProfile.Axis("north","metre"),new SurveyRouteProfile.Axis("east","metre"))),
        null,"origin",SurveyRouteProfile.Direction.FROM_TO,"evidence",
        List.of(point("a","0.00"),point("b","10.000")),List.of(new SurveyRouteProfile.Section("s",new BigDecimal("0.0"),new BigDecimal("10.0"),"section")));}
    private static SurveyRouteProfile.Point point(String id,String chainage){return new SurveyRouteProfile.Point(id,new BigDecimal("1.000"),new BigDecimal("2.00"),new BigDecimal(chainage),null,new BigDecimal("0.01"),new BigDecimal("0.001"),null);}

    @Test void roundTripsAllDeclaredValuesAndScales(){
        var p=profile();byte[] bytes=codec.encode(p);assertEquals(p,codec.decode(bytes));
        assertEquals(new BigDecimal("1.000"),codec.decode(bytes).points().get(0).firstCoordinate());
        assertArrayEquals(bytes,codec.encode(codec.decode(bytes)));assertTrue(SurveyRouteProfileCodec.sha256(bytes).matches("[0-9a-f]{64}"));
        var elevated=new SurveyRouteProfile(p.branchId(),p.horizontalCrs(),new SurveyRouteProfile.VerticalReference("SYNTHETIC","v","foot"),p.chainageOriginReference(),SurveyRouteProfile.Direction.TO_FROM,p.evidenceReference(),
            List.of(new SurveyRouteProfile.Point("a",new BigDecimal("1E+100"),new BigDecimal("2"),BigDecimal.ZERO,new BigDecimal("-10.000"),BigDecimal.ONE,BigDecimal.ONE,new BigDecimal("0.01")),point("b","10")),List.of());
        assertEquals(elevated,codec.decode(codec.encode(elevated)));
    }
    @Test void preservesAxisPointAndSectionOrder(){
        var p=profile();assertEquals(p.horizontalCrs().axes(),codec.decode(codec.encode(p)).horizontalCrs().axes());
        assertEquals(p.points(),codec.decode(codec.encode(p)).points());assertEquals(p.sections(),codec.decode(codec.encode(p)).sections());
        var swapped=new SurveyRouteProfile(p.branchId(),new SurveyRouteProfile.Crs("SYNTHETIC","crs",List.of(p.horizontalCrs().axes().get(1),p.horizontalCrs().axes().get(0))),null,p.chainageOriginReference(),p.direction(),p.evidenceReference(),p.points(),p.sections());
        assertFalse(Arrays.equals(codec.encode(p),codec.encode(swapped)));
    }
    @Test void rejectsMalformedLengthsCountsUtf8AndMarkers(){
        byte[] bytes=codec.encode(profile());var bad=bytes.clone();ByteBuffer.wrap(bad).putInt(Integer.MAX_VALUE);assertThrows(IllegalArgumentException.class,()->codec.decode(bad));
        int at=4+SurveyRouteProfileCodec.FORMAT.length();
        var utf=bytes.clone();utf[at+4]=(byte)0xff;assertThrows(IllegalArgumentException.class,()->codec.decode(utf));
        // Walk the eight descriptor strings to the vertical-reference marker.
        var cursor=ByteBuffer.wrap(bytes);for(int n=0;n<8;n++){int size=cursor.getInt();cursor.position(cursor.position()+size);}
        int marker=cursor.position();var invalid=bytes.clone();invalid[marker]=2;assertThrows(IllegalArgumentException.class,()->codec.decode(invalid));
        cursor.position(marker+1);for(int n=0;n<3;n++){int size=cursor.getInt();cursor.position(cursor.position()+size);}
        var count=bytes.clone();ByteBuffer.wrap(count).putInt(cursor.position(),Integer.MAX_VALUE);assertThrows(IllegalArgumentException.class,()->codec.decode(count));
    }
    @Test void rejectsUnknownFormatTruncationAndTrailingBytes(){
        byte[] bytes=codec.encode(profile());var format=bytes.clone();format[4]='X';assertThrows(IllegalArgumentException.class,()->codec.decode(format));
        for(int n:new int[]{0,15,bytes.length-1,bytes.length/2})assertThrows(IllegalArgumentException.class,()->codec.decode(Arrays.copyOf(bytes,n)));
        assertThrows(IllegalArgumentException.class,()->codec.decode(Arrays.copyOf(bytes,bytes.length+1)));
    }
    @Test void rejectsNoncanonicalDecimalsAndPayloadOverflow(){
        byte[] bytes=codec.encode(profile());var cursor=ByteBuffer.wrap(bytes);
        for(int n=0;n<8;n++){int len=cursor.getInt();cursor.position(cursor.position()+len);}cursor.get();
        for(int n=0;n<3;n++){int len=cursor.getInt();cursor.position(cursor.position()+len);}cursor.getInt();
        int id=cursor.getInt();cursor.position(cursor.position()+id);cursor.getInt();int lengthAt=cursor.position();int len=cursor.getInt();int valueAt=cursor.position();
        byte[] noncanonical=new byte[bytes.length+1];System.arraycopy(bytes,0,noncanonical,0,valueAt);noncanonical[valueAt]=0;
        System.arraycopy(bytes,valueAt,noncanonical,valueAt+1,bytes.length-valueAt);ByteBuffer.wrap(noncanonical).putInt(lengthAt,len+1);
        assertThrows(IllegalArgumentException.class,()->codec.decode(noncanonical));
        assertThrows(IllegalArgumentException.class,()->codec.decode(new byte[SurveyRouteProfileCodec.MAX_BYTES+1]));
        BigDecimal large=new BigDecimal(BigInteger.ONE.shiftLeft(16000));var points=new ArrayList<SurveyRouteProfile.Point>();
        for(int n=0;n<10000;n++)points.add(new SurveyRouteProfile.Point("p"+n,large,large,BigDecimal.valueOf(n),null,BigDecimal.ONE,BigDecimal.ONE,null));
        var p=profile();var huge=new SurveyRouteProfile(p.branchId(),p.horizontalCrs(),null,"origin",p.direction(),"evidence",points,List.of());
        assertThrows(IllegalArgumentException.class,()->codec.encode(huge));
    }
}
