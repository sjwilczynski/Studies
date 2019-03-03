<?php

namespace ciphers;
//How does it work??

use Exception;


class CipherException extends Exception {
}

class Cipher {
    private $cipherMap;

    /**
     * @throws CipherException
     */
    function __construct(string $oldChars, string $newChars) {
        if (strlen($oldChars) != strlen($newChars)) {
            throw new CipherException("Different lengths of character arrays to cipher");
        }
        $this->cipherMap = array();
        for ($i = 0; $i < strlen($oldChars); $i += 1) {
            $this->cipherMap[$oldChars[$i]] = $newChars[$i];
        }
    }

    function cypher(string $text): string {
        $modifiedText = "";
        foreach (str_split($text) as $char) {
            $newChar = array_key_exists($char, $this->cipherMap) ? $this->cipherMap[$char] : $char;
            $modifiedText = $modifiedText . $newChar;
        }
        return $modifiedText;
    }
}
