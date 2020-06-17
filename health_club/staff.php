<!DOCTYPE html>
<?php
		$currentpage="Staff";
		include "pages.php";
?>
<style>
*{
 margin: 0;
 padding: 0;
}
</style>
<html>
	<head>
		<title>Staff</title>
		<link rel="stylesheet" href="index.css">
	</head>
<body>



<?php
// change the value of $dbuser and $dbpass to your username and password
	include 'connectvars.php';
	include 'header.php';

	$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	if (!$conn) {
		die('Could not connect: ' . mysql_error());
	}

	mysqli_close($conn);
?>


<div class="bg-img">
</body>

<form>
	<input class="coachbutton"
  style="position: relative;
  left: 575px;
  top: 230px;
  width: 300px;
  border: 15px solid green;
  padding: 50px;
  margin: 40px;"
  type="button" value="Fitness Coach" onclick="window.location.href='http://web.engr.oregonstate.edu/~zhanzhid/cs340/project/coach.php?user='" />
</form>	
<form>
  <input class="wkrbutton"
  style="position: relative;
  left: 930px;
  top: 5px;
  width: 300px;
  border: 15px solid green;
  padding: 50px;
  margin: 40px;"
  type="button" value="Worker" onclick="window.location.href='http://web.engr.oregonstate.edu/~zhanzhid/cs340/project/worker.php?user='" />
</form>

<html>
<body>
<div class="paragraph">
	<p>Tips</p>
	<p>You can check our fitness coach and staff that are working now</p>
	<p>You can also search for experienced coach in our fitness center</p>
	<p>Feel free to contact us at 555-555-5555 at any time whenever you need help!</p>
	<p>Our community will try the best to help you ASAP</p>
	<p>If injured or see some suspicious people, please immediately call 911</p>
</div>
</body>
</html>

</div>

</html>




