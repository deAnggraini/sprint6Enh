package id.co.bca.pakar.be.oauth2.dao;

import id.co.bca.pakar.be.oauth2.model.StructureIcons;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureIconRepository extends CrudRepository<StructureIcons, Long>{
    @Query("SELECT m FROM StructureIcons m WHERE m.structure.id=:structureId AND m.deleted IS FALSE")
    StructureIcons findByStructureId(Long structureId);
}
