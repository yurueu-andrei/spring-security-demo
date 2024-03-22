package com.example.demo.service;

import com.example.demo.controller.request.ProgramRequest;
import com.example.demo.controller.response.ProgramResponse;
import com.example.demo.entity.Program;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.ProgramMapper;
import com.example.demo.repository.ProgramRepository;
import com.example.demo.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ProgramMapper programMapper;

    @Transactional(readOnly = true)
    public ProgramResponse findById(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new CustomException("There is no such program", HttpStatus.NOT_FOUND));
        return programMapper.toDto(program);
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

    public boolean isAllowedToRead(String username, Long id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new CustomException("There is no such program", HttpStatus.NOT_FOUND))
                .getUsers().stream()
                .anyMatch(u -> u.getUsername().equals(username));

    }

    public boolean isAllowedToReadAll(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("There is no such user", HttpStatus.NOT_FOUND))
                .getRoles().stream()
                .anyMatch(r -> r.getName().equals("Министерство экономики"));

    }

    public boolean isAllowedToCreate(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("There is no such user", HttpStatus.NOT_FOUND))
                .getRoles().stream()
                .anyMatch(r -> r.getName().equals("Министерство экономики"));

    }

    public boolean isAllowedToUpdate(String username, Long id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new CustomException("There is no such program", HttpStatus.NOT_FOUND))
                .getUsers().stream()
                .anyMatch(u -> u.getUsername().equals(username) && u.getRoles().stream()
                        .anyMatch(r -> r.getName().equals("Ответственный заказчик")));

    }

    public boolean isAllowedToDelete(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("There is no such user", HttpStatus.NOT_FOUND))
                .getRoles().stream()
                .anyMatch(r -> r.getName().equals("Министерство экономики"));

    }

}
