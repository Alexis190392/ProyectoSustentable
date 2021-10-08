/* global bootstrap: false */
(function() {
    'use strict'
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    tooltipTriggerList.forEach(function(tooltipTriggerEl) {
        new bootstrap.Tooltip(tooltipTriggerEl)
    })
})()

/* modal */
var myModal = document.getElementById('myModal')
var myInput = document.getElementById('myInput')

myModal.addEventListener('shown.bs.modal', function() {
    myInput.focus()
})

/*Spinner */
$(window).on('load', function() {
    setTimeout(function() {
        $(".loader-page").css({ visibility: "hidden", opacity: "0" })
    }, 1);

});