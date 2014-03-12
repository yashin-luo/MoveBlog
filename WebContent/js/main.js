/*var Conf = {
	'host':'http://www.oschina.net',
	'auth_uri':'/action/oauth2/authorize',
	'response_type':'code',
	'client_id':'c39L0n9Am23s5wTW10dC',
	'redirect_uri':'http://move.pengbo.us/Oauth2Action'
};
*/

/*var Conf = {
		'host':'http://www.oschina.com',
		'auth_uri':'/action/oauth2/authorize',
		'response_type':'code',
		'client_id':'yKqX3IQWBvft0W8JXz0k',
		'redirect_uri':'http://www.moveblog.com:8080/Oauth2Action'
	};
*/

var Conf = {
		'host':'http://www.oschina.com',
		'auth_uri':'/action/oauth2/authorize',
		'response_type':'code',
		'client_id':'BjvoHIHodS09uBtT6wIx',
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
				'<img class="import_loading" src="img/loading2.gif">',
				'<img class="import_ok" src="img/ok.png" style="height:10px;">',
				'<input type="checkbox" id="blog_{id}" data-url="{link}"/>',
				'<span>',
					'<a href="{link}" title="按住Ctrl点击在新页查看《{title}》" target="_blank">{title}</a>',
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
				if(response==null)
					return;
				ajaxErrorHandler(response,callback);
			},
			error:callback
		}).responseText;
	};
	
	var ajaxErrorHandler = function(response,callback,onError){
		var data = typeof response === "object" ? response : eval('('+response+')');
		
		if(data == null){
			return;
		}
		
		if(data.status == 500){
			alert('500 服务器内部错误');
			return;
		}
		if(data.error){
			if(onError){
				return onError(data);
			}
			if(data.code == 0){
				alert(data.error);
				return;
			}else if(data.code == 1){
				delCookie('user');
				location.reload();
				return;
			}
		}
		callback && callback(data);
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
	
	var importBlog = function(arr,len,before,callback){
		var length = arr.length;
		if(length==0)
			return;
		var url = arr.shift();
		before && before(url,len-length);
		return ajax(uri.import_list,function(data){
			callback && callback(data,url,len-length);
			importBlog(arr,len,before,callback);
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
	api.importBlog = importBlog;
	api.on_error = ajaxErrorHandler;
	
	return api;
	
})(Conf,jQuery);

$(function(){
	
	var $user_info = $('.user-info'),
		$blog_provider = $('.blog-providers'),
		$input_url = $('input[name="url"]'),
		$blog_list = $('.blog-list');
		$submit = $('#submit'),
		$cancel = $('#cancel'),
		$importBlog = $('#importBlog'),
		$select_all = $('#select-all'),
		$wp_image = $('.blog-providers ul li.wordpress img'),
		$upload_form = $('#search-form form'),
		$input_file = $('input[name="file"]');
	
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
		if(!Api.logined()){
			if(confirm('OSChina未授权，需刷新页面，并重新登录！')){
				location.reload();
			}
			return;
		}
		var is_upload = !$input_file.attr('disabled') && $input_file.val().length>0;
		if(is_upload){
			$upload_form.submit();
			return;
		}
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
			$importBlog.show();
			$select_all.show();
			if(list!=null){
				$blog_list.html(Api.blog_list_tpl(list));
			}
		});
	});
	
	//取消爬取的博客列表
	$cancel.on('click',function(){
		var is_upload = !$input_file.attr('disabled') && $input_file.val().length>0;
		if(is_upload){
			location.reload();
			return;
		}
		$blog_list.removeClass('loading');
		$(this).hide();
		$submit.show();
		$importBlog.hide();
		$select_all.hide();
		$blog_list.html('');
		$input_url.removeAttr('disabled');
		$input_file.removeAttr('disabled');
		$input_url.focus();
	});
	
	//开始导入所选博客
	$importBlog.on('click',function(){
		var import_tasks = $('.blog-list input[type="checkbox"]:checked');
		if(import_tasks.length==0){
			alert('先选定一篇博客吧！');
			return;
		}
		var urls = import_tasks.map(function(){return $(this).data('url');}).toArray();
		Api.importBlog(urls,urls.length,function(url,index){
			var input = import_tasks.eq(index);
			var li = input.parents('li');
			li.attr('class','loading');
		},function(data,url,index){
			var input = import_tasks.eq(index);
			var li = input.parents('li');
			if(data.error && data.code==0){
				alert(data.error);
				li.removeAttr('class');
			}else{
				li.attr('class','imported');
				input.remove();
			}
		});
	});
	
	//点击博客列表选定或取消，按住ctrl点击查看对应博客
	$blog_list.on('click','a',function(event){
		if(!event.ctrlKey){
			var self = $(this);
			var input = self.parent().siblings('input');
			if(input.is(':checked')){
				input.removeAttr('checked');
			}else{
				input.attr('checked',true);
			}
			event.preventDefault();
			return false;
		}
	});
	
	//全选或取消全选
	$select_all.on('click',function(){
		var inputs = $blog_list.find('input[type="checkbox"]');
		var checked = $blog_list.find('input[type="checkbox"]:checked');
		if(inputs.length==checked.length && inputs.length!=0){
			$select_all.html('全选');
			inputs.removeAttr('checked');
		}else if(inputs.length!=0){
			$select_all.html('取消全选');
			inputs.attr('checked',true);
		}
	});
	
	//选择导入wordpress存档文件
	$wp_image.on('click',function(){
		var self = $(this);
		if(self.is('.gray')){
			$input_file.show().removeAttr('disabled');
			$input_url.hide().attr('disabled','disabled');
		}else{
			$input_file.hide().attr('disabled','disabled');;
			$input_url.show().removeAttr('disabled');;
		}
		self.toggleClass('gray');
	});
	
	$upload_form.ajaxForm({
		dataType:'json',
		beforeSubmit:function(){
			$blog_list.html('');
			$blog_list.addClass('loading');
		},
		success:function(data){
			Api.on_error(data,function(list){
				$blog_list.removeClass('loading');
				$submit.hide();
				$cancel.show();
				$importBlog.show();
				$select_all.show();
				if(list!=null){
					$blog_list.html(Api.blog_list_tpl(list));
				}
			},function(data){
				alert(data.error);
				$blog_list.removeClass('loading');
			});
		},
		error:function(xhr){
			Api.on_error(xhr);
		}
	});
});
