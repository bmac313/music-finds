package org.launchcode.models.data;

import org.launchcode.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDao extends PagingAndSortingRepository<User, Integer> {
}
