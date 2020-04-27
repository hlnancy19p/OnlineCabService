// show edit page in pop dialog
function editPageDialog(orderId){
	// whether to show create form or update form depending on orderId
	if(orderId == 0){
		var params = { };
	}
	else{
		var params = { 
			orderId:orderId
		};
	}
	// use ajax to get edid page dynamically
	var url="/OnlineCabBooking/business/editEmployeeOrder.htm";
	$.ajax({
		url : url,
		type : "POST",
		async : false,
		cache : false,
		global : false,
		dataType : "text",
		data : params,
		beforeSend : function(){
			
		},
		error : function(){
		},
		success : function(data) {
			var editForm = document.getElementById("editForm");
			editForm.innerHTML = data.toString();
			showDialog();

		},
		complete : function(msg) {
		}
		
	});
	
}

// before sbumit, check the values to be valid
function checkValues(){
	  var flag = 0;
	  // clear warning messages
	  $("#pt_msg").html("");
	  $("#pa_msg").html("");
	  $("#nop_msg").html("");
	  $("#cp_msg").html("");
	  $("#app_msg").html("");
	  // check pickup time to be in a certificate format
	 if($.trim($("#pt").val()).length==0){
		 $("#pt_msg").html("cannot be empty");
		 flag = 1;
	 }
	 else{
		 var reDateTime = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/;
		 var dateTime = $("#pt").val();
		 if(!reDateTime.test(dateTime)){
			 $("#pt_msg").html("wrong format");
			 flag = 1;
		 }
		 // pickup time should be in the future
		 var currentDate = new Date();
	     var now = currentDate.Format("yyyy-MM-dd HH:mm:ss");
	     if(dateTime<now){
	      	$("#pt_msg").html("don't use previous time");
			flag = 1;
	     }
	 }
	 // check pickup address to be filled
	 if($.trim($("#pa").val()).length==0){
	  	$("#pa_msg").html("cannot be empty");
	  	flag = 1;
	 }
	 // check number of passengers to be a number
	 if($.trim($("#nop").val()).length==0){
		 $("#nop_msg").html("cannot be empty");
		 flag = 1;
	 }
	 else{
		 // have a max limit for number
		 var reNum = /^\d{1,2}$/;
		 var number = $("#nop").val();
		 if(!reNum.test(number)){
			 $("#nop_msg").html("wrong format");
			 flag = 1;
		 } 
	 }
	// check contact phone number is not empty
	 if($.trim($("#cp").val()).length==0){
		 $("#cp_msg").html("cannot be empty");
		 flag = 1;
	 }
	 // check applier is not null
	 var applier=$("#applier").find("option:selected").val();
	 if(applier.length==0){
		 $("#app_msg").html("choose an applier");
		 flag = 1;
	 }
	 return flag;

}

function SubmitOrder(orderid){
	// if check fails, submit will not be pushed.
	var check = checkValues();
	if(check == 1){
		return;
	}
	// whether to create or update depending on orderId
	if(orderid == 0){
		 createOrder();
	}
	else{
		updateOrder();
	}
	
}

// create a new order
function createOrder(){
	var pickupTime = $("#pt").val();
	var pickupAddr = $("#pa").val();
	var numOfPassengers = $("#nop").val();
	var orderNotes = $("#on").val();
	var contactPhone = $("#cp").val();
	var applier = $("#applier").find("option:selected").val();
	var params = {
			pickupTime:pickupTime,
			pickupAddr:pickupAddr,
			numOfPassengers:numOfPassengers,
			contactPhone:contactPhone,
			orderNotes:orderNotes,
			applier:applier
	};
	// use ajax to send params to back end and receive result
	var url="/OnlineCabBooking/business/createEmployeeOrder.htm";
	$.ajax({
		url : url,
		type : "POST",
		async : false,
		cache : false,
		global : false,
		dataType : "text",
		data : params,
		beforeSend : function(){
			
		},
		error : function(){
		},
		success : function(data) {
			var message = document.getElementById("message");
			// if user is at view Orders page, just show result on this page
			if(message != null){
				message.innerHTML = data.toString();
			    message.style.display = 'block';
				hideDialog();
				refreshOrders();
			}
			else{
				// if user is at home page, alert result
				var receiveMessage = data.toString();
				if(receiveMessage.indexOf("Success")>0){
					alert("create Success!");
					setTimeout("top.location.href='business/viewBusinessOrders.htm'", 1); 
				}
				else{
					alert("create Fail!");
				}
		        
			}

		},
		complete : function(msg) {
		}
		
	});
	
}

// update an existing order
function updateOrder(){
	var orderId = $("#oid").val();
	var pickupTime = $("#pt").val();
	var pickupAddr = $("#pa").val();
	var numOfPassengers = $("#nop").val();
	var contactPhone = $("#cp").val();
	var orderNotes = $("#on").val();
	var statusId = $("#status").val();
	var applier = $("#applier").find("option:selected").val();
	var version = $("#version").val();
	var creater = $("#creater").val();
	var params = {
			orderId:orderId,
			pickupTime:pickupTime,
			pickupAddr:pickupAddr,
			numOfPassengers:numOfPassengers,
			contactPhone:contactPhone,
			orderNotes:orderNotes,
			statusId:statusId,
			applier:applier,
			version:version,
			creater:creater
	};
	// use ajax to send params to back end and receive result
	var url="/OnlineCabBooking/business/updateEmployeeOrder.htm";
	$.ajax({
		url : url,
		type : "POST",
		async : false,
		cache : false,
		global : false,
		dataType : "text",
		data : params,
		beforeSend : function(){
			
		},
		error : function(){
		},
		success : function(data) {
			var message = document.getElementById("message");
			message.innerHTML = data.toString();
		    message.style.display = 'block';
			hideDialog();
			refreshOrders();

		},
		complete : function(msg) {
		}
		
	});
	
}

//format date type
Date.prototype.Format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //Month 
        "d+": this.getDate(), //day 
        "H+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute 
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds() //millisecond
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}