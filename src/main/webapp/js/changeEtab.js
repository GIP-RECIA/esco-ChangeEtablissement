var changeEtabPortlet = changeEtabPortlet || {};

changeEtabPortlet.init = function($, namespace, refCount, undefined) {

	// Init container 1
	(function initContainer1($, namespace, refCount, undefined) {
		var container = '.' + refCount + ' .' + namespace + 'container1';

		$(window).bind('load', function() {
			// On load

			var form = $(container + ' form');
			var msgEtabChangeConfirm = $('.changeEtabPortletMessages .etabChangeConfirm').html();

			var selectEtab = $(container + ' .changeEtab-select');
			var submit = $(container + ' .changeEtab-submit');

			var defaultValue = 'currentEtab';
			var emptyValue = 'EMPTY';

			selectEtab.bind('change', function() {
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

			$(submit).bind('click', function() {
				// On submit

				var confirmed = confirm(msgEtabChangeConfirm);
				if (confirmed) {
					// submit form
					form.submit();
				}
			});

		});

	}($, namespace, refCount));

	// Init container 2
	(function initContainer2($, namespace, refCount, undefined) {
		var container = '.' + refCount + ' .' + namespace + 'container2';

		$(window).bind('load', function() {
			// On load

			var radios = $(container + ' form input[type=radio]');
			var form = $(container + ' form');
			var msgEtabChangeConfirm = $('.changeEtabPortletMessages .etabChangeConfirm').html();

			radios.bind('change', function(event) {
				var radio = event.target;
				console.log("change radio : ", radio);
				var confirmed = confirm(msgEtabChangeConfirm);
				if (confirmed) {
					form.submit();
				}
			});
		});
	}($, namespace, refCount));

	// Init container 3
	(function initContainer3($, namespace, refCount, undefined) {
		var container = '.' + refCount + ' .' + namespace + 'container3';

		initDialog(container, namespace);

	}($, namespace, refCount));

	// Init container 4
	(function initContainer4($, namespace, refCount, undefined) {
		var container = '.' + refCount + ' .' + namespace + 'container4';

		initDialog(container, namespace);

	}($, namespace, refCount));

	function initDialog(container, namespace) {

		$(window).bind('load', function() {
			// On load

			var msgEtabChangeTitle = $('.changeEtabPortletMessages .etabChangeTitle').html();
			var msgEtabChangeAction = $('.changeEtabPortletMessages .etabChangeAction').html();
			var msgEtabChangeCancel = $('.changeEtabPortletMessages .etabChangeCancel').html();
			var msgEtabChangeConfirm = $('.changeEtabPortletMessages .etabChangeConfirm').html();

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

							var confirmed = confirm(msgEtabChangeConfirm);
							if (confirmed) {
								// submit form
								form.submit();
							}
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