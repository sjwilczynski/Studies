<?php


for ($i = 1; $i <= 7; $i++) {
    $data = array("id" => $i, "email" => "e" . $i . "@email.com", "description" => "desc" . $i, "points" => $i, "active" => boolval($i % 2));
    $json = json_encode($data);

    $file = fopen("account" . $data["id"], "w");
    fwrite($file, $json);
    fclose($file);
}