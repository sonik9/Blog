/**
 * Created by Vitalii on 24.06.2015.
 */

var message;
function notify(messages, type, trans) {
    $('.top-right').notify({
        message: {text: messages},
        fadeOut: {enabled: true, delay: 150000},
        type: type,
        transition: trans
    }).show();
}

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
};



(function ($) {
    $.validattion = (function (customFunction) {
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
                                item.css('border-color', '#a94442').children('a, ul').css('border-color', '#a94442')
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
                                item.css('border-color', '#a94442').children('a, ul').css('border-color', '#3c763d')
                            }
                            item.tooltip('destroy');
                            formGroup.addClass('has-success').removeClass('has-error');
                        }


                    switch (attr) {
                        case 'length': {
                            var length = item.attr('data-validation-length');
                            if (val.length < length) {
                                //item.after("<p datatype='tooltip'>Length of this field can't be less then " + length + ".</p>");
                                doIf('Length of this field can\'t be less then ' + length);
                            } else {
                                doElse();
                            }
                            break;
                        }
                        case 'selection': {
                            var count = item.attr('data-validation-count'),
                                sel = item.find(':selected');
                            if (sel.length < count || sel.val().length === 0) {
                                doIf('Count of this field can\'t be les then ' + count);
                            } else {
                                doElse();
                            }
                            break;
                        }
                        case 'email': {
                            var mail = item.val();
                            $.ajax({
                                method: "GET",
                                url: "http://apilayer.net/api/check",
                                async: false,
                                data: {
                                    access_key: "77002e93372fd8809551bb809958b393",
                                    email: mail,
                                    smtp: 1,
                                    format: 1
                                }
                            }).done(function (e) {
                                console.log(e.format_valid, e.smtp_check);
                                if(!e.format_valid){
                                    doIf('Wrong email format !');
                                }
                                if(!e.smtp_check){
                                    doIf('Invalid email!');
                                }
                                if(e.format_valid && e.smtp_check){
                                    doElse();
                                }
                            }).fail(function (e) {
                                console.log(e);
                                var regxMail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                                if(!regxMail.test(mail)){
                                    doIf('Wrong email format !');
                                }else doElse();
                            });
                            break;
                        }
                        case 'pass':{
                            var pass=item.val();
                            var regxPass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
                            if(!regxPass.test(pass)){
                                doIf('Invalid format!\nMinimum 8 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet and 1 Number');
                            }else doElse();
                            break;
                        }
                        case 'pass-copy':{
                            var pass=item.val();
                            var form = $('form');
                            var orig =$('#'+item.attr('orig')).val();
                            if(pass!==orig){
                                doIf('Invalid confirmation!');
                            }else doElse();
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
                        if (typeof customFunction !== 'undefined' && $.isFunction(customFunction)) {
                            customFunction();
                            //
                        } else {
                            form[0].submit();
                        }


                    }
                },

                validateForm: function (form) {
                    var inputs = form.find('*[data-validation]');
                    valid = true;
                    inputs.tooltip("destroy");
                    $.each(inputs, function (index, val) {
                        app.validateItem(val);
                    });
                    return valid;
                }
            },
            valid;

        app.initialize();
    });
})(jQuery);

/*
 var Validation =  function (form) {
 this.form = form;
 }

 Validation.prototype.init = function () {

 }

 Validation.prototype.serchInputs = function () {

 }*/
