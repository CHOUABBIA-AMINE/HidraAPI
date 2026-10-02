/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraSseRegistry
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.realtime
 *
 * @Description : Tracks active Server-Sent Event clients and broadcasts realtime events.
 *
 */
package dz.sh.hidra.platform.realtime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * Tracks active Server-Sent Event clients and broadcasts realtime events.
 */
@Component
public class HidraSseRegistry {

    private static final long DEFAULT_TIMEOUT_MILLIS = 30L * 60L * 1_000L;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter register() {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT_MILLIS);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(error -> emitters.remove(emitter));
        send(emitter, "connected", java.util.Map.of("status", "connected"));
        return emitter;
    }

    public int activeClients() {
        return emitters.size();
    }

    public void broadcast(String eventName, Object payload) {
        for (SseEmitter emitter : emitters) {
            send(emitter, eventName, payload);
        }
    }

    private void send(SseEmitter emitter, String eventName, Object payload) {
        try {
            emitter.send(SseEmitter.event().name(eventName).data(payload));
        } catch (IOException | IllegalStateException exception) {
            emitters.remove(emitter);
            emitter.completeWithError(exception);
        }
    }
}
