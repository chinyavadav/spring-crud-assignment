package com.assignment.crud.controller;

import com.assignment.crud.common.PaginatedDto;
import com.assignment.crud.common.ResponseTemplate;
import com.assignment.crud.dal.entity.UserDetail;
import com.assignment.crud.dto.CreateUpdateUserDto;
import com.assignment.crud.dto.UserDetailDto;
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
    public ResponseTemplate<UserDetailDto> create(
            @ApiParam("CreateUpdateUserDto") @RequestBody CreateUpdateUserDto createUpdateUserDto) {
        UserDetailDto userDetailDto = userService.create(createUpdateUserDto);
        return new ResponseTemplate<>("success", "User successfully created!", userDetailDto);
    }

    @GetMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Finds User by User Id", response = ResponseTemplate.class)
    public ResponseTemplate<UserDetailDto> findById(@ApiParam("userId") @PathVariable UUID userId) {
        UserDetail userDetail = userService.findById(userId);
        if (userDetail != null) {
            return new ResponseTemplate<>(
                    "success",
                    "User has been found!",
                    userService.map(userDetail)
            );
        }
        throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/find",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Finds User by id, firstName or lastName", response = ResponseTemplate.class)
    public ResponseTemplate<List<UserDetailDto>> findById(
            @ApiParam(name = "value") @RequestParam(name = "value") String value) {
        List<UserDetailDto> userDetailDtoList = userService.findAllByIdOrFirstNameOrLastName(value)
                .stream()
                .map(userService::map)
                .collect(Collectors.toList());
        return new ResponseTemplate<>("success", null, userDetailDtoList);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Paginated Users List", response = PaginatedDto.class)
    public PaginatedDto<UserDetailDto> list(
            @ApiParam("page") @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @ApiParam("size") @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        PaginatedDto<UserDetail> userPaginatedDto = userService.getPaginated(page, size);
        return new PaginatedDto<>(
                userPaginatedDto.getData().stream().map(userService::map).collect(Collectors.toList()),
                userPaginatedDto.getLinks()
        );
    }

    @PutMapping(path = "/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Update User", response = ResponseTemplate.class)
    public ResponseTemplate<UserDetailDto> update(
            @ApiParam("userId") @PathVariable UUID userId,
            @ApiParam("CreateUpdateUserDto") @RequestBody CreateUpdateUserDto createUpdateUserDto) {
        UserDetailDto userDetailDto = userService.update(userId, createUpdateUserDto);
        return new ResponseTemplate<>("success", "User updated successfully!", userDetailDto);
    }


    @DeleteMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Delete new Book", response = ResponseTemplate.class)
    public ResponseTemplate<UserDetailDto> delete(@ApiParam("userId") @PathVariable UUID userId) {
        boolean deleted = userService.delete(userId);
        return new ResponseTemplate<>(deleted ? "success" : "failed",
                deleted ? "User successfully deleted!" : "User could not be deleted",
                null);
    }
}
