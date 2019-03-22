<?php

namespace list2;

//without renaming there are warnings in add
class MyNumber {

    private $value;
    private $base;

    public function __construct(int $value, int $base) {
        $this->value = $value;
        $this->base = $base;
    }

    public function convert(int $newBase): MyNumber {
        return new MyNumber($this->value, $newBase);
    }

    public function add(MyNumber $number): MyNumber {
        return new MyNumber($this->value + $number->getValue(), $this->base);
    }


    public function __toString(): string {
        $sign = ($this->value >= 0 ? '' : '-');
        return $sign . base_convert(strval($this->value), 10, $this->base);
    }

    public function getValue(): int {
        return $this->value;
    }

    public function getBase(): int {
        return $this->base;
    }
}