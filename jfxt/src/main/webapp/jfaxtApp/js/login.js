$(function(){
	var allVal='';
	//email blur
	$("input[name='username']").blur(function(allVal){
		var emails =  /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		var usernameVal = $(this).val();
		if(emails.test(usernameVal)){
			allVal='correct!';
		}else if(usernameVal==''){
			allVal='Email cannot be empty!';
			layerOpen(allVal);
		}else{
			allVal='You fill in the email is not correct!';
			layerOpen(allVal);
		}
	});
	
	//password blur
	$("input[name='password']").blur(function(allVal){
		var paswVal = $(this).val();
		if(paswVal.length==''){
			allVal='Password is not correct!';
			layerOpen(allVal);
		}else{
			allVal='You fill in the email is not correct!';
		}
	});
	
	//login button
	$(".login-btn").on('click',function(){
		 layer.open({
			type: 2
			,content: '登录中...'
			,time: 2
		 });
	});
	
	//general layer
	function layerOpen(allVal){
		console.log(allVal);
		layer.open({
			content: allVal,
			btn: 'I know the'
		});
	}
	
	
});