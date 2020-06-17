<!DOCTYPE html>
<?php
	$currentpage="About";
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
		<title>About</title>
		<link rel="stylesheet" href="index.css">
	</head>
<body style= "background-image: url('family.jpg');
  background-repeat: no-repeat;
  height: 500px; 
  background-position: center; 
  background-size: cover;
  background-attachment: fixed;">


<?php
	include 'header.php';
	include 'connectvars.php';	
	$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	if (!$conn) {
		die('Could not connect: ' . mysql_error());
	}
  // query to select all information from supplier table
	$query = "SELECT Hname AS Chain_Store, Address FROM Health_Club";


// Get results from query
	$result = mysqli_query($conn, $query);
	if (!$result) {
		die("Query to show fields from table failed");
	}
// get number of columns in table
	$fields_num = mysqli_num_fields($result);
	echo "<table id='t01' style = ' margin-left:auto; margin-right:auto;' border='2'><tr>";

// printing table headers
	for($i=0; $i<$fields_num; $i++) {
		$field = mysqli_fetch_field($result);
		echo "<td><b>$field->name</b></td>";
	}
	echo "</tr>\n";
	while($row = mysqli_fetch_row($result)) {
		echo "<tr>";
		foreach($row as $cell)
			echo "<td>$cell</td>";
		echo "</tr>\n";
	}
  //echo "<script>alert('text')</script>";
  
	//mysqli_free_result($result);
	mysqli_close($conn);
?>
  <h1 style="text-align: center; margin: 10px; padding: 3px; ">About us</h1>
<h2 class="callout" style="text-align: center; font-size: 20px; ">
	<div class="callout-header">From the start, we have been part of the community</div>
	<div class="callout-container">
	<p>Our increasing membership is a testament to our excellent service </p>
  <p>Our Club's services and equipment are always changing to ensure that our facilities reflect the ever-changing face of health and fitness. Come in today for a free tour of our facilities.<p>
   <p>                            </p>
   <p> Our contact information: Telephone number: 555-555-5555 </p>
   <p> Alternate Telephone number: 666-666-6666 </p>
   <p> Address: 2997 SE 3rd HealthyStreet, OR, 99999 </p>
   
	</div>
</h2>


</body>
</body>

</html>

