/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcScopedReportDocumentMarkerAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;
import dz.sh.hidra.modules.documents.application.port.out.ScopedReportDocumentMarkerPort;
import java.util.Optional;
import java.util.NoSuchElementException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class JdbcScopedReportDocumentMarkerAdapter implements ScopedReportDocumentMarkerPort {
    private final JdbcTemplate jdbc;
    public JdbcScopedReportDocumentMarkerAdapter(JdbcTemplate jdbc) { this.jdbc=jdbc; }
    @Override
    public Optional<Marker> find(String id,boolean lock) {
        return jdbc.query("SELECT * FROM public.hidra_documents_t25_report_output WHERE document_id=?"
                +(lock?" FOR UPDATE":""), (r,n) -> new Marker(r.getString("document_id"),r.getString("run_id"),
                    r.getString("company_id"),r.getString("facility_id"),r.getString("version_id"),
                    r.getString("acceptance_id"),r.getString("checksum_sha256"),r.getLong("content_length")),
                id).stream().findFirst();
    }
    @Override
    public String catalog(String family) {
        var rows=jdbc.query("""
                SELECT id FROM public.hidra_documents_catalog_entry
                WHERE catalog_name=? AND code='GZ2_REPORT_OUTPUT' AND active FOR SHARE
                """,(r,n)->r.getString(1),family);
        if (rows.size()!=1) throw new NoSuchElementException("Report Documents configuration unavailable.");
        return rows.getFirst();
    }
    @Override
    public void bind(Marker m) {
        jdbc.update("""
                INSERT INTO public.hidra_documents_t25_report_output
                    (document_id,run_id,company_id,facility_id,checksum_sha256,content_length)
                VALUES (?,?,?,?,?,?)
                """,m.documentId(),m.runId(),m.companyId(),m.facilityId(),m.checksum(),m.length());
    }
    @Override
    public void uploaded(String document,String version) {
        requireOne(jdbc.update("""
                UPDATE public.hidra_documents_t25_report_output SET version_id=?
                WHERE document_id=? AND version_id IS NULL
                """,version,document));
    }
    @Override
    public void sealed(String document,String version,String acceptance) {
        requireOne(jdbc.update("""
                UPDATE public.hidra_documents_t25_report_output SET acceptance_id=?
                WHERE document_id=? AND version_id=? AND acceptance_id IS NULL
                """,acceptance,document,version));
    }
    private static void requireOne(int count) {
        if (count!=1) throw new IllegalStateException("Report Documents state conflict.");
    }
}
