/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.mapper
 *
 * @Description : Maps organization REST contracts to application commands and responses.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.mapper;

import java.util.Locale;
import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.organization.api.rest.request.AssignEmployeeToUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.CreateEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.request.CreateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.CreatePositionRequest;
import dz.sh.hidra.modules.organization.api.rest.request.SetEmployeeReportingLineRequest;
import dz.sh.hidra.modules.organization.api.rest.request.UpdateEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.request.UpdateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeAssignmentResponse;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeResponse;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitResponse;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitTypeResponse;
import dz.sh.hidra.modules.organization.api.rest.response.PositionResponse;
import dz.sh.hidra.modules.organization.api.rest.response.ReportingLineResponse;
import dz.sh.hidra.modules.organization.application.command.AssignEmployeeToUnitCommand;
import dz.sh.hidra.modules.organization.application.command.CreateEmployeeCommand;
import dz.sh.hidra.modules.organization.application.command.CreateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.command.CreatePositionCommand;
import dz.sh.hidra.modules.organization.application.command.SetEmployeeReportingLineCommand;
import dz.sh.hidra.modules.organization.application.command.UpdateEmployeeCommand;
import dz.sh.hidra.modules.organization.application.command.UpdateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAssignmentDto;
import dz.sh.hidra.modules.organization.application.dto.EmployeeDto;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitDto;
import dz.sh.hidra.modules.organization.application.dto.PositionDto;
import dz.sh.hidra.modules.organization.application.dto.ReportingLineDto;
import dz.sh.hidra.modules.organization.application.query.GetEmployeeByIdQuery;
import dz.sh.hidra.modules.organization.application.query.GetOrganizationUnitByIdQuery;
import dz.sh.hidra.modules.organization.application.query.ListEmployeesQuery;
import dz.sh.hidra.modules.organization.application.query.ListOrganizationUnitsQuery;
import dz.sh.hidra.modules.organization.application.query.ListPositionsQuery;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.EmploymentStatus;
import dz.sh.hidra.modules.organization.domain.value.IdentityUserReference;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitName;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitTypeReference;
import dz.sh.hidra.modules.organization.domain.value.PositionCode;
import dz.sh.hidra.modules.organization.domain.value.PositionTitle;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;

/**
 * Maps organization REST request/response contracts to application commands, queries, and DTOs.
 */
public final class OrganizationRestMapper {

    public CreateEmployeeCommand toCommand(CreateEmployeeRequest request) {
        Objects.requireNonNull(request, "Create employee request must not be null.");
        return new CreateEmployeeCommand(
                EmployeeNumber.of(request.employeeNumber()),
                EmployeeFullName.of(request.fullName()),
                optionalEmail(request.email()),
                optionalIdentityUserReference(request.identityUserReference()));
    }

    public UpdateEmployeeCommand toCommand(String employeeId, UpdateEmployeeRequest request) {
        Objects.requireNonNull(request, "Update employee request must not be null.");
        return new UpdateEmployeeCommand(
                EmployeeId.of(employeeId),
                EmployeeFullName.of(request.fullName()),
                optionalEmail(request.email()));
    }

    public CreateOrganizationUnitCommand toCommand(CreateOrganizationUnitRequest request) {
        Objects.requireNonNull(request, "Create organization unit request must not be null.");
        return new CreateOrganizationUnitCommand(
                OrganizationUnitCode.of(request.code()),
                OrganizationUnitName.of(request.name()),
                OrganizationUnitTypeReference.ofCode(request.typeCode()),
                optionalOrganizationUnitId(request.parentId()),
                optionalOperationalScopeType(request.operationalScopeType()),
                request.operationalScopeId(),
                request.operationalScopeCode(),
                request.operationalScopeName());
    }

    public UpdateOrganizationUnitCommand toCommand(String organizationUnitId, UpdateOrganizationUnitRequest request) {
        Objects.requireNonNull(request, "Update organization unit request must not be null.");
        return new UpdateOrganizationUnitCommand(
                OrganizationUnitId.of(organizationUnitId),
                OrganizationUnitName.of(request.name()),
                optionalOrganizationUnitId(request.parentId()),
                optionalOperationalScopeType(request.operationalScopeType()),
                request.operationalScopeId(),
                request.operationalScopeCode(),
                request.operationalScopeName());
    }

