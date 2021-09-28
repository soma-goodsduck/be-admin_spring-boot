package com.ducks.goodsduck.admin.repository.idol;

import com.ducks.goodsduck.admin.model.entity.IdolMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdolMemberRepository extends JpaRepository<IdolMember, Long> {

    @Query("select m from IdolMember m where m.name like %:name%")
    List<IdolMember> findByName(@Param("name") String name);
}
