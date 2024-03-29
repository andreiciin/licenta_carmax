package com.carmaxbackend.admin.history;

import com.carmax.common.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Integer>, PagingAndSortingRepository<History, Integer> {
	public History findByVehicle(String vehicle);

	@Query("UPDATE History h SET h.enabled = ?2 WHERE h.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);

	public Long countById(Integer id);

	@Query("SELECT h FROM History h WHERE h.vehicle LIKE %?1% "
			+ "OR h.fullDescription LIKE %?1% "
			+ "OR h.service LIKE %?1% ")
	public Page<History> findAll(String keyword, Pageable pageable);
}
