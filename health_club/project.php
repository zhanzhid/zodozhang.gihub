<!DOCTYPE html>
<?php
		$currentpage="Projects";
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
		<title>Projects</title>
		<link rel="stylesheet" href="index.css">
	</head>
<body style= "background-image: url('projects.jpg');background-repeat: no-repeat;
  height: 500px; 
  background-position: center; 
  background-size: cover;
  background-attachment: fixed;">


<?php
// change the value of $dbuser and $dbpass to your username and password
	include 'connectvars.php';
	include 'header.php';

	$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	if (!$conn) {
		die('Could not connect: ' . mysql_error());
	}

// query to select all information from supplier table
	$query = "SELECT Pname AS Projects, Fname AS Coach, Level, Time, Price FROM Projects ";


// Get results from query
	$result = mysqli_query($conn, $query);
	if (!$result) {
		die("Query to show fields from table failed");
	}
// get number of columns in table
	$fields_num = mysqli_num_fields($result);
	echo "<table id='t01' style = ' margin-left:auto; margin-right:auto;' border='1'><tr>";

// printing table headers
	for($i=0; $i<$fields_num; $i++) {
		$field = mysqli_fetch_field($result);
		echo "<td><b>$field->name</b></td>";
	}
	echo "</tr>\n";
	while($row = mysqli_fetch_row($result)) {
		echo "<tr>";
		// $row is array... foreach( .. ) puts every element
		// of $row to $cell variable
		foreach($row as $cell)
			echo "<td>$cell</td>";
		echo "</tr>\n";
	}

	mysqli_free_result($result);
	mysqli_close($conn);
?>
  <h1 style="color: white; text-align: center; margin: 10px; padding: 3px; ">Projects Today</h1>
</body>

</html>


