/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlatformHeaders
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.web
 *
 * @Description : Defines standard platform HTTP/header names.
 *
 */
package dz.sh.hidra.platform.web;

/**
 * Standard platform header names.
 */
public final class PlatformHeaders {

    public static final String CORRELATION_ID = "X-Correlation-Id";
    public static final String REQUEST_ID = "X-Request-Id";
    public static final String ACTOR_ID = "X-Actor-Id";
    public static final String ORGANIZATION_SCOPE = "X-Organization-Scope";
    public static final String TENANT_ID = "X-Tenant-Id";

    private PlatformHeaders() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
