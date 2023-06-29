dropdownProducts = $("#products");
divChosenProducts = $("#chosenProducts");

$(document).ready(function() {

    dropdownProducts.change(function() {
        divChosenProducts.empty();
        showChosenProducts();
    });

    showChosenProducts();
});

function showChosenProducts() {
    dropdownProducts.children("option:selected").each(function() {
        selectedProduct = $(this);
        catId = selectedProduct.val();
        catName = selectedProduct.text();

        divChosenProducts.append("<span class='badge bg-secondary m-1'>" + catName + "</span>");
    });
}