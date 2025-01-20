package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.LeaveRequest;
import org.example.entity.LeaveRequestEntity;
import org.example.exception.LeaveRequestException;
import org.example.repository.LeaveRequestRepository;
import org.example.service.LeaveRequestService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository repository;
    private final ModelMapper mapper;
    private static final String NOT_FOUND_MESSAGE = "Leave Request not found with ID: ";

    @Override
    public LeaveRequest persist(LeaveRequest leaveRequest) {
        try {
            LeaveRequestEntity save = repository.save(mapper.map(leaveRequest, LeaveRequestEntity.class));
            return mapper.map(save, LeaveRequest.class);
        } catch (DataAccessException e) {
            log.error("Failed to persist leave request: {}", leaveRequest, e);
            throw new LeaveRequestException("Failed to save leave request to database");
        } catch (MappingException e) {
            log.error("Error mapping leave request object: {}", leaveRequest, e);
            throw new LeaveRequestException("Failed to convert leave request data");
        }
    }

    @Override
    public List<LeaveRequest> retrieveAll() {
        try {
            List<LeaveRequest> list = new ArrayList<>();
            List<LeaveRequestEntity> all = repository.findAll();

            if (all.isEmpty()) {
                throw new LeaveRequestException("Leave Requests Not Found");
            }
            all.forEach(item -> list.add(mapper.map(item, LeaveRequest.class)));

            return list;
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving leave requests", dae);
            throw new LeaveRequestException("Unable to retrieve leave requests");
        } catch (MappingException me) {
            log.error("Error mapping leave request records", me);
            throw new LeaveRequestException("Unable to process leave request records");
        }
    }

    @Override
    public LeaveRequest retrieveById(Long id) {
        try {
            LeaveRequestEntity leaveRequest = repository.findById(id)
                    .orElseThrow(() -> new LeaveRequestException(NOT_FOUND_MESSAGE + id));

            return mapper.map(leaveRequest, LeaveRequest.class);
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving leave request", dae);
            throw new LeaveRequestException("Unable to retrieve leave request");
        } catch (MappingException me) {
            log.error("Error mapping leave request", me);
            throw new LeaveRequestException("Unable to process leave request");
        }
    }

    @Override
    public LeaveRequest update(Long id, LeaveRequest leaveRequestDetails) {
        try {
            LeaveRequestEntity existingLeaveRequest = repository.findById(id)
                    .orElseThrow(() -> new LeaveRequestException(NOT_FOUND_MESSAGE + id));

            mapper.map(leaveRequestDetails, existingLeaveRequest);

            LeaveRequestEntity updatedLeaveRequest = repository.save(existingLeaveRequest);
            return mapper.map(updatedLeaveRequest, LeaveRequest.class);
        } catch (DataAccessException dae) {
            log.error("Database error while updating leave request with ID: {}", id, dae);
            throw new LeaveRequestException("Unable to update leave request");
        } catch (MappingException me) {
            log.error("Error mapping leave request details for update", me);
            throw new LeaveRequestException("Failed to update leave request data");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            LeaveRequestEntity leaveRequest = repository.findById(id)
                    .orElseThrow(() -> new LeaveRequestException(NOT_FOUND_MESSAGE + id));

            repository.delete(leaveRequest);
            log.info("Leave request with ID {} deleted successfully", id);
        } catch (DataAccessException dae) {
            log.error("Database error while deleting leave request with ID: {}", id, dae);
            throw new LeaveRequestException("Unable to delete leave request");
        }
    }
}
