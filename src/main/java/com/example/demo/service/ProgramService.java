package com.example.demo.service;

import com.example.demo.controller.request.ProgramRequest;
import com.example.demo.controller.response.ProgramResponse;
import com.example.demo.entity.Program;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.ProgramMapper;
import com.example.demo.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProgramService {

    private final ProgramRepository programRepository;

    private final ProgramMapper programMapper;

    @Transactional(readOnly = true)
    public ProgramResponse findById(Long id) {
        Program user = programRepository.findById(id).orElseThrow(() -> new CustomException("There is no such program", HttpStatus.NOT_FOUND));
        return programMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public List<ProgramResponse> findAll(Pageable pageable) {
        try {
            return programRepository.findAll(pageable).map(programMapper::toDto).toList();
        } catch (Exception ex) {
            throw new CustomException("Error during finding all programs", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ProgramResponse save(ProgramRequest programRequest) {
        try {
            Program entity = programMapper.toEntity(programRequest);
            return programMapper.toDto(programRepository.save(entity));
        } catch (Exception ex) {
            throw new CustomException("Error during saving program", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void update(Long id, ProgramRequest programRequest) {
        try {
            Program entity = programRepository.findById(id).orElseThrow(() -> new CustomException("There is no such user", HttpStatus.NOT_FOUND));
            Program program = programMapper.toEntity(programRequest);
            updateProgram(entity, program);
            programRepository.save(entity);
        } catch (Exception ex) {
            throw new CustomException("Error during updating user", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            programRepository.deleteById(id);
        } catch (Exception ex) {
            throw new CustomException("Error during updating user", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateProgram(Program program, Program mappedProgram) {
        program.setName(mappedProgram.getName());
        program.setParent(mappedProgram.getParent());
    }

}
