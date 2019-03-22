<?php

namespace list2;

class RomanNumberRangeExceededException extends \Exception {
}

class RomanNumberFormatter implements NumberFormatter {

    private $romanNumerals = array(
        'M' => 1000,
        'CM' => 900,
        'D' => 500,
        'CD' => 400,
        'C' => 100,
        'XC' => 90,
        'L' => 50,
        'XL' => 40,
        'X' => 10,
        'IX' => 9,
        'V' => 5,
        'IV' => 4,
        'I' => 1);


    /**
     * @throws RomanNumberRangeExceededException
     */
    public function format(MyNumber $number): string {
        if ($this->isOutOfRange($number)) {
            throw new RomanNumberRangeExceededException();
        }

        $representation = "";
        $value = $number->getValue();

        foreach ($this->romanNumerals as $romanString => $romanValue) {
            $times = intdiv($value, $romanValue);
            $representation .= str_repeat($romanString, $times);
            $value %= $romanValue;
        }

        return $representation;
    }

    private function isOutOfRange(MyNumber $number): bool {
        $value = $number->getValue();
        return $value > 3999 || $value < 1;
    }
}