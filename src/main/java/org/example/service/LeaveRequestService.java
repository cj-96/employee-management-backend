package org.example.service;

import org.example.dto.LeaveRequest;

import java.util.List;

public interface LeaveRequestService {
    LeaveRequest persist(LeaveRequest leaveRequest);

    List<LeaveRequest> retrieveAll();

    LeaveRequest retrieveById(Long id);

    LeaveRequest update(Long id, LeaveRequest leaveRequestDetails);

    void delete(Long id);
}
