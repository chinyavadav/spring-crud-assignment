package com.assignment.crud.service;

import com.assignment.crud.common.CRUDService;
import com.assignment.crud.common.PaginatedDto;
import com.assignment.crud.common.PaginationLink;
import com.assignment.crud.dal.entity.UserDetail;
import com.assignment.crud.dto.CreateUpdateUserDto;
import com.assignment.crud.dto.UserDetailDto;
import com.assignment.crud.dal.repo.UserRepository;
import com.assignment.crud.exception.CustomException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements CRUDService<UserDetail> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDetail saveOrUpdate(UserDetail userDetail) {
        return userRepository.save(userDetail);
    }

    @Override
    public UserDetail findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<UserDetail> findAllByIdOrFirstNameOrLastName(String value) {
        UUID id = null;
        String firstName = value, lastName = value;
        try {
            id = UUID.fromString(value);
        } catch (Exception ignore) {
        }
        return userRepository.findAllByIdOrFirstNameOrLastName(id, firstName, lastName);
    }

    @Override
    public PaginatedDto<UserDetail> getPaginated(int page, int size) {
        if (page >= 1 && size > 1) {
            Pageable pageRequest = PageRequest.of(page - 1, size);
            Page<UserDetail> userPage = userRepository.findAll(pageRequest);
            PaginationLink paginationLink = new PaginationLink(userPage.getTotalPages(),
                    userPage.getTotalElements(), page);
            return new PaginatedDto<>(userPage.getContent(), paginationLink);
        }
        throw new CustomException("Page/Size index must not be less than one!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean delete(UUID userId) {
        UserDetail userDetail = findById(userId);
        if (userDetail != null) {
            userDetail.setDeletedAt(ZonedDateTime.now());
            userDetail = saveOrUpdate(userDetail);
            LOGGER.info("DELETED {}", userDetail.toString());
            return true;
        }
        return false;
    }

    public UserDetailDto map(UserDetail userDetail) {
        return modelMapper.map(userDetail, UserDetailDto.class);
    }

    public UserDetail map(UserDetailDto userDetailDto) {
        return modelMapper.map(userDetailDto, UserDetail.class);
    }

    public UserDetailDto create(CreateUpdateUserDto createUserDto) {
        UserDetail userDetail = modelMapper.map(createUserDto, UserDetail.class);
        userDetail = saveOrUpdate(userDetail);
        LOGGER.info("CREATED {}", userDetail.toString());
        return map(userDetail);
    }

    public UserDetailDto update(UUID userId, CreateUpdateUserDto createUserDto) {
        UserDetail userDetail = findById(userId);
        if (userDetail != null) {
            userDetail.setTitle(createUserDto.getTitle());
            userDetail.setJobTitle(createUserDto.getJobTitle());
            userDetail.setFirstName(createUserDto.getFirstName());
            userDetail.setLastName(createUserDto.getLastName());
            userDetail.setDob(createUserDto.getDob());
            userDetail = saveOrUpdate(userDetail);
            LOGGER.info("UPDATED {}", userDetail.toString());
            return map(userDetail);
        }
        throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);
    }
}
