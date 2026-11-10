/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingIngestionApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Verifies immutable replay identity, validation, and native scope admission ordering.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;


import dz.sh.hidra.modules.telemetry.application.command.IngestTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.port.out.*;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.exception.TelemetryReadingReplayConflictException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TelemetryReadingIngestionApplicationServiceTest {
    private final TelemetryReadingIngestionReferencePort.Scope scope =
        new TelemetryReadingIngestionReferencePort.Scope("S-1","D-1","P-1","UNIT-1",
                "FACILITY","F-1",null,"NUMERIC",false);
    private boolean authorized = true;
    private int appended;
    private final Map<String,TelemetryReading> stored = new HashMap<>();
    private final TelemetryReadingIngestionReferencePort refs = new TelemetryReadingIngestionReferencePort() {
        @Override public Scope current(String point,String source,Instant at) {
            if(!"P-1".equals(point)||source!=null&&!"S-1".equals(source))
                throw new IllegalArgumentException("Unknown");
            return scope;
        }
        @Override public void validateValue(Scope s,String quality,String unit,String shape) {
            if(!"Q-1".equals(quality)||!Objects.equals(unit,"UNIT-1")||!"NUMERIC".equals(shape))
                throw new InvalidTelemetryValueException("Invalid synthetic unit, quality or shape.");
        }
    };
    private final TelemetryReadingScopeAuthorizationPort rights = (s,operation)->{
        if(!authorized)throw new org.springframework.security.access.AccessDeniedException("Denied");
    };
    private final TelemetryReadingReplayPort replay = (source,unit,reading)->{
        var existing=stored.putIfAbsent(reading.sourceSequenceNumber(),reading);
        if(existing==null){appended++;return new TelemetryReadingReplayPort.Result(reading,unit,false);}
        if(!existing.rawPayloadHash().equals(reading.rawPayloadHash()))
            throw new TelemetryReadingReplayConflictException();
        return new TelemetryReadingReplayPort.Result(existing,unit,true);
    };
    private final TelemetryReadingIngestionApplicationService service =
            new TelemetryReadingIngestionApplicationService(refs,rights,replay,86400,120);

    private static IngestTelemetryReadingCommand numeric(String seq,String value) {
        return new IngestTelemetryReadingCommand("P-1","S-1",seq,new BigDecimal(value),
                null,null,"UNIT-1","Q-1",Instant.now().minusSeconds(3));
    }
    @Test void sameObservationReturnsOriginalCanonicalReceiptWithoutAppendingAgain() {
        var cmd=numeric("seq-1","25.00");
        var first=service.ingest(cmd);
        var retry=service.ingest(cmd);
        assertFalse(first.replayed());
        assertTrue(retry.replayed());
        assertEquals(first.reading().id(),retry.reading().id());
        assertEquals(first.reading().receivedAt(),retry.reading().receivedAt());
        assertEquals(1,appended);
    }
    @Test void differingValueUnderSameSequenceIsConflict() {
        service.ingest(numeric("seq-2","25"));
        assertThrows(TelemetryReadingReplayConflictException.class,
                ()->service.ingest(numeric("seq-2","26")));
        assertEquals(1,appended);
    }
    @Test void currentAuthorizationIsRecheckedEvenForExactReplay() {
        var cmd=numeric("seq-3","25");
        service.ingest(cmd);
        authorized=false;
        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                ()->service.ingest(cmd));
        assertEquals(1,appended);
    }
    @Test void invalidShapeUnitAndTimeNeverAppend() {
        var invalid=new IngestTelemetryReadingCommand("P-1","S-1","bad",
                BigDecimal.ONE,"extra",null,"UNIT-1","Q-1",Instant.now());
        assertThrows(InvalidTelemetryValueException.class,()->service.ingest(invalid));
        var badUnit=new IngestTelemetryReadingCommand("P-1","S-1","bad",
                BigDecimal.ONE,null,null,"OTHER","Q-1",Instant.now());
        assertThrows(InvalidTelemetryValueException.class,()->service.ingest(badUnit));
        var future=new IngestTelemetryReadingCommand("P-1","S-1","bad",
                BigDecimal.ONE,null,null,"UNIT-1","Q-1",Instant.now().plusSeconds(600));
        assertThrows(InvalidTelemetryValueException.class,()->service.ingest(future));
        assertEquals(0,appended);
    }
}
