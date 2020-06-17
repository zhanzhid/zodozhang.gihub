<!DOCTYPE html>
<!-- Add Part Info to Table Part -->
<?php
		$currentpage="Home";
		include "pages.php";

?>
<style>
*{
 margin: 0;
 padding: 0;
}

html{
	margin: 0;
  padding: 0;
}

body{
 background-repeat: no-repeat;
 background-size: cover;
 background-position: top;
 background-image:url('homebg.png');
 width: 100%;
 height: 1000px;
 background-size: 100% 100%;
 letter-spacing: 0.02em;
 -webkit-font-smoothing: antialiased;
}

.bea{
	position: relative;
  width: 500px;
  height: 550px;
  top: 5%;
  left: 40%;
  background: inherit;
  border-radius: 2px;
  overflow: hidden;
	border-radius: 30px;
}
.bea:after{
	content: '';
  width: 600px;
  height: 500px;
  background: inherit;
  position: absolute;
  left: -25px;
  left position
  right: 0;
  top: -50px;
  top position
  bottom: 0;
  box-shadow: inset 0 0 0 200px rgba(255,255,255,0.05);
  filter: blur(10px);
	}
.bea1{
	position: relative;
	text-align: center;
	z-index: 1;
}
.bea1 > *{
	display: inline-block;
}
url(https://fonts.googleapis.com/css?family=Lato:900);
*, *:before, *:after{
  box-sizing:border-box;
}
body{
  font-family: 'Lato', sans-serif;
    ;
}
div.foo{
  width: 90%;
  margin: 0 auto;
  text-align: center;
}

.letter{
  display: inline-block;
  font-weight: 900;
  font-size: 2.5em;
  margin: 0.2em;
  position: relative;
  color: #00B4F1;
  transform-style: preserve-3d;
  perspective: 400;
  z-index: 1;
}
.letter:before, .letter:after{
  position:absolute;
  content: attr(data-letter);
  transform-origin: top left;
  top:0;
  left:0;
}
.letter, .letter:before, .letter:after{
  transition: all 0.3s ease-in-out;
}
.letter:before{
  color: #fff;
  text-shadow:
    -1px 0px 1px rgba(255,255,255,.8),
    1px 0px 1px rgba(0,0,0,.8);
  z-index: 3;
  transform:
    rotateX(0deg)
    rotateY(-15deg)
    rotateZ(0deg);
}
.letter:after{
  color: rgba(0,0,0,.11);
  z-index:2;
  transform:
    scale(1.08,1)
    rotateX(0deg)
    rotateY(0deg)
    rotateZ(0deg)
    skew(0deg,1deg);
}
.letter:hover:before{
  color: #fafafa;
  transform:
    rotateX(0deg)
    rotateY(-40deg)
    rotateZ(0deg);
}
.letter:hover:after{
  transform:
    scale(1.08,1)
    rotateX(0deg)
    rotateY(40deg)
    rotateZ(0deg)
    skew(0deg,22deg);
}

button.button1 {
    color: white;
		margin: 3px;
		padding: 3px;
    border: 3px solid #FB8F3D;
    background: -webkit-linear-gradient(top, #FDA251, #FB8F3D);
    background: -moz-linear-gradient(top, #FDA251, #FB8F3D);
    background: -ms-linear-gradient(top, #FDA251, #FB8F3D);
}

button.button1:hover {
		margin: 3px;
		padding: 3px;
    border: 1px solid #EB5200;
    background: -webkit-linear-gradient(top, #FD924C, #F9760B);
    background: -moz-linear-gradient(top, #FD924C, #F9760B);
    background: -ms-linear-gradient(top, #FD924C, #F9760B);
    box-shadow: 0 1px #EFEFEF;
}

button.button1:active {
    box-shadow: inset 0 1px 1px rgba(0,0,0,0.3);
}

</style>

<html>
	<head>
		<title>Health Club</title>
		<link rel="stylesheet" href="index.css">
		<link rel="stylesheet" href="//brick.a.ssl.fastly.net/Roboto:400"/>
	</head>
<body>

<?php
  	include "header.php";
  	include 'connectvars.php';
?>

<div class ="bea">
	<div class ="bea1">
		<div class="foo">
		  <span class="letter" data-letter="W">W</span>
		  <span class="letter" data-letter="E">E</span>
		  <span class="letter" data-letter="L">L</span>
		  <span class="letter" data-letter="C">C</span>
		  <span class="letter" data-letter="O">O</span>
		  <span class="letter" data-letter="M">M</span>
		  <span class="letter" data-letter="E">E</span>
			</div>
  	<a style="text-align: center;  margin: 3px;
		padding: 3px; font-family: Comic Sans MS;" >Physical fitness has proven to result in positive effects on the body's blood pressure because staying active and exercising regularly builds up a stronger heart. The heart is the main organ in charge of systolic blood pressure and diastolic blood pressure. Engaging in a physical activity will create a rise in blood pressure, once the activity is stopped, however, the individual's blood pressure will return to normal. The more physical activity that one engages in, the easier this process 	becomes, resulting in a more fitness individual. A "normal" blood pressure is considered to be 120/80 or below. Through regular physical fitness, the heart does not have to work as hard to create a rise in blood pressure, which lowers the force on the arteries, and lowers the over all blood pressure.</a>
		<p style="color:white; background-color: red; font-family: Comic Sans MS; ">For your health, join our Health Club and you will get more benfit for it</p>
		<button  class="button1" onclick="window.location.href='http://web.engr.oregonstate.edu/~zhanzhid/cs340/project/create.php?user='">Join US
		</button>

	</div>

</div>

</body>
</html>
