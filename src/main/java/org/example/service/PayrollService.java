package org.example.service;

import org.example.dto.Payroll;

import java.util.List;

public interface PayrollService {
    Payroll persist(Payroll payroll);

    List<Payroll> retrieveAll();

    Payroll retrieveById(Long id);

    Payroll update(Long id, Payroll payrollDetails);

    void delete(Long id);
}
