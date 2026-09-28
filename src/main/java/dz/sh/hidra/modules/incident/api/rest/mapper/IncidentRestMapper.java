/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.mapper
 *
 * @Description : Maps incident REST models to application models.
 *
 */
package dz.sh.hidra.modules.incident.api.rest.mapper;
import dz.sh.hidra.modules.incident.api.rest.request.CloseIncidentRequest;
import dz.sh.hidra.modules.incident.api.rest.request.OpenIncidentRequest;
import dz.sh.hidra.modules.incident.api.rest.request.RecordIncidentResponseActionRequest;
import dz.sh.hidra.modules.incident.api.rest.response.IncidentResponse;
import dz.sh.hidra.modules.incident.application.command.CloseIncidentCommand;
import dz.sh.hidra.modules.incident.application.command.OpenIncidentCommand;
import dz.sh.hidra.modules.incident.application.command.RecordIncidentResponseActionCommand;
import dz.sh.hidra.modules.incident.application.dto.IncidentSummaryDto;
import java.util.Objects;

/**
 * Maps incident REST models to application models.
 */
public final class IncidentRestMapper {

    private static final IncidentGeneratedRestMapper GENERATED = IncidentGeneratedRestMapper.INSTANCE;

    private IncidentRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CloseIncidentCommand toCommand(CloseIncidentRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CloseIncidentRequest must not be null."));
    }

    public static OpenIncidentCommand toCommand(OpenIncidentRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "OpenIncidentRequest must not be null."));
    }

    public static RecordIncidentResponseActionCommand toCommand(RecordIncidentResponseActionRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RecordIncidentResponseActionRequest must not be null."));
    }

    public static IncidentResponse toResponse(IncidentSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "IncidentSummaryDto must not be null."));
    }
}
