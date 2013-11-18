
$(document).ready(function() {
	  $('#scoreScreen').css('display','block');
	  $('#vestScore').css('display','none');
	  $('#istScore').css('display','none');
});

$('.ist-score').click(function(){
	  $('#scoreScreen').css('display','none');
	  $('#vestScore').css('display','none');
	  $('#istScore').css('display','block');
});
$('.vest-score').click(function(){
	  $('#scoreScreen').css('display','none');
	  $('#vestScore').css('display','block');
	  $('#istScore').css('display','none');
});

$('.cancel').click(function(){
	  $('form').each(function() { this.reset(); });

	  $('#scoreScreen').css('display','block');
	  $('#vestScore').css('display','none');
	  $('#istScore').css('display','none');
});

$(".hideOnClick").click(function(){
	$(this).css('display', 'none');
})

