(function(){
	// ---------------- general data----------------------------
		//---------------------------- url for reqeust ---------------------------------
	var url = 'http://46.101.231.222:8080';
	var urlAuth = url + '/oauth/token';			// ----------- receipt token -------------
	var urlCurrentUser = url + '/restful/api/currentuser';	//---------- receipt info about current user -----------
	var urlUpdateCurrentUser = url + '/restful/api/updatecurrentuser';	//--------- update info about current user -----------
	var urlSignUp = url + '/restful/api/signup';	//--------------- sign up ---------------------------

	var dataReffreshToken = {};
	var refreshToken;
	var accessToken;

	function organizationDataReffreshToken(dataText){
		dataReffreshToken.client_id = 'rest_user';
		dataReffreshToken.grant_type = 'refresh_token';
		refreshToken = dataText.refresh_token;
		dataReffreshToken.refresh_token = refreshToken;
	}
	
	function organizationAccessToken(dataText){
		jQuery.ajax({
			url: urlAuth,
			dataType: 'json',
			type: 'POST',
			data: dataReffreshToken,
			success: function(data, textStatus){
				accessToken = data.access_token;
			},
			complete: function(){
				getCurrentUser();
			}
		});
	}

	function setCookie (cookie_name, value){
		var cookie_string = cookie_name + "=" + value;
		document.cookie = cookie_string;
	}

	function deleteCookie (cookie_name){
  		var cookie_date = new Date ( );  // current date
  		cookie_date.setTime ( cookie_date.getTime() - 1 );
  		document.cookie = cookie_name += "=; expires=" + cookie_date.toGMTString();
	}

	function getCookie (cookie_name){
	  	var results = document.cookie.match ( '(^|;) ?' + cookie_name + '=([^;]*)(;|$)' );
	 
	  	if ( results )
	    	return ( unescape ( results[2] ) );
	  	else
	    	return null;
	}

	//-------------- login current user -------------------------------
		//------------------------ data for first requesy--------------------------------------
		var dataNewRequest = {
			grant_type : 'password',
			client_id : 'rest_user'
		}

		function displayUserInfo(dataUser){
			var helloMsg = 'Hello, ' + dataUser.firstname + ' ' + dataUser.lastname  + '! ' + '(' +  dataUser.login + ')';
			jQuery('.login_user > span').text(helloMsg);
			jQuery('.login_user').css({'visibility' : 'visible'});
		}

		function getCurrentUser(){
			jQuery.ajax({
				url: urlCurrentUser,
				dataType: 'json',
				type: 'POST',
				data: {
					'access_token': accessToken
				},
				success: function(data, textStatus){
					displayUserInfo(data);
				},
				error: function(data){
				}
			});
		}

		function saveLoginStatus(){
			if (getCookie('is_user_login'))
				deleteCookie('is_user_login');

			setCookie('is_user_login', refreshToken, 'secure');
		}

		if (getCookie('is_user_login')){
			var currentUser = {}
			currentUser.refresh_token = getCookie('is_user_login');
			organizationDataReffreshToken(currentUser);
			organizationAccessToken(currentUser);
		}

		jQuery(document).on('click', '.wrap_button > input', function(){

			dataNewRequest.username = jQuery('.login > input').val();
			dataNewRequest.password = jQuery('.password > input').val();

			jQuery.ajax({
				url: urlAuth,
				dataType: 'json',
				type: 'POST',
				data : dataNewRequest,
				success: organizationDataReffreshToken,
				complete: function(data, textStatus){
					if (data.status === 400 ){
						alert('Incorrect login or password! Try again!');
					}else if(data.status === 200){
						organizationAccessToken(data);
						saveLoginStatus();
					}
				}

			});	
		});

	//------------------------ create new customer -----------------------------------
		//----------------- open new window for questionary---------------------------
		var currentWidthLightBox =  parseInt(jQuery('.new_customer').css('width'));	
		var currentHeightLightBox =  parseInt(jQuery('.new_customer').css('height'));
		var scrollY;
		var positionLeft;
		var positionTop;

		function locateLightBox(){
			positionLeft = (window.innerWidth - currentWidthLightBox)/2;
			positionTop = (window.innerHeight -  currentHeightLightBox)/3.5;
			jQuery('.new_customer').css({
				'left': positionLeft + 'px',
				'top' : positionTop + 'px'
			});
		}

		jQuery(document).on('click', '.create_account > span', function(){
			jQuery('.new_customer_background').show();
			jQuery('.new_customer').show();
			locateLightBox();
		});

		jQuery(window).scroll(function(){
			scrollY = positionTop + jQuery(window).scrollTop();
			jQuery('.new_customer').css({
				'top' : scrollY + 'px'
			});
		});

		jQuery(window).resize(function(){
			locateLightBox();
		});


			//---------------- close window for questionary ------------------------------
		jQuery(document).on('click', '.new_customer_background, .new_account_btn_cancel', function(){
			jQuery('.new_customer_background').hide();
			jQuery('.new_customer').hide();
		});
	
		//------------------ ajax request for create customer -------------------------
		var newUser = {};

		function validationLogin(email){ 		// check for "@"
			if (/@/.test(email)){
				jQuery('.new_account_login').css({'border' : '1px solid #FFFFFF'});
				return true;
			}

			jQuery('.new_account_login').css({'border' : '1px solid #FF0000'});
			return false;
		}

		function validationPassword(password){		// check for empty field
			if (password !== ''){
				jQuery('.new_account_password').css({'border' : '1px solid #FFFFFF'});
				return true;
			}

			jQuery('.new_account_password').css({'border' : '1px solid #FF0000'});
			return false;
		}

		function validationFirstName(firstname){		// check for empty field
			if (firstname !== ''){
				jQuery('.new_account_firstname').css({'border' : '1px solid #FFFFFF'});
				return true;
			}

			jQuery('.new_account_firstname').css({'border' : '1px solid #FF0000'});
			return false;
		}

		function validationLastName(lastname){		// check for empty field
			if (lastname !== ''){
				jQuery('.new_account_lastname').css({'border' : '1px solid #FFFFFF'});
				return true;
			}

			jQuery('.new_account_lastname').css({'border' : '1px solid #FF0000'});
			return false;
		}
		
		jQuery(document).on('click', '.new_account_btn_create', function(){
			var validLogin = validationLogin(jQuery('.new_account_login > input').val());
			var validPassword = validationPassword(jQuery('.new_account_password > input').val());
			var validFirstname = validationFirstName(jQuery('.new_account_firstname > input').val());
			var validLastname = validationLastName(jQuery('.new_account_lastname > input').val());
			
			if (validLogin && validPassword && validFirstname && validLastname){
				newUser.login = jQuery('.new_account_login > input').val();
				newUser.password = jQuery('.new_account_password > input').val();
				newUser.firstname = jQuery('.new_account_firstname > input').val();
				newUser.lastname = jQuery('.new_account_lastname > input').val();	
		
				jQuery.ajax({
					url: urlSignUp,
					dataType: 'json',
					contentType: 'application/json; cherset=utf-8',
					type: 'POST',
					data : JSON.stringify(newUser),
					success: function(data, textStatus){
						jQuery('.new_customer_background').hide();
						jQuery('.new_customer').hide();
						alert('You successful create new account!');
					},
					error: function(data, textStatus){
						debugger;
					}

				});	
			}
			
		});

})();