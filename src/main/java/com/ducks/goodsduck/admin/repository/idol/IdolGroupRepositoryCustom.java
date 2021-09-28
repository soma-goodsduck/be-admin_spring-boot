package com.ducks.goodsduck.admin.repository.idol;

import com.ducks.goodsduck.admin.model.entity.IdolGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdolGroupRepositoryCustom {

    List<IdolGroup> findAllByName(String name);
}
