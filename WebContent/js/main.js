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
		'blog_list':'/action/spider',
		'import_list':'/action/moveblog'
	};
	
	var blog_tpl = [
		'<li>',
			'<label for="blog_{id}">',
				'<input type="checkbox" id="blog_{id}" data-url="{link}"/>',
				'<span>',
					'{title}',
				'</span>',
			'</label>',
		'</li>'
	].join('\n');
	
	
	var getCookie = function (name,value){
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
		else 
			return null;
	};
	
	var delCookie = function(name){
	    var exp = new Date();
	    exp.setTime(exp.getTime() - 1);
	    var cval=getCookie(name);
	    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
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
	
	var checkLogin = function(){
		return getCookie('user')!=null;
	};
	
	var doLogout = function(){
		delCookie('user');
		location.reload();
	};
	
	var getBlogList = function(url,callback){
		return ajax(uri.blog_list,callback,{
			url:url
		});
	};
	
	var detectBlogType = function(url){
		var blog_types = ['csdn','cnblogs','51cto','iteye'];
		return $.map(blog_types,function(type){
			return url.indexOf(type)>-1?type:'';
		}).join('');
	};
	
	var generateBlogList = function(arr){
		var ul = $('<ul>');
		for(var i=0;i<arr.length;i++){
			var blog = arr[i];
			var li = blog_tpl
					.replace(/\{link\}/ig,blog.link)
					.replace(/\{id\}/ig,i)
					.replace(/\{title\}/ig,blog.title);
			ul.append(li);
		}
		return ul;
	};
	
	var importBlog = function(arr,callback){
		if(arr.length==0)
			return;
		var url = arr.shift();
		return ajax(uri.import_list,function(data){
			callback && callback(data,url);
			importBlog(arr,callback);
		},{
			link:url
		});
	};
	
	api.ajax = ajax;
	api.cookie = getCookie;
	api.rcookie = delCookie;
	
	api.user = getUserInfo;
	api.login = doLogin;
	api.logout = doLogout;
	api.logined = checkLogin;
	
	api.blog_list = getBlogList;
	api.blog_type = detectBlogType;
	api.blog_list_tpl = generateBlogList;
	api.import = importBlog;
	
	return api;
	
})(Conf,jQuery);

$(function(){
	
	var $user_info = $('.user-info'),
		$blog_provider = $('.blog-providers'),
		$input_url = $('input[name="url"]'),
		$blog_list = $('.blog-list');
		$submit = $('#submit'),
		$cancel = $('#cancel'),
		$import = $('#import');
	
	//查询 login user 信息
	Api.user(function(user){
		if(!user){
			Api.rcookie('user');
			return;
		}
		var img = $user_info.find('img');
		var login = $user_info.find('a.login');
		var logout = $user_info.find('a.logout');
		
		img.attr('src',user.avatar);
		
		login.attr('href',user.url+'/blog')
			.attr('target','_blank')
			.text(user.name+'的博客');
		logout.show();
		
		$input_url.removeAttr('disabled');
		$input_url.focus();
	});
	
	//识别博客归属
	$input_url.on('keyup change',function(){
		var self = $(this);
		var url = self.val();
		var type = Api.blog_type(url);
		var img = $blog_provider.find('li img');
		img.addClass('gray');
		if(type && type.length>0){
			var cur_provider = $blog_provider.find('li.'+type);
			var cur_img = cur_provider.find('img');
			cur_img.removeClass('gray');
		}
	});
	
	//爬取博客列表
	$submit.on('click',function(){
		if(!Api.logined())
			return;
		var url = $input_url.val();
		if(url.length == 0)
			return;
		$blog_list.html('');
		$blog_list.addClass('loading');
		Api.blog_list(url,function(list){
			$blog_list.removeClass('loading');
			$input_url.attr('disabled','disabled');
			$submit.hide();
			$cancel.show();
			$import.show();
			if(list!=null){
				$blog_list.html(Api.blog_list_tpl(list));
			}
		});
	});
	
	//取消爬取的博客列表
	$cancel.on('click',function(){
		$blog_list.removeClass('loading');
		$(this).hide();
		$submit.show();
		$import.hide();
		$blog_list.html('');
		$input_url.removeAttr('disabled');
		$input_url.focus();
	});
	
	//开始导入所选博客
	$import.on('click',function(){
		var import_tasks = $('.blog-list input[type="checkbox"]:checked');
		if(import_tasks.length==0){
			alert('先选定一篇博客吧！');
			return;
		}
		var urls = import_tasks.map(function(){return $(this).data('url');}).toArray();
		Api.import(urls,function(data,url){
			console.log(data,url);
		});
	});
	
});
