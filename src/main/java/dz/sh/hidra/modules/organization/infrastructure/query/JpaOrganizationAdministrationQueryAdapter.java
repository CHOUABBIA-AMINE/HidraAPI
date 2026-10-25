/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOrganizationAdministrationQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.query
 *
 * @Description : JPA-backed organization hierarchy, employee directory, and assignment query adapter.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.query;

import dz.sh.hidra.modules.organization.application.port.in.OrganizationAdministrationQueryUseCase;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitJpaEntity;
import jakarta.persistence.EntityManager;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaOrganizationAdministrationQueryAdapter implements OrganizationAdministrationQueryUseCase {

    private final EntityManager entityManager;

    private final dz.sh.hidra.modules.organization.application.port.in.EmployeeAppointmentLifecycleUseCase appointments;

    public JpaOrganizationAdministrationQueryAdapter(EntityManager entityManager,dz.sh.hidra.modules.organization.application.port.in.EmployeeAppointmentLifecycleUseCase appointments) {
        this.appointments=Objects.requireNonNull(appointments);
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    @Override
    @Transactional(readOnly=true)
    public Page<OrganizationUnitView> units(String query, int page, int size) {
        List<OrganizationUnitView> all = entityManager
                .createQuery("select e from OrganizationUnitJpaEntity e order by e.code", OrganizationUnitJpaEntity.class)
                .getResultList().stream()
                .filter(entity -> matches(query, entity.code(), entity.nameAr(), entity.nameFr(), entity.nameEn()))
                .map(this::unitView)
                .toList();
        return page(all, page, size);
    }

    @Override
    @Transactional(readOnly=true)
    public OrganizationUnitView unit(String id) {
        OrganizationUnitJpaEntity entity = entityManager.find(OrganizationUnitJpaEntity.class, id);
        if (entity == null) {
            throw new IllegalArgumentException("Unknown organization unit: " + id);
        }
        return unitView(entity);
    }

    @Override
    @Transactional(readOnly=true)
    public List<OrganizationUnitView> children(String parentUnitId) {
        return entityManager
                .createQuery(
                        "select e from OrganizationUnitJpaEntity e where e.parentUnitId = :parent order by e.code",
                        OrganizationUnitJpaEntity.class
                )
                .setParameter("parent", parentUnitId)
                .getResultList().stream()
                .map(this::unitView)
                .toList();
    }

    @Override
    @Transactional(readOnly=true)
    public List<OrganizationNodeView> hierarchy() {
        List<OrganizationUnitView> units = entityManager
                .createQuery("select e from OrganizationUnitJpaEntity e order by e.code", OrganizationUnitJpaEntity.class)
                .getResultList().stream()
                .map(this::unitView)
                .toList();

        Map<String, OrganizationUnitView> byId = units.stream()
                .collect(Collectors.toMap(OrganizationUnitView::id, Function.identity()));

        return units.stream()
                .filter(unit -> unit.parentUnitId() == null || !byId.containsKey(unit.parentUnitId()))
                .map(unit -> node(unit, units))
                .toList();
    }

    @Override
    @Transactional(readOnly=true)
    public Page<EmployeeView> employees(String query, int page, int size) {
        List<EmployeeView> all = entityManager
                .createQuery("select e from EmployeeJpaEntity e order by e.employeeNumber", EmployeeJpaEntity.class)
                .getResultList().stream()
                .filter(entity -> matches(
                        query,
                        entity.employeeNumber(),
                        entity.firstNameAr(),
                        entity.lastNameAr(),
                        entity.firstNameLt(),
                        entity.lastNameLt(),
                        displayName(entity.firstNameAr(), entity.lastNameAr()),
                        displayName(entity.firstNameLt(), entity.lastNameLt()),
                        entity.emailAddress()
                ))
                .map(this::employeeView)
                .toList();
        return page(all, page, size);
    }

    @Override
    @Transactional(readOnly=true)
    public EmployeeView employee(String id) {
        EmployeeJpaEntity entity = entityManager.find(EmployeeJpaEntity.class, id);
        if (entity == null) {
            throw new IllegalArgumentException("Unknown employee: " + id);
        }
        return employeeView(entity);
    }


    @Override public Page<EmployeeAssignmentView> assignments(String employeeId,String organizationUnitId,String status,int page,int size) {
        var q=new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Current(null,employeeId,organizationUnitId,
                status==null?null:dz.sh.hidra.modules.organization.domain.value.AssignmentStatus.valueOf(status),page,size,false);
        var result=appointments.currentAssignments(q);
        return new Page<>(result.entries().stream().map(JpaOrganizationAdministrationQueryAdapter::currentView).toList(),
                result.page(),result.size(),result.totalElements(),result.totalPages(),result.hasNext());
    }
    @Override public List<EmployeeAssignmentView> employeeAssignments(String employeeId) {
        var q=new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Current(null,employeeId,null,null,0,100,true);
        return appointments.currentAssignments(q).entries().stream().map(JpaOrganizationAdministrationQueryAdapter::currentView).toList();
    }
    private static EmployeeAssignmentView currentView(dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.PinnedVersion v) {
        var a=v.snapshot().appointment().assignment();
        return new EmployeeAssignmentView(a.id(),a.employeeId(),a.organizationUnitId(),a.positionId(),a.assignmentType().name(),a.validFrom(),a.validTo(),a.status().name());
    }

    private OrganizationNodeView node(OrganizationUnitView parent, List<OrganizationUnitView> units) {
        List<OrganizationNodeView> children = units.stream()
                .filter(candidate -> parent.id().equals(candidate.parentUnitId()))
                .sorted(Comparator.comparing(OrganizationUnitView::code))
                .map(candidate -> node(candidate, units))
                .toList();
        return new OrganizationNodeView(parent, children);
    }

    private OrganizationUnitView unitView(OrganizationUnitJpaEntity entity) {
        return new OrganizationUnitView(
                entity.id(),
                entity.code(),
                entity.nameAr(),
                entity.nameFr(),
                entity.nameEn(),
                entity.unitTypeId(),
                entity.parentUnitId(),
                String.valueOf(entity.status()),
                entity.validFrom(),
                entity.validTo()
        );
    }

    private EmployeeView employeeView(EmployeeJpaEntity entity) {
        return new EmployeeView(
                entity.id(),
                entity.employeeNumber(),
                displayName(entity.firstNameAr(), entity.lastNameAr()),
                displayName(entity.firstNameLt(), entity.lastNameLt()),
                entity.emailAddress(),
                entity.mobileNumber(),
                String.valueOf(entity.employeeType()),
                String.valueOf(entity.status()),
                entity.identityUserReference(),
                entity.hiredAt(),
                entity.terminatedAt()
        );
    }


    private static String displayName(String firstName, String lastName) {
        String first = normalizeName(firstName);
        String last = normalizeName(lastName);
        if (first == null) {
            return last;
        }
        if (last == null) {
            return first;
        }
        return first + " " + last;
    }

    private static String normalizeName(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private static boolean matches(String query, Object... values) {
        if (blank(query)) {
            return true;
        }

        String normalized = query.trim().toLowerCase(Locale.ROOT);
        for (Object value : values) {
            if (value != null && value.toString().toLowerCase(Locale.ROOT).contains(normalized)) {
                return true;
            }
        }
        return false;
    }

    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }

    private static <T> Page<T> page(List<T> all, int requestedPage, int requestedSize) {
        int page = Math.max(0, requestedPage);
        int size = Math.min(200, Math.max(1, requestedSize));
        int from = Math.min(all.size(), page * size);
        int to = Math.min(all.size(), from + size);
        int totalPages = all.isEmpty() ? 0 : (all.size() + size - 1) / size;

        return new Page<>(
                List.copyOf(all.subList(from, to)),
                page,
                size,
                all.size(),
                totalPages,
                to < all.size()
        );
    }
}
