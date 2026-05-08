package com.thenocturn.pos.dto;

import com.thenocturn.pos.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
	private String username;
    private String email;
    private Role role;

}
