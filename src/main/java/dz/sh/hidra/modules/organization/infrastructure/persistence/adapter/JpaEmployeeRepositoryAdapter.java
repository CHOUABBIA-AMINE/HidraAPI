/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaEmployeeRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Employee.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for Employee.
 */
@Component
public class JpaEmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    private final EmployeeJpaRepository repository;
    private final org.springframework.jdbc.core.JdbcTemplate jdbc;
    private final jakarta.persistence.EntityManager entityManager;

    public JpaEmployeeRepositoryAdapter(EmployeeJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "EmployeeJpaRepository must not be null.");
        this.jdbc=null;this.entityManager=null;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public JpaEmployeeRepositoryAdapter(EmployeeJpaRepository repository,org.springframework.jdbc.core.JdbcTemplate jdbc,jakarta.persistence.EntityManager entityManager) {
        this.repository=Objects.requireNonNull(repository);this.jdbc=Objects.requireNonNull(jdbc);this.entityManager=Objects.requireNonNull(entityManager);
    }

    @Override public Employee lockForActivation(String id) {
        requireWrite();
        if(id==null||id.isBlank()||id.length()>80)throw new IllegalArgumentException("Exact employee ID required");
        var entity=entityManager.find(dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeJpaEntity.class,id,jakarta.persistence.LockModeType.PESSIMISTIC_WRITE);
        if(entity==null)throw new java.util.NoSuchElementException("Employee unavailable");
        entityManager.refresh(entity,jakarta.persistence.LockModeType.PESSIMISTIC_WRITE);
        return OrganizationPersistenceMapper.toDomain(entity);
    }
    @Override public java.time.Instant nextActivationTime(Employee current) {
        requireWrite();return jdbc.queryForObject("SELECT greatest(clock_timestamp(),?::timestamptz+interval '1 microsecond')",(r,n)->r.getTimestamp(1).toInstant(),current.updatedAt().toString());
    }
    @Override public Employee storeActivation(Employee before,Employee after,String actor,String reason) {
        requireWrite();
        if(actor==null||actor.isBlank())throw new IllegalArgumentException("Server actor required");
        int changed=jdbc.update("UPDATE public.hidra_org_employee SET status='ACTIVE',hired_at=?::timestamptz,updated_at=?::timestamptz WHERE id=? AND status='REGISTERED' AND updated_at=?::timestamptz",
                after.hiredAt().toString(),after.updatedAt().toString(),before.id(),before.updatedAt().toString());
        if(changed!=1)throw new IllegalStateException("Employee version conflict");
        jdbc.update("INSERT INTO public.hidra_org_employee_lifecycle_event(id,employee_id,actor_id,reason,previous_updated_at,updated_at,occurred_at) VALUES (?,?,?,?,?::timestamptz,?::timestamptz,clock_timestamp())",
                java.util.UUID.randomUUID().toString(),before.id(),actor,reason,before.updatedAt().toString(),after.updatedAt().toString());
        entityManager.refresh(entityManager.find(dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeJpaEntity.class,before.id()));
        return after;
    }
    @Override public java.util.List<dz.sh.hidra.modules.organization.application.port.in.OrganizationFoundationAdministrationUseCase.ActivationEvent> activationHistory(String employeeId) {
        return jdbc.query("SELECT id,employee_id,actor_id,reason,previous_updated_at,updated_at,occurred_at FROM public.hidra_org_employee_lifecycle_event WHERE employee_id=? ORDER BY occurred_at,id LIMIT 1000",
                (r,n)->new dz.sh.hidra.modules.organization.application.port.in.OrganizationFoundationAdministrationUseCase.ActivationEvent(r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getTimestamp(5).toInstant(),r.getTimestamp(6).toInstant(),r.getTimestamp(7).toInstant()),employeeId);
    }
    private void requireWrite() {
        if(jdbc==null||entityManager==null||!org.springframework.transaction.support.TransactionSynchronizationManager.isActualTransactionActive()
                ||org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly())throw new IllegalStateException("Writable employee transaction required");
    }

    @Override
    public Employee save(Employee model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<Employee> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}

