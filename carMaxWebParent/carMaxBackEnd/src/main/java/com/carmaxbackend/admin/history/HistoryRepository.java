package com.carmaxbackend.admin.history;

import com.carmax.common.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Integer>, PagingAndSortingRepository<History, Integer> {
}
