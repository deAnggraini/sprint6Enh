package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.Structure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureRepository extends CrudRepository<Structure, Long>{
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Structure m WHERE m.parentStructure=:parentId AND m.sort=:sort AND m.deleted IS FALSE")
    Boolean existStructureByParentIdAndSort(@Param("parentId") Long parentId, @Param("sort") Long sort);

}
