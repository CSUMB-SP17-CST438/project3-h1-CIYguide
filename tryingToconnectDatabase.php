<?php
   $con=mysqli_connect("mysql10.000webhost.com","username","password","db_name");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
	
   $username = $_POST['username'];
   $password = $_POST['password'];
   $result = mysqli_query($con,"SELECT Role FROM table1 where 
   Username='$username' and Password='$password'");
   $row = mysqli_fetch_array($result);
   $data = $row[0];

   if($data){
      echo $data;
   }
	
   mysqli_close($con);
?>

URL url = new URL(link);
String data  = URLEncoder.encode("username", "UTF-8") 
+ "=" + URLEncoder.encode(username, "UTF-8");
data += "&" + URLEncoder.encode("password", "UTF-8") 
+ "=" + URLEncoder.encode(password, "UTF-8");
URLConnection conn = url.openConnection(); 


<?php
   $con=mysqli_connect("example.com","username","password","database name");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $username = $_GET['username'];
   $password = $_GET['password'];
   $result = mysqli_query($con,"SELECT Role FROM table1 where Username='$username' 
      and Password='$password'");
   $row = mysqli_fetch_array($result);
   $data = $row[0];

   if($data){
      echo $data;
   }
   mysqli_close($con);
?>
