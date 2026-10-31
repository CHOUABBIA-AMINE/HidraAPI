package dz.sh.hidra.modules.simulation.infrastructure.security;

import dz.sh.hidra.modules.simulation.application.port.out.ManufacturerCharacteristicHistoryCursorPort.Position;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HmacManufacturerCharacteristicHistoryCursorAdapterTest {
    private static final Instant NOW=Instant.parse("2026-10-31T08:00:00Z");
    private static final Clock CLOCK=Clock.fixed(NOW,ZoneOffset.UTC);
    private static String key(int fill){
        byte[] bytes=new byte[32];
        Arrays.fill(bytes,(byte)fill);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
    private static HmacManufacturerCharacteristicHistoryCursorAdapter signer(Clock clock){
        return new HmacManufacturerCharacteristicHistoryCursorAdapter("k1",key(7),null,null,clock);
    }
    @Test void roundTripPreservesExactCompanySourceSizeAndPersistedMicrosecondPosition(){
        var source=new Position(Instant.parse("2026-10-30T11:12:13.123456Z"),"revision-1");
        var codec=signer(CLOCK);
        String encoded=codec.encode("company-A","source-A",17,source);
        assertEquals(source,codec.decode(encoded,"company-A","source-A",17));
        assertFalse(encoded.contains("company"));
        assertFalse(encoded.contains("source"));
    }
    @Test void alteredForeignCompanyOrSizeAndNonCanonicalTokensDeny(){
        var codec=signer(CLOCK);
        String token=codec.encode("company-A","source-A",20,
                new Position(Instant.parse("2026-10-30T12:00:00Z"),"r2"));
        assertThrows(IllegalArgumentException.class,()->codec.decode(token,"company-B","source-A",20));
        assertThrows(IllegalArgumentException.class,()->codec.decode(token,"company-A","source-B",20));
        assertThrows(IllegalArgumentException.class,()->codec.decode(token,"company-A","source-A",21));
        assertThrows(IllegalArgumentException.class,()->codec.decode(token+"=","company-A","source-A",20));
        String tampered=(token.charAt(0)=='A'?'B':'A')+token.substring(1);
        assertThrows(IllegalArgumentException.class,()->codec.decode(tampered,"company-A","source-A",20));
    }
    @Test void replayNeedsFreshAuthorizationAndExpiryIsStrict(){
        var codec=signer(CLOCK);
        var position=new Position(Instant.parse("2026-10-29T01:01:01Z"),"r3");
        String token=codec.encode("company","source",7,position);
        assertEquals(position,codec.decode(token,"company","source",7));
        assertEquals(position,codec.decode(token,"company","source",7));
        var expired=signer(Clock.offset(CLOCK,java.time.Duration.ofMinutes(15)));
        assertThrows(IllegalArgumentException.class,()->expired.decode(token,"company","source",7));
        var earlier=signer(Clock.offset(CLOCK,java.time.Duration.ofSeconds(-1)));
        assertThrows(IllegalArgumentException.class,()->earlier.decode(token,"company","source",7));
    }
    @Test void previousKeyIsVerificationOnlyAndBadOrMissingKeysFailClosed(){
        var old=new HmacManufacturerCharacteristicHistoryCursorAdapter("k1",key(7),null,null,CLOCK);
        var newSigner=new HmacManufacturerCharacteristicHistoryCursorAdapter(
                "k2",key(9),"k1",key(7),CLOCK);
        var p=new Position(Instant.parse("2026-10-29T12:00:00Z"),"revision");
        String oldToken=old.encode("company","source",6,p);
        String newToken=newSigner.encode("company","source",6,p);
        assertEquals(p,newSigner.decode(oldToken,"company","source",6));
        assertThrows(IllegalArgumentException.class,()->old.decode(newToken,"company","source",6));
        var disabled=new HmacManufacturerCharacteristicHistoryCursorAdapter(
                "k1",null,null,null,CLOCK);
        assertThrows(IllegalArgumentException.class,()->disabled.encode("company","source",6,p));
        assertThrows(IllegalArgumentException.class,()->disabled.decode(oldToken,"company","source",6));
        assertThrows(IllegalArgumentException.class,()->new HmacManufacturerCharacteristicHistoryCursorAdapter(
                "k1","invalid",null,null,CLOCK));
        assertThrows(IllegalArgumentException.class,()->new HmacManufacturerCharacteristicHistoryCursorAdapter(
                "same",key(7),"same",key(9),CLOCK));
    }
    @Test void invalidPersistedPositionAndScopeAreRejected(){
        assertThrows(IllegalArgumentException.class,()->new Position(
                Instant.parse("2026-10-29T12:00:00.000000001Z"),"revision"));
        assertThrows(IllegalArgumentException.class,()->new Position(NOW,"\n"));
        assertThrows(IllegalArgumentException.class,()->signer(CLOCK).encode(" company","source",1,
                new Position(NOW,"r1")));
    }
}
