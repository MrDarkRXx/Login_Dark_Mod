<?php
//api url filter
if(strpos($_SERVER['REQUEST_URI'],"DB.php")){
    require_once 'Utils.php';
    PlainDie();
}

$conn = new mysqli("localhost", "user", "pass_badededatos", "Name_base_de_datos");
if($conn->connect_error != null){
    die($conn->connect_error);
}