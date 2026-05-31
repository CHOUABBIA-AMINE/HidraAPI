/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DatabaseReadinessHealthIndicator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.health
 *
 * @Description : Reports technical database readiness without exposing sensitive connection details.
 *
 */
package dz.sh.hidra.platform.observability.health;

import java.sql.Connection;
import javax.sql.DataSource;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseReadinessHealthIndicator implements HealthIndicator {

    private static final int VALIDATION_TIMEOUT_SECONDS = 2;

    private final DataSource dataSource;

    public DatabaseReadinessHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(VALIDATION_TIMEOUT_SECONDS)) {
                return Health.up()
                        .withDetail("component", "database")
                        .build();
            }
            return Health.down()
                    .withDetail("component", "database")
                    .withDetail("reason", "connection validation failed")
                    .build();
        } catch (Exception exception) {
            return Health.down()
                    .withDetail("component", "database")
                    .withDetail("reason", exception.getClass().getSimpleName())
                    .build();
        }
    }
}
