package dz.sh.hidra.modules.simulation.application.port.out;

import java.time.Instant;
import java.util.Objects;

/** A navigation hint, never a manufacturer/Identity/Party permission. */
public interface ManufacturerCharacteristicHistoryCursorPort {
    record Position(Instant recordedAt, String revisionId) {
        public Position {
            Objects.requireNonNull(recordedAt, "Persisted ordering timestamp required");
            if (recordedAt.getNano() % 1_000 != 0
                    || revisionId == null || revisionId.isBlank()
                    || !revisionId.equals(revisionId.strip())
                    || revisionId.codePointCount(0, revisionId.length()) > 80
                    || revisionId.codePoints().anyMatch(Character::isISOControl))
                throw new IllegalArgumentException("Invalid persisted history position");
        }
    }
    boolean available();
    Position decode(String token, String companyPartyId, String sourceId, int pageSize);
    String encode(String companyPartyId, String sourceId, int pageSize, Position position);
}
