package com.mahaexam.common.controller;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Topic;
import com.mahaexam.common.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/topics")
public class TopicController extends BaseController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<Topic>> getAll(
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer classId,
            @RequestParam(required = false) Integer subjectId,
            @RequestParam(required = false) Integer chapterId) {
        UserBean user = getUser();

        // If all filters are provided, use filtered search
        if (boardId != null && classId != null && subjectId != null && chapterId != null) {
            return ResponseEntity.ok(topicService.getTopics(boardId, classId, subjectId, chapterId, user));
        }

        // Otherwise return all topics for the user
        return ResponseEntity.ok(topicService.findAll(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getById(@PathVariable int id) {
        Optional<Topic> topic = topicService.findById(id);
        return topic.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Topic> create(@RequestBody Topic topic) {
        UserBean user = getUser();
        topic.setTenantId(user.getTenantId());
        topic.setCreatedBy(user.getUserId().intValue());
        Topic created = topicService.create(topic);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> update(@PathVariable int id, @RequestBody Topic topic) {
        UserBean user = getUser();
        topic.setUpdatedBy(user.getUserId().intValue());
        Topic updated = topicService.update(id, topic);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        topicService.delete(id);
        return ResponseEntity.ok().build();
    }
}
