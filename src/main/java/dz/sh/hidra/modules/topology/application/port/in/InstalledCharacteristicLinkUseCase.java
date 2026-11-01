/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Validates exact draft-link save and protected historical read descriptors.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import java.util.List;
import java.util.Objects;

/**
 * Application descriptor, NOT authorization or a publishable endpoint.
 * Approval, source qualification and simulation admission are intentionally absent.
 */
public interface InstalledCharacteristicLinkUseCase {
    enum Operation { SAVE_DRAFT, READ_CURRENT, READ_REVISION, LIST_HISTORY }

    record Command(Operation operation, String companyPartyId, String installedUnitId,
                   String linkId, InstalledCharacteristicLink draft, String requestId,
                   Long expectedEpoch, Long revision, int limit, Long beforeRevision) {
        public Command {
            Objects.requireNonNull(operation, "Operation required");
            exact(companyPartyId); exact(installedUnitId); exact(linkId);
            switch (operation) {
                case SAVE_DRAFT -> {
                    exact(requestId);
                    if (draft == null || expectedEpoch == null || expectedEpoch < 0
                            || expectedEpoch == Long.MAX_VALUE
                            || draft.linkRevision() != expectedEpoch + 1
                            || !companyPartyId.equals(draft.companyPartyId())
                            || !installedUnitId.equals(draft.installedUnitId())
                            || !linkId.equals(draft.linkId())
                            || revision != null || limit != 0 || beforeRevision != null)
                        throw new IllegalArgumentException("Exact draft revision and expected epoch required");
                }
                case READ_REVISION -> {
                    if (revision == null || revision < 1
                            || draft != null || requestId != null || expectedEpoch != null
                            || limit != 0 || beforeRevision != null)
                        throw new IllegalArgumentException("Exact historical revision required");
                }
                case LIST_HISTORY -> {
                    if (limit < 1 || limit > 100
                            || beforeRevision != null && beforeRevision < 1
                            || draft != null || requestId != null
                            || expectedEpoch != null || revision != null)
                        throw new IllegalArgumentException("Bounded history descriptor required");
                }
                case READ_CURRENT -> {
                    if (draft != null || requestId != null || expectedEpoch != null
                            || revision != null || limit != 0 || beforeRevision != null)
                        throw new IllegalArgumentException("Current read cannot carry draft or cursor");
                }
            }
        }
        private static void exact(String value) {
            if (value == null || value.isBlank() || !value.equals(value.strip())
                    || value.codePointCount(0, value.length()) > 80
                    || value.codePoints().anyMatch(c -> Character.isISOControl(c)
                        || c >= 0xd800 && c <= 0xdfff))
                throw new IllegalArgumentException("Exact bounded identity required");
        }
        @Override public String toString() { return "InstalledCharacteristicCommand[redacted]"; }
    }

    /** Created only after the original protected transaction has physically committed. */
    record Result(InstalledCharacteristicLink link, List<InstalledCharacteristicLink> history,
                  boolean replayed, Long nextBeforeRevision) {
        public Result {
            history = List.copyOf(Objects.requireNonNull(history));
            if (nextBeforeRevision != null && nextBeforeRevision < 1)
                throw new IllegalArgumentException("Positive exclusive history cursor required");
        }
        @Override public String toString() { return "InstalledCharacteristicResult[redacted]"; }
    }

    Result execute(Command command);
}
