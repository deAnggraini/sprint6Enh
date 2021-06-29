package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Images;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Images, Long>{

}
