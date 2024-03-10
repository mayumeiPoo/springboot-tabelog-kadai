package com.example.nagoyameshi.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation,Integer>{
	// ユーザーに関連する予約を取得（ページング対応）
    public Page<Reservation> findByUserOrderByReservationDateDesc(User user, Pageable pageable);

   

	public Page<Reservation> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);



	public Page<Reservation> findByUserAndReservationDateAfterOrReservationDateEqualsAndReservationTimeAfter(User user, LocalDate dateAfter, LocalDate dateEquals, LocalTime time, Pageable pageable);



	public Page<Reservation> findByUserAndReservationDateBeforeOrReservationDateEqualsAndReservationTimeBefore(User user, LocalDate dateBefore, LocalDate dateEquals, LocalTime time, Pageable pageable);
	
	long count();

}
