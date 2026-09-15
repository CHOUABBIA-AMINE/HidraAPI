/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalAdministratorBootstrapRunner
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Bootstrap
 * @Module      : bootstrap
 * @Package     : dz.sh.hidra.bootstrap
 *
 * @Description : Executes explicitly enabled persistent LOCAL administrator provisioning and records its audit event.
 *
 */
package dz.sh.hidra.bootstrap;

import dz.sh.hidra.modules.audit.application.command.RecordAuditEventCommand;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditEventUseCase;
import dz.sh.hidra.modules.audit.domain.value.AuditActorType;
import dz.sh.hidra.modules.audit.domain.value.AuditOperation;
import dz.sh.hidra.modules.identity.application.port.in.BootstrapLocalAdministratorUseCase;
import dz.sh.hidra.modules.identity.application.port.in.BootstrapLocalAdministratorUseCase.BootstrapCommand;
import dz.sh.hidra.modules.identity.application.port.in.BootstrapLocalAdministratorUseCase.BootstrapResult;
import java.time.Instant;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Startup runner for controlled persistent LOCAL administrator provisioning.
 */
@Component
@ConditionalOnProperty(prefix = "hidra.security.bootstrap", name = "enabled", havingValue = "true")
public class LocalAdministratorBootstrapRunner implements ApplicationRunner {

    private final BootstrapLocalAdministratorUseCase bootstrapUseCase;
    private final RecordAuditEventUseCase auditEventUseCase;
    private final String username;
    private final String password;
    private final String emailAddress;
    private final String displayName;

    public LocalAdministratorBootstrapRunner(
            BootstrapLocalAdministratorUseCase bootstrapUseCase,
            RecordAuditEventUseCase auditEventUseCase,
            @Value("${hidra.security.bootstrap.username:hidra-admin}") String username,
            @Value("${hidra.security.bootstrap.password:}") String password,
            @Value("${hidra.security.bootstrap.email-address:}") String emailAddress,
            @Value("${hidra.security.bootstrap.display-name:Hidra Administrator}") String displayName
    ) {
        this.bootstrapUseCase = Objects.requireNonNull(bootstrapUseCase, "Bootstrap use case must not be null.");
        this.auditEventUseCase = Objects.requireNonNull(auditEventUseCase, "Audit event use case must not be null.");
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.displayName = displayName;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        BootstrapResult result = bootstrapUseCase.bootstrap(new BootstrapCommand(
                username,
                password,
                emailAddress,
                displayName
        ));
        if (!result.created()) {
            return;
        }

        Instant occurredAt = Instant.now();
        auditEventUseCase.recordAuditEvent(new RecordAuditEventCommand(
                "IDENTITY_LOCAL_ADMIN_BOOTSTRAP",
                "SECURITY_ADMINISTRATION",
                null,
                "identity",
                "LocalAdministratorBootstrapRunner",
                "AUTH-021:" + result.userId(),
                "identity.local-admin.bootstrap",
                "Persistent LOCAL administrator bootstrap",
                null,
                AuditActorType.SYSTEM,
                "Hidra Bootstrap",
                null,
                "identity",
                "User",
                result.userId(),
                result.username(),
                result.username(),
                AuditOperation.CREATE,
                "PROVISIONED",
                null,
                "AUTH-021 controlled one-time persistent LOCAL administrator provisioning",
                null,
                null,
                null,
                null,
                null,
                null,
                occurredAt,
                null
        ));
    }
}
