package com.mahaexam.common.service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicService {
    List<Topic> findAll(UserBean user);

    Optional<Topic> findById(int id);

    Topic create(Topic topic);

    Topic update(int id, Topic topic);

    void delete(int id);

    List<Topic> getTopics(Integer boardId, Integer classId, Integer subjectId, Integer chapterId, UserBean user);
}
