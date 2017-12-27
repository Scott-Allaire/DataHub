package org.coder229.datahub.repository;

import org.coder229.datahub.model.Reading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.ZonedDateTime;

public interface ReadingRepository extends PagingAndSortingRepository<Reading,Long>, JpaSpecificationExecutor<Reading> {
    Page<Reading> findByEffectiveBetween(ZonedDateTime start, ZonedDateTime end, Pageable pageable);
}
