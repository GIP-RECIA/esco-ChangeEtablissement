
<script type="text/javascript"><rs:compressJs>

	/*
	 * Don't overwrite an existing myPortletName variable, just add to it
	 */
	var ${n} = ${n} || {refCount: 0};
	${n}.refCount ++;
	
	// Add refCount to current portlet container element. 
	var scriptTag = document.getElementsByTagName('script');
	scriptTag = scriptTag[scriptTag.length - 1];
	var parentTag = scriptTag.parentNode;
	parentTag.className = parentTag.className + ' refCount_' + ${n}.refCount;
	
	/*
	 * Switch jQuery to extreme noConflict mode, keeping a reference to it in the myPortletName namespace
	 */
	${n}.jQuery = jQuery.noConflict(true);
	 
	/**
	 * Use an anonymous function to initialize all JavaScript for this portlet.
	 */
	(function($, namespace, refCount) {
		changeEtabPortlet.init($, namespace, refCount);
	})(${n}.jQuery, '${n}', 'refCount_' + ${n}.refCount);
	
</rs:compressJs></script>