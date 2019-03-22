<?php

namespace list2;

class StandardNumberFormatter implements NumberFormatter {

    public function format(MyNumber $number): string {
        return strval($number) . ' [' . $number->getBase() . ']';
    }
}