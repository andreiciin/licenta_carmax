package com.carmax.common.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "history")
public class History {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 64, nullable = false, name = "vehicle")
	private String vehicle;

	private float mileage;

	@Column(length = 512, nullable = false, name = "service")
	private String service;

	@Column(length = 4096, nullable = false, name = "full_description")
	private String fullDescription;

	@Column(name = "created_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdTime;

	private boolean enabled;

	@Column(name = "main_image", nullable = false)
	private String mainImage;

	@OneToMany(mappedBy = "history", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<HistoryImage> images = new HashSet<>();

	@ManyToMany
	@JoinTable(
			name = "car_products",
			joinColumns = @JoinColumn(name = "car_id"),
			inverseJoinColumns = @JoinColumn(name = "product_id")
	)
	private Set<Product> products = new HashSet<>();

	public History(Integer id, String vehicle) {
		this.id = id;
		this.vehicle = vehicle;
	}

	public History() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public float getMileage() {
		return mileage;
	}

	public void setMileage(float mileage) {
		this.mileage = mileage;
	}
	@Override
	public String toString() {
		return "History [id=" + id + ", vehicle=" + vehicle + "]";
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public Set<HistoryImage> getImages() {
		return images;
	}

	public void setImages(Set<HistoryImage> images) {
		this.images = images;
	}

	public void addExtraImage(String imageName) {
		this.images.add(new HistoryImage(imageName, this));
	}

	@Transient
	public String getMainImagePath() {
		if (id == null || mainImage == null) return "/images/image-thumbnail.png";

		return "/history-images/" + this.id + "/" + this.mainImage;
	}

	public boolean containsImageName(String imageName) {
		Iterator<HistoryImage> iterator = images.iterator();

		while (iterator.hasNext()) {
			HistoryImage image = iterator.next();
			if (image.getName().equals(imageName)) {
				return true;
			}
		}

		return false;
	}

	@Transient
	public String getShortName() {
		if (vehicle.length() > 70) {
			return vehicle.substring(0, 70).concat("...");
		}
		return vehicle;
	}
}