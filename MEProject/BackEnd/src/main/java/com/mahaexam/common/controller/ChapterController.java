package com.mahaexam.common.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.ChapterBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Chapter;
import com.mahaexam.common.service.ChapterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/chapter")
@Tag(name = "Class API", description = "Operations related to classes")
public class ChapterController  extends BaseController {

    @Autowired
    private ChapterService service;

    @Operation(summary = "Create a new chapter")
    @PostMapping
    public int create(@RequestBody ChapterBean bean) {
        UserBean user = getUser();
    	Chapter chapter = new Chapter();
    	chapter.setChapterName(bean.getChapterName());
        chapter.setUnit(bean.getUnit());
        chapter.setStatus("Active");
    	chapter.setSubjectId(bean.getSubjectId());
    	chapter.setClassName(bean.getClassName());
    	chapter.setExamType(bean.getExamType());
        chapter.setTenantId(user.getTenantId());
        chapter.setBoaredIds(bean.getBoaredIds());
        chapter.setClassIds(bean.getClassIds());
        return service.save(chapter);
    }

    @Operation(summary = "Get all chapters")
    @GetMapping
    public List<Chapter> getAll(@RequestParam(required = false) Integer boardId,
                                @RequestParam(required = false) Integer classId,
                                @RequestParam(required = false) Integer subjectId,
                                @RequestParam(required = false) String medium) {
    	UserBean user = getUser();
        Long tenantId = Objects.nonNull(user)? user.getTenantId().longValue():getCurrentTenantId();

        if (boardId != null && classId != null && subjectId != null) {
            if (medium != null && !medium.isBlank()) {
                return service.getChaptersByBoardClassSubjectAndMedium(boardId, classId, subjectId, medium, tenantId);
            }
            // If medium not provided, fall back to returning all chapters for tenant or we could implement another method.
        }
        return service.getAllChapterByTenant(user);
    }

    @Operation(summary = "Get a chapter by ID")
    @GetMapping("/{id}")
    public Chapter getById(@PathVariable int id) {
        return service.getChapterById(id);
    }
    

    @Operation(summary = "Get a chapter by Subject ID")
    @GetMapping("/subject/{id}")
    public Chapter getBySubjectId(@PathVariable int id) {
        return service.getBySubjectId(id);
    }

    @Operation(summary = "Update a chapter")
    @PutMapping("/{id}")
    public int update(@PathVariable int id, @RequestBody ChapterBean bean) {
    	Chapter chapter = new Chapter();
    	chapter.setId(id);
        chapter.setChapterName(bean.getChapterName());
        chapter.setUnit(bean.getUnit());
        chapter.setStatus("Active");
        chapter.setSubjectId(bean.getSubjectId());
        chapter.setClassName(bean.getClassName());
        chapter.setExamType(bean.getExamType());
        chapter.setBoaredIds(bean.getBoaredIds());
        chapter.setClassIds(bean.getClassIds());
        return service.updateChapter(chapter);
    }

    @Operation(summary = "Delete a chapter")
    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        return service.deleteChapter(id);
    }
}