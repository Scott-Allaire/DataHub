package org.coder229.datahub.model.specs;

import org.coder229.datahub.model.Reading;
import org.coder229.datahub.model.Source;
import org.coder229.datahub.model.Reading_;
import org.coder229.datahub.model.Source_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.ZonedDateTime;

public class ReadingSpecs {
    public static Specification<Reading> forSource(String sourceName) {
        return (root, query, cb) -> {
            Join<Reading, Source> sourceJoin = root.join(Reading_.source);
            return cb.equal(sourceJoin.get(Source_.name), sourceName);
        };
    }

    public static Specification<Reading> withCategory(String category) {
        return (root, query, cb) -> {
            return cb.equal(root.get(Reading_.category), category);
        };
    }

    public static Specification<Reading> withCode(String code) {
        return (root, query, cb) -> {
            return cb.equal(root.get(Reading_.code), code);
        };
    }

    public static Specification<Reading> withEffectiveBetween(ZonedDateTime start, ZonedDateTime end) {
        return (root, query, cb) -> {
            return cb.and(
                    cb.greaterThanOrEqualTo(root.get(Reading_.effective), start),
                    cb.lessThan(root.get(Reading_.effective), end)
            );
        };
    }
}
