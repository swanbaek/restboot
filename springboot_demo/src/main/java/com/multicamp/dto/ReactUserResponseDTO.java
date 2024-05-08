package com.multicamp.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//응답시 사용할 DTO
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactUserResponseDTO {
	private String accessToken;
    private String refreshToken;

    private Long userIdx;
    private String nickname;

}
