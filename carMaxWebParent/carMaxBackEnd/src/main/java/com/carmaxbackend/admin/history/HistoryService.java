package com.carmaxbackend.admin.history;

import com.carmax.common.entity.History;
import com.carmax.common.exception.HistoryNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HistoryService {

	public static final int HISTORY_PER_PAGE = 10;
	@Autowired
	private HistoryRepository repo;

	public List<History> listAll() { return (List<History>) repo.findAll(); }

	public History save(History history) {

		return repo.save(history);
	}

	public void updateHistoryEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}

	public void delete(Integer id) throws HistoryNotFoundException {
		Long countById = repo.countById(id);

		if (countById == null || countById == 0) {
			throw new HistoryNotFoundException("Could not find any history with ID " + id);
		}

		repo.deleteById(id);
	}
}
