$(document).ready(function (){
	$("#btn1").click(function(){
		$("#p1").css({"color":"red","background-color":"yellow"})
			.slideUp(2000)
			.slideDown(2000,function(){
				$("#p1").attr("style","color:black;background-color:white;");
			});
	});
	
	$("#btn2").click(function (){
		var test = $("#p2").text();
		$("#p3").text(test);
	});
	$("#btn3").click(function (){
		var test = $("#p2").html();
		$("#p3").html(test);
	});
	$("#btn4").click(function(){
		var test = $("#guc_csdn").attr("href");
		$("#p4").text(test);
	});
	$("#btn5").click(function(){
		$("#guc_csdn").attr({"href":"https://blog.csdn.net/qq_31028313/article/details/81562016","title":"谷超的CSDN-连点"});
		// 通过修改的 title 值来修改链接名称
		title =  $("#guc_csdn").attr('title');
		$("#guc_csdn").html(title);
	});
	$("#btn6").click(function(){
		$("#p5").append("<b>尾部</b>");
	});
	$("#btn7").click(function(){
		$("#p5").prepend("<b>头部</b>");
	});
	$("#btn8").click(function(){
		$("img.custom2").remove();
	});
	$("#btn10").click(function(){
		$("img").remove(".custom2");
	});
	$("#btn9").click(function(){
		$("body").empty();
	});
	$("#btn11").click(function(){
		$("#div1").addClass("important")
	});
	$("#btn12").click(function(){
		$("#div1").addClass("blue")
	});
	$("#btn13").click(function(){
		$("#div1").toggleClass("blue")
	});
	//获取元素的直接父元素
	$("#span1").parent().css({"color":"red","border":"2px solid red"});
	//一路向上直到文档的根元素 (<html>) 过滤div
	$("#span2").parents("div").css({"color":"red","border":"2px solid red"});
	$("#span3").parentsUntil("div").css({"color":"red","border":"2px solid red"});
	//将contents.txt中的内容加载到id="div_load"的元素中
	$("#div_load_expression").load("./contents.txt #p_exp");
	$("#div_load").load("./contents.txt");
	$("#btn14").click(function(){
		$.get("./demo_test.php",function(data,status){
			var response = "数据: " + data + "\n状态: " + status;
			$("#p6").text(response);
		});
	});
	$("#btn15").click(function(){
		$.post("./demo_test_post.php",{
			name:"谷超的CSDN-连点",
			url:"https://blog.csdn.net/qq_31028313/article/details/81562016"
		},function(data,status){
			alert("数据: \n" + data + "\n状态: " + status);
		});
	});

});

function appendText(){
	var txt1="<p>文本一。</p>";              // 使用 HTML 标签创建文本
	var txt2=$("<p></p>").text("文本二。");  // 使用 jQuery 创建文本
	var txt3=document.createElement("p");
	txt3.innerHTML="文本三。";               // 使用 DOM 创建文本 text with DOM
	$("body").append(txt1,txt2,txt3);        // 追加新元素
}
function afterText(){
    var txt1="<b>I </b>";                    // 使用 HTML 创建元素
    var txt2=$("<i></i>").text("love ");     // 使用 jQuery 创建元素
    var txt3=document.createElement("big");  // 使用 DOM 创建元素
    txt3.innerHTML="jQuery!";
    $("img.custom").after(txt1,txt2,txt3);          // 在图片后添加文本
}
			