var getDates = function(startDate, endDate) {
	var dates = [],
	currentDate = startDate,
	addDays = function(days) {
		var date = new Date(this.valueOf());
		date.setDate(date.getDate() + days);
		return date;
	};
	while (currentDate <= endDate) {
		dates.push(currentDate);
		currentDate = addDays.call(currentDate, 1);
	}
	return dates;
};

var dates = getDates(new Date(2013,10,22), new Date(2013,10,22));                                                                                                           
dates.forEach(function(date) {
	console.log(date);
});

$('#date_deb_').change(function(){
	var dates = getDates(new Date($("#date_deb_").val()), new Date($("#date_fin_").val()));  
	var row='<div class="row" id="insert_time_to_dates">';
	var times_to_fill="";
	dates.forEach(function(date) {
		console.log(date.getFullYear());
		times_to_fill+=inputs_date(date);
		inputs_date(date);
	});				
	$("#insert_time_to_dates").replaceWith(row+times_to_fill+"</div>");
});

$('#date_fin_').change(function(){
	var dates = getDates(new Date($("#date_deb_").val()), new Date($("#date_fin_").val()));   
	var row='<div class="row" id="insert_time_to_dates">';
	var times_to_fill="";
	dates.forEach(function(date) {
		console.log(date.getFullYear());
		times_to_fill+=inputs_date(date);
	});			

	$("#insert_time_to_dates").replaceWith(row+times_to_fill+"</div>");
});

var inputs_date=function(date)
{
	var regular_format_date=date.getFullYear() + "-"
	+ (date.getMonth() + 1) + "-" + date.getDate();

	var days_name= new Array(7);
	days_name[0]="Dimanche";
	days_name[1]="Lundi";
	days_name[2]="Mardi";
	days_name[3]="Mercredi";
	days_name[4]="Jeudi";
	days_name[5]="Vendredi";
	days_name[6]="Samedi";

	var start_at='<div id="k_'+regular_format_date+'" class="col-md-12"><div class="col-md-5"><div class="form-group"><label for="recipient-name" class="col-form-label date_info" style="font-size: 15px;">'+days_name[date.getDay()]+' ('+regular_format_date+')</label> <input type="time" min="0" max="24" name="heures_deb" id="deb_'+regular_format_date+'" required="required" class="form-control time_deb" /></div></div>';
	var end_at='<div class="col-md-5"><div class="form-group"><label style="font-size: 15px;" for="recipient-name" class="col-form-label date_info">'+days_name[date.getDay()]+' ('+regular_format_date+')</label> <input type="time" name="heures_fin" min="0" max="24" required="required"  id="fin_'+regular_format_date+'" class="form-control time_fin" /></div></div>';
	var hidden_input='<input type="hidden" name="date_to_inset_data" value="'+regular_format_date+'"/>';
	var delete_this='<div class="col-md-2"><a style="margin-top:38px;" href="#" data-id="'+regular_format_date+'" class="delete_day btn btn-danger">-</a></div></div>';
	console.log("Ok");
	return start_at+end_at+hidden_input+delete_this;

}
	$(document).on("click",".delete_day",function()
		{
			$("#k_"+$(this).attr("data-id")).remove();
		});