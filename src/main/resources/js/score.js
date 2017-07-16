jQuery(function() {
    jQuery(".create-athlete-button").click(function () {
        jQuery(".athlete-form-container").load("athlete/createForm")
    });

    jQuery(".delete-athlete").click(function () {
        id = this.id.split("-")[1];
        jQuery.ajax("athlete/"+id, {
            method: "delete"
        })
    })
});