package com.mahaexam.common.service;

import java.util.List;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.repo.ChapterBoardClassMappingRepository;
import org.springframework.stereotype.Service;

import com.mahaexam.common.model.Chapter;
import com.mahaexam.common.repo.ChapterRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChapterServiceImpl implements ChapterService {

	private final ChapterRepository chapterRepository;
    private final ChapterBoardClassMappingRepository chapterBoardClassMappingRepository;

	public ChapterServiceImpl(ChapterRepository chapterRepository, ChapterBoardClassMappingRepository chapterBoardClassMappingRepository) {
		this.chapterRepository = chapterRepository;
        this.chapterBoardClassMappingRepository = chapterBoardClassMappingRepository;
	}

	@Override
	public List<Chapter> getAllChapterByTenant(UserBean user) {
		return chapterRepository.findAll(user);
	}

	@Override
	public Chapter getChapterById(int id) {
        Chapter chapter = chapterRepository.findById(id);
        if (chapter != null) {
            // Fetch board and class mappings for this chapter
            List<com.mahaexam.common.model.ChapterBoardClassMapping> mappings =
                chapterBoardClassMappingRepository.findByChapterIds(List.of(id));

            if (mappings != null && !mappings.isEmpty()) {
                // Extract unique boardIds and classIds
                List<Long> boardIds = mappings.stream()
                    .map(m -> Long.valueOf(m.getBoardId()))
                    .distinct()
                    .collect(java.util.stream.Collectors.toList());

                List<Long> classIds = mappings.stream()
                    .map(m -> Long.valueOf(m.getClassId()))
                    .distinct()
                    .collect(java.util.stream.Collectors.toList());

                chapter.setBoaredIds(boardIds);
                chapter.setClassIds(classIds);
            }
        }
        return chapter;
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public int save(Chapter chapter) {
        // Check uniqueness for each classId
        if (chapter.getClassIds() != null) {
            for (Long classId : chapter.getClassIds()) {
                boolean isExists = chapterRepository.existsByChapterName(
                    chapter.getChapterName(),
                    chapter.getSubjectId(),
                    classId.intValue(),
                    -1 // -1 for new chapter (not updating)
                );
                if (isExists) {
                    throw new IllegalArgumentException("Chapter is already added for this Subject and Class");
                }
            }
        }
        Chapter save = chapterRepository.save(chapter);
        chapterBoardClassMappingRepository.save(save.getId(),chapter.getBoaredIds(),  chapter.getClassIds());
        return save.getId();
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public int updateChapter(Chapter chapter) {
        if (chapter.getClassIds() != null) {
            for (Long classId : chapter.getClassIds()) {
                boolean isExists = chapterRepository.existsByChapterName(
                    chapter.getChapterName(),
                    chapter.getSubjectId(),
                    classId.intValue(),
                    chapter.getId()
                );
                if (isExists) {
                    throw new ValidationException("Chapter name already exists for this Subject and Class.");
                }
            }
        }
        Chapter update = chapterRepository.update(chapter);
        // Refresh mappings: delete existing then insert new combinations of boardIds x classIds
        chapterBoardClassMappingRepository.deleteByChapterId(update.getId());
        if (chapter.getBoaredIds() != null && !chapter.getBoaredIds().isEmpty()
                && chapter.getClassIds() != null && !chapter.getClassIds().isEmpty()) {
            chapterBoardClassMappingRepository.save(update.getId(), chapter.getBoaredIds(), chapter.getClassIds());
        }
        return update.getId();
	}

	@Override
	public int deleteChapter(int id) {
		return chapterRepository.softDelete(id);
	}

	@Override
	public Chapter getBySubjectId(int subjectId) {
		return chapterRepository.getBySubjectId(subjectId);
	}

    @Override
    public List<Chapter> getChaptersByBoardClassSubjectAndMedium(Integer boardId, Integer classId, Integer subjectId, String medium, Long tenantId) {
        return chapterRepository.findByBoardClassSubjectAndMedium(boardId, classId, subjectId, medium, tenantId);
    }

}
