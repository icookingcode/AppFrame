/*求和函数*/
function sumAll() {
    var i, sum = 0;
    for (i = 0; i < arguments.length; i++) {
        sum += arguments[i];
    }
    return sum;
}

/*寻找最大值*/
function findMax() {
    var i, max = arguments[0];
    
    if(arguments.length < 2) return max;
 
    for (i = 0; i < arguments.length; i++) {
        if (arguments[i] > max) {
            max = arguments[i];
        }
    }
    return max;
}
/*JavaScript 闭包*/
var add = (function () {
		var counter = 0;
		return function (x) {
				if(x === undefined){
					return counter = 0 ;
				}
				return counter += x;
			}
})();
function addOne(){
	document.getElementById("demo4").innerHTML= add(1);
}
function resetCounter(){
	document.getElementById("demo4").innerHTML= add();
}

/*检测cookie是否可用*/
function checkCookies(){
	if (navigator.cookieEnabled==true){
		alert("Cookies 可用")
	}
	else{
		alert("Cookies 不可用")
	}
}
/*转换为大写*/
function upperCase(){
	var x=document.getElementById("fname");
	x.value=x.value.toUpperCase();
}