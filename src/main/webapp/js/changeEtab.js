var changeEtabPortlet = changeEtabPortlet || {};

changeEtabPortlet.init = function($, namespace) {
	
	// Init container 1
	(function initContainer1($, namespace, undefined) {
		var container = '#' + namespace + 'container1';
	
		$(window).bind('load', function() {
			// On load
			
			var selectEtab = $(container + ' .changeEtab-select');
			var submit = $(container + ' .changeEtab-submit');
	
			var defaultValue = 'currentEtab';
			var emptyValue = 'EMPTY';
			
			selectEtab.bind('change', function(){
				// On select Etab change
				
				if (selectEtab.val() === emptyValue) {
					selectEtab.val(defaultValue);
				}
				
				if (selectEtab.val() !== defaultValue) {
					submit.removeAttr('disabled');
					submit.parent().addClass('etabSelected');
				} else {
					submit.attr('disabled', 'disabled');
					submit.parent().removeClass('etabSelected');
				}
			});
			
			selectEtab.trigger('change');
			
		});
	}($, namespace));
	
	// Init container 2
	(function initContainer2($, namespace, undefined) {
		var container = '#' + namespace + 'container2';
	
		$(window).bind('load', function() {
			// On load
			
			var button = $(container + ' .changeEtab-button');
			var dialog = $(container + ' .changeEtab-dialog');
			
			var defaultValue = 'currentEtab';
			var emptyValue = 'EMPTY';
			
			button.bind('click', function(){
				// On select button click
				
				dialog.dialog({
					dialogClass: "no-close",
					modal: true,
					buttons: [{
					        	  text: "OK",
					        	  click: function() {
					        		  $( this ).dialog( "close" );
					        	  }
					          }]
					});
				
			});
			
		});
	}($, namespace));
	
};