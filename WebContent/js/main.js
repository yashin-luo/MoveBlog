var Conf = {
	'host':'http://www.oschina.com',
	'auth_uri':'/action/oauth2/authorize',
	'response_type':'code',
	'client_id':'yKqX3IQWBvft0W8JXz0k',
	'redirect_uri':'http://www.moveblog.com:8080/Oauth2Action'
};

var Api = (function(conf,$){
	
	var api = {};
	
	var uri = {
		'user':'/action/user',
		'login':conf.auth_uri + 
				'?response_type=' + conf.response_type + 
				'&client_id=' + conf.client_id +
				'&redirect_uri=' + encodeURIComponent(conf.redirect_uri),
		'blog_list':''
	};
	
	var getCookie = function (name){
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
		else 
			return null;
	};
	
	var ajax = function(url,callback,data,async){
		return $.ajax({
			url:url,
			type:'POST',
			dataType:'json',
			async:typeof async === 'undefined' ? true : async,
			data:data,
			success:function(response){
				var data = typeof response === "object" ? response : JSON.parse(response);
				callback && callback(data);
			},
			error:callback
		}).responseText;
	};
	
	var getUserInfo = function(callback){
		var user_id = getCookie('user');
		return ajax(uri.user,callback,{user_id:user_id});
	};
	
	var doLogin = function(){
		location.href = conf.host + uri.login;
	};
	
	var getBlogList = function(url){
		
	};
	
	api.ajax = ajax;
	api.user = getUserInfo;
	api.login = doLogin;
	api.blog_list = getBlogList;
	api.cookie = getCookie;
	
	return api;
	
})(Conf,jQuery);

$(function(){
	
	//≤È—Ø login user –≈œ¢
	Api.user(function(){
		
	});
	
});
