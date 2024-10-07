package com.Lupus.demo.repository;

import com.Lupus.demo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserInterface extends CrudRepository<User, Long> {
    User findByUserId(Long userId);
}
