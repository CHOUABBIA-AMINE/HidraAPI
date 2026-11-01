package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase;
import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.Command;
import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.Operation;
import dz.sh.hidra.modules.topology.domain.service.InstalledCharacteristicLinkApprovalTargetDigester;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Treats a locator as untrusted addressing data. Every disclosure goes through the
 * original native Topology/Identity/Simulation protected transaction. Suspending an
 * ambient Workflow JPA transaction is mandatory: protected execution rejects nested
 * and ambient transactions. The Workflow transaction resumes after the owner read.
 * S3 must requalify the source/approver inside its own approval publication.
 */
@Component
public class TopologyInstalledCharacteristicLinkWorkflowTargetLookup implements WorkflowOwnedTargetLookup {
    public static final String TYPE="INSTALLED_CHARACTERISTIC_LINK_REVISION";
    private static final String PREFIX="v1";
    private final InstalledCharacteristicLinkUseCase owner;

    public TopologyInstalledCharacteristicLinkWorkflowTargetLookup(InstalledCharacteristicLinkUseCase owner) {
        this.owner=Objects.requireNonNull(owner);
    }
    @Override public String module() { return "topology"; }
    @Override public Set<String> targetTypeCodes() { return Set.of(TYPE); }
    @Override public Optional<Target> eligibleTarget(String type,String digest) { return Optional.empty(); }

    /** Canonical untrusted locator, not an authorization token or an approval. */
    public static String locator(String company,String unit,String link,long revision) {
        if (revision<1) throw new IllegalArgumentException("Positive exact link revision required");
        return PREFIX+"."+enc(company)+"."+enc(unit)+"."+enc(link)+"."+revision;
    }
    private static String enc(String id) {
        if(id==null||id.isBlank()||!id.equals(id.strip())||id.codePointCount(0,id.length())>80
                ||id.codePoints().anyMatch(c->Character.isISOControl(c)||c>=0xd800&&c<=0xdfff))
            throw new IllegalArgumentException("Exact locator identity required");
        return Base64.getUrlEncoder().withoutPadding().encodeToString(id.getBytes(StandardCharsets.UTF_8));
    }
    private record Coordinates(String company,String unit,String link,long revision) {}
    private static Coordinates parse(String supplied) {
        if(supplied==null||supplied.length()>2048) throw new IllegalArgumentException("Invalid locator");
        var parts=supplied.split("\\.",-1);
        if(parts.length!=5||!PREFIX.equals(parts[0])||!parts[4].matches("[1-9][0-9]{0,18}"))
            throw new IllegalArgumentException("Invalid locator");
        String a=dec(parts[1]),b=dec(parts[2]),c=dec(parts[3]);
        long revision=Long.parseLong(parts[4]);
        if(!supplied.equals(locator(a,b,c,revision))) throw new IllegalArgumentException("Noncanonical locator");
        return new Coordinates(a,b,c,revision);
    }
    private static String dec(String value) {
        return new String(Base64.getUrlDecoder().decode(value),StandardCharsets.UTF_8);
    }
    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Optional<Target> eligibleTarget(String type,String digest,String untrustedLocator) {
        if(!TYPE.equals(type)||digest==null||!digest.matches("[0-9a-f]{64}")) return Optional.empty();
        try {
            var c=parse(untrustedLocator);
            var r=owner.execute(new Command(Operation.READ_REVISION,c.company(),c.unit(),c.link(),
                    null,null,null,c.revision(),0,null));
            var link=r.link();
            if(link==null||link.linkRevision()!=c.revision()
                    ||!link.companyPartyId().equals(c.company())
                    ||!link.installedUnitId().equals(c.unit())
                    ||!link.linkId().equals(c.link())
                    ||!digest.equals(InstalledCharacteristicLinkApprovalTargetDigester.digest(link)))
                return Optional.empty();
            return Optional.of(new Target(digest,locator(c.company(),c.unit(),c.link(),c.revision()),
                    "Installed characteristic link revision"));
        } catch(RuntimeException unavailable) {
            // Includes identity DENY, missing source, indeterminate outcome and malformed locator.
            // Never return a fallback target or a protected existence distinction.
            return Optional.empty();
        }
    }
    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public boolean protectedReadAllowed(String type,String digest,String untrustedLocator,ProtectedRead operation) {
        if(operation==null||eligibleTarget(type,digest,untrustedLocator).isEmpty()) return false;
        if(operation==ProtectedRead.REVISION) return true;
        try {
            var c=parse(untrustedLocator);
            // HISTORY requires its independent native permission as well as exact revision READ.
            owner.execute(new Command(Operation.LIST_HISTORY,c.company(),c.unit(),c.link(),
                    null,null,null,null,1,null));
            return true;
        }catch(RuntimeException denied){return false;}
    }
}
