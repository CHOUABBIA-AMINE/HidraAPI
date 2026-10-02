/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.mapper
 *
 * @Description : Maps hse REST models to application models.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.mapper;
import dz.sh.hidra.modules.hse.api.rest.request.CloseHseCaseRequest;
import dz.sh.hidra.modules.hse.api.rest.request.CreateHseCapaRequest;
import dz.sh.hidra.modules.hse.api.rest.request.OpenHseCaseRequest;
import dz.sh.hidra.modules.hse.api.rest.response.HseCapaResponse;
import dz.sh.hidra.modules.hse.api.rest.response.HseCaseResponse;
import dz.sh.hidra.modules.hse.application.command.CloseHseCaseCommand;
import dz.sh.hidra.modules.hse.application.command.CreateHseCapaCommand;
import dz.sh.hidra.modules.hse.application.command.OpenHseCaseCommand;
import dz.sh.hidra.modules.hse.application.dto.HseCapaSummaryDto;
import dz.sh.hidra.modules.hse.application.dto.HseCaseSummaryDto;
import java.util.Objects;

/**
 * Maps hse REST models to application models.
 */
public final class HseRestMapper {

    private static final HseGeneratedRestMapper GENERATED = HseGeneratedRestMapper.INSTANCE;

    private HseRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CloseHseCaseCommand toCommand(CloseHseCaseRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CloseHseCaseRequest must not be null."));
    }

    public static CreateHseCapaCommand toCommand(CreateHseCapaRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateHseCapaRequest must not be null."));
    }

    public static OpenHseCaseCommand toCommand(OpenHseCaseRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "OpenHseCaseRequest must not be null."));
    }

    public static HseCapaResponse toResponse(HseCapaSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "HseCapaSummaryDto must not be null."));
    }

    public static HseCaseResponse toResponse(HseCaseSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "HseCaseSummaryDto must not be null."));
    }
}
