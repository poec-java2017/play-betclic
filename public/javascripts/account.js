var anchor = null;
$(document).ready(function() {
    setInterval(function(){
        if (anchor != window.location.hash && window.location.hash != "") {
            anchor = window.location.hash;

            // Retreive closest tab
            var innerTab = $(anchor + "-form").closest('.tab');

            // Click toggle "buttons"
            $('#toggle-' + innerTab.attr("id")).click();
            $(formId).siblings("input").click();
        }
    });
});