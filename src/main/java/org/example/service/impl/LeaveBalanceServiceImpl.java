package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.LeaveBalance;
import org.example.entity.LeaveBalanceEntity;
import org.example.exception.LeaveBalanceException;
import org.example.repository.LeaveBalanceRepository;
import org.example.service.LeaveBalanceService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    private final LeaveBalanceRepository repository;
    private final ModelMapper mapper;
    private static final String NOT_FOUND_MESSAGE = "Leave balance not found with ID: ";

    @Override
    public LeaveBalance persist(LeaveBalance leaveBalance) {
        try {
            LeaveBalanceEntity save = repository.save(mapper.map(leaveBalance, LeaveBalanceEntity.class));
            return mapper.map(save, LeaveBalance.class);
        } catch (DataAccessException e) {
            log.error("Failed to persist leave balance: {}", leaveBalance, e);
            throw new LeaveBalanceException("Failed to save leave balance to database");
        } catch (MappingException e) {
            log.error("Error mapping leave balance object: {}", leaveBalance, e);
            throw new LeaveBalanceException("Failed to convert leave balance data");
        }
    }

    @Override
    public List<LeaveBalance> retrieveAll() {
        try {
            List<LeaveBalance> list = new ArrayList<>();
            List<LeaveBalanceEntity> all = repository.findAll();

            if (all.isEmpty()) {
                throw new LeaveBalanceException("Leave balances not found");
            }
            all.forEach(item -> list.add(mapper.map(item, LeaveBalance.class)));

            return list;
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving leave balance records", dae);
            throw new LeaveBalanceException("Unable to retrieve leave balance records");
        } catch (MappingException me) {
            log.error("Error mapping leave balance records", me);
            throw new LeaveBalanceException("Unable to process leave balance records");
        }
    }

    @Override
    public LeaveBalance retrieveById(Long id) {
        try {
            LeaveBalanceEntity leaveBalance = repository.findById(id)
                    .orElseThrow(() -> new LeaveBalanceException(NOT_FOUND_MESSAGE + id));

            return mapper.map(leaveBalance, LeaveBalance.class);
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving leave balance record", dae);
            throw new LeaveBalanceException("Unable to retrieve leave balance record");
        } catch (MappingException me) {
            log.error("Error mapping leave balance record", me);
            throw new LeaveBalanceException("Unable to process leave balance record");
        }
    }

    @Override
    public LeaveBalance update(Long id, LeaveBalance leaveBalanceDetails) {
        try {
            LeaveBalanceEntity existingLeaveBalance = repository.findById(id)
                    .orElseThrow(() -> new LeaveBalanceException(NOT_FOUND_MESSAGE + id));

            mapper.map(leaveBalanceDetails, existingLeaveBalance);

            LeaveBalanceEntity updatedLeaveBalance = repository.save(existingLeaveBalance);
            return mapper.map(updatedLeaveBalance, LeaveBalance.class);
        } catch (DataAccessException dae) {
            log.error("Database error while updating leave balance with ID: {}", id, dae);
            throw new LeaveBalanceException("Unable to update leave balance record");
        } catch (MappingException me) {
            log.error("Error mapping leave balance details for update: {}", leaveBalanceDetails, me);
            throw new LeaveBalanceException("Failed to update leave balance data");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            LeaveBalanceEntity leaveBalance = repository.findById(id)
                    .orElseThrow(() -> new LeaveBalanceException(NOT_FOUND_MESSAGE + id));

            repository.delete(leaveBalance);
            log.info("Leave balance with ID {} deleted successfully", id);
        } catch (DataAccessException dae) {
            log.error("Database error while deleting leave balance with ID: {}", id, dae);
            throw new LeaveBalanceException("Unable to delete leave balance record");
        }
    }
}
