package org.launchcode.models.data;

import org.launchcode.models.Discussion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DiscussionDao extends PagingAndSortingRepository<Discussion, Integer> {
}
