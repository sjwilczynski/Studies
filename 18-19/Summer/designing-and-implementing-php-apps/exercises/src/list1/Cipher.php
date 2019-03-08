<?php

namespace ciphers;

use Exception;


class CipherException extends Exception {
}

class Cipher {
    private $cipherMap;

    /**
     * @throws CipherException
     */
    public function __construct(string $oldChars, string $newChars) {
        if (mb_strlen($oldChars) != mb_strlen($newChars)) {
            throw new CipherException("Different lengths of character arrays to cipher");
        }

        $oldCharsArray = self::mbStringToArray($oldChars);
        $newCharsArray = self::mbStringToArray($newChars);

        $this->cipherMap = array();

        for ($i = 0; $i < count($oldCharsArray); $i += 1) {
            $this->cipherMap[$oldCharsArray[$i]] = $newCharsArray[$i];
        }
    }

    public function cypher(string $text): string {
        $modifiedText = "";

        $charArray = $this->mbStringToArray($text);
        foreach ($charArray as $char) {
            $newChar = array_key_exists($char, $this->cipherMap) ? $this->cipherMap[$char] : $char;
            $modifiedText = $modifiedText . $newChar;
        }

        return $modifiedText;
    }

    public static function mbStringToArray(string $text) {
        return preg_split('//u', $text, null, PREG_SPLIT_NO_EMPTY);
    }
}
