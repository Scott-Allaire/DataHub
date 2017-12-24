package org.coder229.datahub.repository;

import org.coder229.datahub.model.Source;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SourceRepository extends PagingAndSortingRepository<Source, Long> {

    Optional<Source> findByName(String name);
}
