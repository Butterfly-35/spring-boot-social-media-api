package com.sha.insta.payload;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDTO {
	
	private long id;
	
	@Schema(description = "Insta Post title")
	@NotEmpty
	@Size(min=2, message = "Post title should be at least 2 characters")
	private String title;
	
	@Schema(description = "Insta Post description")
	@NotEmpty
	private String description;
	
	@Schema(description = "Insta Post content")
	@NotEmpty
	@Size(min=10, message = "Post content should be at least 10 characters")
	private String content;
	
	private Set<CommentDto> comments;
	
	@Schema(description = " Post Category")
	private Long categoryId;
	
	

}
