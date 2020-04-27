package util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class MapUtil {

	// generate request into map for an order
	public static Map<String,String> generateOrderMap(HttpServletRequest request){
		Map<String,String> map = new HashMap<String,String>();
//			System.out.println("Start map generating");
		if(request.getParameter("orderId") != null) {
			map.put("orderId", request.getParameter("orderId"));
		}
		if(request.getParameter("pickupTime") != null) {
			map.put("pickupTime", request.getParameter("pickupTime"));
		}
		if(request.getParameter("pickupAddr") != null) {
			map.put("pickupAddr", request.getParameter("pickupAddr"));
		}
		if(request.getParameter("contactPhone") != null) {
			map.put("contactPhone", request.getParameter("contactPhone"));
		}
		if(request.getParameter("orderNotes") != null) {
			map.put("orderNotes", request.getParameter("orderNotes"));
		}
		if(request.getParameter("numOfPassengers") != null) {
			map.put("numOfPassengers", request.getParameter("numOfPassengers"));
		}
		if(request.getParameter("statusId") != null) {
			map.put("statusId", request.getParameter("statusId"));
		}
		if(request.getParameter("version") != null) {
			map.put("version", request.getParameter("version"));
		}
		if(request.getParameter("creater") != null) {
			map.put("createrId", request.getParameter("creater"));
		}else {
			map.put("createrId", request.getSession().getAttribute("userId").toString());
		}
		map.put("userId", request.getSession().getAttribute("userId").toString());

		
		return map;
	}
	
	// generate request into map for an order
		public static Map<String,String> generateBusinessOrderMap(HttpServletRequest request){
			Map<String,String> map = new HashMap<String,String>();
//				System.out.println("Start map generating");
			if(request.getParameter("orderId") != null) {
				map.put("orderId", request.getParameter("orderId"));
			}
			if(request.getParameter("pickupTime") != null) {
				map.put("pickupTime", request.getParameter("pickupTime"));
			}
			if(request.getParameter("pickupAddr") != null) {
				map.put("pickupAddr", request.getParameter("pickupAddr"));
			}
			if(request.getParameter("contactPhone") != null) {
				map.put("contactPhone", request.getParameter("contactPhone"));
			}
			if(request.getParameter("orderNotes") != null) {
				map.put("orderNotes", request.getParameter("orderNotes"));
			}
			if(request.getParameter("numOfPassengers") != null) {
				map.put("numOfPassengers", request.getParameter("numOfPassengers"));
			}
			if(request.getParameter("statusId") != null) {
				map.put("statusId", request.getParameter("statusId"));
			}
			if(request.getParameter("version") != null) {
				map.put("version", request.getParameter("version"));
			}
			if(request.getParameter("applier") != null) {
				map.put("userId", request.getParameter("applier"));
			}
			if(request.getParameter("creater") != null) {
				map.put("createrId", request.getParameter("creater"));
			}else {
				map.put("createrId", request.getSession().getAttribute("userId").toString());
			}

			return map;
		}
}
