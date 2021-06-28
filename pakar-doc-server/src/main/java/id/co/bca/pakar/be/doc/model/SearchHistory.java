package id.co.bca.pakar.be.doc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "t_search_history")
public class SearchHistory {
	@Id
	@SequenceGenerator(name = "suggestionSearchSeqGen", sequenceName = "suggestionSearchSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "suggestionSearchSeqGen")
	@Column(name = "id")
	private Long id;
	@Column(name = "suggestion_text", columnDefinition="TEXT")
	private String suggestionText;
}
