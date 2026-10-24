/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEmployeeAppointmentAdmissionCoordinator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-23
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Coordinates one-use descriptor closure and transaction-bound protected appointment owner admission.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Action;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request;
import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult;
import dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort;
import dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery;
import dz.sh.hidra.modules.party.application.contract.organization.OrganizationProtectedPartyEligibilityContract;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Non-wired admission coordinator: no Spring bean or protected DML route is
 * activated until genuine native/executor/managed-flush composition is accepted.
 * Constructor ports are genuine owner contracts; mock tests do not establish that acceptance.
 */
public final class ProtectedEmployeeAppointmentAdmissionCoordinator {
    private final OrganizationProtectedAppointmentAuthorizationContract identity;
    private final OrganizationProtectedPartyEligibilityContract party;
    private final ProtectedEmployeeAppointmentRepositoryPort storage;
    private final ProtectedTransactionExecutor executor;

    public ProtectedEmployeeAppointmentAdmissionCoordinator(
            OrganizationProtectedAppointmentAuthorizationContract identity,
            OrganizationProtectedPartyEligibilityContract party,
            ProtectedEmployeeAppointmentRepositoryPort storage,
            ProtectedTransactionExecutor executor) {
        this.identity = Objects.requireNonNull(identity);
        this.party = Objects.requireNonNull(party);
        this.storage = Objects.requireNonNull(storage);
        this.executor = Objects.requireNonNull(executor);
    }

