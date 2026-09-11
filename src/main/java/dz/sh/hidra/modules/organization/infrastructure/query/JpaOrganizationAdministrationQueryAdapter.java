/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOrganizationAdministrationQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
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
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeAssignmentJpaEntity;
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
@Transactional(readOnly = true)
public class JpaOrganizationAdministrationQueryAdapter implements OrganizationAdministrationQueryUseCase {

    private final EntityManager entityManager;

    public JpaOrganizationAdministrationQueryAdapter(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    @Override
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
    public OrganizationUnitView unit(String id) {
        OrganizationUnitJpaEntity entity = entityManager.find(OrganizationUnitJpaEntity.class, id);
        if (entity == null) {
            throw new IllegalArgumentException("Unknown organization unit: " + id);
        }
        return unitView(entity);
    }

    @Override
    public List<OrganizationUnitView> children(String parentUnitId) {
        return entityManager
                .createQuery("select e from OrganizationUnitJpaEntity e where e.parentUnitId = :parent order by e.code", OrganizationUnitJpaEntity.class)
                .setParameter("parent", parentUnitId)
                .getResultList().stream()
                .map(this::unitView)
                .toList();
    }

    @Override
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
    public Page<EmployeeView> employees(String query, int page, int size) {
        List<EmployeeView> all = entityManager
                .createQuery("select e from EmployeeJpaEntity e order by e.employeeNumber", EmployeeJpaEntity.class)
                .getResultList().stream()
                .filter(entity -> matches(query,
                        entity.employeeNumber(), entity.displayNameAr(), entity.displayNameLt(), entity.emailAddress()))
                .map(this::employeeView)
                .toList();
        return page(all, page, size);
    }

    @Override
    public EmployeeView employee(String id) {
        EmployeeJpaEntity entity = entityManager.find(EmployeeJpaEntity.class, id);
        if (entity == null) {
            throw new IllegalArgumentException("Unknown employee: " + id);
        }
        return employeeView(entity);
    }

    @Override
    public Page<EmployeeAssignmentView> assignments(
            String employeeId,
            String organizationUnitId,
            String status,
            int page,
            int size
    ) {
        List<EmployeeAssignmentView> all = entityManager
                .createQuery("select e from EmployeeAssignmentJpaEntity e order by e.validFrom desc", EmployeeAssignmentJpaEntity.class)
                .getResultList().stream()
                .filter(entity -> blank(employeeId) || employeeId.equals(entity.employeeId()))
                .filter(entity -> blank(organizationUnitId) || organizationUnitId.equals(entity.organizationUnitId()))
                .filter(entity -> blank(status) || status.equalsIgnoreCase(String.valueOf(entity.status())))
                .map(this::assignmentView)
                .toList();
        return page(all, page, size);
    }

    @Override
    public List<EmployeeAssignmentView> employeeAssignments(String employeeId) {
        return entityManager
                .createQuery("select e from EmployeeAssignmentJpaEntity e where e.employeeId = :employee order by e.validFrom desc", EmployeeAssignmentJpaEntity.class)
                .setParameter("employee", employeeId)
                .getResultList().stream()
                .map(this::assignmentView)
                .toList();
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
                entity.id(), entity.code(), entity.nameAr(), entity.nameFr(), entity.nameEn(), entity.unitTypeId(),
                entity.parentUnitId(), String.valueOf(entity.status()), entity.operationalScopeType(),
                entity.operationalScopeId(), entity.operationalScopeCode(), entity.operationalScopeName(),
                entity.validFrom(), entity.validTo()
        );
    }

    private EmployeeView employeeView(EmployeeJpaEntity entity) {
        return new EmployeeView(
                entity.id(), entity.employeeNumber(), entity.displayNameAr(), entity.displayNameLt(),
                entity.emailAddress(), entity.mobileNumber(), String.valueOf(entity.employeeType()),
                String.valueOf(entity.status()), entity.identityUserReference(), entity.hiredAt(), entity.terminatedAt()
        );
    }

    private EmployeeAssignmentView assignmentView(EmployeeAssignmentJpaEntity entity) {
        return new EmployeeAssignmentView(
                entity.id(), entity.employeeId(), entity.organizationUnitId(), entity.positionId(),
                String.valueOf(entity.assignmentType()), entity.operationalScopeType(), entity.operationalScopeId(),
                entity.operationalScopeCode(), entity.operationalScopeName(), entity.validFrom(), entity.validTo(),
                String.valueOf(entity.status())
        );
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
        return new Page<>(List.copyOf(all.subList(from, to)), page, size, all.size(), totalPages, to < all.size());
    }
}
