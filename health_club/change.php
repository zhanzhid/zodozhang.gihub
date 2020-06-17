<!DOCTYPE html>
<!-- Add Part Info to Table Part -->
<?php
		$currentpage="Change Address";
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
		<title>Change Address</title>
		<link rel="stylesheet" href="index.css">
		<script type = "text/javascript"  src = "verifyInput.js" > </script>
	</head>

  <body style= "background-image: url('accountbg.png'), url('accountbg3.png');background-repeat: no-repeat;
    background-position: left bottom, right bottom;
    background-attachment: fixed;">

  <?php
  	include "header.php";
  	$msg = "Enter your Address which you want to change";
  	include 'connectvars.php';

    $conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
  	if (!$conn) {
  		die('Could not connect: ' . mysql_error());
  	}
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
      $Id_num = mysqli_real_escape_string($conn, $_POST['Id_num']);
  		$Address = mysqli_real_escape_string($conn, $_POST['Address']);

      $sql = "CREATE TRIGGER `ChangeAddress` AFTER UPDATE ON `Members`  FOR EACH ROW BEGIN IF (old.Address = new.Address) THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'You did not change the address'; END IF; END";
      mysqli_query($conn,$sql);
      $queryIn = "SELECT * FROM Members where Id_num='$Id_num' ";
      $resultIn = mysqli_query($conn, $queryIn);

      if (mysqli_num_rows($resultIn) == 0){
          $msg ="ERROR: Could not able to execute! Id Number: $Id_num is not exist";
      }
      else{
        $query = "UPDATE Members SET Address='$Address' WHERE Id_num= '$Id_num';";
        if(mysqli_query($conn, $query)){
          $msg =  "New Address has been add.";
        } else{
          $msg = "ERROR: Could not able to execute! " . mysqli_error($conn);
        }
      }
    }
?>
<section>
    <h2 style="text-align: center; margin: 10px; padding: 3px;  ">Change Address</h2>
    <h3 style="text-align: center; margin: 10px; padding: 3px; "> <?php echo $msg; ?> </h3>
    <form method="post" id="addForm">
    <fieldset style="  width: 400px;height: 130px;
  margin:auto;">
      <legend>Change:</legend>
      <p style="  width: 400px;  height: auto;padding:10px">
        <label for="sID" >Your ID number:</label>
        <input type="number" min=1 max = 99999 class="required" name="Id_num" id="Id_num">
      </p>
      <p style="  width: 400px;">
          <label for="Color">New Address:</label>
          <input type="text" class="required" name="Address" id="Address">
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
