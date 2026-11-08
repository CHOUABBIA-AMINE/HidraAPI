package dz.sh.hidra.modules.identity.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** The opaque secret is never a URL query parameter or a user assertion. */
public record RefreshTokenRequest(@NotBlank @Size(min = 80, max = 80) String refreshToken) {
    @Override public String toString() { return "RefreshTokenRequest[<redacted>]"; }
}
