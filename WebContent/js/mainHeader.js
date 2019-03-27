(function ($) {
  $(document).ready(function(){
    
	// hide .navbar first
	$("#mainMenu").hide();
	
	// fade in .navbar
	$(function () {
		$(window).scroll(function () {
            // set distance user needs to scroll before we fadeIn navbar
			if ($(this).scrollTop() > 200) {
				$('#mainMenu').fadeIn();
			} else {
				$('#mainMenu').fadeOut();
			}
		});

	
	});

});
  }(jQuery));