<?php

namespace ciphers;

use PHPUnit\Framework\TestCase;

// ./vendor/bin/phpunit --testdox ./tests -- run tests with pretty output

class CipherTest extends TestCase {

    /**
     * @throws CipherException
     */
    public function testReplaceTwoCharacters(): void {
        $this->performTest("AB", "CD", "XAABBXC", "XCCDDXC");
    }

    /**
     * @throws CipherException
     */
    public function testSameCharacterInBothParts(): void {
        $this->performTest("ABCE", "DEFX", "TO JEST TEKST DO PODMIANY ABC DEF",
            "TO JXST TXKST DO PODMIDNY DEF DXF");
    }


    /**
     * @throws CipherException
     */
    public function testDifferentLengthsOfParts(): void {
        $this->expectException(CipherException::class);
        $this->performTest("ABC", "AB", "SOME TEXT AAAA", "SOME TEXT BB");
    }

    /**
     * @throws CipherException
     */
    public function testMultiByteCharacters(): void {
        $this->performTest("ABćE", "ABXD", "SOME TEXT ćAćA", "SOMD TDXT XAXA");
    }

    /**
     * @throws CipherException
     */
    private function performTest(string $oldChar, string $newChars, string $oldText, string $newText): void {
        $cipher = new Cipher($oldChar, $newChars);
        $modifiedText = $cipher->cypher($oldText);
        self::assertEquals($newText, $modifiedText);
    }
}
