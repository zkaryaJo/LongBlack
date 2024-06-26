//로딩 함수
function loading(hide) {
    if (!hide) {
        var loadingHtml = `
            <div class="loading-body">
                <div class="loading">
                	<div class="loading-wrapper">
	                    <span></span>
	                    <span></span>
	                    <span></span>
	                 </div>   
                    <p class="loading-text">잠시만 기다려 주세요</p>
                </div>
            </div>
        `;
        
        $('body').append(loadingHtml);
    } else {
        $('.loading-body').remove();
    }
}
//날짜 함수
function setTodayDate() {
    var today = new Date();
    var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    var dateString = today.toLocaleDateString('ko-KR', options);
    $(".wheather-date").text(dateString);
}


$(document).ready(function() {
	
	//모바일 사이드메뉴
	$('#more-menu').on('click', function() {
        $('.mobile-side-menu').addClass('open');
    });
    
    $('#side-close-btn').on('click', function() {
        $('.mobile-side-menu').removeClass('open');
    });
    
	//모바일 독바
	
    var lastScrollTop = 0;
    var isExecuted = false;

   $(window).on('scroll', function() {
    var st = $(this).scrollTop(); 
    var windowBottom = window.innerHeight + window.scrollY; 
    var documentHeight = document.body.offsetHeight; 

    if (!isExecuted && windowBottom >= documentHeight) {
        isExecuted = true;
        $('.mobile-dock-bar').css('bottom', '-100%');
    } else {
        isExecuted = false;
        if (st > lastScrollTop) {
            $('.mobile-dock-bar').css('bottom', '-100%');
        } else {
            $('.mobile-dock-bar').css('bottom', '0');
        }
    }

	
	    lastScrollTop = st; 
	});
    
    var currentPath = window.location.pathname;

	//메뉴 액티브 
    $('.nav-link').each(function() {
        var $this = $(this);
        var href = $this.attr('href');

        if (currentPath === href) {
            $this.closest('.nav-item').addClass('active');
        } else {
            $this.closest('.nav-item').removeClass('active');
        }
    });
    
	$('.dropdown').removeClass('active');
    $('.dropdown-item').each(function() {
        var $this = $(this);
        var href = $this.attr('href');

        if (currentPath === href) {
            $this.closest('.dropdown').addClass('active');
        }
    });
});
