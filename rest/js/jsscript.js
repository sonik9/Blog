(function(){
	// ---------------- general data----------------------------
		//---------------------------- url for reqeust ---------------------------------
	var url = 'http://localhost:8080';
	var urlAuth = url + '/oauth/token';			// ----------- receipt token -------------
	var urlCurrentUser = url + '/restful/api/currentuser';	//---------- receipt info about current user -----------
	var urlUpdateCurrentUser = url + '/restful/api/updatecurrentuser';	//--------- update info about current user -----------
	var urlSignUp = url + '/restful/api/signup';	//--------------- sign up ---------------------------
	var urlPost = url + '/restful/api/post';  //-------------------- post --------------------------

	var dataReffreshToken = {};
	var refreshToken;
	var accessToken;
	var currentUserLogin =  {};

	function organizationDataReffreshToken(dataText){
		dataReffreshToken.client_id = 'rest_user';
		dataReffreshToken.grant_type = 'refresh_token';
		refreshToken = dataText.refresh_token;
		dataReffreshToken.refresh_token = refreshToken;
	}
	
	function organizationAccessTokenNewUser(dataText){
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
			for (var i in dataUser){
				currentUserLogin[i] = dataUser[i];
			}
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

		var currentUser = {};
		if (getCookie('is_user_login')){
			currentUser.refresh_token = getCookie('is_user_login');
			organizationDataReffreshToken(currentUser);
			organizationAccessTokenNewUser(currentUser);
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
						organizationAccessTokenNewUser(data);
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
			jQuery('.edit_current_user').hide();
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

		//-------------------------- edit current user data--------------------------

		var leftEditWindow = (jQuery(window).innerWidth() - parseInt(jQuery('.edit_current_user').css('width')))/2;
		jQuery('.edit_current_user').css('left', leftEditWindow + 'px');

		jQuery(document).on('click', '.edit_current_user_cancel', function(){
			jQuery('.new_customer_background').hide();
			jQuery('.edit_current_user').hide();
		});
		
		jQuery(document).on('click', '.edit_user_data', function(e){
			e.preventDefault();

			jQuery('.edit_login > input').val(currentUserLogin.login);
			jQuery('.edit_firstname > input').val(currentUserLogin.firstname);
			jQuery('.edit_lastname > input').val(currentUserLogin.lastname);
			jQuery('.edit_photolink > img').attr('src', currentUserLogin.photoLink);
			jQuery('.edit_gender > select').val(currentUserLogin.gender);

			jQuery('.edit_current_user').show();
			jQuery('.new_customer_background').show();
		});


		function organizationAccessTokenForEditUser(dataText){
			jQuery.ajax({
				url: urlAuth,
				dataType: 'json',
				type: 'POST',
				data: dataReffreshToken,
				success: function(data, textStatus){
					accessToken = data.access_token;
				},
				complete: function(){
					editUserData(dataText);
				}
			});
		}

		function editUserData(editUser){
			jQuery.ajax({
				url: urlUpdateCurrentUser + '?access_token=' + accessToken,
				dataType: 'json',
				contentType: 'application/json; cherset=utf-8',
				type: 'POST',
				data: JSON.stringify(editUser),
				success: function(data, textStatus){
					displayUserInfo(data);
					jQuery('.new_customer_background').hide();
					jQuery('.edit_current_user').hide();
				},
				error: function(data){
					alert(data.responseJSON.message);
				}
			});
		}

		jQuery(document).on('click', '.edit_current_user_submit > input', function(){
			var editUser = {};
			editUser.login = jQuery('.edit_login > input').val();
			editUser.firstname = jQuery('.edit_firstname > input').val();
			editUser.lastname = jQuery('.edit_lastname > input').val();
			editUser.gender = jQuery('.edit_gender > select').val();
			editUser.password = jQuery('.edit_apply_password > input').val();

			currentUser.refresh_token = getCookie('is_user_login');

			organizationDataReffreshToken(currentUser);
			organizationAccessTokenForEditUser(editUser);

		});

		//------------------ show posts -------------------------------
		function createPost(post){
			
			function smallDescr(elem){
				var smallDescr = [];
				var allDescr = jQuery(elem).text().split(' ');	
				for (var i=0; i<70; i+=1){
					smallDescr.push(allDescr[i]);
				}

				return smallDescr.join(' ');
			}

			jQuery(post).each(function(){
				var newPostTitle = jQuery('<div class="post_title"></div>').text(this.pstTitle);
				var newPostImg = jQuery('<img class="post_image">').attr('src', this.pstTitle);
				//var newPostDescription = jQuery('<div class="post_descr"></div>').append(jQuery.parseHTML(this.pstDocument));
				var textsmallDecsr = smallDescr(jQuery.parseHTML(this.pstDocument));
				var newPostDescription = jQuery('<div class="post_descr"></div>').text(textsmallDecsr);
				var newPostFirstName = jQuery('<div class="post_firstname"></div>').text('Posted by: ' + this.firstname);
				var newPostLastName = jQuery('<div class="post_lastname"></div>').text(this.lastname);

				var newWrapPost = jQuery('<div class="wrap_one_post"></div>');
				newWrapPost.append(newPostTitle).append(newPostDescription).append(newPostFirstName).append(newPostLastName);

				jQuery('.wrap_post').append(newWrapPost);	
			});
		}

		jQuery.ajax({
			url: urlPost + '?rows=99&page=0',
			dataType: 'json',
			type: 'GET',
			success: function(data, dataText){
				createPost(data);
			},
			error: function(data, dataText){
				
			}
		});

})();