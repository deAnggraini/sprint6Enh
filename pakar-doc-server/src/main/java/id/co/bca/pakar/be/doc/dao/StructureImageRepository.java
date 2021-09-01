package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.StructureImages;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureImageRepository extends CrudRepository<StructureImages, Long>{
    @Query("SELECT m FROM StructureImages m WHERE m.structure.id=:structureId AND m.deleted IS FALSE")
    @CacheEvict
    StructureImages findByStructureId(@Param("structureId") Long structureId);
}
