<?php

use ciphers\Cipher;
use ciphers\CipherException;
use PHPUnit\Framework\TestCase;

class CipherTest extends TestCase {

    /**
     * @throws CipherException
     */
    public function testSimple(): void {
        $this->performTest("AB", "CD", "XAABBXC", "XCCDDXC");
    }

    /**
     * @throws CipherException
     */
    public function testSameCharacter(): void {
        $this->performTest("ABCE", "DEFX", "TO JEST TEKST DO PODMIANY ABC DEF",
            "TO JXST TXKST DO PODMIDNY DEF DXF");
    }


    /**
     * @throws CipherException
     */
    public function testDifferentLengths(): void {
        $this->expectException(CipherException::class);
        $this->performTest("ABC", "AB", "SOME TEXT AAAA", "SOME TEXT BB");
    }

    /**
     * @throws CipherException
     */
    private function performTest(string $oldChar, string $newChars, string $oldText, string $newText): void {
        $cipher = new Cipher($oldChar, $newChars);
        $modifiedText = $cipher->cypher($oldText);
        $this->assertEquals($newText, $modifiedText);
    }
}
