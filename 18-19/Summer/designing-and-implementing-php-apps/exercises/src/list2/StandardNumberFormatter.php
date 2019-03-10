<?php

namespace numbers;

class StandardNumberFormatter implements NumberFormatter {

    public function format(MyNumber $number): string {
        return strval($number) . ' [' . $number->getBase() . ']';
    }
}