    /** Capture native identity before opening the protected physical transaction. */
    public Prepared prepare() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) throw denied();
        return new Prepared(this, Objects.requireNonNull(identity.prepare()));
    }

    /** Only an exact complete descriptor closure can become a native request. */
    static Request requestFor(EmployeeAppointmentCommand command, EmployeeAppointmentQuery query,
                              ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview preview) {
        if ((command == null) == (query == null) || preview == null || preview.overflow()) throw denied();
        Action action;
        String company;
        String employee = preview.employeeId();
        String assignment = preview.assignmentId();
        List<CompanyUnit> pairs = preview.companyUnits();
        if (command != null) {
            company = command.companyPartyId();
            action = switch (command.kind()) {
                case CREATE -> Action.CREATE;
                case END -> Action.END;
                case TRANSFER -> Action.TRANSFER;
                case CORRECT -> Action.CORRECT;
            };
            if (employee == null || pairs.stream().anyMatch(p -> !company.equals(p.companyPartyId())))
                throw denied();
            if (command instanceof EmployeeAppointmentCommand.Create create) {
                if (assignment != null || !employee.equals(create.employeeId())
                        || !pairs.equals(List.of(new CompanyUnit(company, create.organizationUnitId()))))
                    throw denied();
            } else {
                String selected = command instanceof EmployeeAppointmentCommand.End end ? end.assignmentId()
                        : command instanceof EmployeeAppointmentCommand.Transfer transfer ? transfer.assignmentId()
                        : ((EmployeeAppointmentCommand.Correct) command).assignmentId();
                if (!selected.equals(assignment)) throw denied();
                if (command instanceof EmployeeAppointmentCommand.Transfer transfer
                        && !pairs.contains(new CompanyUnit(company, transfer.destinationOrganizationUnitId())))
                    throw denied();
                if (command instanceof EmployeeAppointmentCommand.Correct correct
                        && !pairs.contains(new CompanyUnit(company, correct.correctedOrganizationUnitId())))
                    throw denied();
            }
        } else {
            company = query.companyPartyId();
            action = query instanceof EmployeeAppointmentQuery.Policy ? Action.READ_POLICY
                    : query instanceof EmployeeAppointmentQuery.ExactVersion ? Action.READ_CURRENT
                    : Action.READ_HISTORY;
            if (pairs.stream().noneMatch(p -> company.equals(p.companyPartyId()))) throw denied();
            if (query instanceof EmployeeAppointmentQuery.Policy policy) {
                if (employee != null || assignment != null
                        || !pairs.equals(List.of(new CompanyUnit(company, policy.unitId())))) throw denied();
            } else if (query instanceof EmployeeAppointmentQuery.ExactVersion exact) {
                if (!exact.assignmentId().equals(assignment) || employee == null) throw denied();
            } else if (query instanceof EmployeeAppointmentQuery.History history) {
                switch (history.scope()) {
                    case ASSIGNMENT -> {
                        if (!history.assignmentId().equals(assignment) || employee == null) throw denied();
                    }
                    case EMPLOYEE -> {
                        if (!history.employeeId().equals(employee) || assignment != null) throw denied();
                    }
                    case POSITION -> {
                        if (employee != null || assignment != null
                                || !pairs.contains(new CompanyUnit(company, history.unitId()))) throw denied();
                    }
                }
            }
        }
        return new Request(action, pairs, employee);
    }

    /** Fail closed on absent transaction, foreign issuer, reused token or bad owner preview. */
    Request begin(Prepared prepared, EmployeeAppointmentCommand command) {
        return begin(prepared, Objects.requireNonNull(command), null);
    }

    Request begin(Prepared prepared, EmployeeAppointmentQuery query) {
        return begin(prepared, null, Objects.requireNonNull(query));
    }

    private Request begin(Prepared prepared, EmployeeAppointmentCommand command, EmployeeAppointmentQuery query) {
        try {
            if (!TransactionSynchronizationManager.isActualTransactionActive()
                    || !TransactionSynchronizationManager.isSynchronizationActive()
                    || TransactionSynchronizationManager.isCurrentTransactionReadOnly()) throw denied();
            if (prepared == null || prepared.owner != this
                    || prepared.thread != Thread.currentThread()
                    || !prepared.used.compareAndSet(false, true)) throw denied();
            executor.beginAdmission();
            var preview = command == null ? storage.preview(query) : storage.preview(command);
            if (preview == null) throw denied();
            preview.requireBounded();
            // Return value is an internal request descriptor, never an admission
            // or permission. No Identity/Party/Organization ticket is consumed
            // in this B3E slice. The subsequent protected coordinator stage must
            // acquire every owner fence BEFORE using it for any result.
            return requestFor(command, query, preview);
        } catch (RuntimeException failure) {
            executor.rejectAdmission();
            throw denied(failure);
        }
    }

    /**
     * Qualify a read without demanding current Party selectability for historical
     * pins. This does not expose a row, seal executor admission or issue DML authority.
     * The storage owner must authenticate its own ticket and the native ticket.
     */
    ReadAdmission acquireRead(Prepared prepared, EmployeeAppointmentQuery query) {
        ReadAdmission result = null;
        try {
            Objects.requireNonNull(query);
            Request request = begin(prepared, query);
            // begin has only performed the untrusted pre-lock preview. Identity
            // owns the first relation fences; Organization must acquire its own
            // fences before re-reading the complete current/historical closure.
            var nativeAdmission = Objects.requireNonNull(identity.acquire(prepared.nativeIdentity, request));
            var expected = new ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                    request.companyUnits(), request.employeeId(), assignmentSelector(query), false);
            var repositoryAdmission = Objects.requireNonNull(storage.acquire(nativeAdmission, List.of(), expected));
            var locked = Objects.requireNonNull(storage.preview(query));
            locked.requireBounded();
            if (!expected.equals(locked) || !request.equals(requestFor(null, query, locked))) throw denied();
            result = new ReadAdmission(this, nativeAdmission, repositoryAdmission, query);
            TransactionSynchronizationManager.registerSynchronization(result);
            var sample = Objects.requireNonNull(storage.contribute(repositoryAdmission));
            if (!request.equals(sample.request())
                    || sample.overallDecision()
                        != OrganizationProtectedAppointmentAuthorizationContract.Decision.PERMIT) throw denied();
            result.sample = sample;
            requireCurrentRead(result);
            return result;
        } catch (RuntimeException failure) {
            invalidate(result);
            executor.rejectAdmission();
            throw denied();
        }
    }

    /** Rechecks genuine issuing owners; the opaque handle alone is never authority. */
    void requireCurrentRead(ReadAdmission admission) {
        try {
            if (admission == null || admission.owner != this || admission.guardAttempted
                    || !admission.ready()) throw denied();
            identity.requireCurrent(admission.identityAdmission);
            storage.requireCurrent(admission.repositoryAdmission);
            // An owner callback may invalidate/suspend the transaction. Never let
            // a swallowed invalidation restore the handle on the callback return.
            if (!admission.ready()) throw denied();
        } catch (RuntimeException failure) {
            invalidate(admission);
            executor.rejectAdmission();
            throw denied();
        }
    }

    /** FLUSHING callback: materialize the exact typed read and freeze its complete owner graph. */
    void prepareFinalRead(ReadAdmission admission) {
        try {
            requireCurrentRead(admission);
            if (admission.preparationStarted) throw denied();
            admission.preparationStarted = true;
            if(admission.query instanceof EmployeeAppointmentQuery.Policy policy)
                Objects.requireNonNull(storage.readPolicy(admission.repositoryAdmission, policy));
            else if(admission.query instanceof EmployeeAppointmentQuery.ExactVersion exact)
                Objects.requireNonNull(storage.exactVersion(admission.repositoryAdmission,exact));
            else if(admission.query instanceof EmployeeAppointmentQuery.History history) {
                if(history.scope()==EmployeeAppointmentQuery.Scope.POSITION)
                    Objects.requireNonNull(storage.positionHistory(admission.repositoryAdmission,history));
                else Objects.requireNonNull(storage.privateHistory(admission.repositoryAdmission,history));
            } else throw denied();
            storage.prepareFinalRead(admission.repositoryAdmission);
            if (!admission.ready()) throw denied();
            admission.finalPrepared = true;
        } catch (RuntimeException failure) {
            if (admission != null) admission.invalidate();
            executor.rejectAdmission();
            throw denied();
        }
    }

    /** GUARD callback; all checks around the sole storage statement are SQL-free. */
    void finalizeRead(ReadAdmission admission) {
        try {
            if (admission == null || admission.owner != this || !admission.ready()
                    || !admission.finalPrepared || admission.guardAttempted) throw denied();
            admission.guardAttempted = true;
            storage.finalizeRead(admission.repositoryAdmission);
            if (!admission.ready()) throw denied();
            admission.guarded = true;
        } catch (RuntimeException failure) {
            if (admission != null) admission.invalidate();
            executor.rejectAdmission();
            throw denied();
        }
    }

    /** Complete native admission before ordinary read materialization; no permit escapes this owner. */
    void sealRead(ReadAdmission admission) {
        try {
            requireCurrentRead(admission);
            executor.sealAdmission();
        } catch(RuntimeException failure) {
            invalidate(admission);executor.rejectAdmission();throw denied();
        }
    }

    EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PrivateHistoryEvent> privateHistory(ReadAdmission admission) {
        requireCurrentRead(admission);
        if(!(admission.query instanceof EmployeeAppointmentQuery.History q)
                || q.scope()==EmployeeAppointmentQuery.Scope.POSITION)throw denied();
        return Objects.requireNonNull(storage.privateHistory(admission.repositoryAdmission,q));
    }

    EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PositionHistoryEvent> positionHistory(ReadAdmission admission) {
        requireCurrentRead(admission);
        if(!(admission.query instanceof EmployeeAppointmentQuery.History q)
                || q.scope()!=EmployeeAppointmentQuery.Scope.POSITION)throw denied();
        return Objects.requireNonNull(storage.positionHistory(admission.repositoryAdmission,q));
    }

    EmployeeAppointmentResult.PinnedVersion exactVersion(ReadAdmission admission) {
        requireCurrentRead(admission);
        if(!(admission.query instanceof EmployeeAppointmentQuery.ExactVersion q))throw denied();
        return Objects.requireNonNull(storage.exactVersion(admission.repositoryAdmission,q));
    }

    EmployeeAppointmentResult.PolicyView readPolicy(ReadAdmission admission) {
        requireCurrentRead(admission);
        if(!(admission.query instanceof EmployeeAppointmentQuery.Policy q))throw denied();
        return Objects.requireNonNull(storage.readPolicy(admission.repositoryAdmission,q));
    }

    /** The result remains inside executePublication until its observed physical commit. */
    EmployeeAppointmentResult.MutationReceipt apply(WriteAdmission admission) {
        try {
            if(admission==null || admission.owner!=this || admission.detachedResult!=null)throw denied();
            if(admission.referenceUse==ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY) {
                requireCurrentWrite(admission);
                executor.sealAdmission();
            } else {
                // persistProvisional repeats full owner validation before its permit.
                // Do not duplicate it without any intervening callback or work.
                persistProvisional(admission);
                prepareCommitProof(admission);
            }
            var result=Objects.requireNonNull(storage.mutate(admission.repositoryAdmission,admission.command));
            if(result.kind()!=admission.command.kind() || !result.requestId().equals(admission.command.requestId())
                    || result.historicalReplay()!=(admission.referenceUse==ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)
                    || !admission.ready())throw denied();
            admission.detachedResult=result;
            return result;
        }catch(RuntimeException failure){if(admission!=null)admission.invalidate();executor.rejectAdmission();throw denied(failure);}
    }

    /** Called after managed flush, including replay; no authority is cached from a previous request. */
    void prepareFinal(WriteAdmission admission) {
        try {
            if(admission==null || admission.owner!=this || admission.detachedResult==null)throw denied();
            if(admission.referenceUse==ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY) {
                if(!admission.detachedResult.equals(prepareFinalReplay(admission)))throw denied();
            } else {
                requireCurrentWrite(admission);
                if(!admission.detachedResult.equals(storage.mutate(admission.repositoryAdmission,admission.command)))throw denied();
                prepareFinalWrite(admission);
            }
        }catch(RuntimeException failure){if(admission!=null)admission.invalidate();executor.rejectAdmission();throw denied();}
    }

    void finalizeOperation(WriteAdmission admission) {
        if(admission!=null && admission.referenceUse==ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)
            finalizeReplay(admission);
        else finalizeWrite(admission);
    }

    private static String assignmentSelector(EmployeeAppointmentQuery query) {
        if (query instanceof EmployeeAppointmentQuery.ExactVersion exact) return exact.assignmentId();
        if (query instanceof EmployeeAppointmentQuery.History history) return history.assignmentId();
        return null;
    }

    /**
     * Qualifies owners for a command, never a mutation permit or completed write.
     * Party fences precede Organization; current selectability is consumed only
     * after the storage owner has classified the exact authenticated request.
     */
    WriteAdmission acquireWrite(Prepared prepared, EmployeeAppointmentCommand command) {
        var timings = new BoundaryTimings("acquireWrite");
        WriteAdmission result = null;
        try {
            timings.start("descriptor");
            Request request = begin(prepared, Objects.requireNonNull(command));
            timings.start("native-acquire");
            var nativeAdmission = Objects.requireNonNull(identity.acquire(prepared.nativeIdentity, request));
            result = new WriteAdmission(this, nativeAdmission, command, request);
            TransactionSynchronizationManager.registerSynchronization(result);
            // acquire takes Party relation fences, but does not require ACTIVE.
            timings.start("party-acquire");
            result.partyAdmission = Objects.requireNonNull(party.acquire(command.companyPartyId()));
            var expected = new ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                    request.companyUnits(), request.employeeId(), assignmentSelector(command), false);
            timings.start("storage-acquire");
            result.repositoryAdmission = Objects.requireNonNull(
                    storage.acquire(nativeAdmission, List.of(result.partyAdmission), expected));
            timings.start("locked-preview");
            var locked = Objects.requireNonNull(storage.preview(command));
            locked.requireBounded();
            if (!expected.equals(locked) || !request.equals(requestFor(command, null, locked))) throw denied();
            timings.start("native-contribution");
            var sample = Objects.requireNonNull(storage.contribute(result.repositoryAdmission));
            if (!request.equals(sample.request())
                    || sample.overallDecision()
                        != OrganizationProtectedAppointmentAuthorizationContract.Decision.PERMIT) throw denied();
            result.sample = sample;
            timings.start("reference-classification");
            result.referenceUse = Objects.requireNonNull(
                    storage.referenceUse(result.repositoryAdmission, command, sample));
            requireReferenceShape(result);
            if (result.referenceUse == ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT) {
                timings.start("party-contribution");
                var eligibility = Objects.requireNonNull(storage.contributeParty(result.repositoryAdmission));
                if (!command.companyPartyId().equals(eligibility.partyId())
                        || eligibility.backendPid() != sample.backendPid()
                        || eligibility.transactionId() != sample.transactionId()) throw denied();
                result.eligibility = eligibility;
            }
            timings.start("current-write");
            requireCurrentWrite(result);
            result.admissionTimings = timings.snapshot();
            return result;
        } catch (RuntimeException failure) {
            if (result != null) result.invalidate();
            executor.rejectAdmission();
            throw denied(failure, timings);
        }
    }

    /** Revalidate all issuing owners; an enum, scalar sample or opaque handle is never authority. */
    void requireCurrentWrite(WriteAdmission admission) {
        var timings = new BoundaryTimings("requireCurrentWrite");
        try {
            if (admission == null || admission.owner != this || !admission.ready()
                    || admission.guardAttempted) throw denied();
            timings.start("native-current");
            identity.requireCurrent(admission.identityAdmission);
            timings.start("party-current");
            if (admission.referenceUse == ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT)
                party.requireCurrent(admission.partyAdmission);
            timings.start("storage-current");
            storage.requireCurrent(admission.repositoryAdmission);
            if (admission.provisionalStarted) {
                if (!admission.provisionalClosed) throw denied();
                timings.start("verify-provisional");
                storage.verifyProvisional(admission.repositoryAdmission);
                if (!admission.ready()) throw denied();
                admission.currentWriteTimings = timings.snapshot();
                return;
            }
            timings.start("reference-classification");
            if (storage.referenceUse(admission.repositoryAdmission, admission.command, admission.sample)
                    != admission.referenceUse) throw denied();
            requireReferenceShape(admission);
            timings.start("prepare-mutation");
            storage.prepareMutation(admission.repositoryAdmission, admission.command);
            if (!admission.ready()) throw denied();
            admission.currentWriteTimings = timings.snapshot();
        } catch (RuntimeException failure) {
            // A foreign coordinator/thread must still poison the original transaction.
            if (admission != null) admission.invalidate();
            executor.rejectAdmission();
            throw denied(failure, timings);
        }
    }

    /** One private permit, one fixed block, no owner callback until it closes.
     * No result is returned, and commit stays vetoed until final receipt support exists. */
    void persistProvisional(WriteAdmission admission) {
        var timings = new BoundaryTimings("persistProvisional");
        try {
            timings.start("current-write");
            requireCurrentWrite(admission);
            if (admission.provisionalStarted
                    || admission.referenceUse == ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY) throw denied();
            timings.start("prepare-provisional");
            var statements = Objects.requireNonNull(
                    storage.prepareProvisional(admission.repositoryAdmission, admission.command));
            if (statements.size() != 6) throw denied();
            admission.provisionalStarted = true;
            timings.start("seal-admission");
            var permit = Objects.requireNonNull(executor.sealAdmission());
            timings.start("provisional-block");
            executor.beginProvisional(permit, statements);
            storage.persistProvisional(admission.repositoryAdmission);
            if (!admission.ready()) throw denied();
            executor.endProvisional(permit);
            admission.provisionalClosed = true;
            timings.start("post-write-current");
            requireCurrentWrite(admission);
        } catch (RuntimeException failure) {
            if (admission != null) admission.invalidate();
            executor.rejectAdmission();
            var rejection = denied(failure, timings);
            if (admission != null && admission.owner == this && admission.admissionTimings != null)
                rejection.addSuppressed(new IllegalStateException(admission.admissionTimings));
            if (admission != null && admission.owner == this && admission.currentWriteTimings != null)
                rejection.addSuppressed(new IllegalStateException(admission.currentWriteTimings));
            throw rejection;
        }
    }

    /** One storage-owned proof handoff after closing PROVISIONAL; never returns proof bytes.
     * Final database authentication/receipt and observed commit remain mandatory. */
    void prepareCommitProof(WriteAdmission admission) {
        try {
            requireCurrentWrite(admission);
            if (!admission.provisionalStarted || !admission.provisionalClosed || admission.proofRequested
                    || admission.referenceUse == ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY) throw denied();
            admission.proofRequested = true;
            storage.prepareCommitProof(admission.repositoryAdmission);
            requireCurrentWrite(admission);
        } catch (RuntimeException failure) {
            if (admission != null) admission.invalidate();
            executor.rejectAdmission();
            throw denied(failure);
        }
    }

    /** FLUSHING callback after managed work: no new intent, sample or proof issuance. */
    void prepareFinalWrite(WriteAdmission admission) {
        try {
            requireCurrentWrite(admission);
            if (!admission.proofRequested || !admission.provisionalClosed || admission.finalPrepared
                    || admission.referenceUse == ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY) throw denied();
            storage.prepareFinalWrite(admission.repositoryAdmission);
            if (!admission.ready()) throw denied();
            admission.finalPrepared = true;
        } catch (RuntimeException failure) {
            if (admission != null) admission.invalidate();
            executor.rejectAdmission();
            throw denied();
        }
    }

    /** GUARD callback: validation here and after storage returns is deliberately SQL-free. */
    void finalizeWrite(WriteAdmission admission) {
        try {
            if (admission == null || admission.owner != this || !admission.ready()
                    || !admission.finalPrepared || admission.guardAttempted) throw denied();
            admission.guardAttempted = true;
            storage.finalizeWrite(admission.repositoryAdmission);
            if (!admission.ready()) throw denied();
            admission.guarded = true;
        } catch (RuntimeException failure) {
            if (admission != null) admission.invalidate();
            executor.rejectAdmission();
            throw denied();
        }
    }

    /** FLUSHING: an identical retry needs no provisional permit or new proof. */
    dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.MutationReceipt prepareFinalReplay(WriteAdmission admission) {
        try {
            requireCurrentWrite(admission);
            if(admission.referenceUse!=ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY
                    || admission.provisionalStarted || admission.proofRequested || admission.replayPreparationStarted)throw denied();
            admission.replayPreparationStarted=true;
            var result=Objects.requireNonNull(storage.prepareFinalReplay(admission.repositoryAdmission));
            if(!result.historicalReplay() || result.kind()!=admission.command.kind()
                    || !result.requestId().equals(admission.command.requestId()) || !admission.ready())throw denied();
            admission.finalPrepared=true;return result;
        }catch(RuntimeException failure){if(admission!=null)admission.invalidate();executor.rejectAdmission();throw denied();}
    }

    void finalizeReplay(WriteAdmission admission) {
        try {
            if(admission==null || admission.owner!=this || !admission.ready()
                    || admission.referenceUse!=ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY
                    || !admission.replayPreparationStarted || !admission.finalPrepared || admission.guardAttempted)throw denied();
            admission.guardAttempted=true;storage.finalizeReplay(admission.repositoryAdmission);
            if(!admission.ready())throw denied();admission.guarded=true;
        }catch(RuntimeException failure){if(admission!=null)admission.invalidate();executor.rejectAdmission();throw denied();}
    }

    private static void requireReferenceShape(WriteAdmission admission) {
        var use = admission.referenceUse;
        if (use == null) throw denied();
        if (use == ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY) return;
        boolean needsCurrent = admission.command instanceof EmployeeAppointmentCommand.Create
                || admission.command instanceof EmployeeAppointmentCommand.Transfer
                || admission.command instanceof EmployeeAppointmentCommand.Correct
                    && admission.request.companyUnits().size() == 2;
        if ((use == ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT) != needsCurrent) throw denied();
    }

    private static String assignmentSelector(EmployeeAppointmentCommand command) {
        if (command instanceof EmployeeAppointmentCommand.End end) return end.assignmentId();
        if (command instanceof EmployeeAppointmentCommand.Transfer transfer) return transfer.assignmentId();
        if (command instanceof EmployeeAppointmentCommand.Correct correct) return correct.assignmentId();
        return null;
    }

    /**
     * Private SQL-free lifetime veto. No payload, owner credentials or executor
     * permit leaves this handle. Physical commit/final SQL guards remain required.
     */
    static final class WriteAdmission implements TransactionSynchronization {
        private final ProtectedEmployeeAppointmentAdmissionCoordinator owner;
        private final OrganizationProtectedAppointmentAuthorizationContract.Admission identityAdmission;
        private final EmployeeAppointmentCommand command;
        private final Request request;
        private final Thread thread = Thread.currentThread();
        private final List<ConnectionHolder> holders;
        private volatile boolean active = true;
        private OrganizationProtectedPartyEligibilityContract.Admission partyAdmission;
        private ProtectedEmployeeAppointmentRepositoryPort.Admission repositoryAdmission;
        private OrganizationProtectedAppointmentAuthorizationContract.Contribution sample;
        private OrganizationProtectedPartyEligibilityContract.Contribution eligibility;
        private ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse referenceUse;
        private boolean provisionalStarted, provisionalClosed, proofRequested;
        private boolean finalPrepared, guardAttempted, guarded, replayPreparationStarted;
        private EmployeeAppointmentResult.MutationReceipt detachedResult;
        private String admissionTimings;
        private String currentWriteTimings;

        private WriteAdmission(ProtectedEmployeeAppointmentAdmissionCoordinator owner,
                OrganizationProtectedAppointmentAuthorizationContract.Admission identityAdmission,
                EmployeeAppointmentCommand command, Request request) {
            this.owner = owner;
            this.identityAdmission = identityAdmission;
            this.command = command;
            this.request = request;
            holders = TransactionSynchronizationManager.getResourceMap().values().stream()
                    .filter(ConnectionHolder.class::isInstance).map(ConnectionHolder.class::cast).distinct().toList();
        }

        private boolean ready() {
            return active && thread == Thread.currentThread()
                    && sample != null && repositoryAdmission != null && partyAdmission != null && referenceUse != null
                    && (referenceUse != ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT
                        || eligibility != null)
                    && TransactionSynchronizationManager.isActualTransactionActive()
                    && TransactionSynchronizationManager.isSynchronizationActive()
                    && !TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                    && TransactionSynchronizationManager.getSynchronizations().contains(this)
                    && TransactionSynchronizationManager.getResourceMap().values().containsAll(holders)
                    && holders.stream().noneMatch(ConnectionHolder::isRollbackOnly);
        }

        private void invalidate() {
            active = false;
            holders.forEach(ConnectionHolder::setRollbackOnly);
        }
        @Override public void beforeCommit(boolean readOnly) {
            if (readOnly || !ready() || (provisionalStarted || replayPreparationStarted) && (!finalPrepared || !guarded)) {
                invalidate();
                throw denied();
            }
        }
        @Override public void suspend() { invalidate(); }
        @Override public void afterCompletion(int status) { active = false; }
        @Override public String toString() { return "WriteAdmission[opaque appointment owners]"; }
    }

    private void invalidate(ReadAdmission admission) {
        if (admission != null && admission.owner == this) admission.invalidate();
    }

    /**
     * Private transaction-lifetime gate. It deliberately exports neither native
     * scalars, storage tickets nor a protected payload. No mutation permit is issued.
     */
    static final class ReadAdmission implements TransactionSynchronization {
        private final ProtectedEmployeeAppointmentAdmissionCoordinator owner;
        private final OrganizationProtectedAppointmentAuthorizationContract.Admission identityAdmission;
        private final ProtectedEmployeeAppointmentRepositoryPort.Admission repositoryAdmission;
        private final EmployeeAppointmentQuery query;
        private boolean preparationStarted, finalPrepared, guardAttempted, guarded;
        private final Thread thread = Thread.currentThread();
        private final List<ConnectionHolder> holders;
        private volatile boolean active = true;
        private OrganizationProtectedAppointmentAuthorizationContract.Contribution sample;

        private ReadAdmission(ProtectedEmployeeAppointmentAdmissionCoordinator owner,
                OrganizationProtectedAppointmentAuthorizationContract.Admission identityAdmission,
                ProtectedEmployeeAppointmentRepositoryPort.Admission repositoryAdmission,
                EmployeeAppointmentQuery query) {
            this.owner = owner;
            this.identityAdmission = identityAdmission;
            this.repositoryAdmission = repositoryAdmission;
            this.query = query;
            this.holders = TransactionSynchronizationManager.getResourceMap().values().stream()
                    .filter(ConnectionHolder.class::isInstance).map(ConnectionHolder.class::cast).distinct().toList();
        }

        private boolean ready() {
            return active && sample != null && thread == Thread.currentThread()
                    && TransactionSynchronizationManager.isActualTransactionActive()
                    && TransactionSynchronizationManager.isSynchronizationActive()
                    && !TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                    && TransactionSynchronizationManager.getSynchronizations().contains(this)
                    && TransactionSynchronizationManager.getResourceMap().values().containsAll(holders)
                    && holders.stream().noneMatch(ConnectionHolder::isRollbackOnly);
        }

        private void invalidate() {
            active = false;
            // Poison the original holder even when rejection happens on a foreign
            // thread with no bound transaction. beforeCommit supplies a SQL-free veto.
            holders.forEach(ConnectionHolder::setRollbackOnly);
        }
        @Override public void beforeCommit(boolean readOnly) {
            if (readOnly || !ready() || preparationStarted && (!finalPrepared || !guarded)) {
                invalidate();
                throw denied();
            }
        }
        @Override public void suspend() { invalidate(); }
        @Override public void afterCompletion(int status) { active = false; }
        @Override public String toString() { return "ReadAdmission[opaque appointment owners]"; }
    }

    /** Keep internal provenance while retaining the fixed non-disclosing outward error. */
    private static AccessDeniedException denied(RuntimeException cause, BoundaryTimings timings) {
        var failure = denied(cause);
        failure.addSuppressed(new IllegalStateException(timings.snapshot()));
        return failure;
    }

    /** Diagnostic elapsed times only: never used as authority, a clock or a deadline. */
    private static final class BoundaryTimings {
        private final String boundary;
        private final long began = System.nanoTime();
        private final StringBuilder completed = new StringBuilder();
        private String stage = "entry";
        private long stageBegan = began;

        private BoundaryTimings(String boundary) { this.boundary = boundary; }
        private void start(String next) {
            long now = System.nanoTime();
            completed.append(stage).append('=').append(now - stageBegan).append(';');
            stage = next;
            stageBegan = now;
        }
        private String snapshot() {
            long now = System.nanoTime();
            return "Protected appointment boundary timings (ns): " + boundary + " ["
                    + completed + stage + "=" + (now - stageBegan) + ";total=" + (now - began) + "]";
        }
    }

    private static AccessDeniedException denied(RuntimeException cause) {
        var failure=denied();failure.initCause(cause);return failure;
    }

    private static AccessDeniedException denied() {
        return new AccessDeniedException("Protected appointment admission unavailable");
    }

    /** An opaque preparation is never a transferable native credential. */
    public static final class Prepared {
        private final ProtectedEmployeeAppointmentAdmissionCoordinator owner;
        private final OrganizationProtectedAppointmentAuthorizationContract.Prepared nativeIdentity;
        private final Thread thread = Thread.currentThread();
        private final AtomicBoolean used = new AtomicBoolean();
        private Prepared(ProtectedEmployeeAppointmentAdmissionCoordinator owner,
                         OrganizationProtectedAppointmentAuthorizationContract.Prepared nativeIdentity) {
            this.owner = owner;
            this.nativeIdentity = nativeIdentity;
        }
        @Override public String toString() { return "Prepared[opaque appointment admission]"; }
    }
}
