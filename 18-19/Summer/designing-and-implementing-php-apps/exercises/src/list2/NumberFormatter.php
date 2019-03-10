<?php

namespace numbers;

interface NumberFormatter {
    public function format(MyNumber $number) : string ;
}