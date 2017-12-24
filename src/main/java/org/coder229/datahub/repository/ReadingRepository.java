package org.coder229.datahub.repository;

import org.coder229.datahub.model.Reading;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReadingRepository extends PagingAndSortingRepository<Reading,Long> {
}
