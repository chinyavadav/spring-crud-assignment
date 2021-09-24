package com.assignment.crud.dal.repo;

import com.assignment.crud.dal.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserDetail, UUID> {
    List<UserDetail> findAllByIdOrFirstNameOrLastName(UUID id, String firstName, String lastName);
}
