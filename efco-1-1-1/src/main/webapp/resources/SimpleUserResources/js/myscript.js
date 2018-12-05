
jQuery(document).on("click",".edit_stagiaire",
		function() {

	jQuery("#nom_stag_to_edit").val(
			jQuery("#nom_value_" + jQuery(this).attr("data-id"))
			.text());

	
	jQuery("#prenom_stag_to_edit").val(
			jQuery("#prenom_value_" + jQuery(this).attr("data-id"))
			.text());

	
	jQuery("#email_stag_to_edit").val(
			jQuery("#email_value_" + jQuery(this).attr("data-id"))
			.text());

	
	jQuery("#emailstag_to_edit").val(
			jQuery("#email_value_" + jQuery(this).attr("data-id"))
			.text());

	
	jQuery("#adresse_stag_to_edit").val(
			jQuery("#adresse_value_" + jQuery(this).attr("data-id"))
			.text());

	
	jQuery("#tel_stag_to_edit").val(
			jQuery("#tel_value_" + jQuery(this).attr("data-id"))
			.text());

	
	jQuery("#stagiaire_id_to_edit").val(
			jQuery(this).attr("data-id"));

	jQuery("#edit_stagiaire").modal("show");

});



