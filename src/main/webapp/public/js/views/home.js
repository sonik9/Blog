/**
 * Created by Vitalii on 7/20/2016.
 */
$(document).ready(function () {
    $(".year-item").on("click",function (e) {
        e.preventDefault();
        $(this).parent().find(".month-list").toggleClass("show");
    });
});
