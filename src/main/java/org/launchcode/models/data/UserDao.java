package org.launchcode.models.data;

import org.launchcode.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<User, Integer> {
}
