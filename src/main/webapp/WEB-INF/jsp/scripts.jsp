
<script type="text/javascript"><rs:compressJs>

	/*
	 * Don't overwrite an existing myPortletName variable, just add to it
	 */
	var ${n} = ${n} || {};
	 
	/*
	 * Switch jQuery to extreme noConflict mode, keeping a reference to it in the myPortletName namespace
	 */
	${n}.jQuery = jQuery.noConflict(true);
	 
	/**
	 * Use an anonymous function to initialize all JavaScript for this portlet.
	 */
	(function($, namespace) {
		changeEtabPortlet.init($, namespace);
	})(${n}.jQuery, '${n}');
	
</rs:compressJs></script>