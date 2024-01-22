window.onscroll = function() {
    fixNavbarOnScroll();
};

function fixNavbarOnScroll() {
    var navbar = document.querySelector('.navbar');

    if (window.pageYOffset >= 60) {
        navbar.classList.add('fixed-navbar');
    } else {
        navbar.classList.remove('fixed-navbar');
    }
}
