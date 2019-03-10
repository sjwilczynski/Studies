<?php

use numbers\MyNumber;
use numbers\RomanNumberFormatter;
use numbers\RomanNumberRangeExceededException;


require __DIR__ . '/../vendor/autoload.php';

if ($argc < 2) {
    exit("You should pass at least 2 parameters" . PHP_EOL);
}

try {
    $sum = new MyNumber(0, 10);
    $formatter = new RomanNumberFormatter();
    foreach (array_slice($argv, 1) as $numberWithBase) {
        $split = explode(":", $numberWithBase);
        $base = intval($split[1]);
        $value = intval($split[0], $base);
        $sum = $sum->add(new MyNumber($value, $base));
    }
    echo $formatter->format($sum) . PHP_EOL;
} catch (RomanNumberRangeExceededException $e) {
    exit("Final value exceeded range allowed for roman numbers" . PHP_EOL);
}

