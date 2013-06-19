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
					submit.removeAttr('disabled').removeClass('ui-state-disabled');
					submit.show();
					submit.parent().addClass('etabSelected');
				} else {
					submit.attr('disabled', 'disabled').addClass('ui-state-disabled');
					submit.hide();
					submit.parent().removeClass('etabSelected');
				}
			});
			
			selectEtab.trigger('change');
			
		});
	}($, namespace));
	
	// Init container 2
	(function initContainer2($, namespace, undefined) {
		var container = '#' + namespace + 'container2';
		
		initDialog(container, namespace);
		
	}($, namespace));
	
	// Init container 3
	(function initContainer3($, namespace, undefined) {
		var container = '#' + namespace + 'container3';
		
		initDialog(container, namespace);
		
	}($, namespace));
	
	function initDialog(container, namespace) {

		$(window).bind('load', function() {
			// On load
			
			var msgEtabChangeTitle = $('.changeEtabPortletMessages .etabChangeTitle').html();
			var msgEtabChangeAction = $('.changeEtabPortletMessages .etabChangeAction').html();
			var msgEtabChangeCancel = $('.changeEtabPortletMessages .etabChangeCancel').html();
			
			var form = $(container + ' form');
			var radios = $(container + ' form input[type=radio]');
			var button = $(container + ' .changeEtab-button');
			var dialog = $(container + ' .changeEtab-dialog');

			button.bind('click', function(){
				// On select button click
				
				form[0].reset();
				
				dialog.dialog({
					dialogClass: namespace + '-dialog',
					modal: true,
					title: msgEtabChangeTitle,
					resizable: false,
					width: '700px',
					buttons: [{
					        	  text: msgEtabChangeAction,
					        	  click: function() {
					        		  $( this ).dialog( "close" );
					        		  
					        		  // submit form
					        		  form.submit();
					        	  }
					          }, {
					        	  text: msgEtabChangeCancel,
					        	  click: function() {
					        		  $( this ).dialog( "close" );
					        	  }
					          }]
					});
				
					var actionButton = $('.' + namespace + '-dialog' + " :button:contains('" + msgEtabChangeAction + "')");
					actionButton.attr('disabled', 'disabled').addClass('ui-state-disabled');
			});
			
			radios.bind('change', function(event) {
				var radio = event.target;
				
				var actionButton = $('.' + namespace + '-dialog' + " :button:contains('" + msgEtabChangeAction + "')");
				
				if(radio.value) {
					actionButton.removeAttr('disabled').removeClass('ui-state-disabled');
				} else {
					actionButton.attr('disabled', 'disabled').addClass('ui-state-disabled');
				}
				
			});
			
		});
		
	}
	
};