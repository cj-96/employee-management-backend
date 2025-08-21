package org.example.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
@Filter(name = "softDeleteFilter", condition = "deleted_at IS NULL")
public class EmployeeEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String jobTitle;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    private String profilePhoto;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttendanceEntity> attendanceRecords;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LeaveRequestEntity> leaveRequests;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PayrollEntity> payrolls;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LeaveBalanceEntity> leaveBalances;

    @LastModifiedDate
    @Column()
    @UpdateTimestamp
    private Date updatedAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(updatable = false)
    private Date deletedAt;
    
    @Column()
    private Date updatedBy;

    @Column()
    private Date deletedBy;

    @Column( updatable = false)
    private Long createdBy;
}
