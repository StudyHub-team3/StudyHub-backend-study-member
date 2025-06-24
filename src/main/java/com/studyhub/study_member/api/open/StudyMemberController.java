package com.studyhub.study_member.api.open;

import com.studyhub.study_member.common.dto.ApiResponseDto;
import com.studyhub.study_member.common.web.context.GatewayRequestHeaderUtils;
import com.studyhub.study_member.domain.dto.StudyMemberRequestJoinDto;
import com.studyhub.study_member.domain.dto.StudyMemberResponseDto;
import com.studyhub.study_member.service.StudyMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/study-members", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class StudyMemberController {
    private final StudyMemberService studyMemberService;

    @PostMapping("/join")
    public ApiResponseDto<String> requestJoin(@RequestBody @Valid StudyMemberRequestJoinDto requestJoinDto) {
        Long userId = GatewayRequestHeaderUtils.getUserIdOrThrowException();
        String userName = GatewayRequestHeaderUtils.getCUserNameOrThrowException();

        studyMemberService.requestJoin(requestJoinDto, userId, userName);

        return ApiResponseDto.defaultOk();
    }

    @GetMapping("/members/{studyId}")
    public ApiResponseDto<List<StudyMemberResponseDto>> getMembers(@PathVariable Long studyId) {
        return ApiResponseDto.createOk(studyMemberService.getAcceptedMembers(studyId));
    }

    @DeleteMapping("/members/{studyId}")
    public ApiResponseDto<String> leaveStudy(@PathVariable Long studyId) {
        Long userId = GatewayRequestHeaderUtils.getUserIdOrThrowException();

        studyMemberService.leaveStudy(studyId, userId);

        return ApiResponseDto.defaultOk();
    }

    @GetMapping("/requests/{studyId}")
    public ApiResponseDto<List<StudyMemberResponseDto>> getPendingRequests(@PathVariable Long studyId) {
        return ApiResponseDto.createOk(studyMemberService.getPendingRequests(studyId));
    }

    @PatchMapping("/requests/{memberId}")
    public ApiResponseDto<String> acceptRequest(@PathVariable Long memberId) {
        studyMemberService.acceptJoinRequest(memberId);

        return ApiResponseDto.defaultOk();
    }

    @DeleteMapping("/requests/{memberId}")
    public ApiResponseDto<String> rejectRequest(@PathVariable Long memberId) {
        studyMemberService.rejectJoinRequest(memberId);

        return ApiResponseDto.defaultOk();
    }
}
