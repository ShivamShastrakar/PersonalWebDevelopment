package com.mahaexam.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
	 private int id;
	 private Long tenantId;
	 private String boardName;
	 private LocalDateTime createdAt;
	 private LocalDateTime updatedAt;
	 private LocalDateTime deletedAt;
	 private String deleted;

     // List of state IDs associated with this board
     private List<Integer> stateIds;
}