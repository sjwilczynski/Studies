<?php

$data_folder = __DIR__ . "/../data/";
$file_prefix = "account";

foreach (glob($data_folder . $file_prefix . '*') as $file) {
    unlink($file);
}