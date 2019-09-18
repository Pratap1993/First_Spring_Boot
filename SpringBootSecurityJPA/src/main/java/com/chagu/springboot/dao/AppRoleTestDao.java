package com.chagu.springboot.dao;

import org.springframework.data.repository.CrudRepository;

import com.chagu.springboot.model.AppUser;

import java.util.List;

public interface AppRoleTestDao extends CrudRepository<AppUser, Long> {

	List<AppUser> findByUserIdGreaterThan(Long l);
}
