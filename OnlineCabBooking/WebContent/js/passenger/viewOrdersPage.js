
$(document).ready(function(){
	hideMessage();
});

// hide message div
// message shows the result of different operations
function hideMessage(){
	$( "#messageHide" ).click(function() {
		  $("#message").hide("slow");
		});
}

//delete one order
function deleteOneOrder(orderId){
    var params = {
    		orderId:orderId
    };
    // use ajax to send params to back end and receive result
    var url="deleteOneOrder.htm";
	if(confirm("Are you sure to delete this order?")){
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
			    refreshOrders();
			},
			complete : function(msg) {

			}
			
		});
	}
    
}

// delete several orders
function deleteOrders(){
	// get selected checkbox info for order id
	var orders="";
	var chbx=document.getElementsByName("orderChbx"); 
    for(var i=0;i<chbx.length;i++){
         if(chbx[i].checked){
         orders = orders + chbx[i].value +",";
       }
    }   
    orders = orders.substring(0, orders.length-1);
    if(orders.length == 0){
    		alert("no order is selected.")
    		return;
    }
    var params = {
    		orders:orders
    };
    // use ajax to send params to back end and receive result
    var url="deleteOrders.htm";
	if(confirm("Are you sure to delete these orders?")){
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
			    refreshOrders();
			},
			complete : function(msg) {

			}
			
		});
	}
    
}

// view all info of one order
function viewOneOrder(orderId){
	 var params = {
	    		orderId:orderId
	    };
	// use ajax to send params to back end and receive result
	var url="viewOneOrder.htm";
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

// refresh list of orders
function refreshOrders(){
	// use ajax to send params to back end and receive result
    var params = { };
    var url="ordersList.htm";
	$.ajax({
		url : url,
		type : "GET",
		async : false,
		cache : false,
		global : false,
		dataType : "text",
		beforeSend : function(){
			
		},
		error : function(){
			
		},
		success : function(data) {
			var ordersList = document.getElementById("ordersList");
			ordersList.innerHTML = data.toString();
			hideMessage();
		},
		complete : function(msg) {

		}
		
	});
	 
}