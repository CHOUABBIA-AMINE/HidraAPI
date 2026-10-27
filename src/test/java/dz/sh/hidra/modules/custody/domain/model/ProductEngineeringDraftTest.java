/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductEngineeringDraftTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Provides company-owned catalog-linked product metadata drafts.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.*;
import dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.*;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.ProductEngineeringDraftMetadataCodec;

class ProductEngineeringDraftTest {
    private ProductEngineeringDraft draft(Metadata metadata) {
        return new ProductEngineeringDraft(UUID.randomUUID(),"company","ExactCode","catalog",UUID.randomUUID(),1,metadata);
    }
    @Test void absentMetadataRemainsExplicitlyIncomplete() {
        var metadata=new Metadata(null,null,null,null);
        assertThat(metadata.diagnostics()).containsExactly("NAME_NOT_AUTHORED","EVIDENCE_NOT_AUTHORED","CLASSIFICATION_UNSPECIFIED");
        assertThat(metadata.classification()).isNull();
    }
    @Test void labelsDoNotCreatePropertiesOrChangeCatalogIdentity() {
        var first=draft(new Metadata("A",null,"source",Classification.GAS));
        var next=first.revise(1,new Metadata("B",null,"source",Classification.LIQUID));
        assertThat(next.id()).isEqualTo(first.id());assertThat(next.catalogProductId()).isEqualTo(first.catalogProductId());
        assertThat(next.localCode()).isEqualTo(first.localCode());assertThat(next.creationRequestId()).isEqualTo(first.creationRequestId());
        assertThat(next.version()).isEqualTo(2);assertThat(first.metadata().name()).isEqualTo("A");
    }
    @Test void revisionRequiresPositiveExactVersionWithoutOverflow() {
        var first=draft(new Metadata(null,null,null,null));
        assertThatThrownBy(()->first.revise(2,first.metadata())).isInstanceOf(IllegalArgumentException.class);
        var last=new ProductEngineeringDraft(first.id(),"company","code","catalog",first.creationRequestId(),Long.MAX_VALUE,first.metadata());
        assertThatThrownBy(()->last.revise(Long.MAX_VALUE,last.metadata())).isInstanceOf(IllegalArgumentException.class);
    }
    @Test void exactUnicodeScalarBoundsDoNotCountSurrogatePairsTwice() {
        String scalar="\uD83D\uDE80";
        ProductEngineeringDraft.text(scalar.repeat(80),80,true);
        assertThatThrownBy(()->ProductEngineeringDraft.text(scalar.repeat(81),80,true)).isInstanceOf(IllegalArgumentException.class);
        new Metadata(scalar.repeat(240),scalar.repeat(4000),scalar.repeat(1000),null);
    }
    @Test void invalidUnicodeNulWhitespaceAndOversizeTextRejectWithoutNormalization() {
        for(String value:new String[]{""," "," x","x\n","\u2000x","x\u3000","x\0","\uD800","\uDC00"})
            assertThatThrownBy(()->ProductEngineeringDraft.text(value,80,true)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Metadata("x".repeat(241),null,null,null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Metadata(null,"x".repeat(4001),null,null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Metadata(null,null,"x".repeat(1001),null)).isInstanceOf(IllegalArgumentException.class);
    }
    @Test void metadataCodecRoundTripsAllLabelsAndNullsExactly() {
        var codec=new ProductEngineeringDraftMetadataCodec();
        for(Classification label:new Classification[]{null,Classification.GAS,Classification.LIQUID}) {
            var metadata=new Metadata("é\uD83D\uDE80",null,"exact:source",label);
            assertThat(codec.decode(codec.encode(metadata))).isEqualTo(metadata);
        }
        assertThat(codec.decode(codec.encode(new Metadata(null,null,null,null))).diagnostics()).hasSize(3);
    }
    @Test void malformedUtf8LengthsUnknownLabelsAndTrailingBytesFailClosed() {
        var codec=new ProductEngineeringDraftMetadataCodec();byte[] original=codec.encode(new Metadata("A",null,null,Classification.GAS));
        byte[] utf=original.clone();utf[8]=(byte)0xff;
        byte[] label=original.clone();label[label.length-1]='X';
        byte[] length=original.clone();length[4]=(byte)0x7f;
        for(byte[] invalid:List.of(utf,label,length,Arrays.copyOf(original,original.length+1),Arrays.copyOf(original,original.length-1)))
            assertThatThrownBy(()->codec.decode(invalid)).isInstanceOf(IllegalArgumentException.class);
    }
    @Test void originalRequestDigestBindsEveryImmutableFactAndExactMetadata() {
        var codec=new ProductEngineeringDraftMetadataCodec();var metadata=new Metadata("A",null,null,null);UUID request=UUID.randomUUID();
        String hash=codec.requestDigest("company","code","catalog",request,metadata);
        assertThat(hash).isNotEqualTo(codec.requestDigest("other","code","catalog",request,metadata))
                .isNotEqualTo(codec.requestDigest("company","Code","catalog",request,metadata))
                .isNotEqualTo(codec.requestDigest("company","code","other",request,metadata))
                .isNotEqualTo(codec.requestDigest("company","code","catalog",UUID.randomUUID(),metadata))
                .isNotEqualTo(codec.requestDigest("company","code","catalog",request,new Metadata("B",null,null,null)));
    }
    @Test void commandsRejectIdentityMutationAndAmbiguousShapes() {
        var metadata=new Metadata(null,null,null,null);UUID id=UUID.randomUUID();
        assertThatThrownBy(()->new Command(Kind.READ,"company",id,"code",null,null,null,null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Command(Kind.UPDATE,"company",id,null,null,null,0L,metadata)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Command(Kind.CREATE,"company",id,"code","catalog",id,null,metadata)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new Command(Kind.READ,"company",id,null,null,null,1L,null)).isInstanceOf(IllegalArgumentException.class);
    }
}
