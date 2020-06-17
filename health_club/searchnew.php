<!DOCTYPE html>
<!-- Add Part Info to Table Part -->
<?php
		$currentpage="create";
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
		<title>Search coaches by your choice</title>
		<link rel="stylesheet" href="index.css">
		<script type = "text/javascript"  src = "verifyInput.js" > </script>
	</head>
	<body style= "background-image: url('searchc.jpg');background-repeat: no-repeat;
	  height: 500px; 
    background-position: center; 
    background-size: cover;
	  background-attachment: fixed;">
<?php
  	include "header.php";
    include 'connectvars.php';
    $msg = "Coaches cannot teach more than 5 classes";

    $conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
  	if (!$conn) {
  		die('Could not connect: ' . mysql_error());
  	}
      $Id_num = mysqli_real_escape_string($conn, $_POST['Id_num']);

      $query = " SELECT Fitness_Coach.Fname AS Coach, Fitness_Coach.Phone_number, Projects.Pname AS Projects
            FROM Fitness_Coach left OUTER join Projects on Fitness_Coach.Fname = Projects.Fname
            GROUP BY Projects.Fname
            HAVING COUNT(Projects.Fname) >= '$Id_num'";
      $result = mysqli_query($conn, $query);
      
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
<section>
    <h2 style="text-align: center;margin: 10px; padding: 3px;  ">Search coach who teach more than</h2>
    <h3 style="text-align: center; margin: 10px; padding: 3px; "> <?php echo $msg; ?> </h3>
    <form method="post" id="addForm">
    <fieldset   style="  width: 400px; margin:auto;">
      <legend >Coach Infomation:</legend>
      <p style="  text-align: center; width: 400px;  height: auto;padding:50px">
        <label for="sID" style=" color: white;" >Classes(num<5):</label> 
        <input type="number" min=1 max = 5 class="required" name="Id_num" id="Id_num">
      </p>
      <p style="text-align: center;  margin: 5px; padding: 5px; ">
      <input type = "submit" value = "Search" />
    </p>
    
</form>
</body>
</html>
