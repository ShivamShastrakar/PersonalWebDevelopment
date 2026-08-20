package com.mahaexam.common.service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Topic;
import com.mahaexam.common.repo.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public List<Topic> findAll(UserBean user) {
        return topicRepository.findAll(user);
    }

    @Override
    public Optional<Topic> findById(int id) {
        return topicRepository.findById(id);
    }

    @Override
    public Topic create(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Topic update(int id, Topic topic) {
        topic.setTopicId(id); // Ensure ID matches for update
        return topicRepository.update(topic);
    }

    @Override
    public void delete(int id) {
        topicRepository.delete(id);
    }

    @Override
    public List<Topic> getTopics(Integer boardId, Integer classId, Integer subjectId, Integer chapterId, UserBean user) {
        return topicRepository.findByBoardClassSubjectAndChapter(boardId, classId, subjectId, chapterId, user.getTenantId());
    }
}
