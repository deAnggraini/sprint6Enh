package id.co.bca.pakar.be.doc.dao;

import id.co.bca.pakar.be.doc.model.StructureIcons;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureIconRepository extends CrudRepository<StructureIcons, Long>{
    @Query("SELECT m FROM StructureIcons m WHERE m.structure.id=:structureId AND m.deleted IS FALSE")
    @CacheEvict
    StructureIcons findByStructureId(Long structureId);
}
