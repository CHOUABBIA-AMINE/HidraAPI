/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationAppointmentCommitProofCodecTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-22
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Authenticates transaction-bound appointment commit evidence within Identity.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcOrganizationAppointmentCommitProofKeyAdapter.Context;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationAppointmentCommitProofCodecTest {
    // Independently generated with Python struct/uuid/hmac/hashlib, not the production encoder.
    static final String FRAME = "0000002748494452412f4944454e544954592f4f52472d4150504f494e544d454e542d434f4d4d49542f310000000111111111111111111111111111111111000000097075626c697368657200000008746573742d6b65790000000000000007000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f22222222222222222222222222222222333333333333333333333333333333330000000475736572000000085452414e534645520000000200000007636f6d70616e7900000004f090808000000007636f6d70616e7900000003ee808000000008656d706c6f796565fffffffffff0bdbf0000000000112480000000000001e2400000007b00000000000001c844444444444444444444444444444444ababababababababababababababababababababababababababababababababcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcd";
    static final String MAC = "25045243507018a5ebec21c371204227eaefcb99d1ab6374b1a44ffc7c03eda7";
    private byte[] key() { var b = new byte[32]; for(int i=0;i<32;i++) b[i]=(byte)i; return b; }
    private Contribution evidence() {
        var r = new Request(Action.TRANSFER,List.of(new CompanyUnit("company","𐀀"),new CompanyUnit("company","")),"employee");
        return new Contribution(r,r.requirements().stream().map(x -> new ResourceDecision(x,Decision.PERMIT)).toList(),
                "22222222-2222-2222-2222-222222222222","33333333-3333-3333-3333-333333333333","user",
                Instant.ofEpochSecond(-2,999999000),Instant.ofEpochSecond(1,123456000),123,456);
    }
    private CommitIntent intent() {
        return new CommitIntent(UUID.fromString("44444444-4444-4444-4444-444444444444"),
                "ab".repeat(32),"cd".repeat(32),Instant.ofEpochSecond(0,123456000));
    }
    private Context context() { return new Context("test-key",7,UUID.fromString("11111111-1111-1111-1111-111111111111"),"publisher",123,456); }
    @Test void frozenFrameAndIndependentHmacCoverUnicodeUuidAndNegativeMicroseconds() {
        var proof = new OrganizationAppointmentCommitProofCodec(key()).authenticate(evidence(),intent(),context(),key());
        assertEquals(FRAME,HexFormat.of().formatHex(proof.frame()));
        assertEquals(MAC,HexFormat.of().formatHex(proof.mac()));
        assertEquals(349,proof.frame().length);
    }
    @Test void proofDefensivelyCopiesArraysAndRedactsItsRepresentation() {
        var f=new byte[]{1}; var m=new byte[32]; var p=new CommitProof(f,m); f[0]=9; m[0]=9;
        assertEquals(1,p.frame()[0]); assertEquals(0,p.mac()[0]);
        p.frame()[0]=9; p.mac()[0]=9; assertEquals(1,p.frame()[0]); assertEquals(0,p.mac()[0]);
        assertEquals("CommitProof[redacted]",p.toString());
    }
    @Test void rejectsAmbiguousIntentDigestsAndUnrepresentableMicroseconds() {
        assertThrows(IllegalArgumentException.class,() -> new CommitIntent(UUID.randomUUID(),"AB".repeat(32),"cd".repeat(32),Instant.EPOCH));
        assertThrows(IllegalArgumentException.class,() -> new CommitIntent(UUID.randomUUID(),"ab".repeat(32),"cd".repeat(31),Instant.EPOCH));
        assertThrows(IllegalArgumentException.class,() -> new CommitIntent(UUID.randomUUID(),"ab".repeat(32),"cd".repeat(32),Instant.ofEpochSecond(1,1)));
        assertThrows(ArithmeticException.class,() -> new CommitIntent(UUID.randomUUID(),"ab".repeat(32),"cd".repeat(32),Instant.MAX.minusNanos(999)));
        assertEquals(-1,OrganizationAppointmentCommitProofCodec.micros(Instant.ofEpochSecond(-1,999999000)));
    }
    @Test void rejectsTransportOverflowsWrongMacWidthsAndIncompleteNonce() {
        assertThrows(IllegalArgumentException.class,() -> new CommitProof(new byte[4097],new byte[32]));
        assertThrows(IllegalArgumentException.class,() -> new CommitProof(new byte[0],new byte[32]));
        assertThrows(IllegalArgumentException.class,() -> new CommitProof(new byte[1],new byte[31]));
        assertThrows(IllegalArgumentException.class,() -> new OrganizationAppointmentCommitProofCodec(key()).authenticate(evidence(),intent(),context(),new byte[31]));
    }
    @Test void rejectsStretchedDeadlinePhysicalMismatchAndReadActions() {
        var codec=new OrganizationAppointmentCommitProofCodec(key()); var e=evidence();
        var stretched=new CommitIntent(intent().eventId(),intent().commandSha256(),intent().payloadSha256(),e.validUntil().plusNanos(1000));
        assertThrows(IllegalArgumentException.class,() -> codec.authenticate(e,stretched,context(),key()));
        var wrong=new Context("test-key",7,context().audience(),"publisher",124,456);
        assertThrows(IllegalArgumentException.class,() -> codec.authenticate(e,intent(),wrong,key()));
        var r=new Request(Action.READ_CURRENT,e.request().companyUnits(),"employee");
        var read=new Contribution(r,r.requirements().stream().map(x -> new ResourceDecision(x,Decision.PERMIT)).toList(),
                e.bindingId(),e.sessionId(),e.userId(),e.sampledAt(),e.validUntil(),123,456);
        assertThrows(IllegalArgumentException.class,() -> codec.authenticate(read,intent(),context(),key()));
    }
    @Test void cryptographicKeyAndNonceAffectMacWithoutExposingSigner() {
        var a=new OrganizationAppointmentCommitProofCodec(key()).authenticate(evidence(),intent(),context(),key());
        var b=new OrganizationAppointmentCommitProofCodec(new byte[32]).authenticate(evidence(),intent(),context(),key());
        assertArrayEquals(a.frame(),b.frame()); assertFalse(Arrays.equals(a.mac(),b.mac()));
        var c=new OrganizationAppointmentCommitProofCodec(key()).issue(evidence(),intent(),context());
        assertFalse(Arrays.equals(a.frame(),c.frame()));
        assertFalse(Arrays.equals(a.mac(),c.mac()));
    }
}

