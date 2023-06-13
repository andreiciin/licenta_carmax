package com.carmaxbackend.admin.setting;

import com.carmax.common.entity.Setting;
import org.springframework.data.repository.CrudRepository;

public interface SettingRepository extends CrudRepository<Setting, String> {

}
