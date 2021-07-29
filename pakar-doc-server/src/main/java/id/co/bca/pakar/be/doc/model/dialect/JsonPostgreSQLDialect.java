package id.co.bca.pakar.be.doc.model.dialect;

import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

public class JsonPostgreSQLDialect extends PostgreSQL94Dialect {
    public JsonPostgreSQLDialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "json");
    }
}
