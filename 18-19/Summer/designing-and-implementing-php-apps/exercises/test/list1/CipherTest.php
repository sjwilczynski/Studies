<?php

namespace list1;

use PHPUnit\Framework\TestCase;

// ./vendor/bin/phpunit --testdox ./test -- run test with pretty output

class CipherTest extends TestCase {


    /**
     * @throws CipherException
     */
    public function testDifferentLengthsOfParts(): void {
        $this->expectException(CipherException::class);
        $this->testCypher("ABC", "AB", "SOME TEXT AAAA", "SOME TEXT BB");
    }

    /**
     * @throws CipherException
     * @dataProvider cypherProvider
     */
    public function testCypher(string $oldChar, string $newChars, string $oldText, string $newText): void {
        $cipher = new Cipher($oldChar, $newChars);
        $modifiedText = $cipher->cypher($oldText);
        self::assertEquals($newText, $modifiedText);
    }

    public function cypherProvider() {
        return [
            'replace two characters' => ["AB", "CD", "XAABBXC", "XCCDDXC"],
            'same characters in both parts' => ["ABCE", "DEFX", "TO JEST TEKST DO PODMIANY ABC DEF",
                "TO JXST TXKST DO PODMIDNY DEF DXF"],
            'multi byte characters' => ["ABćE", "ABXD", "SOME TEXT ćAćA", "SOMD TDXT XAXA"]
        ];
    }
}
