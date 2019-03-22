<?php

namespace list2;

use PHPUnit\Framework\TestCase;

class NumberFormatterTest extends TestCase {

    public function testStandardFormatter(): void {
        $formatter = new StandardNumberFormatter();
        $num1 = new MyNumber(1467, 10);
        $num2 = new MyNumber(1467, 2);

        self::assertEquals("1467 [10]", $formatter->format($num1));
        self::assertEquals("10110111011 [2]", $formatter->format($num2));
    }

    /**
     * @throws RomanNumberRangeExceededException
     */
    public function testRomanFormatter(): void {
        $formatter = new RomanNumberFormatter();
        $num1 = new MyNumber(1467, 10);
        $num2 = new MyNumber(1467, 2);

        self::assertEquals("MCDLXVII", $formatter->format($num1));
        self::assertEquals("MCDLXVII", $formatter->format($num2));
    }

    /**
     * @throws RomanNumberRangeExceededException
     */
    public function testOutOfRangeForRomanFormatter(): void {
        $this->expectException(RomanNumberRangeExceededException::class);
        $formatter = new RomanNumberFormatter();
        $num = new MyNumber(4190, 10);

        $formatter->format($num);
    }
}
