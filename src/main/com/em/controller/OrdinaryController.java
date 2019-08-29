package main.com.em.controller;

import com.alibaba.fastjson.JSON;
import main.com.em.domain.PagingVO;
import main.com.em.domain.Reservation;
import main.com.em.domain.Room;
import main.com.em.service.ReservationService;
import main.com.em.service.RoomService;
import main.com.em.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admiral on 2018/1/20.
 */
@Controller
@RequestMapping("/ordinary")
public class OrdinaryController {

    @Resource(name = "roomServiceImpl")
    private RoomService roomService;

    @Resource(name = "reservationServiceImpl")
    private ReservationService reservationService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<会议室信息管理>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    // 显示会议室列表
    @RequestMapping("/showRooms")
    @ResponseBody
    public String showRoom(Model model, Integer page) throws Exception {

        List<Room> list = null;
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(roomService.roomCount());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = roomService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = roomService.findByPaging(page);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roomList", list);

        String json = JSON.toJSONString(map);

        return json;
    }

    //会议室详情/通过会议室名称查询会议室详情
    @RequestMapping(value = "/queryRoomByName")
    @ResponseBody
    private String queryRoom(HttpServletRequest request, Model model) throws Exception {

        String findByName = request.getParameter("roomName");

        List<Room> list = roomService.findByName(findByName);

        Room room = null;

        if(list != null && list.size() > 0){
            room = list.get(0);
        }

        String json = JSON.toJSONString(room);
        return json;
    }

    //根据时间查询可用的会议室列表
    @RequestMapping("/queryRoomsByTime")
    @ResponseBody
    public String findAllReservation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Room> list = null;

        //获取参数
        String date = request.getParameter("date");
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");

        //首先限定日期，按会议室进行分组，然后从限定的结果集中获取

        list = reservationService.findRoomsByTime(date, begintime, endtime);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("roomList", list);

        String json = JSON.toJSONString(map);

        return json;
    }

/*    //搜索借用人
    @RequestMapping(value = "/queryByUser", method = {RequestMethod.POST})
    @ResponseBody
    private String queryUser(String findByName, Model model) throws Exception {

        List<ReservationVo> list = reservationService.queryByUser(findByName);

        model.addAttribute("recordList", list);

        return "/ordinary/showRecord";
    }*/

/*    //预约会议室页面跳转
    @RequestMapping(value = "/reserveRoom", method = RequestMethod.GET)
    @ResponseBody
    public String reserveRoomUI(Model model) throws Exception {
        //从数据库查询出所有会议室信息回显到页面
        List<Room> list = roomService.nameList();
        model.addAttribute("nameList", list);

        return "/ordinary/reserveRoom";
    }*/

    //预定
    @RequestMapping(value = "/reserveRoom")
    @ResponseBody
    public String reserveRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String> map = new HashMap<String, String>();

        try{

            //获取参数
            String date = request.getParameter("date");        //日期
            String begintime = request.getParameter("begintime");   //开始时间
            String endtime = request.getParameter("endtime");      //结束时间
            String room_id = request.getParameter("room_id");      //会议室id
            String  user = request.getParameter("user");         //用户id
            String speaker = request.getParameter("speaker");       //主讲人
            String peopleNum = request.getParameter("peopleNum");    //人数
            String content = request.getParameter("content");        //内容

            //验证，在前端做验证

            //数据入库
            Reservation reservation = new Reservation();
            SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm:ss");
            reservation.setDate(dateSdf.parse(date));
            reservation.setBeginTime(timeSdf.parse(begintime));
            reservation.setEndTime(timeSdf.parse(endtime));
            reservation.setRoomId(Integer.parseInt(room_id));
            reservation.setUser(user);
            reservation.setDef1(speaker);
            reservation.setDef2(peopleNum);
            reservation.setDef3(content);
            reservation.setStatus(1);
            reservation.setCreateTime(new Date());

            reservationService.addReservation(reservation);

        } catch (Exception e){
            return JSON.toJSONString(map.put("result", "fail"));
        }
        return JSON.toJSONString(map.put("result", "seccess"));
    }

    //我的预约记录列表
    @RequestMapping(value = "/myReservations")
    @ResponseBody
    public String myReservations(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String user = request.getParameter("user");

        List<Reservation> list=reservationService.findByUser(user);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reservationList", list);

        String json = JSON.toJSONString(map);

        return json;

    }

    //预约记录详情
    @RequestMapping(value = "/reservationDesc")
    @ResponseBody
    public String reservationDesc(HttpServletRequest request, HttpServletResponse response) throws Exception{

        Integer id = Integer.parseInt(request.getParameter("id"));

        Reservation reservation = reservationService.findById(id);

        String json = JSON.toJSONString(reservation);

        return json;
    }

    //取消预约
    @RequestMapping("/cancelApply")
    @ResponseBody
    public String cancelApplication(HttpServletRequest request, HttpServletResponse response) throws Exception{

        Map<String, String> map = new HashMap<String, String>();

        try{

            Integer id = Integer.parseInt(request.getParameter("id"));

            reservationService.cancelApplication(id);

        } catch (Exception e){
            map.put("result", "fail");
            return JSON.toJSONString(map);
        }

        map.put("result", "success");
        return JSON.toJSONString(map);
    }

}
