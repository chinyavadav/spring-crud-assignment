package com.assignment.crud.service;

import com.assignment.crud.common.CRUDService;
import com.assignment.crud.common.PaginatedDto;
import com.assignment.crud.common.PaginationLink;
import com.assignment.crud.dto.CreateUpdateUserDto;
import com.assignment.crud.dto.UserDto;
import com.assignment.crud.dal.entity.User;
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
public class UserServiceImpl implements CRUDService<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findByField(String field, Object value) {
        return userRepository.findUserByField(field, value);
    }

    @Override
    public PaginatedDto<User> getPaginated(int page, int size) {
        if (page >= 1 && size > 1) {
            Pageable pageRequest = PageRequest.of(page - 1, size);
            Page<User> userPage = userRepository.findAll(pageRequest);
            PaginationLink paginationLink = new PaginationLink(userPage.getTotalPages(),
                    userPage.getTotalElements(), page);
            return new PaginatedDto<>(userPage.getContent(), paginationLink);
        }
        throw new CustomException("Page/Size index must not be less than one!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean delete(UUID userId) {
        User user = findById(userId);
        if (user != null) {
            user.setDeletedAt(ZonedDateTime.now());
            user = saveOrUpdate(user);
            LOGGER.info("DELETED %s", user.toString());
            return true;
        }
        return false;
    }

    public UserDto map(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User map(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserDto create(CreateUpdateUserDto createUserDto) {
        User user = modelMapper.map(createUserDto, User.class);
        user = saveOrUpdate(user);
        LOGGER.info("CREATED %s", user.toString());
        return map(user);
    }

    public UserDto update(UUID userId, CreateUpdateUserDto createUserDto) {
        User user = findById(userId);
        if (user != null) {
            user = modelMapper.map(createUserDto, User.class);
            user = saveOrUpdate(user);
            LOGGER.info("UPDATED %s", user.toString());
            return map(user);
        }
        throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);
    }
}
