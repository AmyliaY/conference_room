package main.com.em.dao;

import main.com.em.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Admiral on 2018/1/19.
 */
public interface ReservationMapper {

    public List<Room> findRoomsByTime(@Param("date")String date, @Param("begintime")String begintime, @Param("endtime")String endtime);

    public Integer reservationCount();

    public List<ReservationVo> findByPaging(PagingVO pagingVO);

    public List<Reservation> findByName(String name);

    public Reservation findById(Integer id);

    public Integer reservationPassCount();

    public List<ReservationVo> findRecord(PagingVO pagingVO);

    public void reviewReservation(Integer id);

    public Integer reserveCount();

    public List<ReservationVo> findAllByPaging(PagingVO pagingVO);

    public void addReservation(Reservation reservation);

    public List<ReservationVo> queryByUser(String name);

    public List<Reservation> findByUser(String user);

    public void cancelApplication(Integer id);
}
