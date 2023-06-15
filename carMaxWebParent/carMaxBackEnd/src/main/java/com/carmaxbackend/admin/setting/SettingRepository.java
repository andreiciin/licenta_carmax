package com.carmaxbackend.admin.setting;

import com.carmax.common.entity.Setting;
import com.carmax.common.entity.SettingCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SettingRepository extends CrudRepository<Setting, String> {
	public List<Setting> findByCategory(SettingCategory category);
}
