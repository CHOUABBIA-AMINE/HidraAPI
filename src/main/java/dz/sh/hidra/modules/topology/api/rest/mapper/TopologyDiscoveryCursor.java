package dz.sh.hidra.modules.topology.api.rest.mapper;
import java.nio.ByteBuffer;
import java.util.Base64;
/** Opaque bounded scan token, never permission or an inventory/denial count. */
public final class TopologyDiscoveryCursor {
    private TopologyDiscoveryCursor(){}
    public static String encode(Integer offset){return offset==null?null:Base64.getUrlEncoder().withoutPadding().encodeToString(ByteBuffer.allocate(5).put((byte)1).putInt(offset).array());}
    public static int decode(String cursor){
        if(cursor==null)return 0;
        try{byte[] bytes=Base64.getUrlDecoder().decode(cursor);if(bytes.length!=5 || bytes[0]!=1)throw new IllegalArgumentException();int offset=ByteBuffer.wrap(bytes,1,4).getInt();if(offset<0 || offset>100000 || !cursor.equals(encode(offset)))throw new IllegalArgumentException();return offset;}
        catch(RuntimeException e){throw new IllegalArgumentException("Invalid discovery scan cursor");}
    }
}
