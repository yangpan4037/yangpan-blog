/**
 * time	2017-08-10
 * name	杨潘 yangpan
 * tel	13541292110
 * mail 916780135@qq.com
 */

 $(function(){
 	var original=$('#originalPic'),
 	wrap=$('.original-wrap'),
 	defaultInfo=wrap.find('.default-info'),
 	fileBtn=$('#fileBtn'),
 	bigPic=$('#bigPic'),
 	middlePic=$('#middlePic'),
 	smallPic=$('#smallPic');

 	var imgType= ["gif", "jpeg", "jpg", "bmp", "png"];
	function filePath(file) {
	    var url = null;
	    if (window.createObjectURL != undefined) {
	        url = window.createObjectURL(file);
	    } else if (window.URL != undefined) {
	        url = window.URL.createObjectURL(file);
	    } else if (window.webkitURL != undefined) {
	        url = window.webkitURL.createObjectURL(file);
	    }
	    return url;
	}
	function preViewPic(arr,src){
		$.each(arr,function(index,el){
			arr[index].attr('src',src).css('z-index',2);
		});
	}
	wrap.click(function(){
		fileBtn.click();
	});
	fileBtn.change(function(){
		var maxFileSize=4;
		file=this.files[0],
		sizeM=Math.pow(1024,2);
		if(file.size>maxFileSize*sizeM){
			$.ALERT("你上传的文件大小为"+(file.size/sizeM).toFixed(2)+'M,'+'可是最大只能上传'+maxFileSize+'M的图片啊');
			return;
		}
	    var imgSrc=filePath(file);
	    if(!RegExp("\.(" + imgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
	        $.ALERT("选择文件错误,图片类型必须是" + imgType.join("，") + "中的一种");
	        return;
	    }
	    defaultInfo.hide();
	    preViewPic([original,bigPic,middlePic,smallPic],imgSrc);
	});
 });