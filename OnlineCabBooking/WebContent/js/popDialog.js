// show a pop dialog
function showDialog(){
	$("body").css("overflow", "hidden");
	document.getElementById('content').style.display='block';
	document.getElementById('fade').style.display='block';
}

// hide a pop dialog
function hideDialog(){
	document.getElementById('content').style.display='none';
	document.getElementById('fade').style.display='none';
	$("body").css("overflow", "auto");
}
