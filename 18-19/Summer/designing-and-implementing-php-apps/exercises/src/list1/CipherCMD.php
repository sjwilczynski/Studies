<?php

use ciphers\Cipher;
use ciphers\CipherException;

// Why do I get % at the end of the line?

include("Cipher.php");

if ($argc != 4) {
    exit("You should pass 3 parameters");
}

try {
    $cipher = new Cipher($argv[1], $argv[2]);
    $modifiedText = $cipher->cypher($argv[3]);
    echo $modifiedText;
} catch (CipherException $e) {
    exit("You should pass as key and values strings of the same lengths");
}

