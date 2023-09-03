package com.example.thejobs.repo;

import com.example.thejobs.entity.Booking;
import com.example.thejobs.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Booking findBookingByDate(String date);

    List<Booking> findBookingByConsultantId(Consultant consultantId);

    List<Booking> findBookingByConsultantIdAndStatus(Consultant consultantId, String status);

    List<Booking> findBookingByStatus(String status);

    @Query(value = """
            SELECT
                DATE_SUB(CURDATE(), INTERVAL 1 DAY) AS yesterday,
                CURDATE() AS today,
                COUNT(CASE WHEN DATE(date) = DATE_SUB(CURDATE(), INTERVAL 1 DAY) THEN 1 ELSE NULL END) AS yesterday_count,
                COUNT(CASE WHEN DATE(date) = CURDATE() THEN 1 ELSE NULL END) AS today_count,
                (COUNT(CASE WHEN DATE(date) = CURDATE() THEN 1 ELSE NULL END) -
                 COUNT(CASE WHEN DATE(date) = DATE_SUB(CURDATE(), INTERVAL 1 DAY) THEN 1 ELSE NULL END)) AS increase_today
            FROM
                booking
            WHERE consultant_id=?1 AND `status` ="APPROVED\"""", nativeQuery = true)
    Object getTodayBookings(String consultantId);

    @Query(value = """
                SELECT COUNT(*) as pending_count FROM booking WHERE consultant_id =?1 and `status`="PENDING"
            """, nativeQuery = true)
    int getTotalPendingBookings(String consultantId);

    @Query(value = """
                SELECT COUNT(*) as reject_count FROM booking WHERE consultant_id =?1 and `status`="REJECT"
            """, nativeQuery = true)
    int getTotalRejectBookings(String consultantId);

    @Query(value = """
                SELECT COUNT(*) as completed_count FROM booking WHERE consultant_id =?1 and `status`="COMPLETED"
            """, nativeQuery = true)
    int getTotalCompletedBookings(String consultantId);

    @Query(value = """
                SELECT COUNT(*) as sheduled_count FROM booking WHERE consultant_id =?1 and `status`="APPROVED"
            """, nativeQuery = true)
    int getSheduledBookings(String consultantId);

    List<Booking> findBookingByConsultantIdAndStatusAndDate(Consultant consultantId, String status, String date);


    @Query(value = """
            SELECT
                DATE_SUB(CURDATE(), INTERVAL 1 DAY) AS yesterday,
                CURDATE() AS today,
                COUNT(CASE WHEN DATE(date) = DATE_SUB(CURDATE(), INTERVAL 1 DAY) THEN 1 ELSE NULL END) AS yesterday_count,
                COUNT(CASE WHEN DATE(date) = CURDATE() THEN 1 ELSE NULL END) AS today_count,
                (COUNT(CASE WHEN DATE(date) = CURDATE() THEN 1 ELSE NULL END) -
                 COUNT(CASE WHEN DATE(date) = DATE_SUB(CURDATE(), INTERVAL 1 DAY) THEN 1 ELSE NULL END)) AS increase_today
            FROM
                booking
            WHERE`status` ="APPROVED\"""", nativeQuery = true)
    Object getAllTodayBookings();


    @Query(value = """
                SELECT COUNT(*) as pending_count FROM booking WHERE `status`="PENDING"
            """, nativeQuery = true)
    int getTotalPendingBookings();

    @Query(value = """
                SELECT COUNT(*) as reject_count FROM booking WHERE `status`="REJECT"
            """, nativeQuery = true)
    int getTotalRejectBookings();

    @Query(value = """
                SELECT COUNT(*) as completed_count FROM booking WHERE `status`="COMPLETED"
            """, nativeQuery = true)
    int getTotalCompletedBookings();



}
