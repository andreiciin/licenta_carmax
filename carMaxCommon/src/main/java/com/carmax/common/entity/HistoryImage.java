package com.carmax.common.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "history_images")
public class HistoryImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "history_id")
	private History history;

	public HistoryImage() {
	}

	public HistoryImage(Integer id, String name, History history) {
		this.id = id;
		this.name = name;
		this.history = history;
	}


	public HistoryImage(String name, History history) {
		this.name = name;
		this.history = history;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	@Transient
	public String getImagePath() {
		return "/history-images/" + history.getId() + "/extras/" + this.name;
	}

}