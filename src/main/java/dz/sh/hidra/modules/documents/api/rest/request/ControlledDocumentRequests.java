/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.request
 * @Description : Typed create, independent acceptance and pinned supersession transport bodies.
 */
package dz.sh.hidra.modules.documents.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class ControlledDocumentRequests {
    private ControlledDocumentRequests(){}
    @Schema(name="ControlledDocumentCreateRequest")
    public record Create(@NotBlank @Size(max=120) String requestId,
            @NotBlank @Size(max=120) String code,
            @NotBlank @Size(max=240) String titleFr,
            @NotBlank String documentTypeId,@NotBlank String classificationId){}
    @Schema(name="ControlledDocumentAcceptRequest")
    public record Accept(@NotBlank @Size(max=120) String requestId,
            @NotBlank @Size(max=500) String reason,@NotBlank String linkRoleId){}
    @Schema(name="ControlledDocumentSupersedeRequest")
    public record Supersede(@NotBlank @Size(max=120) String requestId,
            @NotBlank @Size(max=500) String reason,@NotBlank String linkRoleId,
            @NotBlank String expectedCurrentVersionId){}
}
