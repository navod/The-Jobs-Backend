package com.example.thejobs.repo;

import com.example.thejobs.entity.Booking;
import com.example.thejobs.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Booking findBookingByDate(String date);

    List <Booking> findBookingByConsultantId(Consultant consultantId);
}
