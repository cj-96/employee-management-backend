package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.Payroll;
import org.example.entity.PayrollEntity;
import org.example.exception.PayrollException;
import org.example.repository.PayrollRepository;
import org.example.service.PayrollService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository repository;
    private final ModelMapper mapper;
    private static final String NOT_FOUND_MESSAGE = "Payroll not found with ID: ";

    @Override
    public Payroll persist(Payroll payroll) {
        try {
            PayrollEntity save = repository.save(mapper.map(payroll, PayrollEntity.class));
            return mapper.map(save, Payroll.class);
        } catch (DataAccessException e) {
            log.error("Failed to persist payroll: {}", payroll, e);
            throw new PayrollException("Failed to save payroll to database");
        } catch (MappingException e) {
            log.error("Error mapping payroll object: {}", payroll, e);
            throw new PayrollException("Failed to convert payroll data");
        }
    }

    @Override
    public List<Payroll> retrieveAll() {
        try {
            List<Payroll> list = new ArrayList<>();
            List<PayrollEntity> all = repository.findAll();

            if (all.isEmpty()) {
                throw new PayrollException("Payroll Not Found");
            }
            all.forEach(item -> list.add(mapper.map(item, Payroll.class)));

            return list;
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving payroll records", dae);
            throw new PayrollException("Unable to retrieve payroll records");
        } catch (MappingException me) {
            log.error("Error mapping payroll records", me);
            throw new PayrollException("Unable to process payroll records");
        }
    }

    @Override
    public Payroll retrieveById(Long id) {
        try {
            PayrollEntity payroll = repository.findById(id)
                    .orElseThrow(() -> new PayrollException(NOT_FOUND_MESSAGE + id));

            return mapper.map(payroll, Payroll.class);
        } catch (DataAccessException dae) {
            log.error("Database error while retrieving payroll record", dae);
            throw new PayrollException("Unable to retrieve payroll record");
        } catch (MappingException me) {
            log.error("Error mapping payroll record", me);
            throw new PayrollException("Unable to process payroll record");
        }
    }

    @Override
    public Payroll update(Long id, Payroll payrollDetails) {
        try {
            PayrollEntity existingPayroll = repository.findById(id)
                    .orElseThrow(() -> new PayrollException(NOT_FOUND_MESSAGE + id));

            mapper.map(payrollDetails, existingPayroll);

            PayrollEntity updatedPayroll = repository.save(existingPayroll);
            return mapper.map(updatedPayroll, Payroll.class);
        } catch (DataAccessException dae) {
            log.error("Database error while updating payroll with ID: {}", id, dae);
            throw new PayrollException("Unable to update payroll record");
        } catch (MappingException me) {
            log.error("Error mapping payroll details for update: {}", payrollDetails, me);
            throw new PayrollException("Failed to update payroll data");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PayrollEntity payroll = repository.findById(id)
                    .orElseThrow(() -> new PayrollException(NOT_FOUND_MESSAGE + id));

            repository.delete(payroll);
            log.info("Payroll with ID {} deleted successfully", id);
        } catch (DataAccessException dae) {
            log.error("Database error while deleting payroll with ID: {}", id, dae);
            throw new PayrollException("Unable to delete payroll record");
        }
    }
}
