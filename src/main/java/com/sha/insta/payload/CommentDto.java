package com.sha.insta.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
	private long id;
	
	@NotEmpty(message = "Name should be not empty")
	private String name;
	
	@NotEmpty(message = "Email should be not empty")
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 10, message = "Comment must be min 10 characters")
	private String body;

}
