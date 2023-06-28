package uwb.css533.repositoryManager.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uwb.css533.repositoryManager.model.DependencyInfo;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//This repository interface is required to have basic CRUD functionalities.

@Repository
public interface IDependenciesRepo extends JpaRepository<DependencyInfo, Integer> {

    void deleteDependencyById(int id);

    Integer findDependencyById(int id);

    Boolean existsDependencyInfoByArtifactId(String keyword);

    Boolean existsDependencyInfoByGroupId(String keyword);

    @Transactional /*The persistence context is in JPA the EntityManager, implemented internally using an Hibernate Session (when using Hibernate as the persistence provider).
    The persistence context is just a synchronizer object that tracks the state of a limited set of Java objects and makes sure that changes on those objects are eventually persisted back into the database.*/
    @Modifying
    @Query(value = "update DependencyInfo set search = :search, version = :version, lastModifiedDate = :lastModifiedDate where groupId = :groupId and artifactId = :artifactId")
    int updateEntry(@Param("groupId") String groupId, @Param("artifactId") String artifactId, @Param("search") String search, @Param("version") String version, @Param("lastModifiedDate") Date lastModifiedDate);

    List<DependencyInfo> findBySearchContaining(@Param("active") String keyword);

    List<DependencyInfo> findByArtifactIdContaining(@Param("active") String keyword);

    List<DependencyInfo> findByGroupIdContaining(@Param("active") String keyword);

    List<DependencyInfo> findByVersionContaining(@Param("active") String keyword);
}
