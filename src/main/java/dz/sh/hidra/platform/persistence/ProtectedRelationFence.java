/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedRelationFence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence
 *
 * @Description : Carries immutable owner-defined relation lock requirements without granting authority.
 *
 */
package dz.sh.hidra.platform.persistence;

public record ProtectedRelationFence(String relation,Mode mode) {
    public enum Mode {
        SHARE_ROW_EXCLUSIVE("ShareRowExclusiveLock"), ACCESS_SHARE("AccessShareLock"), SHARE("ShareLock");
        private final String catalog;
        Mode(String catalog){this.catalog=catalog;}
        public String catalog(){return catalog;}
    }
    public ProtectedRelationFence {
        if(relation==null || !relation.matches("public\\.[a-z][a-z0-9_]*") || mode==null)
            throw new IllegalArgumentException("Invalid qualified relation fence");
    }
}
