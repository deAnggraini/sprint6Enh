package id.co.bca.pakar.be.wf.model;

import javax.persistence.*;

@Entity
@Table(name = "r_wf_group_member")
public class WokrflowGroupMemberModel extends EntityBase {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private WokrflowGroupModel group;
    @Column(name = "userid", nullable = false)
    private String userid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WokrflowGroupModel getGroup() {
        return group;
    }

    public void setGroup(WokrflowGroupModel group) {
        this.group = group;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
