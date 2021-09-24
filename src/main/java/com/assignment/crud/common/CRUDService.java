package com.assignment.crud.common;

import java.util.List;
import java.util.UUID;

public interface CRUDService<T> {
    T saveOrUpdate(T object);

    T findById(UUID id);

    PaginatedDto<T> getPaginated(int page, int size);

    boolean delete(UUID id);
}
