package com.carmaxbackend.admin.paging;

import com.carmax.common.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface SearchRepository<T, ID> extends CrudRepository<T, ID>, PagingAndSortingRepository<T, ID> {
	public Page<T> findAll(String keyword, Pageable pageable);
}
