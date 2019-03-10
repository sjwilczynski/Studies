<?php

namespace numbers;

use PHPUnit\Framework\TestCase;

class NumberTest extends TestCase {

    public function testAddNumbersWithTheSameBase(): void {
        $num1 = new MyNumber(10, 10);
        $num2 = new MyNumber(20, 10);

        $added = $num1->add($num2);

        self::assertEquals(new MyNumber(30, 10), $added);

    }

    public function testAddNumbersWithDifferentBase(): void {
        $num1 = new MyNumber(10, 10);
        $num2 = new MyNumber(20, 2);

        $added = $num1->add($num2);

        self::assertEquals(new MyNumber(30, 10), $added);
    }

    public function testAddDoesNotChangeOriginalObjects(): void {
        $num1 = new MyNumber(10, 10);
        $num2 = new MyNumber(20, 10);

        $num1->add($num2);

        self::assertEquals(new MyNumber(10, 10), $num1);
        self::assertEquals(new MyNumber(20, 10), $num2);
    }

    public function testConvertBackAndForth(): void {
        $num1 = new MyNumber(10, 10);
        $convertedOnce = $num1->convert(2);
        $convertedTwice = $convertedOnce->convert(10);

        self::assertEquals(new MyNumber(10, 2), $convertedOnce);
        self::assertEquals(new MyNumber(10, 10), $convertedTwice);
    }

    public function testConvertDoesNotChangeOriginalObject(): void {
        $num1 = new MyNumber(10, 10);
        $num1->convert(2);
        self::assertEquals(new MyNumber(10, 10), $num1);
    }

    public function testRepresentationGeneration(): void {
        $num1 = new MyNumber(17, 10);
        $num2 = new MyNumber(17, 2);

        self::assertEquals("17", strval($num1));
        self::assertEquals("10001", strval($num2));
    }

    public function testRepresentationGenerationNegativeValue(): void {
        $num1 = new MyNumber(-17, 10);
        $num2 = new MyNumber(-17, 2);

        self::assertEquals("-17", strval($num1));
        self::assertEquals("-10001", strval($num2));
    }
}