    public CreatePositionCommand toCommand(CreatePositionRequest request) {
        Objects.requireNonNull(request, "Create position request must not be null.");
        return new CreatePositionCommand(
                PositionCode.of(request.code()),
                PositionTitle.of(request.title()),
                request.description());
    }

    public AssignEmployeeToUnitCommand toCommand(String employeeId, AssignEmployeeToUnitRequest request) {
        Objects.requireNonNull(request, "Assign employee request must not be null.");
        return new AssignEmployeeToUnitCommand(
                EmployeeId.of(employeeId),
                OrganizationUnitId.of(request.organizationUnitId()),
                dz.sh.hidra.modules.organization.domain.value.PositionId.of(request.positionId()),
                optionalOperationalScopeType(request.operationalScopeType()),
                request.operationalScopeId(),
                request.operationalScopeCode(),
                request.operationalScopeName(),
                request.effectiveFrom());
    }

    public SetEmployeeReportingLineCommand toCommand(String employeeId, SetEmployeeReportingLineRequest request) {
        Objects.requireNonNull(request, "Set employee reporting line request must not be null.");
        return new SetEmployeeReportingLineCommand(
                EmployeeId.of(employeeId),
                EmployeeId.of(request.managerEmployeeId()),
                ReportingLineType.valueOf(requiredEnum(request.reportingLineType())),
                request.primaryLine(),
                request.effectiveFrom(),
                request.description());
    }

    public GetEmployeeByIdQuery toGetEmployeeByIdQuery(String employeeId) {
        return new GetEmployeeByIdQuery(EmployeeId.of(employeeId));
    }

    public GetOrganizationUnitByIdQuery toGetOrganizationUnitByIdQuery(String organizationUnitId) {
        return new GetOrganizationUnitByIdQuery(OrganizationUnitId.of(organizationUnitId));
    }

    public ListEmployeesQuery toListEmployeesQuery(
            String searchText,
            String status,
            String organizationUnitId,
            int page,
            int size) {

        return new ListEmployeesQuery(
                searchText,
                optionalEmploymentStatus(status),
                optionalOrganizationUnitId(organizationUnitId),
                PageRequest.of(page, size));
    }

    public ListOrganizationUnitsQuery toListOrganizationUnitsQuery(
            String searchText,
            String typeCode,
            String status,
            String parentId,
            int page,
            int size) {

        return new ListOrganizationUnitsQuery(
                searchText,
                optionalOrganizationUnitType(typeCode),
                optionalOrganizationUnitStatus(status),
                optionalOrganizationUnitId(parentId),
                PageRequest.of(page, size));
    }

    public ListPositionsQuery toListPositionsQuery(String searchText, boolean activeOnly, int page, int size) {
        return new ListPositionsQuery(searchText, activeOnly, PageRequest.of(page, size));
    }

    public EmployeeResponse toResponse(EmployeeDto dto) {
        Objects.requireNonNull(dto, "Employee DTO must not be null.");
        return new EmployeeResponse(
                dto.employeeId(),
                dto.employeeNumber(),
                dto.fullName(),
                dto.email(),
                dto.status(),
                dto.identityUserReference(),
                dto.assignments().stream().map(this::toResponse).toList(),
                dto.reportingLines().stream().map(this::toResponse).toList(),
                dto.createdAt(),
                dto.activatedAt(),
                dto.suspendedAt(),
                dto.disabledAt(),
                dto.updatedAt());
    }

    public EmployeeAssignmentResponse toResponse(EmployeeAssignmentDto dto) {
        Objects.requireNonNull(dto, "Employee assignment DTO must not be null.");
        return new EmployeeAssignmentResponse(
                dto.assignmentId(),
                dto.employeeId(),
                dto.organizationUnitId(),
                dto.positionId(),
                dto.operationalScopeType(),
                dto.operationalScopeId(),
                dto.operationalScopeCode(),
                dto.operationalScopeName(),
                dto.effectiveFrom(),
                dto.effectiveTo());
    }

