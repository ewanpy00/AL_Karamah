package com.school.aet.group;

import com.school.aet.group.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupSummaryDto>> getGroups(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) UUID focusDomainId,
            @RequestParam(required = false) Integer ageMin,
            @RequestParam(required = false) Integer ageMax) {
        return ResponseEntity.ok(groupService.getAllGroups(active, focusDomainId, ageMin, ageMax));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDetailDto> getGroupById(@PathVariable UUID id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        return ResponseEntity.ok(groupService.createGroup(group));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable UUID id, @RequestBody UpdateGroupRequest request) {
        return ResponseEntity.ok(groupService.updateGroup(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{groupId}/students")
    public ResponseEntity<AddStudentsToGroupResponse> addStudentsToGroup(
            @PathVariable UUID groupId,
            @RequestBody AddStudentsToGroupRequest request) {
        return ResponseEntity.ok(groupService.addStudentsToGroup(groupId, request.getStudentIds()));
    }

    @DeleteMapping("/{groupId}/students/{studentId}")
    public ResponseEntity<Void> removeStudentFromGroup(
            @PathVariable UUID groupId,
            @PathVariable UUID studentId) {
        groupService.removeStudentFromGroup(groupId, studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/compatibility")
    public ResponseEntity<GroupDetailDto.CompatibilitySummaryDto> getGroupCompatibility(@PathVariable UUID id) {
        return ResponseEntity.ok(groupService.calculateCompatibility(id));
    }
}
