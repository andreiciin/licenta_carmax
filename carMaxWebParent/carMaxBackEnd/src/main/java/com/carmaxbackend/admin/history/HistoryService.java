package com.carmaxbackend.admin.history;

import com.carmax.common.entity.History;
import com.carmax.common.exception.HistoryNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class HistoryService {

	public static final int HISTORY_PER_PAGE = 6;
	@Autowired
	private HistoryRepository repo;

	public List<History> listAll() { return (List<History>) repo.findAll(); }

	public Page<History> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, HISTORY_PER_PAGE, sort);

		if (keyword != null) {
			return repo.findAll(keyword, pageable);
		}

		return repo.findAll(pageable);
	}

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

	public History get(Integer id) throws HistoryNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new HistoryNotFoundException("Could not find any history with ID " + id);
		}
	}
}
