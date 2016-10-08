package com.snicesoft.freefir.repository;

import com.snicesoft.freefir.domain.Project;
import com.snicesoft.freefir.domain.Version;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhe on 2016/10/3.
 */
@Repository
public interface VersionRepository extends CrudRepository<Version, Long> {

    Version findTopByProjectOrderByAddDateDesc(Project project);

    Version findTopByProjectAndVersionCodeGreaterThanOrderByAddDateDesc(Project project, long versionCode);

    List<Version> findByProjectOrderByAddDateDesc(Project project);

}
