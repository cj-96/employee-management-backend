package org.example.service;

import org.example.dto.LeaveBalance;

import java.util.List;

public interface LeaveBalanceService {
    LeaveBalance persist(LeaveBalance leaveBalance);

    List<LeaveBalance> retrieveAll();

    LeaveBalance retrieveById(Long id);

    LeaveBalance update(Long id, LeaveBalance leaveBalanceDetails);

    void delete(Long id);
}
