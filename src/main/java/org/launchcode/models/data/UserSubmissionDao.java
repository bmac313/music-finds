package org.launchcode.models.data;

import org.launchcode.models.UserSubmission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserSubmissionDao extends PagingAndSortingRepository<UserSubmission, Integer> {
}
