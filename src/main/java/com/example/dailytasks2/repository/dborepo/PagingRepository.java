package com.example.dailytasks2.repository.dborepo;

import com.example.dailytasks2.domain.Entity;
import com.example.dailytasks2.repository.Repository0;

public interface PagingRepository<E extends Entity>
        extends Repository0 {

    Page<E> findAll(Pageable pageable);   // Pageable e un fel de paginator
}
