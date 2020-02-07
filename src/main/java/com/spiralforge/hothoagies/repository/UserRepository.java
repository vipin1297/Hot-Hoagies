package com.spiralforge.hothoagies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.hothoagies.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findBymobileNumber(Long mobileNumber);

}
