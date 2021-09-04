package com.ducks.goodsduck.admin.repository.idol;

import com.ducks.goodsduck.admin.model.entity.IdolGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdolGroupRepository extends JpaRepository<IdolGroup, Long> {
}
