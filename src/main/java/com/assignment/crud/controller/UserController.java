package com.assignment.crud.controller;

import com.assignment.crud.common.PaginatedDto;
import com.assignment.crud.common.ResponseTemplate;
import com.assignment.crud.dal.entity.User;
import com.assignment.crud.dto.CreateUpdateUserDto;
import com.assignment.crud.dto.UserDto;
import com.assignment.crud.exception.CustomException;
import com.assignment.crud.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Api(tags = "user")
@Validated
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Create new User", response = ResponseTemplate.class)
    public ResponseTemplate<UserDto> create(
            @ApiParam("CreateUpdateUserDto") @RequestBody CreateUpdateUserDto createUpdateUserDto) {
        UserDto userDto = userService.create(createUpdateUserDto);
        return new ResponseTemplate<>("success", "User successfully created!", userDto);
    }

    @GetMapping(path = "/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Finds User by User Id", response = ResponseTemplate.class)
    public ResponseTemplate<UserDto> findById(@ApiParam("userId") @PathVariable UUID userId) {
        User user = userService.findById(userId);
        if (user != null) {
            return new ResponseTemplate<>(
                    "success",
                    "User updated successfully!",
                    userService.map(user)
            );
        }
        throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/find",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Finds User by User Id", response = ResponseTemplate.class)
    public ResponseTemplate<List<UserDto>> findById(
            @ApiParam("field") @RequestParam(name = "field") String field,
            @ApiParam("value") @RequestParam(name = "value") Object value) {
        List<UserDto> userDtoList = userService.findByField(field, value)
                .stream()
                .map(userService::map)
                .collect(Collectors.toList());
        return new ResponseTemplate<>("success", null, userDtoList);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Paginated Users List", response = PaginatedDto.class)
    public PaginatedDto<UserDto> list(
            @ApiParam("page") @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @ApiParam("size") @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        PaginatedDto<User> userPaginatedDto = userService.getPaginated(page, size);
        return new PaginatedDto<>(
                userPaginatedDto.getObjects().stream().map(userService::map).collect(Collectors.toList()),
                userPaginatedDto.getLinks()
        );
    }

    @PutMapping(path = "/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Update User", response = ResponseTemplate.class)
    public ResponseTemplate<UserDto> update(
            @ApiParam("userId") @PathVariable UUID userId,
            @ApiParam("CreateUpdateUserDto") @RequestBody CreateUpdateUserDto createUpdateUserDto) {
        UserDto userDto = userService.update(userId, createUpdateUserDto);
        return new ResponseTemplate<>("success", "User updated successfully!", userDto);
    }


    @DeleteMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Delete new Book", response = ResponseTemplate.class)
    public ResponseTemplate<UserDto> delete(@ApiParam("userId") @PathVariable UUID userId) {
        boolean deleted = userService.delete(userId);
        return new ResponseTemplate<>(deleted ? "success" : "failed",
                deleted ? "User successfully deleted!" : "User could not be deleted",
                null);
    }
}
