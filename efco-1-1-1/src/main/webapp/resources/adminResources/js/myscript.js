
jQuery(document).on("click",".edit_type_Formation",
		function() {

	jQuery("#title_type_formation_to_edit").val(
			jQuery("#title_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#descrption_type_formation_to_edit").val(
			jQuery("#description_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery(".type_formation_id_to_edit").val(
			jQuery(this).attr("data-id"));

	jQuery("#edit_type_formation_modal").modal("show");

});


jQuery(document).on("click",".edit_Formation",
		function() {


	jQuery("#title_formation_to_edit").val(
			jQuery("#title_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#desc_formation_to_edit").text(
			jQuery("#desc_value_"+jQuery(this).attr("data-id")).text());


	jQuery(".formation_id_to_edit").val(
			jQuery(this).attr("data-id"));



	jQuery("#type_formation_to_edit_select").val(
			jQuery("#type_formation_value_" + jQuery(this).attr("data-id")).val()
	).change();

	jQuery("#sous_type_formation_to_edit_select").val(
			jQuery("#sous_type_formation_value_" + jQuery(this).attr("data-id")).val()
	).change();



	jQuery("#edit_formation_modal").modal("show");

});


jQuery(document).on("click",".edit_actualite",
		function() {


	jQuery("#title_actualite_to_edit").val(
			jQuery("#title_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#desc_actualite_to_edit").text(
			jQuery("#desc_value_"+jQuery(this).attr("data-id")).text());


	jQuery(".actualite_id_to_edit").val(
			jQuery(this).attr("data-id"));

	jQuery("#edit_actualite_modal").modal("show");

});




jQuery(document).on("click",".edit_user",
		function() {


	jQuery("#nom_user_to_edit").val(
			jQuery("#nom_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#prenom_user_to_edit").val(
			jQuery("#prenom_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#num_tel_user_to_edit").val(
			jQuery("#num_tel_user_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#adresse_user_to_edit").val(
			jQuery("#adresse_user_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#email_user_to_edit").val(
			jQuery("#email_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery(".user_id_to_edit").val(
			jQuery(this).attr("data-id"));

	jQuery("#edit_user_modal").modal("show");

});



jQuery(document).on("click",".edit_user_stag",
		function() {


	jQuery("#nom_user_to_edit").val(
			jQuery("#nom_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#prenom_user_to_edit").val(
			jQuery("#prenom_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#num_tel_user_to_edit").val(
			jQuery("#num_tel_user_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#adresse_user_to_edit").val(
			jQuery("#adresse_user_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#email_user_to_edit").val(
			jQuery("#email_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#client_select_for_stag").val(
			jQuery("#client_id_value_" + jQuery(this).attr("data-id")).val()
	).change();


	jQuery(".user_id_to_edit").val(
			jQuery(this).attr("data-id"));

	jQuery("#edit_user_modal").modal("show");

});



jQuery(document).on("click",".edit_client",
		function() {


	jQuery("#nom_user_to_edit").val(
			jQuery("#nom_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#prenom_user_to_edit").val(
			jQuery("#prenom_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#num_tel_user_to_edit").val(
			jQuery("#num_tel_user_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#adresse_user_to_edit").val(
			jQuery("#adresse_user_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#email_user_to_edit").val(
			jQuery("#email_value_" + jQuery(this).attr("data-id"))
			.text());




	jQuery("#code_postalr_to_edit").val(
			jQuery("#code_postal_client_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#maison_mere_client_to_edit").val(
			jQuery("#maison_mere_client_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#pays_client_to_edit").val(
			jQuery("#pays_client_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#ville_client_to_edit").val(
			jQuery("#ville_client_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#siren_client_to_edit").val(
			jQuery("#siren_client_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#siret_client_to_edit").val(
			jQuery("#siret_client_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#rcs_clien_to_edit").val(
			jQuery("#rcs_client_value_" + jQuery(this).attr("data-id"))
			.text());

	if(jQuery("#assujetti_client_value_" + jQuery(this).attr("data-id")).text()=="true")
	{
		jQuery("#assujetti_client_to_edit").val(1).change();

	}
	else
	{
		jQuery("#assujetti_client_to_edit").val(0).change();

	}


	jQuery("#type_tier_client_to_edit").val(
			jQuery("#type_tier_client_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#forme_juridique_client_to_edit").val(
			jQuery("#forme_juridique_client_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#num_bancaire_client_to_edit").val(
			jQuery("#num_carte_bancaire_client_value_" + jQuery(this).attr("data-id"))
			.text());




	jQuery(".user_id_to_edit").val(
			jQuery(this).attr("data-id"));

	jQuery("#edit_client_modal").modal("show");

});




jQuery(document).on("click",".answer_devis",
		function() {

	jQuery("#nbr_stag_devis_to_answer").val(
			jQuery("#nbr_stag_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#email_devis_to_answer").val(
			jQuery("#email_value_" + jQuery(this).attr("data-id"))
			.text());




	jQuery("#num_tel_devis_to_answer").val(
			jQuery("#num_tel_value_" + jQuery(this).attr("data-id"))
			.text());


	jQuery("#nom_devis_to_answer").val(
			jQuery("#nom_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#type_formation_to_answer").val(
			jQuery("#type_formation_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery(".devis_id_to_answer").val(
			jQuery(this).attr("data-id"));

	jQuery("#answer_devis_modal").modal("show");

});


jQuery(document).on("click",".edit_facture",
		function() {


	jQuery("#nbr_stag_facture_to_edit").val(
			jQuery("#nbr_stag_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#nbr_heure_facture_to_edit").val(
			jQuery("#nbr_heure_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#nom_facture_to_edit").val(
			jQuery("#nom_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#montant_recu_facture_to_edit").val(
			jQuery("#montant_recu_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#totale_facture_to_edit").val(
			jQuery("#totale_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#type_formation_facture_to_edit").val(
			jQuery("#formation_value_" + jQuery(this).attr("data-id")).val()
	).change();


	jQuery("#methode_paiement_facture_to_edit").val(jQuery("#methode_paiement_value_" + jQuery(this).attr("data-id")).val()).change();


	jQuery(".facture_id").val(
			jQuery(this).attr("data-id"));

	jQuery("#edit_facture_modal").modal("show");

});


jQuery(document).on("click",".edit_salle",
		function() {

	jQuery("#nom_salle_to_edit").val(
			jQuery("#nom_value_" + jQuery(this).attr("data-id"))
			.text());

	
	jQuery("#adresse_salle_to_edit").val(
			jQuery("#adresse_value_" + jQuery(this).attr("data-id")).text()
	).change();
	
	jQuery("#localisation_salle_to_edit").val(
			jQuery("#localisation_value_" + jQuery(this).attr("data-id")).text()
	).change();


	jQuery(".salle_id_to_edit").val(
			jQuery(this).attr("data-id"));

	jQuery("#edit_salle_modal").modal("show");

});

jQuery(document).on("click",".edit_sous_type_formation",function(){


	jQuery("#list_type_formation_to_edit").val(
			jQuery("#type_formation_value_" + jQuery(this).attr("data-id")).val()
	).change();


	jQuery("#titre_sous_type_formation_to_edit").val(
			jQuery("#titre_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#desc_sous_type_formation_to_edit").val(
			jQuery("#description_value_" + jQuery(this).attr("data-id"))
			.text());

	jQuery(".sous_type_formation_id_to_edit").val(
			jQuery(this).attr("data-id"));

	jQuery("#edit_sous_type_formation_modal").modal("show");

});

jQuery(document).on("click",".edit_session_Formation",
		function() {

	jQuery("#title_session_formation_to_edit").val(
			jQuery("#value_session_formation_titre_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#formateur_to_edit").val(
			jQuery("#value_session_formation_" + jQuery(this).attr("data-id")).val()
	).change();

	jQuery("#formation_to_edit").val(
			jQuery("#value_session_formation_id" + jQuery(this).attr("data-id")).val()
	).change();


	jQuery("#salle_to_edit").val(
			jQuery("#value_salle_id_formation_" + jQuery(this).attr("data-id")).val()
	).change();

	jQuery("#date_debut_session_formation_to_edit").val(
			jQuery("#value_session_formation_date_debut_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#date_fin_session_formation_to_edit").val(
			jQuery("#value_session_formation_date_fin_" + jQuery(this).attr("data-id"))
			.text());

	jQuery("#duree_session_formation_to_edit").val(
			jQuery("#value_session_formation_duree_" + jQuery(this).attr("data-id"))
			.val());

	jQuery("#nbr_stag_session_formation_to_edit").val(
			jQuery("#value_session_formation_nbr_stag_" + jQuery(this).attr("data-id"))
			.val());
	
	jQuery("#link_session_formation_to_edit").val(
			jQuery("#value_session_formation_link_" + jQuery(this).attr("data-id"))
			.val());

	jQuery(".session_formation_id").val(
			jQuery(this).attr("data-id"));


	var dates = getDates(new Date(jQuery("#date_debut_session_formation_to_edit").val()), new Date(jQuery("#date_fin_session_formation_to_edit").val()));                                                                                                           

	var row='<div class="row" id="insert_time_to_dates_to_edit">';
	var times_to_fill="";
	var i=0;
	dates.forEach(function(date) {
		//	console.log("date to edit :"+date);
		i++;
		times_to_fill+=inputs_date_edit_date(date);
	});			

	jQuery("#insert_time_to_dates_to_edit").replaceWith(row+times_to_fill+"</div>");
	console.log("Done");

	jQuery("#duree_rest_session_formation_to_edit").val(0);

	jQuery("#edit_session_formation_modal").modal("show");

});


jQuery(document).on("change",'#date_fin_session_formation_to_edit',function(){

	var dates = getDates(new Date(jQuery("#date_debut_session_formation_to_edit").val()), new Date(jQuery("#date_fin_session_formation_to_edit").val()));                                                                                                           
	var row='<div class="row" id="insert_time_to_dates_to_edit">';
	var times_to_fill="";
	var i=0;
	dates.forEach(function(date) {
		//	console.log("date to edit :"+date);
		i++;
		times_to_fill+=new_inputs_date_edit_date(date);
	});			

	jQuery("#insert_time_to_dates_to_edit").replaceWith(row+times_to_fill+"</div>");
	jQuery("#duree_rest_session_formation_to_edit").val(jQuery("#duree_session_formation_to_edit").val());
	console.log("Done");
});

var inputs_date_edit_date=function(date)
{
	/*var regular_format_date=date.getFullYear() + "-"
	+ (date.getMonth() + 1) + "-" + date.getDate();*/

	var regular_format_date = date.getFullYear()+"-"+ ('0' + (date.getMonth()+1)).slice(-2) + "-"+('0' + date.getDate()).slice(-2);

	console.log("Date  to edit : : "+regular_format_date);
	var deb=jQuery("#time_event_deb_"+regular_format_date).val();
	var fin=jQuery("#time_event_fin_"+regular_format_date).val();

	var days_name= new Array(7);
	days_name[0]="Dimanche";
	days_name[1]="Lundi";
	days_name[2]="Mardi";
	days_name[3]="Mercredi";
	days_name[4]="Jeudi";
	days_name[5]="Vendredi";
	days_name[6]="Samedi";

	console.log("time_event_deb : "+deb+" // time_event_fin_"+fin);
	console.log("Fin : "+fin);

	var start_at='<div id="k_'+regular_format_date+'" class="col-md-12 '+regular_format_date+'"><div class="col-md-5"><div class="form-group"><label for="recipient-name" style="font-size: 15px;" class="col-form-label date_info">'+days_name[date.getDay()]+' ('+regular_format_date+')</label> <input type="time" min="0" max="24" name="heures_deb" id="deb_'+regular_format_date+'" value="'+deb+'" required="required" class="form-control time_deb_edit" /></div></div>';
	var hidden_input='<input type="hidden" name="date_to_inset_data" value="'+regular_format_date+'"/>';
	
	var end_at='<div class="col-md-5"><div class="form-group"><label for="recipient-name" class="col-form-label date_info" style="font-size: 15px;">'+days_name[date.getDay()]+' ('+regular_format_date+')</label> <input type="time" name="heures_fin" min="0" max="24" required="required"  id="fin_'+regular_format_date+'" value="'+fin+'" class="form-control time_fin_edit" /></div></div>';
	var delete_this='<div class="col-md-2"><a style="margin-top:38px;" href="#" data-id="'+regular_format_date+'" class="delete_day btn btn-danger">-</a></div></div>';
	console.log("Ok");
	return start_at+end_at+hidden_input+delete_this;

}

var new_inputs_date_edit_date=function(date)
{
	/*var regular_format_date=date.getFullYear() + "-"
	+ (date.getMonth() + 1) + "-" + date.getDate();*/

	var regular_format_date = date.getFullYear()+"-"+ ('0' + (date.getMonth()+1)).slice(-2) + "-"+('0' + date.getDate()).slice(-2);

	console.log("Date  to edit : : "+regular_format_date);


	var days_name= new Array(7);
	days_name[0]="Dimanche";
	days_name[1]="Lundi";
	days_name[2]="Mardi";
	days_name[3]="Mercredi";
	days_name[4]="Jeudi";
	days_name[5]="Vendredi";
	days_name[6]="Samedi";



	var start_at='<div id="k_'+regular_format_date+'" class="col-md-12 '+regular_format_date+'"><div class="col-md-5"><div class="form-group"><label for="recipient-name" style="font-size: 15px;" class="col-form-label date_info">'+days_name[date.getDay()]+' ('+regular_format_date+')</label> <input type="time" min="0" max="24" name="heures_deb" id="deb_'+regular_format_date+'" value="" required="required" class="form-control time_deb_edit" /></div></div>';
	var end_at='<div class="col-md-5"><div class="form-group"><label for="recipient-name" class="col-form-label date_info" style="font-size: 15px;">'+days_name[date.getDay()]+' ('+regular_format_date+')</label> <input type="time" name="heures_fin" min="0" max="24" required="required"  id="fin_'+regular_format_date+'" value="" class="form-control time_fin_edit" /></div></div>';
	var hidden_input='<input type="hidden" name="date_to_inset_data" value="'+regular_format_date+'"/>';
	
	var delete_this='<div class="col-md-2"><a style="margin-top:38px;" href="#" data-id="'+regular_format_date+'" class="delete_day btn btn-danger">-</a></div></div>';
	console.log("Ok");
	
	return start_at+end_at+hidden_input+delete_this;

}











