package com.carmaxbackend.admin.history;

import com.carmax.common.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HistoryRepository extends CrudRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {
}
