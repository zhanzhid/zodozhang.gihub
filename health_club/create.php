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
		<title>Create Account</title>
		<link rel="stylesheet" href="index.css">
		<script type = "text/javascript"  src = "verifyInput.js" > </script>
	</head>
	<body style= "background-image: url('accountbg.png'), url('accountbg3.png');background-repeat: no-repeat;
	  background-position: left bottom, right bottom;
	  background-attachment: fixed;">
<?php
  	include "header.php";
    include 'connectvars.php';
    $msg = "Enter your information!";

    $conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
  	if (!$conn) {
  		die('Could not connect: ' . mysql_error());
  	}
  	if ($_SERVER["REQUEST_METHOD"] == "POST") {
      $Id_num = mysqli_real_escape_string($conn, $_POST['Id_num']);
  		$Mname = mysqli_real_escape_string($conn, $_POST['Mname']);
  		$Address = mysqli_real_escape_string($conn, $_POST['Address']);
      $Age = mysqli_real_escape_string($conn, $_POST['Age']);

			$sql = "CREATE TRIGGER `createage` BEFORE INSERT ON `Members` FOR EACH ROW BEGIN IF new.Age <16 THEN 		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Members must be at least 16 years old to work out'; END IF; END";
      mysqli_query($conn,$sql);

      $queryIn = "SELECT * FROM Members where Id_num='$Id_num' ";
      $resultIn = mysqli_query($conn, $queryIn);
      $queryIn_1 = "SELECT * FROM Members where Mname='$Mname' ";
      $resultIn_1 = mysqli_query($conn, $queryIn_1);
      if (mysqli_num_rows($resultIn)> 0) {
        $msg ="Can't Add to Table Id_num: $Id_num already had in Account information";
      }
      else if (mysqli_num_rows($resultIn_1)> 0) {
        $msg ="Can't Add to Table Name: $Mname already had in Account information";
      }
      else {
      // attempt insert query
      $query = "INSERT INTO Members (Id_num, Mname, Address,Age) VALUES ('$Id_num', '$Mname', '$Address','$Age')";
        if(mysqli_query($conn, $query)){
          $msg =  "Record added successfully.";
        } else{
          $msg = "ERROR: Could not able to execute! " . mysqli_error($conn);

        }
      }
    }
?>
<section>
    <h2 style="text-align: center;margin: 10px; padding: 3px;  ">Creat Account</h2>
    <h3 style="text-align: center; margin: 10px; padding: 3px; "> <?php echo $msg; ?> </h3>
    <form method="post" id="addForm">
    <fieldset   style="  width: 400px; margin:auto;">
      <legend >Member Infomation:</legend>
      <p style="  width: 400px;  height: auto;padding:10px">
        <label for="sID">ID number:</label>
        <input type="number" min=1 max = 99999 class="required" name="Id_num" id="Id_num">
      </p>
      <p style="  width: 400px;  height: auto;padding:10px">
          <label for="Name">Name:</label>
          <input type="text" class="required" name="Mname" id="Mname">
      </p>
      <p style="  width: 400px;  height: auto;padding:10px">
          <label for="Color">Address:</label>
          <input type="text" class="required" name="Address" id="Address">
      </p>
      <p style="  width: 400px;  height: auto;padding:10px">
          <label for="sID">Age:</label>
          <input  type="number" min=1 max = 99999 class="required" name="Age" id="Age" title="sid should be numeric">
      </p>
    </fieldset>
    <p style="text-align: center;  margin: 5px; padding: 5px; ">
      <input type = "submit"  value = "Submit" />
      <input type = "reset"  value = "Clear Form" />
      <input type = "button"  value = "GO Back" onclick="window.location.href='http://web.engr.oregonstate.edu/~zhanzhid/cs340/project/account.php?user='"/>
    </p>
</form>
</body>
</html>