    public ReportingLineResponse toResponse(ReportingLineDto dto) {
        Objects.requireNonNull(dto, "Reporting line DTO must not be null.");
        return new ReportingLineResponse(
                dto.reportingLineId(),
                dto.employeeId(),
                dto.managerEmployeeId(),
                dto.reportingLineType(),
                dto.primaryLine(),
                dto.effectiveFrom(),
                dto.effectiveTo(),
                dto.description());
    }

    public OrganizationUnitResponse toResponse(OrganizationUnitDto dto) {
        return toResponse(dto, null);
    }

    public OrganizationUnitResponse toResponse(OrganizationUnitDto dto, String acceptLanguage) {
        Objects.requireNonNull(dto, "Organization unit DTO must not be null.");
        return new OrganizationUnitResponse(
                dto.organizationUnitId(),
                dto.code(),
                dto.name(),
                dto.status(),
                toTypeResponse(dto.typeId(), dto.typeCode(), acceptLanguage),
                dto.parentId(),
                dto.operationalScopeType(),
                dto.operationalScopeId(),
                dto.operationalScopeCode(),
                dto.operationalScopeName(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public PositionResponse toResponse(PositionDto dto) {
        Objects.requireNonNull(dto, "Position DTO must not be null.");
        return new PositionResponse(
                dto.positionId(),
                dto.code(),
                dto.title(),
                dto.description(),
                dto.active(),
                dto.createdAt(),
                dto.updatedAt());
    }

    public PageResult<EmployeeResponse> toEmployeeResponsePage(PageResult<EmployeeDto> pageResult) {
        return PageResult.of(
                pageResult.items().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

    public PageResult<OrganizationUnitResponse> toOrganizationUnitResponsePage(PageResult<OrganizationUnitDto> pageResult) {
        return toOrganizationUnitResponsePage(pageResult, null);
    }

    public PageResult<OrganizationUnitResponse> toOrganizationUnitResponsePage(
            PageResult<OrganizationUnitDto> pageResult,
            String acceptLanguage) {
        return PageResult.of(
                pageResult.items().stream().map(item -> toResponse(item, acceptLanguage)).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

    public PageResult<PositionResponse> toPositionResponsePage(PageResult<PositionDto> pageResult) {
        return PageResult.of(
                pageResult.items().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

    private static OrganizationUnitTypeResponse toTypeResponse(String typeId, String typeCode, String acceptLanguage) {
        OrganizationUnitTypeReference reference = typeId == null
                ? OrganizationUnitTypeReference.ofCode(typeCode)
                : OrganizationUnitTypeReference.of(typeId, typeCode);
        String locale = resolveLocale(acceptLanguage);
        return new OrganizationUnitTypeResponse(reference.id(), reference.name(), reference.localizedLabel(locale), locale);
    }

    private static String resolveLocale(String acceptLanguage) {
        if (acceptLanguage == null || acceptLanguage.isBlank()) {
            return "en";
        }
        String first = acceptLanguage.split(",")[0].trim();
        if (first.length() < 2) {
            return "en";
        }
        return first.substring(0, 2).toLowerCase(Locale.ROOT);
    }

    private static EmployeeEmail optionalEmail(String value) {
        return blank(value) ? null : EmployeeEmail.of(value);
    }

    private static IdentityUserReference optionalIdentityUserReference(String value) {
        return blank(value) ? null : IdentityUserReference.of(value);
    }

    private static OrganizationUnitId optionalOrganizationUnitId(String value) {
        return blank(value) ? null : OrganizationUnitId.of(value);
    }

    private static OperationalScopeType optionalOperationalScopeType(String value) {
        return blank(value) ? null : OperationalScopeType.valueOf(requiredEnum(value));
    }

    private static EmploymentStatus optionalEmploymentStatus(String value) {
        return blank(value) ? null : EmploymentStatus.valueOf(requiredEnum(value));
    }

    private static OrganizationUnitTypeReference optionalOrganizationUnitType(String value) {
        return blank(value) ? null : OrganizationUnitTypeReference.ofCode(value);
    }

    private static OrganizationUnitStatus optionalOrganizationUnitStatus(String value) {
        return blank(value) ? null : OrganizationUnitStatus.valueOf(requiredEnum(value));
    }

    private static String requiredEnum(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }
}
