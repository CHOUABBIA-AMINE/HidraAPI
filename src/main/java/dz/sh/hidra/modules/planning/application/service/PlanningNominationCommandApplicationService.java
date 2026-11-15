/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningNominationCommandApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Coordinates scoped nomination authoring and independent disposition.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.port.in.PlanningNominationCommandUseCase;
import dz.sh.hidra.modules.planning.application.port.out.NominationRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanningNominationGovernancePort;
import dz.sh.hidra.modules.planning.application.port.out.PlanningNominationGovernancePort.Scope;
import dz.sh.hidra.modules.planning.application.port.out.PlanningPeriodRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanningQueryPort;
import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.exception.PlanningRevisionConflictException;
import dz.sh.hidra.modules.planning.domain.model.Nomination;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;
import dz.sh.hidra.modules.planning.domain.service.NominationLifecyclePolicy;
import dz.sh.hidra.modules.planning.domain.value.NominationStatus;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanningNominationCommandApplicationService implements PlanningNominationCommandUseCase {
    private static final String FACILITY = "FACILITY";

    private final OperationalPlanRepositoryPort plans;
    private final PlanRevisionRepositoryPort revisions;
    private final PlanningPeriodRepositoryPort periods;
    private final NominationRepositoryPort nominations;
    private final PlanningNominationGovernancePort governance;
    private final PlanningQueryPort queries;
    private final PlanningAccessService access;

    public PlanningNominationCommandApplicationService(OperationalPlanRepositoryPort plans,
            PlanRevisionRepositoryPort revisions, PlanningPeriodRepositoryPort periods,
            NominationRepositoryPort nominations, PlanningNominationGovernancePort governance,
            PlanningQueryPort queries, PlanningAccessService access) {
        this.plans = plans;
        this.revisions = revisions;
        this.periods = periods;
        this.nominations = nominations;
        this.governance = governance;
        this.queries = queries;
        this.access = access;
    }

    @Override
    @Transactional
    public Receipt create(String revisionId, Create command) {
        Parent parent = lockParent(revisionId);
        String actor = access.nominationWriter(parent.plan(), command.destinationFacilityId(), false);
        PlanningPeriod period = requireCurrent(parent);
        String code = command.code().strip().toUpperCase(Locale.ROOT);
        if (!code.matches("[A-Z0-9][A-Z0-9._-]{0,79}")) {
            throw new InvalidPlanningValueException("Nomination code must be 1 to 80 letters, digits or ._- characters.");
        }
        String fingerprint = fingerprint("CREATE", actor, revisionId, command.expectedRevisionUpdatedAt(), code,
                command.nominationTypeId(), command.productTypeId(), decimal(command.quantity()),
                command.quantityUnitId(), decimal(command.rate()), command.rateUnitId(), command.destinationFacilityId(),
                command.periodStart(), command.periodEnd());
        Optional<Receipt> replay = replay(revisionId, command.commandId(), fingerprint);
        if (replay.isPresent()) {
            return replay.get();
        }
        if (!Objects.equals(command.expectedRevisionUpdatedAt(), parent.revision().updatedAt())) {
            throw conflict("Planning revision changed. Refresh it before creating a nomination.");
        }
        if (governance.codeExists(revisionId, code)) {
            throw conflict("Nomination code already exists in this revision.");
        }
        NominationLifecyclePolicy.requireInterval(command.periodStart(), command.periodEnd(),
                period.periodStart(), period.periodEnd());
        if ((command.rate() == null) != (command.rateUnitId() == null)
                || (command.rate() != null && command.rate().signum() <= 0)) {
            throw new InvalidPlanningValueException("A positive rate and its unit must be supplied together.");
        }
        Instant now = recordedAt();
        String id = UUID.randomUUID().toString();
        String sourceFacilityId = parent.plan().topologyScopeId();
        Nomination nomination = new Nomination(id, revisionId, null, code, command.nominationTypeId(),
                command.productTypeId(), command.quantity(), command.quantityUnitId(), command.rate(),
                command.rateUnitId(), FACILITY, sourceFacilityId, null, FACILITY, command.destinationFacilityId(),
                null, period.companyPartyId(), null, null, null, null, NominationStatus.DRAFT,
                command.periodStart(), command.periodEnd(), now, now);
        nominations.save(nomination);
        governance.register(new Scope(id, revisionId, period.companyPartyId(), sourceFacilityId,
                command.destinationFacilityId(), code, actor));
        Event event = new Event(UUID.randomUUID(), command.commandId(), id, revisionId, 0, actor,
                null, NominationStatus.DRAFT, null, now);
        governance.append(event, fingerprint);
        return new Receipt(event, false);
    }

    @Override
    @Transactional
    public Receipt dispose(String nominationId, Disposition command) {
        String revisionId = governance.revisionId(nominationId).orElseThrow(PlanningAccessService::unavailable);
        Parent parent = lockParent(revisionId);
        Scope scope = governance.lockScope(nominationId).orElseThrow(PlanningAccessService::unavailable);
        String actor = access.nominationWriter(parent.plan(), scope.destinationFacilityId(), true);
        PlanningPeriod period = requireCurrent(parent);
        Nomination nomination = nominations.findById(nominationId).orElseThrow(PlanningAccessService::unavailable);
        if (!scope.revisionId().equals(nomination.revisionId())
                || !scope.companyId().equals(period.companyPartyId())
                || !scope.sourceFacilityId().equals(parent.plan().topologyScopeId())
                || !FACILITY.equals(nomination.sourceAssetType())
                || !FACILITY.equals(nomination.destinationAssetType())
                || !scope.sourceFacilityId().equals(nomination.sourceAssetId())
                || !scope.destinationFacilityId().equals(nomination.destinationAssetId())) {
            throw PlanningAccessService.unavailable();
        }
        String reason = command.reason().strip();
        String fingerprint = fingerprint("DISPOSITION", actor, nominationId, command.expectedVersion(),
                command.decision(), reason);
        Optional<Receipt> replay = replay(scope.revisionId(), command.commandId(), fingerprint);
        if (replay.isPresent()) {
            return replay.get();
        }
        Event latest = governance.history(nominationId).getLast();
        if (command.expectedVersion() != latest.version()) {
            throw conflict("Nomination changed. Refresh the current history before retrying.");
        }
        Instant now = recordedAt();
        if (!now.isAfter(latest.recordedAt())) {
            now = latest.recordedAt().plus(1, ChronoUnit.MICROS);
        }
        Nomination updated = NominationLifecyclePolicy.dispose(nomination, scope.authorId(), actor,
                command.decision(), now);
        nominations.save(updated);
        Event event = new Event(UUID.randomUUID(), command.commandId(), nomination.id(), scope.revisionId(),
                latest.version() + 1, actor, nomination.status(), updated.status(), reason, now);
        governance.append(event, fingerprint);
        return new Receipt(event, false);
    }

    @Override
    @Transactional
    public List<Event> history(String nominationId) {
        queries.findNominationById(access.readableAssets(), nominationId)
                .orElseThrow(PlanningAccessService::unavailable);
        List<Event> events = governance.history(nominationId);
        if (events.isEmpty()) {
            throw PlanningAccessService.unavailable();
        }
        return events;
    }

    private Parent lockParent(String revisionId) {
        String planId = revisions.planIdForRevision(revisionId).orElseThrow(PlanningAccessService::unavailable);
        OperationalPlan plan = plans.findByIdForUpdate(planId).orElseThrow(PlanningAccessService::unavailable);
        PlanRevision revision = revisions.findByIdForUpdate(revisionId)
                .filter(row -> row.planId().equals(plan.id())).orElseThrow(PlanningAccessService::unavailable);
        return new Parent(plan, revision);
    }

    private PlanningPeriod requireCurrent(Parent parent) {
        PlanningPeriod period = periods.findById(parent.plan().periodId())
                .orElseThrow(PlanningAccessService::unavailable);
        if (!parent.revision().id().equals(parent.plan().approvedRevisionId())
                || parent.revision().status() != PlanRevisionStatus.APPROVED
                || !period.allowsNewPlanRevisions()) {
            throw conflict("Nomination changes require the current approved revision in an open planning period.");
        }
        return period;
    }

    private Optional<Receipt> replay(String revisionId, UUID commandId, String fingerprint) {
        return governance.findCommand(revisionId, commandId).map(stored -> {
            if (!stored.fingerprint().equals(fingerprint)) {
                throw conflict("Command ID is already used by a different nomination command.");
            }
            return new Receipt(stored.event(), true);
        });
    }

    private static Instant recordedAt() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    private static String decimal(BigDecimal value) {
        return value == null ? null : value.stripTrailingZeros().toPlainString();
    }

    private static String fingerprint(Object... values) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (Object value : values) {
                if (value == null) {
                    digest.update((byte) 0);
                } else {
                    digest.update((byte) 1);
                    byte[] bytes = value.toString().getBytes(StandardCharsets.UTF_8);
                    digest.update(java.nio.ByteBuffer.allocate(Integer.BYTES).putInt(bytes.length).array());
                    digest.update(bytes);
                }
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 unavailable", exception);
        }
    }

    private static PlanningRevisionConflictException conflict(String detail) {
        return new PlanningRevisionConflictException(detail);
    }

    private record Parent(OperationalPlan plan, PlanRevision revision) { }
}
