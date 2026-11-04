/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FoundationWorkflowDevelopmentProvisioner
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.configuration
 *
 * @Description : Explicit, dev-only and owner-human-validated Workflow demonstration process registration.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.configuration;

import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowHumanActorContract;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Never runs in production or in test profiles. It does not fabricate a target,
 * source approval or Identity grant, and it never bypasses a native authorization check.
 */
@Component
@Profile("dev")
@ConditionalOnProperty(name = "hidra.workflow.foundation-dev-provisioning.enabled", havingValue = "true")
public class FoundationWorkflowDevelopmentProvisioner implements ApplicationRunner {
    private static final String DEFINITION = "foundation-dev-link-approval-v1";
    private static final String REVIEW = "foundation-dev-link-review-v1";
    private static final String APPROVED = "foundation-dev-link-approved-v1";
    private static final String REJECTED = "foundation-dev-link-rejected-v1";
    private static final String MODE = "foundation-dev-direct-assignment-v1";
    private static final String RULE = "foundation-dev-link-assign-v1";

    private final JdbcTemplate jdbc;
    private final WorkflowActorContract actors;
    private final WorkflowHumanActorContract humans;
    private final String reviewer;

    public FoundationWorkflowDevelopmentProvisioner(JdbcTemplate jdbc, WorkflowActorContract actors,
            WorkflowHumanActorContract humans,
            @Value("${hidra.workflow.foundation-dev-provisioning.approver-actor-id:}") String reviewer) {
        this.jdbc = Objects.requireNonNull(jdbc);
        this.actors = Objects.requireNonNull(actors);
        this.humans = Objects.requireNonNull(humans);
        this.reviewer = reviewer == null ? "" : reviewer.trim();
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (reviewer.isBlank()) throw new IllegalStateException(
                "Dev-only Workflow provisioner requires an explicit independent human approver actor ID.");
        var actor = actors.eligibleActor(reviewer, Instant.now())
                .orElseThrow(() -> new IllegalStateException("Configured Workflow reviewer is not eligible."));
        if (humans.eligibleHuman(reviewer, Instant.now())
                .filter(h -> reviewer.equals(h.id()) && actor.displayName().equals(h.displayName())).isEmpty())
            throw new IllegalStateException("Configured Workflow reviewer is not an eligible human.");

        String type = requireCatalog("WORKFLOW_TARGET_TYPE", "INSTALLED_CHARACTERISTIC_LINK_REVISION");
        String purpose = requireCatalog("WORKFLOW_PURPOSE", "INSTALLED_CHARACTERISTIC_LINK_APPROVAL");
        jdbc.update("INSERT INTO public.hidra_workflow_type_catalog "
                + "(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) "
                + "VALUES (?,'WORKFLOW_ASSIGNMENT_MODE','DIRECT_ACTOR',true,0,false,now(),now()) "
                + "ON CONFLICT (id) DO NOTHING", MODE);
        // The definition's technical type references the already registered target family.
        jdbc.update("INSERT INTO public.hidra_workflow_definition "
                + "(id,code,name_fr,type_id,status,version,created_at,updated_at) "
                + "VALUES (?,?,'Validation de liaison installée',?,'ACTIVE',1,now(),now()) "
                + "ON CONFLICT (id) DO NOTHING", DEFINITION, DEFINITION, type);
        jdbc.update("INSERT INTO public.hidra_workflow_step "
                + "(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,allow_delegation,allow_escalation,created_at,updated_at) "
                + "VALUES (?,?,?,'Revue indépendante',1,true,false,false,false,now(),now()) "
                + "ON CONFLICT (id) DO NOTHING", REVIEW, DEFINITION, "REVIEW");
        for (var terminal : List.of(new String[] {APPROVED,"APPROVED","Approuvé","2"},
                new String[] {REJECTED,"REJECTED","Rejeté","3"})) {
            jdbc.update("INSERT INTO public.hidra_workflow_step "
                    + "(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,allow_delegation,allow_escalation,created_at,updated_at) "
                    + "VALUES (?,?,?, ?, ?,true,false,false,false,now(),now()) "
                    + "ON CONFLICT (id) DO NOTHING", terminal[0], DEFINITION, terminal[1], terminal[2],
                    Integer.parseInt(terminal[3]));
        }
        jdbc.update("INSERT INTO public.hidra_workflow_definition_target_binding "
                + "(id,definition_id,target_module,target_type_id,workflow_purpose_id,active,created_at,updated_at) "
                + "VALUES ('foundation-dev-link-binding-v1',?,'topology',?,?,true,now(),now()) "
                + "ON CONFLICT (id) DO NOTHING", DEFINITION, type, purpose);
        jdbc.update("INSERT INTO public.hidra_workflow_step_assignment_rule "
                + "(id,definition_id,step_id,assignment_mode_id,actor_id,active,created_at,updated_at) "
                + "VALUES (?,?,?,?,?,true,now(),now()) ON CONFLICT (id) DO NOTHING",
                RULE, DEFINITION, REVIEW, MODE, reviewer);
        for (var transition : List.of(new String[] {"foundation-dev-link-approve-v1",APPROVED,"APPROVE"},
                new String[] {"foundation-dev-link-reject-v1",REJECTED,"REJECT"})) {
            jdbc.update("INSERT INTO public.hidra_workflow_transition "
                    + "(id,definition_id,from_step_id,to_step_id,decision,reason_required,comment_required,created_at,updated_at) "
                    + "VALUES (?,?,?,?,?,?,false,now(),now()) ON CONFLICT (id) DO NOTHING",
                    transition[0], DEFINITION, REVIEW, transition[1], transition[2],
                    "REJECT".equals(transition[2]));
        }
        String registered = jdbc.queryForObject("SELECT actor_id FROM public.hidra_workflow_step_assignment_rule WHERE id=?",
                String.class, RULE);
        if (!reviewer.equals(registered)
                || !jdbc.queryForObject("SELECT (status='ACTIVE' AND version=1) "
                        + "FROM public.hidra_workflow_definition WHERE id=?", Boolean.class, DEFINITION))
            throw new IllegalStateException("Existing dev Workflow process conflicts with requested configuration.");
    }

    private String requireCatalog(String family, String code) {
        return jdbc.queryForObject("SELECT id FROM public.hidra_workflow_type_catalog "
                + "WHERE catalog_name=? AND code=? AND active=true", String.class, family, code);
    }
}
