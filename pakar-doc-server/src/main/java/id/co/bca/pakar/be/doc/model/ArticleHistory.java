package id.co.bca.pakar.be.doc.model;

import id.co.bca.pakar.be.doc.util.ShaUtils;

import javax.persistence.*;
import java.security.SecureRandom;

import static java.nio.charset.StandardCharsets.UTF_8;

@Entity
@Table(name = "t_article_history")
public class ArticleHistory extends EntityBase {
    @Id
    @SequenceGenerator(name = "articleHistorySeqGen", sequenceName = "articleHistorySeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "articleHistorySeqGen")
    private Long id;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;
    @Column(name = "data")
    private String data;
    @Column(name = "revision")
    private String revision = generateRevision();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * generate revision value
     * @return
     */
    public static String generateRevision() {
        SecureRandom rand = new SecureRandom();

        // Generate random integers in range 0 to 999
        int randomValue = rand.nextInt(1000);
        long milis = System.currentTimeMillis() + randomValue;
        byte[] shaInBytes = ShaUtils.digest(new StringBuffer().append(milis).toString().getBytes(UTF_8));
        return ShaUtils.bytesToHex(shaInBytes);
    }
}
