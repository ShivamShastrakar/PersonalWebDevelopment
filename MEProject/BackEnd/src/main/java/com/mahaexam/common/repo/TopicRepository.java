package com.mahaexam.common.repo;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicRepository {
    List<Topic> findAll(UserBean user);

    Optional<Topic> findById(int id);

    Topic save(Topic topic);

    Topic update(Topic topic);

    void delete(int id); // Soft delete via deleted_at

    List<Topic> findByBoardClassSubjectAndChapter(Integer boardId, Integer classId, Integer subjectId, Integer chapterId, Long tenantId);
}

