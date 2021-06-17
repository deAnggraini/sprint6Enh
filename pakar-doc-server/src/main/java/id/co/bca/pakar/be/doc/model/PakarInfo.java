package id.co.bca.pakar.be.doc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "t_pakar_info")
public class PakarInfo extends EntityBase {
	@Id
	@SequenceGenerator(name = "pakarInfoSeqGen", sequenceName = "pakarInfoSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "pakarInfoSeqGen")
	private Long id;
}
