package dz.sh.hidra.modules.identity.application.port.in;

import dz.sh.hidra.modules.identity.application.model.AuthenticationResult;

/** Public application boundary; the caller never supplies a user, role or session identifier. */
public interface RotateLocalRefreshTokenUseCase {
    AuthenticationResult rotate(String refreshToken, String clientIp, String userAgent, String correlationId);
    void logout(String refreshToken);
}
