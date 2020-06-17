<!DOCTYPE html>
<!-- Add Part Info to Table Part -->
<?php
		$currentpage="Account";
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
		<title>Account</title>
		<link rel="stylesheet" href="index.css">
		<script type = "text/javascript"  src = "verifyInput.js" > </script>

	</head>
<body style= "background-image: url('accountbg.png'), url('accountbg3.png');background-repeat: no-repeat;
  background-position: left bottom, right bottom;
  background-attachment: fixed;">
<?php
  	include "header.php";
  	include 'connectvars.php';
    $conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    if (!$conn) {
      die('Could not connect: ' . mysql_error());
    }

    // query to select all information from supplier table
    $query = "SELECT * from Members ";

    // Get results from query
    $result = mysqli_query($conn, $query);
    if (!$result) {
      die("Query to show fields from table failed");
    }
    // get number of columns in table
    $fields_num = mysqli_num_fields($result);
    echo "<table id='t01' style = '	margin-left:auto;
			margin-right:auto;' border='1'><tr>";

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


  // See if sid is already in the table


    mysqli_close($conn);
?>
	<h1 style="text-align: center; margin: 10px; padding: 3px; ">Account Information</h1>
  <p style="text-align: center; margin: 5px; padding: 5px; ">
    <input  type="button" value = "Create Account" onclick="window.location.href='http://web.engr.oregonstate.edu/~zhanzhid/cs340/project/create.php?user='" />
    <input type = "button"  value = "Change Address" onclick="window.location.href='http://web.engr.oregonstate.edu/~zhanzhid/cs340/project/change.php?user='"/>
  </p>

</body>
</html>
