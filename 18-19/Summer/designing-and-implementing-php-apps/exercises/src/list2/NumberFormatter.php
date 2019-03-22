<?php

namespace list2;

interface NumberFormatter {
    public function format(MyNumber $number) : string ;
}