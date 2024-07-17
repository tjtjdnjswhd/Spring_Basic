package com.example.restapibasic.controller;

import com.example.restapibasic.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final List<Member> members = new ArrayList<>();
    private long nextId = 1;

    @GetMapping
    public List<Member> getMembers() {
        return members;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable(name = "id") Long id) {
        Member foundMember = members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst()
                .orElse(null);

        return foundMember == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(foundMember);
    }

    @PostMapping
    public Member createMember(@RequestBody Member member) {
        member.setId(nextId++);
        members.add(member);
        return member;
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable(name = "id") Long id, @RequestBody Member member) {
        Member foundMember = members.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());

        foundMember.setName(member.getName());
        foundMember.setEmail(member.getEmail());
        return foundMember;
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable(name = "id") Long id) {
        Member foundMember = members.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());

        members.remove(foundMember);
    }
}
