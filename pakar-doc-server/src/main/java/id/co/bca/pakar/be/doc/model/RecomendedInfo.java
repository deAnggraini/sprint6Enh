package id.co.bca.pakar.be.doc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "t_recomended_info")
public class RecomendedInfo {
	@Id
	@SequenceGenerator(name = "recomendPakarInfoSeqGen", sequenceName = "pakarInfoSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "recomendPakarInfoSeqGen")
	private Long id;
//	@ManyToOne
//	@JoinColumn(name = "username", nullable = false)
//	private User user;
	@Column(name = "username", nullable = false)
	private String username;
}
