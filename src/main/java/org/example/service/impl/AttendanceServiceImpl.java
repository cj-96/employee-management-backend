package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.Attendance;
import org.example.entity.AttendanceEntity;
import org.example.exception.AttendanceException;
import org.example.repository.AttendanceRepository;
import org.example.service.AttendanceService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository repository;
    private final ModelMapper mapper;

    @Override
    public Attendance persist(Attendance attendance) {
        try {
            AttendanceEntity save = repository.save(mapper.map(attendance, AttendanceEntity.class));
            return mapper.map(save, Attendance.class);

        } catch (DataAccessException e) {

            log.error("Failed to persist department: {}", attendance, e);
            throw new AttendanceException("Failed to save attendance to database");

        } catch (MappingException e) {

            log.error("Error mapping department object: {}", attendance, e);
            throw new AttendanceException("Failed to convert attendance data");

        }
    }

    @Override
    public List<Attendance> retrieveAll() {
        try{
            List<Attendance> list = new ArrayList<>();
            List<AttendanceEntity> all = repository.findAll();

            if(all.isEmpty()){
                throw new AttendanceException("Attendance Not Found");
            }
            all.forEach(item -> list.add(mapper.map(item, Attendance.class)));
            return list;
        }catch (DataAccessException dae) {
            log.error("Database error while retrieving attendance records", dae);
            throw new AttendanceException("Unable to retrieve attendance records");
        } catch (MappingException me) {
            log.error("Error mapping attendance records", me);
            throw new AttendanceException("Unable to process attendance records");
        }
    }

    @Override
    public Attendance retrieveById(Long id) {
        return mapper.map(repository.findById(id), Attendance.class);
    }

    @Override
    public Attendance update(Long id, Attendance attendanceDetails) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
