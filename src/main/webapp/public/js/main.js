/**
 * Created by Vitalii on 24.06.2015.
 */
function sizeH(bytes) {
    if (typeof bytes !== 'number') {
        return '';
    }
    if (bytes >= 1000000000) {
        return (bytes / 1000000000).toFixed(2) + ' GB';
    }
    if (bytes >= 1000000) {
        return (bytes / 1000000).toFixed(2) + ' MB';
    }
    return (bytes / 1000).toFixed(2) + ' Kb.';
}
$.validattion = (function () {
    var app = {

        initialize: function () {
            this.modules();
            this.setUpListeners();
        },

        modules: function () {

        },

        setUpListeners: function () {
            $('form').on('submit', app.submitForm);
            $('form').on('focusout', 'input', app.validateInput);
            $('form').on('keydown', 'input', app.removeTooltip);
            //$('form').on('focusout', 'select', app.validateInput);

        },

        removeTooltip: function () {
            $(this).tooltip('destroy').parent('.form-group').removeClass('has-error');
        },


        validateItem: function (item) {
            var item = $(item),
                val = item.val(),
                attr = item.attr('data-validation'),
                formGroup = item.parents('.form-group'),
                label = formGroup.find('label').text().toLowerCase(),
                textError = 'Put' + label,
                doIf = function (text) {
                    var child = formGroup.children('.chosen-container, .mce-tinymce');
                    if (child.length > 0) {
                        item = child;
                        item.css('border-color','#a94442').children('a, ul').css('border-color','#a94442')
                    }
                    item.tooltip({
                        triger: 'manual',
                        placement: 'top',
                        title: text
                    }).tooltip('show'),
                        formGroup.addClass('has-error').removeClass('has-success'),

                        valid = false;
                },
                doElse = function () {
                    var child = formGroup.children('.chosen-container, .mce-tinymce');
                    if (child.length > 0) {
                        item = child;
                        item.css('border-color','#a94442').children('a, ul').css('border-color','#3c763d')
                    }
                    item.tooltip('destroy');
                    formGroup.addClass('has-success').removeClass('has-error');
                }


            switch (attr) {
                case 'length':
                {
                    var length = item.attr('data-validation-length');
                    if (val.length < length) {
                        //item.after("<p datatype='tooltip'>Length of this field can't be less then " + length + ".</p>");
                        doIf('Length of this field can\'t be less then ' + length);
                    } else {
                        doElse();
                    }
                    break;
                }
                case 'selection':
                {
                    var count = item.attr('data-validation-count'),
                        sel = item.find(':selected');
                    if (sel.length < count || sel.val().length === 0) {
                        doIf('Count of this field can\'t be les then ' + count);
                    } else {
                        doElse();
                    }
                    break;
                }
            }
        },

        validateInput: function (e) {
            e.preventDefault();
            var items = $(this);
            if (!app.validateItem(items)) {
                return false;
            }
        },

        submitForm: function (e) {
            e.preventDefault();
            var form = $(this);
            if (app.validateForm(form)) {
                form[0].submit();
            }
        },

        validateForm: function (form) {
            var inputs = form.find('*[data-validation]');
            valid=true;
            inputs.tooltip("destroy");
            $.each(inputs, function (index, val) {
                app.validateItem(val);
            });
            return valid;
        }

        },
        valid;

    app.initialize();
})
;
