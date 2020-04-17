function myFunction1(){
	x = document.getElementById("demo");
	x.innerHTML="Hello JAVASCRIPT";
}
function myFunction(a,b,c){
	a.innerHTML="你好世界！";
	b.innerHTML="你最近怎么样?";
	c.innerHTML="已经改变了";
}
function changeStyle(){
	element = document.getElementById("demo");
	element.style.color="#FF0000";
}
function changeImage(){
	element=document.getElementById('myimage')
	if (element.src.match("bulbon")){
		element.src="../i/pic_bulboff.gif";
	}else{
		element.src="../i/pic_bulbon.gif";
	}
}	
function validateInput(){
	var x=document.getElementById("demo2").value;
	if(x==""){
		alert("不能为空");
		return;
	}else if(isNaN(x)){
		alert("不是数字");
		return;
	}
	document.getElementById("demo2").value=square(x);
	
}
function showNowTime(element){
	element.innerHTML=Date();
}
//返回x的平方
function square(x){
	return x*x;
}
function testString(){
	element = document.getElementById('demo7');
	var hello = "Hello";
	element.innerHTML = hello +"的第三个字母是：\""+ hello[2] +"\"";
	element2 = document.getElementById('demo8');
	var x = 8;
	var str = "Hello World";
	var objStr = new String("Hello World");
	element2.innerHTML = "typeof 获取数据类型:<br>" + typeof x + typeof str + typeof objStr;
	element3 = document.getElementById('demo9');
	element3.innerHTML = str === objStr;
	element4 = document.getElementById('demo10');
	element4.innerHTML = "字符串的一些方法:<br>" +"charAt():"+ str.charAt(1)
							+"<br>concat():" + str.concat(hello) ; //拼接字符串
	element5 = document.getElementById('demo11');
	var guc = new employee("guc","android",1991);
	element5.innerHTML = guc.name;
	employee.prototype.salary = null;//往对象中添加一条属性和方法
	guc.salary = "10k";
	element5.innerHTML = guc.name + " salary:" + guc.salary;
	var andy = new employee("andy","测试",1990);
	andy.salary = "20k";
	element6 = document.getElementById('demo12');
	element6.innerHTML = andy.name + " "+andy.salary + andy.toString +" "+ andy.toString();//使用对象中的方法
	
}
//employee 对象
function employee(name,jobtitle,born){
	this.name = name;
	this.jobtitle = jobtitle;
	this.born = born;
	this.toString = function(){//定义对象中的方法
		return this.name + this.jobtitle + this.born;
	}
}
//通过if else 根据时间显示不同的问候语
function greeting(element){
	var time = new Date().getHours();
	var greeting = "早上好";
	if(time <10){
		greeting = "早上好";
	}else if(time >= 10 && time<13){
		greeting = "中午好";
	}else if(time >=13 && time<18){
		greeting = "下午好";
	}else{
		greeting = "晚上好";
	}
	element.innerHTML=greeting;
}
function getWeekday(element){
	var day = new Date().getDay();
	var nowday = "";
	switch(day){
		case 0:
		nowday ="今天是星期一";
		break;
		case 1:
		nowday ="今天是星期二";
		break;
		case 2:
		nowday ="今天是星期三";
		break;
		case 3:
		nowday ="今天是星期四";
		break;
		case 4:
		nowday ="今天是星期五";
		break;
		case 5:
		nowday ="今天是星期六";
		break;
		case 6:
		nowday ="今天是星期日";
		break;
		default:
		nowday = "unknow";
		break;
	}
	element.innerHTML=nowday;
}
//正则语法：/正则表达式主体/修饰符(可选)
function testRegex(){
	element = document.getElementById("demo14");
	var str = "Visit Runoob! hello runoob ！hello world!"; 
    var n = str.search(/Runoob/i);//search()使用正则表达式搜索字符串 匹配Runoob 不区分大小写 
	element.innerHTML= str + "中runoob匹配到起始位置为" + n;
	element2 = document.getElementById("demo15");
	var txt = str.replace(/Hello/ig,"你好");
	element2.innerHTML = str +" <br>替换了hello <br>" +txt;
	var patt1 = new RegExp("Hello","i");//表达式主体，修饰符
	element3 = document.getElementById("demo16");
	element3.innerHTML=str +"中是否包含Hello:" + patt1.test(str);
	
}
//判断字符串是否是由字母数字下划线组成
function isValid(str) { return /^\w+$/.test(str); }
//判断是否为全字母
function isLetter(str){return /^[a-zA-Z]+$/.test(str);}
//判断是否为全数字
function isNumber(str){return /^\d+$/.test(str);}

function testCustomException(inputElement,showElement){
	var x;
	showElement.innerHTML = "";//置空显示
	x=inputElement.value;//x = 输入框的值
	try{
		if(x == "") throw "值为空";
		if(isNaN(x)) throw "不是数字";
		x = Number(x);
		if(x < 5) throw "太小";
		if(x > 20) throw "太大了";
		showElement.innerHTML="输入正确";
	}catch(err){
		showElement.innerHTML="错误："+err;
	}
	
}

function validateForm(){
	var x = document.forms["myForm"]["fname"].value;
	 if (x == null || x == "") {
        alert("需要输入名字。");
        return false;
    }
}
function validateInputWithApi(){
	var inputObj = document.getElementById("input1");
	if(!inputObj.checkValidity()){
		document.getElementById("demo18").innerHTML=inputObj.validationMessage;
	}else{
		document.getElementById("demo18").innerHTML="输入正确";
	}
}