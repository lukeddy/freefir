package com.snicesoft.freefir.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.snicesoft.freefir.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findTopByPkName(String pkName);

    Project findTopByShortUrl(String shortUrl);

}
