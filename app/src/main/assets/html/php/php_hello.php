<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
	<title>第一个PHP代码</title>
	</head>
	<body>
	<?php 
	//单行注释
	# 也是注释
	/*
	多行注释
	*/
	$test = "<h1>Hello World</h1>";
	$test_trans = "<p>你好 世界</p>";
	echo $test,$test_trans; 
	$x = 5;
	$y = 6;
	$z = $x+$y;//全局变量
	
	function myTest(){
		$local_p=100;//局部变量
		global $z;//global 关键字用于函数内访问全局变量
		echo "<p>测试函数内变量：</p>";
		echo "变量local_p为：$local_p";
		echo "<br>";
		echo "全局变量z为:$z";
	}
	myTest();
	?> 
	</body>
</html>