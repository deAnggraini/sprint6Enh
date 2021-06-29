package id.co.bca.pakar.be.doc.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import id.co.bca.pakar.be.doc.model.Structure;

@Repository
public interface StructureRepository extends CrudRepository<Structure, Long>{

}
