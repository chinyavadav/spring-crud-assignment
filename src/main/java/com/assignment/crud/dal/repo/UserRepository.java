package com.assignment.crud.dal.repo;

import com.assignment.crud.dal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE ?1 = ?2")
    List<User> findUserByField(String field, Object value);
}
