dropdownBrands = $("#brand");
dropdownCategories = $("#category");

$(document).ready(function() {

    $("#shortDescription").richText();
    $("#fullDescription").richText();

    dropdownBrands.change(function() {
        dropdownCategories.empty();
        getCategories();
    });

    getCategories();
});


function getCategories() {
    brandId = dropdownBrands.val();
    url = brandModuleURL + "/" + brandId + "/categories";

    $.get(url, function(responseJson) {
        $.each(responseJson, function(index, category) {
            $("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
        });
    });
}