package com.carmaxbackend.admin.history;

import com.carmax.common.entity.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HistoryRepository extends CrudRepository<History, Integer>, PagingAndSortingRepository<History, Integer> {
}
