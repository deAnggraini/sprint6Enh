package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "t_wf_notification")
public class SendNotificationModel extends EntityBase {
    @Id
    @SequenceGenerator(name = "wfSendNotificationSeqGen", sequenceName = "wfSendNotificationSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "wfSendNotificationSeqGen")
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
