package com.example.demo.controller;

import com.example.demo.controller.request.ProgramRequest;
import com.example.demo.controller.response.ProgramResponse;
import com.example.demo.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProgramController {

    private final ProgramService programService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PROGRAM')")
    public ResponseEntity<ProgramResponse> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(programService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_PROGRAM')")
    public ResponseEntity<List<ProgramResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(programService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PROGRAM')")
    public ResponseEntity<ProgramResponse> create(@RequestBody ProgramRequest programRequest) {
        return new ResponseEntity<>(programService.save(programRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PROGRAM')")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody ProgramRequest programRequest) {
        programService.update(id, programRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_PROGRAM')")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        programService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
