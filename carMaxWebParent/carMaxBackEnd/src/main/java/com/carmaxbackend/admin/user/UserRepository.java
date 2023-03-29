package com.carmaxbackend.admin.user;

import com.carmax.common.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
