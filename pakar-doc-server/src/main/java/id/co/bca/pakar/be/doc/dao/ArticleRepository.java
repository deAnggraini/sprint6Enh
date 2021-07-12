package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Icons;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Icons, Long> {
}
