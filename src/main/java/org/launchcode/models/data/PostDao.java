package org.launchcode.models.data;

import org.launchcode.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PostDao extends CrudRepository<Post, Integer> {
}
