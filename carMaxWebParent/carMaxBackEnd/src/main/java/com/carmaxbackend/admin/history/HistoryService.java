package com.carmaxbackend.admin.history;

import com.carmax.common.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

	@Autowired
	private HistoryRepository repo;

	public List<History> listAll() { return (List<History>) repo.findAll(); }
}