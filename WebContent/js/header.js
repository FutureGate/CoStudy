(function ($) {
  $(document).ready(function(){
	// hide .navbar first
	$(".fixed.menu").hide();
	
	// fade in .navbar
	$(function () {
		$(window).scroll(function () {
            // set distance user needs to scroll before we fadeIn navbar
			if ($(this).scrollTop() > 100) {
				$(".fixed.menu").transition("fade in");
			} else {
				$(".fixed.menu").transition("fade out");
			}
		});

	
	});

});
  }(jQuery));