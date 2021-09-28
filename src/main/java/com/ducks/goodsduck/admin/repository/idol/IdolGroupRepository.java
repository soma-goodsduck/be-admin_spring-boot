package com.ducks.goodsduck.admin.repository.idol;

import com.ducks.goodsduck.admin.model.entity.IdolGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdolGroupRepository extends JpaRepository<IdolGroup, Long>, IdolGroupRepositoryCustom {

//    @Query("select g from IdolGroup g where g.korName = :name or g.engName = :name")
//    IdolGroup findByName(@Param("name") String name);
}
