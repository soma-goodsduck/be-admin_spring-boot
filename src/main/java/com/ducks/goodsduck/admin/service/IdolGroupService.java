package com.ducks.goodsduck.admin.service;

import com.ducks.goodsduck.admin.model.entity.IdolGroup;
import com.ducks.goodsduck.admin.repository.idol.IdolGroupRepository;
import com.ducks.goodsduck.admin.util.PropertyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class IdolGroupService {

    String accessKeySecretManager = PropertyUtil.getProperty("cloud.aws.credentials.accessKeySecretManager");
    String secretKeySecretManager = PropertyUtil.getProperty("cloud.aws.credentials.secretKeySecretManager");

    private final IdolGroupRepository idolGroupRepository;

    public List<IdolGroup> getIdolGroups() { return idolGroupRepository.findAll(); }

    public Optional<IdolGroup> getIdolGroup(Long idolGroupId) {
        return idolGroupRepository.findById(idolGroupId);
    }
}
