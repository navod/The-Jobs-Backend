package com.example.thejobs.repo;

import com.example.thejobs.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsultantRepository extends JpaRepository<Consultant, String> {

    Consultant findByEmail(String email);

    Consultant findByCountryAndJobType(String country, String jobType);

    Consultant findByCountry(String country);

    @Query(value = """
                SELECT COUNT(*) as active_consultants FROM consultant WHERE `status`=1
            """, nativeQuery = true)
    int getTotalActiveConsultants();

    @Query(value = """
                SELECT COUNT(*) as deactive_consultants FROM consultant WHERE `status`=0
            """, nativeQuery = true)
    int getTotalDeactiveConsultants();

    @Query(value = """
                SELECT COUNT(*) as total_consultants FROM consultant
            """, nativeQuery = true)
    int getTotalConsultants();

    @Query(value = """
            SELECT consultant.first_name, consultant.id, consultant.last_name, consultant.email, consultant.job_type, consultant.country, SUM(CASE WHEN booking.`status` = 'APPROVED' AND booking.`date` = CURDATE() THEN 1 ELSE 0 END) AS approved_count, SUM(CASE WHEN booking.`status` = 'REJECT' THEN 1 ELSE 0 END) AS reject_count, SUM(CASE WHEN booking.`status` = 'COMPLETED' THEN 1 ELSE 0 END) AS completed_count, SUM(CASE WHEN booking.`status` = 'PENDING' THEN 1 ELSE 0 END) AS pending_count FROM consultant INNER JOIN booking ON booking.consultant_id = consultant.id GROUP BY consultant.id
            """, nativeQuery = true)
    List<Object> totalConsultsAnalytics();
}
