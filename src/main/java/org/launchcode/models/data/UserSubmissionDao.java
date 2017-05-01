package org.launchcode.models.data;

import org.launchcode.models.UserSubmission;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserSubmissionDao extends PagingAndSortingRepository<UserSubmission, Integer> {
}
