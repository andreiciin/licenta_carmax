package com.carmaxbackend.admin.history;

import com.carmax.common.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HistoryService {

	public static final int HISTORY_PER_PAGE = 10;
	@Autowired
	private HistoryRepository repo;

	public List<History> listAll() { return (List<History>) repo.findAll(); }

	public History save(History history) {

		return repo.save(history);
	}
}
